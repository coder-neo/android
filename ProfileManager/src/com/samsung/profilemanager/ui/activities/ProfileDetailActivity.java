package com.samsung.profilemanager.ui.activities;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.ProfileModel;
import com.samsung.profilemanager.util.CustomProfileUtil;

public class ProfileDetailActivity extends Activity implements OnClickListener
{
	private static final String	TAG	= "ProfileDetailActivity";
	private TextView wifi,bluetooth,brightness,ringtone,notification,profileName,mediaVolume,alarmVolume;
	private Button editButton,applyButton,deleteButton;
	private ProgressBar notificationBar,ringtoneBar,brightnessBar,alarmBar,mediaBar;
	private Bundle dataBundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_option);
		wifi=(TextView)findViewById(R.id.wifi);
		bluetooth=(TextView)findViewById(R.id.bluetooth);
		ringtone=(TextView)findViewById(R.id.ringtoneText);
		notification=(TextView)findViewById(R.id.notificationText);
		profileName=(TextView)findViewById(R.id.profileName);
		brightness=(TextView)findViewById(R.id.brightnessText);
		mediaVolume=(TextView)findViewById(R.id.mediaVolume);
		alarmVolume=(TextView)findViewById(R.id.alarmVolume);
		editButton=(Button)findViewById(R.id.editButton);
		applyButton=(Button)findViewById(R.id.applyButton);
		deleteButton=(Button)findViewById(R.id.deleteButton);
		notificationBar=(ProgressBar)findViewById(R.id.notificationDisplayBar);
		alarmBar=(ProgressBar)findViewById(R.id.alarmDisplayBar);
		ringtoneBar=(ProgressBar)findViewById(R.id.ringtoneDisplayBar);
		mediaBar=(ProgressBar)findViewById(R.id.mediaDisplayBar);
		brightnessBar=(ProgressBar)findViewById(R.id.brightnessDisplayBar);
		notificationBar.setMax(100);
		mediaBar.setMax(100);
		ringtoneBar.setMax(100);
		alarmBar.setMax(100);
		brightnessBar.setMax(255);
		editButton.setOnClickListener(this);
		applyButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		updateDataInViews();
	}
	private void updateDataInViews()
	{
		dataBundle=getIntent().getBundleExtra(AppConstants.dataBundle);
		if(dataBundle!=null)
		{
			if(dataBundle.getBoolean(AppConstants.WIFI))
			{
				wifi.setText("Enabled");
			}
			else
			{
				wifi.setText("Disabled");
			}
			if(dataBundle.getBoolean(AppConstants.BLUETOOTH))
			{
				bluetooth.setText("Enabled");
			}
			else
			{
				bluetooth.setText("Disabled");
			}
			
			profileName.setText(dataBundle.getString(AppConstants.PROFILE_NAME));
			brightness.setText(""+(dataBundle.getInt(AppConstants.BRIGHTNESS)*100/255));
			brightnessBar.setProgress(dataBundle.getInt(AppConstants.BRIGHTNESS));
			ringtoneBar.setProgress(dataBundle.getInt(AppConstants.RING_VOLUME));
			ringtone.setText(""+dataBundle.getInt(AppConstants.RING_VOLUME));
			notification.setText(""+dataBundle.getInt(AppConstants.NOTIFICATION_VOLUME));
			notificationBar.setProgress(dataBundle.getInt(AppConstants.NOTIFICATION_VOLUME));
			alarmVolume.setText(""+dataBundle.getInt(AppConstants.ALARM_VOLUME));
			alarmBar.setProgress(dataBundle.getInt(AppConstants.ALARM_VOLUME));
			mediaVolume.setText(""+dataBundle.getInt(AppConstants.MEDIA_VOLUME));
			mediaBar.setProgress(dataBundle.getInt(AppConstants.MEDIA_VOLUME));
		}
	}
	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
			case R.id.applyButton:
			{
				dataBundle=getIntent().getBundleExtra(AppConstants.dataBundle);
				setWifi(dataBundle.getBoolean(AppConstants.WIFI));
				setBluetooth(dataBundle.getBoolean(AppConstants.BLUETOOTH));
				setBrightness(dataBundle.getInt(AppConstants.BRIGHTNESS));
				setVolumes(dataBundle.getInt(AppConstants.RING_VOLUME), dataBundle.getInt(AppConstants.ALARM_VOLUME), dataBundle.getInt(AppConstants.NOTIFICATION_VOLUME), dataBundle.getInt(AppConstants.MEDIA_VOLUME));
				Log.i(TAG,"Profile set");
				Toast.makeText(ProfileDetailActivity.this, "Selected Profile Applied", Toast.LENGTH_LONG).show();
				Intent intent=new Intent(ProfileDetailActivity.this,ProfileActivity.class);
				startActivity(intent);
				break;
			}
			case R.id.deleteButton:
			{
				ProfileModel profileModel=new ProfileModel();
				dataBundle=getIntent().getBundleExtra(AppConstants.dataBundle);
				profileModel.setProfileName(dataBundle.getString(AppConstants.PROFILE_NAME));
				new CustomProfileUtil(ProfileDetailActivity.this).deleteData(profileModel);
				Toast.makeText(ProfileDetailActivity.this, "Profile deleted", Toast.LENGTH_LONG).show();
				finish();
				break;
			}
			case R.id.editButton:
			{
				dataBundle=getIntent().getBundleExtra(AppConstants.dataBundle);
				Intent editIntent=new Intent(ProfileDetailActivity.this,ProfileCreateActivity.class);
				editIntent.putExtra(AppConstants.dataBundle, dataBundle);
				startActivity(editIntent);
				break;
			}
		}
	}

	/*
	 * This function is used to set the state of the Wi-Fi based on the values set in profile
	 */
	private void setWifi(boolean isWifiEnabled)
	{
		WifiManager wifiManager = (WifiManager) ProfileDetailActivity.this.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(isWifiEnabled);
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
	 * This function is used to set the bluetooth based on the profile
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
		WindowManager.LayoutParams lp = getWindow().getAttributes();
	    lp.screenBrightness =(float) (brightnessLevel/(255*1.0));// 100 / 100.0f;
	    getWindow().setAttributes(lp);
	}
}
