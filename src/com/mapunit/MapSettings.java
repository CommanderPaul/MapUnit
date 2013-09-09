package com.mapunit;

import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapSettings implements Constants {

	static final LatLng CATALYST = new LatLng(45.513, -122.834);

	static final float NORTH = 0;

	//TODO these need to be combined with MapSettingsActivity
	public static String MAP_PREFERENCES = "Map Preferences";
	public static String TILT_KEY = "tilt";
	public static String ZOOM_KEY = "zoom";
	
	private SharedPreferences preferences;
	
	public MapSettings(Activity activity){
		preferences = activity.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE);
	}
	
	
	/**
	 * 
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

		// call map.animateCamera(wholeThing) to implement immediately
		//call map.animateCamera(wholeThing, 9000, null);//	takes 9 seconds or so to finish
		
		return wholeThing;
	}
	
	private CameraUpdate setMapCenter(){
		CameraUpdate newCenter = CameraUpdateFactory.newLatLng(CATALYST);
		// call map.moveCamera(newCenter) to use
		return newCenter;
	}
	
	private CameraUpdate setZoomLevel(){
		CameraUpdate newZoom = CameraUpdateFactory.zoomTo(17);
		// call map.animateCamera(newZoom) to use
		return newZoom;
	}
	
	
}
