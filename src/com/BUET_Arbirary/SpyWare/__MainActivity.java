package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class __MainActivity extends Activity implements OnItemClickListener,
		LocationListener {

	private ListView lvMainMenu;
	private List<CLVRow> detailsOptionsList;
	private String[] allOptions;
	private Map<String, Integer> imgMap;
	private Map<String, String> option2ndLineMap;
	private Dialog gpsDialog;
	private TextView tvGPSLat, tvGPSLon, tvGPSAccuracy, tvGPSProvider;
	private LocationManager locationManager;
	private static String gpsLat = "", gpsLon = "", accuracyParameter = "",
			providerUsed = "";
	private CLVRow clvRow;
	private String lastCallTime;
	private int sizeOfContactList,runningapps,numberOfPhoto;
	ImageView imgSet;
	String brLastTime,battcharged;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		lvMainMenu = (ListView) findViewById(R.id.lvMainMenu);
		lvMainMenu.setOnItemClickListener(this);
		detailsOptionsList = new ArrayList<CLVRow>();
		allOptions = getResources().getStringArray(R.array.mainMenu);

		// seting Hashmaps
		imgMap = new HashMap<String, Integer>();
		option2ndLineMap = new HashMap<String, String>();
		int size = allOptions.length;
		for (int i = 0; i < size; i++) {
			imgMap.put(allOptions[i], getImageIdByPos(i));
			option2ndLineMap.put(allOptions[i], getSecLineByPos(i));
		}

		// init gps dialog
		gpsDialog = new Dialog(__MainActivity.this);
		gpsDialog.setContentView(R.layout.gps_show_layout);
		gpsDialog.setTitle("Position(GPS) of Device:");
		Log.d("onCreate()", "gpsDialogSet");

		tvGPSLat = (TextView) gpsDialog.findViewById(R.id.tvGPSLatitude);
		tvGPSLon = (TextView) gpsDialog.findViewById(R.id.tvGPSLongitude);
		tvGPSAccuracy = (TextView) gpsDialog.findViewById(R.id.tvGPSAccuracy);
		tvGPSProvider = (TextView) gpsDialog.findViewById(R.id.tvGPSProvider);
		// TODO check provider & accuracy param
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		accuracyParameter = "ACCESS_FINE_LOCATION";// TODO
		providerUsed = "GPS_PROVIDER";// TODO
		thr.start();
		imgSet=(ImageView)findViewById(R.id.ivSettingsbarMainMenu);
		imgSet.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				Intent intentSet = new Intent(getApplicationContext(),
						Settings.class);
				intentSet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intentSet);
			}
		});
		Log.d("onCreate()", "all set(also gpsDialog)");
	}
	
	Thread thr = new Thread(new Runnable() {

		@Override
		public void run() {

			Log.d("thread", "cholche");

			Looper.prepare();

			Log.d("thread", "cholche");
			while(true)
			{
				handler.sendEmptyMessage(0);
				GetContactList getContactList =new GetContactList();
				//List<CLVRow> contactListMy = getContactList.readContacts(getContentResolver());
				//sizeOfContactList=contactListMy.size();
				//handler.sendEmptyMessage(1);
			
				CallHistroyActivity callHistoryActivity=new CallHistroyActivity();
				lastCallTime=callHistoryActivity.getlastcalltime(managedQuery(android.provider.CallLog.Calls.CONTENT_URI,
						null, null, null, null));
				handler.sendEmptyMessage(2);
			
				SmsInHistory smsInHistory = new SmsInHistory();
				List<CLVRow> smsHistoryList = smsInHistory
						.getAllSms(getApplicationContext());
				Iterator iterator = smsHistoryList.iterator();
				while (iterator.hasNext()) {
					Log.d("iterator", "iterating");
					clvRow = (CLVRow) iterator.next();
				}
				handler.sendEmptyMessage(3);
			
				PhotoActivity photoActivity=new PhotoActivity();
				final String[] projection = { MediaStore.Images.Media.DATA,
						MediaStore.Images.Media.DISPLAY_NAME,
						MediaStore.Images.Media.DATE_TAKEN,
						MediaStore.Images.Media.SIZE,
						MediaStore.Images.Media.LONGITUDE,
						MediaStore.Images.Media.LATITUDE };
				final String selection = MediaStore.Images.Media.BUCKET_ID + " ="
						+ " ?";
				String CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory()
						.toString() + "/DCIM/Camera";
				String CAMERA_IMAGE_BUCKET_ID = String.valueOf(CAMERA_IMAGE_BUCKET_NAME.toLowerCase().hashCode());
				final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID};
			
				ContentResolver contentResolver = getApplicationContext().getContentResolver();
				List<CLVRow> photoList=photoActivity.getCameraImages(getApplicationContext(),android.provider.MediaStore.Images.Media.query(
						contentResolver, Images.Media.EXTERNAL_CONTENT_URI, projection,
						selection, selectionArgs, null));
				numberOfPhoto=photoList.size();
				handler.sendEmptyMessage(5);
			
				BrowserHistroyActivity brHistory=new BrowserHistroyActivity();
				Cursor mCur = managedQuery(android.provider.Browser.BOOKMARKS_URI,
						null, null, null, null);
				brLastTime=brHistory.getlasttime(mCur);
				handler.sendEmptyMessage(6);
			
				BatteryManagementActivity batManage=new BatteryManagementActivity();
				battcharged=batManage.getBatteryInfo();
				handler.sendEmptyMessage(7);
				Looper.loop();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	});
	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if(msg.what==0)
			{
				detailsOptionsList.get(0).set2ndline(
						"GPS on");
				setAdapter();
			}
			if(msg.what==1)
			{
					detailsOptionsList.get(1).set2ndline(
							sizeOfContactList+ " contacts");
					setAdapter();
			}
			if(msg.what==2)
			{
				if(lastCallTime!=null)
				{
					detailsOptionsList.get(2).set2ndline(
							"Last Call Time: " + lastCallTime);
					setAdapter();
				}
				else{
					detailsOptionsList.get(2).set2ndline("Last Call Time: " + lastCallTime);
					setAdapter();
				}
			}
			if (msg.what == 3) {
				if (clvRow != null)
				{
					detailsOptionsList.get(3).set2ndline(
							"Last incoming message:" + clvRow.get2ndLine()
									+ "\t" + "At the time:"
									+ clvRow.get3rdLine());
					setAdapter();
				}	
			}
			if(msg.what==5)
			{
				detailsOptionsList.get(5).set2ndline(numberOfPhoto+" photos");
				setAdapter();
			}
			if(msg.what==6)
			{
				if(brLastTime!=null)
				{
					detailsOptionsList.get(6).set2ndline("Last used: "+brLastTime);
				
					setAdapter();
				}
				else 
				{
					detailsOptionsList.get(6).set2ndline("Not used.");
					
					setAdapter();
	
				}
			}
			if(msg.what==7)
			{
				detailsOptionsList.get(7).set2ndline(battcharged);
				setAdapter();
			}
		};
	};

	private String getSecLineByPos(int pos) {
		// TODO
		String str = "testing";
		switch (pos) {
		case 4:
			// Installed insObj=new Installed();
			// Running runObj=new Running();
			// str=insObj.getInstalledAppCount()+" installed, "+runObj.getRunningAppCount()+" running";
			break;

		default:
			break;
		}
		return str;
	}

	private Integer getImageIdByPos(int pos) {
		switch (pos) {
		case 0:
			return R.drawable.gps;
		case 1:
			return R.drawable.contact_list;
		case 2:
			return R.drawable.callhistory;
		case 3:
			return R.drawable.smshistory;
		case 4:
			return R.drawable.applicationlist;
		case 5:
			return R.drawable.photo;
		case 6:
			return R.drawable.browserhistory;
		case 7:
			return R.drawable.battery;

		default:
			return R.drawable.clvimage;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		
		int size = allOptions.length;
		Log.d("Aloptions", "Length=" + size);
		detailsOptionsList = new ArrayList<CLVRow>();
		// ImageView img = new ImageView(getApplicationContext());
		for (int i = 0; i < size; i++) {
			// img.setImageResource(imgMap.get(allOptions[i]));
			Integer id = imgMap.get(allOptions[i]);
			if (id == null)
				id = 0;
			// Log.d("MainMenu:: imgID",i+"th image resource id:"+id);
			detailsOptionsList.add(new CLVRow(id, allOptions[i],
					option2ndLineMap.get(allOptions[i]), ""));
		}
		setAdapter();

	}

	private void setAdapter() {

		CLVAdapter adapter = new CLVAdapter(this, R.layout.clv_row,
				detailsOptionsList);
		lvMainMenu.setAdapter(adapter);
	}

	@Override
	protected void onPause() {
		super.onPause();
		// locationManager.removeUpdates(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parentView, View view, int position,
			long id) {

		switch (position) {
		case 0:
			// GPS
			showGPSDialog();
			break;
		case 1:
			// Contact List
			Intent iContactlist = new Intent(getApplicationContext(),
					GetContactList.class);
			iContactlist.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(iContactlist);
			break;

		case 2:
			// Call History
			Intent iCallHistory = new Intent(getApplicationContext(),
					CallHistroyActivity.class);
			iCallHistory.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(iCallHistory);
			break;

		case 3:
			// SMS History
			Intent intentSMS = new Intent(getApplicationContext(),
					SmsInOutHistoryActivity.class);
			intentSMS.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentSMS);
			break;

		case 4:
			// Application
			Intent intentApp = new Intent(getApplicationContext(),
					ApplicationlistActivity.class);
			intentApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentApp);
			break;

		case 5:
			// Photos
			Intent intentPhoto = new Intent(getApplicationContext(),
					PhotoActivity.class);
			intentPhoto.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentPhoto);
			break;

		case 6:
			// Browser History
			Intent intentBrowser = new Intent(getApplicationContext(),
					BrowserHistroyActivity.class);
			intentBrowser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentBrowser);
			break;

		case 7:
			// Battery
			Intent intentBattery = new Intent(getApplicationContext(),
					BatteryManagementActivity.class);
			intentBattery.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentBattery);
			break;
			
		case 8:
			// TODO Data Packet usage
			Intent intentDataUsage=new Intent(getApplicationContext(),DataUsageActivity.class);
			intentDataUsage.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentDataUsage);
			break;
			
		case 9:
			// PieChartShow
			Intent intentPieChart=new Intent(getApplicationContext(),PieChartShow.class);
			intentPieChart.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intentPieChart);
			break;

		default:
			break;
		}

	}

	private void showGPSDialog() {

		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 10, this);
		Log.d("showGPSDialog()", gpsLat + ", " + gpsLon);
		tvGPSLat.setText(gpsLat);
		tvGPSLon.setText(gpsLon);
		tvGPSAccuracy.setText(accuracyParameter);
		tvGPSProvider.setText(providerUsed);

		Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE))
				.getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		gpsDialog.getWindow().setLayout(width, (height / 2));
		Button btnCancel = (Button) gpsDialog
				.findViewById(R.id.btnGPSDialogCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				gpsDialog.dismiss();
			}
		});
		gpsDialog.show();
	}

	@Override
	public void onLocationChanged(Location location) {
		if (location != null) {
			gpsLat = location.getLatitude() + "";
			gpsLon = location.getLongitude() + "";
			Log.d("onLocationChanged()", gpsLat + ", " + gpsLon);
			showGPSDialog();
		}
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

}
