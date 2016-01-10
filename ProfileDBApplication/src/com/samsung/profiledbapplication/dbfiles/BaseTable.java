package com.samsung.profiledbapplication.dbfiles;

import android.content.ContentValues;
import android.database.Cursor;

public class BaseTable implements DbInterface
{
	
	@Override
	public Cursor getData()
	{
		return null;
	}

	@Override
	public int updateData(ContentValues recieveData)
	{
		return 0;
	}

	@Override
	public Cursor isDuplicate(String number)
	{
		return null;
	}

	

	@Override
	public int deleteData(String[] number)
	{
		// TODO Auto-generated method stub
		return 0;
	}

}
