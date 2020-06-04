package in.edu.atri.atriupdates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Splash extends Activity {
	
	@Override
	protected void onCreate(Bundle justMyName) {
		// TODO Auto-generated method stub
		super.onCreate(justMyName);
		setContentView(R.layout.splashlayout);
		Thread timer = new Thread(){
			public void run(){
				try{
					sleep(3000);
				}catch(InterruptedException e){
					e.printStackTrace();
				}finally{
					Intent log= new Intent("android.intent.Login");
					startActivity(log);
				}
			}
		};
		timer.start();
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
	

}
