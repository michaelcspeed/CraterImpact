package com.faulkestelescope.craterimpact;

import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Spinner;
import org.holoeverywhere.widget.TextView;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.actionbarsherlock.app.SherlockFragment;

import control.DataProvider;

public class DepthFragment extends SherlockFragment implements
		OnItemSelectedListener {

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
	private int w; // width of screen
	private int h; // height of screen
	private String diamString;
	private String depthString;
	private View mainView;
	private CraterDrawView canvasView;

	private void findViews() {
		craterSpinner = (Spinner) mainView.findViewById(R.id.craterSpinner);
		canvasView = (CraterDrawView) mainView.findViewById(R.id.canvasView);

		craterSpinner.setOnItemSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.depth_fragment, container, false);

		findViews();


		return mainView;

	}


	@Override
	public void onItemSelected(AdapterView<?> parentView, View arg1,
			int itemselected, long arg3) {
		Log.d("ci", "test");
		canvasView.changeBuilding(itemselected);

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

		if (rpInt == 0)
			rpInt = 1;
		if (rwInt == 0)
			rwInt = 1;
		
		buildingImage.getLayoutParams().height = rpInt;
		buildingImage.getLayoutParams().width = rwInt;

		Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
		Drawable d2 = new BitmapDrawable(getResources(),
				Bitmap.createScaledBitmap(bitmap, rwInt, rpInt, true));

		buildingImage.setImageDrawable(d2);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

}