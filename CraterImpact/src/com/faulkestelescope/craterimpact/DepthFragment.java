package com.faulkestelescope.craterimpact;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DepthFragment extends Fragment {

	public static DepthFragment newInstance() {
		DepthFragment frag = new DepthFragment();
		return frag;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View mainView = inflater.inflate(
				R.layout.depth_fragment, container,
				false);

		return mainView;
	}
}