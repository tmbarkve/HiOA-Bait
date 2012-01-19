package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import no.hioa.recruiting.R.layout;
import no.hioa.recruiting.R.string;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;

public class DecoyActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(R.string.option_menu_connect); 
    	menu.add(R.string.option_menu_about); 
    	return super.onCreateOptionsMenu(menu);
    }
}