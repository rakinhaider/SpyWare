package com.BUET_Arbirary.SpyWare;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Searchhistroy extends Activity {
	private ListView lv;
	private ArrayList<String> list=new ArrayList<String>();
	private ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchhistry);
		lv=(ListView)findViewById(R.id.listView1);
		
		Log.d("msg","hello");
		  Cursor mCur = managedQuery(android.provider.Browser.SEARCHES_URI,
	        		null, null, null, null
	        		);
		  int search = mCur.getColumnIndex(Browser.SearchColumns.SEARCH);
	        int url = mCur.getColumnIndex(Browser.SearchColumns.URL);
	        int date = mCur.getColumnIndex(Browser.SearchColumns.DATE);
	        if(mCur.moveToFirst())
	        {
	        	list.add(mCur.getString(search));
	        	list.add(mCur.getString(url));
	        	if(mCur.isNull(date))
	        	       list.add("NULL");
	        	else
	        	{
	        		String ds=mCur.getString(date);
	        		long ms=Long.parseLong(ds);
	        		
	        		Date d=new Date(ms);
	        		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH/mm/ss");
	        		String s=formatter.format(d);
	        	
	        		list.add(s);
	        	}
	        	
	        	list.add(mCur.getString(url));
	        		
	        }
	    		
	        	
	        while (mCur.moveToNext()) {
	        	list.add(mCur.getString(search));
	        	list.add(mCur.getString(url));
	        	if(mCur.isNull(date))
	        	       list.add("NULL");
	        	else
	        	{
	            		String ds=mCur.getString(date);
	            		long ms=Long.parseLong(ds);
	            		
	            		Date d=new Date(ms);
	            		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy HH/mm/ss");
	            		String s=formatter.format(d);
	            	
	            		list.add("nnnnnnn"+s);
	        	}
	        	
	        	list.add(mCur.getString(url));
	        }

		Log.d("msg","hello");
	        if(list.size()==0)
	        	list.add("no Search Results");
	       adapter=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_dropdown_item_1line,list);
	       lv.setAdapter(adapter);
	        
	}

}
