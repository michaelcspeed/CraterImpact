package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener;
import org.holoeverywhere.widget.TextView;

import control.DataProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;

public class ObjectSizeActivity extends Activity implements OnClickListener,
		OnSeekBarChangeListener {
	private TextView textProjectileSize;
	private SeekBar seekBarProjectileSize;
	private Button buttonNext;
	private ImageView imageProjectile;
	private LayoutParams params;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.objectsizelayout);
		findViews();

		params = (LayoutParams) imageProjectile.getLayoutParams();

		params.width = 250;
		params.height = 250;
		imageProjectile.setLayoutParams(params);
		setTitle(R.string.lblAstDiam);

	}

	private void findViews() {
		textProjectileSize = (TextView) findViewById(R.id.textProjectileSize);
		seekBarProjectileSize = (SeekBar) findViewById(R.id.seekBarProjectileSize);
		buttonNext = (Button) findViewById(R.id.buttonNext);
		imageProjectile = (ImageView) findViewById(R.id.imageProjectile);

		seekBarProjectileSize.setOnSeekBarChangeListener(this);
		buttonNext.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == buttonNext) {
			DataProvider.setProjDiam(seekBarProjectileSize.getProgress());
			Intent intent = new Intent(this, ObjectVelocityActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onProgressChanged(SeekBar arg0, int progressNo, boolean arg2) {
		textProjectileSize.setText(progressNo + "m");

		params = (LayoutParams) imageProjectile.getLayoutParams();

		int coef = 30;
		if (progressNo > 2000 && progressNo < 11000) {

			params.width = progressNo / coef;
			params.height = progressNo / coef;
			// existing height is ok as is, no need to edit it
			imageProjectile.setLayoutParams(params);
		} else if (progressNo <= 2000) {
			params.width = 2000 / coef;
			params.height = 2000 / coef;
			// existing height is ok as is, no need to edit it
			imageProjectile.setLayoutParams(params);
		} else {
			params.width = 11000 / coef;
			params.height = 11000 / coef;
			// existing height is OK as is, no need to edit it
			imageProjectile.setLayoutParams(params);
		}
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
