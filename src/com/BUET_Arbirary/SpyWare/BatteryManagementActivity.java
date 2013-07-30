package com.BUET_Arbirary.SpyWare;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BatteryManagementActivity extends Activity {
	TextView tvBattery;
	String batInfo;
	Button btnBack;
	int level;
	int scale;
	private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
			scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
			getBatteryInfo();
			tvBattery.setText(batInfo);
			// context.setText(String.valueOf(level) + "%");
		}
	};
	public String getBatteryInfo()
	{
		
		int percentage = 0;
		// int
		// s=intent.getS)Extra(BatteryManager.BATTERY_STATUS_CHARGING,"-1");
		if (level >= 0 && scale > 0) {
			percentage = (level * 100) / scale;
			Log.d("tag", level+" "+scale+" "+percentage);
		}
		// Toast.makeText(
		// getApplicationContext(),
		// BatteryManager.BATTERY_STATUS_CHARGING + "k" + level
		// + scale + percentage, Toast.LENGTH_LONG).show();
		String status = "";
		// if ((BatteryManager.BATTERY_STATUS_CHARGING) == 2)
		status = "Charging";
		// else
		// status = "Discharging";

		batInfo = "Battery Status:\t" + status + "\nBattery Level:\t"+ percentage + "%";
		
		return "Battery Status:\t" + status ;

	}
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battery_info);
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_CHANGED));
		this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(
				Intent.ACTION_BATTERY_OKAY));
		batInfo = "";
		tvBattery = (TextView) findViewById(R.id.tvBattery);
		btnBack = (Button) findViewById(R.id.btnBackBattery);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});

	}

}