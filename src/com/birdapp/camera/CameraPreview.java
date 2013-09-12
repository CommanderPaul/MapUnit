package com.birdapp.camera;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;


/**
 * 
 * @author paulw
 *
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{
	
	private SurfaceHolder surfaceHolder;
    private Camera camera;
    public static String TAG = "some tag";

    
    
    
    
    
    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.camera = camera;
        

        // set camera preferences here?
        
        
        
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        surfaceHolder = getHolder();
        
        surfaceHolder.addCallback(this);
        
        
        // deprecated setting, but required on Android versions prior to 3.0
        //mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, now tell the camera where to draw the preview.
    	
    	 // set preferences here?
        CameraFeatures cameraFeatures = new CameraFeatures(camera);
        cameraFeatures.setCameraFeatures();
    	
    	
        try {
            camera.setPreviewDisplay(holder);
            camera.startPreview();
        } catch (IOException e) {
            Log.d(TAG, "Error setting camera preview: " + e.getMessage());
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // empty. Take care of releasing the Camera preview in your activity.
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // If your preview can change or rotate, take care of those events here.
        // Make sure to stop the preview before resizing or reformatting it.
    	
    	
    	
    	
    	
    	
    	/**
    	 * This runs only once as long as the manifiest forces landscape view
    	 */
    	    	
    	Log.i(TAG, "surface view changed");

        if (surfaceHolder.getSurface() == null){
          // preview surface does not exist
          return;
        }

        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e){
          // ignore: tried to stop a non-existent preview
        }

        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();

        } catch (Exception e){
            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
        }
    }

	
	
	
}
