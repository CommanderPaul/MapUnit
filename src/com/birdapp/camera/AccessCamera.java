package com.birdapp.camera;

import android.hardware.Camera;

public class AccessCamera {
	
	private static Camera camera;
	
	
	// importing the hardware.camera, not the graphics.camera
	
	/** A safe way to get an instance of the Camera object. */
	public static Camera getCameraInstance(){
	    Camera camera = null;
	    try {
	    	camera = Camera.open(); // attempt to get a Camera instance - should specify which one
	    }
	    catch (Exception e){
	        // Camera is not available (in use or does not exist)
	    }
	    return camera; // returns null if camera is unavailable
	}
	
	public static void closeCamera(){
		
		// when is the best time to call this?
		camera.release();
	}
	
	
}
