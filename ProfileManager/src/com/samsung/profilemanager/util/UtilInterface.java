package com.samsung.profilemanager.util;

import java.util.ArrayList;

import android.content.ContentValues;
import android.database.Cursor;

import com.samsung.profilemanager.models.IModel;

public interface UtilInterface
{
	public ArrayList<IModel> getData();
	public boolean updateData(IModel recieveData);
	public Cursor isDuplicate(String number);
	public ContentValues setContentValues(IModel recieveData);
	public boolean deleteData(IModel recieveData);
}
