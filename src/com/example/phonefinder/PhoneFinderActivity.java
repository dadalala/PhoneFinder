package com.example.phonefinder;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PhoneFinderActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_finder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.phone_finder, menu);
		return true;
	}

}
