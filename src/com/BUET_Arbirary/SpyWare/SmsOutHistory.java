package com.BUET_Arbirary.SpyWare;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

public class SmsOutHistory extends Activity {
	/** Called when the activity is first created. */
	ListView smsList;
	CLVAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smslist);

		smsList = (ListView) findViewById(R.id.lvSMSlist);
		adapter = new CLVAdapter(getApplicationContext(),
				R.layout.clv_row, getAllSms());
		smsList.setAdapter(adapter);

	}

	public List<CLVRow> getAllSms() {
		List<CLVRow> res = new ArrayList<CLVRow>();
		Uri uriSms = Uri.parse("content://sms");
		Context context = getApplicationContext();
		ContentResolver contentResolver = context.getContentResolver();
		String[] projection = { "address", "body", "date", "type" };
		Cursor cursor = contentResolver.query(uriSms, projection, null, null,
				null);

		int addressColumn = cursor.getColumnIndexOrThrow("address");
		int bodyColumn = cursor.getColumnIndexOrThrow("body");
		int timeColumn = cursor.getColumnIndexOrThrow("date");
		int typeColumn = cursor.getColumnIndexOrThrow("type");

		if (cursor.moveToFirst()) {
			do {

				String smsAddress = cursor.getString(addressColumn);
				String smsBody = cursor.getString(bodyColumn);

				String Time = cursor.getString(timeColumn);
				Long ms = Long.parseLong(Time);
				Date ds = new Date(ms);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"dd/mm/yy hh:mm:ss");
				Time = formatter.format(ds);

				String smsType = cursor.getString(typeColumn);
				Integer type = Integer.parseInt(smsType);

				if (type == 2) {
					SmsInfo smsInfo = new SmsInfo(smsAddress, smsBody, Time,
							"Sent Item");
					res.add(smsInfo.getSMSInfoAsCLVRow());
				}
			} while (cursor.moveToNext());

		}

		cursor.close();
		return res;
	}
}