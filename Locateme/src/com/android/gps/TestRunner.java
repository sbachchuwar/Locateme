package com.android.gps;

import junit.framework.TestSuite;
import android.os.Bundle;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;
import android.util.Log;

public class TestRunner extends InstrumentationTestRunner 
{
	//LocationManager lm;

	private static final String TAG = "LocatorTestRunner";
	public static int expectedLongitude=73; // Taken Pune location as default value
	public static int expectedLatitude=18; // Taken Pune location as default value
	public static String ConnectMedia;
	
	/*public void onCreate(Bundle arguments) 
	{	
		
	}*/
	public TestSuite getAllTests() 
	{
        TestSuite suite = new InstrumentationTestSuite(this);
        suite.addTestSuite(TRunner.class);
        return suite;
    }
	public ClassLoader getLoader() {
        return TestRunner.class.getClassLoader();
    }
	public void onCreate(Bundle arguments) 
	{		
		Log.i(TAG, "in TesRunner");
		String param_value= arguments.getString("Expected_Longitude");
		if(param_value != null)
		{
			try
			{
				expectedLongitude=Integer.parseInt(param_value);
				Log.i(TAG, "value of Expected Longitude is " + expectedLongitude);
			}
			catch(NumberFormatException e)
			{
				Log.i(TAG, "Invalid Argument, taken default value");
				
			}
		}
		param_value= arguments.getString("Expected_Latitude");
		if(param_value != null)
		{
			try
			{
				expectedLatitude=Integer.parseInt(param_value);
				Log.i(TAG, "value of Expected Latitude is " + expectedLatitude);
			}
			catch(NumberFormatException e)
			{
				Log.i(TAG, "Invalid Argument, taken default value");
				
			}
		}
		param_value= arguments.getString("Connect_Media");
		if(param_value !=null)
		{
			
			ConnectMedia= param_value;
				Log.i(TAG, "Connection Media is " + ConnectMedia);
			
			
		}
		
		super.onCreate(arguments);
	}
	
}
