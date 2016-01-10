package com.samsung.profilemanager.models;

public class CallModel extends BaseModel 
{

	private int id;
	private String contactNumber;
	private long callTime;
	private int frequency;
	private String contactName;
	
	//Getters and Setters
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getContactName()
	{
		return contactName;
	}
	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}
	public String getContactNumber()
	{
		return contactNumber;
	}
	public void setContactNumber(String contactNumber)
	{
		this.contactNumber = contactNumber;
	}
	public long getCallTime()
	{
		return callTime;
	}
	public void setCallTime(long callTime)
	{
		this.callTime = callTime;
	}
	public int getFrequency()
	{
		return frequency;
	}
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}
	
}
