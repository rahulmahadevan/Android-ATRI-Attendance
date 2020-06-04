package in.edu.atri.atriupdates;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class ConfirmAttendance extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.confirm_attendance);
		EditText absentees = (EditText) findViewById(R.id.etAtt);
		Bundle bundle = getIntent().getExtras();
		int b1=0,b2=0,b3=0;	
		String bnum = bundle.getString("bnum");
		EditText yearET,branchET,semET,secET,periodET,dateET,presET,facname;
        facname = (EditText) findViewById(R.id.editText7);
        facname.setOnKeyListener(null);
        EditText topcov,topsch;
        topcov = (EditText) findViewById(R.id.editText8);
        topcov.setOnKeyListener(null);
        topsch = (EditText) findViewById(R.id.editText9);
        topsch.setOnKeyListener(null);
		presET = (EditText) findViewById(R.id.editText11);
		yearET = (EditText) findViewById(R.id.etYear);
		EditText hoursET = (EditText) findViewById(R.id.ethrs);
		branchET = (EditText) findViewById(R.id.e);
		Button showabs = (Button) findViewById(R.id.bShowAbs);
		semET = (EditText) findViewById(R.id.etSem);
		secET = (EditText) findViewById(R.id.editText3);
		periodET = (EditText) findViewById(R.id.editText2);
		dateET = (EditText) findViewById(R.id.etDate);
		int abs = bundle.getInt("absNo");
		int total = bundle.getInt("presNo");
		int pres = total-abs;
		int hnum = bundle.getInt("hnum");
		hoursET.setText(Integer.toString(hnum));
		String b1str,b2str,b3str;
		if(!bnum.equals("0")){
			b1str = bundle.getString("b1");
			b2str = bundle.getString("b2");
			b3str = bundle.getString("b3");
			b1 = Integer.parseInt(b1str);
			b2 = Integer.parseInt(b2str);
			b3 = Integer.parseInt(b3str);
		}
		hoursET.setOnKeyListener(null);
		String hnums = Integer.toString(hnum);
		String[] zeroone = bundle.getStringArray("zeroone");
		absentees.setText(String.valueOf(abs));
		absentees.setKeyListener(null);
		String year = bundle.getString("year");
		String sem = bundle.getString("sem");
		String branch = bundle.getString("branch");
		String date = bundle.getString("date");
		String section = bundle.getString("section");
		String period = bundle.getString("period");
		final String[] absList = bundle.getStringArray("absStr");
		final String clsname = branch+year+section;
		yearET.setText(year);
		presET.setText(String.valueOf(pres));
		semET.setText(sem);
		branchET.setText(branch);
		dateET.setText(date);
		secET.setText(section);
		periodET.setText(period);
		yearET.setKeyListener(null);
		branchET.setKeyListener(null);
		dateET.setKeyListener(null);
		secET.setKeyListener(null);
		semET.setKeyListener(null);
		periodET.setKeyListener(null);
		presET.setKeyListener(null);
		int bno = Integer.parseInt(bnum);
		
		int temp,totalb;
		ArrayList<String> presabsal = new ArrayList<String>();
	    String finalpoststr = "('"+date+"','"+period+"'";
	    
		Log.v("Total", Integer.toString(total));
		
		if(bnum.equals("0")){
			for(int i = 0;i<total;i++){
				finalpoststr = finalpoststr + ",'" + zeroone[i] + "'";
			}
			finalpoststr = finalpoststr + ",'" + hnum + "')";
		}
		
		
		/*
		
		
		}
		
		Log.v("Batch Number:", bnum);
		*/
		else if(!bnum.equals("0")){
			for(int i=0;i<zeroone.length;i++){
				presabsal.add(zeroone[i]);
			}
			
		    	if(bnum.equals("1")){
		    		totalb = b1+b2+b3;
		    		for(int i=b1;i<totalb;i++){
		    			presabsal.add(i, "0");
		    		}
		    	}
		    	else if(bnum.equals("2")){
		    		for(int i=0;i<b1;i++){
		    			presabsal.add(i, "0");
		    		}
		    		totalb = b1+b2+b3;
		    		for(int i=b1+b2;i<totalb;i++){
		    			presabsal.add(i,"0");
		    		}
		    	}
		    	else if(bnum.equals("3")){
		    		totalb = b1+b2;
		    		for(int i=0;i<totalb;i++){
		    			presabsal.add(i, "0");
		    		}
		    	}
		    	
		    	Log.v("Presabsal Size:", Integer.toString(presabsal.size()));
				
				String [] onezerostr = presabsal.toArray(new String[presabsal.size()]);
				String str = Arrays.toString(zeroone);

				
				Log.v("THE STR", finalpoststr);
				
		    	
		    	for(int i=0;i<b1+b2+b3;i++){
					finalpoststr =  finalpoststr +","+ onezerostr[i] + "" ;
				}
				finalpoststr = finalpoststr + ",'" + hnum + "')";
			
				Log.v("FINAL POST STRING:", finalpoststr);
		 }		
		
		
		final String finalfinalpoststr = finalpoststr;
		
		showabs.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				AlertDialog.Builder alertDialog = new AlertDialog.Builder(ConfirmAttendance.this);
				LayoutInflater inflater = getLayoutInflater();
				View convertView = (View) inflater.inflate(R.layout.absentees_finallist, null);
				alertDialog.setView(convertView);
				alertDialog.setTitle("Absentees List");
				ListView lv = (ListView) convertView.findViewById(R.id.listView1);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConfirmAttendance.this,android.R.layout.simple_list_item_1,absList);
				lv.setAdapter(adapter);
				alertDialog.show();
			}
		});
		
		Button confirm,back;
		confirm = (Button) findViewById(R.id.bConfirm);
		back = (Button) findViewById(R.id.bBack);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		confirm.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent facConf= new Intent("android.intent.FConfirmation");
				facConf.putExtra("clsname", clsname);
				facConf.putExtra("fpost", finalfinalpoststr);
				startActivity(facConf);
			}
		});
		
		
	}
	
}

/*else if(bnum.equals("0")){
	for(int i=0;i<total;i++){
		finalpoststr =  finalpoststr +","+ onezerostr[i] + "" ;
	}
	finalpoststr = finalpoststr + ")";

	Log.v("FINAL POST STRING:", finalpoststr);
}
*/