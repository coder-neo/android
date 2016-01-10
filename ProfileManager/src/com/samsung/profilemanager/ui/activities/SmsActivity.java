package com.samsung.profilemanager.ui.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.ui.adapter.SmsAdapter;
import com.samsung.profilemanager.util.SmsUtil;
import com.samsung.profilemanager.util.UtilInterface;

public class SmsActivity extends Activity 
{
	private String TAG = "SmsActivity";
	private SmsAdapter mSmsAdapter;

	protected void onCreate(Bundle savedInstanceState) 
	{
		try 
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.sms);
			mSmsAdapter = new SmsAdapter(this);
			ListView smsListView = (ListView) findViewById(R.id.smsList);
			smsListView.setAdapter(mSmsAdapter);
			Log.i(TAG, "onCreate");

		}
		catch (Exception e) 
		{
			Log.i(TAG, "Exception in onCreate");
		}
	}
	@Override
	protected void onResume() 
	{
		try 
		{
			super.onResume();
			fetchDataFromDb();
		} 
		catch (Exception e) 
		{
			Log.i(TAG, "Exception in onResume");
		}
	}
	/*
	 * Function for fetching data from Database and setting it in Adapter
	 */
	private void fetchDataFromDb() 
	{
		UtilInterface smsTable = new SmsUtil(SmsActivity.this);
		ArrayList<IModel> smsmodel = smsTable.getData();
		mSmsAdapter.setData(smsmodel);
		mSmsAdapter.notifyDataSetChanged();
	}
}
