package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.Spinner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ObjectTargetDensActivity extends Activity implements
		OnClickListener, OnItemSelectedListener {

	private Spinner Spinner1;
	private Button buttonNext;
	private ImageView imageDens;
	private View waterLevelSlider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.targetdensity);
		findViews();
		setTitle(R.string.lbTgDens);
	}

	private void findViews() {
		Spinner1 = (Spinner) findViewById(R.id.Spinner1);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageDens = (ImageView) findViewById(R.id.imageDens);
		waterLevelSlider = (SeekBar) findViewById(R.id.seekBarWater);

		buttonNext.setOnClickListener(this);
		Spinner1.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			Intent intent = new Intent(this, DistanceFromCrashSiteActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		Bitmap btm = null;
		switch (arg2) {
		case 0:
			waterLevelSlider.setVisibility(View.VISIBLE);
			btm = BitmapFactory
					.decodeResource(getResources(), R.drawable.targetwater);
			break;
		case 1:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			btm = BitmapFactory.decodeResource(getResources(), R.drawable.targetporousrock);
			break;
		case 2:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			btm = BitmapFactory.decodeResource(getResources(), R.drawable.targetignatiousrock);
			break;

		default:
			break;
		}

		imageDens.setImageBitmap(btm);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

}
