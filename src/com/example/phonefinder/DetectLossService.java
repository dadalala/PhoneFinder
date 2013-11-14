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
	
	private SMSChecker smsCheckerRunnable = null;
	Thread smsCheckerThread = null;
	private CallLogChecker callLoggerRunnerable = null;
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
				
				Intent intentservice = new Intent(getApplicationContext(),WifiCheckerActivity.class);
				startService(intentservice);
            }
    };
    
	
	@Override
    public void onCreate() {
		Log.d("DetectLossService", "Service Started");


		Toast.makeText(getApplicationContext(), "Your Device is Starting to be protected",
				   Toast.LENGTH_LONG).show();
    
		smsCheckerRunnable = new SMSChecker(handler, getApplicationContext() );
		smsCheckerThread = new Thread (smsCheckerRunnable);
		callLoggerRunnerable = new CallLogChecker(handler);
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

