package com.mapunit;

import java.util.List;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;



public class PointCalculations {
	/**
	 * center of mass of inputed points in the form of a list of markers
	 * returns a LatLng
	 * will probably fail at prime meridian and equator
	 */
	public LatLng figureCenterOfMass(List<MarkerOptions> listOfMarkers){
		
		// set up running totals
		double runningLat = 0;
		double runningLon = 0;
		
		for (MarkerOptions marker : listOfMarkers){
			
			runningLat += marker.getPosition().latitude;
			runningLon += marker.getPosition().longitude;
			
		}
		
		double finalLat = runningLat/listOfMarkers.size();
		double finalLon = runningLon/listOfMarkers.size();
		
		LatLng finalPosition = new LatLng(finalLat, finalLon);
		
		return finalPosition;
	}
	
	//TODO not finished
	public void figureDistanceInMeters(List<Marker> listOfMarkers){
		
		int x = listOfMarkers.size();
		
		// results is a float array that holds the results of the calculation
		//Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);
		Location locationA = new Location("A");
		locationA.setLatitude(listOfMarkers.get(0).getPosition().latitude);
		locationA.setLongitude(listOfMarkers.get(0).getPosition().longitude);
		
		//locationA.distanceTo(locationB);
		
	}
	
	public void calculateArea(){
		
		
		
	}
	
}
