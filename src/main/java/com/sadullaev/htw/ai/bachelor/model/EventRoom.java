package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Row;


public class EventRoom {
	
	private String room;
	private Date date;
	
	private List<Event> events = new ArrayList<Event>();
	
	transient DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	transient DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	
	public EventRoom(String room, Iterable<Row> rowEvents) throws ParseException {
		this.room = room;
		
		for(Row row : rowEvents) {
			
			String dateDate=row.get(0).toString();  
			String beginTime=row.get(2).toString();  
			String endTime=row.get(3).toString();  
			
			
			
			try {
				this.date=new Date(dateFormat.parse(dateDate).getTime());
			} catch (ParseException e) {
				System.out.println("Date problem!");
			}  
			
			
			Timestamp begin = new Timestamp(timeFormat.parse(beginTime).getTime());
			Timestamp end = new Timestamp(timeFormat.parse(endTime).getTime());

			Event event = new Event(begin, end);
			this.events.add(event);
		}
		//Collections.sort(events, (a,b) -> a.getBegin().compareTo(b.getEnd()));
			
	}
	
	
	
	public String getRoom() {
		return room;
	}



	public void setRoom(String room) {
		this.room = room;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public List<Event> getEvents() {
		return events;
	}



	public void setEvents(List<Event> events) {
		this.events = events;
	}



	public List<Event> getFreeTimes() {
		List<Event> freeTimes = new ArrayList<Event>();
		
		if(events.size()!=0) {
			
			if ((events.get(0).getBegin().getTime()-events.get(0).getMin().getTime()) > 0 ) {
				Event freeR = new Event(events.get(0).getMin(), events.get(0).getBegin());
				freeTimes.add(freeR);
			}
			
			if(events.size()>1) {
				for(int i = 0; i < events.size()-1; i++) {
					Timestamp begin = events.get(i).getEnd();
					Timestamp end = events.get(i+1).getBegin();
					
					Event freeR = new Event(begin, end);
					freeTimes.add(freeR);
				}
			}
			
			if ( (events.get(0).getMax().getTime()-events.get(events.size()-1).getEnd().getTime()) >0 ) {
				Event freeR = new Event(events.get(events.size()-1).getEnd(), events.get(events.size()-1).getMax());
				freeTimes.add(freeR);
			}
			
			
			
		}
		
		return freeTimes;
		
	}
	
	

	@Override
	public String toString() {
		return "room=" + room + ", date=" + date + ", events=" + events;
	}

	
	
	

	
	
	
	
	
	
}
