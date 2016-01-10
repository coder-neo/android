package com.samsung.profilemanager.observer;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;


import com.samsung.profilemanager.models.SmsModel;
import com.samsung.profilemanager.util.SmsUtil;

public class SmsObserver extends ContentObserver {

	private Context mContext;

	private String mContactName;
	static final Uri SMS_STATUS_URI = Uri.parse("content://sms");
	private static final String TAG = "SmsObserver";
	private Cursor sms_sent_cursor;
	

	public SmsObserver(Handler handler, Context ctx) 
	{
		super(handler);
		Log.i(TAG, "Sms Observer fired");
		mContext = ctx;
	}

	public boolean deliverSelfNotifications() 
	{
		return true;
	}

	public void onChange(boolean selfChange) 
	{  
		try
		{  
			Log.e("SMSObserver","Notification on SMS observer");  
			sms_sent_cursor = mContext.getContentResolver().query(SMS_STATUS_URI, null, null,null, null);  
			int total_sms_sent = 0 ;
			
			if (sms_sent_cursor != null && sms_sent_cursor.getCount()>0) 
			{  
				//checking total sms sent 
				while(sms_sent_cursor.moveToNext())
				{
					String protocol= sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("protocol"));
					if(protocol == null) 
						total_sms_sent++;
				}
				
				//checking if any new sms has been sent
				SharedPreferences pref =  mContext.getApplicationContext().getSharedPreferences("SMS_Count_Pref", 0);
				int sms_total_count = pref.getInt("TOTAL_SMS_SENT", 0);
				
				Log.e("SMSObserver sms_total_count", sms_total_count+"");
				Log.e("total_sms_sent", total_sms_sent+"");
				if(sms_total_count >= total_sms_sent)
					return;
				
				//if new sms has been sent
				if (sms_sent_cursor.isFirst() || sms_sent_cursor.moveToFirst()) 
				{  
					String protocol = "";
					//move the cursor to the last send sms in the sms uri
					while(protocol != null)
					{
						protocol= sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("protocol"));
						if(!sms_sent_cursor.isLast() && protocol!= null) 
							sms_sent_cursor.moveToNext();
					}
					//for send  protocol is null  
					if(protocol == null)
					{  
						int type = sms_sent_cursor.getInt(sms_sent_cursor.getColumnIndex("type"));  
						Log.e("Info","SMS Type : " + type);  
						// for actual state type=2  
						//if(type == 2)
						{  
							String smsBodyStr = sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("body")).trim();  
							String phoneNoStr = sms_sent_cursor.getString(sms_sent_cursor.getColumnIndex("address")).trim();  
							long smsDatTime = sms_sent_cursor.getLong(sms_sent_cursor.getColumnIndex("date")); 
							
							
							if(null != phoneNoStr && phoneNoStr.contains("-"))
								phoneNoStr = phoneNoStr.replaceAll("-", "");
							
							Log.e("Info","SMS Content : "+smsBodyStr);  
							Log.e("Info","SMS Phone No : "+phoneNoStr);  
							Log.e("Info","SMS Time : "+smsDatTime);  
							ContentResolver contentResolver = mContext.getContentResolver();
							Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNoStr));
						    Cursor resultCursor = contentResolver.query(uri,null,null,null, null);
						    if(resultCursor.moveToFirst())
						    {
						    	mContactName=resultCursor.getString(resultCursor.getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
						    }
						    else
						    {
						     	mContactName="Unknown Number";
						    }
						     resultCursor.close();
						     SmsModel smsModel = new SmsModel();
						     smsModel.setName(mContactName);
							 smsModel.setNumber(phoneNoStr);
							 smsModel.setFrequency(1);
							 smsModel.setTimeRecieved(smsDatTime > 0 ? smsDatTime : System.currentTimeMillis());
								
							 //inserting new sms sent data in SMS table
							 SmsUtil smsTable = new SmsUtil(mContext);
							 smsTable.updateData(smsModel);
								
							//updateData(phoneNoStr, smsDatTime, mContactName);
						}  
					}  
				} 
			}  
			else Log.e("Info","Send Cursor is Empty");  
			
			//updating new  total sms sent count in shared pref to check if any new sms has been sent  
			SharedPreferences sharedpref =  mContext.getApplicationContext().getSharedPreferences("SMS_Count_Pref", 0);
			final Editor editor = sharedpref.edit();
			editor.putInt("TOTAL_SMS_SENT", total_sms_sent);
			editor.commit();
			
		}
		catch(Exception ex)
		{  Log.e("SMSObserver ", "Error on onChange : "+ex);  	}
		finally
		{
			if(sms_sent_cursor!=null)
				sms_sent_cursor.close();
		}
		super.onChange(selfChange);  
	}

	
 // The function updates the Sms Database
	/*
	public void updateData(String phoneNumber, long time, String name) {
		Log.i(TAG, "Updating the database");
		SmsModel smsModel = new SmsModel();
		smsModel.setNumber(phoneNumber);
		smsModel.setTimeRecieved(time);
		smsModel.setName(name);
		DbInterface mDbInterface = new SmsClass(mContext);
		mDbInterface.updateData(smsModel);
	}
	*/
}