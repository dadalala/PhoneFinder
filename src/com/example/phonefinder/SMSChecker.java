package com.example.phonefinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsMessage;
import android.util.Log;

public class SMSChecker implements Runnable{
	//handler to send info to main thread
		Handler tt2Handler;
		//determine if thread is running or not
		private volatile boolean execute;
		
		//other varables
		  final static String ACTION = "NotifyServiceAction";
		  //final static String STOP_SERVICE = "";
		  //final static int RQS_STOP_SERVICE = 1;
		  private SMSreceiver  mSMSreceiver;
		  private IntentFilter mIntentFilter;
		  SMSreceiver notifyServiceReceiver;
		  
		  Context smsCheckerContext;
		
		//constructor
		public SMSChecker(Handler handler, Context context) {
			 tt2Handler = handler;
			 smsCheckerContext = context;
		
		      mSMSreceiver = new SMSreceiver(handler); //private
		      mIntentFilter = new IntentFilter(); //private
		      mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
			  smsCheckerContext.registerReceiver(mSMSreceiver, mIntentFilter); 
			
			 
		   }
		
		@Override
	    public void run() {
			//start thread
			this.execute = true;
			
			//continue running till main thread calls terminate
			while (this.execute)
			{
				//do stuff here....
						      	
			}
			
			Log.d("SMSChecker","exiting thread");
			

		}

		public void terminate() {
	        this.execute = false;
	        smsCheckerContext.unregisterReceiver(mSMSreceiver);
	    }
		
		
		public static  class SMSreceiver extends BroadcastReceiver
		  {
		      private final String TAG = this.getClass().getSimpleName();
		      Handler smsReceiverHandler;
		      
		      public SMSreceiver(Handler handler)
		      {
		    	  smsReceiverHandler = handler;
		    	  
		      }
		      
				@Override
		      public void onReceive(Context context, Intent intent)
		      {
		          
		      	//---get the SMS message passed in---
		      	SmsMessage[] messages = null;
		      	String phonenum=null;
		      	String msg=null;
		      	String str=null;
		      	String str2=null;
		      	Log.i(TAG, "Intent recieved: " + intent.getAction());
		 	
		      	// A PDU is a "protocol discription unit", which is the industry format for an SMS message.
		      	//A large message might be broken into many, which is why it is an array of objects.
		      	// Retrieves a map of extended data from the intent.
		      	Bundle bundle = intent.getExtras();
		      	if (bundle != null) {
		      		
		             Object[] pdus = (Object[])bundle.get("pdus");
		             messages = new SmsMessage[pdus.length];
		             for (int i = 0; i < messages.length; i++) {
		                 messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
		                 phonenum= messages[i].getDisplayOriginatingAddress();
		                 
		                 Log.d("SMSChecker","sender's no.: "+phonenum);
		                 
		                 msg = messages[i].getMessageBody().toString();
		                 str =msg.toUpperCase(); 
		                 str.trim();
		                 str2=str.replaceAll("\\s", "");//removes all whitespace and return a new string
		                 System.out.println(str2);
		                 
		             }
		             if(str2.contains("WHEREAREYOU") || str2.contains("PHONEWHEREAREYOU") || str2.contains("PHONE,WHEREAREYOU") ){
		                 Log.d("query sms received","moving to next check");
		                 String sendingnum=phonenum;
		                 System.out.println(sendingnum);
		                  //start activity readcalllog
		                 threadMsg(phonenum.toString(),smsReceiverHandler );
//		                 smsReceiverHandler.sendEmptyMessage(0);
		                 
//		                 Intent i = new Intent();
//		                 i.setClassName("com.example.smsquery", "com.example.smsquery.readcalllog");
//		                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		                 context.startActivity(i);
		                 Log.d("SMSChecker","run to here?");
		                 //start activity wifi
		                 
		                 
		             }
		      		}
		      }
				
				
				private void threadMsg(String msg, Handler handler) {
				     if (!msg.equals(null) && !msg.equals("")) {
				           Message msgObj = handler.obtainMessage();
				           Bundle b = new Bundle();
				           b.putString("message", msg);
				           msgObj.setData(b);
				           handler.sendMessage(msgObj);
				       }
				   }
				
		  }

}
