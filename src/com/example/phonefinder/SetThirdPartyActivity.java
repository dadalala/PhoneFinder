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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetThirdPartyActivity extends Activity{

	Button submitThirdPartyInfo;
	
	//accounts variable
	private static String thirdPartyName ;
	private static String thirdPartyNum ;
	private static String email ;
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
		setContentView(R.layout.third_party_contact);
		
		submitThirdPartyInfo = (Button) findViewById(R.id.submitThirdPartyInfo);
		
		submitThirdPartyInfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				//save details
				EditText thirdPartyNameText = (EditText) findViewById(R.id.thirdPartyName);
				EditText thirdPartyNumText = (EditText) findViewById(R.id.thirdPartyNum);
				
				thirdPartyName = thirdPartyNameText.getText().toString();
				thirdPartyNum = thirdPartyNumText.getText().toString();
				//upload details to database
				SharedPreferences accountDetails = getSharedPreferences(accountDetailsPref, 0);
				email = accountDetails.getString("email","nil");
				
				
				//go back to main page
				Intent intent = new Intent(v.getContext(),SettingsActivity.class);
				startActivity(intent);
			}
		});
		
		
		
	}// onCreate
	
	 @Override
	    protected void onStop(){
	       super.onStop();

	       	SharedPreferences accountDetails = getSharedPreferences(accountDetailsPref, 0);
			SharedPreferences.Editor accountDetailsEditor = accountDetails.edit();
			accountDetailsEditor.putString("thirdPartyName", thirdPartyName);
			accountDetailsEditor.putString("thirdPartyNum", thirdPartyNum);
			accountDetailsEditor.commit();
	
	 }//on Stop
	 
	 
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
				pDialog = new ProgressDialog(SetThirdPartyActivity.this);
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
				params.add(new BasicNameValuePair("thirdPartyName", thirdPartyName));
				params.add(new BasicNameValuePair("thirdPartyNum", thirdPartyNum));
				
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
					emailUsedAlert = new AlertDialog.Builder(SetThirdPartyActivity.this);
					emailUsedAlert.setMessage("Third Party Number Updated");
					emailUsedAlert.setNeutralButton("OK", null);
					emailUsedAlert.show();	
					
				}
				else
				{
					emailUsedAlert = new AlertDialog.Builder(SetThirdPartyActivity.this);
					emailUsedAlert.setMessage("Number incorrect format");
					emailUsedAlert.setNeutralButton("OK", null);
					emailUsedAlert.show();		
					
				}
					
					
				
			}

		}
	
}
