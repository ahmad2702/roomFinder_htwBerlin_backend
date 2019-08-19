package com.sadullaev.htw.ai.bachelor.propertiesLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DatabaseConnect {
	
	/*
	 * Class variables
	 */
	
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
    
    /**
     * Constructor
     */
    public DatabaseConnect() {
    	load();
    }
    
    /**
     * Function for properties load
     */
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

	/**
	 * Getter function for host
	 * @return host
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * Getter function for port
	 * @return port
	 */
	public static String getPort() {
		return port;
	}

	/**
	 * Getter function for login
	 * @return login
	 */
	public static String getLogin() {
		return login;
	}

	/**
	 * Getter function for password
	 * @return password
	 */
	public static String getPassword() {
		return password;
	}

	/**
	 * Getter function for unicode
	 * @return unicode
	 */
	public static String getUseUnicode() {
		return useUnicode;
	}

	/**
	 * Getter function for character encoding
	 * @return character encoding
	 */
	public static String getCharacterEncoding() {
		return characterEncoding;
	}

	/**
	 * Getter function for auto reconnect
	 * @return auto reconnect
	 */
	public static String getAutoReconnect() {
		return autoReconnect;
	}

	/**
	 * Getter function for ssl
	 * @return ssl
	 */
	public static String getUseSSL() {
		return useSSL;
	}

	/**
	 * Getter function for timezone
	 * @return timezone
	 */
	public static String getServerTimezone() {
		return serverTimezone;
	}

}
