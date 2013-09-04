package com.mapunit;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// add google play services library in properites


public class MainActivity extends Activity {
	
	static final LatLng HAMBURG = new LatLng(55.558, 9.927);
	static final LatLng KIEL = new LatLng(53.551, 9.993);
	static final LatLng CATALYST = new LatLng(45.513, -122.834);
	static final int STREET_ZOOM = 17;
	static final float NORTH = 0;
	private static final float LOW_TILT = 40F;
	private static final float TOP_TILT = 0F;
	
	// GoogleMap is the main class of the Google Maps Android API and is the entry point
	// for all methods related to the map. You cannot instantiate a GoogleMap object 
	// directly, rather, you must obtain one from the getMap() method on a MapFragment 
	// or MapView that you have added to your application. 
	// A GoogleMap can only be read and modified from the main thread.
	// https://developers.google.com/maps/documentation/android/reference/com/google/android/gms/maps/GoogleMap
	private GoogleMap map;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		
		// add 'my location' button
		map.setMyLocationEnabled(true);

		// map type
		//map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		//map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		//map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		//map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		
		/**
		 * Set map properties individually
		 * 
		 * 
		// set map center
		CameraUpdate newCenter = CameraUpdateFactory.newLatLng(CATALYST);
		map.moveCamera(newCenter);
		
		// set zoom level
		CameraUpdate defaultZoom = CameraUpdateFactory.zoomTo(STREET_ZOOM);
		map.animateCamera(defaultZoom);
		**/
		
		// CameraPosition may be a better solution than setting individual items with CameraUpdate
		// uses method chaining
		CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(CATALYST) // sets center of map
		.zoom(STREET_ZOOM) // sets zoom level - It is a float!!
		.bearing(NORTH) // sets the orientation of the camera
		.tilt(LOW_TILT) // sets tilt of the camera
		.build(); // builds the CameraPosition
		CameraUpdate wholeThing = CameraUpdateFactory.newCameraPosition(cameraPosition);
		map.animateCamera(wholeThing);// use to implement immediatly
		
		//map.animateCamera(wholeThing, 9000, null);	//	takes 9 seconds or so to finish
		
		
		// marker stuff here
		BitmapDescriptor iconToUse = BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on);
		
		MarkerOptions firstMarker = new MarkerOptions()
		.position(CATALYST)	//	position of marker
		.visible(true) // is visible
		.draggable(true) // is draggable
		.title("Where are you?") // marker title
		.snippet("what is a snippit?") // snippit appears to be text under title
		.icon(iconToUse)
		//ic_coins_l
		;
		
		
		// customize info window
		map.setInfoWindowAdapter(new InfoWindowAdapter(){

			@Override
			public View getInfoContents(Marker marker) {
				double lat = marker.getPosition().latitude;
				double lon = marker.getPosition().longitude;
				marker.setSnippet("Latitude is " + lat + "  Longitude is" + lon);// doesn't recognize \n as a newline
				
				// can return custom view - don't need newline
				View view = getLayoutInflater().inflate(R.layout.info_window,null);
				TextView latitude = ((TextView)view.findViewById(R.id.window_lat));// reference off of main view
				latitude.setText("Latitude is " + lat);
				
				TextView longitude = ((TextView)view.findViewById(R.id.window_lon));
				longitude.setText("Longitude is " + lon);
				
				// if you return null, it uses the default view, but
				// you can still modify the marker
				return view;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				// TODO Auto-generated method stub
				return null;
			}
			
			
			
			
		});
		
		
		map.addMarker(firstMarker);
		
		
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
