package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class ObjectDensityActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner spinner1;
	private Button buttonNext;
	private ImageView imageDens;
	private DisplayImageOptions options;

	SharedPreferences prefs;
	private int imageUri;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objectdensity);
		findViews();
		setTitle(R.string.lbPjDens);
		
		 prefs = this.getSharedPreferences(
			      "com.faulkestelescope.craterimpact", Context.MODE_PRIVATE);
	}

	private void findViews() {
		spinner1 = (Spinner) findViewById(R.id.Spinner1);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageDens = (ImageView) findViewById(R.id.imageDens);

		buttonNext.setOnClickListener(this);
		spinner1.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			int l = spinner1.getSelectedItemPosition();
			prefs.edit().putInt(Globals.projdensKey, l+1).commit();
		
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
			break;
		case 1:
			imageUri =   R.drawable.porous;
			break;
		case 2:

			imageUri = R.drawable.dense;
			break;
		case 3:
			imageUri = R.drawable.iron;
			break;
		default:
			break;
		}	
	
		imageDens.setImageResource(imageUri);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}

}
