package in.edu.atri.atriupdates;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SyllabusReport extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.syllabus_report);
		EditText year,sem,branch,subject,section,fname;
		Button print,home,clear;
		//percentage is not initialized
		TextView comments;
		year = (EditText) findViewById(R.id.etYear);
		sem = (EditText) findViewById(R.id.etSem);
		branch = (EditText) findViewById(R.id.etBranch);
		subject = (EditText) findViewById(R.id.etSub);
		section = (EditText) findViewById(R.id.etSec);
		fname = (EditText) findViewById(R.id.etFname);
		print = (Button) findViewById(R.id.bPrint);
		home = (Button) findViewById(R.id.bHome);
		clear = (Button) findViewById(R.id.bClear);
		comments = (TextView) findViewById(R.id.tvComment);
		
		
		
	}
	

}
