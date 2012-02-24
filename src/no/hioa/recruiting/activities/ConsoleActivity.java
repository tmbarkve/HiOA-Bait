package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import no.hioa.recruiting.models.ConnectionStatus;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;

public class ConsoleActivity extends Activity implements OnClickListener {
	private static final String TAG = "ConsoleActivity";
	private static final Boolean D = true; 
	
	private ConnectionStatus mConntectionStatus; 
	
	private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		private static final String TAG = "ConsoleActivity:mBroadcastReceiver"; 
		
		@Override
		public void onReceive(Context context, Intent intent) {
	 		String address = intent.getStringExtra(AmarinoIntent.EXTRA_DEVICE_ADDRESS);
	 		int dataType = intent.getIntExtra(AmarinoIntent.EXTRA_DATA_TYPE, -1);
	 		
	 		if(dataType == AmarinoIntent.STRING_EXTRA)
	 			Log.v(TAG, "Address: " + address + " Datatype: String");
	 		else 
	 			Log.v(TAG, "Address: " + address + " Datatype: " + dataType);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(D) Log.d(TAG, "--- onCreate ---"); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.console);
        
    	mConntectionStatus = (ConnectionStatus) getIntent()
    			.getSerializableExtra(ConnectionActivity.EXTRA_CONNECTION_STATUS);
    	
    	IntentFilter filter = new IntentFilter(); 
        registerReceiver(mBroadcastReceiver, new IntentFilter(AmarinoIntent.ACTION_RECEIVED)); 
    	
        Button b = (Button) findViewById(R.id.console_submit);
        b.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		EditText et = (EditText) findViewById(R.id.console_command_in);
		if(et.length() > 0) { 
			TextView tv = (TextView) findViewById(R.id.console_output); 
			String command = ">>> " + et.getText().toString() + "\n";
			tv.append(command);
			
			Log.v(TAG, "Sending data to: " + mConntectionStatus.getDeviceAddress() 
					+ ", payload: " + et.getText().toString());
			Amarino.sendDataToArduino(this, mConntectionStatus.getDeviceAddress(), 
					'a', et.getText().toString().toCharArray());
		}
	}
	
	@Override
	protected void onStop() {
		unregisterReceiver(mBroadcastReceiver);
	}
}
