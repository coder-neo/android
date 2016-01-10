package com.samsung.profilemanager.util;

import java.util.Calendar;

import android.text.format.DateFormat;

public class DateUtil
{
	/*
	 * This method is used to convert the date in milliseconds to readable format
	 */
	public static CharSequence DateToString(long time)
	{
		return DateFormat.format("MM/dd/yy h:mmaa",time);
	}

	/*
	 * This method is used to get the time for the next notification to be displayed
	 */
	public static long getTime(long time)
	{
		Calendar calendar=Calendar.getInstance();
		Calendar mCalendar=Calendar.getInstance();
		mCalendar.setTimeInMillis(time);
		calendar.set(Calendar.HOUR_OF_DAY, mCalendar.get(Calendar.HOUR_OF_DAY));
		calendar.set(Calendar.MINUTE, mCalendar.get(Calendar.MINUTE));
		calendar.set(Calendar.SECOND, mCalendar.get(Calendar.SECOND));
		calendar.set(Calendar.MILLISECOND,0);
		if(calendar.getTimeInMillis()<System.currentTimeMillis())
		{   
			calendar.add(Calendar.DATE, 1);
		}
		return calendar.getTimeInMillis();
	}
}
