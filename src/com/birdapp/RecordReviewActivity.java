package com.birdapp;

import com.birdapp.database.BirdRecordsAdapter;
import com.mapunit.R;
import com.mapunit.R.layout;
import com.mapunit.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

public class RecordReviewActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_record_review);
		

		// get a reference to the listview bird_list inside of activity_bird_records.xml
		ListView listView = (ListView) findViewById(R.id.bird_list);
		
		// instantiate the adapter
		BirdRecordsAdapter adapter = new BirdRecordsAdapter(this);
		
		
		// configure the listView to use the adapter
		// puts view items in listView
		listView.setAdapter(adapter);
		
		//	log event for LogCat as an information event
		Log.i("BirdRecords","onCreate fired");
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.record_review, menu);
		return true;
	}

}
