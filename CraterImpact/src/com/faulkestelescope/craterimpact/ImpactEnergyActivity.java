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

public class ImpactEnergyActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(getString(R.string.lblImpEnergy));

		ArrayList<ParameterAndValueObject> items = new ArrayList<ParameterAndValueObject>();

		HashMap<String, String> inputs = DataProvider.getDgEnergy();
		String ke = inputs.get(getString(R.string.lbKE));
		String ime = inputs.get(getString(R.string.lbImE));
		String freq = inputs.get(getString(R.string.lbFreq));
		
		ke = ke.replace(".0</sup>", "</sup>");
		ime = ime.replace(".0</sup>", "</sup>");
		
		items.add(new ParameterAndValueObject(getString(R.string.lbKE), ke));
		items.add(new ParameterAndValueObject(getString(R.string.lbImE), ime));
		items.add(new ParameterAndValueObject(getString(R.string.lbFreq), freq));

		
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.input_values_list_item, getListView(), false);
		getListView().addHeaderView(header, null, false);

		setListAdapter(new ParameterAndValueListAdapter(this, items));

	}

}