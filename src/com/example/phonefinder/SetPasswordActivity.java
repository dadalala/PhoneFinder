package com.example.phonefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SetPasswordActivity extends Activity{

	Button submitPassword;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setpassword);

        submitPassword = (Button) findViewById(R.id.submitPassword); 
        
        submitPassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//check if default password is correct
				
				//pop up alert if different
				
				//check if new and confirm password are different
				
				//pop up alert if different
				
				//if correct store password
				
				//toast "password changed"
				
				Intent intent = new Intent(v.getContext(),SettingsActivity.class);
				startActivity(intent);
			}
		});
        
        
        
        
        
        
        
        
        
        
        
        
        
    }


}
