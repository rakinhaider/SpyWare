package com.BUET_Arbirary.SpyWare;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CLVAdapter extends ArrayAdapter<CLVRow> {

	private Context thisContext;

	public CLVAdapter(Context context, int textViewResourceId,
			List<CLVRow> items) {
		super(context,textViewResourceId,items);
		thisContext=context;
	}
	
	private class ViewHolder{
		ImageView ivCLV;
		TextView tvHeadline,tv2ndLine,tv3rdLine;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;

		LayoutInflater mInflater = (LayoutInflater) thisContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.clv_row, null);
			holder=new ViewHolder();
			holder.ivCLV=(ImageView)convertView.findViewById(R.id.ivCLV);
			holder.tvHeadline=(TextView)convertView.findViewById(R.id.tvClvHeadline);
			holder.tv2ndLine=(TextView)convertView.findViewById(R.id.tvClv2ndLine);
			holder.tv3rdLine=(TextView)convertView.findViewById(R.id.tvClv3rdLine);
			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();
		
		CLVRow rowItem = getItem(position);
		
		//ImageView imgView=rowItem.getIvCLV();
		//if(id==null)id=0;
		Integer id=rowItem.getIntCLV();
		if(id==null)id=0;
		holder.ivCLV.setImageResource(id); 
		//Log.d("Inside getView(): CLVAdapter",id+"");
		
		holder.tvHeadline.setText(rowItem.getHeadline());
		holder.tv2ndLine.setText(rowItem.get2ndLine());
		holder.tv3rdLine.setText(rowItem.get3rdLine());
		
		return convertView;
	}

}
