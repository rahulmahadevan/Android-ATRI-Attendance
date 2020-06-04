package in.edu.atri.atriupdates;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class HodHome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hod_home);
		Button scr,wsc,dwa,fna,ma,ca;
		scr = (Button) findViewById(R.id.bSyllabusCoverage);
		wsc = (Button) findViewById(R.id.button2);
		dwa = (Button) findViewById(R.id.bDayWise);
		fna = (Button) findViewById(R.id.bFortNight);
		ma = (Button) findViewById(R.id.bMonthly);
		ca = (Button) findViewById(R.id.bCummulative);
	}
	

}
