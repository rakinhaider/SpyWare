package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Installed extends Activity {

	//TextView header;
	ListView appList;
	ArrayAdapter<PInfo> adapter;
	// ArrayList<Application> appClassList;
	private static ArrayList<PInfo> pac;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.installed);
		Log.d("TAG", "hoise");
		//header = (TextView) findViewById(R.id.header);
		//header.setTextColor(Color.BLACK);
		appList = (ListView) findViewById(R.id.applist);

		getPackages();
	   

		adapter = new ArrayAdapter<PInfo>(getApplicationContext(),
				android.R.layout.simple_list_item_1, pac);

		appList.setAdapter(adapter);

	}

	private void getPackages() {
		/*false = no system packages*/
		pac = getInstalledApps(false); 
		final int max = pac.size();
		for (int i = 0; i < max; i++) {
			pac.get(i).prettyPrint();
		}
	}

	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		List<PackageInfo> packs = Installed.this.getPackageManager().getInstalledPackages(0);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if ((!getSysPackages) && (p.versionName == null)) {
				continue;
			}
			PInfo newInfo = new PInfo(p.applicationInfo.loadLabel(
					Installed.this.getPackageManager()).toString(), p.packageName,
					p.versionName, p.versionCode,
					p.applicationInfo.loadIcon(Installed.this.getPackageManager()));
			res.add(newInfo);
		}
		return res;
	}
	public int getInstalledAppCount(){
		getInstalledApps(true);
		return pac.size();
	}
}
