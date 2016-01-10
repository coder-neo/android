package com.samsung.profilemanager.recievers;

import java.util.Date;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.samsung.profilemanager.models.CallModel;
import com.samsung.profilemanager.util.PhoneUtil;
import com.samsung.profilemanager.util.UtilInterface;


public class CallReceiver extends BroadcastReceiver 
{
	public static String phoneNumber;
	public static String contactName;
	private final String TAG="CallReciever";
	//private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) 
	{
	 	 //to retrieve the phone number
		 phoneNumber=intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		 
		 //to retrieve the contact name
		 ContentResolver contentResolver = context.getContentResolver();
		 Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
	     Cursor resultCursor = contentResolver.query(uri,null,null,null, null);
	     if(resultCursor.moveToFirst())
	     {
	    	contactName=resultCursor.getString(resultCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
	     }
	     else
	     {
	     	contactName="Unknown Number";
	     }
	     resultCursor.close();
	     updateDatabase(phoneNumber, new Date().getTime(),contactName,context);
	 }
         
	
	
	//To update the values in the phone table
	public void updateDatabase(String phoneNo, long time,String contactName,Context context)
	{
		try
		{
			UtilInterface mPhoneTable=new PhoneUtil(context);
			CallModel mCallModel=new CallModel();
			mCallModel.setContactNumber(phoneNo);
			mCallModel.setContactName(contactName);
			mCallModel.setCallTime(time);
			mPhoneTable.updateData(mCallModel);
		}
		catch(Exception e)
		{
			Log.i(TAG, e+"");
		}
	}
	

}
