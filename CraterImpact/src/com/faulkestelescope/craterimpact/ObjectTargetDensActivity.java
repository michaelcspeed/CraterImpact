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
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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
	private Bitmap bmp;
	private int y;
	private int x;
	private boolean waterOn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Log.d("ci", "0");

		setContentView(R.layout.targetdensity);

		setTitle(R.string.lbTgDens);

		prefs = this.getSharedPreferences("com.faulkestelescope.craterimpact",
				Context.MODE_PRIVATE);

		// get width of screen for images
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

	//	getCoordsForWater();

		waterView.setDepth(1, windowWidth);

		buttonNext.setOnClickListener(this);
		spinner1.setOnItemSelectedListener(this);
		waterLevelSlider.setOnSeekBarChangeListener(this);

	}

	/*private void getCoordsForWater() {
		x = imageDens.getDrawable().getBounds().left;
		y = imageDens.getDrawable().getBounds().top;
	}*/

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

		Log.d("ci", "2");

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		switch (arg2) {
		case 0:
			waterLevelSlider.setVisibility(View.VISIBLE);
			waterLevelText.setVisibility(View.VISIBLE);
			waterView.setVisibility(View.VISIBLE);
			waterOn = true;

			break;
		case 1:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			waterLevelText.setVisibility(View.INVISIBLE);
			waterView.setVisibility(View.INVISIBLE);

			imageUri = R.drawable.sedimentary;

			waterOn = false;

			break;
		case 2:
			waterLevelSlider.setVisibility(View.INVISIBLE);
			waterLevelText.setVisibility(View.INVISIBLE);
			waterView.setVisibility(View.INVISIBLE);

			imageUri = R.drawable.dense;

			waterOn = false;
			break;

		default:
			break;
		}

		if (!waterOn) {

			if (bmp != null)
				bmp.recycle();

			bmp = BitmapFactory.decodeResource(getResources(), imageUri);

			imageDens.setImageBitmap(bmp);

		} else {
			bmp = BitmapFactory.decodeResource(getResources(), R.drawable.blank);

			imageDens.setImageBitmap(bmp);
		}

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

	//	getCoordsForWater();
		waterView.setDepth(progressNo, windowWidth);

		Log.d("ci", "7");
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub

	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			// Calculate ratios of height and width to requested height and
			// width
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);

			// Choose the smallest ratio as inSampleSize value, this will
			// guarantee
			// a final image with both dimensions larger than or equal to the
			// requested height and width.
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

}
