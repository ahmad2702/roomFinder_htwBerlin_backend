package com.sadullaev.htw.ai.bachelor.restApi.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class TestDBTablesProperties {
	
	private static File file = new File("src/main/resources/db_tables.properties");
	
	@Test
	public void testFileExistsAndCanRead() {
		boolean result = false;

		if (file.exists() && file.canRead()) {
			result = true;
		}
		
		assertTrue(result);
	}
	
	@Test
	public void testDbNameNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("db.name").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	@Test
	public void testAllEventsTableNotEmpty() throws IOException {
		boolean result = false;
		
		InputStream inputStream = new FileInputStream(file);
		Properties property = new Properties();
		property.load(inputStream);
		
		try {
			result = !property.getProperty("all.events").isEmpty();
		}catch (Exception e) {
			result = false;
		}
		
		
		assertTrue(result);
	}
	
	
}
