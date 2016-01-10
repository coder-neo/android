package com.samsung.profilemanager.constants;

import android.net.Uri;

public class AppConstants
{
	public static final String URI_PREFIX="content://com.samsung.profilemanager.provider.AppDatabase/";
	public static final String CONTENT_PROVIDER="content://com.samsung.profiledbapplication.provider.DbContentProvider/";

	public static final String PHONE_TABLE="Phone_Table";
	public static final String SMS_TABLE="Sms_Table";
	
	public static final String PHONE_URI_STRING=CONTENT_PROVIDER+PHONE_TABLE;
	public static final String SMS_URI_STRING=CONTENT_PROVIDER+SMS_TABLE;
	public static String DB_NAME="Prof_Manager_DB";
	public static int VERSION=1;
	public static final String SMS_CONTENT_URI="content://sms";
	public static final String CUSTOM_PROFILE_TABLE="Custom_Profile_Table";
	public static final String URI_CUSTOM_PROFILE_STRING=AppConstants.CONTENT_PROVIDER+CUSTOM_PROFILE_TABLE;
	
	public static final String dataBundle="dataBundle";
	
	public static String sNum= "Number";
	public static String sName="Name";
	public static String sTime="Time";
	public static String sFreq="Freq";
	public static String type="type";
	public static final String CUSTOM_PROFILE_ID="Profile_ID";
	public static final String PROFILE_NAME="Profile_Name";
	public static final String WIFI="Wifi";
	public static final String GPS="GPS";
	public static final String BLUETOOTH="Bluetooth";
	public static final String BRIGHTNESS="Brightness";
	public static final String RING_VOLUME="Ring_Volume";
	public static final String MEDIA_VOLUME="Media_Volume";
	public static final String ALARM_VOLUME="Alarm_Volume";
	public static final String NOTIFICATION_VOLUME="Notification_Volume";
	public static final String SET_PROFILE_TIME="set_profile_time";
	
	
	public static final Uri PHONE_URI=Uri.parse(AppConstants.PHONE_URI_STRING);
	public static final Uri SMS_URI=Uri.parse(AppConstants.SMS_URI_STRING);
	public static Uri CUSTOM_PROFILE_URI=Uri.parse(URI_CUSTOM_PROFILE_STRING);
	
	public static final String PHONE_ID="Phone_ID";
	public static final String PHONE_CONTACT_NUMBER="Number";
	public static final String PHONE_FREQUENCY="Frequency";
	public static final String PHONE_TIME_INTERVAL="Interval";
	public static final String PHONE_NAME="Contact_Name";

}
