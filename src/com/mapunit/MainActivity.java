package com.mapunit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

// add google play services library in properites


public class MainActivity extends Activity {
	
	static final LatLng CATALYST = new LatLng(45.513, -122.834);
	static final int STREET_ZOOM = 17;
	static final float NORTH = 0;
	
	private FakeMarkerOptionsGenerator fakker;
	private List<Circle> circles = new ArrayList<Circle>();
	private Marker centerOfMassMarker;
	private List<Marker> placedMarkers = new ArrayList<Marker>();
	
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
		
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap(); //initializes map
		
		this.fakker = new FakeMarkerOptionsGenerator();	//generates fake marker options

		map.animateCamera(new MapSettings().configureMapView());// configures map view
		
		// customize info window	//remember, only one info window can be shown at a time, either reuse or recreate
		// custom info window is based on marker, that is the key!
		// how do we know which dataset is associated with the marker???
		// set marker snippet to record id??  that might work

		customizeInfoWindowAdapter();// customized marker listener
		
		
		for (MarkerOptions marker : fakker.getMarkerList()) {
			Marker placedMarker = map.addMarker(marker);
			placedMarkers.add(placedMarker);
		}

		
		
		// Location.distanceBetween(startLatitude, startLongitude,
		// endLatitude, endLongitude, results);
		
	}
	
	/**
	 * Add concentric circles to data points in list of MarkerOptions
	 * running a second time clears circles from map and 
	 * then clears circles list
	 */
	public void circlesForPoints() {

		if (circles != null && circles.size() > 0) {
			// clear circles
			for (Circle circle : circles) {
				circle.remove(); // remove from map
			}
			circles.clear();
		} else {
			// add circles
			for (MarkerOptions marker : fakker.getMarkerList()) {

				Circle circle = map.addCircle(new CircleOptions()
						.center(marker.getPosition()).radius(100)// in meters
						.strokeColor(Color.RED));
				circles.add(circle);
			}
		}
	}

	
	public void showClosestPoint(){
		
		PointCalculations pc = new PointCalculations();
		
		// need to make sure I have center of mass
		LatLng centerPoint = pc.figureCenterOfMass(fakker.getMarkerList());
		pc.findClosestToCenter(centerPoint, placedMarkers);
		
	}
	
	/**
	 * 
	 * 
	 * activated by menu selection
	 * selecting when already on the map
	 * should hide the marker
	 */
	public void showCenterOfMass() {

		if (centerOfMassMarker != null ) {
			centerOfMassMarker.remove();
			centerOfMassMarker = null;
		} else {
			PointCalculations pc = new PointCalculations();//TODO push this up
			LatLng centerOfMass = pc.figureCenterOfMass(fakker.getMarkerList());

			// TODO find/make a good marker for center of mass
			// iconToUse =
			// BitmapDescriptorFactory.fromResource(android.R.drawable.);

			MarkerOptions centerMarker = new MarkerOptions()
					.position(centerOfMass)					
					.visible(true)
					.draggable(true)
					.title("Where are you?")
					.snippet("what is a snippit?") //text under title
			// .icon(iconToUse)

			;
			centerOfMassMarker = map.addMarker(centerMarker);												
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


	/**
	 * Capture menu selections here
	 * can switch on a string in java 7 BUT ADT only supports 5 or 6!!!
	 * Currently reacting to string title of name, which is bad for localization
	 * appears that the other option is by item number, which is determined
	 * by order.  Menu not settled yet, so, TODO convert menu to use item number
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item){

		// Under Development ----------------------------------------
		if (item.getTitle().toString().equalsIgnoreCase("under_development")){
			
			
			showClosestPoint();
			
		}
		
		// Add Features ---------------------------------------------
		if (item.getTitle().toString().equalsIgnoreCase("Center of Mass")){
			showCenterOfMass();
		}
		if (item.getTitle().toString().equalsIgnoreCase("Show Circles")){
			circlesForPoints();
		}
		
		
		// Map type-------------------------------------------------
		if (item.getTitle().toString().equalsIgnoreCase("Hybrid")){
			map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		}
		
		if (item.getTitle().toString().equalsIgnoreCase("Normal")){
			map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		}
		if (item.getTitle().toString().equalsIgnoreCase("Terrain")){
			map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
		}
		if (item.getTitle().toString().equalsIgnoreCase("Satellite")){
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
		}
		
		// Show location button --------------------------------------
		if (item.getTitle().toString().equalsIgnoreCase("My Location Button")) {

			if (map.isMyLocationEnabled()) {
				map.setMyLocationEnabled(false);
				item.setCheckable(false);
				item.setChecked(false);
			} else {
				map.setMyLocationEnabled(true);
				item.setCheckable(true);
				item.setChecked(true);
			}
		}
		
		
		
		return true;
	}
	
	
	
	private void customizeInfoWindowAdapter() {

		// there is only one info window per map
		// need to modify for each marker
		// listens for marker
		
		map.setInfoWindowAdapter(new InfoWindowAdapter() {

			@Override
			public View getInfoContents(Marker marker) {
				double lat = marker.getPosition().latitude;
				double lon = marker.getPosition().longitude;
				
				// can return custom view
				View view = getLayoutInflater().inflate(R.layout.info_window,
						null);
				TextView latitude = ((TextView) view
						.findViewById(R.id.window_lat));// reference off of main
														// view
				//latitude.setText(marker.getTitle() + "  " + "Latitude is " + lat);

				TextView longitude = ((TextView) view
						.findViewById(R.id.window_lon));
				//longitude.setText("Longitude is " + lon);
				
				
				ImageView imageView = ((ImageView)view.findViewById(R.id.first_bird));
				
				
				
				File imgFile = new  File("/storage/sdcard0/Pictures/BirdAppPictures/"+marker.getTitle()+".jpg");
				if (imgFile.exists()){

				Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

				imageView.setImageBitmap(bitmap);
				}
				// if you return null, it uses the default view, but
				// you can still modify the marker
				return view;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				
				return null;
			}

		});

	}
}
