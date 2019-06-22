package com.sadullaev.htw.ai.bachelor.app;

import static spark.Spark.get;
import static spark.Spark.port;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.sadullaev.htw.ai.bachelor.storage.EventManager;


public class BackendApp 
{
	
	transient static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
    public static void main( String[] args )
    {
    	System.out.println("REST-API start..");
    	port(7777);
    	//ipAddress("192.168.0.80");
    	
    	System.out.println("Apache Spark start..");
    	EventManager.setupAndLoad();
        EventManager eventManager = new EventManager();
        eventManager.extractRoomsAtUniversity();
       
        
        System.out.println("Started!");
        
        
    	get("/hello", (request, response)->{
    		response.type("text/plain");
    		return "Hallo";
    	});
    	
    	
    	get("/events/all", (request, response)->{
    		response.type("application/json");
    		return eventManager.getAll();
    	});
    	
    	
    	
    	
    	
    	
    	
    	//for web
    	get("/events/finder", (request, response)->{
    		response.type("application/json");
    		
    		System.out.println("-Event Finder-");
    		
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




		
    	get("/events/number/:number", (request, response)->{
    		response.type("application/json");    		
    		int number = Integer.parseInt(request.params(":number"));
    		
    		return eventManager.getAll(number);
    	});

        
        
        //Free Rooms
        get("/rooms/free", (request, response)->{
    		response.type("application/json");    		
    		
    		System.out.println("-Free Room Finder-");
    		
    		String date = request.headers("date");
    		System.out.println("Datum: " + date);
    		
    		String room = request.headers("room");
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
        
         
        
        /*
        Date dateFromRequest = null;
        try {
        	dateFromRequest=new Date(dateFormat.parse("2019-06-24").getTime());
		} catch (ParseException e) {
			System.out.println("Date problem!");
		} 
        eventManager.getFreeRooms(dateFromRequest, "2019-06-24", "WH Gebäude C 624", 30, 30);
        */
    	
    	
    }
}
