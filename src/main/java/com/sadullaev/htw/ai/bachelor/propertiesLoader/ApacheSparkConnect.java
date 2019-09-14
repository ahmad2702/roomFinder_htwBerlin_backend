package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ApacheSparkConnect {
	
	/*
	 * Class variables
	 */
	
    static Properties property = new Properties();
    private static String appName;
    private static String master;
    private static String executorMemory;
    
    /**
     * Constructor
     */
    public ApacheSparkConnect() {
    	load();
    }
    
    /**
     * Function for properties load
     */
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

    /**
     * Getter function for application name
     * @return name
     */
	public static String getAppName() {
		return appName;
	}

	/**
	 * Getter function for master
	 * @return master
	 */
	public static String getMaster() {
		return master;
	}

	/**
	 * Getter function for executor memory size
	 * @return memory size
	 */
	public static String getExecutorMemory() {
		return executorMemory;
	}

}
