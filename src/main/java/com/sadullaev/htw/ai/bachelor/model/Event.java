package com.sadullaev.htw.ai.bachelor.model;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events")
public class Event {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
	
	@Column(name = "date")
    private Date date;
	
	@Column(name = "begin")
    private Timestamp begin;
	
	@Column(name = "end")
    private Timestamp end;
	
	@Column(name = "lsf_nr")
    private String lsfNr;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "lsf_id")
    private double lsfId;
	
	@Column(name = "building")
    private String building;
	
	@Column(name = "room")
    private String room;
	
	@Column(name = "lecturer")
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

	public String getLsfNr() {
		return lsfNr;
	}
	
	public void setLsfNr(String lsfNr) {
		this.lsfNr = lsfNr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLsfId() {
		return lsfId;
	}

	public void setLsfId(int lsfId) {
		this.lsfId = lsfId;
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

	@Override
	public String toString() {
		return "Event [date=" + date + ", begin=" + begin + ", end=" + end + ", lsfNr=" + lsfNr + ", name=" + name
				+ ", lsfId=" + lsfId + ", building=" + building + ", room=" + room + ", lecturer=" + lecturer + "]";
	}
	
	
	
	
	
	
	
}

