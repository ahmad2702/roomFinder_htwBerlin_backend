package com.sadullaev.htw.ai.bachelor.restApi.tests;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;
import com.sadullaev.htw.ai.bachelor.restApi.testModel.TestEvent;
import com.sadullaev.htw.ai.bachelor.storage.EventManager;

@SuppressWarnings("static-access")
public class TestEventManager {
	
	private static SessionFactory sessionFactory;
	private static DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm");
	private static DateFormat formatSQL = new SimpleDateFormat("yyyy-MM-dd");
	private static List<TestEvent> eventList;
	
	private static EventManager eventManager;
	
	
	@BeforeClass
	public static void initTest() throws ParseException, IOException {
		File dbFile = new File("src/main/resources/db_connect.properties");
		InputStream inputStream = new FileInputStream(dbFile);
		Properties dbProperties = new Properties();
		dbProperties.load(inputStream);
		
		File tableFile = new File("src/main/resources/db_tables.properties");
		inputStream = new FileInputStream(tableFile);
		Properties tableProperties = new Properties();
		tableProperties.load(inputStream);
		
		File hibernateFile = new File("src/test/resources/hibernate.properties");
		inputStream = new FileInputStream(hibernateFile);
		Properties hibernateProperties = new Properties();
		hibernateProperties.load(inputStream);
		hibernateProperties.setProperty("hibernate.connection.url", "jdbc:mysql://"+dbProperties.getProperty("host")+":"+dbProperties.getProperty("port")+
				"/"+tableProperties.getProperty("db.name"));
		hibernateProperties.setProperty("hibernate.connection.username", dbProperties.getProperty("login"));
		hibernateProperties.setProperty("hibernate.connection.password", dbProperties.getProperty("password"));
		
		try {
			// Hibernate config
			Configuration configuration = new Configuration().addProperties(hibernateProperties);
	        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
	        configuration.addAnnotatedClass(TestEvent.class);
	        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}catch (Exception e) {
			System.out.println("SessionFactory kann nicht initialisiert werden, "
					+ "da die Datei hibernate.properties nicht vervollstaendigt ist.");
		}

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
        
		try {
			Session session = sessionFactory.openSession();  
			for (TestEvent event: eventList) {
				session.beginTransaction();
				session.save(event);
				session.getTransaction().commit();
			}
			session.close();
		} catch (Exception e) {
			System.out.println("SessionFactory war nicht initialisiert.");
		}

		// Eventmanger config
    	new ApacheSparkConnect();
    	new DatabaseConnect();
    	new DatabaseTables().setAllEvents("events_test");
    	
    	try {
			eventManager = new EventManager();
    	eventManager.setupAndLoad();
		} catch (Exception e) {
			System.out.println("Apache Spark funktioniert nicht, da db_connect.propeties falsch verfollstaendigt ist.");
		}
    	
	}
	
	@AfterClass
	public static void setUpAfter() {
		try {
			Session session = sessionFactory.openSession();    
			String deleteAllSqlQuery = "delete FROM com.sadullaev.htw.ai.bachelor.restApi.testModel.TestEvent";
			session.beginTransaction();
			session.createQuery(deleteAllSqlQuery).executeUpdate();
			session.close();
		} catch (Exception e) {
			// Session can't be closed.
		}
	}
	
	@Test
	public void getAllTest() {
		String responsedJson = null;
		
		String erwartetJson = "[{\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 17:00:00.0\",\"end\":\"2019-07-01 "
				+ "18:30:00.0\",\"name\":\"English1\",\"lsf_id\":5.0,\"room\":\"123\",\"lecturer\":\"Prof.Dr.Test5\"}, {\""
				+ "date\":\"2019-07-01\",\"begin\":\"2019-07-01 09:45:00.0\",\"end\":\"2019-07-01 11:15:00.0\",\"name\":\""
				+ "Mathe2\",\"lsf_id\":2.0,\"room\":\"345\",\"lecturer\":\"Prof.Dr.Test2\"}, {\"date\":\"2019-07-01\",\""
				+ "begin\":\"2019-07-01 12:15:00.0\",\"end\":\"2019-07-01 13:45:00.0\",\"name\":\"Mathe3\",\"lsf_id\":3.0,\""
				+ "room\":\"456\",\"lecturer\":\"Prof.Dr.Test3\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 "
				+ "14:00:00.0\",\"end\":\"2019-07-01 15:30:00.0\",\"name\":\"Mathe4\",\"lsf_id\":4.0,\"room\":\"567\",\""
				+ "lecturer\":\"Prof.Dr.Test4\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 08:00:00.0\",\"end\":\""
				+ "2019-07-01 09:30:00.0\",\"name\":\"Mathe1\",\"lsf_id\":1.0,\"room\":\"624\",\"lecturer\":\"Prof.Dr.Test1\"}]";
		try {
			List<String> all = eventManager.getAll();
			responsedJson = all.toString();
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		assertTrue(responsedJson!=null && responsedJson.equals(erwartetJson));
	}
	
	@Test
	public void getAllLimitedTest() {
		String responsedJson = null;
		
		String erwartetJson = "[{\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 17:00:00.0\",\"end\":\""
				+ "2019-07-01 18:30:00.0\",\"name\":\"English1\",\"lsf_id\":5.0,\"room\":\"123\",\"lecturer\":\""
				+ "Prof.Dr.Test5\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 09:45:00.0\",\"end\":\""
				+ "2019-07-01 11:15:00.0\",\"name\":\"Mathe2\",\"lsf_id\":2.0,\"room\":\"345\",\"lecturer\":\"Prof.Dr.Test2\"}]";
		
		try {
			List<String> all = eventManager.getAll(2);
			responsedJson = all.toString();
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		assertTrue(responsedJson!=null && responsedJson.equals(erwartetJson));
	}
	
	@Test
	public void findEventTest() {
		String responsedJson = null;
		
		String erwartetJson = "[{\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 09:45:00.0\",\""
				+ "end\":\"2019-07-01 11:15:00.0\",\"name\":\"Mathe2\",\"lsf_id\":2.0,\"room\":\"345\",\""
				+ "lecturer\":\"Prof.Dr.Test2\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 12:15:00.0\",\""
				+ "end\":\"2019-07-01 13:45:00.0\",\"name\":\"Mathe3\",\"lsf_id\":3.0,\"room\":\"456\",\""
				+ "lecturer\":\"Prof.Dr.Test3\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 14:00:00.0\",\""
				+ "end\":\"2019-07-01 15:30:00.0\",\"name\":\"Mathe4\",\"lsf_id\":4.0,\"room\":\"567\",\"lecturer\":\""
				+ "Prof.Dr.Test4\"}, {\"date\":\"2019-07-01\",\"begin\":\"2019-07-01 08:00:00.0\",\"end\":\""
				+ "2019-07-01 09:30:00.0\",\"name\":\"Mathe1\",\"lsf_id\":1.0,\"room\":\"624\",\"lecturer\":\"Prof.Dr.Test1\"}]";
		
		try {
			List<String> all = eventManager.getEventsFiltered("Mathe", "2019-07-01", "", 15);
			responsedJson = all.toString();
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		assertTrue(responsedJson!=null && responsedJson.equals(erwartetJson));
	}
	
	@Test
	public void extractRoomsTest() {
		String rooms = null;
		
		String erwartet = "[[567], [456], [345], [123], [624]]";
		
		try {
			eventManager.extractRoomsAtUniversity();
			rooms = Arrays.toString(eventManager.getRooms());
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		eventManager.setRooms(null); // for next tests necessary
		assertTrue(rooms!=null && rooms.equals(erwartet));
	}
	
	@Test
	public void loadRoomsForDayTest() throws ParseException {
		String freeRoomInfo = null;
		
		String erwartet = "[RoomFreeInfo [date=2019-07-01, rooms=[Room [room=123, freeTimes=[["
				+ "begin=2019-07-01 07:00:00.0, end=2019-07-01 17:00:00.0, time=600], [begin=2019-07-01 "
				+ "18:30:00.0, end=2019-07-01 22:00:00.0, time=210]]], Room [room=345, freeTimes=[["
				+ "begin=2019-07-01 07:00:00.0, end=2019-07-01 09:45:00.0, time=165], [begin=2019-07-01 "
				+ "11:15:00.0, end=2019-07-01 22:00:00.0, time=645]]], Room [room=456, freeTimes=[["
				+ "begin=2019-07-01 07:00:00.0, end=2019-07-01 12:15:00.0, time=315], [begin=2019-07-01 "
				+ "13:45:00.0, end=2019-07-01 22:00:00.0, time=495]]], Room [room=567, freeTimes=[["
				+ "begin=2019-07-01 07:00:00.0, end=2019-07-01 14:00:00.0, time=420], [begin=2019-07-01 "
				+ "15:30:00.0, end=2019-07-01 22:00:00.0, time=390]]], Room [room=624, freeTimes=[["
				+ "begin=2019-07-01 07:00:00.0, end=2019-07-01 08:00:00.0, time=60], [begin=2019-07-01 "
				+ "09:30:00.0, end=2019-07-01 22:00:00.0, time=750]]]]]]";
		
		String dateAsString = "2019-07-01";
		Date dateAsDate = new Date(formatSQL.parse(dateAsString).getTime());
		
		try {
			eventManager.extractRoomsAtUniversity();
			eventManager.loadNewByDate(dateAsDate, dateAsString);
			freeRoomInfo = eventManager.getFreeRoomList().toString();
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		eventManager.setRooms(null); // for next tests necessary
		assertTrue(freeRoomInfo!=null && freeRoomInfo.equals(erwartet));
	}
	
	@Test
	public void greeFreeRoomsTest() throws ParseException {
		String responsedJson = null;
		
		String erwartetJson = "[{\"date\":\"2019-07-01 00:00\",\"roomName\":\"123\",\"beginTime\":\""
				+ "2019-07-01 07:00\",\"endTime\":\"2019-07-01 17:00\",\"time\":600},{\"date\":\"2019-07-01 "
				+ "00:00\",\"roomName\":\"345\",\"beginTime\":\"2019-07-01 07:00\",\"endTime\":\"2019-07-01 09:45\",\""
				+ "time\":165},{\"date\":\"2019-07-01 00:00\",\"roomName\":\"456\",\"beginTime\":\"2019-07-01 "
				+ "07:00\",\"endTime\":\"2019-07-01 12:15\",\"time\":315},{\"date\":\"2019-07-01 00:00\",\""
				+ "roomName\":\"567\",\"beginTime\":\"2019-07-01 07:00\",\"endTime\":\"2019-07-01 14:00\",\"time\":"
				+ "420},{\"date\":\"2019-07-01 00:00\",\"roomName\":\"624\",\"beginTime\":\"2019-07-01 07:00\",\""
				+ "endTime\":\"2019-07-01 08:00\",\"time\":60},{\"date\":\"2019-07-01 00:00\",\"roomName\":\"624\",\""
				+ "beginTime\":\"2019-07-01 09:30\",\"endTime\":\"2019-07-01 22:00\",\"time\":750},{\"date\":\""
				+ "2019-07-01 00:00\",\"roomName\":\"345\",\"beginTime\":\"2019-07-01 11:15\",\"endTime\":\"2019-07-01 "
				+ "22:00\",\"time\":645},{\"date\":\"2019-07-01 00:00\",\"roomName\":\"456\",\"beginTime\":\"2019-07-01 "
				+ "13:45\",\"endTime\":\"2019-07-01 22:00\",\"time\":495},{\"date\":\"2019-07-01 00:00\",\"roomName\":\""
				+ "567\",\"beginTime\":\"2019-07-01 15:30\",\"endTime\":\"2019-07-01 22:00\",\"time\":390},{\"date\":\""
				+ "2019-07-01 00:00\",\"roomName\":\"123\",\"beginTime\":\"2019-07-01 18:30\",\"endTime\":\"2019-07-01 "
				+ "22:00\",\"time\":210}]";
		
		String dateAsString = "2019-07-01";
		Date dateAsDate = new Date(formatSQL.parse(dateAsString).getTime());
		
		try {
			eventManager.extractRoomsAtUniversity();
			responsedJson = eventManager.getFreeRooms(dateAsDate, dateAsString, "", 15, 30);
		} catch (Exception e) {
			System.out.println("Bitte pruefen, ob die Datei db_connect/db_tables.properties vervollstaendigt ist!");
		}

		eventManager.setRooms(null);
		assertTrue(responsedJson!=null && responsedJson.equals(erwartetJson));
	}

}
