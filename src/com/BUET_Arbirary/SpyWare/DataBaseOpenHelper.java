package com.BUET_Arbirary.SpyWare;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseOpenHelper extends SQLiteOpenHelper {

	

	private static String Databasename="DATAUSAGE2";
	private static int Databasevertion=1;
	public DataBaseOpenHelper(Context context) {
		super(context, Databasename,null, Databasevertion);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	
	public void onCreate(SQLiteDatabase db) {
		
		// TODO Auto-generated method stub
		String sqlstr="Create table "+"datausageinfo"+"("+"KEY_ID "+ "INTEGER PRIMARY KEY,"+" date"+"  TEXT,"+ " totalreceivebytes "+"TEXT,"+" totalsentbytes "+" TEXT," +" totalreceivepackets " +" TEXT, "+" totalsentpackets "+" TEXT"+")";
		
		db.execSQL(sqlstr);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS"+"datausageinfo");

	}
	public void setdata(String date,String totalreceivebytes,String totalsentbytes,String totalreceivepackets,String totalsentpackets)
	{
		Log.d("msg",totalreceivebytes+totalsentbytes+totalreceivepackets+totalsentpackets);
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("date",date);
		values.put("totalreceivebytes",totalreceivebytes);
		values.put("totalsentbytes",totalsentbytes);
		values.put("totalreceivepackets",totalreceivepackets);
		values.put("totalsentpackets",totalsentpackets);
		db.insert("datausageinfo",null, values);
		db.close();

	}
	public void demodata()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("totalreceivebytes","14565");
		
		db.insert("datausageinfo",null, values);
		db.close();
	}
	public int chk()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String[] colums=new String[]{"totalreceivebytes"};
		Cursor cr=db.query("datausageinfo", colums,null,null,null,null,null);
		int x=0;
		if(cr.moveToFirst())
			x=1;
		cr.close();
		db.close();
		return x;
		
	}
	public ArrayList<String> getdata()
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ArrayList<String> list=new ArrayList<String>();
		String[] colums=new String[]{"date,totalreceivebytes,totalsentbytes,totalreceivepackets,totalsentpackets"};
		Cursor cr=db.query("datausageinfo", colums,null,null,null,null,null);
		if(cr.moveToLast())
		{
			list.add(cr.getString(0));
			list.add(cr.getString(1));
			list.add(cr.getString(2));
			list.add(cr.getString(3));
			list.add(cr.getString(4));
		}
		cr.close();
		db.close();
		return list;
	}
	public  void updatelist(String date,String totalreceivebytes,String totalsentbytes,String totalreceivepackets,String totalsentpackets)
	{
		
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues values=new ContentValues();
		values.put("date",date);
		values.put("totalreceivebytes",totalreceivebytes);
		values.put("totalsentbytes",totalsentbytes);
		values.put("totalreceivepackets",totalreceivepackets);
		values.put("totalsentpackets",totalsentpackets);
		db.update("usageinfo", values,"KEY_ID"+"="+1,null);
		db.insert("datausageinfo",null, values);
		db.close();
	}


}
