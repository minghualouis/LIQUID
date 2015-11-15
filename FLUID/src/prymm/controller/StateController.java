package prymm.controller;

/**
 * State controller for application
 * @author Minghua
 *
 */
public class StateController {

	/**
	 * When updating the state, use below defined ones
	 */
	public static final int INITIAL = 0;
	public static final int IDLE = 1;
	public static final int RUNNING = 2;
	public static final int PAUSE = 3;
	public static final int TERMINAL = 4;
	
	private static int CURRENT = 0;
	
	public static int getCurrentState()
	{
		return CURRENT;
	}
	
	/**
	 * Used to set current state, below are the state available
	 * StateController.INITIAL
	 * StateController.IDLE
	 * StateController.RUNNING
	 * StateController.PAUSE
	 * StateController.TERMINAL
	 * @param state
	 */
	public static void setCurrentState(int state)
	{
		CURRENT = state;
	}
}
