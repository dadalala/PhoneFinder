package com.example.phonefinder;

import android.os.Handler;

public class ThreadTemplate implements Runnable{
	
	//handler to send info to main thread
	Handler ttHandler;
	//determine if thread is running or not
	private volatile boolean execute;
	
	//constructor
	public ThreadTemplate(Handler handler) {
		 ttHandler = handler;
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
