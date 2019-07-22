package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApacheSparkConnect {
	
    static Properties property = new Properties();
    
    private static String appName;
    private static String master;
    private static String executorMemory;
    
    public ApacheSparkConnect() {
    	load();
    }
    
    public static void load() {
    	
    	try {
            InputStream input = ApacheSparkConnect.class.getClassLoader().getResourceAsStream("apache_spark.properties");
            property.load(input);

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
