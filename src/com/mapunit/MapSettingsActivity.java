package com.mapunit;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

public class MapSettingsActivity extends Activity implements Constants {
	
	private SharedPreferences preferences;
	private NumberPicker tiltNumberPicker;
	private NumberPicker zoomNumberPicker;
	private Spinner mapTypeSpinner;
	private List<String> mapTypes;
	
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
		// map type spinner configuration
		mapTypeSpinner = (Spinner)findViewById(R.id.map_type_spinner);
		
		mapTypes = new ArrayList<String>();//TODO should this be an enum?
		mapTypes.add("Hybrid");
		mapTypes.add("Normal");
		mapTypes.add("Terrain");
		mapTypes.add("Satellite");
		
		ArrayAdapter<String> mapTypeDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mapTypes);
		mapTypeDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mapTypeSpinner.setAdapter(mapTypeDataAdapter);
		
		mapTypeSpinner.setSelection(figureListIDFromSharedPreferences());
		
		
	}
	
	private int figureListIDFromSharedPreferences(){
		String currentSetting = preferences.getString(MAP_TYPE_KEY, DEFAULT_MAP_TYPE);
		int returnIndex = 0;// default is first item
		
		for (int x = 0; x < mapTypes.size(); x++){
			if (mapTypes.get(x).equalsIgnoreCase(currentSetting)){
				returnIndex =x;
			}
		}
		
		return returnIndex;
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
		setMapType();
		
		// close activity
		finish();
	}
	
	
	public boolean setMapType(){
		String selection = (String) mapTypeSpinner.getSelectedItem();  // returns string from list
		boolean result = writeStringToSharedPreferences(MAP_TYPE_KEY, selection);
		return result;
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
	
	private boolean writeStringToSharedPreferences(String key, String value){
		boolean result = preferences.edit().putString(key, value).commit();
		return result;
	}
	
	
	
	// should get map preferences elsewhere
	public float getTiltFromSharedPreferences(View view){
		return preferences.getFloat(TILT_KEY, 0f);
	}
	
	

}
