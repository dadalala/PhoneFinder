package com.example.phonefinder;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;


public class EvidenceCollectingService extends Service{

	private SMSChecker collectLocationRunnable = null;
	private CallLogChecker collectPictureRunnable = null;
	private CallLogChecker collectCallLogsRunnable = null;
	private CallLogChecker lockDownPhoneRunnable = null;
	
	Thread collectLocationThread = null;
	Thread collectPictureThread = null;
	Thread collectCallLogsThread = null;
	Thread lockDownPhoneThread = null;
	
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler()
    {

		@Override public void handleMessage(Message msg)
            {
			//Ends threads and move to verify loss	
			Log.d("handle message", "massage handeled");
				
				if (collectLocationThread != null)
				{
					collectLocationRunnable.terminate();
					try {
						collectLocationThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				if (collectPictureThread != null)
				{
					collectPictureRunnable.terminate();
					try {
						collectPictureThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (collectCallLogsThread != null)
				{
					collectCallLogsRunnable.terminate();
					try {
						collectCallLogsThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (lockDownPhoneThread != null)
				{
					lockDownPhoneRunnable.terminate();
					try {
						lockDownPhoneThread.join();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				Intent intentservice = new Intent(getApplicationContext(),DetectLossService.class);
				startService(intentservice);
            }
    };
	
	
	@Override
    public void onCreate() {
		Log.d("evidence collecting service", "starting evidence collection service");
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		
		for(int i = 0; i < 100; i++)
			Log.d("evidence Collect","value = " + i);

//		Intent intentservice2 = new Intent(getApplicationContext(), VerifyLossService.class);
//	    startService(intentservice2);
	    
	}

	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

}
