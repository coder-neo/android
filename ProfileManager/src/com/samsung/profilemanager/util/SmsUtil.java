package com.samsung.profilemanager.util;

import java.util.ArrayList;
import java.util.Date;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.models.SmsModel;

public class SmsUtil extends BaseUtil
{

		//Attributes for Sms_Table
		public static final String SMS_ID="Sms_ID";
		public static final String SMS_CONTACT_NUMBER="Number";
		public static final String SMS_FREQUENCY="Frequency";
		public static final String SMS_TIME_INTERVAL="Time_recieved";
		public static final String SMS_NAME="Name";
		
		public static final String SMS_TABLE="Sms_Table";
		
		private static String TAG="SMS TABLE";
		
		private Context mContext;
		private final Uri SMS_URI=Uri.parse(AppConstants.SMS_URI_STRING);
		
		public SmsUtil(Context context)
		{
			mContext=context;
		}
		
		/*
		 * This function is used to get the data for the service
		 */
		public ArrayList<IModel> getServiceData(Date mDate)
		{
			ArrayList<IModel> resultList=new ArrayList<IModel>();
			SmsModel mSmsModel;
			Cursor resultCursor=mContext.getContentResolver().query(AppConstants.SMS_URI, null, null, null, null);
			if(resultCursor!=null)
			{
				for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
				{
					int frequency=resultCursor.getInt(resultCursor.getColumnIndex(SMS_FREQUENCY));
					long time=resultCursor.getLong(resultCursor.getColumnIndex(SMS_TIME_INTERVAL));
					
					//Date tempDate=new Date(time);
					long curTime=mDate.getTime();
					time=curTime-time;
					
					//Convert the time to days
					time=time/(1000*60*60*24);
					if( frequency>1 && time<2)
					{
						mSmsModel=new SmsModel();
						mSmsModel.setId(resultCursor.getInt(resultCursor.getColumnIndex(SMS_ID)));
						mSmsModel.setNumber(resultCursor.getString(resultCursor.getColumnIndex(SMS_CONTACT_NUMBER)));
						mSmsModel.setFrequency(resultCursor.getInt(resultCursor.getColumnIndex(SMS_FREQUENCY)));
						mSmsModel.setTimeRecieved(resultCursor.getLong(resultCursor.getColumnIndex(SMS_TIME_INTERVAL)));
						mSmsModel.setName(resultCursor.getString(resultCursor.getColumnIndex(SMS_NAME)));
						resultList.add(mSmsModel);
						Log.i(TAG, "Record added "+mSmsModel.getNumber()+" with time "+time);
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
		
		/*
		 *This function is used to retrieve all the values from the database and return it as an array list 
		 */
		
		@Override
		public ArrayList<IModel> getData()
		{
			SmsModel mSmsModel;
			ArrayList<IModel> resultList=new ArrayList<IModel>();
			
			//Get the data from the database
			Cursor resultCursor=mContext.getContentResolver().query(SMS_URI, null, null, null, null);
			
			//retrieving the data from the cursor
			if(resultCursor!=null)
			{
				for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
				{
					mSmsModel=new SmsModel();
					
					mSmsModel.setId(resultCursor.getInt(resultCursor.getColumnIndex(SMS_ID)));
					mSmsModel.setNumber(resultCursor.getString(resultCursor.getColumnIndex(SMS_CONTACT_NUMBER)));
					mSmsModel.setFrequency(resultCursor.getInt(resultCursor.getColumnIndex(SMS_FREQUENCY)));
					mSmsModel.setTimeRecieved(resultCursor.getLong(resultCursor.getColumnIndex(SMS_TIME_INTERVAL)));
					mSmsModel.setName(resultCursor.getString(resultCursor.getColumnIndex(SMS_NAME)));
					resultList.add(mSmsModel);
				}
				Log.i(TAG, "getData");
				resultCursor.close();
			}
			else
			{
				Log.i(TAG, "getData cursor is null");
			}
			return resultList;
		}
		
		@Override
		public boolean updateData(IModel recievedData)
		{
			Log.i(TAG, "UpdateData");
			SmsModel mSmsModel=(SmsModel)recievedData;
			ContentValues mContentValues=setContentValues(mSmsModel);
			Log.i(TAG,"ContentValues set");
			mContext.getContentResolver().update(SMS_URI, mContentValues, null, null);
			return true;
		}
		
		/*
		 *This function is used to input the values from the model to the content provider 
		 */
		@Override
		public ContentValues setContentValues(IModel recieveData)
		{
			ContentValues updatedValues=new ContentValues();
			SmsModel mSmsModel=(SmsModel)recieveData;
			updatedValues.put(SMS_CONTACT_NUMBER,mSmsModel.getNumber());
			updatedValues.put(SMS_FREQUENCY,mSmsModel.getFrequency());
			updatedValues.put(SMS_TIME_INTERVAL,mSmsModel.getTimeRecieved());
			updatedValues.put(SMS_NAME,mSmsModel.getName());
			return updatedValues;
		}
		
		public boolean deleteData(IModel recieveData)
		{
			SmsModel smsModel=(SmsModel)recieveData;
			String [] selectionArgs={smsModel.getNumber()};
			int result = mContext.getContentResolver().delete(SMS_URI, null, selectionArgs);
			if (result==0)
				return false;
			return true;
		}
	
}
