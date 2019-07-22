package com.sadullaev.htw.ai.bachelor.storage;

import java.sql.Date;
import java.util.List;

public interface EventManagerInterface {
	
	List<String> getEventsFiltered(String title, String date, String lecturer, int number);
	
	
	String getFreeRooms(Date dateAsDate, String dateAsString, String room, int time, int number);
	
	
}
