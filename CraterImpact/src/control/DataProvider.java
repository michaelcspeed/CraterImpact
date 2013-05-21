package control;

import java.util.HashMap;
//###########################################################################

import ImpactModel.ImpactModel;
/**
 * Provides a link between the underlying calculation and the front end UI
 * features. All communication with the back end is done though this class.
 * It is also the class to go though when getting the results of an impact.
 * 
 * In this class are a collection of getter and setter methods which should 
 * interface with the UI. 
 * 
 */
//###########################################################################
public class DataProvider {

	static Control control;/**A local reference to the application control**/
	
	static final String LANGUAGE_UK = "uk";/**For UK locale**/
	static final String LANGUAGE_DE = "de";/**For German locale**/
	static final String LANGUAGE_FR = "fr";/**For French locale**/
	static final String LANGUAGE_PL = "pl";/**For Polish locale**/
	static final String LANGUAGE_SP = "sp";/**For Spanish locale**/
	static final String LANGUAGE_CY = "cy";/**For Welsh locale**/
	
	static String selected_language;
	
	static  double latitude;
	static  double longitude;
	
	//Input fields//
	static int cbPjDens;/**Projectile Density selected index (combo box in ui)**/
	static int cbTgDens;/**Target Density selected index (combo box in ui)**/
	static int slTgDepth;/**Selected depth when water is the target (slider in ui)**/
	static int cbSelectDepthObject;/**Not actually crater depth but selected landmark on Crater depth screen (a como box)**/
	
	static int cbLocation;/**The selected location if a default location is selected**/
	
	static double projDiam;/**Projectile Diameter**/
	static double projAngle;/**Projectile Angle**/
	static double projVel;/**Projectile velocity**/
	static double impactDist;/**Distance from Impact**/
	
	//Output Fields//
	static String txtDamage;/**Description of damage caused by seismic activity**/
	static String txtImpactor;/**Description of the impact**/
	
	//Outputs for Results Tables in key value pairs//
	static HashMap<String,String> dgInputs;/**The values input**/
	static HashMap<String,String> dgOutputs;/**The core of the calculated results**/
	static HashMap<String,String> dgEnergy;/**Energy created by impact**/
	static HashMap<String,String> dgFirevall;/**Fire ball results**/
	
	static ImpactModel impactor;/**The populated impact data model**/

	/**
	 * Sets the application controller object so the data provider can communicate to it.
	 */
	public void setController(Control ctrl )
	{
		control = ctrl;
	}
	
	/**
	 * Sets the projectile density selected index.
	 * @return An int value.
	 */
	static public int getCbPjDens() {
		return cbPjDens;
	}

	/**
	 * Sets the projectile density selected index.
	 * @param cbPjDens An int.
	 */
	static public void setCbPjDens(int cbPjDens_) {
		cbPjDens = cbPjDens_;
	}

	/**
	 * Gets the target density selected index.
	 * @return An int value.
	 */
	static public int getCbTgDens() {
		return cbTgDens;
	}

	/**
	 * Sets the target density selected index.
	 * @param cbTgDens An int value.
	 */
	static public void setCbTgDens(int cbTgDens_) {
		cbTgDens = cbTgDens_;
	}

	/**
	 * Gets the selected depth for when water is the target.
	 * @return Stored as an integer.
	 */
	static public int getSlTgDepth() {
		return slTgDepth;
	}

	/**
	 * Sets the selected depth when water is the target.
	 * @param slTgDepth The depth as an integer value.
	 */
	static public void setSlTgDepth(int slTgDepth_) {
		slTgDepth = slTgDepth_;
	}

	/**
	 * Gets the textual description of the damage caused by the impact.
	 * @return A description contained in a String.
	 */
	static public String getTxtDamage() {
		return txtDamage;
	}

	/**
	 * Sets the text describing the damage caused by the 
	 * impact.
	 * @param txtDamage A string containing the description.
	 */
	static public void setTxtDamage(String txtDamage_) {
		txtDamage = txtDamage_;
	}

	/**
	 * Gets the textual description of the impact.
	 * @return A String containing the description.
	 */
	static public String getTxtImpactor() {
		return txtImpactor;
	}

	/**
	 * Sets the textual description of the impact.
	 * @param txtImpactor The description in a String.
	 */
	static public void setTxtImpactor(String txtImpactor_) {
		txtImpactor = txtImpactor_;
	}

	/**
	 * Gets the HashMap containing the titles and values of the data 
	 * originally input by the various UI elements of the application.
	 * @return The HashMap of inputs in title, value pairs.
	 */
	static public HashMap<String, String> getDgInputs() {
		return dgInputs;
	}

	/**
	 * Sets the HashMap containing the titles and values of the data originally 
	 * input by the various UI elements of the application.
	 * @param dgInputs The HashMap of inputs in title, value pairs.
	 */
	static public void setDgInputs(HashMap<String, String> dgInputs_) {
		dgInputs = dgInputs_;
	}

	/**
	 * Gets the HashMap containint the core calculated data about the
	 * impact. The data is stored in title, values pairs.
	 * @return The HashMap containing the data.
	 */
	static public HashMap<String, String> getDgOutputs() {
		return dgOutputs;
	}

	/**
	 * Sets the results output Hashmap. The results are stored in title 
	 * value pairs.
	 * @param dgOutputs The Hashmap of the core calculated data.
	 */
	static public void setDgOutputs(HashMap<String, String> dgOutputs_) {
		dgOutputs = dgOutputs_;
	}

	/**
	 * Gets a HashMap of impact energy data. It is stored in title, value pairs.
	 * @return The HashMap containing the data.
	 */
	static public HashMap<String, String> getDgEnergy() {
		return dgEnergy;
	}

	/**
	 * Set the HashMap of impact energy data. It is stored in title value pairs.
	 * @param dgEnergy The HashMap of engergy data.
	 */
	static public void setDgEnergy(HashMap<String, String> dgEnergy_) {
		dgEnergy = dgEnergy_;
	}

	/**
	 * Gets the HashMap of the fire ball data which containtstitle value pairs.
	 * @return The HashMap of fire ball data.
	 */
	static public HashMap<String, String> getDgFirevall() {
		return dgFirevall;
	}

	/**
	 * Set the FireWall HasMap containing fireball data in title value pairs/
	 * @param dgFirevall HashMap of fireball data.
	 */
	static public void setDgFirevall(HashMap<String, String> dgFirevall_) {
		dgFirevall = dgFirevall_;
	}

	/**
	 * Get the impactor model object 
	 * @return The impactor model object.
	 */
	static public ImpactModel getImpactor() {
		return impactor;
	}

	/**
	 * Set the impactor data model object.
	 * @param impactor The impactor object.
	 */
	static public void setImpactor(ImpactModel impactor_) {
		impactor = impactor_;
	}

	/**
	 * Get the selected projectile diameter
	 * @return
	 */
	public static double getProjDiam() {
		return projDiam;
	}

	/**
	 * Set the projectile diameter
	 * @param projDiam
	 */
	public static void setProjDiam(double projDiam) {
		DataProvider.projDiam = projDiam;
	}

	/**
	 * Get the projectile angle.
	 * @return
	 */
	public static double getProjAngle() {
		return projAngle;
	}

	/**
	 * Set the projectile angle.
	 * @param projAngle
	 */
	public static void setProjAngle(double projAngle) {
		DataProvider.projAngle = projAngle;
	}

	/**
	 * Get the projectile velocity.
	 * @return
	 */
	public static double getProjVel() {
		return projVel;
	}

	/**
	 * Set the projectile velocity.
	 * @param projVel
	 */
	public static void setProjVel(double projVel) {
		DataProvider.projVel = projVel;
	}

	/**
	 * Get the distance from impact.
	 * @return
	 */
	public static double getImpactDist() {
		return impactDist;
	}

	/**
	 * Set the distance from impact.
	 * @param impactDist
	 */
	public static void setImpactDist(double impactDist) {
		DataProvider.impactDist = impactDist;
	}

	/**
	 * Get the selected depth
	 * @return
	 */
	public static int getCbSelectDepthObject() {
		return cbSelectDepthObject;
	}

	/**
	 * Set the selected depth 
	 * @param cbSelectDepthObject
	 */
	public static void setCbSelectDepthObject(int cbSelectDepthObject) {
		DataProvider.cbSelectDepthObject = cbSelectDepthObject;
	}
	
	
	/**
	 * Gets the selected location id. The id is an integer that related to the selected place.
	 * @return The selected place type as an integer.
	 */
	public static int getCbLocation() {
		return cbLocation;
	}

	
	/**
	 * Gets the he map latitude.
	 * @return A double.
	 */
	public static double getLatitude() {
		return latitude;
	}

	/**
	 * Sets the map latitude..
	 * @param latitude A double.
	 */
	public static void setLatitude(double latitude) {
		DataProvider.latitude = latitude;
	}

	/**
	 * Gets the map longitude.
	 * @return A double.
	 */
	public static double getLongitude() {
		return longitude;
	}

	/**
	 * Sets the map longitude.
	 * @param longitude A double.
	 */
	public static void setLongitude(double longitude) {
		DataProvider.longitude = longitude;
	}

	/**
	 * Sets the selected location id. The id is an integer that related to the selected place.
	 * zero indicates no selection.
	 * @param The new selected place type as an integer.
	 */
	public static void setCbLocation(int cbLocation) {
		DataProvider.cbLocation = cbLocation;
	}

	/**
	 * Though this method the back end will instruct the front end to display
	 * an alert with a title and alert text.
	 * @param desc The alert descriptive text.
	 * @param title The alert title.
	 */
	public static void displayAlert(String desc, String title)
	{
		//Michael you need to code this bit to link this with
		//what ever UI you decide to build.
	}

	/**
	 * Gets the currently selected language instance.
	 * @return
	 */
	public static String getSelected_language() {
		return selected_language;
	}

	/**
	 * Sets the selected language and also updates the locale via the
	 * contol class.
	 * @param selected_language
	 */
	public static void setSelected_language(String selected_language_) 
	{
		selected_language = selected_language_;
		control.selectLanguage(selected_language);	
	}
	
	/**
	 * Validates all input fields. If any are invalid then an error dialog is generated.
	 * If all are o.k. the crater calcs are conducted and the data values of this class 
	 * adjusted appropriately.
	 */
	public static void checkInputs()
	{
		control.checkInput();
	}
	

	
	
}//###########################################################################
