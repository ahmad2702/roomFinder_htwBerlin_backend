package com.sadullaev.htw.ai.bachelor.restApi;

import static spark.Spark.*;

public class App 
{
    public static void main( String[] args )
    {
        
    	
    	get("/hello", (request, response)->{
    		response.type("text/plain");
    		return "Hallo";
    	});
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    }
}
