package com.samsung.profilemanager.ui.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.models.SmsModel;

import com.samsung.profilemanager.ui.activities.SmsLogActivity;


public class SmsAdapter extends BaseAdapter 
{
	private ArrayList<IModel> mSmsAdapter;
	private Context mContext;
	private String TAG="Sms Adapter";

	public SmsAdapter(Context context)
	{
		mContext = context;
	}

	public void setData(ArrayList<IModel> listReceived) 
	{
		mSmsAdapter = listReceived;
		Log.i(TAG, "SetDAta");
	}

	@Override
	public int getCount() 
	{
		return mSmsAdapter==null ? 0 : mSmsAdapter.size();
		
	}

	@Override
	public Object getItem(int arg0) 
	{
		return mSmsAdapter==null ? null : mSmsAdapter.get(arg0);
		
	}
	
	@Override
	public long getItemId(int position) 
	{
		
		return 0;
	}
	
	
	private class ViewHolder 
	{
		//TextView contactNumber;
		TextView contactName;
		//TextView timeText;
		TextView frequency;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{
		
		ViewHolder holder = null;
		
		// This a new view we inflate the new layout
		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) 
		{
			convertView = inflater.inflate(R.layout.initial_sms_layout, null);
			holder = new ViewHolder();
			holder.contactName=(TextView)convertView.findViewById(R.id.smsName);
			holder.frequency=(TextView)convertView.findViewById(R.id.smsFrequency);
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		
		SmsModel contact = (SmsModel) mSmsAdapter.get(position);
		if(contact==null)
		{
			return null;
		}
		
		
		
		//setting the values of the new item in the list
		if(!contact.getName().equals("Unknown Number"))
		{
		holder.contactName.setText(contact.getName());
		}
		else
		{
			holder.contactName.setText(contact.getNumber());
		}
	
		holder.frequency.setText("("+contact.getFrequency()+")");
		final String number=contact.getNumber();
		final long time=contact.getTimeRecieved();
		final String name=contact.getName();
		final int freq=contact.getFrequency();
		
		holder.contactName.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view)
			{
				Intent intent=new Intent(mContext, SmsLogActivity.class);
				intent.putExtra(AppConstants.sNum,number);
				intent.putExtra(AppConstants.sName,name);
				intent.putExtra(AppConstants.sTime,time);
				intent.putExtra(AppConstants.sFreq,freq);
				mContext.startActivity(intent);
				
				/*
				ProjectUtils.openSmsScreen(phonenNumber, mContext);
				*/
			}
		});
		return convertView;
	}
	
}
