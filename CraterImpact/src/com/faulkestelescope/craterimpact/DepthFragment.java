package com.faulkestelescope.craterimpact;

import java.util.HashMap;

import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import control.DataProvider;

public class DepthFragment extends Fragment implements OnItemSelectedListener {

	public static DepthFragment newInstance() {
		DepthFragment frag = new DepthFragment();
		return frag;
	}

	private ImageView craterImage;
	private ImageView buildingImage;
	private TextView hAndWText;
	private Spinner craterSpinner;
	private double c; // constant height of building
	private double cp; // current pixels
	private double rp; // resulting pixels
	private double depth;
	private double diam;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(R.layout.depth_fragment, container,
				false);

		buildingImage = (ImageView) mainView.findViewById(R.id.buildingImage);
		craterImage = (ImageView) mainView.findViewById(R.id.craterImage);
		hAndWText = (TextView) mainView.findViewById(R.id.heightAndWidthText);
		craterSpinner = (Spinner) mainView.findViewById(R.id.craterSpinner);

		craterSpinner.setOnItemSelectedListener(this);

		HashMap<String, String> outarray = DataProvider.getDgOutputs();

		String depthString = outarray.get(getString(R.string.lbCrDepth));
		String diamString = outarray.get(getString(R.string.lbCrDiam));

		hAndWText.setText(depthString + " x " + diamString);
		return mainView;
	}

	@Override
	public void onItemSelected(AdapterView<?> parentView, View arg1,
			int itemselected, long arg3) {
		Drawable d;
		double height = 0;
		switch (itemselected) {
		case 0:
			d = getResources().getDrawable(R.drawable.spynx);
			height = 20;
			break;

		case 1:
			d = getResources().getDrawable(R.drawable.bigben);
			height = 96;
			break;

		case 2:
			d = getResources().getDrawable(R.drawable.eifel_tower);
			height = 324;
			break;

		case 3:
			d = getResources().getDrawable(R.drawable.empire_state);
			height = 449;
			break;

		case 4:
			d = getResources().getDrawable(R.drawable.cn_tower);
			height = 553;
			break;

		case 5:
			d = getResources().getDrawable(R.drawable.burj_dubai);
			height = 800;
			break;

		default:
			d = getResources().getDrawable(R.drawable.spynx);
			break;
		}
		scaleImage(d, height);
	}

	private void scaleImage(Drawable d, double buildingHeight) {

		double height = d.getIntrinsicHeight();
		double width = d.getIntrinsicWidth();

		double crDepth = DataProvider.getImpactor().crDepth;

		// 340 is the amount of pixels in the crater in the image
		double scaler = (340 / crDepth);
		double rp = height * scaler;
		double rw = width * scaler;

		// Turn the new dimens into ints because pixels are whole numbers
		int rpInt = (int) Math.round(rp);
		int rwInt = (int) Math.round(rw);
		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		Drawable d2 = new BitmapDrawable(getResources(),
				Bitmap.createScaledBitmap(bitmap, rwInt, rpInt, true));

		buildingImage.setImageDrawable(d2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

	}
}