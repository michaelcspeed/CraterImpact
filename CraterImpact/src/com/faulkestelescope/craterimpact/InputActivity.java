package com.faulkestelescope.craterimpact;

import java.util.ArrayList;
import java.util.HashMap;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListActivity;
import org.holoeverywhere.preference.SharedPreferences;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

import control.DataProvider;

import Calcs.CraterCalcs;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;

public class InputActivity extends ListActivity {

	private SharedPreferences prefs;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefs = this.getSharedPreferences("com.faulkestelescope.craterimpact",
				Context.MODE_PRIVATE);

		ArrayList<ParameterAndValueObject> items = new ArrayList<ParameterAndValueObject>();

		// I expected this to be pjMass but the website
		// also uses crMass so I'm
		// leaving it as is.

		HashMap<String, String> inputs = DataProvider.getDgInputs();
		HashMap<String, String> fire = DataProvider.getDgFirevall();

		String imm = inputs.get(getString(R.string.lbImM));
		String pjvel = inputs.get(getString(R.string.lbPjVel));
		String pjang = inputs.get(getString(R.string.lbPjAng));
		String pjdens = inputs.get(getString(R.string.lbPjDens));
		String tgdens = inputs.get(getString(R.string.lbTgDens));
		String fbrad = fire.get(getString(R.string.lbFbRad));
		
		imm = imm.replace(".0</sup>", "</sup>");
		fbrad = fbrad.replace(".0</sup>", "</sup>");

		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbImM), imm));
		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbPjVel), pjvel));
		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbPjAng), pjang));
		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbPjDens), pjdens));
		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbTgDens), tgdens));
		//fbrad may be null if there is no fireball
		items.add(new ParameterAndValueObject(getResources().getString(
				R.string.lbFbRad), fbrad!=null?fbrad:"-"));
		
		
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.input_values_list_item, getListView(), false);
		getListView().addHeaderView(header, null, false);

		setListAdapter(new ParameterAndValueListAdapter(this, items));

	}

}