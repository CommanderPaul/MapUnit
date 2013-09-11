package com.mapunit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
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
	
	public boolean resetView(final GoogleMap map){
		// can't seem to execute two animateCamera's consecutively, need to use callback

		CameraUpdate newZoom = CameraUpdateFactory.zoomTo(STANDARD_ZOOM);// set zoom
		map.animateCamera(newZoom, 

				new CancelableCallback(){
					@Override
					public void onCancel() {
						// do nothing
					}

					@Override
					public void onFinish() {
						CameraUpdate newCenter = CameraUpdateFactory.newLatLng(CATALYST);
						map.moveCamera(newCenter);
					}}
				);
		return true;
	}
	

}
