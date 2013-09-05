package com.mapunit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FakeMarkerOptionsGenerator {
	/**
	 * Should generate and return a list of marker objects
	 * This should simulate database access
	 * Markers are called MarkerOptions
	 * 
	 * Data Fields
	 * birdName
	 * latitude
	 * longitude
	 * first picture if it exists
	 * icon tied to name
	 * 
	 */
	
	// Fields
	List<MarkerOptions> markerList;
	MarkerOptions markerOptions;
	
	// Constructors
	public FakeMarkerOptionsGenerator(){
		this.markerList = new ArrayList<MarkerOptions>();
		populateMarkerList();
	}
	
	// Methods
	
	
	private void populateMarkerList() {
	
		String title = null;
		
		for (int x = 0; x < 10; x++) {

			BitmapDescriptor iconToUse = null;
			
			File imgFile = new  File("/xxxxxstorage/sdcard0/Pictures/BirdAppPictures/crow.jpg");
			if (imgFile.exists()){

			Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
			
			iconToUse = BitmapDescriptorFactory.fromBitmap(bitmap);
					

			}else{
				// couldn't find the image, use default
				iconToUse = BitmapDescriptorFactory.fromResource(android.R.drawable.btn_star_big_on);
			}
			
						
					if (x % 4 == 1){
						title = "crow";
					}else if (x % 4 == 2){
						title = "pigeon";
					}else if (x % 4 == 3){
						title = "bluejay";
					}else{
						title = "robin";
					}
					
					
			
					markerOptions = new MarkerOptions().position(generatePoints())
							.visible(true) // is visible
							.draggable(true) // is draggable
							.title(title) // marker title
							.snippet("what is a snippit?") // snippit appears to be text
							// under title
							.icon(iconToUse)
			;

			markerList.add(markerOptions);

		}
	}
	
	
	
	
	// random points about Catalyst
	private LatLng generatePoints(){
		LatLng returnPoint = null;
		//new LatLng(45.513, -122.834);
		double baseLat = 45.513f;
		double baseLon = -122.834f;
		
		Random random = new Random();
		double newLat = ((random.nextFloat() - .5) * .001) + baseLat;
		double newLon = ((random.nextFloat() - .5) * .001) + baseLon;
		
		returnPoint = new LatLng(newLat, newLon);
		
		return returnPoint;
	}

	
	// Getters and Setters
	
	public List<MarkerOptions> getMarkerList() {
		return markerList;
	}
	
	public void setMarkerList(List<MarkerOptions> markerList) {
		this.markerList = markerList;
	}
	
	
}
