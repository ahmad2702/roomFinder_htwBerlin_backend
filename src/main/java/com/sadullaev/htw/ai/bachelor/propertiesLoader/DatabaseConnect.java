package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnect {
	
    static Properties property = new Properties();
	
    private static String host;
    private static String port;
    
    private static String login;
    private static String password;
    
    private static String useUnicode;
    private static String characterEncoding;
    private static String autoReconnect;
    private static String useSSL;
    private static String serverTimezone;
    
	public static void load() {
		
		try {
            InputStream input = DatabaseConnect.class.getClassLoader().getResourceAsStream("db_connect.properties");
            property.load(input);

            host = property.getProperty("host");
            port = property.getProperty("port");
            
            login = property.getProperty("login");
            password = property.getProperty("password");
            
            useUnicode = property.getProperty("useUnicode");
            characterEncoding = property.getProperty("characterEncoding");
            autoReconnect = property.getProperty("autoReconnect");
            useSSL = property.getProperty("useSSL");
            serverTimezone = property.getProperty("serverTimezone");
            

        } catch (IOException e) {
            System.err.println("Properties fuer DB_Connect nicht gefunden!");
        }

	}

	public static String getHost() {
		return host;
	}

	public static String getPort() {
		return port;
	}

	public static String getLogin() {
		return login;
	}

	public static String getPassword() {
		return password;
	}

	public static String getUseUnicode() {
		return useUnicode;
	}

	public static String getCharacterEncoding() {
		return characterEncoding;
	}

	public static String getAutoReconnect() {
		return autoReconnect;
	}

	public static String getUseSSL() {
		return useSSL;
	}

	public static String getServerTimezone() {
		return serverTimezone;
	}

}
