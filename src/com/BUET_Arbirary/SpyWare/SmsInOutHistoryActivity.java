package com.BUET_Arbirary.SpyWare;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class SmsInOutHistoryActivity extends TabActivity {
	/** Called when the activity is first created. */
	
	private Button btnBack;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sms_show);

		btnBack=(Button)findViewById(R.id.btnBackSMS);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		TabHost tabHost = getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(getApplicationContext(),
				SmsInHistory.class);
		spec = tabHost.newTabSpec("SmsInHistory").setIndicator("Inbox", null)
				.setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(getApplicationContext(),
				SmsOutHistory.class);
		spec = tabHost.newTabSpec("SmsOutHistory")
				.setIndicator("Sent Items", null).setContent(intent);
		tabHost.addTab(spec);
	}
}