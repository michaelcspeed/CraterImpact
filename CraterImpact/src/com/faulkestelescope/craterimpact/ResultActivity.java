package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;

import android.os.Bundle;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getResources().getString(R.string.lblImpEnergy));
		setContentView(R.layout.resultsactivity);
	}

}