package com.sadullaev.htw.ai.bachelor.app;

import static spark.Spark.get;
import static spark.Spark.port;

import com.sadullaev.htw.ai.bachelor.storage.EventManager;


public class BackendApp 
{
    public static void main( String[] args )
    {
    	System.out.println("REST-API start..");
    	port(7777);
    	//ipAddress("192.168.0.80");
    	
    	System.out.println("Apache Spark start..");
    	EventManager.setupAndLoad();
        EventManager eventManager = new EventManager();
        eventManager.extractRoomsAtUniversity();
        eventManager.loadNewAll();
        
        
        System.out.println("Started!");
        
        /*
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
    		
    		String title = request.headers("title");
    		System.out.println(title);
    		
    		
    		String date = request.headers("date");
    		System.out.println(date);
    		
    		String lecturer = request.headers("lecturer");
    		System.out.println(lecturer);
    		
    		int number = Integer.parseInt(request.headers("number"));
    		System.out.println(number);
    		
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
    		String room = "WH Gebäude F 201";
            String date = "2019-06-25";
            int time = 30;
    		
    		return eventManager.getFreeRooms();
    	});
        */
         
        
    	
    	
    }
}
