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
import com.samsung.profilemanager.models.CallModel;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.ui.activities.CallLogActivity;

public class CallAdapter extends BaseAdapter {
	private ArrayList<IModel> mCallList;
	private Context mContext;
	private String TAG = "InitialCallAdapter";
	
	public CallAdapter(Context context)

	{
		mContext = context;
	}
	/*
	 *  Function to receive Data in the Adapter 
	 */
	public void setData(ArrayList<IModel> listReceived) {
		Log.i(TAG, "set data");

		mCallList = listReceived;

	}

	@Override
	public int getCount() {
		return mCallList == null ? 0 : mCallList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCallList == null ? 0 : mCallList.get(position);
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	private class ViewHolder {

		TextView contactName;
		TextView contactFrequency;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder = null;

		// This a new view we inflate the new layout

		LayoutInflater inflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.initial_call_layout, null);
			holder = new ViewHolder();
			//holder.contactNum = (TextView) convertView.findViewById(R.id.number);
			holder.contactName = (TextView) convertView.findViewById(R.id.contactName);
			holder.contactFrequency=(TextView)convertView.findViewById(R.id.contactFrequency);
			//holder.time = (TextView) convertView.findViewById(R.id.time);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		CallModel contact = (CallModel) mCallList.get(position);
		if(contact==null)
		{
			return null;
		}

		if(!contact.getContactName().equals("Unknown Number"))
		{
		holder.contactName.setText(contact.getContactName());
		}
		else
		{
			holder.contactName.setText(contact.getContactNumber());
		}
		final String number=contact.getContactNumber();
		final long time=contact.getCallTime();
		final String name=contact.getContactName();
		final int freq=contact.getFrequency();
		holder.contactFrequency.setText("("+freq+")");
		holder.contactName.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view)
			{
				Log.i(TAG,"on click");
				Intent intent=new Intent(mContext, CallLogActivity.class);
				intent.putExtra(AppConstants.sNum,number);
				intent.putExtra(AppConstants.sName,name);
				intent.putExtra(AppConstants.sTime,time);
				intent.putExtra(AppConstants.sFreq,freq);
				mContext.startActivity(intent);

			}
		});
		return convertView;
	}
}
