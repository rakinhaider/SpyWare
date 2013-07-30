package com.BUET_Arbirary.SpyWare;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class CPUPieChart extends Activity {
	List<PieDetailsItem> piedata = new ArrayList<PieDetailsItem>(0);
	List<RunningAppProcessInfo> runAppInfoList;
	List<Integer> pidList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie);

		PieDetailsItem item;
		int maxCount = 0;
		// int itemCount = 0;
		// int items[] = { 20, 40, 10, 15, 5 };//TODO set color
		int colors[] = { -6777216, -16776961, -16711681, -12303292, -7829368 };
//		String itemslabel[] = { " vauesr ur 100", " vauesr ur 200",
//				" vauesr ur 300", " vauesr ur 400", " vauesr ur 500" };

		// TODO
		pidList = getPidList();
		Log.d("Pid List","got: "+pidList);
		//float items[] = { readUsage() };
		ArrayList<Float> items=new ArrayList<Float>();
		int len=pidList.size();
		for(int i=0;i<len;i++)
		{
			items.add((Float)readUsage(pidList.get(i)));
		}
		for (int i = 0; i < len; i++) {
			// itemCount = items[i];
			item = new PieDetailsItem();
			item.count = items.get(i);
			item.label = String.valueOf(pidList.get(i));
			item.color = colors[i%5];
			piedata.add(item);
			maxCount = maxCount + (int) Math.ceil(items.get(i));
		}

		int size = 155;
		int BgColor = 0xffa11b1;
		Bitmap mBaggroundImage = Bitmap.createBitmap(size, size,
				Bitmap.Config.ARGB_8888);
		View_PieChart piechart = new View_PieChart(this);
		piechart.setLayoutParams(new LayoutParams(size, size));
		piechart.setGeometry(size, size, 2, 2, 2, 2, 2130837504);
		piechart.setSkinparams(BgColor);

		piechart.setData(piedata, maxCount);//DONE

		piechart.invalidate();
		piechart.draw(new Canvas(mBaggroundImage));
		piechart = null;
		ImageView mImageView = new ImageView(this);
		mImageView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		mImageView.setBackgroundColor(BgColor);
		mImageView.setImageBitmap(mBaggroundImage);
		LinearLayout finalLayout = (LinearLayout) findViewById(R.id.pie_container);
		finalLayout.addView(mImageView);
	}

	private List<Integer> getPidList() {
		ActivityManager actvityManager = (ActivityManager) CPUPieChart.this
				.getSystemService(ACTIVITY_SERVICE);
		// PackageManager pm = CPUPieChart.this.getPackageManager();
		pidList = new ArrayList<Integer>();
		runAppInfoList = actvityManager.getRunningAppProcesses();
		Iterator<RunningAppProcessInfo> itRunAppProcInfo = runAppInfoList
				.iterator();

		Log.d("getPidList(): CPU", "hoise1");
		while (itRunAppProcInfo.hasNext()) {
			try {
				ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (itRunAppProcInfo
						.next());
				pidList.add((Integer) info.pid);
				Log.d("TAG", ((Integer) info.pid).toString());
			} catch (Exception e) {
			}
		}
		return pidList;
	}

	private float readUsage(Integer pid) {
		try {
			RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
			String load = reader.readLine();
			Log.d("readusage()", "Pid read: reader.readLine()="+load);

			String[] toks = load.split(" ");

			long idle1 = Long.parseLong(toks[5]);
			long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			try {
				Thread.sleep(360);
			} catch (Exception e) {
			}

			reader.seek(0);
			load = reader.readLine();
			reader.close();

			toks = load.split(" ");

			long idle2 = Long.parseLong(toks[5]);
			long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3])
					+ Long.parseLong(toks[4]) + Long.parseLong(toks[6])
					+ Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

			return (float) (cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		return 0;
	}
}