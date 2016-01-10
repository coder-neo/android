package com.samsung.profilemanager.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.models.ProfileModel;

public class CustomProfileUtil extends BaseUtil{
	public static final String CUSTOM_PROFILE_TABLE="Custom_Profile_Table";
	private Context mContext;
	private String TAG="CustomProfileClass";
	
	public CustomProfileUtil(Context context) {
		// TODO Auto-generated constructor stub
		mContext=context;
	}
	
	//Check for time not equal to 0
	public ArrayList<IModel> getServiceData() 
	{
		int id=0;
		ArrayList<IModel> resultList=new ArrayList<IModel>();
		ProfileModel mProfileModel;
		String selection=AppConstants.SET_PROFILE_TIME+" <> ?";
		String [] selectionArgs={""+id};
		Cursor resultCursor=mContext.getContentResolver().query(AppConstants.CUSTOM_PROFILE_URI, null, selection, selectionArgs, null);
		//Log.i(TAG,"Service Data No of records "+resultCursor.getCount());
		for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
		{
			if(resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.SET_PROFILE_TIME))!=0)
			{
				mProfileModel=new ProfileModel();
				mProfileModel.setProfileID(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.CUSTOM_PROFILE_ID)));
				mProfileModel.setProfileName(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PROFILE_NAME)));
				mProfileModel.setBluetoothEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.BLUETOOTH))));
				mProfileModel.setWifiEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.WIFI))));
				mProfileModel.setGPSEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.GPS))));
				mProfileModel.setMediaVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.MEDIA_VOLUME)));
				mProfileModel.setNotficationVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.NOTIFICATION_VOLUME)));
				mProfileModel.setRingVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.RING_VOLUME)));
				mProfileModel.setAlarmVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.ALARM_VOLUME)));
				mProfileModel.setTimeActivated(resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.SET_PROFILE_TIME)));
				resultList.add(mProfileModel);
				Log.i(TAG,"Service record added "+mProfileModel.getProfileName());
			}
		}
		
		return resultList;
	}
	@Override
	public ArrayList<IModel> getData() {
		// TODO Auto-generated method stub
		ProfileModel mProfileModel;
		Log.i(TAG, "Get data called");
		ArrayList<IModel> resultList=new ArrayList<IModel>();
		Cursor resultCursor=mContext.getContentResolver().query(AppConstants.CUSTOM_PROFILE_URI, null, null, null, null);
		//retrieving the data from the cursor
		if(resultCursor!=null)
		{
			Log.i(TAG, "No of records "+resultCursor.getCount());
			for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
			{
				mProfileModel=new ProfileModel();
				mProfileModel.setProfileID(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.CUSTOM_PROFILE_ID)));
				mProfileModel.setProfileName(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PROFILE_NAME)));
				mProfileModel.setWifiEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.WIFI))));
				mProfileModel.setBluetoothEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.BLUETOOTH))));
				mProfileModel.setGPSEnabled(Boolean.parseBoolean(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.GPS))));
				mProfileModel.setBrightness(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.BRIGHTNESS)));
				mProfileModel.setRingVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.RING_VOLUME)));
				mProfileModel.setMediaVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.MEDIA_VOLUME)));
				mProfileModel.setNotficationVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.NOTIFICATION_VOLUME)));
				mProfileModel.setAlarmVolume(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.ALARM_VOLUME)));
				mProfileModel.setTimeActivated(resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.SET_PROFILE_TIME)));
				resultList.add(mProfileModel);
				Log.i(TAG, "Profile record added "+mProfileModel.getProfileName());
			}
			resultCursor.close();
		}
		else
		{
			Log.i(TAG, "getData Cursor is null");
		}
		return resultList;
	}
	
	
	@Override
	public ContentValues setContentValues(IModel recieveData) 
	{
		ContentValues updatedValues=new ContentValues();
		ProfileModel mProfileModel =(ProfileModel)recieveData;
		updatedValues.put(AppConstants.PROFILE_NAME, mProfileModel.getProfileName());
		updatedValues.put(AppConstants.WIFI, String.valueOf(mProfileModel.isWifiEnabled()));
		updatedValues.put(AppConstants.GPS, "false");
		updatedValues.put(AppConstants.BLUETOOTH, String.valueOf(mProfileModel.isBluetoothEnabled()));
		updatedValues.put(AppConstants.BRIGHTNESS, mProfileModel.getBrightness());
		updatedValues.put(AppConstants.RING_VOLUME, mProfileModel.getRingVolume());
		updatedValues.put(AppConstants.MEDIA_VOLUME, mProfileModel.getMediaVolume());
		updatedValues.put(AppConstants.NOTIFICATION_VOLUME, mProfileModel.getNotficationVolume());
		updatedValues.put(AppConstants.ALARM_VOLUME, mProfileModel.getAlarmVolume());
		updatedValues.put(AppConstants.SET_PROFILE_TIME,mProfileModel.getTimeActivated());
		return updatedValues;
		
	}
	@Override
	public boolean updateData(IModel recievedData) 
	{
		ProfileModel mProfileModel=(ProfileModel)recievedData;
		ContentValues mContentValues=setContentValues(mProfileModel);
		mContext.getContentResolver().update(AppConstants.CUSTOM_PROFILE_URI, mContentValues, null, null);
		Log.i(TAG, "UpdateData function in"+TAG);
		return true;
	}
	@Override
	public boolean deleteData(IModel recieveData) 
	{
		ProfileModel mProfileModel=(ProfileModel)recieveData;
		String [] selectionArgs={mProfileModel.getProfileName()};
		int result = mContext.getContentResolver().delete(AppConstants.CUSTOM_PROFILE_URI, null, selectionArgs);
		if (result==0)
			return false;
		return true;
	}

}
