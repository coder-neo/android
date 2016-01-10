package com.samsung.profiledbapplication.dbfiles;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.samsung.profiledbapplication.contstants.AppConstants;

public class DbHelper extends SQLiteOpenHelper
{
	String [] createStatements={AppConstants.Phone_createStatement,AppConstants.Sms_createStatement,	AppConstants.Custom_profile_createStatement};//,ProfileTracker.createStatementString};

	String [] dropStatements={AppConstants.Phone_dropStatement,AppConstants.Sms_dropStatement,AppConstants.Custom_profile_dropStatement}; //,CustomProfileTable.dropStatement,ProfileTracker.dropStatement};

	
	private final String TAG="DbHelper";
	
	public DbHelper(Context context, String name, CursorFactory factory,
			int version)
	{
		super(context, name, factory, version);
	
	}
	public DbHelper(Context context)
	{
		super(context,AppConstants.DB_NAME,null,AppConstants.VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{

		try
		{
			for(int j=0;j<createStatements.length;j++)
			{
				db.execSQL(createStatements[j]);
			}
	
			Log.i(TAG, "on create");
		}
		catch(Exception e)
		{
			Log.i(TAG,"onCreate Exception caused: "+e.getMessage());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{

		try
		{
			for(int j=0;j<dropStatements.length;j++)
			{
				db.execSQL(dropStatements[j]);
			}
			onCreate(db);
			Log.i(TAG, "On update");
		}
		catch(Exception e)
		{
			Log.i(TAG,"onUpgrade exception caused: "+e.getMessage());
		}
	}

}