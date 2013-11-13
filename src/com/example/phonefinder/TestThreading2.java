package com.example.phonefinder;

import android.os.Handler;
import android.util.Log;

public class TestThreading2  implements Runnable {
	
	Handler tt2Handler;
	private volatile boolean execute;
	
	int i;
	
	 public TestThreading2(Handler handler) {
		 tt2Handler = handler;
	   }
	
	@Override
    public void run() {
		this.execute = true;
		
		while (this.execute)
		{
			i++;
				
				Log.d("test thread", "Second tread:" + i);
				
				if(i == 6)
				{
					tt2Handler.sendEmptyMessage(0);
				}
		}
		

	}

	public void terminate() {
        this.execute = false;
    }
	
	
	
}
