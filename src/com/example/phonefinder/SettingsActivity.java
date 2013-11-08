package com.example.phonefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SettingsActivity extends Activity{
	
	Button setPassword;
	Button setThirdPartyNumber;
	Button uninstall;
	Button backToMainButton;
	Button createAccount;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings);
	
		setPassword = (Button) findViewById(R.id.setPassword); 
        setThirdPartyNumber = (Button) findViewById(R.id.setThirdPartyNumber); 
        uninstall = (Button) findViewById(R.id.uninstall);
        backToMainButton = (Button) findViewById(R.id.backToMainButton);
        createAccount = (Button) findViewById(R.id.createAccount);
        
        setPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),SetPasswordActivity.class);
				startActivity(intent);
			}
		});
		
        setThirdPartyNumber.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),SetThirdPartyActivity.class);
				startActivity(intent);
			}
		});
		
        createAccount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),CreateOnlineAccountActivity.class);
				startActivity(intent);
			}
		});
        
        uninstall.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//uninstall app
			}
		});
		
        backToMainButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),PhoneFinderActivity.class);
				startActivity(intent);
			}
		});
		
	}

}
