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
		double latitude = 20;
		double longitude = 20;
		
		
		LocationManager locationManager;
		Location location;
		// GPS status flag to check if GPS is switched on or not
		boolean isGPSEnabled = false;

		// network status flag to check if network provider connection is switched
		// on or not
		boolean isNetworkEnabled = false;

		// flag for GPS status
		boolean canGetLocation = false;
		
		
		JSONParser jsonParser = new JSONParser();
		Thread updateDatabase;
		public static final String accountDetailsPref = "accountDetails";
		// JSON Node names
		private static final String TAG_SUCCESS = "success";
		// url to get all products list
		private static String url_updateGPS = "http://172.23.194.222/project/insert_location.php";
		
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
				try {
					locationManager = (LocationManager) collectGPSLocationContext
			                .getSystemService(collectGPSLocationContext.LOCATION_SERVICE);

			        // getting GPS status
			        isGPSEnabled = locationManager
			                .isProviderEnabled(LocationManager.GPS_PROVIDER);

			        // getting network status
			        isNetworkEnabled = locationManager
			                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			        if (!isGPSEnabled && !isNetworkEnabled) {
			            // no network provider is enabled
			        } else {
			            this.canGetLocation = true;
			            if (isNetworkEnabled) {
			                locationManager.requestLocationUpdates(
			                        LocationManager.NETWORK_PROVIDER,
			                        60000,
			                        10, (LocationListener) collectGPSLocationContext);
			                Log.d("Network", "Network");
			                if (locationManager != null) {
			                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			                    if (location != null) {
			                        latitude = location.getLatitude();
			                        longitude = location.getLongitude();
			                    }
			                }
			            }
			            // if GPS Enabled get latitude/longitude using GPS Services
			            if (isGPSEnabled) {
			                if (location == null) {
			                    locationManager.requestLocationUpdates(
			                            LocationManager.GPS_PROVIDER,
			                            60000,
			                            10, (LocationListener) collectGPSLocationContext);
			                    Log.d("GPS Enabled", "GPS Enabled");
			                    if (locationManager != null) {
			                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
			                        if (location != null) {
			                            latitude = location.getLatitude();
			                            longitude = location.getLongitude();
			                        }
			                    }
			                }
			            }
			        }

			    } catch (Exception e) {
			        e.printStackTrace();
			    }

				
				
				
//				LocationManager lm = (LocationManager)collectGPSLocationContext.getSystemService(collectGPSLocationContext.LOCATION_SERVICE);
//				Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//				
//				if(location != null)
//				{
//					longitude = location.getLongitude();
//					latitude = location.getLatitude();
//				}
					
				
				Log.d("Getting address", "Latitude = " + latitude);
	            Log.d("Getting address", "Longitude = " + longitude);
 
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
			    	        				params.add(new BasicNameValuePair("location_link", curLocationAddress));
			    	        				params.add(new BasicNameValuePair("email", email));
			    	        				
			    	        				
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
		        
				
		        this.execute = false;
			}// while
			
			Log.d("CollectGPSLocation","thread terminating running");
			

		}

		public void terminate() {
	        this.execute = false;
	    }
		


}
