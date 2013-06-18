package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;

import control.DataProvider;

import android.os.Bundle;
import android.widget.TextView;

public class DamageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.damage));
		setContentView(R.layout.resultsactivity);

		TextView t = (TextView) findViewById(R.id.resultstext);

		try {
			t.setText(DataProvider.getTxtDamage());
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

}