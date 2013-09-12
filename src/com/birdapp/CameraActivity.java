package com.birdapp;

import com.birdapp.camera.AccessCamera;
import com.birdapp.camera.CameraFragment;
import com.birdapp.camera.CameraPreview;
import com.birdapp.camera.TakePicture;
import com.mapunit.R;
import com.mapunit.R.layout;
import com.mapunit.R.menu;

import android.hardware.Camera;
import android.os.Bundle;
import android.app.Activity;
import android.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.FrameLayout;

public class CameraActivity extends Activity {


	
	
	protected static final String TAG = "Log Tag xxx";
	/**
	 * Does not work in emulator
	 * Does work on phone
	 * 
	 */
	
	
	private Camera camera;
    private CameraPreview cameraPreview;

    
    public void takePicture(View view){
    	Log.w(TAG, "test xxxxxxxx");
    	
    	TakePicture pictureCallback = new TakePicture();
    	camera.takePicture(null, null, pictureCallback);// third parameter is a PictureCallback object
    }
    
    
    public void showFragment(View view){
    	
    	Log.w(TAG, "show fragment");
    	
    	
    	
    	// Create an instance of Camera
        camera = AccessCamera.getCameraInstance();
        
        // Create our Preview view and set it as the content of our activity.
        cameraPreview = new CameraPreview(this, camera);
        
        View preview = (View) findViewById(R.id.surfaceView1);
        
    	
    	FragmentManager fm = getFragmentManager();
    	CameraFragment cameraFragment = new CameraFragment();
    	
    	
    	cameraFragment.setRetainInstance(true);
    	
    	cameraFragment.show(fm, "hey_there");// not sure what tag string is for
    	
    	
    	// Create our Preview view and set it as the content of our activity.
       
        
        
        
        // need to add cameraPreview to Camera fragment - add class that implements surfaceview
        
        
    	
    	
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		
		// Create an instance of Camera
        camera = AccessCamera.getCameraInstance();
        
        // Create our Preview view and set it as the content of our activity.
        cameraPreview = new CameraPreview(this, camera);
        
        FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(cameraPreview);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}
	
	@Override
	public void onPause(){
		super.onPause();

		// camera should be released when activity goes onPause()
		camera.stopPreview();
		camera.release();
		
	}
	
	
	
	
	
	
	
	
	
}
