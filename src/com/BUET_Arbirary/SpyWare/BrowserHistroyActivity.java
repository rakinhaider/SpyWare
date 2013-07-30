package com.BUET_Arbirary.SpyWare;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class BrowserHistroyActivity extends Activity implements
		android.view.View.OnClickListener {
	private ListView listview;
	// private TextView textview;
	private Button btnsearch;
	private List<CLVRow> list;
	private CLVAdapter adapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser_history);
		list = new ArrayList<CLVRow>();
		listview = (ListView) findViewById(R.id.listView1);
		btnsearch = (Button) findViewById(R.id.btnSearches);
		btnsearch.setOnClickListener(this);

		Cursor mCur = managedQuery(android.provider.Browser.BOOKMARKS_URI,
				null, null, null, null);
		// mCur.moveToFirst();
		int index = mCur.getColumnIndex(Browser.BookmarkColumns.TITLE);
		int url = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
		int date = mCur.getColumnIndex(Browser.BookmarkColumns.DATE);
		//int bookmark = mCur.getColumnIndex(Browser.BookmarkColumns.BOOKMARK);

		String title = "", dateFrmt = "", urlStr = "";
		if (mCur.moveToFirst()) {
			do {
				title = mCur.getString(index);
				// list.add(mCur.getString(bookmark));
				// if (mCur.getString(bookmark).equals("1"))
				// list.add("NULL");
				// else {
				String ds;
				if (mCur.isNull(date))
					ds = "<No Date Found>";
				else {
					ds = mCur.getString(date);
					long ms = Long.parseLong(ds);

					Date d = new Date(ms);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"mm:ss, dd/MM/yyyy");
					dateFrmt = "Browsing Time: "+formatter.format(d);
				}

				urlStr="URL: "+mCur.getString(url);
				list.add(new CLVRow(R.drawable.clvimage, title, dateFrmt, urlStr));
			} while (mCur.moveToNext());
		}

		adapter = new CLVAdapter(getApplicationContext(),
				R.layout.clv_row, list);
		listview.setAdapter(adapter);
	}

	public String getlasttime(Cursor mCur )
	{
		List<CLVRow> list=new ArrayList<CLVRow>();
		// mCur.moveToFirst();
		int index = mCur.getColumnIndex(Browser.BookmarkColumns.TITLE);
		int url = mCur.getColumnIndex(Browser.BookmarkColumns.URL);
		int date = mCur.getColumnIndex(Browser.BookmarkColumns.DATE);
		//int bookmark = mCur.getColumnIndex(Browser.BookmarkColumns.BOOKMARK);

		String title = "", dateFrmt = "", urlStr = "";
		if (mCur.moveToFirst()) {
			do {
				title = mCur.getString(index);
				// list.add(mCur.getString(bookmark));
				// if (mCur.getString(bookmark).equals("1"))
				// list.add("NULL");
				// else {
				String ds;
				if (mCur.isNull(date))
					ds = "<No Date Found>";
				else {
					ds = mCur.getString(date);
					long ms = Long.parseLong(ds);

					Date d = new Date(ms);
					SimpleDateFormat formatter = new SimpleDateFormat(
							"mm:ss, dd/MM/yyyy");
					dateFrmt = "Browsing Time: "+formatter.format(d);
				}

				urlStr="URL: "+mCur.getString(url);
				list.add(new CLVRow(null, title, dateFrmt, urlStr));
			} while (mCur.moveToNext());
		}
		else
			return null;
		return list.get(0).get2ndLine();
		
	}
	public void onClick(View v) {
		if (v.getId() == R.id.btnSearches) {
			Intent intent = new Intent(getApplicationContext(),
					Searchhistroy.class);
			startActivity(intent);

		}
	}

}