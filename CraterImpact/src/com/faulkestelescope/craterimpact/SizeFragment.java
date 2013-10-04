package com.faulkestelescope.craterimpact;

import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemSelectedListener;
import org.holoeverywhere.widget.Spinner;

import android.app.Dialog;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.GroundOverlay;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;

import control.DataProvider;

public class SizeFragment extends SherlockFragment implements
		OnInfoWindowClickListener, LocationListener, OnClickListener,
		OnItemSelectedListener, OnMapClickListener {

	private GoogleMap map;
	private boolean mapVerified;
	private LatLng myLoc;

	private Location location;
	private View mainView;
	private Spinner spinner;
	private double lat;
	private double lon;
	private double depth;
	private double diam;
	private ImageButton button;
	private GroundOverlay groundOverlay;

	public static SizeFragment newInstance() {
		SizeFragment frag = new SizeFragment();
		return frag;
	}

	private void findViews() {

		button = (ImageButton) mainView.findViewById(R.id.maptypebutton);
		button.setOnClickListener(this);

		mapVerified = false;

		if (map == null) {
			map = ((SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map))
					.getMap();
			// Check if we were successful in obtaining the map.
			if (map != null) {
				mapVerified = true;
				map.setOnMapClickListener(this);
			}
		}

		// Getting Google Play availability status
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getActivity().getBaseContext());

		// Showing status
		if (status != ConnectionResult.SUCCESS) { // Google Play Services are
													// not available

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,
					getActivity(), requestCode);
			dialog.show();
			mapVerified = false;
			button.setVisibility(View.INVISIBLE);
		} else { // Google Play Services are available
			button.setVisibility(View.VISIBLE);
			// Getting reference to the SupportMapFragment of activity_main.xml
			SupportMapFragment fm = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().findFragmentById(R.id.map);

			// Getting GoogleMap object from the fragment
			map = fm.getMap();

			// Enabling MyLocation Layer of Google Map
			map.setMyLocationEnabled(true);
			map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

			// Getting LocationManager object from System Service
			// LOCATION_SERVICE
			LocationManager locationManager = (LocationManager) getActivity()
					.getSystemService(getActivity().LOCATION_SERVICE);

			// Creating a criteria object to retrieve provider
			Criteria criteria = new Criteria();

			// Getting the name of the best provider
			String provider = locationManager.getBestProvider(criteria, true);

			if (provider != null) {

				// Getting Current Location
				location = locationManager.getLastKnownLocation(provider);

				if (location != null) {
					onLocationChanged(location);
				}
				locationManager
						.requestLocationUpdates(provider, 20000, 0, this);

			}

		}

		spinner = (Spinner) mainView.findViewById(R.id.craterSpinner);
		spinner.setOnItemSelectedListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mainView = inflater.inflate(R.layout.size_fragment, container, false);

		findViews();

		if (mapVerified) {

			try {

				map.animateCamera(CameraUpdateFactory
						.newLatLngZoom(myLoc, 5.0f));
			} catch (Exception e) {
				Toast.makeText(getActivity(), "Cannot get GPS location",
						Toast.LENGTH_SHORT).show();

			}
		}

		return mainView;

	}

	@Override
	public void onLocationChanged(Location location) {

		try {

			// Getting latitude of the current location
			double latitude = location.getLatitude();

			// Getting longitude of the current location
			double longitude = location.getLongitude();

			myLoc = new LatLng(latitude, longitude);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.maptypebutton:
			// Set mapy type to satellite if it's normal, switch back if it's
			// satellite
			map.setMapType(map.getMapType() == GoogleMap.MAP_TYPE_NORMAL ? GoogleMap.MAP_TYPE_SATELLITE
					: GoogleMap.MAP_TYPE_NORMAL);
			break;

		default:
			break;
		}
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View v, int pos, long id) {

		switch (pos) {
		case 0:
			// cardiff
			lat = 51.482;
			lon = -3.172;
			break;
		case 1:
			// london
			lat = 51.514;
			lon = -0.103;
			break;

		case 2:
			// paris
			lat = 48.856;
			lon = 2.352;
			break;
		case 3:
			// nyc
			lat = 40.714;
			lon = -74.0059;
			break;
		case 4:
			// az
			lat = 34.0482;
			lon = -111.091;
			break;
		case 5:
			// wolfe
			lat = -19.1235;
			lon = 127.766;
			break;
		case 6:
			// roter kamm
			lat = -27.769;
			lon = 16.28238;
			break;
		case 7:
			// mistatin lake, canada
			lat = 55.911496;
			lon = -63.2589;
			break;
		case 8:
			// ghana
			lat = 6.493309;
			lon = -1.369;
			break;
		case 9:
			// tajikstan
			lat = 39.0106;
			lon = 73.563691;
			break;

		}

		postOverlay();

	}

	private void postOverlay() {

		try {
			depth = DataProvider.getImpactor().crDepth;
			diam = DataProvider.getImpactor().crDiam;
		} catch (Exception e) {
			depth = 100;
			diam = 100000;
		}

		// First time the spinner is selected, map might not be initialised
		try {
			MapsInitializer.initialize(getActivity().getApplicationContext());

			BitmapDescriptor image = BitmapDescriptorFactory
					.fromResource(R.drawable.craterimpact);

			LatLngBounds bounds = getBounds();

			// deletes last ground overlay. It'll be null the first time around
			if (groundOverlay != null)
				groundOverlay.remove();

			// Adds a ground overlay with 50% transparency.
			groundOverlay = map
					.addGroundOverlay(new GroundOverlayOptions().image(image)
							.positionFromBounds(bounds).transparency(0.5f));

			CameraPosition camPos = new CameraPosition(new LatLng(lat, lon), 6,
					map.getCameraPosition().tilt,
					map.getCameraPosition().bearing);
			map.animateCamera(CameraUpdateFactory.newCameraPosition(camPos),
					2000, null);

		} catch (GooglePlayServicesNotAvailableException e) {

			e.printStackTrace();
		}

	}

	private LatLngBounds getBounds() {

		LatLngBounds b = new LatLngBounds(getSECoord(lat, lon, diam),
				getNECoord(lat, lon, diam));
		return b;
	}

	double r = 6378137; // earth radius, sphere

	// Converts meters to degrees LAT
	private LatLng getSECoord(double lat, double lon, double length) {
		double dn = -diam / 2;
		double de = dn;

		// Coordinate offsets in radians
		double dLat = dn / r;
		double dLon = de / (r * Math.cos(Math.PI * lat / 180));

		return new LatLng(lat + dLat * 180 / Math.PI, lon + dLon * 180
				/ Math.PI);
	}

	private LatLng getNECoord(double lat, double lon, double length) {
		double dn = diam / 2;
		double de = dn;

		// Coordinate offsets in radians
		double dLat = dn / r;
		double dLon = de / (r * Math.cos(Math.PI * lat / 180));

		return new LatLng(lat + dLat * 180 / Math.PI, lon + dLon * 180
				/ Math.PI);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
	}

	@Override
	public void onMapClick(LatLng point) {
		lat = point.latitude;
		lon = point.longitude;
		postOverlay();
	}
}