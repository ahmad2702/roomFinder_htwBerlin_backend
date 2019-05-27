package com.sadullaev.htw.ai.bachelor.app;

import static spark.Spark.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.sadullaev.htw.ai.bachelor.model.Event;
import com.sadullaev.htw.ai.bachelor.storage.EventManager;


public class BackendApp 
{
    public static void main( String[] args )
    {
    	port(7777);
        
    	
    	EventManager.setupAndLoad();
    	
        
        EventManager eventManager = new EventManager();
        
        
    	get("/hello", (request, response)->{
    		response.type("text/plain");
    		return "Hallo";
    	});
    	
    	
    	get("/events/pro", (request, response)->{
    		response.type("application/json");
    		
    		String title = request.headers("title");
    		int number = Integer.parseInt(request.headers("number"));
    		
    		return eventManager.getEventsFiltered(title, number);
    	});
    	
    	
    	get("/events/all", (request, response)->{
    		response.type("application/json");
    		return eventManager.getAll();
    	});
    	
    	get("/events/:number", (request, response)->{
    		response.type("application/json");
    		List<Event> collection = eventManager.getAllEvents();
    		
    		int number = Integer.parseInt(request.params(":number"));
    		
    		return eventManager.getAll(number);
    	});
    	
    	
    	
    	
    	
    }
}
