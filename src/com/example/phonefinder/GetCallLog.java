package com.example.phonefinder;

import java.sql.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.provider.CallLog;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetCallLog extends Activity{
	
	String callLog;
	TextView callLogView;
	Button backToMainFromCallLog;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_call_log);
        
        callLogView = (TextView) findViewById(R.id.callLogView);
        backToMainFromCallLog = (Button) findViewById(R.id.backToMainFromCallLog);
		
        
		callLogView.setText(getCallDetails());
		
		backToMainFromCallLog.setOnClickListener(new View.OnClickListener() 
		{
				
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),EvidenceCollectingActivity.class);
				startActivity(intent);
					}
		});

		
		
    }
	
	
	
	
	
	
	
	private String getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            String phNumber = managedCursor.getString(number);
            String callType = managedCursor.getString(type);
            String callDate = managedCursor.getString(date);
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = managedCursor.getString(duration);
            String dir = null;
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
            case CallLog.Calls.OUTGOING_TYPE:
                dir = "OUTGOING";
                break;

            case CallLog.Calls.INCOMING_TYPE:
                dir = "INCOMING";
                break;

            case CallLog.Calls.MISSED_TYPE:
                dir = "MISSED";
                break;
            }
            sb.append("\nPhone Number:--- " + phNumber + " \nCall Type:--- "
                    + dir + " \nCall Date:--- " + callDayTime
                    + " \nCall duration in sec :--- " + callDuration);
            sb.append("\n----------------------------------");
        }
        managedCursor.close();
        return sb.toString();

    }
	
	

}
