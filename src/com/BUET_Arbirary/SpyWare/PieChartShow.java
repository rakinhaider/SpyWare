package com.BUET_Arbirary.SpyWare;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class PieChartShow extends TabActivity {

	private Button btnBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie_chart_tab);

		btnBack = (Button) findViewById(R.id.btnBackPie);
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
				MemoryPieChartActivity.class);
		spec = tabHost.newTabSpec("MemoryPieChartActivity")
				.setIndicator("Memory Usage", null).setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(getApplicationContext(),
				CPUPieChart.class);
		spec = tabHost.newTabSpec("CPU").setIndicator("CPUPieChart", null)
				.setContent(intent);
		tabHost.addTab(spec);
	}

}
