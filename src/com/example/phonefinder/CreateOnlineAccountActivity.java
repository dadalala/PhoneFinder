package com.example.phonefinder;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CreateOnlineAccountActivity extends Activity{
	
	Button submitOnlineAcct;
	
	private static String email ;
	private static String password ;
	private static String IMEI;
	public static final String accountDetailsPref = "accountDetails";
	
	private ProgressDialog pDialog;
	private static boolean successSignUp;
	private AlertDialog.Builder emailUsedAlert;
	
	JSONParser jsonParser = new JSONParser();
	
	// url to get all products list
		private static String url_signup = "http://172.23.194.222/project/signup.php";
		
		// JSON Node names
			private static final String TAG_SUCCESS = "success";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.create_online_account);
		
		submitOnlineAcct = (Button) findViewById(R.id.submitOnlineAcct);
		
		submitOnlineAcct.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				EditText emailAddress = (EditText) findViewById(R.id.emailAddress);
				EditText acctPassword = (EditText) findViewById(R.id.acctPassword);
				//save details
				email = emailAddress.getText().toString();
				password = acctPassword.getText().toString();
				//IMEI = getIMEINumber();
				//upload account details to database
				new CreateAccount().execute();
				//store email and password
				
				//pop up account created, website to log in to workstation
				
				//go back to main page
				
			}
		});
		
	
	}// on create
	
	
	 @Override
	    protected void onStop(){
	       super.onStop();

	       	SharedPreferences accountDetails = getSharedPreferences(accountDetailsPref, 0);
			SharedPreferences.Editor accountDetailsEditor = accountDetails.edit();
			accountDetailsEditor.putString("email", email);
			accountDetailsEditor.putString("acctPassword", password);
			accountDetailsEditor.putBoolean("logIn", true);
			accountDetailsEditor.commit();
	
	 }//on Stop
	 
	 
	String getIMEINumber ()
	{
		String IMEI;
		
		TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		IMEI = telephonyManager.getDeviceId();
		
		return IMEI;
	}
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 /**
		 * Background Async Task to Create new account
		 * */
		class CreateAccount extends AsyncTask<String, String, String> {

			/**
			 * Before starting background thread Show Progress Dialog
			 * */
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				pDialog = new ProgressDialog(CreateOnlineAccountActivity.this);
				pDialog.setMessage("Creating Account..");
				pDialog.setIndeterminate(false);
				pDialog.setCancelable(true);
				pDialog.show();
			}

			/**
			 * packing and sending online
			 * */
			protected String doInBackground(String... args) {
				
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("email", email));
				params.add(new BasicNameValuePair("password", password));
				
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

					if (success == 1) {
						// successfully created product
						successSignUp = true;
						Intent intent = new Intent(getApplicationContext(),SettingsActivity.class);
						startActivity(intent);
						
						 
					} else {
						// failed to create product
						successSignUp = false;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}

				return null;
			}

			/**
			 * After completing background task Dismiss the progress dialog
			 * **/
			protected void onPostExecute(String file_url) {
				// dismiss the dialog once done
				pDialog.dismiss();
				
				if(successSignUp)
				{
					emailUsedAlert = new AlertDialog.Builder(CreateOnlineAccountActivity.this);
					emailUsedAlert.setMessage("Account Created");
					emailUsedAlert.setNeutralButton("OK", null);
					emailUsedAlert.show();	
					
				}
				else
				{
					emailUsedAlert = new AlertDialog.Builder(CreateOnlineAccountActivity.this);
					emailUsedAlert.setMessage("Email already used!");
					emailUsedAlert.setNeutralButton("OK", null);
					emailUsedAlert.show();		
					
				}
					
					
				
			}

		}
}
