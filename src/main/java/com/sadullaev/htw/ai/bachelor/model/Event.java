package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {
	

    private int id;
	
    private Date date;
	
    private Timestamp begin;
	
    private Timestamp end;
	
    private String lsf_nr;
	
    private String name;
	
    private double lsf_id;
	
    private String building;
	
    private String room;
	
    private String lecturer;
	
	public Event() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
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

	public String getLsf_nr() {
		return lsf_nr;
	}

	public void setLsf_nr(String lsf_nr) {
		this.lsf_nr = lsf_nr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLsf_id() {
		return lsf_id;
	}

	public void setLsf_id(double lsf_id) {
		this.lsf_id = lsf_id;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getLecturer() {
		return lecturer;
	}

	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	
	
	
	
	
	
	
	
}

