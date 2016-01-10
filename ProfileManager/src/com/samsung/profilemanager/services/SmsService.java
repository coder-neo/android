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
import com.samsung.profilemanager.models.SmsModel;
import com.samsung.profilemanager.util.DateUtil;
import com.samsung.profilemanager.util.SmsUtil;
   
public class SmsService extends Service
{

	public static final String	TAG	= "SmsService";
	private SmsTimerTask mTimerTask;
	private Timer mTimer;
	private Date mTodayDate;
	private int SmsId=2000;
	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}
	
	@Override
	public void onCreate()
	{
		super.onCreate();
		Log.i("Service", "On create");
		mTimerTask=new SmsTimerTask();
		mTimer=new Timer();
		mTodayDate=new Date(System.currentTimeMillis());
		mTimer.schedule(mTimerTask, mTodayDate, 5000);
	}
	
	public class SmsTimerTask extends TimerTask
	{

		@Override
		public void run()
		{
			try
			{
				createSmsAlarm();
			}
			catch(Exception e)
			{
				Log.i(TAG, "Error in createSmsAlarm "+e.getMessage());
			}
		}
		
	}
	
	/*
	 * This function creates an alarm for the notification to be displayed by the system
	 */
	public void createSmsAlarm()
	{
		ArrayList<IModel> resultModel=new SmsUtil(SmsService.this).getServiceData(mTodayDate);
		for(int counter=0;counter<resultModel.size();counter++)    
		{
			Log.i(TAG,"Records Exist");
			SmsModel mSmsModel=(SmsModel)resultModel.get(counter);
			Intent intent=new Intent(SmsService.this,NotificationService.class);
			Bundle bundle=new Bundle();
			bundle.putString(AppConstants.sName, mSmsModel.getName());
			bundle.putString(AppConstants.sNum, mSmsModel.getNumber());
			bundle.putString(AppConstants.type, "Sms");
			intent.putExtra("mainBundle", bundle);
			int pId=counter+SmsId;
			PendingIntent pIntent=PendingIntent.getService(SmsService.this, pId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			AlarmManager alarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
			alarmManager.set(AlarmManager.RTC_WAKEUP, DateUtil.getTime(mSmsModel.getTimeRecieved()), pIntent);
			Log.i(TAG, "Record added "+mSmsModel.getNumber());
		}
		Log.i(TAG,"Running after 1 second");
	}
}
