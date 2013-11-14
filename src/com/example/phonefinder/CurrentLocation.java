package com.example.phonefinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CurrentLocation extends Activity {
	
	// GPSTracker class
	GPSTracker gps;
	//getReverseGeoCoding address;
	TextView currAddress;
	Button fromCurrLocationToMain;

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.current_location);
	    
	    currAddress = (TextView) findViewById(R.id.currAddress);
	    fromCurrLocationToMain = (Button) findViewById(R.id.fromCurrLocationToMain);
	    Log.d("Getting address", "About to start");
        // create class object
        gps = new GPSTracker(CurrentLocation.this);

        // check if GPS enabled     
        if(gps.canGetLocation()){

            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            
            Log.d("Getting address", "Latitude = " + gps.getLatitude());
            Log.d("Getting address", "Longitude = " + gps.getLongitude());
            
            Geocoder geoCoder = new Geocoder(
                    getBaseContext(), Locale.getDefault() );
            
            try 
    		{
    	        List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
    	
    	        String add = "";
    	        if (addresses.size() > 0) 
    	        {
    	            for (int i=0; i<=addresses.get(0).getMaxAddressLineIndex(); 
    	                 i++)
    	               add += addresses.get(0).getAddressLine(i) + "\n";
    	            
    	            
    	        }
    	        
    	        currAddress.setText(add);
    	
    		}
    		
    		catch(IOException e)
    		{
    			 e.printStackTrace();
    		}

        }
        
        fromCurrLocationToMain.setOnClickListener(new View.OnClickListener() 
 		{
 				
 			@Override
 			public void onClick(View v) {
 				Intent intent = new Intent(v.getContext(),EvidenceCollectingActivity.class);
 				startActivity(intent);
 					}
 		});

	}    
}