package com.samsung.profilemanager.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class ProjectUtils
{

	/**
	 * This function opens the SMS Application
	 * @param phoneNumber
	 * @param context
	 */
	public static void openSmsScreen(String phoneNumber,Context context)
	{
		Uri uri=Uri.parse("smsto:"+phoneNumber);
		Intent smsIntent=new Intent(Intent.ACTION_SENDTO,uri);
		context.startActivity(smsIntent);
	}
	/**
	 * This function opens the Dialer Screen
	 * @param phoneNumber
	 * @param context
	 */
	
	public static void openDialerScreen(String phoneNumber,Context context)
	{
		Intent intent = new Intent(Intent.ACTION_DIAL);
		intent.setData(Uri.parse("tel:" + phoneNumber));
		context.startActivity(intent);
	}
}
