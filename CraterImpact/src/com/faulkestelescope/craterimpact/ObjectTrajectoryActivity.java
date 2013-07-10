package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ObjectTrajectoryActivity extends Activity implements
		OnClickListener,
		org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener {

	private TextView textTraj;
	private SeekBar seekBarTraj;
	private Button buttonNext;
	private CustomImageView imageTraj;
	float start = 0;
	float finsih = 0;
	private Bitmap bMap;
	private static Matrix matrix;
	
	SharedPreferences prefs;

	private void findViews() {
		textTraj = (TextView) findViewById(R.id.textProjectileAngle);
		seekBarTraj = (SeekBar) findViewById(R.id.seekBarProjectileAngle);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageTraj = (CustomImageView) findViewById(R.id.imageTraj);

		buttonNext.setOnClickListener(this);
		seekBarTraj.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			Long l = (long) seekBarTraj.getProgress();
			prefs.edit().putLong(Globals.trajKey, l).commit();
			Intent intent = new Intent(this, ObjectDensityActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objecttrajectory);
		findViews();
		setTitle(R.string.lbPjAng);
		
		prefs = this.getSharedPreferences(
			      "com.faulkestelescope.craterimpact", Context.MODE_PRIVATE);

	}

	@Override
	protected void onResume() {
		super.onResume();
	};

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {
		
		if(progressNo == 0)
			progressNo = 1;

		imageTraj.setAngleRotation(90-progressNo);

		textTraj.setText(progressNo + "\u00B0");
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
