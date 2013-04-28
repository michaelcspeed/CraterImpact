package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

public class ObjectTrajectoryActivity extends Activity implements
		OnClickListener,
		org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener {

	private TextView textTraj;
	private SeekBar seekBarTraj;
	private Button buttonNext;
	private View imageTraj;
	private Bitmap bMap;
	private Matrix matrix;
	private RotateAnimation an;
	float start = 0;
	float finsih = 0;

	private void findViews() {
		textTraj = (TextView) findViewById(R.id.textProjectileAngle);
		seekBarTraj = (SeekBar) findViewById(R.id.seekBarProjectileAngle);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageTraj = (View) findViewById(R.id.imageTrajectoryLine);

		buttonNext.setOnClickListener(this);
		seekBarTraj.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objecttrajectory);
		findViews();
		setTitle(R.string.lbPjAng);
	}

	@Override
	protected void onResume() {
		super.onResume();

	};

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {

		textTraj.setText(progressNo + "\u00B0");

		// Create an animation instance
		an = new RotateAnimation(0.0f, 360 - progressNo, 0, 0);

		// Set the animation's parameters
		an.setDuration(10); // duration in ms
		an.setRepeatCount(0); // -1 = infinite repeated
		// an.setRepeatMode(Animation.RELATIVE_TO_SELF); // reverses each repeat
		//an.setFillAfter(true); // keep rotation after animation

		// Apply animation to image view
		imageTraj.setAnimation(an);

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
