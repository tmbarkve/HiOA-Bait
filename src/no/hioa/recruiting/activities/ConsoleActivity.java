package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ConsoleActivity extends Activity implements OnClickListener {
	private static final String TAG = "ConsoleActivity";
	private static final Boolean D = true; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(D) Log.d(TAG, "--- onCreate ---"); 
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.console);
        
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
		}
	}
}
