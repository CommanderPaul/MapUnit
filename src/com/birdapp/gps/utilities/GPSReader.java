package com.birdapp.gps.utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.widget.Toast;

public class GPSReader implements LocationListener{

	private LocationManager locationManager;
	private String provider;
	private Context context;
	private Location location;

	
	// Constructor
	public GPSReader(Context context){
		
		this.context = context;
		
		locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		
		checkToSeeIfGPSIsOn();// needs to refresh after turning on gps
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);// set the locationManager to use the gps
		provider = locationManager.getBestProvider(criteria, false);
		
		// returns null if provider is disabled.  Appears to return null if using emulator
		location = locationManager.getLastKnownLocation(provider);
		
		// have to request location updates or it won't work - won't take updates from ddms
		locationManager.requestLocationUpdates(provider, 20000, 1, this);
		
	}
	
	public void updateGps(){
		
		locationManager.requestLocationUpdates(provider, 400, 1, this);
		
	}
	
	public LocationManager getLocationManager(){
		return locationManager;
	}
	
	public Location getLocation() {
		// location can be null if using an emulator
		// or if gps is turned off or not available.
		
		// attempt to update location
		// returns null if provider is disabled.  Appears to return null if using emulator
		location = locationManager.getLastKnownLocation(provider);
		
		return location;
	}


	public void setLocation(Location location) {
		this.location = location;
	}


	@Override
	public void onLocationChanged(Location location) {
		// this fires all the time

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		//Toast.makeText(context,  "Enabled new provider " + provider,  Toast.LENGTH_SHORT).show();
		
		// attempt to update location
		// returns null if provider is disabled.  Appears to return null if using emulator
		location = locationManager.getLastKnownLocation(provider);
		
		//TODO need to make this an event that can be listened in order to update text fields.
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		//Toast.makeText(context,  "Disabled provider " + provider,  Toast.LENGTH_SHORT).show();
		
	}
	
	
	
	/**
	 * Checks to see if gps is on.  Opens GPS enable activity if not enabled.
	 * Returns boolean as to whether it is enabled or not.
	 * @return
	 */
	public boolean checkToSeeIfGPSIsOn(){
		
		LocationManager service = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

		if (!enabled){
			
			AlertDialog ad = new AlertDialog.Builder(context).create();
			
			ad.setTitle("GPS Inactive!");
			ad.setMessage("Do you want to activate the GPS?");
			
			// Setting Icon to Dialog
	        ad.setIcon(android.R.drawable.stat_sys_warning);
			
			// negative button shows up first
			// set no button
			ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Don't Activate GPS", 
					
				new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						
						Toast.makeText(context, "GPS will not be used.", Toast.LENGTH_SHORT).show();
						dialog.cancel();
					}}
				);
			
			// set yes button
			ad.setButton(DialogInterface.BUTTON_POSITIVE, "Activate GPS", 
					
				new DialogInterface.OnClickListener() {
			
					@Override
		            public void onClick(DialogInterface dialog, int which) {
						
						Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
						context.startActivity(intent);
						
		            }}
				);
			
			ad.show();
			
		}
		
		return enabled;
	}

}
