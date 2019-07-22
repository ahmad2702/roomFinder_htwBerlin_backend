package com.sadullaev.htw.ai.bachelor.data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Time {
	
	transient private static final int DEFAULT_START_TIME = 7;
	transient private static final int DEFAULT_END_TIME = 22;
	
	transient private Timestamp min;
	transient private Timestamp max;
	
	private Timestamp begin;
    private Timestamp end;
    
    private long time; // in Minuten
	
    public Time() {
    	
    }
    
    public Time(Timestamp begin, Timestamp end) {
		this.begin = begin;
		this.end = end;
		
		calculateTime();
		
		Date date = new Date(begin.getTime());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_START_TIME);
		calendar.set(Calendar.MINUTE, 0);
		min = new Timestamp(calendar.getTime().getTime());
		
		
		calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_END_TIME);
		calendar.set(Calendar.MINUTE, 0);
		max = new Timestamp(calendar.getTime().getTime());
	}
    
    public Time(Date date) {
    	Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
    	
		calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_START_TIME);
		calendar.set(Calendar.MINUTE, 0);
    	begin = new Timestamp(calendar.getTime().getTime());
    	
    	calendar.set(Calendar.HOUR_OF_DAY, DEFAULT_END_TIME);
		calendar.set(Calendar.MINUTE, 0);
    	end = new Timestamp(calendar.getTime().getTime());
		
		calculateTime();
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
	
	public void calculateTime() {
		Date dateBegin = new Date(begin.getTime());
		Calendar beginCalendar = Calendar.getInstance();
		beginCalendar.setTime(dateBegin);
		
		
		Date dateEnd = new Date(end.getTime());
		Calendar endCalendar = Calendar.getInstance();
		endCalendar.setTime(dateEnd);
		
		time = (endCalendar.get(Calendar.HOUR_OF_DAY)*60+endCalendar.get(Calendar.MINUTE))
				-(beginCalendar.get(Calendar.HOUR_OF_DAY)*60+beginCalendar.get(Calendar.MINUTE));
	}

	public Timestamp getMin() {
		return min;
	}

	public Timestamp getMax() {
		return max;
	}

	@Override
	public String toString() {
		return "[begin=" + begin + ", end=" + end + ", time=" + time + "]";
	}
	
	
}

