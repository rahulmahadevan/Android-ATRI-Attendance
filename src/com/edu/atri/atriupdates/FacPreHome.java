package in.edu.atri.atriupdates;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class FacPreHome extends Activity{
	Button day,range;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fac_prehome);
		day = (Button) findViewById(R.id.day);
		range = (Button) findViewById(R.id.range);
		range.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent rng = new Intent("android.intent.FacPreHome");
				startActivity(rng);
			}
		});
		day.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	

}
