package com.samsung.profiledbapplication.dbfiles;

import android.content.ContentValues;
import android.database.Cursor;

import android.util.Log;

import com.samsung.profiledbapplication.contstants.AppConstants;



public class PhoneTable extends BaseTable
{
	
	private final String TAG="PhoneTable";
	
	@Override
	public Cursor getData()
	{
		Log.i(TAG,"getData");
		
		//Get the data from the database using the given condition
		String orderBy=AppConstants.PHONE_FREQUENCY+" desc, "+AppConstants.PHONE_TIME_INTERVAL+" asc";
		Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.PHONE_TABLE, null, null, null, null, null, orderBy);
		if(resultCursor==null)
		{
			Log.i(TAG, "Cursor is null");
		}
		return resultCursor;
	}
	
	/*
	 * This function is used to find if the present record already exists in the db or not
	 */
	@Override
	public Cursor isDuplicate(String number)
	{
		String [] projections = {AppConstants.PHONE_ID,AppConstants.PHONE_FREQUENCY};
		String selection=AppConstants.PHONE_CONTACT_NUMBER+"=?";
		String [] selectionArgs={number};
		Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.PHONE_TABLE, projections, selection, selectionArgs, null, null,null);
		if(resultCursor==null)
		{
			Log.i(TAG,"isDuplicate Cursor is null");
		}
		
		return resultCursor;
	}
	
	/*
	 *  This function is used to update the values in the database
	 */
	
	public int updateData(ContentValues recievedData)
	{
		
		Cursor resultCursor=isDuplicate(recievedData.getAsString(AppConstants.PHONE_CONTACT_NUMBER));
		int id=0;
		if(resultCursor.getCount()==0)
		{
			recievedData.put(AppConstants.PHONE_FREQUENCY,1);
			DbContentProvider.mDatabase.insert(AppConstants.PHONE_TABLE, null, recievedData);
		}
		else
		{
			for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
			{
			    id=resultCursor.getInt(resultCursor.getColumnIndex(AppConstants.PHONE_ID));
			    recievedData.put(AppConstants.PHONE_FREQUENCY, ( resultCursor.getInt ( resultCursor.getColumnIndex ( AppConstants.PHONE_FREQUENCY ) ) ) + 1);
				String whereClause=AppConstants.PHONE_ID+"=?";
				String [] whereArgs={id+""};
				id=DbContentProvider.mDatabase.update(AppConstants.PHONE_TABLE, recievedData, whereClause, whereArgs);
			}
		}
		
		return id;
	}
	
		
	/*
	 * This function is used to delete a particular row from the database 
	 */
	@Override
	public int deleteData(String[] phoneNumber)
	{
		
		String selection=AppConstants.PHONE_CONTACT_NUMBER+"=?";
		int result=DbContentProvider.mDatabase.delete(AppConstants.PHONE_TABLE, selection, phoneNumber);
		return result;
	}
	
}
