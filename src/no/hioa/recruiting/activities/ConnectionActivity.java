package no.hioa.recruiting.activities;

import java.util.TooManyListenersException;

import no.hioa.recruiting.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;
import android.widget.ToggleButton;

public class ConnectionActivity extends Activity {
	private static final String TAG = "ConnectionActivity"; 
	private static final Boolean D = true; 
	
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
				Toast.makeText(getApplicationContext(), 
						"Bluetooth mode changed", Toast.LENGTH_SHORT);
			}
		});
		
		if(mBluetoothAdapter == null) { 
			
		}
		
		if(mBluetoothAdapter.isEnabled()) { 
			t.setChecked(true); 
		} else { 
			t.setChecked(false); 
		}
	}
}
