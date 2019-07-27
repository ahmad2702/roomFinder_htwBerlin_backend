package com.sadullaev.htw.ai.bachelor.restApi;

import static org.junit.Assert.assertEquals;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
import com.sadullaev.htw.ai.bachelor.restApi.testModel.TestEvent;

import spark.servlet.SparkApplication;

@SuppressWarnings("static-access")
public class RestApiTest {

	private static SessionFactory sessionFactory;
	private static DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private static List<TestEvent> eventList;
	
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
	
	@BeforeClass
	public static void initTest() throws ParseException {
		// Hibernate config
		Configuration configuration = new Configuration();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        configuration.addAnnotatedClass(TestEvent.class);
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);		
		
        
        
     // Test objects        
        eventList = new ArrayList<TestEvent>();
        
        String dateString = "01.07.2019 ";
        Date date = new Date(new Timestamp(format.parse(dateString+"00:00").getTime()).getTime());
		
		TestEvent event1 = new TestEvent();
		event1.setDate(date);
		event1.setBegin(new Timestamp(format.parse(dateString+"08:00").getTime()));
		event1.setEnd(new Timestamp(format.parse(dateString+"09:30").getTime()));
		event1.setLsfNr("123");
		event1.setName("Mathe1");
		event1.setLsfId(1);
		event1.setBuilding("Gebäude C");
		event1.setRoom("624");
		event1.setLecturer("Prof.Dr.Test1");
		event1.setIsActual(1);
		eventList.add(event1);

		TestEvent event2 = new TestEvent();
		event2.setDate(date);
		event2.setBegin(new Timestamp(format.parse(dateString+"09:45").getTime()));
		event2.setEnd(new Timestamp(format.parse(dateString+"11:15").getTime()));
		event2.setLsfNr("234");
		event2.setName("Mathe2");
		event2.setLsfId(2);
		event2.setBuilding("Gebäude G");
		event2.setRoom("345");
		event2.setLecturer("Prof.Dr.Test2");
		event2.setIsActual(1);
		eventList.add(event2);
		
		TestEvent event3 = new TestEvent();
		event3.setDate(date);
		event3.setBegin(new Timestamp(format.parse(dateString+"12:15").getTime()));
		event3.setEnd(new Timestamp(format.parse(dateString+"13:45").getTime()));
		event3.setLsfNr("345");
		event3.setName("Mathe3");
		event3.setLsfId(3);
		event3.setBuilding("Gebäude H");
		event3.setRoom("456");
		event3.setLecturer("Prof.Dr.Test3");
		event3.setIsActual(1);
		eventList.add(event3);
		
		TestEvent event4 = new TestEvent();
		event4.setDate(date);
		event4.setBegin(new Timestamp(format.parse(dateString+"14:00").getTime()));
		event4.setEnd(new Timestamp(format.parse(dateString+"15:30").getTime()));
		event4.setLsfNr("456");
		event4.setName("Mathe4");
		event4.setLsfId(4);
		event4.setBuilding("Gebäude Z");
		event4.setRoom("567");
		event4.setLecturer("Prof.Dr.Test4");
		event4.setIsActual(1);
		eventList.add(event4);
		
		TestEvent event5 = new TestEvent();
		event5.setDate(date);
		event5.setBegin(new Timestamp(format.parse(dateString+"17:00").getTime()));
		event5.setEnd(new Timestamp(format.parse(dateString+"18:30").getTime()));
		event5.setLsfNr("104");
		event5.setName("English1");
		event5.setLsfId(5);
		event5.setBuilding("Gebäude A");
		event5.setRoom("123");
		event5.setLecturer("Prof.Dr.Test5");
		event5.setIsActual(1);
		eventList.add(event5);
        
		Session session = sessionFactory.openSession();  
		for (TestEvent event: eventList) {
			session.beginTransaction();
			session.save(event);
			session.getTransaction().commit();
		}
		session.close();
	}
	
	@AfterClass
	public static void setUpAfter() {
		Session session = sessionFactory.openSession();    
		String deleteAllSqlQuery = "delete FROM com.sadullaev.htw.ai.bachelor.restApi.testModel.TestEvent";
		session.beginTransaction();
		session.createQuery(deleteAllSqlQuery).executeUpdate();
		session.close();
	}
	
	@Test
	public void statusTest() throws HttpClientException {
		String erwartet = "REST-API is working!";
		
		GetMethod request = testServer.get("/status", false);
	    HttpResponse httpResponse = testServer.execute(request);
	    String message = new String(httpResponse.body());
	    
	    assertEquals(erwartet, message);
	}
	
	@Test
	public void getAllTest() throws HttpClientException {
		String erwartetJson = "[{\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 17:00:00.0\",\"end\":\"2019-07-01 "
				+ "18:30:00.0\",\"name\":\"English1\",\"lsf_id\":5.0,\"room\":\"123\",\"lecturer\":\"Prof.Dr.Test5\"}, {\""
				+ "date\":\"2019-07-01\",\"begin\":\"2019-07-01 09:45:00.0\",\"end\":\"2019-07-01 11:15:00.0\",\"name\":\""
				+ "Mathe2\",\"lsf_id\":2.0,\"room\":\"345\",\"lecturer\":\"Prof.Dr.Test2\"}, {\"date\":\"2019-07-01\",\""
				+ "begin\":\"2019-07-01 12:15:00.0\",\"end\":\"2019-07-01 13:45:00.0\",\"name\":\"Mathe3\",\"lsf_id\":3.0,\""
				+ "room\":\"456\",\"lecturer\":\"Prof.Dr.Test3\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 "
				+ "14:00:00.0\",\"end\":\"2019-07-01 15:30:00.0\",\"name\":\"Mathe4\",\"lsf_id\":4.0,\"room\":\"567\",\""
				+ "lecturer\":\"Prof.Dr.Test4\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 08:00:00.0\",\"end\":\""
				+ "2019-07-01 09:30:00.0\",\"name\":\"Mathe1\",\"lsf_id\":1.0,\"room\":\"624\",\"lecturer\":\"Prof.Dr.Test1\"}]";
		
		GetMethod request = testServer.get("/events/all", false);
	    HttpResponse httpResponse = testServer.execute(request);
	    String message = new String(httpResponse.body());
	    
	    assertEquals(erwartetJson, message);
	}
	
}
