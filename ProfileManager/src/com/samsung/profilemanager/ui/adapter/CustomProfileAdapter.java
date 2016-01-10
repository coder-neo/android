package com.samsung.profilemanager.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.models.ProfileModel;
import com.samsung.profilemanager.ui.activities.ProfileDetailActivity;

public class CustomProfileAdapter extends BaseAdapter
{

	private ArrayList<IModel> mProfileList;
	private Context mContext;
	private String TAG = "CustomProfileAdapter";
	
	public CustomProfileAdapter(Context context)
	{
		mContext=context;
	}
	
	public void setData(ArrayList<IModel> listReceived) 
	{
		Log.i(TAG, "set data");
		mProfileList = listReceived;
	}

	@Override
	public int getCount() {
		return mProfileList == null ? 0 : mProfileList.size();
	}

	@Override
	public Object getItem(int position) 
	{
		return mProfileList == null ? 0 : mProfileList.get(position);
	}

	@Override
	public long getItemId(int position) 
	{
		return 0;
	}

	private class ViewHolder
	{
		TextView profileName;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		Log.i(TAG,"getView");
		ViewHolder holder = null;
		// This a new view we inflate the new layout
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.initial_profile_layout, null);
			holder = new ViewHolder();
			holder.profileName=(TextView)convertView.findViewById(R.id.profileName);
			convertView.setTag(holder);
		} 
		else 
		{
			holder = (ViewHolder) convertView.getTag();
		}

	    ProfileModel profile = (ProfileModel) mProfileList.get(position);
		if(profile==null)
		{
			return null;
		}

		holder.profileName.setText(profile.getProfileName());
		final boolean wifiState=profile.isWifiEnabled();
		final boolean bluetoothState=profile.isBluetoothEnabled();
		final int brightness=profile.getBrightness();
		final int ringtoneVolume=profile.getRingVolume();
		final int notificationVolume=profile.getNotficationVolume();
		final String profileName=profile.getProfileName();
		final int profileID=profile.getProfileID();
		final int alarmVolume=profile.getAlarmVolume();
		final int mediaVolume=profile.getMediaVolume();
		final long time=profile.getTimeActivated();
		
		holder.profileName.setOnClickListener(new View.OnClickListener() 
		{
			public void onClick(View view)
			{
				Log.i(TAG,"on click");
				Intent intent=new Intent(mContext, ProfileDetailActivity.class);
				Bundle dataBundle=new Bundle();
				dataBundle.putInt(AppConstants.CUSTOM_PROFILE_ID, profileID);
				dataBundle.putBoolean(AppConstants.WIFI,wifiState);
				dataBundle.putBoolean(AppConstants.BLUETOOTH,bluetoothState);
				dataBundle.putInt(AppConstants.BRIGHTNESS,brightness);
				dataBundle.putInt(AppConstants.RING_VOLUME,ringtoneVolume);
				dataBundle.putInt(AppConstants.NOTIFICATION_VOLUME,notificationVolume);
				dataBundle.putString(AppConstants.PROFILE_NAME, profileName);
				dataBundle.putInt(AppConstants.MEDIA_VOLUME, mediaVolume);
				dataBundle.putInt(AppConstants.ALARM_VOLUME, alarmVolume);
				dataBundle.putLong(AppConstants.PHONE_TIME_INTERVAL, time);
				intent.putExtra(AppConstants.dataBundle, dataBundle);
				mContext.startActivity(intent);

			}
		});
		
		return convertView;
	}

}
