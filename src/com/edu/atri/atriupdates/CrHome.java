package in.edu.atri.atriupdates;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CrHome extends Activity {
	final String syear[] = {""},ssem[]={""}, sbranch[]={""}, ssec[]={""};
	EditText year, branch, section, date, period, sem,hours, subname,fac;
    String urlmain="http://192.168.43.233:8080/rahul/ClassNamesList.php";
	   protected static final String TAG = "CrHome"; 
	   int hnum;
	   String bnum = "0",subjname;
	   String yearp,branchp,sectionp,clsvar,datep,semp,periodp,clssub,flagT="0",flagH="0",flagS="0",TorL,batch;
	   


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cr_home);
		String yearL = null,secL = null,branchL = null;
		Button atten;
		Bundle bundle= getIntent().getExtras();
		String jsonresp = bundle.getString("respJS");
		fac = (EditText) findViewById(R.id.editText7);
		subname = (EditText) findViewById(R.id.subname);
		year = (EditText) findViewById(R.id.editText1);
		branch = (EditText) findViewById(R.id.editText4);
		hours = (EditText) findViewById(R.id.showsubs);
		section = (EditText) findViewById(R.id.editText5);
		sem = (EditText) findViewById(R.id.editText2);
		date = (EditText) findViewById(R.id.editText3);
		fac.setOnKeyListener(null);
		period = (EditText) findViewById(R.id.editText6);
		atten = (Button) findViewById(R.id.bAtten);
	
		
		
		try {
			JSONArray arr = new JSONArray(jsonresp);
			for(int i=0;i<arr.length();i++){
			    JSONObject jObj = arr.getJSONObject(i);
			    yearL = jObj.getString("year");
			    branchL = jObj.getString("branch");
			    secL = jObj.getString("section");
	                Log.v(TAG, "ClassName: " + branchL+yearL+secL);
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		year.setText(yearL);
		year.setKeyListener(null);
		branch.setText(branchL);
		branch.setKeyListener(null);
		section.setText(secL);
		sem.setText("2");
		sem.setKeyListener(null);
		section.setKeyListener(null);
		
		Date now = new Date();
//		Date alsoNow = Calendar.getInstance().getTime();
		String dateStr = new SimpleDateFormat("dd-MM-yy").format(now);
		date.setText(dateStr);
		date.setKeyListener(null);
		
		period.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final CharSequence torl[]={"Theory","LAB"};
				AlertDialog.Builder builder = new AlertDialog.Builder(CrHome.this);
        		builder.setTitle("Choose Type of Class");
        		builder.setItems(torl, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	period.setText(torl[which]);
        		    	hours.setText("");
        				subname.setText("");
        				bnum="0";
        				flagH="0";
        				TorL = period.getText().toString();
        		    	flagT="1";
        		    }
        		});
        		builder.show();
				
			}
		});
		
		hours.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(flagT.equals("1")){
					if(TorL.equals("Theory")){	
						flagH="0";
						final CharSequence hrs[]={"1","2"};
						AlertDialog.Builder builder = new AlertDialog.Builder(CrHome.this);
						builder.setTitle("Single or Double Hour?");
						builder.setItems(hrs, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// the user clicked on subs[which]
								hours.setText(hrs[which]);
								if(hrs[which].equals("1")){
									hnum = 1;
								}
								else{
									hnum = 2;
								}
								flagH="1";
							}
						});
						builder.show();
					}
					else if(TorL.equals("LAB")){
						flagH="0";
						final CharSequence hrs[]={"B1","B2","B3"};
						AlertDialog.Builder builder = new AlertDialog.Builder(CrHome.this);
						builder.setTitle("Select Batch Number");
						builder.setItems(hrs, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// the user clicked on subs[which]
								hours.setText(hrs[which]);
								batch = hours.getText().toString();
								flagH="1";
							}
						});
						builder.show();
					}
				}
				else{
					Toast.makeText(getApplicationContext(), "Select Theory or Lab first", Toast.LENGTH_LONG).show();
					
				}
			}
		});
		
		subname.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(flagH.equals("1")){
					yearp = year.getText().toString();
					branchp = branch.getText().toString();
					semp = sem.getText().toString();
					if(TorL.equals("Theory")){
						clssub = branchp+yearp+"-"+semp;
						bnum = "0";
					}
					else if(TorL.equals("LAB")){
						hnum=3;
						clssub = branchp+yearp+"-"+semp+"l";
						if(batch.equals("B1")){
							bnum = "1";
						}
						else if(batch.equals("B2")){
							bnum = "2";
						}
						else if(batch.equals("B3")){
							bnum = "3";
						}
					}
					new GetSubs().execute();
				}
				else
					Toast.makeText(getApplicationContext(), "Select all fields in Period", Toast.LENGTH_LONG).show();
			}
		});
		
		
		atten.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(subname.getText().toString().equals("") || hours.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Select Subject", Toast.LENGTH_LONG).show();
				}
				else{
					yearp= year.getText().toString();
					branchp = branch.getText().toString();
					sectionp = section.getText().toString();
					clsvar = branchp+yearp+sectionp;
					new GetList().execute();
				}
				
			}
		});
		
		
		
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		

	}
	
	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		bnum="0";
	}



	public class GetList extends AsyncTask<String, String, String> {
		 
        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }
 
        @Override
        protected String doInBackground(String... strings) {
            try {
 
            	String postReceiverUrl = urlmain;
   	         Log.v(TAG, "postURL: " + postReceiverUrl);
   	             
   	         // HttpClient
   	         HttpClient httpClient = new DefaultHttpClient();
   	             
   	         // post header
   	         HttpPost httpPost = new HttpPost(postReceiverUrl);
   	         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
   	            nameValuePairs.add(new BasicNameValuePair("clsvar",clsvar));
   	            nameValuePairs.add(new BasicNameValuePair("batch",bnum));
   	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	   	     
   	            // execute HTTP post request
   	            
   	            HttpResponse response = httpClient.execute(httpPost);

   	            HttpEntity resEntity = response.getEntity();
	            String responseStr = EntityUtils.toString(resEntity).trim();

   	             
   	            if (resEntity != null) {
   	            	
 
   	                Log.v(TAG, "Response: " +  responseStr);
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
            	if(flagS.equals("1")){	
            		Intent att = new Intent("android.intent.Uncheck");
        			try {
						JSONArray arr = new JSONArray(respo);
						att.putExtra("btfsize", arr.length());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
            		yearp=year.getText().toString();
            		att.putExtra("year", yearp);
            		datep = date.getText().toString();
            		att.putExtra("date", datep);
            		branchp = branch.getText().toString();
            		att.putExtra("branch", branchp);
            		att.putExtra("hnum",hnum);
            		att.putExtra("bnum",bnum);
            		sectionp = section.getText().toString();
            		att.putExtra("section", sectionp);
            		periodp= period.getText().toString();
            		att.putExtra("period", subjname);
            		att.putExtra("clsvar", clsvar);
            		att.putExtra("respJson", respo);
            		startActivity(att);
            	}
            	else{
            		Toast.makeText(getApplicationContext(), "Select Subject", Toast.LENGTH_LONG).show();
            	}
        	}
        }
    }    
	
	public class GetSubs extends AsyncTask<String, String, String> {
		 
        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }
 
        @Override
        protected String doInBackground(String... strings) {
            try {
 
            	String postReceiverUrl = "http://192.168.43.233:8080/rahul/subs.php" ;
   	         Log.v(TAG, "postURL: " + postReceiverUrl);
   	             
   	         // HttpClient
   	         HttpClient httpClient = new DefaultHttpClient();
   	             
   	         // post header
   	         HttpPost httpPost = new HttpPost(postReceiverUrl);
   	         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
   	            nameValuePairs.add(new BasicNameValuePair("clssub",clssub));
   	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	   	     
   	            // execute HTTP post request
   	            
   	            HttpResponse response = httpClient.execute(httpPost);

   	            HttpEntity resEntity = response.getEntity();
	            String responseStr = EntityUtils.toString(resEntity).trim();

   	             
   	            if (resEntity != null) {
   	            	
 
   	                Log.v(TAG, "Response: " +  responseStr);
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
        		ArrayList<String> al = new ArrayList<String>();
        		try {
        			JSONArray arr = new JSONArray(respo);
        			for(int i=0;i<arr.length();i++){
        			    JSONObject jObj = arr.getJSONObject(i);
        			    String temp = jObj.getString(clssub);
        			    if(!temp.equals(""))
        			    	al.add(temp);
        	                Log.v(TAG, "SubName: " + temp);

        			}

        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		String [] Subslist = al.toArray(new String[al.size()]);
        		final CharSequence subs[] = (CharSequence[]) Subslist;
        		AlertDialog.Builder builder = new AlertDialog.Builder(CrHome.this);
        		builder.setTitle("Pick a Subject");
        		builder.setItems(subs, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	subname.setText(subs[which]);
        		    	subjname = subname.getText().toString();
        		    	flagS = "1";
        		    }
        		});
        		builder.show();

        	}
        }
    }    
	
	

}
