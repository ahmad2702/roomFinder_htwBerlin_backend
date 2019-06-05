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
    	
    	get("/events/pro/test", (request, response)->{
    		response.type("application/json");
    		
    		String title = "Comp";
    		String date = "2019-06-02";
    		int number = 10;
    		
    		return eventManager.getEventsFilteredFree(date, number);
    	});
    	
    	
    	
    	//for web
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
    		int number = Integer.parseInt(request.params(":number"));
    		
    		return eventManager.getAll(number);
    	});
    	
    	
    	
    	
    	
    }
}
