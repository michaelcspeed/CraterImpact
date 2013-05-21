package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;

import control.DataProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class ObjectDensityActivity extends Activity implements OnClickListener,
		OnItemSelectedListener {

	private Spinner Spinner1;
	private Button buttonNext;
	private ImageView imageDens;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.objectdensity);
		findViews();
		setTitle(R.string.lbPjDens);
	}

	private void findViews() {
		Spinner1 = (Spinner) findViewById(R.id.Spinner1);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageDens = (ImageView) findViewById(R.id.imageDens);

		buttonNext.setOnClickListener(this);
		Spinner1.setOnItemSelectedListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			DataProvider.setCbPjDens(Spinner1.getSelectedItemPosition());
			Intent intent = new Intent(this, ObjectTargetDensActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {

		Bitmap btm = null;
		switch (arg2) {
		case 0:

			btm = BitmapFactory.decodeResource(getResources(), R.drawable.ice);
			break;
		case 1:
			btm = BitmapFactory.decodeResource(getResources(), R.drawable.porousrock);
			break;
		case 2:

			btm = BitmapFactory.decodeResource(getResources(), R.drawable.denserock);
			break;
		case 3:
			btm = BitmapFactory.decodeResource(getResources(), R.drawable.iron);
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
