package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ObjectTargetDensActivity extends Activity implements
		OnClickListener, OnItemSelectedListener,
		org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener {

	private Spinner spinner1;
	private Button buttonNext;
	private ImageView imageDens;
	private SeekBar waterLevelSlider;

	SharedPreferences prefs;
	private TextView waterLevelText;
	private WaterView waterView;
	private int windowWidth;
	private int imageUri;
	private DisplayImageOptions options;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.targetdensity);
		
		setTitle(R.string.lbTgDens);

		prefs = this.getSharedPreferences("com.faulkestelescope.craterimpact",
				Context.MODE_PRIVATE);
		
		//get width of screen for images
		DisplayMetrics disp = this.getResources().getDisplayMetrics();
		windowWidth = disp.widthPixels;
		
		findViews();
		
	}

	private void findViews() {
		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageDens = (ImageView) findViewById(R.id.imageDens);
		waterLevelSlider = (SeekBar) findViewById(R.id.seekBarWater);
		waterLevelText = (TextView) findViewById(R.id.waterLevelText);
		waterView = (WaterView) findViewById(R.id.waterView);
		waterView.setDepth(1, windowWidth);

		buttonNext.setOnClickListener(this);
		spinner1.setOnItemSelectedListener(this);
		waterLevelSlider.setOnSeekBarChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			// Save the spinner position as a preference for density
			// Save the slider number as a preference for water level

			int l = spinner1.getSelectedItemPosition();
			int water = waterLevelSlider.getProgress();
			prefs.edit().putInt(Globals.targdensKey, l + 1).commit();
			try {
				prefs.edit().putInt(Globals.waterdepthKey, water).commit();
			} catch (Exception e) {
				e.printStackTrace();
			}
			// DataProvider.setImpactDist(spinner1.getSelectedItemPosition());
			Intent intent = new Intent(this,
					DistanceFromCrashSiteActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		
		switch (arg2) {
		case 0:
			waterLevelSlider.setVisibility(View.VISIBLE);
			waterLevelText.setVisibility(View.VISIBLE);
			waterView.setVisibility(View.VISIBLE);
			imageUri = R.drawable.targetwater;
			break;
		case 1:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			waterLevelText.setVisibility(View.INVISIBLE);
			waterView.setVisibility(View.INVISIBLE);

			imageUri =  R.drawable.sedimentary;
			break;
		case 2:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			waterLevelText.setVisibility(View.INVISIBLE);
			waterView.setVisibility(View.INVISIBLE);
			
			imageUri =  R.drawable.dense;
			break;

		default:
			break;
		}

//	imageUri = "http://i.imgur.com/dJ4zL3h.jpg";
	
		imageDens.setImageResource(imageUri);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {
		if (progressNo == 0)
			progressNo = 1;

		waterLevelText.setText(progressNo + " m");
		waterView.setDepth(progressNo, windowWidth);
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
