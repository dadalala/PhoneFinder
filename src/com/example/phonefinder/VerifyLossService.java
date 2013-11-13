package com.example.phonefinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class VerifyLossService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
    public void onCreate() {
		Log.d("Verifyf Loss","verify loss service activated");
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		
		for(int i = 0; i < 100; i++)
		Log.d("Verifyf Loss","value = " + i);
	}

}
