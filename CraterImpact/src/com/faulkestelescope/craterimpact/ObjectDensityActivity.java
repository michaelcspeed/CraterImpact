package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

public class ObjectDensityActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner spinner1;
	private Button buttonNext;
	private ImageView imageDens;
	private DisplayImageOptions options;

	SharedPreferences prefs;
	private int imageUri;
	private Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objectdensity);
		findViews();
		setTitle(R.string.lbPjDens);

		Log.d("ci", "0");

		prefs = this.getSharedPreferences("com.faulkestelescope.craterimpact",
				Context.MODE_PRIVATE);

	}

	private void findViews() {
		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		Log.d("ci", "1");

		buttonNext = (Button) findViewById(R.id.buttonNext);
		Log.d("ci", "2");
		imageDens = (ImageView) findViewById(R.id.imageDens);
		Log.d("ci", "3");
		buttonNext.setOnClickListener(this);
		Log.d("ci", "4");
		spinner1.setOnItemSelectedListener(this);
		Log.d("ci", "5");
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			int l = spinner1.getSelectedItemPosition();
			prefs.edit().putInt(Globals.projdensKey, l + 1).commit();

			Intent intent = new Intent(this, ObjectTargetDensActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		switch (arg2) {
		case 0:

			imageUri = R.drawable.ice;
			Log.d("ci", "6");
			break;
		case 1:
			imageUri = R.drawable.porous;
			Log.d("ci", "7");
			break;
		case 2:

			imageUri = R.drawable.dense;
			Log.d("ci", "8");
			break;
		case 3:
			imageUri = R.drawable.iron;
			Log.d("ci", "9");
			break;
		default:
			break;
		}

		if(bmp!=null)
		bmp.recycle();

		bmp = decodeSampledBitmapFromResource(getResources(), imageUri, 720,
				720);

		imageDens.setImageBitmap(bmp);

		Log.d("ci", "10");
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
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
