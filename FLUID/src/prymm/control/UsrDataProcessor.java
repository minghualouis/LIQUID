package prymm.control;

import prymm.databean.Flow;
import prymm.databean.Fluid;


/**
 * Getting all the input data from user interface and initialize the fluid
 * @author Minghua
 *
 */
public class UsrDataProcessor 
{
	/**
	 * Process user data from User configuration interface - UsrDataConfig
	 * Initialize the flow
	 */
	public static void processUsrData()
	{
		UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
		initializeFlow(usrData);
	}
	
	private static void initializeFlow(UsrDataConfig dataConfig)
	{
		//initialize the flow with user configuration
		
		int xDim = dataConfig.getLength(); // first parameter of flow
		int yDim = dataConfig.getWidth(); // second parameter of flow
		double temperature = Double.valueOf(dataConfig.getTemperature());
		// initialize the fluid with user configuration
		Fluid fluidObj = new Fluid(dataConfig.getFluidType(), temperature);
		
		// create flow with basic configuration
		Flow flow = new Flow(xDim, yDim, fluidObj);
		
	}
}
