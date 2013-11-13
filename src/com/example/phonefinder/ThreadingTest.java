package com.example.phonefinder;

import android.util.Log;

public class ThreadingTest implements Runnable{
	
	@Override
    public void run() {
		
		for (int i = 0; i < 10; i++)
		{
			
			Log.d("test thread", "First tread:" + i);
		}
		
}

}
