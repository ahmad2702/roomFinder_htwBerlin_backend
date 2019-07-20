package com.sadullaev.htw.ai.bachelor.storage;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sadullaev.htw.ai.bachelor.model.FreeTimeForResponse;
import com.sadullaev.htw.ai.bachelor.model.Room;
import com.sadullaev.htw.ai.bachelor.model.RoomFreeInfo;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;


public class EventManager {
	
	static Row[] rooms;
	
	static List<RoomFreeInfo> infos = new ArrayList<RoomFreeInfo>();
	static RoomFreeInfo day;
	
	private static DataFrame dataFrame = null;
	private static JavaSparkContext sc;
	private static SQLContext sqlContext;
	
	final static DateTimeFormatter dateTimeFormatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static void setupAndLoad() {
		new ApacheSparkConnect();
		ApacheSparkConnect.load();
		
		new DatabaseConnect();
		DatabaseConnect.load();
		
		new DatabaseTables();
		DatabaseTables.load();
		
		SparkConf sparkConf = new SparkConf().setAppName(ApacheSparkConnect.getAppName())
                .setMaster(ApacheSparkConnect.getMaster()).set("spark.executor.memory", ApacheSparkConnect.getExecutorMemory());
		sc = new JavaSparkContext(sparkConf);
		sc.setLogLevel("ERROR");
		sqlContext = new SQLContext(sc);
        
        String sql = "(select * from "+ DatabaseTables.getAllEvents() +" where is_actual=1 order by room, date, begin asc) as all_events";

        dataFrame = sqlContext
        	    .read()
        	    .format("jdbc")
        	    .option("url", "jdbc:mysql://"
        	    		+ DatabaseConnect.getHost()
        	    		+ ":"
        	    		+ DatabaseConnect.getPort()
        	    		+ "/"
        	    		+ DatabaseTables.getDbName()
        	    		+ "?useUnicode="
        	    		+ DatabaseConnect.getUseUnicode()
        	    		+ "&characterEncoding="
        	    		+ DatabaseConnect.getCharacterEncoding()
        	    		+ "&autoReconnect="
        	    		+ DatabaseConnect.getAutoReconnect()
        	    		+ "&useSSL="
        	    		+ DatabaseConnect.getUseSSL()
        	    		+ "&serverTimezone="
        	    		+ DatabaseConnect.getServerTimezone())
        	    .option("user", DatabaseConnect.getLogin())
        	    .option("password", DatabaseConnect.getPassword())
        	    .option("dbtable", sql)
        	    .load();
	}

	
	public List<String> getAll() {
		JavaRDD<String> jsonRDD = dataFrame.toJSON().toJavaRDD();      
		List<String> mylist = jsonRDD.collect();   
		return mylist;
	}
	
	public List<String> getAll(int number) {
		JavaRDD<String> jsonRDD = dataFrame.toJSON().toJavaRDD();      
		List<String> mylist = jsonRDD.collect().stream().limit(number).collect(Collectors.toList());   
		return mylist;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<String> getEventsFiltered(String title, String date, String lecturer, int number) {
		
		Column dateColumn = new Column("date");
		Column beginColumn = new Column("begin");
		Column endColumn = new Column("end");
		Column titleColumn = new Column("name");
		Column lsfIdColumn = new Column("lsf_id");
		Column roomColumn = new Column("room");
		Column lecturerColumn = new Column("lecturer");
		
		DataFrame dataFrameResult = dataFrame
				.filter(titleColumn.contains(title));
		
		if(date != null && !date.equals("")) {
			dataFrameResult = dataFrameResult.filter(dateColumn.geq(date));
		}else {
			LocalDate localDate = LocalDate.now();
			String now = localDate.format(dateTimeFormatterSql);
			dataFrameResult = dataFrameResult.filter(dateColumn.geq(now));
		}
		
		if(lecturer != null && !lecturer.equals("")) {
			dataFrameResult = dataFrameResult.filter(lecturerColumn.contains(lecturer));
		}
		
		dataFrameResult = dataFrameResult.select(dateColumn, beginColumn, endColumn, titleColumn, lsfIdColumn, roomColumn, lecturerColumn).sort(dateColumn);
		
		JavaRDD<String> jsonRDD = dataFrameResult.toJSON().toJavaRDD();     
		List<String> mylist = jsonRDD.collect().stream().limit(number).collect(Collectors.toList());   
		
		return mylist;
	}

	
	
	
	
	public void extractRoomsAtUniversity() {
		Column roomColumn = new Column("room");
		rooms = dataFrame.select(roomColumn).distinct().collect();
	}
	

	public synchronized void loadNewByDate(Date dateAsDate, String dateAsString) {
		
		if(infos.stream().anyMatch(str -> str.getDate().getTime()==dateAsDate.getTime())) {
			return;
		}
		
		Column dateColumn = new Column("date");
		Column roomColumn = new Column("room");
		Column beginColumn = new Column("begin");
		Column endColumn = new Column("end");
		
	//start
		String datum = dateAsString;
		
		DataFrame dataFrameResult = dataFrame
				.select(dateColumn, roomColumn, beginColumn, endColumn).filter(dateColumn.contains(datum));
		
		//JavaRDD<Row> javaRDD = dataFrameResult.toJavaRDD();
		JavaRDD<Row> javaRDD = sc.parallelize(dataFrameResult.collectAsList(),50);
		day = new RoomFreeInfo(datum);
		
		javaRDD.groupBy(e -> e.get(1)).foreach(item -> {
			Room room = new Room(item._1.toString());
            room.extractAndSaveFreeTime(item._2);
            day.add(room);
            
        });
		day.addRest(rooms);
		day.sortRoom();
		
		infos.add(day);
	//end
	}

	public String getFreeRooms(Date dateAsDate, String dateAsString, String room, int time, int number) {
		List<FreeTimeForResponse> result = null;
		
		if(!infos.stream().anyMatch(str -> str.getDate().getTime()==dateAsDate.getTime())) {
			loadNewByDate(dateAsDate,dateAsString);
		}
		
		
		RoomFreeInfo roomFreeInfo = infos.stream().filter(str -> str.getDate().getTime()==dateAsDate.getTime()).findFirst().orElse(null);
		List<Room> allRooms = roomFreeInfo.getRooms();
		
		if(room != null && !room.equals("")) {
			allRooms = allRooms.stream().filter(x-> x.getRoom().contains(room)).collect(Collectors.toList());
		}


		result = allRooms.stream().flatMap(x -> x.getFreeTimes().stream().map(zeit -> new FreeTimeForResponse(dateAsDate, x.getRoom(), zeit.getBegin(), zeit.getEnd(), zeit.getTime()))).collect(Collectors.toList());;

		
		
		int timeAsIntAndInMiliseconds = time * 60000;
		if(time != 0) {
			result = result.stream().filter(x-> x.getTime()>=time && 
					x.getEndTime().getTime()>= (dateAsDate.getTime()+timeAsIntAndInMiliseconds)).collect(Collectors.toList());
		}else {
			result = result.stream().filter(x-> x.getTime()!=0).collect(Collectors.toList());
		}
		
		result.sort(Comparator.comparing(FreeTimeForResponse::getBeginTime));
				
		Gson gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		String json = gsonBuilder.toJson(result.stream().limit(number).collect(Collectors.toList()));
		
		return json;
	}
	

	

	
	
	
	
}
