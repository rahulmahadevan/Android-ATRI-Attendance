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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FacultyConfirmation extends Activity {
	protected static final String TAG = "FacultyConfirmation";
	String urlm = "http://192.168.43.233:8080/rahul/insertAttend.php";
	String clsname,fcode,fpost;
	Button submit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.faculty_confirmation);
	    final EditText code;
		Bundle bundle = getIntent().getExtras();
	    fpost = bundle.getString("fpost");
		Button back;
		code = (EditText) findViewById(R.id.editText1);
		Log.v("POST STRING", fpost);
		clsname = bundle.getString("clsname");
		Log.v("CLASS NAME", clsname);
		submit = (Button) findViewById(R.id.bSubmit);
		back = (Button) findViewById(R.id.bCancel);
		back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				onBackPressed();
			}
		});
		
		submit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fcode = code.getText().toString();
				Log.v("FCODE", fcode);
				new fstrpost().execute();
				
			}
		});
		
	}
	

public class fstrpost extends AsyncTask<String, String, String> {
	 
    protected void onPreExecute() {
        super.onPreExecute();
        // do stuff before posting data
    }

    @Override
    protected String doInBackground(String... strings) {
        try {

        	String postReceiverUrl = urlm;
	         Log.v(TAG, "postURL: " + postReceiverUrl);
	             
	         // HttpClient
	         HttpClient httpClient = new DefaultHttpClient();
	             
	         // post header
	         HttpPost httpPost = new HttpPost(postReceiverUrl);
	         List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	            nameValuePairs.add(new BasicNameValuePair("cls",clsname));
	            nameValuePairs.add(new BasicNameValuePair("value", fpost));
	            nameValuePairs.add(new BasicNameValuePair("code", fcode));
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
        if(respo.equals("code failed")){
        	Toast.makeText(getApplicationContext(), "Invalid Faculty Code",Toast.LENGTH_LONG).show();
        }
        else if(respo.equals("insert failed")){
        	Toast.makeText(getApplicationContext(), "Database ERROR", Toast.LENGTH_LONG).show();
        }
        else if(respo.equals("yes")){
        	Toast.makeText(getApplicationContext(), "Attendance Taken Successfully", Toast.LENGTH_SHORT).show();
        	submit.setEnabled(false);
        	(new Handler())
            .postDelayed(
            new Runnable() {
            public void run() {
            // launch your activity here
            	finish();
    			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
    			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    			startActivity(intent);
            }
            }, 3500);        }
		
    }
}    

}
