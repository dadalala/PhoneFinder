package com.example.phonefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetThirdPartyActivity extends Activity{

	Button submitThirdPartyInfo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third_party_contact);
		
		submitThirdPartyInfo = (Button) findViewById(R.id.submitThirdPartyInfo);
		
		submitThirdPartyInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//save details
				
				//upload details to database
				
				
				//go back to main page
				Intent intent = new Intent(v.getContext(),SettingsActivity.class);
				startActivity(intent);
			}
		});
		
		
		
	}
	
}
