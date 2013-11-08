package com.example.phonefinder;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class DetectLossService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
		Log.d("DetectLossService", "Service Started");


		Toast.makeText(getApplicationContext(), "Your Device is Starting to be protected",
				   Toast.LENGTH_LONG).show();
    
	

	
	
	}

	
	

		
	
}

