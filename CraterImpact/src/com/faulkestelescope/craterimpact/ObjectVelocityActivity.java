package com.faulkestelescope.craterimpact;

import java.util.Locale;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.TextView;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ObjectVelocityActivity extends Activity implements
		OnClickListener,
		org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener {

	private TextView textVelocity;
	private SeekBar seekBarVelocity;
	private Button buttonNext;
	private ImageView imageVelocty;
	private Bitmap bMap;
	private Matrix matrix;

	private void findViews() {
		textVelocity = (TextView) findViewById(R.id.textVelocity);
		seekBarVelocity = (SeekBar) findViewById(R.id.seekBarVelocity);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageVelocty = (ImageView) findViewById(R.id.imageVelocty);

		buttonNext.setOnClickListener(this);
		seekBarVelocity.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			Intent intent = new Intent(this, ObjectTrajectoryActivity.class);
			startActivity(intent);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objectvelocitylayout);
		findViews();
		setTitle(R.string.lblObVelocity);
	}

	@Override
	protected void onResume() {
		super.onResume();
		
	};

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {

		textVelocity.setText(progressNo + "kmph");

		// Decode Image using Bitmap factory.
		bMap = BitmapFactory.decodeResource(getResources(),
				R.drawable.ic_launcher);

		// Create object of new Matrix.
		matrix = new Matrix();

		// set image rotation value to 45 degrees in matrix.
		matrix.postRotate((float) (progressNo * 3)); // 180degrees / 60 kmph = 3

		// Create bitmap with new values.
		Bitmap bMapRotate = Bitmap.createBitmap(bMap, 0, 0, bMap.getWidth(),
				bMap.getHeight(), matrix, true);

		// put rotated image in ImageView.
		imageVelocty.setImageBitmap(bMapRotate);

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
