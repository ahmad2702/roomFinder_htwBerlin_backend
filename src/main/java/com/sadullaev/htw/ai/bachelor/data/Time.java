package com.sadullaev.htw.ai.bachelor.data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;

public class Time {
	
	/*
	 * Class variables
	 */
	
	transient private static final int DEFAULT_START_TIME = 7;
	transient private static final int DEFAULT_END_TIME = 22;
	
	/**
	 * Instance variables
	 */
	
	transient private Timestamp min;
	transient private Timestamp max;
	private Timestamp begin;
    private Timestamp end;
    private long time; // in Minuten
	
    /**
     * Constructor
     */
    public Time() {
    	
    }
    
    /**
     * Constructor
     * @param begin
     * @param end
     */
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
    
    /**
     * Constructor
     * @param date
     */
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

    /**
     * Getter function for begin
     * @return begin
     */
	public Timestamp getBegin() {
		return begin;
	}

	/**
	 * Setter function for begin
	 * @param begin
	 */
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}

	/**
	 * Getter function for end
	 * @return end
	 */
	public Timestamp getEnd() {
		return end;
	}

	/**
	 * Setter function for end
	 * @param end
	 */
	public void setEnd(Timestamp end) {
		this.end = end;
	}

	/**
	 * Getter function for time
	 * @return time
	 */
	public long getTime() {
		return time;
	}

	/**
	 * Setter function for time
	 * @param time
	 */
	public void setTime(long time) {
		this.time = time;
	}
	
	/**
	 * Function for time calculation
	 */
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

	/**
	 * Getter function for time minimum
	 * @return
	 */
	public Timestamp getMin() {
		return min;
	}

	/**
	 * Getter function for time maximum
	 * @return
	 */
	public Timestamp getMax() {
		return max;
	}

	/**
	 * Overrided function for TOSTRING for best performance
	 */
	@Override
	public String toString() {
		return "[begin=" + begin + ", end=" + end + ", time=" + time + "]";
	}

}

