package com.example.phonefinder;

import android.os.Handler;

public class CallLogChecker implements Runnable{

	//handler to send info to main thread
		Handler callLogHandler;
		//determine if thread is running or not
		private volatile boolean execute;
		
		//constructor
		public CallLogChecker(Handler handler) {
			 callLogHandler = handler;
		   }
		
		@Override
	    public void run() {
			//start thread
			this.execute = true;
			
			//continue running till main thread calls terminate
			while (this.execute)
			{
				//do stuff here....
				//call CallLogCheckerService
			}
			

		}

		public void terminate() {
	        this.execute = false;
	    }
	
}
