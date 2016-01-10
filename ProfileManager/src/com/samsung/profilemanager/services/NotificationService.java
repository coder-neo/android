package com.samsung.profilemanager.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;

public class NotificationService extends Service
{
	private Bundle bundle;
	private static int notificationId=1;
	private final String TAG="NotificationService";
	@Override
	public IBinder onBind(Intent arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		// TODO Auto-generated method stub
		bundle=intent.getBundleExtra("mainBundle");
		if(bundle==null)
		{
			Log.i(TAG,"Bundle is null");
		}
		else
		{
			
			String type=bundle.getString(AppConstants.type);
			if(type.equals("Phone"))
			{
				String Ticker=bundle.getString(AppConstants.sNum);
				String Heading=bundle.getString(AppConstants.sName);
				String Main=bundle.getString(AppConstants.sNum);
			createPhoneNotification(Ticker,Heading,Main);
			}
			else if(type.equals("Sms"))
			{
				String Ticker=bundle.getString(AppConstants.sNum);
				String Heading=bundle.getString(AppConstants.sName);
				String Main=bundle.getString(AppConstants.sNum);
				createSmsNotification(Ticker,Heading,Main);
			}
			else if(type.equals("CustomProfile"))
			{
				String Main=bundle.getString(AppConstants.PROFILE_NAME);
				String Heading="Profile Manager";
				String Ticker=Main+" Profile Applied";
				createProfileNotification(Ticker,Heading,Main);
				Log.i(TAG, Main+" Applied" );
			}
		}
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void createProfileNotification(String ticker, String title,
			String main) {
		
			setProfile();
			//Toast.makeText(this, main, Toast.LENGTH_SHORT).show();
			Log.i(TAG,main);
		// TODO Auto-generated method stub
		NotificationManager mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		String mainText="Profile Changed to "+main;
		PendingIntent pIntent=PendingIntent.getActivity(this, 0, new Intent(), 0);
		Notification mNotification=new Notification.Builder(this).setAutoCancel(true).setContentIntent(pIntent).
								   setContentText(mainText).setContentTitle(title).setTicker(ticker).setSmallIcon(R.drawable.app_icon).build();
		mNotificationManager.notify(notificationId++,mNotification);
	
	
	
	}
	private void setProfile() {
		// TODO Auto-generated method stub
		
		 	
			setWifi(bundle.getBoolean(AppConstants.WIFI));
			setBluetooth(bundle.getBoolean(AppConstants.BLUETOOTH));
			setBrightness(bundle.getInt(AppConstants.BRIGHTNESS));
			setVolumes(bundle.getInt(AppConstants.RING_VOLUME), bundle.getInt(AppConstants.ALARM_VOLUME), bundle.getInt(AppConstants.NOTIFICATION_VOLUME), bundle.getInt(AppConstants.MEDIA_VOLUME));
			Log.i(TAG,"Profile set");
			
		
	}
	public void createPhoneNotification(String ticker,String title,String main)
	{
		NotificationManager mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		String mainText="Do u want to call "+main+"?";
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + main));
		PendingIntent pIntent=PendingIntent.getActivity(this, 0, intent, 0);
		Notification mNotification=new Notification.Builder(this).setAutoCancel(true).setContentIntent(pIntent).
								   setContentText(mainText).setContentTitle(title).setTicker(ticker).setSmallIcon(R.drawable.app_icon).build();
		mNotificationManager.notify(notificationId++,mNotification);
	}
	

	public void createSmsNotification(String ticker,String title,String main)
	{
		NotificationManager mNotificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		String mainText="Do u want to send a message to "+main+"?";
		Uri uri=Uri.parse("smsto:"+main);
		Intent smsIntent=new Intent(Intent.ACTION_SENDTO,uri);
		
		PendingIntent pIntent=PendingIntent.getActivity(this, 0, smsIntent, 0);
		Notification mNotification=new Notification.Builder(this).setAutoCancel(true).setContentIntent(pIntent).
								   setContentText(mainText).setContentTitle(title).setTicker(ticker).setSmallIcon(R.drawable.app_icon).build();
		mNotificationManager.notify(notificationId++,mNotification);
	}

	/*
	 * This function is used to set the state of the Wi-Fi based on the values set in profile
	 */

	private void setWifi(boolean isWifiEnabled)
	{
		try
		{
			WifiManager wifiManager = (WifiManager) NotificationService.this.getSystemService(Context.WIFI_SERVICE);
			wifiManager.setWifiEnabled(isWifiEnabled);
		}
		catch(Exception e)
		{
			Log.i(TAG, "Exception in wifi "+e.getMessage());
		}
	}
	/*
	 * This function is used to set the Volume for the device based on the profile
	 */
	private void setVolumes(int ringtoneVolume,int alarmVolume,int notificationVolume,int mediaVolume)
	{
		AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		audioManager.setStreamVolume(AudioManager.STREAM_ALARM, alarmVolume*audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)/100,0);
		audioManager.setStreamVolume(AudioManager.STREAM_NOTIFICATION, notificationVolume*audioManager.getStreamMaxVolume(AudioManager.STREAM_NOTIFICATION)/100, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_RING, ringtoneVolume*audioManager.getStreamMaxVolume(AudioManager.STREAM_RING)/100, 0);
		audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mediaVolume*audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)/100, 0);
		
	}
	
	 	/*
		 * This function is used to set the Bluetooth based on the profile
		 */
		private void setBluetooth(boolean isBluetoothEnabled)
		{
			BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
			if(mBluetoothAdapter==null)
				{
				Log.i(TAG,"Device doesnt support bluetooth");
			}
			else
			{
				if(isBluetoothEnabled)
				{
					mBluetoothAdapter.enable();
				}
				else
				{
					mBluetoothAdapter.disable();
				}
			}
			
		}
		
		/*
		 * This function is used to set the brightness of the device
		 */
		
		private void setBrightness(int brightnessLevel)
		{
			android.provider.Settings.System.putInt(getContentResolver(),android.provider.Settings.System.SCREEN_BRIGHTNESS, brightnessLevel);
			
		}


}
