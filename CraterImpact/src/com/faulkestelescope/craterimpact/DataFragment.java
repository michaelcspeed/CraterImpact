package com.faulkestelescope.craterimpact;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class DataFragment extends Fragment implements OnClickListener {

	private Button inputButton;
	private Button damageButton;
	private Button energyButton;
	private Button resultResult;
	private Button fireballButton;
	private View mainView;

	private void findViews() {
		inputButton = (Button) mainView.findViewById(R.id.inputButton);
		damageButton = (Button) mainView.findViewById(R.id.damageButton);
		energyButton = (Button) mainView.findViewById(R.id.energyButton);
		resultResult = (Button) mainView.findViewById(R.id.resultResult);
		fireballButton = (Button) mainView.findViewById(R.id.fireballButton);

		inputButton.setOnClickListener(this);
		damageButton.setOnClickListener(this);
		energyButton.setOnClickListener(this);
		resultResult.setOnClickListener(this);
		fireballButton.setOnClickListener(this);
	}

	
	@Override
	public void onClick(View v) {
		if (v == inputButton) {
			Intent intent = new Intent(getActivity(), InputActivity.class);
			startActivity(intent);
		} else if (v == damageButton) {
			Intent intent = new Intent(getActivity(), DamageActivity.class);
			startActivity(intent);
		} else if (v == energyButton) {
			Intent intent = new Intent(getActivity(), ImpactEnergyActivity.class);
			startActivity(intent);
		} else if (v == resultResult) {
			Intent intent = new Intent(getActivity(), null);
			startActivity(intent);
		} else if (v == fireballButton) {
			Intent intent = new Intent(getActivity(), FireballActivity.class);
			startActivity(intent);
		}
	}

	public static DataFragment newInstance() {
		DataFragment frag = new DataFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.data_fragment, container, false);

		findViews();

		return mainView;
	}

}