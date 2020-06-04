package in.edu.atri.atriupdates;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;



public class FacultyHome extends Activity{
    String urlmain="http://192.168.43.233:8080/rahul/totalrecords.php";
    String yearstr,semstr,secstr,branchstr,fromstr,tostr,bys,URL;
    String flagRec = "0", flagRolls = "0",bnum = "0";
    String toview,fromview;
    String urlrolls = "http://192.168.43.233:8080/rahul/ClassNamesList.php";
    String respJson = null;
	String[][] tablecontent = new String[10][70];
    int valcount = 0, rollscount = 0, col=0,row=0;
	EditText year,branch,sem,section,etfrom,etto;
	Button show;
	ArrayList<String> al = new ArrayList<String>();
	ArrayList<String> alvalues = new ArrayList<String>();
	ArrayList<String> alkeys = new ArrayList<String>();
	ArrayList<String> alsubs = new ArrayList<String>();
	DatePickerDialog date;
	Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faculty_home);
		year = (EditText) findViewById(R.id.etyear);
		branch = (EditText) findViewById(R.id.etbranch);
		sem = (EditText) findViewById(R.id.etsem);
		section = (EditText) findViewById(R.id.etsec);
		etfrom = (EditText) findViewById(R.id.etfrom);
		etto = (EditText) findViewById(R.id.etto);
		show = (Button) findViewById(R.id.bshow);
		calendar = Calendar.getInstance();
		
		
		
		
		final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		        calendar.set(Calendar.YEAR, year);
		        calendar.set(Calendar.MONTH, monthOfYear);
		        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel();
		    }

		};
		etfrom.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new DatePickerDialog(FacultyHome.this, date, calendar
		                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		                calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

		    @Override
		    public void onDateSet(DatePicker view, int year, int monthOfYear,
		            int dayOfMonth) {
		        // TODO Auto-generated method stub
		        calendar.set(Calendar.YEAR, year);
		        calendar.set(Calendar.MONTH, monthOfYear);
		        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		        updateLabel2();
		    }

		};
		etto.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				new DatePickerDialog(FacultyHome.this, date2, calendar
		                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
		                calendar.get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		year.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence yr[]={"1","2","3","4"};
				AlertDialog.Builder builder = new AlertDialog.Builder(FacultyHome.this);
        		builder.setTitle("Choose Year");
        		builder.setItems(yr, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	year.setText(yr[which]);
        		    }
        		});
        		builder.show();
			}
		});
		sem.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence seme[]={"1","2"};
				AlertDialog.Builder builder = new AlertDialog.Builder(FacultyHome.this);
        		builder.setTitle("Choose Semester");
        		builder.setItems(seme, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	sem.setText(seme[which]);
        		    }
        		});
        		builder.show();
			}
		});
		branch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence branchc[]={"CSE","IT","ECE","EEE","MECH","CE"};
				AlertDialog.Builder builder = new AlertDialog.Builder(FacultyHome.this);
        		builder.setTitle("Choose Branch");
        		builder.setItems(branchc, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	branch.setText(branchc[which]);
        		    }
        		});
        		builder.show();
			}
		});
		section.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence sect[]={"A","B","C","D"};
				AlertDialog.Builder builder = new AlertDialog.Builder(FacultyHome.this);
        		builder.setTitle("Choose Section");
        		builder.setItems(sect, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	section.setText(sect[which]);
        		    }
        		});
        		builder.show();
			}
		});
		
		show.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			
				
				new attrecords().execute();
				new GetList().execute();
				
				
		}
		});
	
	
	}
	
	private void updateLabel() {
	    String myFormat = "yyyy-MM-dd"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
	    
	    etfrom.setText(sdf.format(calendar.getTime()));
	    
	    String tof = "dd/MM";
	    SimpleDateFormat sdfto = new SimpleDateFormat(tof);
	    toview = sdfto.format(calendar.getTime());
	}
	
	private void updateLabel2() {
	    String myFormat = "yyyy-MM-dd"; //In which you need put here
	    SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
	    
	    etto.setText(sdf.format(calendar.getTime()));
	    
	    String fromf = "dd/MM";
	    SimpleDateFormat sdfto = new SimpleDateFormat(fromf);
	    fromview = sdfto.format(calendar.getTime());
	}
	
	public class attrecords extends AsyncTask<String, String, String> {
		 
        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }
 
        @Override
        protected String doInBackground(String... strings) {
        	
        	yearstr = year.getText().toString();
        	semstr = sem.getText().toString();
        	branchstr = branch.getText().toString();
        	fromstr = etfrom.getText().toString();
        	tostr = etto.getText().toString();
        	secstr = section.getText().toString();
        	bys = branchstr + yearstr + secstr;
        	
        	 Log.v("", "postURL: " + urlmain);
             
	            // HttpClient
	            HttpClient httpClient = new DefaultHttpClient();
	             
	            // post header
	            HttpPost httpPost = new HttpPost(urlmain);
	     
	            // add your data
	            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("bys",bys));
	            nameValuePairs.add(new BasicNameValuePair("year", yearstr));
	            nameValuePairs.add(new BasicNameValuePair("branch", branchstr));
	            nameValuePairs.add(new BasicNameValuePair("sem", semstr));
	            nameValuePairs.add(new BasicNameValuePair("to", tostr));
	            nameValuePairs.add(new BasicNameValuePair("from", fromstr));

	             
	            try {
					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	     
	            // execute HTTP post request
	            HttpResponse response = null;
				try {
					response = httpClient.execute(httpPost);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            HttpEntity resEntity = response.getEntity();
	             
	            if (resEntity != null) {
	                 
	                String responseStr = null;
					try {
						responseStr = EntityUtils.toString(resEntity).trim();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                Log.v("", "Response: " +  responseStr);
	                return responseStr;
	                // you can add an if statement here and do other actions based on the response
	            }
              
        
         return null;
        	
        	
        }
        
        @Override
        protected void onPostExecute(String resp) {
            // do stuff after posting data
        	//make a global string, set that string value to resp, do same for getrollslist and check if not null only then intent
        	if(!resp.equals("")){
        		String key = "";
        		JSONObject jObj = null;
        		flagRec = "1";
        		respJson = resp;
        		Log.v("RecFlag", flagRec);
        		try {
        				jObj = new JSONObject(resp);
        			    Iterator<String> keysIterator = jObj.keys();
        			    while (keysIterator.hasNext()) 
        			    {
        			            key = (String)keysIterator.next();
        			            if(!key.equals("")){
        			            	alsubs.add(key);
        			            	Log.v("JSON PARSING 1:", "Subjects: " + key);
        			            }
        			            
        			    }  
               	} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		valcount=0;     
        		try{
        			for(int i=0;i<1;i++){
        				JSONObject inJObj = jObj.getJSONObject(alsubs.get(i));
        				Iterator<String> vals = inJObj.keys();
        				while(vals.hasNext()){
        					String keyStr = (String)vals.next();
        					String valueStr = inJObj.getString(keyStr);
        					valcount++;
        				}
        			}
        		} catch (JSONException e) {
            		// TODO Auto-generated catch block
           			e.printStackTrace();
           		}
        		Log.v("ValsCount:",Integer.toString(valcount));
//Attendance values of all subjects array list initialization with valcount * 9 as 9 is max number of subjects
        		for(int i=0;i<alsubs.size()+1;i++){
        			for(int j=0;j<valcount+1;j++){
        				tablecontent[i][j]="x";
        			}
        		}
        		row = alsubs.size()+1;
        		col = valcount+1;
        		Log.v("ROW", Integer.toString(row));
        		Log.v("COLUMN", Integer.toString(col));
//alvalues may have all the att values of all subs in order of the subs arrlist, so valcount is imp if alvalues is right        		
        		for(int i=0;i<alsubs.size();i++){
        			try{
        				Log.v("Subject:", alsubs.get(i));
        				JSONObject inJObj = jObj.getJSONObject(alsubs.get(i));
        				for(int j=0;j<valcount-1;j++){
        					String valstr = inJObj.getString(Integer.toString(j));
        					tablecontent[i+1][j+2]= valstr;
        				}
        				String valn = inJObj.getString("n");
        				tablecontent[i+1][0]=alsubs.get(i);
        				tablecontent[i+1][1]= valn;
        			} catch (JSONException e) {
            			// TODO Auto-generated catch block
            			e.printStackTrace();
            		}
        		}
        		for(int i=0;i<alsubs.size();i++){
        			Log.v("Subject:", alsubs.get(i));
        			for(int j=0;j<valcount+1;j++){
        				Log.v("Values:", tablecontent[i+1][j]);
        			}
        		}
        		Log.v("Records Flag:", flagRec);
        	}
        }
    }
	
	public class GetList extends AsyncTask<String, String, String> {
		 
        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }
 
        @Override
        protected String doInBackground(String... strings) {
            try {
 
            	String postReceiverUrl = urlrolls;
   	         Log.v("Roll numbers:", "postURL: " + postReceiverUrl);
   	             
   	         // HttpClient
   	         HttpClient httpClient = new DefaultHttpClient();
   	             
   	         // post header
   	         HttpPost httpPost = new HttpPost(postReceiverUrl);
   	         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
   	            nameValuePairs.add(new BasicNameValuePair("clsvar",bys));
   	            nameValuePairs.add(new BasicNameValuePair("batch",bnum));
   	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	   	     
   	            // execute HTTP post request
   	            
   	            HttpResponse response = httpClient.execute(httpPost);

   	            HttpEntity resEntity = response.getEntity();
	            String responseStr = EntityUtils.toString(resEntity).trim();

   	             
   	            if (resEntity != null) {
   	            	
 
   	                Log.v("Rollno.", "Response: " +  responseStr);
   	            }                 
	                return responseStr;
   	            
   	            
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(String respo) {
            if(!respo.equals(null)){
            	flagRolls = "1";
            	Log.v("Rolls Flag:", flagRolls);
            	try {
        			JSONArray arr = new JSONArray(respo);
        			rollscount=0;
        			for(int i=0;i<arr.length();i++){
        			    JSONObject jObj = arr.getJSONObject(i);
        			    String temp = jObj.getString(bys);
        			    if(!temp.equals("")){
        			    	al.add(temp);
        			    	Log.v("LIST:", "NameList: " + temp);
        			    	rollscount++;
        			    }
        			}
        			Log.v("CountRolls:", Integer.toString(rollscount));
            	} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
            	for(int i=0;i<al.size();i++){
            		tablecontent[0][i+2]= al.get(i);
            	}
            	String dt = toview + "-" + fromview;
				tablecontent[0][0] = dt;
				tablecontent[0][1] = "Total Classes";
			/*	for(int i=0;i<row;i++){
            		Log.v("New Row",Integer.toString(i+1));
            		for(int j=0;j<col;j++){
            			Log.v("val", tablecontent[i][j]);
            		}
            	}
            	*/
				if(flagRec.equals("1") && flagRolls.equals("1")){
		            Intent att = new Intent("android.intent.Attendance");
		            Bundle bundle = new Bundle();
		            bundle.putSerializable("tablecontent", tablecontent);
		            att.putExtras(bundle);
		            att.putExtra("row", row);
		            att.putExtra("col", col);
		            startActivity(att);
				}
            }
        }
	}
	
	public void reload(){
		Intent intent = getIntent();
		overridePendingTransition(0,0);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		finish();
		overridePendingTransition(0,0);
		startActivity(intent);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		reload();
	}
	
	
}
	
