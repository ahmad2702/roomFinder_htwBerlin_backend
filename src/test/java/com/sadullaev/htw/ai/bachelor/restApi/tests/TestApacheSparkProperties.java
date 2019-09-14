package com.sadullaev.htw.ai.bachelor.restApi.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class TestApacheSparkProperties {
	
	private static File file = new File("src/main/resources/apache_spark.properties");
	
	@Test
	public void testFileExistsAndCanRead() {
		boolean result = false;

		if (file.exists() && file.canRead()) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void testAppNameNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("app.name").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testMasterNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("set.master").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testSparkExecutorMemoryNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("spark.executor.memory").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
}
