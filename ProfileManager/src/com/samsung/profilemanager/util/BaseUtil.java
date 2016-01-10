package com.samsung.profilemanager.util;

import java.util.ArrayList;


import android.content.ContentValues;
import android.database.Cursor;

import com.samsung.profilemanager.models.IModel;


public class BaseUtil implements UtilInterface
{

	
	
	
	@Override
	public ArrayList<IModel> getData()
	{
		return null;
	}

	@Override
	public boolean updateData(IModel recieveData)
	{
		return false;
	}

	@Override
	public Cursor isDuplicate(String number)
	{
		return null;
	}

	@Override
	public boolean deleteData(IModel recieveData)
	{
		return false;
	}

	
	
	
	@Override
	public ContentValues setContentValues(IModel recieveData)
	{
		
		return null;
	}

}
