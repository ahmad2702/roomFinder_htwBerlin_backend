package com.sadullaev.htw.ai.bachelor.data;

import java.io.Serializable;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.spark.sql.Row;

public class FreeRoomList implements Serializable {
	
	/**
	 * Version
	 */
	transient private static final long serialVersionUID = 1L;
	
	/**
	 * Instance variables
	 */
	
	private Date date;
	private List<Room> rooms = new ArrayList<Room>();
	transient DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * Constructor
	 * @param date
	 * @param rooms
	 */
	public FreeRoomList(Date date, List<Room> rooms) {
		this.date = date;
		this.rooms = rooms;
	}
	
	/**
	 * Constructor
	 */
	public FreeRoomList() {
		
	}

	/**
	 * Constructor
	 * @param date
	 */
	public FreeRoomList(String date) {
		try {
			this.date=new Date(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			System.out.println("Date problem!");
		} 
	}
	
	/**
	 * Function for adding new room
	 * @param room
	 */
	public synchronized void add(Room room) {
		this.rooms.add(room);
	}
	
	/**
	 * Function for adding rest rooms
	 * @param roomList
	 */
	public synchronized void addRest(Row[] roomList) {
		for (int i = 0; i < roomList.length; i++) {
			String roomNumber = roomList[i].getString(0);
			if (!rooms.stream().anyMatch(str -> str.getRoom().equals(roomNumber))) {
				Room room = new Room(roomNumber);
				Time event = new Time(date);
				room.add(event);
				rooms.add(room);
			}
		}
		sortRoom();
	}
	
	/**
	 * Room sort
	 */
	public synchronized void sortRoom() {
		Collections.sort(rooms, (a,b) -> a.getRoom().compareTo(b.getRoom()));
	}

	/**
	 * Getter function for Date
	 * @return date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Setter function for date
	 * @param date
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Getter function for room list
	 * @return room list
	 */
	public List<Room> getRooms() {
		return rooms;
	}

	/**
	 * Setter function for room list
	 * @param rooms
	 */
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	/**
	 * Overrided function for TOSTRING for best performance
	 */
	@Override
	public String toString() {
		return "RoomFreeInfo [date=" + date + ", rooms=" + rooms + "]";
	}
	
	
	
	
}
