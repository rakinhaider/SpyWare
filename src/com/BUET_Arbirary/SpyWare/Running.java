package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Running extends Activity {

	//TextView header;
	ListView applist;
	ArrayList<String> appNameList;
	ArrayAdapter<String> adapter;
	PackageManager pm;
	Drawable icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installed);

		//header = (TextView) findViewById(R.id.header);
		//header.setTextColor(Color.BLACK);
		applist = (ListView) findViewById(R.id.applist);
		initAll();
	}

	public int initAll() {
		ActivityManager actvityManager = (ActivityManager) Running.this.getSystemService(ACTIVITY_SERVICE);
		pm = Running.this.getPackageManager();
		appNameList = new ArrayList<String>();
		List<RunningAppProcessInfo> procInfos = actvityManager
				.getRunningAppProcesses();
		Iterator<RunningAppProcessInfo> i = procInfos.iterator();
		@SuppressWarnings("unused")
		ApplicationInfo appInfo = new ApplicationInfo();

		Log.d("TAG", "hoise1");
		while (i.hasNext()) {
			try {
				ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
						.next());
				String applicationName = (String) pm.getApplicationLabel(pm
						.getApplicationInfo(info.processName,
								PackageManager.GET_META_DATA));
				appInfo = pm.getApplicationInfo(info.processName,
						PackageManager.GET_META_DATA);
				//String applicationPackage = appInfo.packageName;
				icon = pm.getApplicationIcon(pm.getApplicationInfo(
						info.processName, PackageManager.GET_META_DATA));
				// Toast.makeText(getApplicationContext(), info.pid,
				// Toast.LENGTH_LONG).show();
				Log.d("TAG", ((Integer) info.pid).toString());
				appNameList.add(applicationName);
			} catch (Exception e) {
			}
		}

		for (String s : appNameList) {
			Log.d("LOOP", s);
		}
		Log.d("TAG", "hoise2");
		adapter = new ArrayAdapter<String>(Running.this.getApplicationContext(),
				android.R.layout.simple_list_item_1, appNameList);
		applist.setAdapter(adapter);
		return appNameList.size();
	}

	public int getRunningAppCount() {
		initAll();
		return appNameList.size();
	}
}
