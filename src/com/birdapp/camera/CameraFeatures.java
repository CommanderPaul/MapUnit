package com.birdapp.camera;

import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;

/**
 * This class polls the camera for features
 * and then sets the desired features
 * 
 * @author pwroe
 *
 */
public class CameraFeatures {
	
	private static final String TAG = "CameraFeature Class Tag ";
	private Camera camera;
	
	// constructor should take a camera instance
	public CameraFeatures(Camera camera){
		
		this.camera = camera;
		
	}
	
	// poll camera for features
	private void pollCameraForFeatures(){
		
		// get supported sizes
    	for(Size size : camera.getParameters().getSupportedPreviewSizes()){
    		Log.i( TAG,("Height" + size.height + " Width " +  size.width));	//	display in LogCat
    	}
    	
		
	}
	
	
	// set desired features
	public void setCameraFeatures(){
		
		Parameters camparam = camera.getParameters();
    	
    	
//    	camparam.setPreviewSize(480, 640); crashes - params reversed
    	camparam.setPreviewSize(640, 480);
    	
//    	camparam.setGpsAltitude(123123);
    	camparam.setZoom(30);	//	can be up to 30 on my phone
    	camparam.setColorEffect("negative");	//	not all color effects work
    	
    	//Sets the dimensions for EXIF thumbnail in Jpeg picture. 
    	//If applications set both width and height to 0, EXIF will not contain thumbnail.
    	//jpeg-thumbnail-size-values=512x288,480x288,432x288,512x384,352x288,176x144,0x0; (for samsung III front camera)
    	camparam.setJpegThumbnailSize(176, 144);
    	
    	//picture-size-values=3264x2448,3264x2176,3264x1836,2048x1536,2048x1152,1600x1200,1280x960,1280x720,960x720,640x480;
    	camparam.setPictureSize(640, 480);// will crash if values are unsupported.
    	
    	//camparam.set(key, value)	//	can set unspecified parameters
    	
    	
    	
    	camera.setParameters(camparam);
		
		
		
	}
	
	
	
}
