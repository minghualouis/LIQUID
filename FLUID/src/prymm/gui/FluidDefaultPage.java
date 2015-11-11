package prymm.gui;

/**
 * Separating the UI from system
 * @author Minghua
 *
 */
public class FluidDefaultPage 
{
	/**
	 * Should be configured here
	 */
	protected static boolean isReplay = false;
	protected static String replayFileName = "";
	protected static String logFileName = "";
	
	private static FluidDefaultPage window;
	
	/**
	 * create shell and display object and all the widgets
	 */
	public void open() {
		// TODO Auto-generated method stub
		
	}
	
	public static FluidDefaultPage getCurrentPage()
	{
		if (window == null) 
		{
			return null;
		}
		return window;
	}


	public void setCurrentPage(FluidDefaultPage window2) 
	{
		window = window2;
	}
}
