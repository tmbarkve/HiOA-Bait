package no.hioa.recruiting.activities;

import java.util.Set;

import no.hioa.recruiting.R;
import no.hioa.recruiting.models.ArduinoConnection;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;

public class ConnectionActivity extends ListActivity {
	private static final Boolean D = true;
	private static final String TAG = "ConnectionActivity";

	public static final String EXTRA_CONNECTION_STATUS = "no.hioa.recruiting.activities.ConnectionActivity.CONNECTION_STATUS";

	private static ArrayAdapter<String> mDeviceList;

	private BluetoothAdapter mBluetoothAdapter;
	private ToggleButton mBluetoothOnButton;
	private ArduinoConnection mConnectionStatus;

	private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		private static final String TAG = "ConnectionActivity:BroadcastReceiver";

		@Override
		public void onReceive(Context context, Intent intent) {
			// Gets the intents action identifier
			String action = intent.getAction();
			if (D) Log.i(TAG, "--- onReceive mBroadcastReceiver: " + action);

			// If the action is equal to "device found"
			if (BluetoothDevice.ACTION_FOUND.equals(action)) {
				Log.i(TAG, "--- Bluetooth device found ---");
				BluetoothDevice device = (BluetoothDevice) intent
						.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

				Log.i(TAG, "--- " + device.getName() + " ---");
				mDeviceList.add(device.getName() + "\n" + device.getAddress());
				return;
			}

			if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
				ConnectionActivity.this.unregisterReceiver(mBroadcastReceiver);
				notify("Bluetooth Scan finished");
				return;
			}

			if (action.equals(AmarinoIntent.ACTION_CONNECTED)) {
				mConnectionStatus.setConnected(true);
				notify("Connected to device: " + mConnectionStatus.getDeviceAddress());
				return;
			}

			if (action.equals(AmarinoIntent.ACTION_CONNECTION_FAILED)) {
				mConnectionStatus.setConnected(false);
				notify("Failed to connect to device: "
						+ mConnectionStatus.getDeviceAddress());
				return;
			}

			if (action.equals(AmarinoIntent.ACTION_DISCONNECTED)) {
				mConnectionStatus = null;
				notify("Disconnected");
				return;
			}

			if (action.equals(AmarinoIntent.ACTION_CONNECTED_DEVICES)) {
				String[] devices = intent
						.getStringArrayExtra(AmarinoIntent.EXTRA_CONNECTED_DEVICE_ADDRESSES);
				if (devices != null) {
					mConnectionStatus.setConnected(true);
					mConnectionStatus.setDeviceAddress(devices[0]);
					notify(mConnectionStatus.toString());
					return;
				}
				mConnectionStatus = new ArduinoConnection();
				notify("No connection established");
			}
		}

		private void notify(String message) {
			Log.i(TAG, "--- " + message + " ---");
			Toast.makeText(ConnectionActivity.this, message, Toast.LENGTH_SHORT).show();
		}
	};

	@Override
	public void onPause() {
		try {
			unregisterReceiver(mBroadcastReceiver);
		}
		catch (IllegalArgumentException e) {
			Log.e(TAG, "--- No Receiver registered ---");
		}
		super.onPause();
	}

	private void initializeBluetooth() {
		mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

		// Checks if the handset supports bluetooth
		if (mBluetoothAdapter == null) {
			Toast.makeText(ConnectionActivity.this,
					"This device does not support bluetooth", Toast.LENGTH_SHORT);
		}

		// Checks if Bluetooth is turned on
		if (mBluetoothAdapter.isEnabled()) {
			mBluetoothOnButton.setChecked(true);
			// Checks for already paired devices
			Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
			if (pairedDevices.size() > 0) {
				for (BluetoothDevice device : pairedDevices) {
					mDeviceList.add(device.getName() + "\n" + device.getAddress());
				}
			}
		}
		else {
			mBluetoothOnButton.setChecked(false);
		}
	}

	private void refreshList() {
		// TODO: implement progressbar
		if (mBluetoothAdapter.isDiscovering()) mBluetoothAdapter.cancelDiscovery();

		// Removes all listed devices
		mDeviceList.clear();

		mBluetoothAdapter.startDiscovery();
	}

	private void setupBroadcastReceiver() {
		IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
		filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
		filter.addAction(AmarinoIntent.ACTION_CONNECT);
		filter.addAction(AmarinoIntent.ACTION_CONNECTED);
		filter.addAction(AmarinoIntent.ACTION_CONNECTION_FAILED);
		//filter.addAction(AmarinoIntent.ACTION_PAIRING_REQUESTED);
		filter.addAction(AmarinoIntent.ACTION_DISCONNECTED);
		filter.addAction(AmarinoIntent.ACTION_CONNECTED_DEVICES);

		registerReceiver(mBroadcastReceiver, filter);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D) Log.d(TAG, "--- onCreate ---");
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.connection);

		mConnectionStatus = new ArduinoConnection();

		// Set up and register broadcast receiver and filters
		setupBroadcastReceiver();

		sendBroadcast(new Intent(AmarinoIntent.ACTION_GET_CONNECTED_DEVICES));

		mDeviceList = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		setListAdapter(mDeviceList);

		// TODO: create textview that holds connection information

		mBluetoothOnButton = (ToggleButton) findViewById(R.id.connection_toggle_bluetooth_button);
		mBluetoothOnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO: implement progressbar & button lock
				if (mBluetoothAdapter.isEnabled()) {
					mBluetoothAdapter.disable();
					((ToggleButton) v).setChecked(false);
					setListAdapter(null);
				}
				else {
					mBluetoothAdapter.enable();
					((ToggleButton) v).setChecked(true);
					refreshList();
				}
			}
		});

		Button b = (Button) findViewById(R.id.connection_scan_for_devices_button);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mBluetoothAdapter.isEnabled())
					refreshList();
				else
					Toast.makeText(ConnectionActivity.this, "Bluetooth is not enabled",
							Toast.LENGTH_SHORT).show();
			}
		});

		b = (Button) findViewById(R.id.connection_console_button);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mConnectionStatus.isConnected()) {
					Intent i = new Intent(ConnectionActivity.this, ConsoleActivity.class);
					i.putExtra(EXTRA_CONNECTION_STATUS, mConnectionStatus);
					startActivity(i);
				}
				else {
					Toast.makeText(ConnectionActivity.this,
							"Can't open a console, no connections active",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		ListView lv = (ListView) findViewById(android.R.id.list);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				// get the mac address from the second line of
				// the string in the list item
				if (!mConnectionStatus.isConnected()) {
					String deviceMacAddress = (mDeviceList.getItem(position).split("\n"))[1];

					Log.v(TAG, "--- Connecting to device: " + deviceMacAddress + " ---");
					mConnectionStatus.setDeviceAddress(deviceMacAddress);
					Amarino.connect(ConnectionActivity.this, deviceMacAddress);
				}
				else {
					Toast.makeText(ConnectionActivity.this,
							"A connection is already established", Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		initializeBluetooth();
	}

	@Override
	protected void onDestroy() {
		try {
			unregisterReceiver(mBroadcastReceiver);
		}
		catch (IllegalArgumentException e) {
			Log.e(TAG, "--- No Receiver registered ---");
		}
		super.onDestroy();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("Are you sure you want to exit?")
		       .setCancelable(false)
		       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                //MyActivity.this.finish();
		           }
		       })
		       .setNegativeButton("No", new DialogInterface.OnClickListener() {
		           public void onClick(DialogInterface dialog, int id) {
		                dialog.cancel();
		           }
		       });
		AlertDialog alert = builder.create();
		
		return super.onCreateDialog(id);
	}
}
