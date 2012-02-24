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
import android.widget.Toast;

public class BaitActivity extends Activity {
	private static final String TAG = "BaitActivity"; 
	private static final boolean D = true; 
	
	public static final int CONNECTED_RESULT = 666; 
	
	private boolean mConnected;
	private String mDeviceAddress; 
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(D) Log.d(TAG, "--- onCreate ---"); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bait);
        
        mConnected = false; 
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
				Intent i = new Intent(this, ConnectionActivity.class); 
				startActivityForResult(i, CONNECTED_RESULT); 
			break; 
		}
		return super.onOptionsItemSelected(item);
   }
   
   @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(requestCode == CONNECTED_RESULT) { 
			if(D) Log.v(TAG, "--- onActivityResult: requestCode == CONNECTED_RESULT ---");
			//mConnected = data.getBooleanExtra(ConnectionActivity.EXTRA_IS_CONNECTED, false);
			//mDeviceAddress = data.getStringExtra(ConnectionActivity.EXTRA_DEVICE_ADDRESS); 
			//if(mConnected)
			//	Toast.makeText(this, "Connected to " + mDeviceAddress, Toast.LENGTH_SHORT).show(); 
		//	else 
		//		Toast.makeText(this, "Not connected", Toast.LENGTH_SHORT).show(); 
		}
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
