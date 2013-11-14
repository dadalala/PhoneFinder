package com.example.phonefinder;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class PhoneFinderActivity extends Activity {
	
	Button guiSettings;
	Button guiStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_finder);
		
		 guiSettings = (Button) findViewById(R.id.guiSettings); 
	     guiStart = (Button) findViewById(R.id.guiStart); 
		
	     guiSettings.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(v.getContext(),DetectLossService.class);
					startActivity(intent);
				}
			});
		
	     guiStart.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					//set boot up start
					Log.d("PhoneFinder", "entered click");
					//start service
					Intent intentservice = new Intent(v.getContext(), SettingsActivity.class);
				    startService(intentservice);
					
					//close activity
				}
			});
		
		
		
		
		
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_finder, menu);
		return true;
	}

}
