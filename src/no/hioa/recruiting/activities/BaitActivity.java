package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;

public class BaitActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.bait);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.decoy_option_menu, menu);
        return true;
    }
    
   @Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.decoy_option_about:
				startActivity(new Intent(BaitActivity.this, AboutActivity.class)); 
				break;
			case R.id.decoy_option_connect:
				startActivity(new Intent(BaitActivity.this, ConnectionSettingsActivity.class));
				break; 
		}
		return super.onOptionsItemSelected(item);
	}
}
