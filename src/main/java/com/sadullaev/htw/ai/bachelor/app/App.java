package com.sadullaev.htw.ai.bachelor.app;

import static spark.Spark.*;

import com.sadullaev.htw.ai.bachelor.storage.EventManager;


public class App 
{
    public static void main( String[] args )
    {
        
    	EventManager eventManager = new EventManager();
        eventManager.setup();
    	
    	
    	get("/hello", (request, response)->{
    		response.type("text/plain");
    		return "Hallo";
    	});
    	
    	
    	get("/sss", (request, response)->{
    		response.type("text/plain");
    		return "Sss";
    	});
    	
    	
    	
    	
    	
    	
    	
    }
}
