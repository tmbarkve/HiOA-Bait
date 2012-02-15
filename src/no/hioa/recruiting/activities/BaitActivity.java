	package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class BaitActivity extends Activity {
	private static final String TAG = "BaitActivity"; 
	private static final boolean D = true; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.d(TAG, "--- onCreate ---"); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bait);
        
        initButtons();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if(D) Log.d(TAG, "--- onCreateOptionsMenu ---"); 
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bait_option_menu, menu);
        return true;
    }
    
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	   if(D) Log.d(TAG, "--- onOptionsItemSelected ---"); 
		switch (item.getItemId()) {
			case R.id.bait_option_about:
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
			break;
			case R.id.bait_option_connect:
				startActivity(new Intent(getApplicationContext(), ConnectionActivity.class)); 
			break; 
		}
		return super.onOptionsItemSelected(item);
   }
   
   private void initButtons() {
	   Button topLeft = (Button) findViewById(R.id.bait_button_top_left); 
       topLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
       
       Button topRight = (Button) findViewById(R.id.bait_button_bottom_right); 
       topRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		}); 
       
       Button bottomLeft = (Button) findViewById(R.id.bait_button_bottom_left); 
       bottomLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		}); 
       
       Button bottomRight = (Button) findViewById(R.id.bait_button_bottom_right); 
       bottomRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			}
		});
	}
}
