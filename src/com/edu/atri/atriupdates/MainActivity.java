package in.edu.atri.atriupdates;

import java.util.ArrayList;
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
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	   protected static final String TAG = "MainActivity";
	EditText idField,passwordField;	
       Button login;
       TextView resp;
       int flag=0;
       String id,password,getid="";
       String urlmain="http://192.168.43.233:8080/rahul/login.php";
       
       //Do something to show if the device is connected to the Internet

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
  	    idField = (EditText) findViewById(R.id.etId);
        passwordField = (EditText) findViewById(R.id.etPass);
        login = (Button) findViewById(R.id.bLogin);
        final RadioButton std;
		final RadioButton fac;
        std = (RadioButton) findViewById(R.id.radio0);
        fac = (RadioButton) findViewById(R.id.radio1);
        Button reg = (Button) findViewById(R.id.bReg);
        reg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
			    Intent regpage = new Intent("android.intent.Register");
			    startActivity(regpage);
			}
		});
        
        login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				id = idField.getText().toString();
				password = passwordField.getText().toString();
			// TODO Auto-generated method stub
				if(std.isChecked()){
					//check if the Id and Password match with details in Students table of the DB
					new checkLogin().execute();
					
				 
				}
				else if(fac.isChecked()){
					//check if the Id and Password match with details in Faculty table of the DB
					Intent fachome= new Intent("android.intent.Faculty");
					startActivity(fachome);
				}
			  }	
			  
		});
        
    }
    
     
    public class checkLogin extends AsyncTask<String, String, String> {
		 
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
   	            nameValuePairs.add(new BasicNameValuePair("id",id));
   	            nameValuePairs.add(new BasicNameValuePair("password", password));
   	            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
   	   	     
   	            // execute HTTP post request
   	            
   	            HttpResponse response = httpClient.execute(httpPost);

   	            HttpEntity resEntity = response.getEntity();
   	             
   	            if (resEntity != null) {
   	                 
   	                String responseStr = EntityUtils.toString(resEntity).trim();
   	                Log.v(TAG, "Response: " +  responseStr);
   	                return responseStr;// you can add an if statement here and do other actions based on the response
   	            }                 
            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
 
        @Override
        protected void onPostExecute(String respo) {
            if(!respo.equals("Invalid Details")){
					Intent crhome= new Intent("android.intent.Cr");
					crhome.putExtra("respJS", respo);
					startActivity(crhome);
			  }
			  else{
				  Toast.makeText(getApplicationContext(), "Invalid login details", Toast.LENGTH_LONG).show();
			  }
			
        }
    }    
  
    

    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}




	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
}

