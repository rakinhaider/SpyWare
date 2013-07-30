package com.BUET_Arbirary.SpyWare;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ContactListDetailsShow extends Activity{
	
	private TextView tvDetails;
	private Button btnBack;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_details_show);
		
		btnBack=(Button)findViewById(R.id.btnBackContactDetailsShow);
		btnBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		tvDetails=(TextView)findViewById(R.id.tvContactDetails);
		tvDetails.setText("\tContact Details\n");
		
		Intent intentDetails=getIntent();
		tvDetails.append("Name: "+intentDetails.getStringExtra("name")+"\n");
		tvDetails.append("Phone Number: "+intentDetails.getStringExtra("phoneNum")+"\n");
		
		ArrayList<String> emails=intentDetails.getStringArrayListExtra("emails");
		int len=emails.size();
		String emailStr="";
		for (int i = 0; i < len; i++) {
			emailStr += emails.get(i);
			if ((i + 1) < len)
				emailStr += ", ";
		}
		tvDetails.append("Email Address(s): "+emailStr+"\n");
	}

}
