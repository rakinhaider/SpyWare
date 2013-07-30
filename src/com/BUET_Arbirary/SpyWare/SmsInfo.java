package com.BUET_Arbirary.SpyWare;

public class SmsInfo {
	String smsAddress;
	String smsBody;
	String smsTime;
	String smsType;
	public SmsInfo(String smsAddress, String smsBody, String time, String smsType) {
		super();
		this.smsAddress = smsAddress;
		this.smsBody = smsBody;
		smsTime = time;
		this.smsType = smsType;
	}
	public CLVRow getSMSInfoAsCLVRow(){
		return new CLVRow(null, smsAddress, smsBody, smsTime);
	}
	@Override
	public String toString() {
		return "SmsInfo [smsAddress=" + smsAddress + ", smsBody=" + smsBody
				+ ", smsTime=" + smsTime + ", type=" + smsType + "]";
	}
	
}
