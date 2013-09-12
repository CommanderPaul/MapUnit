package com.input;

import java.util.Date;

import android.app.Activity;
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

import com.birdapp.CameraActivity;
import com.birdapp.RecordReviewActivity;
import com.birdapp.database.GeneralDatabase;
import com.birdapp.database.ObservationRecord;
import com.birdapp.gps.utilities.GPSReader;
import com.mapunit.Constants;
import com.mapunit.MainActivity;
import com.mapunit.R;



public class BirdEntryActivity extends Activity implements Constants{

	// Fields
	
	private Long dateValue;
	private TextView dateField;
	
	private String nameValue;// actual string
	private EditText nameField;// object holding the string
	
	private String activityValue;
	private EditText activityField;

	private String notesValue;
	private EditText notesField;
	
	private GeneralDatabase db;

	private Double latValue;
	private TextView latField;
	
	private Double lonValue;
	private TextView lonField;
	
	private GPSReader gpsReader;
	
	// default constructor
	public BirdEntryActivity(){
		this.db = new GeneralDatabase(this);
	}
	
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
		nameField = (EditText)findViewById(R.id.editTextBirdName);
		setListenerToEditText(nameField);
		// input validation for bird activity
		activityField = (EditText)findViewById(R.id.editTextBirdActivity);
		setListenerToEditText(activityField);
		
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
	
	public void submit(View view){
		
//		dateValue = Long.parseLong(dateField.getText().toString());//TODO need work, need to convert date to long
		nameValue = nameField.getText().toString();
		activityValue = activityField.getText().toString();
//		latValue = Double.parseDouble(latField.getText().toString());
//		lonValue = Double.parseDouble(lonField.getText().toString());
//		notesValue = notesField.getText().toString();
		
		
		
		ObservationRecord record = new ObservationRecord();
//		record.setDate(dateValue);
		record.setName(nameValue);
		record.setActivity(activityValue);
//		record.setLatitude(latValue);
//		record.setLongitude(lonValue);
//		record.setNotes(notesValue);
		
		Log.w("xxx", "xxx  " + nameValue + "   " + activityValue);
		
		
		db.addObservationRecord(record);
//		
//		
//		Context context = getApplicationContext();
//		CharSequence text = "Bird Report Saved!";
//		int duration = Toast.LENGTH_SHORT;
//
//		Toast toast = Toast.makeText(context, text, duration);
//		toast.show();

		// kill view and go to main menu
		//finish();
		
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
				
		// Show Records ------------------------------------
				if (item.getTitle().toString().equalsIgnoreCase(getString(R.string.show_records))){
					Intent intent = new Intent(this, RecordReviewActivity.class);
					startActivity(intent);
				}
		
		return true;
		
	}
}
