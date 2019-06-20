package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.spark.sql.Row;

public class RoomFreeInfo {
	
	private Date date;
	private List<Room> rooms = new ArrayList<Room>();
	
	transient DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public RoomFreeInfo(Date date, List<Room> rooms) {
		this.date = date;
		this.rooms = rooms;
	}
	
	public RoomFreeInfo() {
		
	}

	public RoomFreeInfo(String date) {
		try {
			this.date=new Date(dateFormat.parse(date).getTime());
		} catch (ParseException e) {
			System.out.println("Date problem!");
		} 
	}
	
	public synchronized void add(Room room) {
		this.rooms.add(room);
	}
	
	public synchronized void addRest(Row[] roomList) {
		for (int i = 0; i < roomList.length; i++) {
			String roomNumber = roomList[i].getString(0);
			if (!rooms.stream().anyMatch(str -> str.getRoom().equals(roomNumber))) {
				Room room = new Room(roomNumber);
				Event event = new Event(date);
				room.add(event);
				rooms.add(room);
			}
		}
		sortRoom();
	}
	
	public synchronized void sortRoom() {
		Collections.sort(rooms, (a,b) -> a.getRoom().compareTo(b.getRoom()));
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	@Override
	public String toString() {
		return "RoomFreeInfo [date=" + date + ", rooms=" + rooms + "]";
	}
	
	
	
	
}
