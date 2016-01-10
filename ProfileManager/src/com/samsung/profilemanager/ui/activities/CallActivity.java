package com.samsung.profilemanager.ui.activities;

import java.util.ArrayList;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.ui.adapter.CallAdapter;
import com.samsung.profilemanager.util.PhoneUtil;
import com.samsung.profilemanager.util.UtilInterface;
import com.samsung.profilemanager.models.IModel;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import android.widget.ListView;

public class CallActivity extends Activity 
{
	private String TAG = "CallActivity";
	private CallAdapter mCallAdapter = new CallAdapter(this);

	protected void onCreate(Bundle savedInstanceState) 
	{

		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.call);
			Log.i(TAG, "onCreate");
			ListView callListView = (ListView) findViewById(R.id.callList);
			callListView.setAdapter(mCallAdapter);

			} 
		catch (Exception e) 
		{
			Log.i(TAG, "Exception in onCreate");
		}
	}

	@Override
	protected void onResume() {
		try {
			super.onResume();
			
			fetchDataFromDb();
		} catch (Exception e) {
			Log.i(TAG, "Exception in onResume");
		}
	}

	/*
	 * Function for fetching data from Database and setting it in Adapter
	 */
	private void fetchDataFromDb() {
		UtilInterface phoneTable = new PhoneUtil(CallActivity.this);
		ArrayList<IModel> callmodel = phoneTable.getData();
		mCallAdapter.setData(callmodel);
		mCallAdapter.notifyDataSetChanged();
	}
}
