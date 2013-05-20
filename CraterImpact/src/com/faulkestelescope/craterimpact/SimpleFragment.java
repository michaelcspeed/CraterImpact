package com.faulkestelescope.craterimpact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SimpleFragment extends Fragment {

	public static SimpleFragment newInstance() {
		SimpleFragment frag = new SimpleFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(
				R.layout.simple_fragment, container,
				false);

		return mainView;
	}
}