package com.sadullaev.htw.ai.bachelor.storage;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.mortbay.util.ajax.JSON;

import com.sadullaev.htw.ai.bachelor.model.Event;

public class EventManager {
	
	
	private static DataFrame dataFrame = null;
	
	public static void setupAndLoad() {
		SparkConf sparkConf = new SparkConf().setAppName("Read Op")
                .setMaster("local[*]").set("spark.executor.memory","1g");
        SparkContext sc = new SparkContext(sparkConf);
        SQLContext sqlContext = new SQLContext(sc);
        
        String sql = "(select * from events) as all_events";

        dataFrame = sqlContext
        	    .read()
        	    .format("jdbc")
        	    .option("url", "jdbc:mysql://localhost:3306/lsf_5?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=UTC")
        	    .option("user", "root")
        	    .option("password", "indigo27")
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

	
	@SuppressWarnings("unchecked")     
	public List<Event> getAllEvents (){
		
		
		return null;
	}
	
}
