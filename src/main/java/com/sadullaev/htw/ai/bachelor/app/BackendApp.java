package com.sadullaev.htw.ai.bachelor.app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sadullaev.htw.ai.bachelor.propertiesLoader.ApacheSparkConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseConnect;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.DatabaseTables;
import com.sadullaev.htw.ai.bachelor.propertiesLoader.RestConfiguration;
import com.sadullaev.htw.ai.bachelor.storage.EventManager;


public class BackendApp 
{
	
	transient static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
    public static void main( String[] args )
    {
    	System.out.println("REST-API start..");
    	    	
    	// load propterties
    	new ApacheSparkConnect();
    	new DatabaseConnect();
    	new DatabaseTables();
    	new RestConfiguration();
    	
    	run();
    }
    
    public static void run() {
    	// Set Port
    	port(RestConfiguration.getPort());
    	
    	
    	System.out.println("Apache Spark start..");
    	EventManager.setupAndLoad();
        EventManager eventManager = new EventManager();
        eventManager.extractRoomsAtUniversity();
        
        
        // For status check
    	get("/status", (request, response)->{
    		response.type("text/plain");
    		response.status(200);
    		return "REST-API is working!";
    	});
    	
    	
    	
    	
    	
    	
    	
    	//----------------------------------------------------------------------------------------------
    	// For Test
    	get("/events/all", (request, response)->{
    		response.type("application/json");
    		return eventManager.getAll();
    	});
    	
    	get("/events/number/:number", (request, response)->{
    		response.type("application/json");    		
    		int number = Integer.parseInt(request.params(":number"));
    		
    		return eventManager.getAll(number);
    	});
    	//----------------------------------------------------------------------------------------------
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	//----------------------------------------------------------------------------------------------
    	//Event Search
    	get("/events/finder", (request, response)->{
    		response.type("application/json");
    		
    		System.out.println("-----------------");
    		System.out.println("-Event Finder-");
    		
    		DateFormat dF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		java.util.Date cD = new java.util.Date();
    		System.out.println("* " + dF.format(cD));
    		
    		String ip = request.ip();
    		System.out.println("* " + ip);
    		
    		String title = request.headers("title");
    		System.out.println("Titel: " + title);
    		
    		
    		String date = request.headers("date");
    		System.out.println("Datum: " + date);
    		
    		String lecturer = request.headers("lecturer");
    		System.out.println("Dozent: " + lecturer);
    		
    		int number = Integer.parseInt(request.headers("number"));
    		System.out.println("Anzahl: " + number);
    		
    		System.out.println("-----------------");
    		
    		return eventManager.getEventsFiltered(title, date, lecturer, number);
    	});
    	//----------------------------------------------------------------------------------------------



		
    	

        
    	//----------------------------------------------------------------------------------------------
        //Free Rooms Search
        get("/rooms/free", (request, response)->{
    		response.type("application/json");    		
    		
    		System.out.println("-----------------");
    		System.out.println("-Free Room Finder-");
    		
    		DateFormat dF = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    		java.util.Date cD = new java.util.Date();
    		System.out.println("* " + dF.format(cD));
    		
    		String ip = request.ip();
    		System.out.println("* " + ip);
    		
    		String date = request.headers("date");
    		System.out.println("Datum: " + date);
    		
    		String room = null;
    		if(request.headers("room") != null && !request.headers("room").equals("")) {
    			room = new String(request.headers("room").getBytes("ISO-8859-1"), "UTF-8");
    		}
    		System.out.println("Raum: " + room);
    		
    		int time = 0;
    		if(request.headers("time") != null && !request.headers("time").equals("")) {
    			time = Integer.parseInt(request.headers("time"));
    		}
    		System.out.println("Dauer: " + time);
    		
            int number = Integer.parseInt(request.headers("number"));
            System.out.println("Anzahl: " + number);
            
            System.out.println("-----------------");
    		
            
            
            Date dateFromRequest = null;
            try {
            	dateFromRequest=new Date(dateFormat.parse(date).getTime());
    		} catch (ParseException e) {
    			System.out.println("Date problem!");
    		} 
            
    		return eventManager.getFreeRooms(dateFromRequest, date, room, time, number);
    	});
      //----------------------------------------------------------------------------------------------
        
        
        
        System.out.println("REST-API ist started.");
    }
    
}
