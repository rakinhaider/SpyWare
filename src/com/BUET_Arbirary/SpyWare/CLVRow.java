package com.BUET_Arbirary.SpyWare;


public class CLVRow {
	private String headline,secLine,thirdline;
	private Integer intCLV;

	public CLVRow(Integer intCLV,String headline, String secLine, String thirdLine) {
		this.intCLV=intCLV;
		this.headline=headline;
		this.secLine=secLine;
		this.thirdline=thirdLine;
	}
	
	public Integer getIntCLV() {
		return intCLV;
	}

	public void setIvCLV(Integer intCLV) {
		this.intCLV = intCLV;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String get2ndLine() {
		return secLine;
	}

	public void set2ndline(String secLine) {
		this.secLine = secLine;
	}

	public String get3rdLine() {
		return thirdline;
	}

	public void set3rdLine(String thirdLine) {
		this.thirdline = thirdLine;
	}

}
