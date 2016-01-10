package com.samsung.profilemanager.util;

import java.util.ArrayList;
import java.util.Date;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.CallModel;
import com.samsung.profilemanager.models.IModel;



public class PhoneUtil extends BaseUtil
{
	
	public static final String PHONE_TABLE="Phone_Table";
	
	//Attributes of the Table
	
	//URI for the table
	
	private Context mContext;
	private String TAG="PhoneTable";
	public PhoneUtil(Context context)
	{
		mContext=context;
	}
	public ArrayList<IModel> getServiceData(Date mDate)
	{
		ArrayList<IModel> resultList=new ArrayList<IModel>();
		CallModel mCallModel;
		Cursor resultCursor=mContext.getContentResolver().query(AppConstants.PHONE_URI, null, null, null, null);
		if(resultCursor!=null)
		{
			for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
			{
				int frequency=resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_FREQUENCY));
				long time=resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.PHONE_TIME_INTERVAL));
				//Date tempDate=new Date(time);
				long curTime=mDate.getTime();
				time=curTime-time;
				//Convert the time to days
				time=time/(1000*60*60*24);
				if( frequency>1 && time<2)
				{
					mCallModel=new CallModel();
					mCallModel.setId(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_ID)));
					mCallModel.setContactNumber(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PHONE_CONTACT_NUMBER)));
					mCallModel.setFrequency(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_FREQUENCY)));
					mCallModel.setCallTime(resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.PHONE_TIME_INTERVAL)));
					mCallModel.setContactName(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PHONE_NAME)));
					resultList.add(mCallModel);
					Log.i(TAG, "Record added "+mCallModel.getContactNumber()+" with time "+time);
				}
				else
				{
					Log.i(TAG,"Record rejected");
				}
			}
			resultCursor.close();
			
		}
		return resultList;
	}
	
	@Override
	public ArrayList<IModel> getData()
	{
		Log.i(TAG,"getData");
		CallModel mCallModel;
		ArrayList<IModel> resultList=new ArrayList<IModel>();
		Cursor resultCursor=mContext.getContentResolver().query(AppConstants.PHONE_URI, null, null, null, null);
		//retrieving the data from the cursor
		if(resultCursor!=null)
		{
			for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
			{
				mCallModel=new CallModel();
				
				mCallModel.setId(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_ID)));
				mCallModel.setContactNumber(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PHONE_CONTACT_NUMBER)));
				mCallModel.setFrequency(resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_FREQUENCY)));
				mCallModel.setCallTime(resultCursor.getLong(resultCursor.getColumnIndex(AppConstants.PHONE_TIME_INTERVAL)));
				mCallModel.setContactName(resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PHONE_NAME)));
				resultList.add(mCallModel);
			}
			resultCursor.close();
		}
		else
		{
			Log.i(TAG, "getData Cursor is null");
		}
		return resultList;
	}
	/*
	 *  This function is used to update the values in the database
	 */
	@Override
	public boolean updateData(IModel recievedData)
	{
		CallModel mCallModel=(CallModel)recievedData;
		ContentValues mContentValues=setContentValues(mCallModel);
		mContext.getContentResolver().update(AppConstants.PHONE_URI, mContentValues, null, null);
	
		return true;
	}
	
	/*
	 *This function is used to input the values from the model to the content provider 
	 */
	@Override
	public ContentValues setContentValues(IModel recieveData)
	{
		ContentValues updatedValues=new ContentValues();
		CallModel mCallModel=(CallModel)recieveData;
		updatedValues.put(AppConstants.PHONE_CONTACT_NUMBER,mCallModel.getContactNumber());
		updatedValues.put(AppConstants.PHONE_FREQUENCY,mCallModel.getFrequency());
		updatedValues.put(AppConstants.PHONE_TIME_INTERVAL,mCallModel.getCallTime());
		updatedValues.put(AppConstants.PHONE_NAME,mCallModel.getContactName());
		return updatedValues;
	}
	
	/*
	 * This function is used to delete a particular row from the database 
	 */
	@Override
	public boolean deleteData(IModel recieveData)
	{
		CallModel callModel=(CallModel)recieveData;
		String [] selectionArgs={callModel.getContactNumber()};
		int result = mContext.getContentResolver().delete(AppConstants.PHONE_URI, null, selectionArgs);
		if (result==0)
			return false;
		return true;
	}
}
