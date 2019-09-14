package com.sadullaev.htw.ai.bachelor.restApi.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class TestDBConnectProperties {
	
	private static File file = new File("src/main/resources/db_connect.properties");
	
	@Test
	public void testFileExistsAndCanRead() {
		boolean result = false;

		if (file.exists() && file.canRead()) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void testHostNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("host").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testPortNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("port").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testLoginNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("login").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testPasswordNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("password").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testUnicodeNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("useUnicode").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testCharacterEncodingNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("characterEncoding").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testReconnectNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("autoReconnect").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testSslNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("useSSL").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testTimezoneNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("serverTimezone").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
}
