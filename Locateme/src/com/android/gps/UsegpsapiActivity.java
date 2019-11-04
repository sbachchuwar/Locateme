package com.android.gps;



import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;

public class UsegpsapiActivity extends Activity {
    /** Called when the activity is first created. */
	LocationManager lm;
	WifiManager wifi;
    private String TAG = "usegpsapi";
    private static LocationListener locListener;
    private final static int LOCATION_UPDATE_TIME=0;
    private final static int LOCATION_UPDATE_DISTANCE=0;
    NetworkInfo nInfo;
    ConnectivityManager connect;
    //WifiConfiguration network;
    public int LongitudeValue=0;
    public int LatitudeValue=0;
    public int IsWifiConnected=0;
    public int Is3gConnected=0;
    public int WIFI_DISABLE_TIMEOUT=15000;
    public int WIFI_ENABLE_TIMEOUT=15000;
    public int WIFI_CONNECTION_TIMEOUT=20000;
    private static String host="74.125.236.128";
    private final static int timeout=3000;
    private long finish;
    
    public void onCreate(Bundle savedInstanceState) 
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        Log.i(TAG, "in On Create" );
        wifi = (WifiManager)getSystemService(Context.WIFI_SERVICE);
        //network=new WifiConfiguration();
        /*LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        LocationListener locListener=new LocationListener() {
			
			@Override
			public void onProviderDisabled(String provider)
		      {
				Log.i(TAG,"provider disabled: " + provider);
		      }

		      @Override
		      public void onProviderEnabled(String provider)
		      {
		    	  Log.i(TAG,"provider Enabled: " + provider);
		      }

		      @Override
		      public void onStatusChanged(String provider, int status, Bundle extras)
		      {

		      }
			public void onLocationChanged(Location loc) {
				String Text = "My current location is: " +
		        "Latitud = " + loc.getLatitude() +
		        "Longitud = " + loc.getLongitude();
				Log.i(TAG, Text);
				LongitudeValue= (int)loc.getLongitude();
				LatitudeValue=(int)loc.getLatitude();
				
				
				
			}
		};
		lm.requestLocationUpdates( LocationManager. NETWORK_PROVIDER, 0, 0, locListener);
		*/
        
        try
        {
        	if(TestRunner.ConnectMedia.equalsIgnoreCase("wifi"))
    		{
        		
    			testwificonnection();
    		}
    		else if(TestRunner.ConnectMedia.equalsIgnoreCase("3g"))
    		{
    			
    			wifi.setWifiEnabled(false);
    			
    			long s= java.lang.System.currentTimeMillis();
    			long start=s;
    			while(java.lang.System.currentTimeMillis() - s < WIFI_DISABLE_TIMEOUT)
    			{
    				
    				if (wifi.getWifiState() == WifiManager.WIFI_STATE_DISABLED)
    				{
    				Log.i(TAG, "Disabled wifi");
    				finish=java.lang.System.currentTimeMillis();
    				break;
    				}
    			}	
    					
    			if (!wifi.isWifiEnabled() && (finish-start)<=WIFI_DISABLE_TIMEOUT)
    			{
    				Log.i(TAG, "Wifi disabled, checking 3G connection");
    				checkInternetConnection();
    			}
    			else
    			{
    				Log.i(TAG, "WiFi is not getting disabled in 15 seconds, Exiting App");
    			}
    			
    		}
        	
        }
        catch (NullPointerException e)
        {
        	e.printStackTrace();
        }
		
        //checkInternetConnection();
		//testwificonnection();
		//waitforsec();
    }
    
    public void testwificonnection()
    {
    	//wm = (WifiManager)getSystemService(Context.WIFI_SERVICE);
    	connect = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
    	Log.i(TAG, "Checking WiFi connection");
        try
        {
        	nInfo= connect.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if(nInfo.isConnected())
        	{
        		Log.i(TAG, "Wifi Is connected");
        		IsWifiConnected=1;
        		
        		/*try {
					InetAddress.getByName(host).isReachable(timeout);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
        		startGPS();
        		waitforsec();
        	}
        	else
        	{
        		Log.i(TAG, "Wifi Is not Connected");
        		IsWifiConnected=0;
        		//startGPS();
        	}
		}
		
        catch (NullPointerException e) 
        {
			Log.i(TAG, "Got Exception");
		}
    }
    public void checkInternetConnection() 
    {
        final ConnectivityManager conMgr = (ConnectivityManager) getSystemService (Context.CONNECTIVITY_SERVICE);
        if (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() &&    conMgr.getActiveNetworkInfo().isConnected()) {
             // return true;
        	Log.i(TAG, "3G Is connected");
        	Is3gConnected=1;
        	startGPS();
        	waitforsec();
        } 
        else 
        {
              System.out.println("Internet Connection Not Present");
            //return false;
              Is3gConnected=0;
              Log.i(TAG, "3G Is not connected");
        }
     }
    public void startGPS()
    {
    	Log.i(TAG, "in startGPS" );
    	if (lm==null)
    	{
    		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
    		Log.i(TAG, "assigned lm value" );
    		locListener = new MylocationListener();
    		if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
	        {
		    	Log.i(TAG, "starting up Network location provider...");
		    	
		        lm.requestLocationUpdates(
		                LocationManager.NETWORK_PROVIDER, 
		                LOCATION_UPDATE_TIME, 
			            LOCATION_UPDATE_DISTANCE,  
		                locListener);
	        }
    	}
    	
    }
    private class MylocationListener implements LocationListener
    {
    	
		public void onLocationChanged(Location loc) 
		{
			Log.i(TAG, "in Mylocation listener" );

			if (loc != null)
			{
				updateValue(loc);
			}
		
		}

		@Override
		public void onProviderDisabled(String provider) 
		{
			Log.i(TAG,"provider disabled: " + provider);	
			
		}

		@Override
		public void onProviderEnabled(String provider) 
		{
			Log.i(TAG,"provider Enabled: " + provider);	
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) 
		{
			
			
		}
    	
    }
    private void updateValue(Location loc)
    {
    	Log.i(TAG, "in updateValue" );

    	String Text = "My current location is: " +
        "Latitud = " + loc.getLatitude() +
        "Longitud = " + loc.getLongitude();
		Log.i(TAG, Text);
		LongitudeValue= (int)loc.getLongitude();
		LatitudeValue=(int)loc.getLatitude();
    }
    public void waitforsec()
    {
    	Log.i(TAG, "in waitforsec" );
    	try {
			Thread.sleep(50000);
			Log.i(TAG, "waited for 5 seconds");
		} 
    	catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.i(TAG, "Got Error in waitforsec");
		}
    }
}