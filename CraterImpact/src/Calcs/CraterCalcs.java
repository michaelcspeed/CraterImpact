package Calcs;

import java.text.DecimalFormat;
import java.util.HashMap;
import com.faulkestelescope.craterimpact.R;
import control.DataProvider;
import android.content.Context;
import ImpactModel.ImpactModel;
import android.util.Log;

//###############################################################################
/**
 * This class holds the core data model for the crater impact application
 * holding data for target, projectile, calculated cater values, crater
 * dimensions, fireball, Ejecta, Air Blast, Seismic activity and impact.
 * 
 * @author asscott
 * @version v1.0
 */
// ###############################################################################
public class CraterCalcs {

	// Variables used in calculations
	// import
	// mx.collections.ArrayCollection;<--------------------------------------------Collections
	// Required??

	public double velocity; // velocity at target surface km/s
	// public double distance; // distance in km
	public double mwater; // mass of water
	public double vseafloor; // velocity of projectile at seafloor
	public double energy0; // input energy before atmospheric entry
	public double energy_power;
	public double energy0_megatons;
	public double megaton_power;
	public double megaton_factor;
	public double energy_megatons; // energy at suface in MT
	public double energy_seafloor; // energy at seafloor (bottom of water layer)
	public double iFactor; // intact factor >= 1, projectile lands intact
	public double dispersion; // dispersion of pancake upon impact, 2 *
								// semimajor axis of pancaked impactor
	public double delta; // angle of the distance entered, measured from center
							// of the earth
	public double des; // description of intensity
	public double wdiameter; // diameter of crater in water
	public double sf; // scale factor for shock wave calculations
	public double h; // part of the fireball below the horizon
	public double no_radiation; // set to true if the fireball is below horizon
	public double irradiation_time; // time of irradiation

	// Constants
	public double PO = Math.pow(10, 5); // ambient pressure in Pa
	public double G = 9.8; // acceleration due to gravity
	public double R_earth = 6370; // radius of the earth in km
	public int surface_wave_v = 5; // velocity of surface wave in km/s
	public double melt_coeff = 8.9 * Math.pow(10, -21); // coefficient for melt
														// volume calc
	public double vEarth = 1.1 * Math.pow(10, 12); // volume of earth in km^3
	public double vratio; // ratio of volume of crater to volume of earth
	public double mratio; // ratio of melt to volume of earth
	public double mcratio; // ratio of melt to volume of crater
	public double lEarth = 5.86 * Math.pow(10, 33); // angular momen. of earth
													// in (kg m^3)/sec
	public double lratio; // ratio of proj ang. momen, to $lEarth
	public double pEarth = 1.794 * Math.pow(10, 32); // lin. momen of earth in
														// (kg * m) / sec
	public double pratio; // ratio of proj lin. momen to $pEarth
	public int rhoSurface = 1; // suface density of atmosphere kg/m^3
	public int scaleHeight = 8000; // scale height of atmosphere in m
	public int dragC = 1; // drag coefficient
	public int fp = 7; // pancake factor
	public int valid_data = 1;
	public String returnVal;
	public String freq_text;

	public double cSize; // = new Number(); //variable passed onto flash file
	public double cDepth; // = new Number(); //variable passed onto flash file

	public Context ctx;
	/** The Android context **/

	public ImpactModel impactor = new ImpactModel();

	/** For logging events and values for debugging purposes **/
	public final String LOGTAG = "CraterImpact";

	DecimalFormat nbFormat = new DecimalFormat("#,###,###,##0");
	DecimalFormat nbFormat2 = new DecimalFormat("#,###,###,##0.00");
	DecimalFormat nbFormat3 = new DecimalFormat("#,###,###,##0.00000");

	// ==================================================================
	/**
	 * A constructor for the CraterCalcs.
	 * 
	 * @param ctx
	 *            The Android contexts useful for getting strings.
	 */
	// ==================================================================
	public CraterCalcs(Context ctx) {
		this.ctx = ctx;
	}// =================================================================

	// ==================================================================
	/**
	 * Gets the core data by performing the main calculations.
	 */
	// ==================================================================
	public void getData() {
		Log.d("ci", "" + DataProvider.getCbPjDens());
		Log.d("ci", "" + DataProvider.getProjAngle());
		Log.d("ci", "" + DataProvider.getProjDiam());

		// distance = 100;
		loadParameters();
		main_calculation();

		/*
		 * if (impactor.imDist < impactor.crDiam){ impactor.imDist =
		 * impactor.crDiam /100; loadParameters(); main_calculation(); }
		 */
		sentData();

	}// =================================================================

	// ==================================================================
	/**
	 * Resets all the model values to zero
	 */
	// ==================================================================
	public void loadParameters() {
		// Alert.show(message, "Crater parameters");
		// impactor.reset();

		Log.d("ci", "Proj dens: " + DataProvider.getCbPjDens());
		Log.d("ci", "Targ dens: " + DataProvider.getCbTgDens());

		impactor.pjDiam = DataProvider.getProjDiam();
		impactor.imDist = DataProvider.getImpactDist();
		impactor.pjDens = DataProvider.getCbPjDens();
		impactor.tgDens = DataProvider.getCbTgDens();
		impactor.pjAngle = DataProvider.getProjAngle();
		impactor.pjVel = DataProvider.getProjVel();
		impactor.tgDepth = DataProvider.getSlTgDepth();

		switch (DataProvider.getCbPjDens()) {
		case 1:
			impactor.pjDens = 1000.0;
			break;
		case 2:
			impactor.pjDens = 1500.0;
			break;
		case 3:
			impactor.pjDens = 3000.0;
			break;
		case 4:
			impactor.pjDens = 8000.0;
			break;
		}// end switch

		// Convert target density to a number
		switch (DataProvider.getCbTgDens()) {
		case 1:
			impactor.tgDens = 1000.0;
			// Only water is allowed a depth
			impactor.tgDepth = DataProvider.getSlTgDepth();
			break;
		case 2:
			impactor.tgDens = 2500.0;
			impactor.tgDepth = 0;
			break;
		case 3:
			impactor.tgDens = 2750.0;
			impactor.tgDepth = 0;
			break;
		}// end switch

	}// end load parameters===============================================

	// ==================================================================
	/**
	 * 
	 * @pre: All necessary parameters are valid, results calculated
	 * @post: Displays the results to the user
	 */
	// ==================================================================
	public void main_calculation() {

		Log.i(LOGTAG, "P diam " + impactor.pjDiam);
		Log.i(LOGTAG, "P dens " + impactor.pjDens);
		Log.i(LOGTAG, "V input " + impactor.pjVel);
		Log.i(LOGTAG, "Target dens " + impactor.tgDens);

		calc_energy();
		if (impactor.pjDiam <= 1000.) {
			print_atmosphere();
		}
		find_crater();

		if (impactor.abAltBurst <= 0.) {

			find_ejecta();
			Log.i(LOGTAG, "Vel " + impactor.imVel);

			if (impactor.imVel >= 15) {
				Log.i(LOGTAG, "Running find thermal");
				find_thermal();
			}
			find_magnitude();
		}

		air_blast();

		Log.i(LOGTAG, "Energy " + impactor.imEnergy);
	}// =================================================================

	// ==================================================================
	/**
	 * Retrieves from the model and packages the calculated results by sending
	 * the data to the data provider class from where it may be collected by the
	 * UI.
	 */
	// ==================================================================
	public void sentData() {
		HashMap<String, String> inArray = new HashMap<String, String>();

		inArray.put(getString(R.string.lbImM), standform(impactor.crMass)
				+ " kg");
		inArray.put(getString(R.string.lbPjVel),
				nbFormat.format(impactor.pjVel) + " km/s");
		inArray.put(getString(R.string.lbPjAng),
				nbFormat.format(impactor.pjAngle) + "&deg;");
		inArray.put(getString(R.string.lbPjDens),
				nbFormat.format(impactor.pjDens)
						+ " kg/m<sup><small>2</small></sup>");
		inArray.put(getString(R.string.lbTgDens),
				nbFormat.format(impactor.tgDens)
						+ " kg/m<sup><small>3</small></sup>");

		HashMap<String, String> outarray = new HashMap<String, String>();

		if (impactor.crDepth > 1)
			outarray.put(getString(R.string.lbCrDepth),
					nbFormat.format(impactor.crDepth) + " m");

		if (impactor.crDiam > 1)
			outarray.put(getString(R.string.lbCrDiam),
					nbFormat.format(impactor.crDiam) + " m");

		if (impactor.ejThickness > 0.0001)
			outarray.put(getString(R.string.lbCrThickness),
					nbFormat2.format(impactor.ejThickness) + " m");

		if (impactor.abAltBurst != 0)
			outarray.put(getString(R.string.lbAbBurst),
					nbFormat.format(impactor.abAltBurst) + " m");

		if (impactor.abAltBreak != 0)
			outarray.put(getString(R.string.lbAbBreak),
					nbFormat.format(impactor.abAltBreak) + " m");

		if (impactor.abWindvel > 0.5)
			outarray.put(getString(R.string.lbAbVel),
					nbFormat.format(impactor.abWindvel) + " m/s");

		outarray.put(getString(R.string.lbAbRichter),
				nbFormat.format(impactor.smRichter) + " m/s");

		if (impactor.abAmpl > 0)
			outarray.put(getString(R.string.lbAbAmpl),
					nbFormat.format(impactor.abAmpl) + " dB");

		HashMap<String, String> firearray = new HashMap<String, String>();
		firearray.put(getString(R.string.lbFbRad),
				nbFormat2.format(impactor.fbRadius) + " km");
		firearray.put(getString(R.string.lbFbDuration),
				nbFormat3.format(impactor.fbDuration) + " s");
		firearray.put(getString(R.string.lbFbPeaktime),
				standform(impactor.fbPeaktime) + " s");

		if (impactor.fbExposure != 0)
			firearray.put(getString(R.string.lbFbExp),
					standform(impactor.fbExposure) + " J/m�");

		HashMap<String, String> nodata = new HashMap<String, String>();
		nodata.put(getString(R.string.lbNoData), getString(R.string.lbNoData));

		HashMap<String, String> energyarray = new HashMap<String, String>();
		energyarray.put(getString(R.string.lbKE), standform(impactor.pjEnergy)
				+ " J");
		energyarray.put(getString(R.string.lbImE), standform(impactor.imEnergy)
				+ " J");
		energyarray.put(getString(R.string.lbFreq),
				nbFormat.format(impactor.imFreq) + " yrs");

		DataProvider.setDgInputs(inArray);
		DataProvider.setDgOutputs(outarray);
		DataProvider.setDgEnergy(energyarray);

		if (impactor.fbRadius > 0.01)
			DataProvider.setDgFirevall(firearray);
		else
			DataProvider.setDgFirevall(nodata);

		// Projectile travelling through the atmosphere
		impactor.imDesc = "";

		// if (impactor.pjDens > 1000){

		if (iFactor >= 1) {
			impactor.imDesc += getString(R.string.projectile1)
					+ nbFormat.format(impactor.imVel) + " km/s \n\n";
			impactor.imDesc += getString(R.string.projectile2)
					+ standform(impactor.imEnergy) + " J";
		}// end if
		else {
			if (impactor.abAltBurst > 0) {
				impactor.imDesc += getString(R.string.projectile3)
						+ nbFormat.format(impactor.abAltBurst) + " m\n\n";
				impactor.imDesc += getString(R.string.projectile4)
						+ nbFormat.format(impactor.imVel) + " km/s\n\n";
				impactor.imDesc += getString(R.string.projectile5)
						+ standform(impactor.imEnergy) + " J \n\n";
				impactor.imDesc += getString(R.string.projectile6);
			}// end if
			else {
				impactor.imDesc += getString(R.string.projectile7)
						+ nbFormat2.format(impactor.imVel) + " km/s";
				// impactor.imDesc +=
				// "The broken projectile fragments strike the ground in an ellipse of dimension <b>%g km by %g km</b>",
				// FormatSigFigs(dispersion/(1000 * sin(theta * pi /180)),
				// sigfigs), FormatSigFigs(dispersion / 1000, sigfigs));
			}// end else
		}// end else

		Log.i(LOGTAG, "Impact vel" + impactor.imVel);
		/*
		 * }else{ impactor.imDesc =
		 * "The impactor was cometary and it is likely that no solid fragments hit the earth."
		 * ; }
		 */
		// Thermal effects and blast wave

		impactor.smDesc = "";

		if (impactor.fbExposure > (Math.pow(10, 6) * megaton_factor))
			impactor.smDesc += getString(R.string.expos1) + "\n\n";

		if (impactor.fbExposure > (4.2 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos2) + "\n\n";
		else if (impactor.fbExposure > (2.5 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos3) + "\n\n";
		else if (impactor.fbExposure > (1.3 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos4) + "\n\n";

		if (impactor.fbExposure > (3.3 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos5) + "\n\n";

		if (impactor.fbExposure > (6.7 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos6) + "\n\n";

		if (impactor.fbExposure > (2.5 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos7) + "\n\n";

		if (impactor.fbExposure > (3.8 * Math.pow(10, 5) * megaton_factor))
			impactor.smDesc += getString(R.string.expos8) + "\n\n";

		if (impactor.abOpressure > 42600)
			impactor.smDesc += getString(R.string.opress1) + "\n\n";
		else if (impactor.abOpressure > 38500)
			impactor.smDesc += getString(R.string.opress2) + "\n\n";

		if (impactor.abOpressure > 26800)
			impactor.smDesc += getString(R.string.opress3) + "\n\n";
		else if (impactor.abOpressure > 22900)
			impactor.smDesc += getString(R.string.opress4) + "\n\n";

		if (impactor.abOpressure > 273000)
			impactor.smDesc += getString(R.string.opress5) + "\n\n";

		if (impactor.abOpressure > 121000)
			impactor.smDesc += getString(R.string.opress6) + "\n\n";
		else if (impactor.abOpressure > 100000)
			impactor.smDesc += getString(R.string.opress7) + "\n\n";

		if (impactor.abOpressure > 379000)
			impactor.smDesc += getString(R.string.opress8) + "\n\n";

		// //// damage descriptions: glass, transportation, forrests

		if (impactor.abOpressure > 6900)
			impactor.smDesc += getString(R.string.opress9) + "\n\n";

		if (impactor.abOpressure > 426000)
			impactor.smDesc += getString(R.string.opress10) + "\n\n";
		else if (impactor.abOpressure > 297000)
			impactor.smDesc += getString(R.string.opress11) + "\n\n";

		if (impactor.abOpressure < 6900)
			impactor.smDesc += getString(R.string.opress12) + "\n\n";

		if (impactor.abWindvel > 62)
			impactor.smDesc += getString(R.string.opress13) + "\n\n";
		else if (impactor.abWindvel > 40)
			impactor.smDesc += getString(R.string.opress14) + "\n\n";

		if ((impactor.crDiam / 2) >= (impactor.imDist * 1000))
			impactor.smDesc = getString(R.string.incrater);

		Log.i(LOGTAG, "Im Dist" + impactor.imDist);

		DataProvider.setTxtDamage(impactor.smDesc);
		// txDamage.text = impactor.smDesc;
		DataProvider.setTxtImpactor(impactor.imDesc);

		DataProvider.setImpactor(impactor);
		// txImpactor.text = impactor.imDesc;

		// Check if below lines are needed.

		// lbDamage.text =
		// dsLanguage.lastResult.damage1+nbFormat.format(impactor.imDist)+dsLanguage.lastResult.damage2;
		// Log.i(LOGTAG,txDamage.text);
		// setCraterSize();
		// sizeCompare();
		// showMap();
	}// =-==============================================================

	// ==================================================================
	/**
	 * Calculates the impact energy assigning results to class global impact
	 * model.
	 */
	// ==================================================================
	public void calc_energy() {
		// //// mass = density * volume, volume calculated assuming the
		// projectile to be approximately spherical
		// //// V = 4/3pi(r^3) = 1/6pi(d^3)
		double alpha;
		double beta;
		double linmom;
		double angmom;
		double freq_p;

		impactor.crMass = ((Math.PI * Math.pow(impactor.pjDiam, 3)) / 6)
				* impactor.pjDens;
		Log.i(LOGTAG, "Mass " + impactor.crMass);
		impactor.pjEnergy = 0.5 * impactor.crMass
				* Math.pow((impactor.pjVel * 1000), 2);
		energy0_megatons = impactor.pjEnergy / (4.186 * Math.pow(10, 15)); // ////
																			// joules
																			// to
																			// megatons
																			// conversion

		/*
		 * if(mass < 1){ print_noimpact(); }
		 */

		atmospheric_entry();

		linmom = impactor.crMass * (impactor.imVel * 1000);
		angmom = impactor.crMass * (impactor.imVel * 1000)
				* Math.cos(impactor.pjAngle * Math.PI / 180) * R_earth;

		if (impactor.pjVel > (0.25 * 3 * Math.pow(10, 5))) { // relativistic
																// effects,
																// multiply
																// energy by
																// 1/sqrt(1 -
																// v^2/c^2)
			beta = 1 / Math.sqrt(1 - Math.pow(impactor.pjVel, 2) / 9
					* Math.pow(10, 10));
			impactor.pjEnergy *= beta;
			linmom *= beta;
			angmom *= beta;
		}// end if

		if (impactor.abAltBurst > 0) {
			impactor.imEnergy = 0.5
					* impactor.crMass
					* (Math.pow(impactor.pjVel * 1000, 2) - Math.pow(
							impactor.imVel * 1000, 2));
			impactor.imMegaton = impactor.imEnergy / (4.186 * Math.pow(10, 15)); // ////
																					// joules
																					// to
																					// megatons
																					// conversion
		}// end if
		else {
			impactor.abAltBurst = 0;
			impactor.imEnergy = 0.5 * impactor.crMass
					* Math.pow(impactor.imVel * 1000, 2);
			impactor.imMegaton = impactor.imEnergy / (4.186 * Math.pow(10, 15)); // ////
																					// joules
																					// to
																					// megatons
																					// conversion
		}// end else

		lratio = angmom / lEarth;
		pratio = linmom / pEarth;

		mwater = (Math.PI * Math.pow(impactor.pjDiam, 2) / 4)
				* (impactor.tgDepth / Math
						.sin(impactor.pjAngle * Math.PI / 180)) * 1000;
		vseafloor = impactor.imVel
				* Math.exp(-(3 * 1000 * 0.877 * impactor.tgDepth)
						/ (2 * impactor.pjDens * impactor.pjDiam * Math
								.sin(impactor.pjAngle * Math.PI / 180)));
		energy_seafloor = 0.5 * impactor.crMass * Math.pow(vseafloor * 1000, 2);

		Log.i(LOGTAG, "" + vseafloor);
		Log.i(LOGTAG, "Im vel" + impactor.imVel);
		Log.i(LOGTAG, "Targ Dens" + impactor.tgDepth);
		Log.i(LOGTAG, "Pj Ang" + impactor.pjAngle);

		delta = (180 / Math.PI) * (impactor.imDist / R_earth);

		energy_power = Math.log(impactor.imEnergy) / Math.log(10);
		energy_power = (int) (energy_power);
		// impactor.pjEnergy /= Math.pow(10,energy_power);

		megaton_power = Math.log(energy0_megatons) / Math.log(10);
		megaton_power = (int) (megaton_power);
		energy0_megatons /= Math.pow(10, megaton_power);

		impactor.imFreq = 110 * Math.pow(
				energy0_megatons * Math.pow(10, megaton_power), 0.77);

		/*
		 * if(impactor.imFreq > 1000){ freq_p =
		 * Math.log(impactor.imFreq)/Math.LN10; freq_p = int(freq_p);
		 * impactor.imFreq /= Math.pow(10,freq_p);
		 * 
		 * if($freq * 10**$freq_p < 4.5e9){ printf(
		 * "<dd>The average interval between impacts of this size somewhere on Earth during the last 4 billion years is <b>%.1f x 10<sup>%.0f</sup>years</b>"
		 * , $freq, $freq_p); }else{ print
		 * "<dd>The average interval between impacts of this size is longer than the Earth's age."
		 * ; print
		 * "<dd>Such impacts could only occur during the accumulation of the Earth, between 4.5 and 4 billion years ago."
		 * ; } printf("</dl>\n"); return; }
		 */
	}// ===================================================================

	// ==================================================================
	/**
	 * Calculates statistics and details regarding atmospheric entry and assigns
	 * the results within the Global impact model.
	 */
	// ==================================================================
	public void atmospheric_entry() {
		double yield; // yield strength of projectile in Pa
		double av; // velocity decrement factor
		double rStrength; // strength ratio
		double vTerminal; // m/s
		double vSurface; // velocity of the impactor at surface in m/s if
							// greater than terminal velocity
		double altitude1;
		double omega;
		double vBU;
		double tmp;
		double vFac;
		double lDisper;
		double alpha2;
		double altitudePen;
		double expfac;
		double altitudeScale;
		double integral;

		yield = Math.pow(10, (2.107 + 0.0624 * Math.pow(impactor.pjDens, 0.5))); // yield
																					// strength
																					// of
																					// projectile
																					// in
																					// Pa

		av = 3
				* rhoSurface
				* dragC
				* scaleHeight
				/ (2 * impactor.pjDens * impactor.pjDiam * Math
						.sin(impactor.pjAngle * Math.PI / 180)); // velocity
																	// decrement
																	// factor

		rStrength = yield / Math.pow(rhoSurface * impactor.pjVel * 1000, 2); // strength
																				// ratio

		iFactor = 5.437 * av * rStrength;

		if (iFactor >= 1)// projectile lands intact
		{

			impactor.abAltBurst = 0;
			tmp = (2 * impactor.pjDens * impactor.pjDiam * G / (3 * rhoSurface * dragC));
			vTerminal = Math.pow(tmp, 0.5);
			vSurface = impactor.pjVel * 1000 * Math.exp(-av);

			if (vTerminal > vSurface)
				impactor.imVel = vTerminal;
			else
				impactor.imVel = vSurface;

		}// end if
		else // projectile does not land intact
		{

			// Alert.show("Projectile does not land");
			altitude1 = -scaleHeight * Math.log(rStrength);
			omega = 1.308 - 0.314 * iFactor - 1.303
					* Math.pow((1 - iFactor), 0.5);
			impactor.abAltBreak = altitude1 - omega * scaleHeight;
			vBU = impactor.pjVel
					* 1000
					* Math.exp(-av
							* Math.exp(-impactor.abAltBreak / scaleHeight)); // m/s

			vFac = 1.5
					* Math.pow((dragC * rhoSurface / (2 * impactor.pjDens)),
							0.5)
					* Math.exp(-impactor.abAltBreak / (2 * scaleHeight));
			lDisper = impactor.pjDiam
					* Math.sin(impactor.pjAngle * Math.PI / 180)
					* Math.pow((impactor.pjDens / (2 * dragC * rhoSurface)),
							0.5)
					* Math.exp(impactor.abAltBreak / (2 * scaleHeight));

			alpha2 = Math.pow((Math.pow(fp, 2) - 1), 0.5);
			altitudePen = 2 * scaleHeight
					* Math.log(1 + alpha2 * lDisper / (2 * scaleHeight));
			impactor.abAltBurst = impactor.abAltBreak - altitudePen;
			trace("Projectile does not land");

			if (impactor.abAltBurst > 0) { // impactor bursts in atmosphere
				expfac = 1
						/ 24
						* alpha2
						* (24 + 8 * Math.pow(alpha2, 2) + 6 * alpha2 * lDisper
								/ scaleHeight + 3 * Math.pow(alpha2, 3)
								* lDisper / scaleHeight);
				impactor.imVel = vBU * Math.exp(-expfac * vFac);
			}// end if
			else {
				altitudeScale = scaleHeight / lDisper;
				integral = Math.pow(altitudeScale, 3)
						/ 3
						* (3
								* (4 + 1 / Math.pow(altitudeScale, 2))
								* Math.exp(impactor.abAltBreak / scaleHeight)
								+ 6
								* Math.exp(2 * impactor.abAltBreak
										/ scaleHeight)
								- 16
								* Math.exp(3 * impactor.abAltBreak
										/ (2 * scaleHeight)) - 3
								/ Math.pow(altitudeScale, 2) - 2);
				impactor.imVel = vBU * Math.exp(-vFac * integral);
				dispersion = impactor.pjDiam
						* Math.pow(
								(1 + 4
										* Math.pow(altitudeScale, 2)
										* Math.pow(
												(Math.exp(impactor.abAltBreak
														/ (2 * scaleHeight)) - 1),
												2)), 0.5);

			}// end else

		}// end else

		impactor.imVel /= 1000;

	}// =================================================================

	// ==================================================================
	/**
	 * Crater based calculations to find the dimensions of the crater and its
	 * depth. Assigns the results to the class global impact model.
	 */
	// ==================================================================
	public void find_crater() {
		double Cd;
		double beta;
		double anglefac;
		double vbreccia;
		double rimHeightf;

		anglefac = Math.pow((Math.sin(impactor.pjAngle * Math.PI / 180)),
				(1 / 3));

		if (impactor.tgType == 1) {
			Cd = 1.88;
			beta = 0.22;
		}// end if
		else if (impactor.tgType == 2) {
			Cd = 1.54;
			beta = 0.165;
		}// end else if
		else {
			Cd = 1.6;
			beta = 0.22;
		}// end else

		if (impactor.tgDepth != 0) { // calculate crater in water using Cd =
										// 1.88 and beta = 0.22

			wdiameter = 1.88
					* (Math.pow((impactor.crMass / impactor.tgDens), 1 / 3))
					* Math.pow(((1.61 * G * impactor.pjDiam) / Math.pow(
							(impactor.imVel * 1000), 2)), (-0.22));
			wdiameter *= anglefac;

			impactor.tgDens = 2700; // change target density for seafloor crater
									// calculation
		}// end else

		// vseafloor == surface velocity if there is no water
		impactor.crTsDiam = Cd
				* (Math.pow((impactor.crMass / impactor.tgDens), 0.333))
				* Math.pow(((1.61 * G * impactor.pjDiam) / Math.pow(
						(vseafloor * 1000), 2)), (-beta));
		impactor.crTsDiam *= anglefac;

		if (dispersion >= impactor.crTsDiam) // if crater field is formed,
												// compute crater dimensions
												// assuming
			impactor.crTsDiam /= 2; // impact of largest fragment (with diameter
									// = 1/2 initial diameter)

		impactor.crTsDepth = impactor.crTsDiam / 2.828;

		if (impactor.crTsDiam * 1.25 >= 3200) { // complex crater will be
												// formed, use equation from
												// McKinnon and Schenk (1985)
			impactor.crDiam = (1.17 * Math.pow(impactor.crTsDiam, 1.13)) / (2.8554);
			impactor.crDepth = 37 * Math.pow(impactor.crDiam, 0.301);
		}// end if
		else// simple crater will be formed.
		{

			// Diameter of final crater in m
			impactor.crDiam = 1.25 * impactor.crTsDiam;

			// Breccia lens volume in m^3
			vbreccia = 0.032 * Math.pow(impactor.crDiam, 3); // in m^3

			// Rim height of final crater in m
			rimHeightf = 0.07 * Math.pow(impactor.crTsDiam, 4)
					/ Math.pow(impactor.crDiam, 3);

			// Thickness of breccia lens in m
			impactor.crBrecThick = 2.8
					* vbreccia
					* ((impactor.crTsDepth + rimHeightf) / (impactor.crTsDepth * Math
							.pow(impactor.crDiam, 2)));

			// Final crater depth (in m) = transient crater depth + final rim
			// height - breccia thickness
			impactor.crDepth = impactor.crTsDepth + rimHeightf
					- impactor.crBrecThick;

		}// end else

		impactor.crVol = (Math.PI / 24)
				* Math.pow((impactor.crTsDiam / 1000), 3);
		vratio = impactor.crVol / vEarth;

		if (impactor.imVel >= 12) {
			impactor.crVolMelt = melt_coeff * (energy_seafloor)
					* Math.sin(impactor.pjAngle * Math.PI / 180); // energy_seafloor
																	// =
																	// impactor.imEnergy
																	// if there
																	// is no
																	// water
																	// layer
			if (impactor.crVolMelt > vEarth)
				impactor.crVolMelt = vEarth;

			mratio = impactor.crVolMelt / vEarth;
			mcratio = impactor.crVolMelt / impactor.crVol;
		}// end if
		Log.i(LOGTAG, "Final Crater diam " + impactor.crDiam);
		Log.i(LOGTAG, "Final crater depth " + impactor.crDepth);
		Log.i(LOGTAG, "Transient crater " + impactor.crTsDiam);

	} // //// End o find_crater==========================================

	// ==================================================================
	/**
	 * Calculates statistics about the impact ejecta. Assigns the resuts to the
	 * class global impact model.
	 */
	// ==================================================================
	public void find_ejecta() {

		double phi = (impactor.imDist) / (2 * R_earth);
		double X = (2 * Math.tan(phi)) / (1 + Math.tan(phi));
		double e = -Math.pow((0.5 * Math.pow((X - 1), 2) + 0.5), 0.5); // eccentricity
																		// of
																		// eliptical
																		// path
																		// of
																		// the
																		// ejecta
		double a = (X * R_earth * 1000) / (2 * (1 - Math.pow(e, 2))); // semi
																		// major
																		// axis
																		// of
																		// elliptical
																		// path

		double part1 = Math.pow(a, 1.5)
				/ Math.pow((G * Math.pow(R_earth * 1000, 2)), 0.5);
		double term1 = 2 * Math.atan(Math.pow(((1 - e) / (1 + e)), 0.5)
				* Math.tan(phi / 2));
		double term2 = e * Math.pow((1 - Math.pow(e, 2)), 0.5) * Math.sin(phi)
				/ (1 + e * Math.cos(phi));

		impactor.ejFalltime = 2 * part1 * (term1 - term2);

		impactor.ejThickness = Math.pow(impactor.crTsDiam, 4)
				/ (112 * Math.pow(impactor.imDist * 1000, 3));
		Log.i(LOGTAG, "Ejecta thickness " + impactor.ejThickness);

		// //// compute mean fragment size
		double a2 = 2.65;
		double half_diameter = (impactor.crDiam / 1000) / 2; // half of final
																// crater
																// diameter in
																// km
		double dc = 2400 * Math.pow(half_diameter, -1.62);

		impactor.ejFragsize = dc
				* Math.pow((half_diameter / impactor.imDist), a2);

	}// //// end of find_ejecta==========================================

	// ==================================================================
	/**
	 * Calculates thermal statistics and assigns the results to the class global
	 * impact model.
	 */
	// ==================================================================
	public void find_thermal() {
		double eta;
		double T_star;
		double sigma;
		double del;
		double f;

		eta = 3 * Math.pow(10, -3); // // factor for scaling thermal energy
		T_star = 3000; // // temperature of fireball

		double temp = Math.pow(impactor.imEnergy, 1.0 / 3.0);
		double temp2 = Math.pow(10, -6);
		impactor.fbRadius = 2 * temp2 * temp; // // impactor.fbRadius is
												// in km
		sigma = 5.67 * Math.pow(10, -8); // // Stephan-Boltzmann constant

		impactor.fbExposure = (eta * impactor.imEnergy)
				/ (2 * Math.PI * Math.pow(impactor.imDist * 1000, 2));

		h = (1 - Math.cos(delta * Math.PI / 180)) * R_earth; // h is in km,
																// R_earthis in
																// km
		del = Math.acos(h / impactor.fbRadius);
		f = (2 / Math.PI) * (del - (h / impactor.fbRadius) * Math.sin(del));

		if (h > impactor.fbRadius) {
			no_radiation = 1;
			return;
		}

		no_radiation = 0;
		impactor.fbExposure *= f;

		impactor.fbDuration = impactor.fbRadius / (impactor.imVel);
		impactor.fbPeaktime = (eta * impactor.imEnergy)
				/ (2 * Math.PI * Math.pow(impactor.fbRadius * 1000, 2) * sigma * Math
						.pow(T_star, 4));

		megaton_factor = Math.pow(impactor.imMegaton, 1 / 6);
		Log.i(LOGTAG, "Exposure " + impactor.fbExposure);

	}// =================================================================

	// ==================================================================
	/**
	 * returns the richter magnitude of the seismic disturbance caused by impact
	 */
	// ==================================================================
	public void find_magnitude() {
		double Ax;

		impactor.smRichter = 0.67 * ((Math.log(energy_seafloor)) / Math.log(10)) - 5.87;

		if (impactor.imDist >= 700) {

			Ax = 20 * Math.pow(
					10,
					(impactor.smRichter - 1.66
							* (Math.log(delta) / Math.log(10)) - 3.3));
			Ax /= 1000; // factor for determining "effective impactor.smRichter"
						// at given impactor.imDist

		}// end if
		else if (impactor.imDist >= 60)
			Ax = Math.pow(10,
					(impactor.smRichter - (0.0048 * impactor.imDist + 2.5644)));
		else
			Ax = Math
					.pow(10,
							(impactor.smRichter - (0.00238 * impactor.imDist + 1.3342)));

		impactor.smEffMag = (Math.log(Ax) / Math.log(10)) + 1.4;
		impactor.smArrival = impactor.imDist / surface_wave_v;
		Log.i(LOGTAG, "Effective mag: " + impactor.smEffMag);
	}// =================================================================

	// ==================================================================
	/**
	 * Calculates statistics about the air blast created and assigns the results
	 * to the class global impact model.
	 */
	// ==================================================================
	public void air_blast() {

		int vsound = 330; // speed of sound in m/s
		int r_cross0 = 290; // radius at which relationship between overpressure
							// and distance changes (for surface burst)
		int op_cross = 75000; // overpressure at crossover
		double r_cross;
		double z_scale;
		double r_mach;
		double energy_ktons;
		double d_scale;
		double slantRange;
		double d_smooth;
		double p_machT;
		double p_0 = 0;// =0 added by AS to stop uninitialise var error when
						// trans from action script.
		double expFactor = 0;// =0 added by AS to stop uninitialise var error
								// when trans from action script.
		double p_regT = 0;// =0 added by AS to stop uninitialise var error when
							// trans from action script.

		energy_ktons = 1000 * impactor.imMegaton; // energy in kilotons

		slantRange = Math.pow(
				Math.pow(impactor.imDist, 2)
						+ Math.pow((impactor.abAltBurst / 1000), 2), 0.5); // for
																			// air
																			// burst,
																			// distance
																			// is
																			// slant
																			// range
																			// from
																			// explosion
		impactor.abShockTime = (slantRange * 1000) / vsound; // distance in
																// meters
																// divided by
																// velocity of
																// sound in m/s

		sf = Math.pow((energy_ktons), 1 / 3);
		d_scale = (impactor.imDist * 1000) / sf;

		z_scale = impactor.abAltBurst / sf;
		// radius at which relationship between overpressure and distance
		// changes
		r_cross = r_cross0 + 0.65 * z_scale;
		r_mach = 550 * z_scale / (1.2 * (550 - z_scale));
		if (z_scale >= 550)
			r_mach = 1e30;

		if (impactor.abAltBurst > 0) {

			d_smooth = Math.pow(z_scale, 2) * 0.00328;
			p_machT = ((r_cross * op_cross) / 4) * (1 / (r_mach + d_smooth))
					* Math.pow((1 + 3 * (r_cross / (r_mach + d_smooth))), 1.3);
			p_0 = 3.1423e11 / Math.pow(z_scale, 2.6);
			expFactor = -34.87 / Math.pow(z_scale, 1.73);
			p_regT = p_0 * Math.exp(expFactor * (r_mach - d_smooth));
		}// end if
		else {
			d_smooth = 0;
			p_machT = 0;
		}// end else

		if (d_scale >= (r_mach + d_smooth))
			impactor.abOpressure = ((r_cross * op_cross) / 4) * (1 / d_scale)
					* (1 + 3 * Math.pow((r_cross / d_scale), 1.3));
		else if (d_scale <= (r_mach - d_smooth))
			impactor.abOpressure = p_0 * Math.exp(expFactor * d_scale);
		else
			impactor.abOpressure = p_regT - (d_scale - r_mach + d_smooth) * 0.5
					* (p_regT - p_machT) / d_smooth;

		impactor.abWindvel = ((5 * impactor.abOpressure) / (7 * PO))
				* (vsound / Math.pow(
						(1 + (6 * impactor.abOpressure) / (7 * PO)), 0.5));

		// //// damage descriptions: structures

		// //// sound intensity

		if (impactor.abOpressure > 0)
			impactor.abAmpl = 20 * (Math.log(impactor.abOpressure) / Math
					.log(10));
		else
			impactor.abAmpl = 0;

		Log.i(LOGTAG, "O pressure " + impactor.abOpressure);
	}// ================================================================

	// ==================================================================
	/**
	 * Calculates atmospheric data and assigns the results to the class global
	 * impact model.
	 */
	// ==================================================================
	public void print_atmosphere() {

		// Atmospheric Entry:

		double en = 0.5
				* impactor.pjMass
				* (Math.pow(impactor.pjVel * 1000, 2) - Math.pow(
						impactor.imVel * 1000, 2));
		double en_power;
		double ens_power;
		double en_mton;
		double enmton_power;

		en_mton = en / (4.186 * Math.pow(10, 15)); // joules to megatons
													// conversion

		en_power = Math.log(en) / Math.log(10);
		en_power = (int) (en_power);
		en /= Math.pow(10, en_power);

		ens_power = Math.log(impactor.imEnergy) / Math.log(10);
		ens_power = (int) (ens_power);
		impactor.imEnergy /= Math.pow(10, ens_power);

		enmton_power = Math.log(en_mton) / Math.log(10);
		enmton_power = (int) (enmton_power);
		en_mton /= Math.pow(10, enmton_power);

		megaton_power = Math.log(impactor.imMegaton) / Math.log(10);
		megaton_power = (int) (megaton_power);
		impactor.imMegaton /= Math.pow(10, megaton_power);

		Log.i(LOGTAG, "impactor pjDen " + impactor.pjDens);
		Log.i(LOGTAG, "impactor altburst " + impactor.abAltBurst);
		Log.i(LOGTAG, "iFactor " + iFactor);
		impactor.imEnergy *= Math.pow(10, ens_power);
		impactor.imMegaton *= Math.pow(10, megaton_power);

	}// ================================================================

	// ==================================================================
	/**
	 * Convets a double to standard form.
	 * 
	 * @param a
	 *            The double to convert.
	 * @return The conerted number as a String.
	 */
	// ==================================================================
	private String standform(double a) {
		double exponent = Math.floor(Math.log(Math.abs(a)) / Math.log(10));

		if (a == 0)
			exponent = 0; // handle glitch if the number is zero

		// find mantissa (e.g. "3.47" is mantissa of 3470; need to divide by
		// 1000)
		double tenToPower = Math.pow(10, exponent);
		double mantissa = a / tenToPower;

		Log.i(LOGTAG, "x " + mantissa + " y " + exponent + " a " + a);
		return nbFormat2.format(mantissa) + " x 10<sup><small>" + exponent
				+ "</sup></small>";
	} // ==========================================================================

	// ============================================================================
	/**
	 * A convenience method to enable the trace method to work which was
	 * originally an ActionScript commend. Using this method means that many log
	 * commands to not need to be rewritten.
	 */
	// ============================================================================
	private void trace(String s) {
		Log.i(LOGTAG, s);
	}// ===========================================================================

	// ============================================================================
	/**
	 * A convenience method for getting at strings of the Android framework.
	 * 
	 * @param resID
	 *            the ID of the string to be sought.
	 * @return The string that matches the resID, or an empty string if no
	 *         Android framework context exists.
	 */
	// ============================================================================
	private String getString(int resId) {
		if (ctx != null)
			return ctx.getString(resId);
		else
			return "";
	}// ============================================================================

}// ##############################################################################
