package com.mapunit;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

public class MapSettingsActivity extends Activity {

	private static String MAP_PREFERENCES = "Map Preferences";
	private static String TILT_KEY = "tilt";
	private static String ZOOM_KEY = "zoom";
	
	
	private SharedPreferences preferences;
	private NumberPicker tiltNumberPicker;
	private NumberPicker zoomNumberPicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_settings);
		
		preferences = this.getSharedPreferences(MAP_PREFERENCES, Context.MODE_PRIVATE);
		
		// tilt number picker configuration
		tiltNumberPicker = (NumberPicker)findViewById(R.id.tilt_setting);
		tiltNumberPicker.setMaxValue(90);
		tiltNumberPicker.setMinValue(0);
		tiltNumberPicker.setValue((int) preferences.getFloat(TILT_KEY, 0f));
		// zoom number picker configuration
		zoomNumberPicker = (NumberPicker)findViewById(R.id.zoom_setting);
		zoomNumberPicker.setMaxValue(20);
		zoomNumberPicker.setMinValue(0);
		zoomNumberPicker.setValue((int) preferences.getFloat(ZOOM_KEY, 0f));
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map_settings, menu);
		return true;
	}
	
	
	public void saveChanges(View view){
		setMapTilt();
		setMapZoom();
		
	}
	
	
	/**
	 * Set Map Tilt
	 * This should read input from Map Setting layout
	 * returns boolean - true = success
	 * @param view
	 */
	public boolean setMapTilt(){
		float tilt = tiltNumberPicker.getValue();
		
		// can be from 0f to 90f
		if (tilt > 90){tilt = 90f;}
		if (tilt < 0){tilt = 0f;}
		
		return writeFloatToSharedPreferences(TILT_KEY, tilt);
	}
	
	public boolean setMapZoom(){
		float zoom = zoomNumberPicker.getValue();
		
		// can be from 0f to 20f
		if (zoom > 20){zoom=20f;}
		if (zoom < 0) {zoom=0f;}
		
		return writeFloatToSharedPreferences(ZOOM_KEY, zoom);
	}
	
	public void resetTilt(View view){
		tiltNumberPicker.setValue(0);
	}
	public void resetZoom(View view){
		zoomNumberPicker.setValue(17);//TODO move 17 up
	}
	
	// used by both zoom and tilt
	private boolean writeFloatToSharedPreferences(String key, float value){
		boolean result = preferences.edit().putFloat(key, value).commit();
		return result;
	}
	
	// should get map preferences elsewhere
	public float getTiltFromSharedPreferences(View view){
		return preferences.getFloat(TILT_KEY, 0f);
	}
	
	

}
