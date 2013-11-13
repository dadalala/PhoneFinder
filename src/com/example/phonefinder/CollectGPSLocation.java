package com.example.phonefinder;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

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
		double latitude = -1;
		double longitude = -1;
		
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
		    	
		    	        String curLocationAddress = "";
		    	        if (addresses.size() > 0) 
		    	        {
		    	            for (int i=0; i<=addresses.get(0).getMaxAddressLineIndex(); 
		    	                 i++)
		    	            	curLocationAddress += addresses.get(0).getAddressLine(i) + "\n";

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
