package com.mapunit;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapSettings {

	static final LatLng CATALYST = new LatLng(45.513, -122.834);
	static final int STREET_ZOOM = 17;
	
	static final float NORTH = 0;
	private static final float LOW_TILT = 40F;
	private static final float TOP_TILT = 0F;
	
	/**
	 * 
	 * CameraPosition may be a better solution than setting individual items with CameraUpdate
	 */
	public CameraUpdate configureMapView(){
		
		// uses method chaining
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(CATALYST) // sets center of map
		.zoom(STREET_ZOOM) // sets zoom level - It is a float!!
		.bearing(NORTH) // sets the orientation of the camera
		.tilt(LOW_TILT) // sets tilt of the camera
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
		CameraUpdate newZoom = CameraUpdateFactory.zoomTo(STREET_ZOOM);
		// call map.animateCamera(newZoom) to use
		return newZoom;
	}
	
	
}
