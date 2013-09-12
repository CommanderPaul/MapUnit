package com.birdapp.database;

import java.util.Date;

/**
 * Record Object - may be normalized later
 * @author Paul Wroe
 *
 */
public class ObservationRecord {
	
	
	//	Fields
	private Long id;
	private Long date;
	private String name;
	private String activity;
	private Double latitude;
	private Double longitude;
	private String notes;
	
	
	// Constructors
	public ObservationRecord(){
		
	}
	public ObservationRecord(String name){
		this.name = name;
	}
	public ObservationRecord(Date date, String name, String activity, Double latitude, Double longitude, String notes){
			
	}
	public ObservationRecord(long id, Long date, String name, String activity, Double latitude, Double longitude, String notes){
		this.id = id;
		this.date = date;
		this.name = name;
		this.activity = activity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.notes = notes;
	}
	public ObservationRecord(String name, String activity, Double latitude, Double longitude, String notes){
		this.name = name;
		this.activity = activity;
		this.latitude = latitude;
		this.longitude = longitude;
		this.notes = notes;
	}
	public ObservationRecord(long id, String name, String activity, Double latitude, Double longitude, String notes){
		
	}
	
	// getters and setters
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getActivity() {
		return activity;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
