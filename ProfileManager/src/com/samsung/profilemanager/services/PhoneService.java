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
import com.samsung.profilemanager.models.CallModel;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.util.DateUtil;
import com.samsung.profilemanager.util.PhoneUtil;

public class PhoneService extends Service
{

	public static final String	TAG	= "PhoneService";
	private PhoneTimerTask mTimerTask;
	private Timer mTimer;
	private Date mDate;
	
	@Override
	public IBinder onBind(Intent arg0)
	{
		return null;
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i("Service", "On create");
		mTimerTask=new PhoneTimerTask();
		mTimer=new Timer();
		mDate=new Date(System.currentTimeMillis());
		mTimer.schedule(mTimerTask, mDate, 5000);
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return 0;
	}
	public class PhoneTimerTask extends TimerTask
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
		ArrayList<IModel> resultModel=new PhoneUtil(PhoneService.this).getServiceData(mDate);
		for(int counter=0;counter<resultModel.size();counter++)
		{
			Log.i(TAG,"Records Exist");
			CallModel mCallModel=(CallModel)resultModel.get(counter);
			Intent intent=new Intent(PhoneService.this,NotificationService.class);
			Bundle bundle=new Bundle();
			bundle.putString(AppConstants.sName, mCallModel.getContactName());
			bundle.putString(AppConstants.sNum, mCallModel.getContactNumber());
			bundle.putString(AppConstants.type,"Phone");
			intent.putExtra("mainBundle", bundle);
			PendingIntent pIntent=PendingIntent.getService(PhoneService.this, counter, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, DateUtil.getTime(mCallModel.getCallTime()), pIntent);
			Log.i(TAG, "Record added "+mCallModel.getContactNumber());
		}
		Log.i(TAG,"Running after 1 second");
	}
}
