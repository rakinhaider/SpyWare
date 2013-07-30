package com.BUET_Arbirary.SpyWare;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class ApplicationlistActivity extends TabActivity {
    /** Called when the activity is first created. */
	
	Button btnBack;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.applist_activity);
        //Resources res=getResources();
        
        btnBack=(Button)findViewById(R.id.btnBackApplicationList);
        btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
        
        TabHost tabHost = getTabHost(); 
        
        TabHost.TabSpec spec;
        
        Intent intent=new Intent().setClass(this, Installed.class);
        spec=tabHost.newTabSpec("Installed").setIndicator("Installed App",null).setContent(intent);
        
        tabHost.addTab(spec);
        
        intent=new Intent().setClass(this, Running.class);
        spec=tabHost.newTabSpec("Running").setIndicator("Running App",null).setContent(intent);
        
        //tabHost.getTabWidget().getChildAt(0).setBackgroundColor(Color.BLUE);
//        TextView textView = (TextView) tabHost.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
//        textView.setTextColor(Color.WHITE);
        //tabHost.getTabWidget().getChildAt(1).setBackgroundColor(Color.BLUE);
//        textView = (TextView) tabHost.getTabWidget().getChildAt(1).findViewById(android.R.id.title);
//        textView.setTextColor(Color.WHITE);
        
        tabHost.addTab(spec);
        
        tabHost.setCurrentTab(0);
    }
}