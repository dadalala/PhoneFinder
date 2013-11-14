package com.example.phonefinder;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



public class CallLogCheckerActivity extends Activity {
	
	String callLog;
	TextView callLogView;
	String callLogToDatabase;
	Button backToMainFromCallLog;
	String email ="cs4274@nus.edu.sg";
	private static final String TAG = "call log retreving in process";
	
JSONParser jsonParser = new JSONParser();
	
	// url to get all products list
		private static String url_signup = "http://172.28.177.132/project/insert_call_log.php";
		
		// JSON Node names
			private static final String TAG_SUCCESS = "success";
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readcalllog);
        
        callLogView = (TextView) findViewById(R.id.callLogView);
        backToMainFromCallLog = (Button) findViewById(R.id.backToMainFromCallLog);
		
        callLogToDatabase = getCallDetails();
        callLogView.setText(callLogToDatabase);
        
        
		System.out.println("run to here2?");
		
		new SendToDatabase().execute();
		
		//backToMainFromCallLog.setOnClickListener(new View.OnClickListener() 
		//{
				
		//	@Override
			/*public void onClick(View v) {
				Intent intent = new Intent(v.getContext(),readSMS.class);
				startActivity(intent);
			}*/
		//});

		
		
    }
	
	
        
   public  String getCallDetails() {

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor =this.getContentResolver().query(CallLog.Calls.CONTENT_URI, null,
                    null, null, null);
        int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        sb.append("Call Details :");
        while (managedCursor.moveToNext()) {
            Log.v(TAG,"collecting call list");	
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
   
   
	 /**
		 * Background Async Task to Create new account
		 * */
		class SendToDatabase extends AsyncTask<String, String, String> {

			

			/**
			 * packing and sending online
			 * */
			protected String doInBackground(String... args) {
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("call_log_link", callLogToDatabase));
				params.add(new BasicNameValuePair("email", email));
				
				
				// getting JSON Object
				// Note that create product url accepts POST method
				JSONObject json = jsonParser.makeHttpRequest(url_signup,
						"POST", params);
				
				// check log cat fro response
				Log.d("Create Response", json.toString());

				// check for success tag
				try {
					int success = json.getInt(TAG_SUCCESS);
					Log.d("Sign Up Success Message", json.getString("message").toString());


				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			
		}
   
   
   
   
   
   

}
