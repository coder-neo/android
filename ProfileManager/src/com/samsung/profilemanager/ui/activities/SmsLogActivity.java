package com.samsung.profilemanager.ui.activities;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.util.DateUtil;
import com.samsung.profilemanager.util.ProjectUtils;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SmsLogActivity extends Activity implements View.OnClickListener
{
	private String TAG="SmsLogActivity";
	private TextView mNumberTxt ;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		try
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.sms_layout);
			Log.i(TAG, "onCreate");

			TextView name  =(TextView) findViewById(R.id.contactName);
			mNumberTxt     =(TextView) findViewById(R.id.number);
			TextView time  =(TextView) findViewById(R.id.time);
			
			Bundle bundle =getIntent().getExtras();
			if(bundle==null)
			{
				Toast.makeText(SmsLogActivity.this, "No data found in bundle", Toast.LENGTH_LONG).show();
			}
			else
			{
				String phonenum = bundle.getString(AppConstants.sNum);
				String contactName = bundle.getString(AppConstants.sName);
				long callTime = bundle.getLong(AppConstants.sTime,0);
				//int frequency=bundle.getInt(CallAdapter.sFreq, 0);

				mNumberTxt.setOnClickListener(this);

				name.setText(contactName);
				mNumberTxt.setText(phonenum);
				time.setText((DateUtil.DateToString(callTime)));
				//freq.setText(""+frequency);
			}

		}
		catch(Exception e)
		{
			Log.i(TAG,"Exception in onCreate");
		}
		
	}
	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.number:
				ProjectUtils.openSmsScreen(mNumberTxt.getText().toString(), SmsLogActivity.this);
				 break;
		}
	}
}
