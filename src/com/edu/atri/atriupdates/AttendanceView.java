package in.edu.atri.atriupdates;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

public class AttendanceView extends Activity{
	String[][] tablecontent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.attendance_view);
		Bundle bundle= getIntent().getExtras();
		final int row = bundle.getInt("row");
		final int col = bundle.getInt("col");
		Object[] objectArray = (Object[]) getIntent().getExtras().getSerializable("tablecontent");
		tablecontent = new String[objectArray.length][];
		for(int i=0;i<objectArray.length;i++){
			tablecontent[i] = (String[]) objectArray[i];
		}
        TableRow.LayoutParams wrapWrapTableRowParams = new TableRow.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		//Above code works and tablecontent has the required values
		TableLayout t1 = (TableLayout) findViewById(R.id.table1);
        TableLayout fixedColumn = (TableLayout) findViewById(R.id.fixed_column);
        
        for(int i=0;i<col;i++){
        	TextView fixedView = makeTableRowWithText(tablecontent[0][i], 33, 50);
            fixedView.setBackgroundColor(Color.rgb(135, 206, 250));
            fixedColumn.addView(fixedView);
        }
        
		for(int j=0;j<col;j++){
			TableRow trow = new TableRow(this);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(wrapWrapTableRowParams);
			trow.setLayoutParams(lp);
			for(int i=1;i<row;i++){
				TextView tv = makeTableRowWithText(tablecontent[i][j], 33, 50);
				tv.setGravity(Gravity.CENTER);
				trow.addView(tv);
			}
			t1.addView(trow);
		}
        
    }
	 private TextView recyclableTextView;

	    public TextView makeTableRowWithText(String text, int widthInPercentOfScreenWidth, int fixedHeightInPixels) {
	        int screenWidth = getResources().getDisplayMetrics().widthPixels;
	        recyclableTextView = new TextView(this);
	        recyclableTextView.setText(text);
	        recyclableTextView.setTextColor(Color.BLACK);
	        recyclableTextView.setTextSize(18);
	        recyclableTextView.setBackgroundResource(R.drawable.cell_shape);
	        recyclableTextView.setWidth(widthInPercentOfScreenWidth * screenWidth / 100);
	        recyclableTextView.setHeight(fixedHeightInPixels);
	        return recyclableTextView;
	    }

		@Override
		protected void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			finish();
			
		}	
	    
	 

}
