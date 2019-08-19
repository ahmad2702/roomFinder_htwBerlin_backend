package com.sadullaev.htw.ai.bachelor.data;

import java.sql.Date;
import java.sql.Timestamp;

public class FreeTimeResponse {
	
	/**
	 * Instance variables
	 */
	
	private Date date;
	private String roomName;
	private Timestamp beginTime;
	private Timestamp endTime;
	private long time;
	
	/**
	 * Constructor
	 * @param date
	 * @param roomName
	 * @param beginTime
	 * @param endTime
	 * @param time
	 */
	public FreeTimeResponse(Date date, String roomName, Timestamp beginTime, Timestamp endTime, long time) {
		this.date = date;
		this.roomName = roomName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.time = time;
	}

	/**
	 * Getter function for date
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
	 * Getter function for Room
	 * @return room
	 */
	public String getRoomName() {
		return roomName;
	}

	/**
	 * Setter function for room
	 * @param roomName
	 */
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	/**
	 * Getter function for begin
	 * @return begin as timestamp
	 */
	public Timestamp getBeginTime() {
		return beginTime;
	}

	/**
	 * Setter function for begin
	 * @param beginTime as timestamp
	 */
	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	/**
	 * Getter function for end
	 * @return end as timestamp
	 */
	public Timestamp getEndTime() {
		return endTime;
	}

	/**
	 * Setter function for end
	 * @param endTime as timestamp
	 */
	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	/**
	 * Getter function for time
	 * @return time as long
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
	 * Overrided function for TOSTRING for best performance
	 */
	@Override
	public String toString() {
		return "FreeTimeForResponse [date=" + date + ", roomName=" + roomName + ", beginTime=" + beginTime
				+ ", endTime=" + endTime + ", time=" + time + "]";
	}

}
