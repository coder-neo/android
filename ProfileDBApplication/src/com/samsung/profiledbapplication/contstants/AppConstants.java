package com.samsung.profiledbapplication.contstants;



import android.net.Uri;

public class AppConstants
{
	public static final String CONTENT_PROVIDER="content://com.samsung.profiledbapplication.provider.DbContentProvider/";

	public static String DB_NAME="Prof_Manager_DB";
	public static int VERSION=1;
	
	
	//Attributes for Sms_Table
	public static final String SMS_ID="Sms_ID";
	public static final String SMS_CONTACT_NUMBER="Number";
	public static final String SMS_FREQUENCY="Frequency";
	public static final String SMS_TIME_INTERVAL="Time_recieved";
	public static final String SMS_NAME="Name";
			
	public static final String SMS_TABLE="Sms_Table";
			
			
			
	//create and drop statements
	public static String Sms_createStatement="Create table "+SMS_TABLE+" ( "+SMS_ID+" integer primary key autoincrement, "
					  													+SMS_CONTACT_NUMBER+" text not null, "
																		  +SMS_FREQUENCY+" integer not null, "
																		  +SMS_TIME_INTERVAL+" text not null, "
																		  +SMS_NAME+" text not null )";
		
	public static String Sms_dropStatement="drop table if exists "+SMS_TABLE;
			
	//Uri for the sms table
	public static final String URI_SMS_STRING=AppConstants.CONTENT_PROVIDER+SMS_TABLE;
	public static final Uri URI_SMS=Uri.parse(URI_SMS_STRING);
	public static String uriQuery=URI_SMS+"-query/#";
	public static final Uri URIQUERY=Uri.parse(uriQuery);
			
	//Phone table
	public static final String PHONE_TABLE="Phone_Table";
			
	//Attributes of the Table
	public static final String PHONE_ID="Phone_ID";
	public static final String PHONE_CONTACT_NUMBER="Number";
	public static final String PHONE_FREQUENCY="Frequency";
	public static final String PHONE_TIME_INTERVAL="Interval";
	public static final String PHONE_NAME="Contact_Name";
			
	//Create and drop table statement
	public static String Phone_createStatement="Create table "+PHONE_TABLE+" ( "+PHONE_ID+" integer primary key autoincrement, "
																			+PHONE_CONTACT_NUMBER+" text not null, "
																			+PHONE_FREQUENCY+" integer not null, "
																			+PHONE_TIME_INTERVAL+" text not null, "
																			+PHONE_NAME+" text not null )";
			
	public static String Phone_dropStatement="Drop table if exists "+PHONE_TABLE;
			  
	//URI for the table
	public static final String URI_PHONE_STRING=AppConstants.CONTENT_PROVIDER+PHONE_TABLE;
	public static final Uri URI_PHONE=Uri.parse(URI_PHONE_STRING);
	public static String URI_QUERY_STRING=URI_PHONE_STRING+"-query/#";
	public static final Uri URI_QUERY=Uri.parse(URI_QUERY_STRING);
		
	
	//Custom_prfile_table
	public static final String CUSTOM_PROFILE_TABLE="Custom_Profile_Table";
	
	//Attributes of the Table
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
	public static String Custom_profile_createStatement="Create table "+CUSTOM_PROFILE_TABLE+" ( "+CUSTOM_PROFILE_ID+" integer primary key autoincrement, "
			+PROFILE_NAME+" text not null, "
			+WIFI+" text not null, "
			+BLUETOOTH+" text not null, "
			+GPS+" text not null, "
			+BRIGHTNESS+" integer not null, "
			+RING_VOLUME+" integer not null, "
			+MEDIA_VOLUME+" integer not null, "
			+ALARM_VOLUME+" integer not null, "
			+NOTIFICATION_VOLUME+" integer not null, "
			+SET_PROFILE_TIME+" text)";

	public static String Custom_profile_dropStatement="Drop table if exists "+CUSTOM_PROFILE_TABLE;
	
	//URI for the table
	public static final String URI_CUSTOM_PROFILE_STRING=AppConstants.CONTENT_PROVIDER+CUSTOM_PROFILE_TABLE;
	public static final Uri URI_CUSTOM_PROFILE=Uri.parse(URI_CUSTOM_PROFILE_STRING);

	
	
}
