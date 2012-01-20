package no.hioa.recruiting.activities;

import no.hioa.recruiting.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class AboutActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.about);
	}
}
