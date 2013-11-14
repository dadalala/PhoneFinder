package com.example.phonefinder;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class EvidenceCollectingActivity extends Activity{
	
	Button getImei;
	Button getCurrAddress;
	Button takeVideo;
	Button callLog;
	Button faceDetection;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evidence_page);
        

        getCurrAddress = (Button) findViewById(R.id.getCurrAddress); 
        callLog = (Button) findViewById(R.id.callLog);
        faceDetection = (Button) findViewById(R.id.faceDetection);

    
        
        getCurrAddress.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),CurrentLocation.class);
				startActivity(intent);
			}
		});

// 		callLog.setOnClickListener(new View.OnClickListener() {
//			
// 			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(v.getContext(),GetCallLog.class);
//				startActivity(intent);
//			}
//		});

// 		faceDetection.setOnClickListener(new View.OnClickListener() {
//			
// 			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(v.getContext(),LightDetection.class);
//				startService(intent);
//			}
//		});
// 		


    }

}
