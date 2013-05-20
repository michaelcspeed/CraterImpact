package com.faulkestelescope.craterimpact;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class ResultsTabControllerActivity extends SherlockFragmentActivity {

	private static String TAG = "TabActivity";
	public static Context appContext;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_activity);

		// ActionBar gets initiated
		ActionBar actionbar = getSupportActionBar();
		// Tell the ActionBar we want to use Tabs.
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// initiating both tabs and set text to it.
		ActionBar.Tab sizeTab = actionbar.newTab().setText("Size");
		ActionBar.Tab depthTab = actionbar.newTab().setText("Depth");
		ActionBar.Tab dataTab = actionbar.newTab().setText("Data");

		// create the two fragments we want to use for display content
		Fragment sizeFragment = new SizeFragment();
		Fragment depthFragment = new DepthFragment();
		Fragment dataFragment = new DataFragment();

		// set the Tab listener. Now we can listen for clicks.
		sizeTab.setTabListener(new MyTabsListener(sizeFragment));
		depthTab.setTabListener(new MyTabsListener(depthFragment));
		dataTab.setTabListener(new MyTabsListener(dataFragment));
		
		// add the two tabs to the actionbar
		actionbar.addTab(sizeTab);
		actionbar.addTab(depthTab);
		actionbar.addTab(dataTab);

	}

	class MyTabsListener implements ActionBar.TabListener {
		public Fragment fragment;

		public MyTabsListener(Fragment fragment) {
			this.fragment = fragment;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if (fragment == null) {
				Log.v(TAG, "fragment is null");
			}

			if (ft == null) {
				Log.v(TAG, "fragment TRANSACTION is null");
			}

			ft.replace(R.id.fragment_container, fragment);
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// Toast.makeText(getApplicationContext(), "Reselected!",
			// Toast.LENGTH_LONG).show();

		}

	}
}