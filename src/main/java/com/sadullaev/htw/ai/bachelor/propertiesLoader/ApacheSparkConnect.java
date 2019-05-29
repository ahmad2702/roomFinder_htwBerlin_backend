package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApacheSparkConnect {
	
	FileInputStream fis;
    Properties property = new Properties();
    
    private static String appName;
    private static String master;
    private static String executorMemory;
    
    public ApacheSparkConnect() {
    	
    	try {
            fis = new FileInputStream("src/main/resources/apache_spark.properties");
            property.load(fis);

            appName = property.getProperty("app.name");
            master = property.getProperty("set.master");
            executorMemory = property.getProperty("spark.executor.memory");
            
            System.out.println(appName);
        } catch (IOException e) {
            System.err.println("Properties fuer ApacheSpark_Connect nicht gefunden!");
        }
    	
    }

	public static String getAppName() {
		return appName;
	}

	public static String getMaster() {
		return master;
	}

	public static String getExecutorMemory() {
		return executorMemory;
	}


}
