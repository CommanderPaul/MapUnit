package com.mapunit;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MakeMarker {
	
	static final LatLng CATALYST = new LatLng(45.513, -122.834);
	
	public void addMarker(GoogleMap map){
		//TODO method not called
		
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
				
				Marker markMark = map.addMarker(firstMarker);// returns marker that was added to the map
		
		
		
	}
	
}
