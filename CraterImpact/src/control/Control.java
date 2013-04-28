package control;

import java.util.Locale;

import android.content.Context;
import android.content.res.Configuration;
import Calcs.CraterCalcs;
import ImpactModel.ImpactModel;


public class Control {
	
	/**The impact model which holds the data model for the impact calculations**/
	 public ImpactModel  impactor = new ImpactModel();
	 

	public static int checkHelp = 0;
	
	public CraterCalcs craterCalcs;
	public Context ctx;/**The Android context**/
	
	private static Control controlSingleton;

	//===================================================================================================
	/**
	 * Gets a singleton instance of this class.
	 */
	//===================================================================================================
	public static Control getInstance() 
	{
		if (controlSingleton == null)
			controlSingleton = new Control(null);
		
		return controlSingleton;
	}//==================================================================================================
	
	
	//===================================================================================================
	/**
	 * Gets a singleton instance of this class.
	 */
	//===================================================================================================
	public static Control getInstance(Context ctx) 
	{
		if (controlSingleton == null)
			controlSingleton = new Control(ctx);
		
		return controlSingleton;
	}//==================================================================================================
	
	//===================================================================================================
	/**
	 * A constructor fort the control class.
	 * @param The application context for getting at strings and locale etc.
	 */
	//===================================================================================================
	private  Control(Context ctx_)
	{			
		ctx = ctx_;
		craterCalcs = new CraterCalcs(ctx);

		//Default location nr Cardiff
		DataProvider.setLatitude(51.481300);
		DataProvider.setLongitude(-3.180500);
		
		//Default language UK
		DataProvider.setSelected_language(DataProvider.LANGUAGE_UK);
		    
		resetInputs();//Reset all.

	}//===================================================================================================

	
	//===================================================================================================
	/**
	 * Set the language for the application by reconfiguring the locale so that Android gets other strings.
	 * @param languageType The language type as a String containing a two letter pair e.g. uk, cy, gr etc.
	 */
	//===================================================================================================
	public void selectLanguage(String languageType)
	{
		//Should update Android resources to new Locale
		Locale locale = new Locale(languageType);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		ctx.getResources().updateConfiguration(config, ctx.getResources().getDisplayMetrics());	
	}//===================================================================================================

	
	//===================================================================================================
	/**
	 * Resets the input values in the data provider and subsiquently the UI.
	 */
	//===================================================================================================
	private void  resetInputs()
	{
		impactor.reset();
		
		DataProvider.setProjDiam(0);
		DataProvider.setProjAngle(0);
		DataProvider.setProjVel(0);
		DataProvider.setCbPjDens(0);
		DataProvider.setCbTgDens(0);
		DataProvider.setCbSelectDepthObject(0);
		DataProvider.setImpactDist(0);
		DataProvider.setCbLocation(0);
		
	   
		
		//map.clearOverlays();//Not needed in Android version may be needed in HTML5 version.
		
		//Reset UI values NOT Used in Androiv Version as all is hooked up via Data Provider
		//MAY BE USEFULL IN HTML5 version
		/*
		flashLC.send("icImpactAngle","rotateArrow", "1");
		txAngle.text = "0º";
		flashLC.send("icImpactSpeed","rotateArrow", "1");
		txSpeed.text = "0";
		flashLC.send("icImpactSize","changeSize", "1");
		txSize.text = "0m";
		flashLC.send("icProjDensity","changeFrame", "1");
		flashLC.send("icTargetDensity","changeFrame", "1");
		flashLC.send("icCraterDepth","changeObject", "1", "0");
		*/
		//cbSelectDepthObject.selectedIndex = 0;
		
	}//===================================================================================================

	
	
	//===================================================================================================
	/**
	 * Check the input values and if o.k. do calculations.
	 */
	//===================================================================================================
	public void checkInput()
	{
		String message = "";
		if (DataProvider.getCbTgDens()  == 0){
			message += "Enter target density\n";
		}
		if (DataProvider.getCbPjDens()  == 0){
			message += "Enter projectile density\n";
		}
		if (impactor.pjDiam < 100){
			message += "Enter projectile diameter\n";
		}
		if (impactor.pjAngle == 0){
			message += "Enter trajectory angle\n";
		}
		if (impactor.pjVel == 0){
			message += "Enter projectile velocity\n";
		}
		if (message.length() == 0){
			craterCalcs.getData();
		}else
		{
			DataProvider.displayAlert(message, "more details required");
		}//end if
	}//===================================================================================================

	

}//######################################################################################################
