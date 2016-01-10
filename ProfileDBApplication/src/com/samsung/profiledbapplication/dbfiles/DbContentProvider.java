package com.samsung.profiledbapplication.dbfiles;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;


import com.samsung.profiledbapplication.contstants.AppConstants;

public class DbContentProvider extends ContentProvider
{
	private static final UriMatcher URI_MATCHER;
	private static final int PHONE_TAB=1;
	private static final int PHONE_TAB_ID=2;
	private static final int SMS_TAB=3;
	private static final int SMS_TAB_ID=4;
	private static final int CUSTOM_PROF_TAB=5;
	private static DbHelper mDbHelper;
	public static SQLiteDatabase mDatabase;
	private final String TAG="DbContentProvider";
	public static final String PROVIDER="com.samsung.profiledbapplication.provider.DbContentProvider";
	
	static
	{
		URI_MATCHER=new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(PROVIDER,AppConstants.PHONE_TABLE , PHONE_TAB);
		URI_MATCHER.addURI(PROVIDER, AppConstants.PHONE_TABLE+"/#", PHONE_TAB_ID);
		URI_MATCHER.addURI(PROVIDER, AppConstants.SMS_TABLE, SMS_TAB);
		URI_MATCHER.addURI(PROVIDER,AppConstants.SMS_TABLE+"/#",SMS_TAB_ID);
		URI_MATCHER.addURI(PROVIDER, AppConstants.CUSTOM_PROFILE_TABLE, CUSTOM_PROF_TAB);
	}
	
	@Override
	public int delete(Uri receievedUri, String whereClause, String[] phoneNumber)
	{
		// TODO Auto-generated method stub
		int rowDeleted=0;
		switch(URI_MATCHER.match(receievedUri))
		{
			case PHONE_TAB:
			{
				rowDeleted=new PhoneTable().deleteData(phoneNumber);
				break;
			}
			case SMS_TAB:
			{
				rowDeleted=new SmsTable().deleteData(phoneNumber);
				break;
			}
			case CUSTOM_PROF_TAB:
			{
				rowDeleted=new CustomProfileTable().deleteData(phoneNumber);
				break;
			}
		}
		return rowDeleted;
	}

	@Override
	public String getType(Uri arg0)
	{
		return null;
	}

	@Override
	public Uri insert(Uri receievedUri, ContentValues receievedValues)
	{
		
		Uri itemUri=null;
		long id;
		switch(URI_MATCHER.match(receievedUri))
		{
			case PHONE_TAB:
			{
				id=mDatabase.insert(AppConstants.PHONE_TABLE, null, receievedValues);
				if(id>0)
				{
					itemUri=ContentUris.withAppendedId(receievedUri, id);
				}
				else
				{
					Log.e(TAG, "Error inserting in Phone Table");
				}
				break;
			}
			case SMS_TAB:
			{
				id=mDatabase.insert(AppConstants.SMS_TABLE, null, receievedValues);
				if(id>0)
				{
					itemUri=ContentUris.withAppendedId(receievedUri, id);
				}
				else
				{
					Log.e(TAG, "Error inserting in SMS table");
				}
				break;
			}
		}
		return itemUri;
	}

	@Override
	public boolean onCreate()
	{
		
		mDbHelper=new DbHelper(getContext());
		mDatabase=mDbHelper.getWritableDatabase();
		Log.i(TAG, "onCreate DB");
		if(mDatabase==null)
			Log.i(TAG,"DB NUll");
		return true;
	}

	@Override
	public Cursor query(Uri recievedUri, String[] projection, String selection, String[] selectionArgs,
			String orderBy)
	{
		
		Log.i(TAG,"query");
		Cursor resultCursor=null;
		switch(URI_MATCHER.match(recievedUri))
		{
			case PHONE_TAB:
			{
				Log.i(TAG, "Phone query");
				resultCursor=new PhoneTable().getData();
				return resultCursor;
			}
			case SMS_TAB:
			{
				Log.i(TAG, "Sms Query");
				resultCursor=new SmsTable().getData();
				return resultCursor;
			}
			case CUSTOM_PROF_TAB:
			{
				Log.i(TAG, "Custom Profile Query");
				resultCursor=new CustomProfileTable().getData();
				return resultCursor;
			}
		}
		return resultCursor;
	}

	@Override
	public int update(Uri receievedUri, ContentValues receivedValues, String whereClause, String[] whereArgs)
	{
		int rowsUpdated=0;
		switch(URI_MATCHER.match(receievedUri))
		{
			case PHONE_TAB:
			{
				rowsUpdated=new PhoneTable().updateData(receivedValues);
				break;
			}
			case SMS_TAB:
			{
				rowsUpdated=new SmsTable().updateData(receivedValues);
				break;
			}
			case CUSTOM_PROF_TAB:
			{
				Log.i("CustomProfileClass", "Update in"+TAG);
				rowsUpdated=new CustomProfileTable().updateData(receivedValues);
				break;
			}
		}
		return rowsUpdated;
	}

}
