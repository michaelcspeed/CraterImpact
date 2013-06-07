package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import control.DataProvider;

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
			DataProvider.setProjAngle(seekBarTraj.getProgress());
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
		
		
		/*
		// Decode Image using Bitmap factory.
		bMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.impactangle);

		// Create object of new Matrix.
		matrix = new Matrix();

		// set image rotation value to 45 degrees in matrix.
		matrix.postRotate(progressNo); // 180degrees / 60 kmph = 3

		// Create bitmap with new values.
		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
				bMap.getHeight(), matrix, true);

		// put rotated image in ImageView.
		imageTraj.setImageBitmap(bMapRotate);
*/
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
