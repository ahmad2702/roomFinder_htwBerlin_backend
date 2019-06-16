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
    	//ipAddress("192.168.0.80");
    	
    	EventManager.setupAndLoad();
        EventManager eventManager = new EventManager();
        
        
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
    		String date = request.headers("date");
    		String lecturer = request.headers("lecturer");
    		int number = Integer.parseInt(request.headers("number"));
    		
    		return eventManager.getEventsFiltered(title, date, lecturer, number);
    	});
    	
    	get("/events/pro/test", (request, response)->{
    		response.type("application/json");
    		
    		String title = "Comp";
    		String date = "2019-06-02";
    		int number = 10;
    		
    		return eventManager.getEventsFilteredFree(date, number);
    	});
    	
    	
    	
    	
    	
    	
    	
    	
    	get("/events/:number", (request, response)->{
    		response.type("application/json");    		
    		int number = Integer.parseInt(request.params(":number"));
    		
    		return eventManager.getAll(number);
    	});
    	
    	
    	
    }
}
