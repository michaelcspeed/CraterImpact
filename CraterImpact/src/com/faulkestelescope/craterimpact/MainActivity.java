package com.faulkestelescope.craterimpact;

import java.util.Locale;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.Button;
import org.holoeverywhere.widget.Spinner;

import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener,
		org.holoeverywhere.widget.AdapterView.OnItemSelectedListener {

	protected Dialog mSplashDialog;
	private Spinner spinner1;
	private Button button1;
	private ImageView imageView1;

	/**
	 * Removes the Dialog that displays the splash screen
	 */
	protected void removeSplashScreen() {
		if (mSplashDialog != null) {
			mSplashDialog.dismiss();
			mSplashDialog = null;
		}
	}

	protected void showSplashScreen() {
		mSplashDialog = new Dialog(this, R.style.SplashScreen);
		mSplashDialog.setContentView(R.layout.splashscreen);
		mSplashDialog.setCancelable(false);
		mSplashDialog.show();

		// Set Runnable to remove splash screen just in case
		final Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				removeSplashScreen();
			}
		}, 3000);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// showSplashScreen();

		setContentView(R.layout.activity_main);
		findViews();
		setTitle(R.string.lblTitle);

	}

	private void findViews() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		button1 = (Button) findViewById(R.id.button1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		spinner1.setOnItemSelectedListener(this);
		button1.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == button1) {

			String[] languages = getResources().getStringArray(
					R.array.languagesCodes);
			int i = spinner1.getSelectedItemPosition();
			setLanguage(languages[i]);

			Intent intent = new Intent(this, ObjectSizeActivity.class);
			startActivity(intent);

		}
	}

	private void setLanguage(String lang) {
		Locale locale = new Locale(lang);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		getBaseContext().getResources().updateConfiguration(config,
				getBaseContext().getResources().getDisplayMetrics());

	}

	


	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int i,
			long arg3) {
		String[] languages = getResources().getStringArray(
				R.array.languagesCodes);
	
		setLanguage(languages[i]);
		
		button1.setText(R.string.btStart);
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

}