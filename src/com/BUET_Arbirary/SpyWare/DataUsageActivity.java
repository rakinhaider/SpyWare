package com.BUET_Arbirary.SpyWare;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.net.TrafficStats;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DataUsageActivity extends Activity  implements OnClickListener{
	private TextView textdata;
	private TextView textnewdata;
	private TextView txtcompare;
	private Button btnBack,btnsave;
	private DataBaseOpenHelper  db;
	long received=0;
	long sent=0;
	long rpacket=0;
	long spacket=0;
	String date;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datausage);
        textdata=(TextView)findViewById(R.id.tvdata);
        textnewdata=(TextView)findViewById(R.id.tvnewdata);
        txtcompare=(TextView)findViewById(R.id.tvcompare);
        btnsave=(Button)findViewById(R.id.btnsavew);
        btnBack=(Button)findViewById(R.id.btnBackData);
        btnBack.setOnClickListener(this);
        //textdata.append("    Data Usage: "+ TrafficStats.getMobileRxBytes()+"fff"+TrafficStats.getMobileRxPackets());
        received = this.getNetworkRxBytes();
         sent = this.getNetworkTxBytes();
         rpacket=this.getNetworkrcvpackets();
         spacket=this.getNetworksentpackets();
          
        if (received == TrafficStats.UNSUPPORTED) {
            Log.d("test", "TrafficStats is not supported in this device.");
        } else {
            textdata.append("bytes received via WiFi/LAN: " + received);
            textdata.append("\nbytes sent via WiFi/LAN: " + sent);
            textdata.append("\npackets sent via WiFi/LAN: " + rpacket);
            textdata.append("\npackets sent via WiFi/LAN: " +spacket+"\n\n\n");
        }
        btnsave.setOnClickListener(this);
        db=new DataBaseOpenHelper(this);
        int j=db.chk();
        date="";
        Calendar cld=Calendar.getInstance();
		date=date+cld.get(Calendar.YEAR)+(cld.get(Calendar.MONTH)+1)+cld.get(Calendar.DAY_OF_MONTH);
        if(j==0)
        	db.setdata(date,""+received,""+sent,""+rpacket,""+spacket);
        
        ArrayList<String> savedlist=db.getdata();
        textnewdata.append("\nLast modified date:"+savedlist.get(0));
        textnewdata.append("\nbytes received via WiFi/LAN: " +savedlist.get(1)+"bytes");
        textnewdata.append("\nbytes sent via WiFi/LAN: " + savedlist.get(2)+"bytes");
        textnewdata.append("\npackets  received " + savedlist.get(3));
        textnewdata.append("\npackets sent : " +savedlist.get(4)+"\n\n");
        
        long trb=Long.parseLong(savedlist.get(1));
        long tsb=Long.parseLong(savedlist.get(2));
        long pr=Long.parseLong(savedlist.get(3));
        long ps=Long.parseLong(savedlist.get(4));
        txtcompare.append("\ntotal received bytes: "+ (received-trb));
        txtcompare.append("\nbytes sent via WiFi/LAN: " + (sent-tsb));
        txtcompare.append("\npackets  received " + (rpacket-pr));
        txtcompare.append("\npackets sent : " +(spacket-ps));
        
        
        
        
    } 
    private String getNetworkInterface() {
        String wifiInterface = null;
        try {
            Class<?> system = Class.forName("android.os.SystemProperties");
            Method getter = system.getMethod("get", String.class);
            wifiInterface = (String) getter.invoke(null, "wifi.interface");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (wifiInterface == null || wifiInterface.length() == 0) {
            wifiInterface = "eth0";
        }
        return wifiInterface;
    }
    private long readLongFromFile(String filename) {
        RandomAccessFile f = null;
        try {
            f = new RandomAccessFile(filename, "r");
            String contents = f.readLine();
            if (contents != null && contents.length() > 0) {
                return Long.parseLong(contents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (f != null) try { f.close(); } catch (Exception e) { e.printStackTrace(); }
        }
        return TrafficStats.UNSUPPORTED;
    }
    private long getNetworkTxBytes() {
        String txFile = "sys/class/net/" + this.getNetworkInterface() + "/statistics/tx_bytes";
        return readLongFromFile(txFile);
    }
    private long getNetworkRxBytes() {
        String rxFile = "sys/class/net/" + this.getNetworkInterface() + "/statistics/rx_bytes";
        return readLongFromFile(rxFile);
    }
    private long getNetworkrcvpackets() {
        String rxFile = "sys/class/net/" + this.getNetworkInterface() + "/statistics/rx_packets";
        return readLongFromFile(rxFile);
    }
    private long getNetworksentpackets() {
        String rxFile = "sys/class/net/" + this.getNetworkInterface() + "/statistics/tx_packets";
        return readLongFromFile(rxFile);
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId()==R.id.btnsavew)
		{
			db.setdata(date,""+received,""+sent, ""+rpacket,""+ spacket);
			Toast.makeText(getApplicationContext(),"data saved",Toast.LENGTH_LONG).show();
		}
		else if(v.getId()==R.id.btnBackData)
			finish();
	}
}
