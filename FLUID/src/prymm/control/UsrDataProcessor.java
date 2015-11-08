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
		
		// get user configured data
		UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
		Flow initialFlow = initializeFlow(usrData);
		if (initialFlow != null) // can be later updated as general verification
		{ 
			startCalculation(initialFlow);
		}
	}
	
	private static void startCalculation(Flow currentFlow) 
	{
		// change the state first to running
		StateController.setCurrentState(StateController.RUNNING);
		RenderingMachine rm = new RenderingMachine(currentFlow);
		Thread calculationThread = new Thread(rm);
		calculationThread.start();		
	}

	private static Flow initializeFlow(UsrDataConfig dataConfig)
	{
		
		// set initial state first to initial indicating the process of initializing fluid
		StateController.setCurrentState(StateController.INITIAL);
		//initialize the flow with user configuration
		int xDim = dataConfig.getLength(); // first parameter of flow
		int yDim = dataConfig.getWidth(); // second parameter of flow
		double temperature = Double.valueOf(dataConfig.getTemperature());
		// initialize the fluid with user configuration
		Fluid fluidObj = new Fluid(dataConfig.getFluidType(), temperature);
		// create flow with basic configuration
		Flow flow = new Flow(xDim, yDim, fluidObj);
		return flow;
	}
}
