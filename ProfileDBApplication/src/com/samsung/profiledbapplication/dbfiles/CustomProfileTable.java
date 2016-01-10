package com.samsung.profiledbapplication.dbfiles;

import com.samsung.profiledbapplication.contstants.AppConstants;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

public class CustomProfileTable extends BaseTable {
	
	private final String TAG="CustomProfileTable"; 
	
	@Override
	public Cursor getData() 
	{
		// TODO Auto-generated method stub
		Log.i(TAG,"getData");
		String orderBy=AppConstants.PROFILE_NAME;
		Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.CUSTOM_PROFILE_TABLE, null, null, null, null, null, orderBy);
		if(resultCursor==null)
		{
			Log.i(TAG, "Cursor is null");
		}
		return resultCursor;
		
	}
	
	@Override
	public Cursor isDuplicate(String profile_name) 
	{
		Log.i(TAG,"isDuplicate called");
		String [] projections = {AppConstants.PROFILE_NAME};
		String selection=AppConstants.PROFILE_NAME+"=?";
		String [] selectionArgs={profile_name};
		Cursor resultCursor=DbContentProvider.mDatabase.query(AppConstants.CUSTOM_PROFILE_TABLE, projections, selection, selectionArgs, null, null,null);
		if(resultCursor==null)
		{
			Log.i(TAG,"isDuplicate Cursor is null");
		}
		Log.i(TAG,"isDuplicate completed");
		return resultCursor;
	}
	@Override
	public int updateData(ContentValues recievedData) 
	{
		// TODO Auto-generated method stub
		Log.i("CustomProfileClass","Update in"+TAG);
		Cursor resultCursor=isDuplicate(recievedData.getAsString(AppConstants.PROFILE_NAME));
		String profileName;
		int id=0;
		if(resultCursor.getCount()==0)
		{
			DbContentProvider.mDatabase.insert(AppConstants.CUSTOM_PROFILE_TABLE, null, recievedData);
		}
		else
		{
			for(resultCursor.moveToFirst();!resultCursor.isAfterLast();resultCursor.moveToNext())
			{
			    profileName=resultCursor.getString(resultCursor.getColumnIndex(AppConstants.PROFILE_NAME));
			    String whereClause=AppConstants.PROFILE_NAME+"=?";
				String [] whereArgs={profileName};
				id=DbContentProvider.mDatabase.update(AppConstants.CUSTOM_PROFILE_TABLE, recievedData, whereClause, whereArgs);
			}
		}
		return id;
	}
	@Override
	public int deleteData(String[] name) 
	{
		String selection=AppConstants.PROFILE_NAME+"=?";
		int result=DbContentProvider.mDatabase.delete(AppConstants.CUSTOM_PROFILE_TABLE, selection, name);
		return result;
	}

}
