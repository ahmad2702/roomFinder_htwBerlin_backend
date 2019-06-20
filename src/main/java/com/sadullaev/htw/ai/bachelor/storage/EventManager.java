package com.sadullaev.htw.ai.bachelor.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import com.sadullaev.htw.ai.bachelor.model.Room;
import com.sadullaev.htw.ai.bachelor.model.RoomFreeInfo;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;


public class EventManager {
	
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
        //dataFrame.filter("lecturer='Schuy'").show();
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
		
		dataFrameResult = dataFrameResult.select(dateColumn, beginColumn, endColumn, titleColumn, lsfIdColumn, roomColumn, lecturerColumn);
		
		JavaRDD<String> jsonRDD = dataFrameResult.toJSON().toJavaRDD();     
		List<String> mylist = jsonRDD.collect().stream().limit(number).collect(Collectors.toList());   
		
		//System.out.println(Arrays.toString(mylist.toArray()));
		return mylist;
	}

	
	
	
	
	
	

	public void loadNewAll() {
		Column dateColumn = new Column("date");
		Column roomColumn = new Column("room");
		Column beginColumn = new Column("begin");
		Column endColumn = new Column("end");
		
		//LocalDate localDate = LocalDate.now();
		//String now = localDate.format(dateTimeFormatterSql);
		
	//schleife start
		String datum = "2019-06-20";
		
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
		
		day.sortRoom();
		
		infos.add(day);
	//schleife end
		
		//System.out.println(infos);
	}

	
	public void extractRoomsAtUniversity() {
		Column roomColumn = new Column("room");
		
		Row[] rooms = dataFrame.select(roomColumn).distinct().collect();
		
		
		
	} 
	
	
	
	public String getFreeRooms() {
		Gson gsonBuilder = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm").create();
		String rrr = gsonBuilder.toJson(infos);

		return rrr;
	}
	
	
	
	/**
		javaRDD.groupBy(e -> e.get(1)).foreach(item -> {
            item._2.forEach(x ->  {
            	System.out.println(x.get(1)); 
            });        		
        });
		
		
		javaRDD.foreach(item -> {
            System.out.println(item.get(1)); 
        });
		*/
	
	
	
	
	

	
	
	
	
}
