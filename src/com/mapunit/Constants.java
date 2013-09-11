package com.mapunit;

import com.google.android.gms.maps.model.LatLng;

/**
 * Constants for MapUnit
 * @author pwroe
 *
 */
public interface Constants {
	public static String test = "hey";
	
	// constants in Interface are implicitly public static final
	
	LatLng CATALYST = new LatLng(45.513, -122.834);
	
	String MAP_PREFERENCES = "Map Preferences";
	String TILT_KEY = "tilt";
	String ZOOM_KEY = "zoom";
	String MAP_TYPE_KEY = "map_type";
	String DEFAULT_MAP_TYPE = "Normal";
	
	float NORTH = 0;
	float STANDARD_ZOOM = 15f;
	
}
