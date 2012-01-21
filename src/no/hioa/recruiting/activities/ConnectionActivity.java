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
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConnectionActivity extends ListActivity {
	private static final String TAG = "ConnectionActivity"; 
	private static final Boolean D = true; 
	
	private static ArrayAdapter<String> mDeviceList; 
    
	private BluetoothAdapter mBluetoothAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(D) Log.d(TAG, "--- onCreate ---"); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.connection);
		
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		
		ToggleButton t = (ToggleButton) findViewById(R.id.toggle_bluetooth_button);
		t.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: implement progressbar & button lock
				if(mBluetoothAdapter.isEnabled()) { 
					mBluetoothAdapter.disable(); 
					((ToggleButton) v).setChecked(false);
					setListAdapter(null);
				} else { 
					mBluetoothAdapter.enable(); 
					((ToggleButton) v).setChecked(true);
					refreshList(); 
				}
			}
		});
		
		Button b = (Button) findViewById(R.id.scan_for_devices_button);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refreshList();
			}
		});
		
		// Checks if the handset supports bluetooth
		if(mBluetoothAdapter == null) { 
			Toast.makeText(ConnectionActivity.this, 
					"This device does not support bluetooth", Toast.LENGTH_SHORT);
		}
		
		// Checks if Bluetooth is turned on
		if(mBluetoothAdapter.isEnabled()) { 
			t.setChecked(true);
		} else { 
			t.setChecked(false); 
		}
	}
	
	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mBcastReceiver);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "--- No Receiver registered ---");
		}
		super.onDestroy();
	}
	

    @Override
    public void onPause() {
		try {
			unregisterReceiver(mBcastReceiver);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "--- No Receiver registered ---");
		}
        super.onPause();
    }
	
	private void refreshList() {
		// TODO: implement progressbar
		if(mBluetoothAdapter.isDiscovering()) mBluetoothAdapter.cancelDiscovery();
		
		mDeviceList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		setListAdapter(mDeviceList);
		
		// Register the BroadcastReceiver
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED); 
		registerReceiver(mBcastReceiver, filter); 
		
		mBluetoothAdapter.startDiscovery(); 
	} 
	
	private final BroadcastReceiver mBcastReceiver = new BroadcastReceiver() {
		private static final String TAG = "BroadcastReceiver"; 
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// Gets the intents action identifier
	        String action = intent.getAction();
	        // If the action is equal to "device found"
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				Log.i(TAG, "--- Bluetooth device found ---"); 
				BluetoothDevice device = (BluetoothDevice) intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
				Log.i(TAG, "--- " + device.getName() + " ---"); 
				mDeviceList.add(device.getName() + "\n" + device.getAddress());
			}
			
			if(BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) { 
				Log.i(TAG, "--- Bluetooth Scan finished ----"); 
				ConnectionActivity.this.unregisterReceiver(mBcastReceiver);
			}
		}
	};
}
