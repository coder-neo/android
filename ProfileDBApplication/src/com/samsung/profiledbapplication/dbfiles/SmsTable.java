package com.samsung.profiledbapplication.dbfiles;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import com.samsung.profiledbapplication.contstants.AppConstants;

public class SmsTable extends BaseTable
{
		private static String TAG="SMS TABLE";
		
		@Override
		public Cursor getData()
		{
			Log.i(TAG,"getData");
			//Get the data from the database using the given condition
			String orderBy=AppConstants.SMS_FREQUENCY+" desc, "+AppConstants.SMS_TIME_INTERVAL+" asc";
			Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.SMS_TABLE, null, null, null, null, null, orderBy);
			if(resultCursor==null)
			{
				Log.i(TAG,"getData cursor is null");
			}
			return resultCursor;
		}
		
		@Override
		public Cursor isDuplicate(String number)
		{
			String [] projections = {AppConstants.SMS_ID,AppConstants.SMS_FREQUENCY};
			String selection=AppConstants.SMS_CONTACT_NUMBER+"=?";
			String [] selectionArgs={number};
			Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.SMS_TABLE, projections, selection, selectionArgs, null, null,null);
			if(resultCursor==null)
			{
				Log.i(TAG,"isDuplicate cursor is null");
			}
			return resultCursor;
		}
		
		public int updateData(ContentValues recievedData)
		{
			
			Cursor resultCursor=isDuplicate(recievedData.getAsString(AppConstants.SMS_CONTACT_NUMBER));
			int id=0;
			if(resultCursor.getCount()==0)
			{
				recievedData.put(AppConstants.SMS_FREQUENCY,1);
				DbContentProvider.mDatabase.insert(AppConstants.SMS_TABLE, null, recievedData);
			}
			else
			{
				for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
				{
				    id=resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.SMS_ID));
				    recievedData.put(AppConstants.SMS_FREQUENCY, ( resultCursor.getInt ( resultCursor.getColumnIndex ( AppConstants.SMS_FREQUENCY ) ) ) + 1);
					String whereClause=AppConstants.SMS_ID+"=?";
					String [] whereArgs={id+""};
					
					id=DbContentProvider.mDatabase.update(AppConstants.SMS_TABLE, recievedData, whereClause, whereArgs);
				}
			}
			
			return id;
		}
		
		@Override
		public int deleteData(String[] phoneNumber)
		{
			
			String selection=AppConstants.SMS_CONTACT_NUMBER+"=?";
			int result=DbContentProvider.mDatabase.delete(AppConstants.SMS_TABLE, selection, phoneNumber);
			return result;
		}
}
