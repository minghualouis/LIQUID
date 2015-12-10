package prymm.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashSet;

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
	
	private static HashSet<Thread> threadSet = new HashSet<Thread>();
	
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
		if(StateController.getCurrentState() == StateController.IDLE || StateController.getCurrentState() == StateController.TERMINAL)
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
			Thread simThread = threadSet.iterator().next();
			simThread.interrupt();
			threadSet.removeAll(threadSet);
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
		//Radhika:Changed state check to idle from initial
		if(StateController.getCurrentState() == StateController.IDLE)
		{
			UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
			retriveForReplay(usrData);
			StateController.setCurrentState(StateController.IDLE);
		}		
	}
	
	
	
	/**INTERNAL USE**/
	
	
	private static void retriveForReplay(UsrDataConfig usrData) {
		//R: : Added for parsing log file & set configuration 
				InputStream ins = null; // raw byte-stream
				Reader r = null; 

				BufferedReader br = null; // buffered for readLine()
				try {
				    String s;
				    String a[]=new String[10];
				    int i = 0 ;
					//ins = new FileInputStream("C:\\Users\\parth\\Desktop\\radhika.txt");
				    ins = new FileInputStream(usrData.getReplayPath()); 
				    r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
				    br = new BufferedReader(r);
				    
				    while ((s = br.readLine()) != null) {
				    	 String[] columns = s.split(" ");
				    	 
				    	if(Driver.DEBUG == true){
				    		System.out.println(columns[0] + "  " + columns[1]);
				    	}
				        a[i] = columns[1];
				        i++;
				    }
									
				    usrData.setViscosity(a[0]);
				    usrData.setLength(Integer.parseInt(a[1]));
				    usrData.setWidth(Integer.parseInt(a[2]));
				    usrData.setBarrierShape(a[3]);
				    //usrData.setContainerSize(a[4]);
				    //usrData.setFluidType(a[5]);
				    usrData.setInitialForce(a[6]);
				    usrData.setInitialSpeed(a[7]);
				    usrData.setTemperature(a[8]);

				    //System.out.println("Viscosity after set is "+usrData.getViscosity());
				   // System.out.println("Length after set is "+UsrDataConfig.getUsrDataConfig().getLength());
				    //System.out.println("Width after set is "+UsrDataConfig.getUsrDataConfig().getWidth());
					
					   
					   
				}
				catch (Exception e)
				{
				    System.err.println(e.getMessage()); // handle exception
				}
				finally {
				    if (br != null) { try { br.close(); } catch(Throwable t) { /* ensure close happens */ } }
				    if (r != null) { try { r.close(); } catch(Throwable t) { /* ensure close happens */ } }
				    if (ins != null) { try { ins.close(); } catch(Throwable t) { /* ensure close happens */ } }
				}
				
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
		calculationThread.setName("renderingMachine");
		threadSet.add(calculationThread);
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
