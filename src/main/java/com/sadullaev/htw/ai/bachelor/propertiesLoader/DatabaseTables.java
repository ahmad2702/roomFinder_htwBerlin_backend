package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DatabaseTables {
	
	FileInputStream fis;
    Properties property = new Properties();
	
    private static String dbName;
	private static String allEvents;
	
	public DatabaseTables() {
		
		try {
            fis = new FileInputStream("src/main/resources/db_tables.properties");
            property.load(fis);
            
            dbName = property.getProperty("db.name");
            allEvents = property.getProperty("all.events");
            

        } catch (IOException e) {
            System.err.println("Properties fuer DB_Tables nicht gefunden!");
        }
		
	}

	public static String getDbName() {
		return dbName;
	}

	public static String getAllEvents() {
		return allEvents;
	}

}
