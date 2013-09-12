package com.input;

import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.birdapp.CameraActivity;
import com.birdapp.gps.utilities.GPSReader;
import com.mapunit.Constants;
import com.mapunit.MainActivity;
import com.mapunit.R;
//import com.mapunit.R.layout;
//import com.mapunit.R.menu;

public class BirdEntryActivity extends Activity implements Constants{

	// Fields
	
	private Double latValue;
	private TextView latField;
	
	private Double lonValue;
	private TextView lonField;
	
	private GPSReader gpsReader;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bird_entry);
		
		gpsReader = new GPSReader(this);
		
		// get current time
		Date d = new Date();
		CharSequence s = DateFormat.format("MM-dd-yyyy", d.getTime());
		
		TextView clockView = (TextView) findViewById(R.id.observation_time);
		clockView.setText(s);
		
		
		// input validation for bird name
		EditText birdName = (EditText)findViewById(R.id.editTextBirdName);
		setListenerToEditText(birdName);
		// input validation for bird activity
		EditText birdActivity = (EditText)findViewById(R.id.editTextBirdActivity);
		setListenerToEditText(birdActivity);
		
		// set initial gps coordiates
		latField = (TextView) findViewById(R.id.TextViewLatitude);// this may need to go in onCreate()
		lonField = (TextView) findViewById(R.id.TextViewLongitude);
		populateLatAndLong();
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bird_entry, menu);
		return true;
	}
	
	
	@Override
	protected void onResume(){
		super.onResume();
		populateLatAndLong();
	}
	
	
	/**
	 * @param view
	 */
	public void alert(View view){//TODO remove this, function has been moved to GPSReader
		
		AlertDialog ad = new AlertDialog.Builder(this).create();

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
					
					Toast.makeText(getApplicationContext(), "GPS will not be used.", Toast.LENGTH_SHORT).show();
					dialog.cancel();
				}}
			);
		
		// set yes button
		ad.setButton(DialogInterface.BUTTON_POSITIVE, "Activate GPS", 
				
			new DialogInterface.OnClickListener() {
		
				@Override
	            public void onClick(DialogInterface dialog, int which) {
					
					gpsReader.checkToSeeIfGPSIsOn();
					
	            }}
			);
		
		ad.show();
	}
	
	/**
	 * Uses GPSReader class to get lat and lon from gps
	 * and auto-populate the appropriate text fields
	 */
	private void populateLatAndLong(){
		
		//TODO make a listener that updates every time gps onStatusChanged 
		
		Location location = gpsReader.getLocation();
		
		if (location != null) {
			gpsReader.updateGps();
			// this shouldn't run unless there is a good location.
			latField.setText(String.valueOf(location.getLatitude()));
			lonField.setText(String.valueOf(location.getLongitude()));
		}
		
	}
	
	
	/**
	 * Set a focus change listener to an EditText
	 * @param editText
	 */
	private void setListenerToEditText(final EditText editText){
		//TODO can set up a hashmap or something to further reduce code duplication
		editText.setOnFocusChangeListener(new OnFocusChangeListener(){
			
			public void onFocusChange(View v, boolean hasFocus){
				
				final String textString = editText.getText().toString();
				
				if(!hasFocus){
					if(textString.length() < 1){

						if( editText.getResources().getResourceEntryName(editText.getId()).equals("editTextBirdName")  ){
							editText.setError("Bird Name is Required!");							
						}
//						if( editText.getResources().getResourceEntryName(editText.getId()).equals("editTextBirdActivity")){
//							editText.setError(BIRD_ACTIVITY_REQUIRED);
//						}
						
						editText.getId();
						getResources().getInteger(R.id.editTextBirdActivity);
						
						if( editText.getResources().getResourceEntryName(editText.getId())
								.equals( getResources().getString(R.id.editTextBirdActivity) )){
							editText.setError(BIRD_ACTIVITY_REQUIRED);
						}
					}
					
					// use matches to detect non characters
					if (!textString.matches(REGULAR_EXPRESSION_ONLY_LETTERS)){
						editText.setError(NON_LETTER_DETECTED);
					}
				}
			}
		});
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		
		// Show Map ---------------------------------------
				if (item.getTitle().toString().equalsIgnoreCase( getString(R.string.show_map))
						&& (!MainActivity.isRunning()) ){// will not fire if MainActivity is still active
					Intent intent = new Intent(this, MainActivity.class);
					startActivity(intent);
				}
		// Show Camera ------------------------------------
				if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.show_camera))){
					Intent intent = new Intent(this, CameraActivity.class);
					startActivity(intent);
				}
		
		return true;
		
	}
}
