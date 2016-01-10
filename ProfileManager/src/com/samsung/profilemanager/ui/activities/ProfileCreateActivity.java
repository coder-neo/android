package com.samsung.profilemanager.ui.activities;

import java.util.Calendar;
import android.app.Activity;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;
import com.samsung.profilemanager.R;
import com.samsung.profilemanager.constants.AppConstants;
import com.samsung.profilemanager.models.ProfileModel;
import com.samsung.profilemanager.util.CustomProfileUtil;
import com.samsung.profilemanager.util.UtilInterface;

public class ProfileCreateActivity extends Activity implements OnClickListener,OnCheckedChangeListener
{

    private static final int	TIMER_ID	= 999;
	//	private static final String	TAG	= "ProfileLogActivity";
	private Switch wifiSwitch,bluetoothSwitch;
	private SeekBar brightnessBar,ringtoneBar,notificationBar,alarmBar,mediaBar;
	private EditText profileEdit;
	private Button submitProfile;
	private CheckBox enableCheckBox;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.create_profile_layout);
		super.onCreate(savedInstanceState);
		wifiSwitch=(Switch)findViewById(R.id.wifiSwitch);
		bluetoothSwitch=(Switch)findViewById(R.id.bluetoothSwitch);
		brightnessBar=(SeekBar)findViewById(R.id.brightnessBar);
		notificationBar=(SeekBar)findViewById(R.id.notificationBar);
		ringtoneBar=(SeekBar)findViewById(R.id.ringtoneBar);
		mediaBar=(SeekBar)findViewById(R.id.mediaBar);
		alarmBar=(SeekBar)findViewById(R.id.alarmBar);
		submitProfile=(Button)findViewById(R.id.submitButton);
		enableCheckBox=(CheckBox)findViewById(R.id.enablecheckBox);
		submitProfile.setOnClickListener(this);
		enableCheckBox.setOnCheckedChangeListener(this);
		profileEdit=(EditText)findViewById(R.id.profileEditText);
		notificationBar.setMax(100);
		ringtoneBar.setMax(100);
		mediaBar.setMax(100);
		alarmBar.setMax(100);
		brightnessBar.setMax(255);
		Bundle dataBundle=getIntent().getBundleExtra("dataBundle");
		if(dataBundle!=null)
		{
			wifiSwitch.setChecked(dataBundle.getBoolean(AppConstants.WIFI));
			bluetoothSwitch.setChecked(dataBundle.getBoolean(AppConstants.BLUETOOTH));
			brightnessBar.setProgress(dataBundle.getInt(AppConstants.BRIGHTNESS));
			ringtoneBar.setProgress(dataBundle.getInt(AppConstants.RING_VOLUME));
			notificationBar.setProgress(dataBundle.getInt(AppConstants.NOTIFICATION_VOLUME));
			alarmBar.setProgress(dataBundle.getInt(AppConstants.ALARM_VOLUME));
			mediaBar.setProgress(dataBundle.getInt(AppConstants.MEDIA_VOLUME));
			profileEdit.setText(dataBundle.getString(AppConstants.PROFILE_NAME));
		}
	}
  
	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.submitButton:
			{
				ProfileModel mProfileModel = new ProfileModel();
				mProfileModel.setBluetoothEnabled(bluetoothSwitch.isChecked());
				mProfileModel.setWifiEnabled(wifiSwitch.isChecked());
				mProfileModel.setBrightness(brightnessBar.getProgress());
				mProfileModel.setRingVolume(ringtoneBar.getProgress());
				mProfileModel.setNotficationVolume(notificationBar.getProgress());
				mProfileModel.setProfileName(profileEdit.getText().toString());
				mProfileModel.setMediaVolume(mediaBar.getProgress());
				mProfileModel.setAlarmVolume(alarmBar.getProgress());
				if(calendar!=null)
				{
					mProfileModel.setTimeActivated(calendar.getTimeInMillis());
				}
				UtilInterface mProfClass = new CustomProfileUtil(ProfileCreateActivity.this);
				mProfClass.updateData(mProfileModel);
				Toast.makeText(ProfileCreateActivity.this, "Profile Saved", Toast.LENGTH_LONG).show();
				finish();
				break;
			}	
		}
	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		switch(buttonView.getId())
		{
			case R.id.enablecheckBox:
			{
				if(isChecked)
				{
					showDialog(TIMER_ID);
					//new TimePickerDialog(ProfileCreateActivity.this, callBack, hourOfDay, minute, is24HourView)
				}
			}
		}
	}
	
	protected Dialog onCreateDialog(int id) 
	{
		switch (id) 
		{
		case TIMER_ID:
			// set time picker as current time
			calendar=Calendar.getInstance();
			return new TimePickerDialog(this,timePickerListener, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),false);
 		}
		return null;
	}
	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() 
	{
		public void onTimeSet(TimePicker view, int selectedHour,int selectedMinute) 
		{
			calendar.set(Calendar.HOUR_OF_DAY, selectedHour);
			calendar.set(Calendar.MINUTE,selectedMinute);
 			Toast.makeText(ProfileCreateActivity.this, "Time set with hour "+calendar.get(Calendar.HOUR_OF_DAY)+" and min "+calendar.get(Calendar.MINUTE), Toast.LENGTH_LONG).show(); 
		}
	};
}
