package com.faulkestelescope.craterimpact;

import java.util.ArrayList;

import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.app.ListActivity;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

public class InputActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ArrayList<ParameterAndValueObject> items = new ArrayList<ParameterAndValueObject>();

		items.add(new ParameterAndValueObject("hello", "hello"));
		items.add(new ParameterAndValueObject("hello", "hello"));
		items.add(new ParameterAndValueObject("hello", "hello"));
		items.add(new ParameterAndValueObject("hello", "hello"));

		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(
				R.layout.input_values_list_item, getListView(), false);
		getListView().addHeaderView(header, null, false);

		setListAdapter(new ParameterAndValueListAdapter(this, items));

	}

}