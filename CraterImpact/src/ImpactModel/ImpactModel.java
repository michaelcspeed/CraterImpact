package ImpactModel;

//###############################################################################
/**
 * This class holds the core data model for the crater impact application holding
 * data for target, projectile, calculated cater values, crater dimensions, 
 * fireball, Ejecta, Air Blast, Seismic activity and impact.
 * 
 * @author asscott
 * @version v1.0
 */
//###############################################################################
public class ImpactModel 
{
			// Target
			public double tgDens;
			public double tgDepth;
			public double tgDist;
			public double tgType;

			// Projectile
			public double pjDens;
			public double pjVel;
			public double pjAngle;
			public double pjDiam;
			public double pjMass;
			public double pjEnergy;
			
			// ---- Output variables
			// Crater calculated values
			public double crHeight;
			public double crDiam;
			public double crDepth;
			public double crMass;
			public double crWatermass;
			public double crVol;
			public double crVolMelt;
			public double crBrecThick;
			
			// Transient crater dimensions
			public double crTsDiam;
			public double crTsDepth;
			
			// Fireball 
			public double fbRadius;
			public double fbDuration;
			public double fbExposure;
			public double fbFlux;
			public double fbPeaktime;
			public double fbEnergy;
			// Ejecta
			public double ejFalltime;
			public double ejThickness;
			public double ejFragsize;
			// Air blast
			public double abAmpl;
			public double abWindvel;
			public double abOpressure;
			public String abDesc;
			public double abShockTime;
			public double abAltBreak;
			public double abAltBurst;
			// Seismic 
			public double smRichter;
			public double smArrival;
			public double smEffMag;
			public String smDesc;
			// Impact
			public double imEnergy;
			public double imMegaton;
			public double imFreq;
			public String imDesc;
			public double imDist;
			public double imVel;
			
			//==================================================================
			/**
			 * Constructor for the impact model which sets all values to zero.
			 */
			//==================================================================
			public  ImpactModel() 
			{
				reset();
			}//=================================================================
			
			//==================================================================
			/**
			 * Resets all the model values to zero
			 */
			//==================================================================
			public void reset()
			{
				//target
				 tgDens=0;
				 tgDepth = 0;
				 tgDist = 0;
				 tgType = 0;
				// Projectile
				 pjDens = 0;
				 pjVel = 0;
				 pjAngle = 0;
				 pjDiam = 0;
				 pjMass = 0;
				 pjEnergy = 0;
				// ---- Output variables
				// Crater calculated values
				 crHeight = 0;
				 crDiam = 0;
				 crDepth = 0;
				 crMass = 0;
				 crWatermass = 0;
				 crVol = 0;
				 crVolMelt = 0;
				 crBrecThick = 0;
				 // Transient crater dimensions
				 crTsDiam = 0;
				 crTsDepth = 0;
				// Fireball 
				 fbRadius = 0;
				 fbDuration = 0;
				 fbExposure = 0;
				 fbFlux = 0;
				 fbPeaktime = 0;
				 fbEnergy = 0;
				// Ejecta
				 ejFalltime = 0;
				 ejThickness = 0;
				 ejFragsize = 0;
				// Air blast
				 abAmpl = 0;
				 abWindvel = 0;
				 abOpressure = 0;
				 abDesc = "";
				 abShockTime = 0;
				 abAltBreak = 0;
				 abAltBurst = 0;
				// Seismic 
				 smRichter = 0;
				 smArrival = 0;
				 smEffMag = 0;
				 smDesc = "";
				// Impact
				 imEnergy = 0;
				 imMegaton = 0;
				 imFreq = 0;
				 imDesc = "";
				 imDist = 0;
				 imVel = 0;
			}//=================================================================
	
}//##############################################################################
