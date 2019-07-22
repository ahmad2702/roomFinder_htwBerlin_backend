package com.sadullaev.htw.ai.bachelor.data;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Row;

public class Room {
	
	private String room;
	private List<Time> freeTimes = new ArrayList<Time>();
	
	transient DateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public Room(String room, List<Time> freeTimes) {
		this.room = room;
		this.freeTimes = freeTimes;
	}
	
	public Room() {
		
	}

	public Room(String room) {
		this.room = room;
	}
	
	public void extractAndSaveFreeTime(Iterable<Row> actualEventsInRoom) throws ParseException {
		List<Time> actualEvents = new ArrayList<Time>();
		
		for(Row event : actualEventsInRoom) {
			String beginTime=event.get(2).toString();  
			String endTime=event.get(3).toString();  
			
			Timestamp begin = new Timestamp(timeFormat.parse(beginTime).getTime());
			Timestamp end = new Timestamp(timeFormat.parse(endTime).getTime());
			
			Time ev = new Time(begin, end);
			actualEvents.add(ev);
		}
		
		if(actualEvents.size()!=0) {
			
			if ((actualEvents.get(0).getBegin().getTime()-actualEvents.get(0).getMin().getTime()) > 0 ) {
				Time freeR = new Time(actualEvents.get(0).getMin(), actualEvents.get(0).getBegin());
				freeTimes.add(freeR);
			}
			
			if(actualEvents.size()>1) {
				for(int i = 0; i < actualEvents.size()-1; i++) {
					Timestamp begin = actualEvents.get(i).getEnd();
					Timestamp end = actualEvents.get(i+1).getBegin();
					
					Time freeR = new Time(begin, end);
					freeTimes.add(freeR);
				}
			}
			
			if ( (actualEvents.get(0).getMax().getTime()-actualEvents.get(actualEvents.size()-1).getEnd().getTime()) >0 ) {
				Time freeR = new Time(actualEvents.get(actualEvents.size()-1).getEnd(), actualEvents.get(actualEvents.size()-1).getMax());
				freeTimes.add(freeR);
			}
			
		}
	}
	
	public void add(Time event) {
		freeTimes.add(event);
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public List<Time> getFreeTimes() {
		return freeTimes;
	}

	public void setFreeTimes(List<Time> freeTimes) {
		this.freeTimes = freeTimes;
	}

	@Override
	public String toString() {
		return "Room [room=" + room + ", freeTimes=" + freeTimes + "]";
	}
	
	
	
	
}
