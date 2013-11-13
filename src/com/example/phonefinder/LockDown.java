package com.example.phonefinder;

import android.os.Handler;

public class LockDown implements Runnable{
	
	//handler to send info to main thread
		Handler lockDownHandler;
		//determine if thread is running or not
		private volatile boolean execute;
		
		//constructor
		public LockDown(Handler handler) {
			lockDownHandler = handler;
		   }
		
		@Override
	    public void run() {
			//start thread
			this.execute = true;
			
			//continue running till main thread calls terminate
			while (this.execute)
			{
				//do stuff here....
			}
			

		}

		public void terminate() {
	        this.execute = false;
	    }

}
