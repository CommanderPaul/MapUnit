package com.birdapp.database;

import java.util.ArrayList;

import com.mapunit.R;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



/**
 * Adapter class for connecting listView to BirdRecords Activity
 * 
 * BaseAdapter is an abstract class for making adapters
 * use to make adapters for listView
 * may also be able to use ArrayAdapter, SimpleAdapter and CursorAdapter
 * 
 * @author pwroe
 *
 */
public class BirdRecordsAdapter extends BaseAdapter{

	// fields
	private ArrayList<ObservationRecord> listOfRecords;

	
	// Constructor
	public BirdRecordsAdapter(Context context){
		this.listOfRecords = new ArrayList<ObservationRecord>();

		// populate data for list here?
		GeneralDatabase db = new GeneralDatabase(context);// context has to be from an activity
		this.listOfRecords = (ArrayList<ObservationRecord>) db.getAllObservationRecords();
		
		//listOfRecords.add(new ObservationRecord("bob","bob", 123.3, 123.3, "notes"));
		
		
	}
	
	// runs when list adapter is clicked on
	@Override
	public boolean isEnabled(int position){
		
		//System.out.println("clicked  " + position);
		// crashes when returns true
		return false;
	}
	

	
	
	/**
	 * Returns the number of records in Array
	 * abstract method from base
	 */
	@Override
	public int getCount() {
		return listOfRecords.size();
	}

	
	/**
	 * returns ObservationRecord from listOfRecords
	 * at input position
	 * abstract method from base
	 */
	@Override
	public Object getItem(int position) {
		return listOfRecords.get(position);
	}


	/**
	 * returns ObservationRecord id based on 
	 * input position
	 * abstract method from base
	 */
	@Override
	public long getItemId(int position) {
		return ((ObservationRecord) getItem(position)).getId();
	}

	
	/**
	 * This method returns the View that is repeated inside of the ListView
	 * 
	 * abstract method from base
	 * 
	 * this is the method that returns the view used to display data in the listView
	 * getView() is the link between the data stored in the Adapter and how
	 * it is displayed in the ListaView
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		// convertView is a ListView
		
		// if convertView is null, then it needs to be inflated
		if (convertView == null){
			LayoutInflater inflater = LayoutInflater.from(parent.getContext());
			// inflate view that is inside of the listView
			convertView = inflater.inflate(R.layout.bird_object, parent, false);
		}
		
		
		
		//	this part seems more about getting each iteration of the list
		// ie, bird1, bird2.  this method runs for each item in the listOfRecords
		
		// get object from list of objects
		ObservationRecord observationRecord = listOfRecords.get(position);
		
		
		// populate and manipulate child views for current iteration of listView
		TextView birdNotesTextView = (TextView) convertView.findViewById(R.id.editTextBirdNotes);
		birdNotesTextView.setText(observationRecord.getNotes());
		
		TextView birdNameTextView = (TextView) convertView.findViewById(R.id.editTextBirdName);
		birdNameTextView.setText(observationRecord.getName());
		
		Button deleteBird = (Button) convertView.findViewById(R.id.buttonDeleteBird);
		//deleteBird.setText(" " + observationRecord.getId());
		deleteBird.setTag(observationRecord.getId());
		
		
		
		return convertView;
	}

}
