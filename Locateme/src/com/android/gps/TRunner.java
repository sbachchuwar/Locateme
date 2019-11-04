package com.android.gps;


import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;


import com.android.gps.UsegpsapiActivity;

public class TRunner extends ActivityInstrumentationTestCase2<UsegpsapiActivity> 
{
	private String TAG="TRunner";

	private UsegpsapiActivity mActivity;
	public TRunner() 
	{
		super("com.android.gps", UsegpsapiActivity.class);
		// TODO Auto-generated constructor stub
	}
	protected void setUp() throws Exception {
		mActivity=this.getActivity();
        getActivity();
        super.setUp();
    }
	public void testGPS()
	{
		
		
		if(TestRunner.ConnectMedia.equalsIgnoreCase("wifi"))
		{
			if(mActivity.IsWifiConnected==1)
			{
				Log.i(TAG, "value of Longitude is" + mActivity.LongitudeValue + "& Latitude is" + mActivity.LatitudeValue + "");
		       	assertTrue(mActivity.LongitudeValue == TestRunner.expectedLongitude);
				assertTrue(mActivity.LatitudeValue == TestRunner.expectedLatitude);
			}
			else
			{
				Log.i(TAG, "Connection Media is wifi but wifi is not connected. FAILED");
				assertFalse(mActivity.IsWifiConnected==0);
			}
				
		}
		else if (TestRunner.ConnectMedia.equalsIgnoreCase("3g"))
		{
			if(mActivity.Is3gConnected==1)
			{
				Log.i(TAG, "value of Longitude is" + mActivity.LongitudeValue + "& Latitude is" + mActivity.LatitudeValue + "");
		       	assertTrue(mActivity.LongitudeValue == TestRunner.expectedLongitude);
				assertTrue(mActivity.LatitudeValue == TestRunner.expectedLatitude);
				
			}
			else
			{
				Log.i(TAG, "Connection Media is 3G but 3G is not connected. FAILED");
				assertFalse(mActivity.Is3gConnected==0);
			}
			
		}
		else
		{
			Log.i(TAG, "No Connect Media is mentioned. Please use this command properly:adb.exe shell am instrument -e Expected_Longitude <Expected Longitude Value> -e Expected_Latitude <Expected Latitude Value> -e Connect_Media <3g or wifi> -w com.android.gps/.TestRunner");
		}
       	
	}

}
