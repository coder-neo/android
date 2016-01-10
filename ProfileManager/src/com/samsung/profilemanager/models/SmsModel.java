package com.samsung.profilemanager.models;

public class SmsModel extends BaseModel
{
	private int id;
	private String number;
	private int frequency;
	private long timeRecieved;
	private String name;
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getNumber()
	{
		return number;
	}
	public void setNumber(String number)
	{
		this.number = number;
	}
	public int getFrequency()
	{
		return frequency;
	}
	public void setFrequency(int frequency)
	{
		this.frequency = frequency;
	}
	public long getTimeRecieved()
	{
		return timeRecieved;
	}
	public void setTimeRecieved(long timeRecieved)
	{
		this.timeRecieved = timeRecieved;
	}
	
}
