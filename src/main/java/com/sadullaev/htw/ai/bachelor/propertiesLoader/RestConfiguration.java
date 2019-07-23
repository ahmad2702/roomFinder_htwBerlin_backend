package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RestConfiguration {
	
	static Properties property = new Properties();
	
	private static int port;
	
	public RestConfiguration() {
		load();
	}
	
	public static void load() {
		
		try {
            InputStream input = DatabaseConnect.class.getClassLoader().getResourceAsStream("rest.properties");
            property.load(input);

            port = Integer.parseInt(property.getProperty("port"));

        } catch (IOException e) {
            System.err.println("Properties fuer RestConfiguration nicht gefunden!");
        }

	}

	public static int getPort() {
		return port;
	}
	
	
	
}
