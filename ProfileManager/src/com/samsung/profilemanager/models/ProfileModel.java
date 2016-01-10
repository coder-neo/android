package com.samsung.profilemanager.models;



public class ProfileModel extends BaseModel {

	private int profileID;
	private String profileName;
	private boolean isWifiEnabled;
	private boolean isBluetoothEnabled;
	private boolean isGPSEnabled;
	private int brightness;
	private int ringVolume;
	private int mediaVolume;
	private int notficationVolume;
	private int alarmVolume;
	private long timeActivated;
	public long getTimeActivated()
	{
		return timeActivated;
	}
	public void setTimeActivated(long timeActivated)
	{
		this.timeActivated = timeActivated;
	}
	public int getAlarmVolume()
	{
		return alarmVolume;
	}
	public void setAlarmVolume(int alarmVolume)
	{
		this.alarmVolume = alarmVolume;
	}
	public int getProfileID() {
		return profileID;
	}
	public void setProfileID(int profileID) {
		this.profileID = profileID;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public boolean isWifiEnabled() {
		return isWifiEnabled;
	}
	public void setWifiEnabled(boolean isWifiEnabled) {
		this.isWifiEnabled = isWifiEnabled;
	}
	public boolean isBluetoothEnabled() {
		return isBluetoothEnabled;
	}
	public void setBluetoothEnabled(boolean isBluetoothEnabled) {
		this.isBluetoothEnabled = isBluetoothEnabled;
	}
	public boolean isGPSEnabled() {
		return isGPSEnabled;
	}
	public void setGPSEnabled(boolean isGPSEnabled) {
		this.isGPSEnabled = isGPSEnabled;
	}
	public int getBrightness() {
		return brightness;
	}
	public void setBrightness(int brightness) {
		this.brightness = brightness;
	}
	public int getRingVolume() {
		return ringVolume;
	}
	public void setRingVolume(int ringVolume) {
		this.ringVolume = ringVolume;
	}
	public int getMediaVolume() {
		return mediaVolume;
	}
	public void setMediaVolume(int mediaVolume) {
		this.mediaVolume = mediaVolume;
	}
	public int getNotficationVolume() {
		return notficationVolume;
	}
	public void setNotficationVolume(int notficationVolume) {
		this.notficationVolume = notficationVolume;
	}
	
	
	}
