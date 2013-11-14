package com.example.phonefinder;

import java.util.List;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;



public class WifiCheckerActivity extends Activity  {
	
    TextView wificonnectionView;
    WifiManager mainWifi;
    WifiReceiver receiverWifi;
    List<ScanResult> wifiList;
    StringBuilder sb = new StringBuilder();
    
   // Array of string to store the available wifi
    String [] avail_wifi;
   
    
    public void onCreate(Bundle savedInstanceState) {
    	
       super.onCreate(savedInstanceState);
       
       setContentView(R.layout.wifichecker);
       wificonnectionView = (TextView) findViewById(R.id.wificonnectionView);
       
       // Initiate wifi service manager
       mainWifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
       
       // Check for wifi is disabled
       if (mainWifi.isWifiEnabled() == false)
            {   
    	        // If wifi disabled then enable it
                // Toast.makeText(getApplicationContext(), "wifi is disabled..making it enabled", Toast.LENGTH_LONG).show();
                mainWifi.setWifiEnabled(true);
            } 
       
       // wifi scanned value broadcast receiver 
       receiverWifi = new WifiReceiver();
       
       // Register broadcast receiver 
       // Broadcast receiver will automatically call when number of wifi connections changed
       registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
       mainWifi.startScan();
       wificonnectionView.setText("Starting Scan...");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Refresh");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        mainWifi.startScan();
        wificonnectionView.setText("Starting Scan");
        return super.onMenuItemSelected(featureId, item);
    }

    protected void onPause() {
        unregisterReceiver(receiverWifi);
        super.onPause();
    }

    protected void onResume() {
        registerReceiver(receiverWifi, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        super.onResume();
    }
    
    // Broadcast receiver class called its receive method 
    // when number of wifi connections changed
    
    class WifiReceiver extends BroadcastReceiver {
    	
    	// This method call when number of wifi connections changed
        public void onReceive(Context c, Intent intent) {
        	
            sb = new StringBuilder();
            wifiList = mainWifi.getScanResults(); 
            sb.append("\n        Number Of Wifi connections :"+wifiList.size()+"\n\n");
            
            for(int i = 0; i < wifiList.size(); i++){
            	
                sb.append(new Integer(i+1).toString() + ". ");
                sb.append((wifiList.get(i)).toString());
                sb.append("\n\n");
                
                }
            
            wificonnectionView.setText(sb); 
          
            //to store a list of available wifi
         
            String[] avail_wifi = new String[1000];
          
            for( int j = 0; j < wifiList.size(); j++){
            	//wifiList.get(j).toString();
            	// store the string into array
        	  avail_wifi [j]=wifiList.get(j).toString();
            	
            }
            	
             //compare with database wifilist?      			
         }
        
        
    }

}
