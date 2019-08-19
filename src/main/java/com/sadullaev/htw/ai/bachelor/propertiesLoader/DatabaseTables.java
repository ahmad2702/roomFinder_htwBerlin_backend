package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseTables {
	
	/*
	 * Class variables
	 */
	
    static Properties property = new Properties();
    private static String dbName;
	private static String allEvents;

	/**
     * Constructor
     */
	public DatabaseTables() {
		load();
	}
	
	/**
     * Function for properties load
     */
	public static void load() {
		try {
            InputStream input = DatabaseTables.class.getClassLoader().getResourceAsStream("db_tables.properties");
            property.load(input);
            
            dbName = property.getProperty("db.name");
            allEvents = property.getProperty("all.events");
            

        } catch (IOException e) {
            System.err.println("Properties fuer DB_Tables nicht gefunden!");
        }
	}

	/**
     * Getter function for db name
     * @return db name
     */
	public static String getDbName() {
		return dbName;
	}

	/**
     * Getter function for all events table
     * @return table
     */
	public static String getAllEvents() {
		return allEvents;
	}

	/**
     * Setter function for all events table
     * @return table
     */
	public static void setAllEvents(String allEvents) {
		DatabaseTables.allEvents = allEvents;
	}

}
