package in.edu.atri.atriupdates;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TableRow;


public class UncheckAbsentees extends Activity{
	int temp;
	String tempstr;
    private static String TAG = "UncheckAbsentees";
    ArrayList<String> getroll = new ArrayList<String>();
    ArrayList<String> zeroonestr = new ArrayList<String>();
    ArrayList<String> onehr = new ArrayList<String>();
	ArrayList<String> hrcount = new ArrayList<String>();
    String rolltemp;
    String b1,b2,b3;
    int i=0;
    int absNo=0,presNo=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uncheck_absentees);
		Bundle bundle = getIntent().getExtras();
		int arrcount;
		final String bnum = bundle.getString("bnum");
		final int hnum = bundle.getInt("hnum");
		final String year = bundle.getString("year");
		final String section = bundle.getString("section");
		final String date = bundle.getString("date");
		final String branch = bundle.getString("branch");
		final String period = bundle.getString("period");
		String clsname = bundle.getString("clsvar");
		//Button done = (Button) findViewById(R.id.bDone);
		String respJ = bundle.getString("respJson");
		ArrayList<String> al = new ArrayList<String>();
		try {
			JSONArray arr = new JSONArray(respJ);
			for(int i=0;i<arr.length();i++){
			    JSONObject jObj = arr.getJSONObject(i);
			    String temp = jObj.getString(clsname);
			    al.add(temp);
	                Log.v(TAG, "NameList: " + temp);

			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(!bnum.equals("0")){
			b3 = al.get(al.size()-1);
			al.remove(al.size()-1);
			Log.v("b3", b3);
			b2 = al.get(al.size()-1);
			al.remove(al.size()-1);
			Log.v("b2", b2);
			b1 = al.get(al.size()-1);
			al.remove(al.size()-1);
			Log.v("b1", b1);
		}
		String [] nameslist = al.toArray(new String[al.size()]);
		arrcount = nameslist.length;
		int inc=0;
		LinearLayout myLay = (LinearLayout) findViewById(R.id.myL);
		for( int i=0;i<arrcount;i++){
			if(!nameslist[i].equals("")){
				inc++;
			TableRow row =new TableRow(this);
		    row.setId(i);
		    row.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
		    final CheckBox checkBox = new CheckBox(this);
		    checkBox.setId(i);
		    checkBox.setText(nameslist[i]);
		    checkBox.setChecked(true);
		    row.addView(checkBox);  
		    myLay.addView(row);
		    checkBox.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(checkBox.isChecked()){
						temp = checkBox.getId();
						zeroonestr.remove(Integer.toString(temp));
						getroll.remove(checkBox.getText().toString());
						absNo--;
						temp=-1;	
						checkBox.toggle();
					}
					else{
						if(Integer.toString(hnum).equals("2")){
							temp = checkBox.getId();
							hrcount.add(checkBox.getText().toString());
							checkBox.setText(checkBox.getText().toString()+" - 1 hour only");
							checkBox.setAlpha(1);
							onehr.add(Integer.toString(temp));
							rolltemp = checkBox.getText().toString();
							getroll.add(rolltemp);
							absNo++;
							temp=-1;
						}
						
					}
				}
			});
		    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
					// TODO Auto-generated method stub
					
				}
			});
		    
			
		    
			}
		}
	
		presNo = inc;
		/*done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String [] absRollsStr = getroll.toArray(new String[getroll.size()]);
				Intent conf = new Intent("android.intent.Confirm");
				Collections.sort(zeroonestr);
				String [] onezerostr = zeroonestr.toArray(new String[zeroonestr.size()]);
				conf.putExtra("zeroone", onezerostr);
				conf.putExtra("absNo", absNo);
				conf.putExtra("presNo", presNo);
				conf.putExtra("absStr", absRollsStr);
				conf.putExtra("hnum",hnum);
				conf.putExtra("year", year);
				conf.putExtra("branch",branch);
				conf.putExtra("b1", b1);
				conf.putExtra("b2", b2);
				conf.putExtra("b3", b3);
				conf.putExtra("bnum", bnum);
				conf.putExtra("section", section);
				conf.putExtra("date", date);
				conf.putExtra("period", period);
				conf.putExtra("sem", "2");
				startActivity(conf);
			}
		});*/
	}
		

}
