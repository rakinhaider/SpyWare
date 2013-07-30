package com.BUET_Arbirary.SpyWare;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class CallHistroyActivity extends Activity implements
		android.view.View.OnClickListener {
	private ListView listview;
	// private TextView textview;
	// private ArrayList<String> list=new ArrayList<String>();
	private List<CLVRow> list = new ArrayList<CLVRow>();
	private ArrayList<String> listnum = new ArrayList<String>();
	private ArrayList<String> listduration = new ArrayList<String>();
	private ArrayList<String> listdate = new ArrayList<String>();
	private ArrayList<String> listrawdate = new ArrayList<String>();
	private ArrayList<String> listtype = new ArrayList<String>();
	private ArrayList<String> listname = new ArrayList<String>();
	// private ArrayList<String> finallist=new ArrayList<String>();

	private Button btnBack;

	int number = 0;
	int duration = 0;
	int type = 0;
	int name = 0;
	int date = 0;
	private CLVAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.call_history);

		btnBack = (Button) findViewById(R.id.btnBackCallHistory);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

		listview = (ListView) findViewById(R.id.listView1);
		// textview=(TextView)findViewById(R.id.textView1);
		Cursor mCur = managedQuery(android.provider.CallLog.Calls.CONTENT_URI,
				null, null, null, null);
		// if(mCur.moveToFirst())
		// {
		number = mCur.getColumnIndex(CallLog.Calls.NUMBER);
		duration = mCur.getColumnIndex(CallLog.Calls.DURATION);
		type = mCur.getColumnIndex(CallLog.Calls.TYPE);
		name = mCur.getColumnIndex(CallLog.Calls.CACHED_NAME);
		date = mCur.getColumnIndex(CallLog.Calls.DATE);
		// }
		// int url = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
		// int date = mCur.getColumnIndex(Browser.BookmarkColumns.DATE);
		String _name,_number,_callDuration,_callType,_callTime;
		Integer imgID=null;
		if (mCur.moveToFirst()) {
			do {
				if (mCur.isNull(name)) {
					//list.add("No Name saved");
					_name="";
					listname.add("No Name saved");
				} else {
					//list.add(mCur.getString(name));
					_name=mCur.getString(name);
					listname.add(mCur.getString(name));

				}

				_number=mCur.getString(number);
				listnum.add(_number);
				
				String sec = mCur.getString(duration);
				long time = Long.parseLong(sec);
				//time = time;
				Date d = new Date(time);
				SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
				String s = formatter.format(d);
				//list.add(s);
				_callDuration="Duration: "+s;
				listduration.add(s);

				String ds = mCur.getString(date);
				listrawdate.add(mCur.getString(date));
				long ms = Long.parseLong(ds);
				d = new Date(ms);
				formatter = new SimpleDateFormat("hh:mm:ss, dd/MM/yyyy");
				s = formatter.format(d);
				//list.add(s);
				_callTime="Time: "+s;
				listdate.add(s);
				
				if (mCur.getString(type).equals("0")) {
					//list.add("missed Call");
					imgID=R.drawable.miscall;
					listtype.add("missed Call");
				}
				else if (mCur.getString(type).equals("1")) {
					//list.add("Incoming  Call");
					imgID=R.drawable.incoming;
					listtype.add("Incoming Call");
				}
				else if (mCur.getString(type).equals("2")) {
					//list.add("Outgoing Call");
					imgID=R.drawable.outgoing;
					listtype.add("Outgoing Call");
				}
				list.add(new CLVRow(imgID, _name+"["+_number+"]", _callDuration, _callTime));

			} while (mCur.moveToNext());
		}
		mCur.close();
		// finallist=list;
		setAdapter();
		// sortByTime();
		// sortByDuration();
		// textview.setOnClickListener(this);
	}

	private void sortByDuration() {
		list.clear();
		Collections.sort(listduration);

		//list = listduration;
		setAdapter();
	}

	private void sortByTime() {
		list.clear();
		Collections.sort(listrawdate);
		//list = listrawdate;
		setAdapter();
	}
	public String getlastcalltime(Cursor mCur)
	{
		
		// if(mCur.moveToFirst())
		// {
		number = mCur.getColumnIndex(CallLog.Calls.NUMBER);
		duration = mCur.getColumnIndex(CallLog.Calls.DURATION);
		type = mCur.getColumnIndex(CallLog.Calls.TYPE);
		name = mCur.getColumnIndex(CallLog.Calls.CACHED_NAME);
		date = mCur.getColumnIndex(CallLog.Calls.DATE);
		// }
		// int url = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
		// int date = mCur.getColumnIndex(Browser.BookmarkColumns.DATE);
		String _name,_number,_callDuration,_callType,_callTime;
		Integer imgID=null;
		if (mCur.moveToFirst()) {
			do {
				if (mCur.isNull(name)) {
					//list.add("No Name saved");
					_name="";
					listname.add("No Name saved");
				} else {
					//list.add(mCur.getString(name));
					_name=mCur.getString(name);
					listname.add(mCur.getString(name));

				}

				_number=mCur.getString(number);
				listnum.add(_number);
				
				String sec = mCur.getString(duration);
				long time = Long.parseLong(sec);
				//time = time;
				Date d = new Date(time);
				SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
				String s = formatter.format(d);
				//list.add(s);
				_callDuration="Duration: "+s;
				listduration.add(s);

				String ds = mCur.getString(date);
				listrawdate.add(mCur.getString(date));
				long ms = Long.parseLong(ds);
				d = new Date(ms);
				formatter = new SimpleDateFormat("hh:mm:ss, dd/MM/yyyy");
				s = formatter.format(d);
				//list.add(s);
				_callTime="Time: "+s;
				listdate.add(s);
				
				if (mCur.getString(type).equals("0")) {
					//list.add("missed Call");
					imgID=R.drawable.miscall;
					listtype.add("missed Call");
				}
				else if (mCur.getString(type).equals("1")) {
					//list.add("Incoming  Call");
					imgID=R.drawable.incoming;
					listtype.add("Incoming Call");
				}
				else if (mCur.getString(type).equals("2")) {
					//list.add("Outgoing Call");
					imgID=R.drawable.outgoing;
					listtype.add("Outgoing Call");
				}
				list.add(new CLVRow(imgID, _name+"["+_number+"]", _callDuration, _callTime));

			} while (mCur.moveToNext());
		
		}
		else
			return null;
		Collections.sort(listdate);
		return listdate.get(0);
		
	}
	private void setAdapter() {

		adapter = new CLVAdapter(getApplicationContext(),
				R.layout.clv_row, list);
		listview.setAdapter(adapter);
	}

	public void onClick(View v) {
		if (v.getId() == R.id.textView1) {
			final Dialog dialog = new Dialog(CallHistroyActivity.this);
			dialog.setContentView(R.layout.callhistrydialog);
			TextView tv1 = (TextView) dialog.findViewById(R.id.SortByduration);
			TextView tv2 = (TextView) dialog.findViewById(R.id.SortBydate);
			TextView tv3 = (TextView) dialog
					.findViewById(R.id.Sortincomingcall);
			dialog.show();
			Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
					.getDefaultDisplay();
			int width = display.getWidth();
			int height = display.getHeight();
			dialog.getWindow().setLayout(width, (height * 4) / 5);
			tv1.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					if (v.getId() == R.id.SortByduration)
						dialog.dismiss();
					sortByDuration();
					// TODO Auto-generated method stub

				}
			});
			tv2.setOnClickListener(new View.OnClickListener() {

				public void onClick(View v) {
					if (v.getId() == R.id.SortBydate) {
						dialog.dismiss();
						sortByTime();
					}
					// TODO Auto-generated method stub

				}
			});
		}

		// TODO Auto-generated method stub

	}

}