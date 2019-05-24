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
        
    	EventManager eventManager = new EventManager();
        eventManager.setup();
        eventManager.loadData();
    	
    	
    	get("/hello", (request, response)->{
    		response.type("text/plain");
    		return "Hallo";
    	});
    	
    	
    	get("/sss", (request, response)->{
    		response.type("text/plain");
    		return "Sss";
    	});
    	
    	get("/events/:number", (request, response)->{
    		response.type("application/json");
    		List<Event> collection = eventManager.getAllEvents();
    		
    		int number = Integer.parseInt(request.params(":number"));
    		List<Event> result = collection.stream().limit(number).collect(Collectors.toList());
    		
    		return new Gson().toJson(new Gson().toJsonTree(result));
    	});
    	
    	
    	
    	
    	
    }
}
