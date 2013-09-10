package com.mapunit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;


public class MapSettings implements Constants {
	
	private SharedPreferences preferences;
	
	public MapSettings(Activity activity){
		preferences = activity.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	
	public int getMapTypeFromSharedPreferences(){
		
		int returnValue = 0;
		String mapType = preferences.getString(MAP_TYPE_KEY, DEFAULT_MAP_TYPE);

		if (mapType.equalsIgnoreCase("Hybrid")){//TODO push up strings
			returnValue = GoogleMap.MAP_TYPE_HYBRID;
		}
		if (mapType.equalsIgnoreCase("Normal")){
			returnValue = GoogleMap.MAP_TYPE_NORMAL;
		}
		if (mapType.equalsIgnoreCase("Terrain")){
			returnValue = GoogleMap.MAP_TYPE_TERRAIN;
		}
		if (mapType.equalsIgnoreCase("Satellite")){
			returnValue = GoogleMap.MAP_TYPE_SATELLITE;
		}
		return returnValue;
	}
	
	
	/**
	 * CameraPosition may be a better solution than setting individual items with CameraUpdate
	 */
	public CameraUpdate configureMapView(){

		float tilt = preferences.getFloat(TILT_KEY, 0f);// tilt 0 to 90
		float zoom = preferences.getFloat(ZOOM_KEY, 17f);// zoom 0 to 20?

		// uses method chaining
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(CATALYST) // sets center of map
		.zoom(zoom) // sets zoom level - It is a float!!
		.bearing(NORTH) // sets the orientation of the camera
		.tilt(tilt) // sets tilt of the camera
		.build(); // builds the CameraPosition
		CameraUpdate wholeThing = CameraUpdateFactory.newCameraPosition(cameraPosition);

		//call map.animateCamera(wholeThing) to implement immediately
		//call map.animateCamera(wholeThing, 9000, null);//	takes 9 seconds or so to finish
		
		return wholeThing;
	}
	
	public boolean centerOnCatalyst(GoogleMap map){
//		CameraUpdate newCenter = CameraUpdateFactory.newLatLng(CATALYST);
//		map.moveCamera(newCenter);
		
		
CameraUpdate newZoom = CameraUpdateFactory.zoomTo(10.0f);//TODO why does this work here and not in resetZoom?????
		//TODO might need a callback before running second update.
		map.animateCamera(newZoom);
		
		return true;
	}
	
	public boolean resetZoom(GoogleMap map){
		
		Log.w("xxx" , "xxx zoom running? ");
		
		CameraUpdate newZoom = CameraUpdateFactory.zoomTo(10.0f);
		
		map.animateCamera(newZoom);
		return true;
	}
	
}
