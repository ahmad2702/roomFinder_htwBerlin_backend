package com.sadullaev.htw.ai.bachelor.storage;

import java.sql.Date;
import java.util.List;

public interface EventManagerInterface {
	
	/**
	 * Find event room into db
	 * @param title
	 * @param date
	 * @param lecturer
	 * @param number
	 * @return list of rooms
	 */
	List<String> getEventsFiltered(String title, String date, String lecturer, int number);
	
	/**
	 * Find free room into db
	 * @param dateAsDate
	 * @param dateAsString
	 * @param room
	 * @param time
	 * @param number
	 * @return list of rooms
	 */
	String getFreeRooms(Date dateAsDate, String dateAsString, String room, int time, int number);

}
