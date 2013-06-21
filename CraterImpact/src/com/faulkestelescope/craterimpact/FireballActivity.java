package com.faulkestelescope.craterimpact;

import java.util.ArrayList;
import java.util.HashMap;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListActivity;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

import control.DataProvider;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class FireballActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.lblFireball));
		HashMap<String, String> fire = DataProvider.getDgFirevall();

		ArrayList<ParameterAndValueObject> items = new ArrayList<ParameterAndValueObject>();

		String rad = fire.get(getString(R.string.lbFbRad));
		String dur = fire.get(getString(R.string.lbFbDuration));
		String peak = fire.get(getString(R.string.lbFbPeaktime));

		// check for nulls here too. putting a dash in if there is no fireball
		items.add(new ParameterAndValueObject(getString(R.string.lbFbRad),
				rad != null ? rad : "-"));
		items.add(new ParameterAndValueObject(getString(R.string.lbFbDuration),
				dur != null ? dur : "-"));
		items.add(new ParameterAndValueObject(getString(R.string.lbFbPeaktime),
				peak != null ? peak : "-"));

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.input_values_list_item, getListView(), false);
		getListView().addHeaderView(header, null, false);

		setListAdapter(new ParameterAndValueListAdapter(this, items));

	}

}