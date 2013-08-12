package com.faulkestelescope.craterimpact;

import org.holoeverywhere.app.Activity;

import control.DataProvider;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitle(getString(R.string.lblWhatImpactor));
		setContentView(R.layout.resultsactivity);
		TextView t = (TextView) findViewById(R.id.resultstext);
		String text = DataProvider.getImpactor().imDesc;
		try {
			t.setText(Html.fromHtml(text));
		} catch (Exception e) {
			t.setText("");
		}
	}

}