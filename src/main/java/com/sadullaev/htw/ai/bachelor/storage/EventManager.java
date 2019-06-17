package com.sadullaev.htw.ai.bachelor.storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;


public class EventManager {

	private static DataFrame dataFrame = null;
	private static ApacheSparkConnect apacheSparkConnect;
	private static DatabaseConnect databaseConnect;
	private static DatabaseTables databaseTables;
	
	final static DateTimeFormatter dateTimeFormatterSql = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public static void setupAndLoad() {
		apacheSparkConnect = new ApacheSparkConnect();
		databaseConnect = new DatabaseConnect();
		databaseTables = new DatabaseTables();
		
		SparkConf sparkConf = new SparkConf().setAppName(ApacheSparkConnect.getAppName())
                .setMaster(ApacheSparkConnect.getMaster()).set("spark.executor.memory", ApacheSparkConnect.getExecutorMemory());
        SparkContext sc = new SparkContext(sparkConf);
        SQLContext sqlContext = new SQLContext(sc);
        
        String sql = "(select * from "+ DatabaseTables.getAllEvents() +") as all_events";

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

	
	

	
	
	
	
}
