package com.mapunit;

import java.util.List;

import android.location.Location;
import android.util.Log;

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

		double runningLat = 0d;
		double runningLon = 0d;
		
		for (MarkerOptions marker : listOfMarkers){
			runningLat += marker.getPosition().latitude;
			runningLon += marker.getPosition().longitude;
		}
		
		double finalLat = runningLat/listOfMarkers.size();
		double finalLon = runningLon/listOfMarkers.size();
		
		LatLng finalPosition = new LatLng(finalLat, finalLon);
		
		return finalPosition;
	}
	
	public Marker findClosestToCenter(LatLng centerPoint, List<Marker> listOfMarkers){
		float closestDistance = -1;
		Marker closestMarker = null;
		for (Marker marker : listOfMarkers){
			Log.d("xxxxx"," " + figureDistanceInMeters(centerPoint, marker));
			
			
			if (closestMarker == null){// first marker
				closestMarker = marker;
				
			}else if (figureDistanceInMeters(centerPoint, marker) < closestDistance){
				closestMarker = marker;
				closestDistance = figureDistanceInMeters(centerPoint, marker);
			}
			
			
		}
		
		return closestMarker;
	}
	
	

	public float figureDistanceInMeters(LatLng centerMarker, Marker unknownMarker){
		
		//Location.distanceBetween(startLatitude, startLongitude, endLatitude, endLongitude, results);

		// set center location
		Location centerLocation = new Location("Center");
		centerLocation.setLatitude(centerMarker.latitude);
		centerLocation.setLongitude(centerMarker.longitude);
		
		// set unknown marker
		Location unknownLocation = new Location("Unknown");
		unknownLocation.setLatitude(unknownMarker.getPosition().latitude);
		unknownLocation.setLongitude(unknownMarker.getPosition().longitude);
		
		// figure distance
		float distance = centerLocation.distanceTo(unknownLocation);// in meters
		
		return distance;
	}
	
	public void calculateArea(){
		//TODO calculating the area poses additional challenges
	}
	
}
