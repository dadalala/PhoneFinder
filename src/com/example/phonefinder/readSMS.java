package com.example.phonefinder;

//import android.app.Activity;
//import android.app.IntentService;
import android.app.Service;
import android.content.BroadcastReceiver;
//import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
//import android.database.Cursor;
//import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.util.Log;




public class readSMS extends Service {

  final static String ACTION = "NotifyServiceAction";
  //final static String STOP_SERVICE = "";
  //final static int RQS_STOP_SERVICE = 1;
  private SMSreceiver  mSMSreceiver;
  private IntentFilter mIntentFilter;
  SMSreceiver notifyServiceReceiver;

  
  @Override
  public IBinder onBind(Intent arg0) {
          return null;
  }

  @Override
  public void onCreate() {
      notifyServiceReceiver = new SMSreceiver(); //public
      mSMSreceiver = new SMSreceiver(); //private
      mIntentFilter = new IntentFilter(); //private
      mIntentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
      registerReceiver(mSMSreceiver, mIntentFilter);

      super.onCreate();
  }

 /* @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
      Log.i("MyService","reading SMS Service started");
      IntentFilter intentFilter = new IntentFilter();
      intentFilter.addAction(ACTION);
      registerReceiver(notifyServiceReceiver, intentFilter);

      return super.onStartCommand(intent, flags, startId);
  }*/

  @Override
  public void onDestroy() {
      Log.i("MyService","Service destroyed");
      this.unregisterReceiver(mSMSreceiver);
      super.onDestroy();

  }

  public static  class SMSreceiver extends BroadcastReceiver
  {
      private final String TAG = this.getClass().getSimpleName();

      
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
                 Intent i = new Intent();
                 i.setClassName("com.example.smsquery", "com.example.smsquery.readcalllog");
                 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                 context.startActivity(i);
                 System.out.println("run to here?");
                 //start activity wifi
                 
             }
      	}
 	 	 }
   }

  
}
