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
    
		Thread mBackground1;
		Thread mBackground2;
		
		mBackground1 = new Thread(new Runnable()
        {
                // Setup the run() method that is called when the background thread
                // is started.
                public void run()
                {
                        // Do you background thread process here...
                        // In my case, strMsgRcvd is a String where I store
                        // the message received via my socket.

                	for(int i = 0; i < 10; i++)
                	{
                		Log.d("DetectLossService", "first tread test: " + i);
                		
                	}
                

                }
        });
		
		mBackground2 = new Thread(new Runnable()
        {
                // Setup the run() method that is called when the background thread
                // is started.
                public void run()
                {
                        // Do you background thread process here...
                        // In my case, strMsgRcvd is a String where I store
                        // the message received via my socket.

                	for(int i = 0; i < 10; i++)
                	{
                		Log.d("DetectLossService", "second tread test: " + i);
                		
                	}
                

                }
        });
		
		mBackground1.start();
		mBackground2.start();
	
	}

	
	

		
	
}

