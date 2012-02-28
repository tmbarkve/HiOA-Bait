package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import no.hioa.recruiting.models.ArduinoConnection;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import at.abraxas.amarino.Amarino;
import at.abraxas.amarino.AmarinoIntent;

public class BaitActivity extends Activity {
	private static final String TAG = "BaitActivity";
	private static final boolean D = true;

	// Used by Amarino library, not implemented on Arduino. Arbitrary value.
	private static final char ARDUINO_FLAG = 'a';

	public static final int CONNECTED_RESULT = 666;
	public static final char LED_ON = 'b';
	public static final char LED_OFF = 'a';
	public static final char PLACEHOLDER_A = 'c';
	public static final char PLACEHOLDER_B = 'd';

	private ArduinoConnection mConnection;

	private final BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String[] devices = intent
					.getStringArrayExtra(AmarinoIntent.EXTRA_CONNECTED_DEVICE_ADDRESSES);
			if (devices != null) {
				mConnection.setConnected(true);
				// given there only exists one connection
				mConnection.setDeviceAddress(devices[0]);
				notify(mConnection.toString());
			}
			else {
				mConnection = new ArduinoConnection();
				notify("No connection established");
			}
		}

		private void notify(String message) {
			Log.i(TAG + ":mBroadcastReceiver", "--- " + message + " ---");
			Toast.makeText(BaitActivity.this, message, Toast.LENGTH_SHORT).show();
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D) Log.d(TAG, "--- onCreate ---");
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.bait);

		mConnection = new ArduinoConnection();
		registerReceiver(mBroadcastReceiver, new IntentFilter(
				AmarinoIntent.ACTION_CONNECTED_DEVICES));
		sendBroadcast(new Intent(AmarinoIntent.ACTION_GET_CONNECTED_DEVICES));

		initButtons();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		if (D) Log.d(TAG, "--- onCreateOptionsMenu ---");
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.bait_option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (D) Log.d(TAG, "--- onOptionsItemSelected ---");
		switch (item.getItemId()) {
			case R.id.bait_option_about:
				startActivity(new Intent(getApplicationContext(), AboutActivity.class));
				break;
			case R.id.bait_option_connect:
				Intent i = new Intent(this, ConnectionActivity.class);
				startActivity(i);
				break;
		}
		return super.onOptionsItemSelected(item);
	}

	private void initButtons() {
		Button topLeft = (Button) findViewById(R.id.bait_button_top_left);
		topLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendToArduino(LED_OFF);
			}
		});

		Button topRight = (Button) findViewById(R.id.bait_button_top_right);
		topRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendToArduino(LED_ON);
			}
		});

		Button bottomLeft = (Button) findViewById(R.id.bait_button_bottom_left);
		bottomLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendToArduino(PLACEHOLDER_A);
			}
		});

		Button bottomRight = (Button) findViewById(R.id.bait_button_bottom_right);
		bottomRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				sendToArduino(PLACEHOLDER_B);
			}
		});
	}

	private void sendToArduino(char data) {
		if (mConnection.isConnected()) {
			if (D) Log.v(TAG, "--- Sending: " + data);
			Amarino.sendDataToArduino(this, mConnection.getDeviceAddress(),
					ARDUINO_FLAG, data);
		}
		else {
			Toast.makeText(this, "No connection established", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onDestroy() {
		unregisterReceiver(mBroadcastReceiver);
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		registerReceiver(mBroadcastReceiver, new IntentFilter(
				AmarinoIntent.ACTION_CONNECTED_DEVICES));
		sendBroadcast(new Intent(AmarinoIntent.ACTION_GET_CONNECTED_DEVICES));
	}

	@Override
	protected void onResume() {
		super.onResume();
		registerReceiver(mBroadcastReceiver, new IntentFilter(
				AmarinoIntent.ACTION_CONNECTED_DEVICES));
		sendBroadcast(new Intent(AmarinoIntent.ACTION_GET_CONNECTED_DEVICES));
	}

	@Override
	protected void onStop() {
		unregisterReceiver(mBroadcastReceiver);
		super.onStop();
	}
}
