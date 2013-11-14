package com.example.phonefinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.phonefinder.CallLogCheckerActivity.SendToDatabase;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
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
	String add;
	String email ="cs4274@nus.edu.sg";
	private static final String TAG = "call log retreving in process";
JSONParser jsonParser = new JSONParser();
	
	// url to get all products list
		private static String url_signup = "http://172.28.177.132/project/insert_location.php";
		
		// JSON Node names
			private static final String TAG_SUCCESS = "success";

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
    	
    	        add = "";
    	        if (addresses.size() > 0) 
    	        {
    	            for (int i=0; i<=addresses.get(0).getMaxAddressLineIndex(); 
    	                 i++)
    	               add += addresses.get(0).getAddressLine(i) + "\n";
    	            
    	            
    	        }
    	        
    	        currAddress.setText(add);
    	        new SendToDatabase().execute();
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
	
	
	 /**
	 * Background Async Task to Create new account
	 * */
	class SendToDatabase extends AsyncTask<String, String, String> {

		

		/**
		 * packing and sending online
		 * */
		protected String doInBackground(String... args) {
			
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			params.add(new BasicNameValuePair("location_link", add));
			params.add(new BasicNameValuePair("email", email));
			
			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_signup,
					"POST", params);
			
			// check log cat fro response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);
				Log.d("Sign Up Success Message", json.getString("message").toString());


			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		
	}
	
	
	
	
	
	
	
	
	
	
	
	
}