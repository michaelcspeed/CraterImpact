package com.faulkestelescope.craterimpact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SizeFragment extends Fragment {

	public static SizeFragment newInstance() {
		SizeFragment frag = new SizeFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(
				R.layout.size_fragment, container,
				false);

		return mainView;
	}
}