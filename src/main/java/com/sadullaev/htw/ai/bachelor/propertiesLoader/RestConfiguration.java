package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestConfiguration {
	
	/*
	 * Class variables
	 */
	
	static Properties property = new Properties();
	private static int port;
	
	/**
     * Constructor
     */
	public RestConfiguration() {
		load();
	}
	
	/**
     * Function for properties load
     */
	public static void load() {
		try {
            InputStream input = DatabaseConnect.class.getClassLoader().getResourceAsStream("rest.properties");
            property.load(input);

            port = Integer.parseInt(property.getProperty("port"));

        } catch (IOException e) {
            System.err.println("Properties fuer RestConfiguration nicht gefunden!");
        }
	}

	/**
     * Getter function for port
     * @return port
     */
	public static int getPort() {
		return port;
	}

}
