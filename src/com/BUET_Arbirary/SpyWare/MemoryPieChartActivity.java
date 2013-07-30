package com.BUET_Arbirary.SpyWare;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MemoryPieChartActivity extends Activity {
	List<PieDetailsItem> piedata = new ArrayList<PieDetailsItem>(0);
	PieDetailsItem item;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pie);//TODO

		PieDetailsItem item;
		long maxCount = 0;
		int itemCount = 0;
		//List<Long> items = new ArrayList<Long>(0);
		int items[]={20,15,40,10,5};
		int colors[] = { -6777216, -16776961, -16711681, -12303292, -7829368 };
		String itemslabel[] = { " vauesr ur 100", " vauesr ur 200",
				" vauesr ur 300", " vauesr ur 400", " vauesr ur 500" };
		
//		MemoryInfo mi = new MemoryInfo();
//		ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
//		activityManager.getMemoryInfo(mi);
//		long availableMegs = mi.availMem / 1048576L;
//		items.add(availableMegs);
		//long totalRAM=getTotalRAM();
		//items.add(totalRAM-availableMegs);
		
		int len=items.length;
		for (int i = 0; i < len; i++) {
//			itemCount = items[i];
			item = new PieDetailsItem();
			item.count = (float)items[i];
			item.label = itemslabel[i];
			item.color = colors[i];
			piedata.add(item);
			maxCount = maxCount + items[i];
		}
		int size = 155;
		int BgColor = 0xffa11b1;
		Bitmap mBaggroundImage = Bitmap.createBitmap(size, size,
				Bitmap.Config.ARGB_8888);
		View_PieChart piechart = new View_PieChart(this);
		piechart.setLayoutParams(new LayoutParams(size, size));
		piechart.setGeometry(size, size, 2, 2, 2, 2, 2130837504);
		piechart.setSkinparams(BgColor);
		piechart.setData(piedata, (int)maxCount);
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
	public static long getTotalRAM() {
		long mem=0;
	    RandomAccessFile reader = null;
	    String load = null;
	    try {
	        reader = new RandomAccessFile("/proc/meminfo", "r");
	        load = reader.readLine();
	        mem=Long.parseLong(load);
	    } catch (IOException ex) {
	        ex.printStackTrace();
	    } finally {
	        // Streams.close(reader);
	    }
	    return mem;
	}
}