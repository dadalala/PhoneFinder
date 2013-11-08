package com.example.phonefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateOnlineAccountActivity extends Activity{
	
	Button submitOnlineAcct;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_online_account);
		
		submitOnlineAcct = (Button) findViewById(R.id.submitOnlineAcct);
		
		submitOnlineAcct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//save details
				
				//upload account details to database
				
				//pop up account created, website to log in to workstation
				
				//go back to main page
				Intent intent = new Intent(v.getContext(),SettingsActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
