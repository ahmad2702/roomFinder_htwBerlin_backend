package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
	
	private static Timestamp min;
	private static Timestamp max;
	
	private Timestamp begin;
    private Timestamp end;
    
    private long time; // in Minuten
	
    public Event(Timestamp begin, Timestamp end) {
		this.begin = begin;
		this.end = end;
		
		time = (end.getHours()*60+end.getMinutes())-(begin.getHours()*60+begin.getMinutes());
		
		min = new Timestamp(begin.getYear(), begin.getMonth(), begin.getDate(), 7, 0, 0, 0);
		max = new Timestamp(end.getYear(), end.getMonth(), end.getDate(), 22, 0, 0, 0);
	}
    
    public Event(Date date) {
    	begin = new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 7, 0, 0, 0);
		end = new Timestamp(date.getYear(), date.getMonth(), date.getDate(), 22, 0, 0, 0);
		
		time = (end.getHours()*60+end.getMinutes())-(begin.getHours()*60+begin.getMinutes());
    }
    
    public Event() {
    	
    }

	public Timestamp getBegin() {
		return begin;
	}

	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	public Timestamp getEnd() {
		return end;
	}

	public void setEnd(Timestamp end) {
		this.end = end;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public static Timestamp getMin() {
		return min;
	}

	public static Timestamp getMax() {
		return max;
	}

	@Override
	public String toString() {
		return "[begin=" + begin + ", end=" + end + ", time=" + time + "]";
	}
	
	
}

