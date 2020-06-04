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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterPage extends Activity {
	
	EditText nameET,idET,clsET,passET,confirmpassET,secET,branchET;
	String name,id,cls,password,confirmpass,sec,branch;
	Button regButton;
	String flag="0";
	String urlmain="http://192.168.43.233:8080/rahul/register.php";
    private static final String TAG = "RegisterPage.java";
    int resp=0;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_page);
		nameET = (EditText) findViewById(R.id.etName);
		idET = (EditText) findViewById(R.id.etId);
		clsET = (EditText) findViewById(R.id.etClass);
		secET= (EditText) findViewById(R.id.etSec);
		passET = (EditText) findViewById(R.id.etPass);
		branchET= (EditText) findViewById(R.id.etbranch);
		confirmpassET = (EditText) findViewById(R.id.etConfirmPass);
        regButton = (Button) findViewById(R.id.bRegister);
        regButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				password=passET.getText().toString();
		        confirmpass = confirmpassET.getText().toString();
		        if(nameET.getText().toString().equals("") || idET.getText().toString().equals("") || clsET.getText().toString().equals("") || branchET.getText().toString().equals("") || secET.getText().toString().equals("") || passET.getText().toString().equals("")){
		        	Toast.makeText(getApplicationContext(), "Enter details in all Fields", Toast.LENGTH_LONG).show();
		        }
		        else{
		        	if(password.equals(confirmpass)){
		        		new PostDataAsyncTask().execute();
		        		if(flag.equals("1")){
		        			onBackPressed();
		        			clearfields();
		        			//CHECK response and adjust
		        			Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
		        			finish();
		        		}
		        	}	
		        	else{
		        		Toast.makeText(getApplicationContext(), "Passwords donot match!",Toast.LENGTH_LONG).show();
		        	}	          
		        }
			}
		});
        
        clsET.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence yr[]={"1","2","3","4"};
				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage.this);
        		builder.setTitle("Choose Year");
        		builder.setItems(yr, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	clsET.setText(yr[which]);
        		    }
        		});
        		builder.show();
			}
		});
        
        branchET.setOnClickListener(new View.OnClickListener() {
			
        		@Override
        	public void onClick(View arg0) {
        		// TODO Auto-generated method stub
        		final CharSequence branchc[]={"CSE","IT","ECE","EEE","MECH","CE"};
        		AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage.this);
        		builder.setTitle("Choose Branch");
        		builder.setItems(branchc, new DialogInterface.OnClickListener() {
        			@Override
        			public void onClick(DialogInterface dialog, int which) {
        				// the user clicked on subs[which]
        				branchET.setText(branchc[which]);
        			}
        		});
        		builder.show();
        	}
		});
        
        secET.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final CharSequence sect[]={"A","B","C","D"};
				AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPage.this);
        		builder.setTitle("Choose Section");
        		builder.setItems(sect, new DialogInterface.OnClickListener() {
        		    @Override
        		    public void onClick(DialogInterface dialog, int which) {
        		        // the user clicked on subs[which]
        		    	secET.setText(sect[which]);
        		    }
        		});
        		builder.show();
			}
		});
	    
	}
	
	
	 public class PostDataAsyncTask extends AsyncTask<String, String, String> {
		 
	        protected void onPreExecute() {
	            super.onPreExecute();
	            // do stuff before posting data
	        }
	 
	        @Override
	        protected String doInBackground(String... strings) {
	            try {
	 
	                // 1 = post text data, 2 = post file
	            	name=nameET.getText().toString();
		    		id=idET.getText().toString();
		    		cls=clsET.getText().toString();
		    		branch = branchET.getText().toString();
		    		password=passET.getText().toString();
		            // url where the data will be posted
		    		if(branch.equals("CSE") || branch.equals("ECE"))
			    		sec = secET.getText().toString();
		    		else
		    			sec = "";
		            String postReceiverUrl = urlmain;
		            Log.v(TAG, "postURL: " + postReceiverUrl);
		             
		            // HttpClient
		            HttpClient httpClient = new DefaultHttpClient();
		             
		            // post header
		            HttpPost httpPost = new HttpPost(postReceiverUrl);
		     
		            // add your data
		            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		            nameValuePairs.add(new BasicNameValuePair("id",id));
		            nameValuePairs.add(new BasicNameValuePair("year", cls));
		            nameValuePairs.add(new BasicNameValuePair("branch", branch));
		            nameValuePairs.add(new BasicNameValuePair("section", sec));
		            nameValuePairs.add(new BasicNameValuePair("password", password));

		             
		            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		     
		            // execute HTTP post request
		            HttpResponse response = httpClient.execute(httpPost);
		            HttpEntity resEntity = response.getEntity();
		             
		            if (resEntity != null) {
		                 
	   	                String responseStr = EntityUtils.toString(resEntity).trim();
		                Log.v(TAG, "Response: " +  responseStr);
		                return responseStr;
		                // you can add an if statement here and do other actions based on the response
		            }
	                 
	            } catch (NullPointerException e) {
	                e.printStackTrace();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            return null;
	        }
	 
	        @Override
	        protected void onPostExecute(String resp) {
	            // do stuff after posting data
	        	if(resp.equals("0")){
	        		flag="0";
	        	}
	        	else
	        		flag="1";
	        }
	    }
	     

	
	public void clearfields(){
		nameET.setText("");
		idET.setText("");
		clsET.setText("");
		passET.setText("");
		confirmpassET.setText("");

	}
	
	
	

}
