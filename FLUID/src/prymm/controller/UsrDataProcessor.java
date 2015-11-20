package prymm.controller;

import prymm.model.Driver;
import prymm.model.Flow;
import prymm.model.Fluid;
import prymm.model.FluidFactory;
import prymm.model.RenderingMachine;


/**
 * Getting all the input data from user interface and initialize the fluid
 * @author Minghua
 *
 */
public class UsrDataProcessor 
{
	
	private static Flow initialFlow;
	private static Fluid fluidObj;
	
	
	/**CONTORLLER USE, for UI use**/
	
	/**
	 * INITIAL -> IDLE
	 * Default initialization, initialize application resources
	 * Process user data from User configuration interface - UsrDataConfig
	 * Initialize the flow
	 */
	public static void initalFlowMain()
	{
		if(StateController.getCurrentState() == StateController.INITIAL)
		{
			UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
			initialFlow = initializeFlow(usrData);
			if (initialFlow != null) 
			{
				// set initial state first to initial indicating the process of initializing fluid
				StateController.setCurrentState(StateController.IDLE);	
			}
		}

	}
	
	/**
	 * IDLE -> RUNNING
	 * When user press run, begin execution
	 * @throws Exception
	 */
	public static void processUsrData() throws Exception
	{
		if(StateController.getCurrentState() == StateController.IDLE)
		{
			// update user configuration data first
			updateFluid();
			if (initialFlow != null) // can be later updated as general verification
			{ 
				StateController.setCurrentState(StateController.RUNNING);
				startCalculation(initialFlow);
			}
			else
			{
				throw new Exception("Flow is not properly initialized");
			}
		}
		
	}
	
	/**
	 * RUNNING -> PAUSE
	 * For pausing the current execution
	 */
	public static void pauseExecution()
	{
		if(StateController.getCurrentState() == StateController.RUNNING)
		{
			StateController.setCurrentState(StateController.PAUSE);
		}
	}
	
	/**
	 * PAUSE -> RUNNING
	 * continue execution
	 */
	public static void continueExecution()
	{
		if(StateController.getCurrentState() == StateController.PAUSE)
		{
			StateController.setCurrentState(StateController.RUNNING);
		}
	}
	
	/**
	 * RUNNING -> TERMINAL
	 * PAUSE -> TERMINAL
	 * Stop execution during running or pause state
	 */
	public static void stopExecution()
	{
		if(StateController.getCurrentState() == StateController.RUNNING || StateController.getCurrentState() == StateController.PAUSE)
		{
				StateController.setCurrentState(StateController.TERMINAL);
		}
	}
	
	
	/**
	 * TERMINAL operation
	 * Retrieve log only is available when state is TERMINAL
	 */
	public static void retrieveLog()
	{
		if(StateController.getCurrentState() == StateController.TERMINAL)
		{
			// retrieve log
		}
	}
	
	/**
	 * For replay use
	 */
	public static void replay()
	{
		if(StateController.getCurrentState() == StateController.INITIAL)
		{
			UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
			retriveForReplay(usrData);
			StateController.setCurrentState(StateController.IDLE);
		}
	}
	
	
	
	/**INTERNAL USE**/
	
	
	private static void retriveForReplay(UsrDataConfig usrData) {
		// TODO Auto-generated method stub
		
	}

	/**
	 *  Used when state is idle
	 *  When user 
	 */
	private static void updateFluid() 
	{
		UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
		int xDim = usrData.getLength(); // first parameter of flow
		int yDim = usrData.getWidth(); // second parameter of flow
		double temperature = Double.valueOf(usrData.getTemperature());
		String fluidType = usrData.getFluidType();

		fluidObj = FluidFactory.getFluid(fluidType, temperature);
		initialFlow.updateFlow(xDim, yDim, fluidObj);
	}

	private static void startCalculation(Flow currentFlow) 
	{
		// change the state first to running
		
		RenderingMachine rm = new RenderingMachine(currentFlow);
		Thread calculationThread = new Thread(rm);
		calculationThread.start();
	}

	private static Flow initializeFlow(UsrDataConfig dataConfig)
	{
		
		//initialize the flow with user configuration
		int xDim = dataConfig.getLength(); // first parameter of flow
		int yDim = dataConfig.getWidth(); // second parameter of flow
		double temperature = Double.valueOf(dataConfig.getTemperature());
		String fluidType = dataConfig.getFluidType();

		// initialize fluid using static factory pattern to give extension for user 
		fluidObj = FluidFactory.getFluid(fluidType, temperature);
		// create flow with basic configuration
		Flow flow = new Flow(xDim, yDim, fluidObj);

		return flow;
	}
}
