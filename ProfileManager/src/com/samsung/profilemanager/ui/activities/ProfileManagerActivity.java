package com.samsung.profilemanager.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.observer.SmsObserver;
import com.samsung.profilemanager.services.CustomProfileService;
import com.samsung.profilemanager.services.PhoneService;
import com.samsung.profilemanager.services.SmsService;


public class ProfileManagerActivity extends Activity implements OnClickListener 
{

	private Button mCallButton;
	private Button mSmsButton;
	private Button mProfileButton;
	private String TAG="MainActivity";
	private final Uri SMS_URI=Uri.parse(AppConstants.SMS_CONTENT_URI);
	private SharedPreferences activityPref;
	private SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_manager_activity);
		mCallButton = (Button) findViewById(R.id.callButton);
		mSmsButton = (Button) findViewById(R.id.smsButton);
		mProfileButton = (Button) findViewById(R.id.profileButton);
		mCallButton.setOnClickListener(this);
		mSmsButton.setOnClickListener(this);
		mProfileButton.setOnClickListener(this);
		
		
		activityPref=getSharedPreferences("activityPref", MODE_PRIVATE);
		boolean isFirstTime=activityPref.getBoolean("isFirstTime", false);
		Log.i(TAG, "SharedPrefs "+isFirstTime);
		if(isFirstTime==false)
		{
			Log.i(TAG, "SharedPrefs");
			startServices();
			SmsObserver smsObserver = new SmsObserver(new Handler(),ProfileManagerActivity.this);
			getContentResolver().registerContentObserver(SMS_URI, true,smsObserver);
			editor=activityPref.edit();
			editor.putBoolean("isFirstTime", true);
			editor.commit();
		}
	}

	public void startServices()
	{
		Intent serviceIntent=new Intent(this,PhoneService.class);
		startService(serviceIntent);
		Intent smsServiceIntent=new Intent(this,SmsService.class);
		startService(smsServiceIntent);
		Intent customProfileService=new Intent(this,CustomProfileService.class);
		startService(customProfileService);
		Log.i(TAG,"Service intent fired");
	}
	@Override
	public void onClick(View v) 
	{
		try
		{
			switch(v.getId())
			{
				case R.id.callButton:
				{
					Log.i(TAG, "Inside call fn");
					Intent intent = new Intent(this, CallActivity.class);
					startActivity(intent);
					break;
				}
				case R.id.smsButton:   
				{
					Log.i(TAG,"inside sms function");
					Intent intent = new Intent(this, SmsActivity.class);
					startActivity(intent);
					break;
				}
				case R.id.profileButton:
				{
					Log.i(TAG,"inside profile function");
					Intent intent = new Intent(this, ProfileActivity.class);
					startActivity(intent);
					break;
				}
			}
		}
		catch(Exception e)
		{
			Toast.makeText(ProfileManagerActivity.this, "Under Implementation", Toast.LENGTH_LONG).show();
		}
	}
}
