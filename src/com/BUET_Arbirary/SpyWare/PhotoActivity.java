package com.BUET_Arbirary.SpyWare;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

public class PhotoActivity extends Activity {
	/** Called when the activity is first created. */

	public static String CAMERA_IMAGE_BUCKET_ID;
	public static String CAMERA_IMAGE_BUCKET_NAME;
	ListView photoList;
	// ArrayAdapter<PhotoInfo> adapter;
	CLVAdapter adapter;
	Button btnBack;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photo_acitivty);
		
		btnBack=(Button)findViewById(R.id.btnBackPhoto);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		CAMERA_IMAGE_BUCKET_NAME = Environment.getExternalStorageDirectory()
				.toString() + "/DCIM/Camera";
		CAMERA_IMAGE_BUCKET_ID = getBucketId(CAMERA_IMAGE_BUCKET_NAME);

		photoList = (ListView) findViewById(R.id.photolist);
		// adapter = new ArrayAdapter<PhotoInfo>(getApplicationContext(),
		// android.R.layout.simple_list_item_1,
		// getCameraImages(getApplicationContext()));
		final String[] projection = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.LONGITUDE,
				MediaStore.Images.Media.LATITUDE };
		final String selection = MediaStore.Images.Media.BUCKET_ID + " ="
				+ " ?";
		final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
		ContentResolver contentResolver = getApplicationContext().getContentResolver();

		final Cursor cursor = android.provider.MediaStore.Images.Media.query(
				contentResolver, Images.Media.EXTERNAL_CONTENT_URI, projection,
				selection, selectionArgs, null);

		adapter = new CLVAdapter(getApplicationContext(), R.layout.clv_row,
				getCameraImages(getApplicationContext(),android.provider.MediaStore.Images.Media.query(
						contentResolver, Images.Media.EXTERNAL_CONTENT_URI, projection,
						selection, selectionArgs, null)));

		photoList.setAdapter(adapter);
	}

	public static String getBucketId(String path) {
		return String.valueOf(path.toLowerCase().hashCode());
	}

	public static List<CLVRow> getCameraImages(Context context,Cursor cursor) {
		final String[] projection = { MediaStore.Images.Media.DATA,
				MediaStore.Images.Media.DISPLAY_NAME,
				MediaStore.Images.Media.DATE_TAKEN,
				MediaStore.Images.Media.SIZE,
				MediaStore.Images.Media.LONGITUDE,
				MediaStore.Images.Media.LATITUDE };
		final String selection = MediaStore.Images.Media.BUCKET_ID + " ="
				+ " ?";
		final String[] selectionArgs = { CAMERA_IMAGE_BUCKET_ID };
		ContentResolver contentResolver = context.getContentResolver();

		// ArrayList<String> result = new ArrayList<String>(cursor.getCount());
		List<CLVRow> res = new ArrayList<CLVRow>();

//		int dataColumn = cursor
//				.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

		if (cursor.moveToFirst()) {
			int nameColumn = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
		int dateTakenColumn = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_TAKEN);
		int fileSizeColumn = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);
		int lonColumn = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.LONGITUDE);
		int latColumn = cursor
				.getColumnIndexOrThrow(MediaStore.Images.Media.LATITUDE);

			do {

				String photoName = cursor.getString(nameColumn);

				//String photoUrl = cursor.getString(dataColumn);

				String milisecTime = cursor.getString(dateTakenColumn);
				long ms = Long.parseLong(milisecTime);
				Date d = new Date(ms);
				SimpleDateFormat formatter = new SimpleDateFormat(
						"hh:mm:ss, dd/mm/yy");
				String Time = formatter.format(d);

				String sizeString = cursor.getString(fileSizeColumn);
				Long sizeLong = Long.parseLong(sizeString);

				String lon = cursor.getString(lonColumn);
				String lat = cursor.getString(latColumn);
				// PhotoInfo pInfo = new PhotoInfo(photoName, photoUrl, Time,
				// lon,
				// lat, sizeLong);
				res.add(new CLVRow(null, photoName, "Taken Time: " + Time,
						"Size: " + sizeLong/1024 + "MB, Latitude: " + lat
								+ ", Longitude: " + lon));
			} while (cursor.moveToNext());

		}
		cursor.close();
		return res;
	}
}