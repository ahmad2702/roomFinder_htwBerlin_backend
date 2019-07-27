package com.sadullaev.htw.ai.bachelor.restApi;

import static org.junit.Assert.assertEquals;

import org.junit.ClassRule;
import org.junit.Test;

import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.sparkjava.test.SparkServer;
import com.sadullaev.htw.ai.bachelor.app.BackendApp;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.RestConfiguration;

import spark.servlet.SparkApplication;

@SuppressWarnings("static-access")
public class RestApiTest {
	
	private static int port = new RestConfiguration().getPort();
	
	@ClassRule
	public static SparkServer<WebAppTestSparkApp> testServer = new SparkServer<>(WebAppTestSparkApp.class, port);
	
	public static class WebAppTestSparkApp implements SparkApplication {
		  public void init() {
			new ApacheSparkConnect();
			new DatabaseConnect();
			DatabaseTables databaseTables = new DatabaseTables();
			databaseTables.setAllEvents("events_test");
		    new BackendApp().run();
		  }
	}
	
	@Test
	public void statusTest() throws HttpClientException {
		String erwartet = "OK";
		
		GetMethod request = testServer.get("/status", false);
	    HttpResponse httpResponse = testServer.execute(request);
	    String message = httpResponse.message();
	    
	    assertEquals(erwartet, message);
	}
	
}
