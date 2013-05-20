package com.faulkestelescope.craterimpact;

import java.util.ArrayList;

import org.holoeverywhere.ArrayAdapter;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.widget.TextView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public class ParameterAndValueListAdapter extends ArrayAdapter<ParameterAndValueObject> {
	private final Context context;
	private final ArrayList<ParameterAndValueObject> values;
 
	public ParameterAndValueListAdapter(Context context, ArrayList<ParameterAndValueObject> values) {
		super(context, R.layout.input_values_list_item, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.input_values_list_item, parent, false);
		TextView pText = (TextView) rowView.findViewById(R.id.parameterText);
		TextView valueText = (TextView) rowView.findViewById(R.id.valueText);
		pText.setText(values.get(position).parameter);
		valueText.setText(values.get(position).value);

		return rowView;
	}
}