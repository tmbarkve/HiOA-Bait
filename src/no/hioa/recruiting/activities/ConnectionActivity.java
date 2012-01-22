package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConnectionActivity extends ListActivity {
	private static final String TAG = "ConnectionActivity"; 
	private static final Boolean D = true; 
	
    public static final int STATE_NONE = 0;       // we're doing nothing
    public static final int STATE_CONNECTING = 1; // now initiating an outgoing connection
    public static final int STATE_CONNECTED = 2;  // now connected to a remote device
	
    private static BluetoothDevice[] deviceList; 
    
	private BluetoothAdapter mBluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.connection);
		if(D) Log.d(TAG, "--- onCreate ---"); 
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		ToggleButton t = (ToggleButton) findViewById(R.id.toogleBluetooth);
		t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(mBluetoothAdapter.isEnabled()) { 
					mBluetoothAdapter.disable(); 
				} else { 
					mBluetoothAdapter.enable(); 
					refreshList(); 
				}
			}
		});
		
		if(mBluetoothAdapter == null) { 
			Toast.makeText(ConnectionActivity.this, 
					"This device does not support bluetooth", Toast.LENGTH_SHORT);
		}
		
		if(mBluetoothAdapter.isEnabled()) { 
			t.setChecked(true);
		} else { 
			t.setChecked(false); 
		}
	}
	
	private void refreshList() {
		
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, values);
		setListAdapter(adapter);
	

	}
}
