package in.edu.atri.atriupdates;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class UncheckUpdate extends Activity{
	ListView lv;
	String rolltemp;
    String b1,b2,b3;
    ArrayList<String> al;
	int total = 0;
	ArrayAdapter<String> adapter;
    int i=0;
    Parcelable state;
    ArrayList<String> getroll = new ArrayList<String>();
    ArrayList<String> zeroonestr = new ArrayList<String>();
    ArrayList<String> onehr = new ArrayList<String>();
    int absNo=0,presNo=0;
	int[] btf= new int[100];
	String [] absRollsStr ;
	ArrayList<String> absstr= new ArrayList<String>();
	ArrayList<String> al2 = new ArrayList<String>();
	Button next;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uncheck_update);
		Bundle bundle = getIntent().getExtras();
		lv = (ListView) findViewById(R.id.idListView);
		final int hnum = bundle.getInt("hnum");
		final String bnum = bundle.getString("bnum");
		int btfsize = bundle.getInt("btfsize");
		final String year = bundle.getString("year");
		final String section = bundle.getString("section");
		final String date = bundle.getString("date");
		final String branch = bundle.getString("branch");
		final String period = bundle.getString("period");
		String clsname = bundle.getString("clsvar");
		//Button done = (Button) findViewById(R.id.bDone);
		next = (Button) findViewById(R.id.bnext);
		String respJ = bundle.getString("respJson");
		al = new ArrayList<String>();
		try {
			JSONArray arr = new JSONArray(respJ);
			for(int i=0;i<arr.length();i++){
			    JSONObject jObj = arr.getJSONObject(i);
			    String temp = jObj.getString(clsname);
			    if(!temp.equals("")){
			    	al.add(temp);
			    	Log.v("LIST:", "NameList: " + temp);
			    }
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
		
		String aptoal;
		for(i=0;i<al.size();i++){
			btf[i]=hnum;
		}
		for(i=0;i<al.size();i++){
			if(!al.get(i).equals("")){
				aptoal=al.get(i);
				al2.add(al.get(i));
				al.set(i, aptoal+"								"+Integer.toString(hnum));
				total++;
			}
			else{
				al.remove(i);
			}
		}	
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,al);
		lv.setAdapter(adapter);
		Log.v("Total:", Integer.toString(total));
		
		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i,long l) {
				// TODO Auto-generated method stub
				String selector = al2.get(i);
				if(btf[i]==hnum){
					btf[i]=0;
					absNo++;
					String subselector = selector+"								"+btf[i];
					al.set(i, subselector);
					((TextView)view).setText(selector+"								"+btf[i]);
					absstr.add(selector);
				}
				else if(btf[i]==0){
					btf[i]=1;
					absNo--;
					absstr.remove(selector);
					String subselector = selector+"								"+btf[i];
					al.set(i, subselector);
					((TextView)view).setText(selector+"								"+btf[i]);

				}
				else if(btf[i]==1 && hnum!=1){
					btf[i]=2;
					String subselector = selector+"								"+btf[i];
					al.set(i, subselector);
					((TextView)view).setText(selector+"								"+btf[i]);
				}
				else if(btf[i]==2 && hnum!=2){
					btf[i]=3;
					String subselector = selector+"								"+btf[i];
					al.set(i, subselector);
					((TextView)view).setText(selector+"								"+btf[i]);
				}
			}
		});
		
		next.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Collections.sort(absstr);
				String [] absRollsStr = absstr.toArray(new String[absstr.size()]);
				presNo = total - absNo;
				String[] poststr = new String[total];
				
				for(i=0;i<total;i++){
					poststr[i]=Integer.toString(btf[i]);
					Log.v("Post Str:", poststr[i]);
				}
				
				Intent conf = new Intent("android.intent.Confirm");
				conf.putExtra("absNo", absNo);
				conf.putExtra("presNo", total);
				conf.putExtra("zeroone",poststr);
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
		});
		
	}
	

}
