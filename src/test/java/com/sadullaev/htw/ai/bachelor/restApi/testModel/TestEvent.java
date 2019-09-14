package com.sadullaev.htw.ai.bachelor.restApi.testModel;

import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "events_test")
public class TestEvent {
	
	/*
	 * Instance variables 
	 */
	
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
	
	@Column(name = "is_actual")
    private int isActual;
	
	/**
	 * Constructor
	 */
	public TestEvent() {
		
	}
	
	/**
	 * Getter function for id 
	 * @return id as Integer
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Setter function for id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Getter function for Date
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
	 * Getter function for Begin timestamp of event
	 * @return
	 */
	public Timestamp getBegin() {
		return begin;
	}
	
	/**
	 * Setter function for Begin timestamp of event
	 * @param begin
	 */
	public void setBegin(Timestamp begin) {
		this.begin = begin;
	}
	
	/**
	 * Getter function for End timestamp of event
	 * @return timestamp
	 */
	public Timestamp getEnd() {
		return end;
	}
	
	/**
	 * Setter function for End timestamp of event
	 * @return timestamp
	 */
	public void setEnd(Timestamp end) {
		this.end = end;
	}
	
	/**
	 * Getter function for Lsf number
	 * @return lsf number as string
	 */
	public String getLsfNr() {
		return lsfNr;
	}
	
	/**
	 * Setter function of lsf number
	 * @param lsfNr
	 */
	public void setLsfNr(String lsfNr) {
		this.lsfNr = lsfNr;
	}
	
	/**
	 * Getter function of Name of event
	 * @return eventsname
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter function of eventsname
	 * @param name of event
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter function of lsf id
	 * @return lsf id as double
	 */
	public double getLsfId() {
		return lsfId;
	}
	
	/**
	 * Setter function of lsf id
	 * @param lsfId
	 */
	public void setLsfId(int lsfId) {
		this.lsfId = lsfId;
	}
	
	/**
	 * Getter function of Building
	 * @return building name as string
	 */
	public String getBuilding() {
		return building;
	}
	
	/**
	 * Setter function of Building
	 * @param building
	 */
	public void setBuilding(String building) {
		this.building = building;
	}
	
	/**
	 * Getter function of room
	 * @return room
	 */
	public String getRoom() {
		return room;
	}
	
	/**
	 * Setter function of room
	 * @param room
	 */
	public void setRoom(String room) {
		this.room = room;
	}
	
	/**
	 * Getter function of lecturer
	 * @return lecturer
	 */
	public String getLecturer() {
		return lecturer;
	}
	
	/**
	 * Setter function of lecturer
	 * @param lecturer
	 */
	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	/**
	 * Getter function of status of event
	 * @return status as integer
	 */
	public int getIsActual() {
		return isActual;
	}
	
	/**
	 * Setter function of status of event
	 * @param isActual
	 */
	public void setIsActual(int isActual) {
		this.isActual = isActual;
	}
	
	/**
	 * Ovverided function EQUALS for best performance
	 */
	@Override
    public boolean equals(Object obj) {
        if(obj==null) return false;
        if (!(obj instanceof TestEvent))
            return false;
        if (obj == this)
            return true;
        return this.getName().equals(((TestEvent)obj).getName()) &&
        		this.getBegin().getTime()==((TestEvent)obj).getBegin().getTime() &&
        		this.getEnd().getTime()==((TestEvent)obj).getEnd().getTime() &&
        		this.getBuilding().equals(((TestEvent)obj).getBuilding()) &&
        		this.getRoom().equals(((TestEvent)obj).getRoom()) &&
        		this.getLecturer().equals(((TestEvent)obj).getLecturer()) &&
        		this.getIsActual()==((TestEvent)obj).getIsActual();
    }

	/**
	 * Ovverided function TOSTRING for best performance
	 */
	@Override
	public String toString() {
		return "Event [id=" + id + ", date=" + date + ", begin=" + begin + ", end=" + end + ", lsfNr=" + lsfNr
				+ ", name=" + name + ", lsfId=" + lsfId + ", building=" + building + ", room=" + room + ", lecturer="
				+ lecturer + ", isActual=" + isActual + "]";
	}
	
}