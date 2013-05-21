package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener;
import org.holoeverywhere.widget.TextView;

import control.DataProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class DistanceFromCrashSiteActivity extends Activity implements
		OnClickListener, OnSeekBarChangeListener {
	private TextView textProjectileSize;
	private SeekBar seekBarProjectileSize;
	private Button buttonNext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.distancelayout);
		findViews();

		setTitle(R.string.lblDistance);
	}

	private void findViews() {
		textProjectileSize = (TextView) findViewById(R.id.textDistance);
		seekBarProjectileSize = (SeekBar) findViewById(R.id.seekBarDistance);
		buttonNext = (Button) findViewById(R.id.buttonNext);

		seekBarProjectileSize.setOnSeekBarChangeListener(this);
		buttonNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			DataProvider.setImpactDist(seekBarProjectileSize.getProgress());
			Intent intent = new Intent(this, ResultsTabControllerActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {
		textProjectileSize.setText(progressNo + " km");		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

}
