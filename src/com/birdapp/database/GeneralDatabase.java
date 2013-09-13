package com.birdapp.database;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite class for BirdWatcher
 * @author Paul Wroe
 *
 */
public class GeneralDatabase extends SQLiteOpenHelper{

	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_NAME = "BirdWatcherDatabase";
	
	// table names
	private static final String TABLE_BIRD_OBSERVATIONS = "ObservationRecords";
	
	// table column names
	private static final String COLUMN_NAME_ID = "id";
	private static final String COLUMN_NAME_DATE = "date";
	private static final String COLUMN_NAME_NAME = "bird_name";
	private static final String COLUMN_NAME_ACTIVITY = "bird_activity";
	private static final String COLUMN_NAME_LATITUDE = "bird_latitude";
	private static final String COLUMN_NAME_LONGTITUDE = "bird_longitude";
	private static final String COLUMN_NAME_NOTES = "bird_notes";
	private static final String[] COLUMN_NAMES_ARRAY = new String[] {
			COLUMN_NAME_ID, COLUMN_NAME_DATE, COLUMN_NAME_NAME,
			COLUMN_NAME_ACTIVITY, COLUMN_NAME_LATITUDE, COLUMN_NAME_LONGTITUDE,
			COLUMN_NAME_NOTES };

	//	table column numbers
	private static final int ID_COLUMN = 0;
	private static final int DATE_COLUMN = 1;
	private static final int NAME_COLUMN = 2;
	private static final int ACTIVITY_COLUMN = 3;
	private static final int LATITUDE_COLUMN = 4;
	private static final int LONGITUDE_COLUMN = 5;
	private static final int NOTES_COLUMN = 6;
	
	//	queries
	private static final String SELECT_ALL_RECORDS_QUERY =	//	can use sqlite query with null parameter too
			"SELECT "
			+ COLUMN_NAME_ID
			+ ", " + COLUMN_NAME_DATE
			+ ", " + COLUMN_NAME_NAME
			+ ", " + COLUMN_NAME_ACTIVITY
			+ ", " + COLUMN_NAME_LATITUDE
			+ ", " + COLUMN_NAME_LONGTITUDE
			+ ", " + COLUMN_NAME_NOTES
			+ " FROM "
			+  TABLE_BIRD_OBSERVATIONS;
	
	//	SQLiteOpenHelper requires an explicit constructor
	public GeneralDatabase(Context context){
		super (context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	/**
	 * onCreate runs only when the database does not already exist.
	 * This method creates the TABLE_BIRD_OBSERVATIONS table and then calls 
	 * addInitialBirds() to add some default birds to the 
	 * database.
	 */
	@Override
	public void onCreate(SQLiteDatabase db){

		String CREATE_CONTACTS_TABLE = "CREATE TABLE "
				+ TABLE_BIRD_OBSERVATIONS
				+ "("
				+ COLUMN_NAME_ID + " INTEGER PRIMARY KEY,"		//	Column 0
				+ COLUMN_NAME_DATE + " NUMERIC, "				//	Column 1
				+ COLUMN_NAME_NAME + " TEXT, "					//	Column 2
				+ COLUMN_NAME_ACTIVITY + " TEXT, "				//	Column 3
				+ COLUMN_NAME_LATITUDE + " NUMERIC, "			//	Column 4
				+ COLUMN_NAME_LONGTITUDE + " NUMERIC, "			//	Column 5
				+ COLUMN_NAME_NOTES + " TEXT "					//	Column 6
				+ ")"
				;
		db.execSQL(CREATE_CONTACTS_TABLE);
		addInitialBirds(db);
	}
	
	/**
	 * Inserts Bird Observations into database and does not close database.
	 * Closing database inside of onCreate causes crash.
	 * @param db
	 * @param fortune
	 */
	private void addInitialBirds(SQLiteDatabase db){
		
		// populate new database
		List <ObservationRecord> birdRecordList = new ArrayList<ObservationRecord>();
		birdRecordList.add(new ObservationRecord("Chicken"));
		birdRecordList.add(new ObservationRecord("Big Bird"));
		birdRecordList.add(new ObservationRecord("Pidgeon"));
		birdRecordList.add(new ObservationRecord("Robin"));
		
		for(ObservationRecord birdRecord : birdRecordList){
			
			ContentValues values = new ContentValues();
			values.put(COLUMN_NAME_DATE,  birdRecord.getDate() );
			values.put(COLUMN_NAME_NAME,  birdRecord.getName() );
			values.put(COLUMN_NAME_ACTIVITY,  birdRecord.getActivity());
			values.put(COLUMN_NAME_LATITUDE,  birdRecord.getLatitude());
			values.put(COLUMN_NAME_LONGTITUDE,  birdRecord.getLongitude());
			values.put(COLUMN_NAME_NOTES,  birdRecord.getNotes());

			// inserting row
			db.insert(TABLE_BIRD_OBSERVATIONS, null, values);
		}
	}
	
	// upgrading database - required for super
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_BIRD_OBSERVATIONS);
		
		// create table again
		onCreate(db);
	}
	
	
	//	CRUD operations

	/**
	 * Add ObservationRecord to the database
	 * This method instantiates a database and later closes the database.
	 * @param birdRecord
	 */
	public boolean addObservationRecord(ObservationRecord birdRecord){
		
		boolean returnValue = false;
		long returnID = -1;
		
		SQLiteDatabase db = this.getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(COLUMN_NAME_DATE,  birdRecord.getDate() );
		values.put(COLUMN_NAME_NAME,  birdRecord.getName() );
		values.put(COLUMN_NAME_ACTIVITY,  birdRecord.getActivity());
		values.put(COLUMN_NAME_LATITUDE,  birdRecord.getLatitude());
		values.put(COLUMN_NAME_LONGTITUDE,  birdRecord.getLongitude());
		values.put(COLUMN_NAME_NOTES,  birdRecord.getNotes());
		
		// inserting row
		returnID = db.insert(TABLE_BIRD_OBSERVATIONS, null, values); // returns -1 on error, otherwise, id of column is returned
		
		//TODO put close in a finally of a try-catch
		db.close();	//	 always have to close connection!!!
		
		if (returnID > 0){
			returnValue = true;
		}
		
		return returnValue;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public ObservationRecord getObservationRecordById (int id){
		SQLiteDatabase db = this.getReadableDatabase();
		
		//  query (String table, String[] columns, 
		//	String selection, String[] selectionArgs, 
		//	String groupBy, String having, String orderBy) 
		
		Cursor cursor = db.query(
			TABLE_BIRD_OBSERVATIONS						//	Table Name
			, COLUMN_NAMES_ARRAY						//	Names of Columns
			, COLUMN_NAME_ID + " = ?" 					//	passing null returns everything otherwise a string of 'where'
			, new String[] { String.valueOf(id) }		//	string array for parameters
			, null, null, null);

		if (cursor != null){
			cursor.moveToFirst();
		}
		
		return returnObservationRecordFromCursor(cursor);	//	seperate method to populate object from cursor
	}
	
	/**
	 * Gets a list of all of the fortunes in the database
	 * Returns List<Fortune>
	 * @return
	 */
	public List<ObservationRecord> getAllObservationRecords(){
		List<ObservationRecord> recordList = new ArrayList<ObservationRecord>();
		
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(SELECT_ALL_RECORDS_QUERY, null);
		
		if (cursor.moveToFirst()){	//	move cursor to the first row if it exists
			
			do{
				recordList.add(	//	add a populated ObservationRecord to list
						returnObservationRecordFromCursor(cursor)
						);
			} while (cursor.moveToNext());	// there is a next row to move the cursor to
		}
		
		return recordList;
	}
	
	
	// to be implemented
	
	// update
	public boolean updateBirdObservation(ObservationRecord observationRecord){
		
		return true;
	}

	
	// delete
	
	
	/**
	 * Get a count of the number of records in the BirdObservation table
	 * @return
	 */
	public int getBirdObservationCount(){
		
		SQLiteDatabase db = this.getReadableDatabase();
		
		Cursor cursor = db.rawQuery(SELECT_ALL_RECORDS_QUERY, null);
		int returnValue = cursor.getCount();
		
		cursor.close();
		
		return returnValue;
	}
	
	/**
	 * Takes a cursor and returns a populated ObservationRecord.
	 * @param cursor
	 * @return
	 */
	private ObservationRecord returnObservationRecordFromCursor(Cursor cursor){
		ObservationRecord observationRecord = new ObservationRecord ( 
				Integer.parseInt(cursor.getString(ID_COLUMN))
				,cursor.getLong(DATE_COLUMN)
				, 
				cursor.getString(NAME_COLUMN)
				, cursor.getString(ACTIVITY_COLUMN)
				, cursor.getDouble(LATITUDE_COLUMN)
				, cursor.getDouble(LONGITUDE_COLUMN)
				, cursor.getString(NOTES_COLUMN)
				);
		
		return observationRecord;
	}
}
