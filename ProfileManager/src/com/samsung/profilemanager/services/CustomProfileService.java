package com.samsung.profilemanager.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.models.ProfileModel;
import com.samsung.profilemanager.util.CustomProfileUtil;
import com.samsung.profilemanager.util.DateUtil;

public class CustomProfileService extends Service {

	private CustomProfileTimerTask mTimerTask;
	private Timer mTimer;
	private Date mDate;
	private final String TAG="CustomProfileService";
	private int customId=1000;
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		Log.i(TAG, "On create");
		mTimerTask=new CustomProfileTimerTask();
		mTimer=new Timer();
		mDate=new Date(System.currentTimeMillis());
		mTimer.schedule(mTimerTask, mDate, 5000);
	}
	
	public class CustomProfileTimerTask extends TimerTask
	{
		@Override
		public void run()
		{
			try
			{
				createAlarm();
			}
			catch(Exception e)
			{
				Log.i(TAG, "Error in createAlarm "+e.getMessage());
			}
		}
	}
	
	public void createAlarm()
	{
		ArrayList<IModel> resultModel=new CustomProfileUtil(CustomProfileService.this).getServiceData();
		for(int counter=0;counter<resultModel.size();counter++)
		{
			Log.i(TAG,"Records Exist");
			ProfileModel mProfileModel=(ProfileModel)resultModel.get(counter);
			Intent intent=new Intent(CustomProfileService.this,NotificationService.class);
			Bundle bundle=new Bundle();
			bundle.putInt(AppConstants.CUSTOM_PROFILE_ID, mProfileModel.getProfileID());
			bundle.putString(AppConstants.PROFILE_NAME, mProfileModel.getProfileName());
			bundle.putBoolean(AppConstants.WIFI, mProfileModel.isWifiEnabled());
			bundle.putBoolean(AppConstants.GPS, mProfileModel.isGPSEnabled());
			bundle.putBoolean(AppConstants.BLUETOOTH, mProfileModel.isBluetoothEnabled());
			bundle.putInt(AppConstants.RING_VOLUME, mProfileModel.getRingVolume());
			bundle.putInt(AppConstants.MEDIA_VOLUME, mProfileModel.getMediaVolume());
			bundle.putInt(AppConstants.ALARM_VOLUME, mProfileModel.getAlarmVolume());
			bundle.putInt(AppConstants.NOTIFICATION_VOLUME, mProfileModel.getNotficationVolume());
			bundle.putString(AppConstants.type,"CustomProfile");
			intent.putExtra("mainBundle", bundle);
			int pendingIntentId=counter+customId;
			PendingIntent pIntent=PendingIntent.getService(CustomProfileService.this, pendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, DateUtil.getTime(mProfileModel.getTimeActivated()), pIntent);
			Log.i(TAG, "Record added "+mProfileModel.getProfileName());
		}
		Log.i(TAG,"Running after 1 second");
	}
}
