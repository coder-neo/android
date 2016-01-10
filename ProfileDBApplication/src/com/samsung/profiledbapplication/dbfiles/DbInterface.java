package com.samsung.profiledbapplication.dbfiles;

import android.content.ContentValues;
import android.database.Cursor;



public interface DbInterface
{
	public Cursor getData();
	public int updateData(ContentValues recieveData);
	public Cursor isDuplicate(String number);
	public int deleteData(String[] number);
}
