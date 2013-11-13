package com.example.phonefinder;


import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class DetectLossService extends Service {
	
	private TestThreading2 smsCheckerRunnable = null;
	Thread smsCheckerThread = null;
	private TestThreading2 callLoggerRunnerable = null;
	Thread callLoggerThread = null;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()
    {

		@Override public void handleMessage(Message msg)
            {
			//Ends threads and move to verify loss	
			Log.d("handle message", "massage handeled");
				
				if (smsCheckerThread != null)
				{
					smsCheckerRunnable.terminate();
					try {
						smsCheckerThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (callLoggerThread != null)
				{
					callLoggerRunnerable.terminate();
					try {
						callLoggerThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Intent intentservice = new Intent(getApplicationContext(),VerifyLossService.class);
				startService(intentservice);
            }
    };
    
	
	@Override
    public void onCreate() {
		Log.d("DetectLossService", "Service Started");


		Toast.makeText(getApplicationContext(), "Your Device is Starting to be protected",
				   Toast.LENGTH_LONG).show();
    
		smsCheckerRunnable = new TestThreading2(handler);
		smsCheckerThread = new Thread (smsCheckerRunnable);
		callLoggerRunnerable = new TestThreading2(handler);
		callLoggerThread = new Thread (callLoggerRunnerable);
		
        
		callLoggerThread.start();
		smsCheckerThread.start();
	
	}

	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
   
	
}

