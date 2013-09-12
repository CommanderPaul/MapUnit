package com.birdapp.camera;

import com.mapunit.R;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;


public class CameraFragment extends DialogFragment{
	
	
	
	
	
	public CameraFragment(){
		
	}
	

	
	
	 // can use any view for the active portion of the dialog fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	// need to either use a surface or set a surface to the view
        //View view = inflater.inflate(R.layout.camera_surface_dialog_fragment, container);
        FrameLayout preview = (FrameLayout) inflater.inflate(R.layout.camera_surface_dialog_fragment, container);
        
        getDialog().setTitle("Hello Dude!");

        
        
        View textView = (View) inflater.inflate(R.layout.test_textview, container);
        
        preview.addView(textView); 
        
        //View surfaceView = (View) inflater.inflate(R.layout.camera_surface_dialog_fragment, container);
        
        
        // need to add CameraPreview instantiation to preview
        
        
        //preview.addView(surfaceView);
        
        
        
        
        return preview;
    }
}
