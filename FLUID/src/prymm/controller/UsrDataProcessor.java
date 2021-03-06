package prymm.controller;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;

import prymm.model.Driver;
import prymm.model.Flow;
import prymm.model.Fluid;
import prymm.model.FluidFactory;
import prymm.model.LogWriter;
import prymm.model.RenderingMachine;

import java.io.*;

import prymm.model.SingleDrop;


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
	
	private static LinkedList<SingleDrop> flowMeters = new LinkedList<SingleDrop>();
	
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
			Iterator<Thread> threadIterator = threadSet.iterator();
			Thread simThread = threadIterator.next();
			Thread logThread = threadIterator.next();
			simThread.interrupt();
			logThread.interrupt();
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
		//R: :Changed state check to idle from initial
		//R: :Added terminal state as well because browse file is enabled in terminal state
		if(StateController.getCurrentState() == StateController.IDLE|| StateController.getCurrentState() == StateController.TERMINAL)
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
				    String a[]=new String[100];
				    int i = 0 ;
					ins = new FileInputStream(usrData.getReplayPath()); 
				    r = new InputStreamReader(ins, "UTF-8"); // leave charset out for default
				    br = new BufferedReader(r);
				    s = br.readLine();//R: :Added to skip first line with no config info
				    while (i<9) {//R: :Changed as need to read only configuration
				    	s = br.readLine();
				    	 String[] columns = s.split(" : ");//R: :Changed to support new log format
				    	 
				    	if(Driver.DEBUG == true){
				    		System.out.println(columns[0] + "  " + columns[1]);
				    	}
				        a[i] = columns[1];
				        i++;
				    }
				    									
				    //usrData.setViscosity(a[0]);
				    usrData.setLength(Integer.parseInt(a[0]));
				    usrData.setWidth(Integer.parseInt(a[1]));
				    usrData.setTemperature((a[2]));
				    usrData.setFluidType(a[3]);
				    usrData.setBarrierShape(a[4]);
				    usrData.setInitialSpeed(a[5]);
				    usrData.setInitialForce(a[6]);
				    if ("true".equals(a[7]))
				    usrData.setEntryAdded(true);
				    else
				    usrData.setEntryAdded(false);
				    if ("true".equals(a[8]))	
				    usrData.setExitAdded(true);
				    else
				    usrData.setExitAdded(false);
				    	
				    
				    
				    
				    // minghua added start
				    // create an int value storing # of all flowmeters read from log file
				    // create 2 int[] array1, array2, one to put the x value, another to put y value
//				    for (int j = 0; j < flowmeterNum; j++) {
//						
//				    	initialFlow.getAllDrops()[array1[j]][array2[j]].isFlowMeter = true;
//					}
				    // minghua added end
				    
				    //System.out.println("Viscosity after set is "+usrData.getViscosity());
//				    System.out.println("Length after set is "+UsrDataConfig.getUsrDataConfig().getLength());
//				    System.out.println("Width after set is "+UsrDataConfig.getUsrDataConfig().getWidth());
//				    System.out.println("Temperature after set is "+UsrDataConfig.getUsrDataConfig().getTemperature());
//				    System.out.println("Fluid after set is "+UsrDataConfig.getUsrDataConfig().getFluidType());
//				    System.out.println("Barrier after set is "+UsrDataConfig.getUsrDataConfig().getBarrierShape());
//				    System.out.println("Speed after set is "+UsrDataConfig.getUsrDataConfig().getInitialSpeed());
//				    System.out.println("force after set is "+UsrDataConfig.getUsrDataConfig().getInitialForce());
				       
					   
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
		

		UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
		int xDim = usrData.getLength(); // first parameter of flow
		int yDim = usrData.getWidth(); // second parameter of flow
		double temperature = Double.valueOf(usrData.getTemperature());
		String fluidType = usrData.getFluidType();
		String barrier = usrData.getBarrierShape() ;
		String initSpeed = usrData.getInitialSpeed();
		String initForce = usrData.getInitialForce();
	    boolean pipeEntry = usrData.isEntryAdded();
	    boolean pipeExit = usrData.isExitAdded();
		File fileName = new File(".\\log.txt");
		if(fileName.exists())
		{
			fileName.delete();
		}else
		{
			try {
				fileName.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	    PrintWriter outFile = null;
	    try {
	    	 if (!fileName.exists()) {
	    		 fileName.createNewFile();
	    		}
	        outFile = new PrintWriter( new BufferedWriter(new FileWriter(fileName,true)));
	        outFile.println("*****************************CONFIGURATION SETTINGS**************************");
	        outFile.println("x-Dimension    : " + xDim);
	        outFile.println("y-Dimension    : " + yDim);
	        outFile.println("Temperature    : " + temperature);
	        outFile.println("Fluid type     : " + fluidType);
	        outFile.println("Barrier Shape  : " + barrier);
	        outFile.println("Initial Speed  : " + initSpeed);
	        outFile.println("Initial Force  : " + initForce);
	        outFile.println("Pipe at Entry? : " + pipeEntry);
	        outFile.println("Pipe at Exit?  : " + pipeExit);
	        outFile.println("***********************BREAKPOINTS AND FLOWMETER READINGS********************");
	        outFile.println();
	        outFile.flush();
	     } catch (IOException ex) {
	        ex.printStackTrace();
	      } finally {
	        if (outFile != null) {
	           try {
	              outFile.close();
	           } catch (Exception e) {

	           }
	        }
	      }
		Thread writeLogThread = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				while(StateController.getCurrentState() == StateController.RUNNING)
				{
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						Thread.currentThread().interrupt(); // very important
				        break;
					}
					LogWriter.writeLog(currentFlow);
				}
				
			}
			
		});
		writeLogThread.setName("LogThread");
		threadSet.add(writeLogThread);
		writeLogThread.start();
		
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

	public static void addFlowMeter(double xScale, double yScale) {
		// TODO Auto-generated method stub
		
		UsrDataConfig usrData = UsrDataConfig.getUsrDataConfig();
		int xLocation = (int) (usrData.getLength() * xScale);
		int yLocation = (int) (usrData.getWidth() * yScale);
		initialFlow.getAllDrops()[xLocation][yLocation].isFlowMeter = true;
		flowMeters.add(initialFlow.getAllDrops()[xLocation][yLocation]);
	}
}
