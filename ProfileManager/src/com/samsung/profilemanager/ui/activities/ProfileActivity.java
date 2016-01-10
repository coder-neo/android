package com.samsung.profilemanager.ui.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.samsung.profilemanager.R;
import com.samsung.profilemanager.models.IModel;
import com.samsung.profilemanager.ui.adapter.CustomProfileAdapter;
import com.samsung.profilemanager.util.CustomProfileUtil;

public class ProfileActivity extends Activity implements OnClickListener
{
 
	private CustomProfileAdapter mCustomProfileAdapter;
	private Button createProfile;
	private final String TAG="ProfileActivity";
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile_selector);
		ListView profileListView=(ListView)findViewById(R.id.profileList);
		createProfile=(Button)findViewById(R.id.createButton);
		createProfile.setOnClickListener(this);
		mCustomProfileAdapter=new CustomProfileAdapter(this);
		profileListView.setAdapter(mCustomProfileAdapter);
	}
	@Override
	protected void onResume()
	{
		try
		{
		super.onResume();
		fetchDataFromDB();
		}
		catch(Exception e)
		{
			Log.i(TAG,"onResume");
		}
	}
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.createButton:
			{
				Intent intent=new Intent(ProfileActivity.this,ProfileCreateActivity.class);
				startActivity(intent);
				break;
			}
		}
	}
	public void fetchDataFromDB()
	{
		ArrayList<IModel> mProfileList=new CustomProfileUtil(ProfileActivity.this).getData();
		mCustomProfileAdapter.setData(mProfileList);
		mCustomProfileAdapter.notifyDataSetChanged();
	}
}
