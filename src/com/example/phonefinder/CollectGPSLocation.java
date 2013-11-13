package com.example.phonefinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class CollectGPSLocation implements Runnable {
	
	//handler to send info to main thread
		Handler collectGpsLocationHandler;
		//determine if thread is running or not
		private volatile boolean execute;
		
		//other varables
		Context collectGPSLocationContext;
		Context collectGPSLocationBaseContext;
		String curLocationAddress = "";
		String email = "";
		double latitude = -1;
		double longitude = -1;
		JSONParser jsonParser = new JSONParser();
		Thread updateDatabase;
		public static final String accountDetailsPref = "accountDetails";
		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		// url to get all products list
		private static String url_updateGPS = "http://172.23.49.254/project/signup.php";
		
		//constructor
		public CollectGPSLocation(Handler handler, Context context, Context baseContext) {
			collectGpsLocationHandler = handler;
			collectGPSLocationContext = context;
			collectGPSLocationBaseContext = baseContext;
			
		   }
		

		
		@Override
	    public void run() {
			//start thread
			this.execute = true;
			
			Log.d("CollectGPSLocation","thread starts running");
			SharedPreferences accountDetails = collectGPSLocationContext.getSharedPreferences(accountDetailsPref, 0);
			email = accountDetails.getString("email","nil");
			
			//continue running till main thread calls terminate
			while (this.execute)
			{
				
				LocationManager lm = (LocationManager)collectGPSLocationContext.getSystemService(collectGPSLocationContext.LOCATION_SERVICE);
				Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
				
				longitude = location.getLongitude();
				latitude = location.getLatitude();
 
	            if(latitude >= 0 && longitude >= 0)
	            {
	            
		            Log.d("Getting address", "Latitude = " + latitude);
		            Log.d("Getting address", "Longitude = " + longitude);
		            
		            Geocoder geoCoder = new Geocoder(
		            		collectGPSLocationBaseContext, Locale.getDefault() );
		            try 
		    		{
		    	        List<Address> addresses = geoCoder.getFromLocation(latitude, longitude, 1);
		    	        String newAddress = "";
		    	        
		    	        if (addresses.size() > 0) 
		    	        {
		    	            for (int i=0; i<=addresses.get(0).getMaxAddressLineIndex(); 
		    	                 i++)
		    	            	newAddress += addresses.get(0).getAddressLine(i) + "\n";

		    	        }
		    	        
		    	        if (newAddress != curLocationAddress)
		    	        {
		    	        	curLocationAddress = newAddress;
		    	        	
		    	        	updateDatabase = new Thread(new Runnable() { 
		    	        		public void run() { 
		    	        			try {
			    	        				List<NameValuePair> params = new ArrayList<NameValuePair>();
			    	        				params.add(new BasicNameValuePair("email", email));
			    	        				params.add(new BasicNameValuePair("insert_location", curLocationAddress));
			    	        				
			    	        				// getting JSON Object
			    	        				// Note that create product url accepts POST method
			    	        				JSONObject json = jsonParser.makeHttpRequest(url_updateGPS,
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
			    	        				
					    	            } catch (Exception e) {
					    	                 //TODO Auto-generated catch block
					    	                e.printStackTrace();
					    	            	} 
		    	        			} 
		    	        		});

		    	        	updateDatabase.start(); 
		    	        	
		    	        }
		    	        
		    	        //save curLocationAddress to database
		    	        Log.d("CollectGPSLocation","current address: "+ curLocationAddress);
		    	        
		    	
		    		}
		    		
		    		catch(IOException e)
		    		{
		    			 e.printStackTrace();
		    		}
	            }
		        
				
		        //this.execute = false;
			}// while
			
			Log.d("CollectGPSLocation","thread terminating running");
			

		}

		public void terminate() {
	        this.execute = false;
	    }
		


}
