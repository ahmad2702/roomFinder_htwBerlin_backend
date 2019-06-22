package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Timestamp;

public class FreeTimeForResponse {
	
	private String roomName;
	private Timestamp beginTime;
	private Timestamp endTime;
	private long time;
	
	public FreeTimeForResponse(String roomName, Timestamp beginTime, Timestamp endTime, long time) {
		this.roomName = roomName;
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.time = time;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Timestamp getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Timestamp beginTime) {
		this.beginTime = beginTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "FreeTimeForResponse [roomName=" + roomName + ", beginTime=" + beginTime + ", endTime=" + endTime
				+ ", time=" + time + "]";
	}
	
	
	
	
}
