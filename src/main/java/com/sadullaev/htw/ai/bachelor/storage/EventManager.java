package com.sadullaev.htw.ai.bachelor.storage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Column;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.GroupedData;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.mortbay.util.ajax.JSON;

import com.sadullaev.htw.ai.bachelor.model.Event;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;

import scala.tools.nsc.typechecker.PatternMatching.DPLLSolver.Lit;

public class EventManager {

	private static DataFrame dataFrame = null;
	private static ApacheSparkConnect apacheSparkConnect;
	private static DatabaseConnect databaseConnect;
	private static DatabaseTables databaseTables;
	
	
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
	
	
	public List<String> getEventsFiltered(String title, int number) {
		DataFrame dataFrameResult = dataFrame.filter(dataFrame.col("name").contains(title));
		
		JavaRDD<String> jsonRDD = dataFrameResult.toJSON().toJavaRDD();     
		
		List<String> mylist = jsonRDD.collect().stream().limit(number).collect(Collectors.toList());   
		return mylist;
	}

	public List<String> getEventsFilteredFree(String date, int number) {
		DataFrame dataFrameResult = dataFrame.filter(dataFrame.col("date").contains(date));
		
		Column cols = new Column("room");
		DataFrame rooms = dataFrame.select(cols).distinct();
		rooms.show();
		
		JavaRDD<String> jsonRDD = dataFrameResult.toJSON().toJavaRDD();     
		
		List<String> mylist = jsonRDD.collect().stream().limit(number).collect(Collectors.toList());   
		return mylist;
	}
	
	
	
	
}
