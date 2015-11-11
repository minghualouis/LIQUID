package prymm.gui;

import org.eclipse.swt.widgets.Canvas;

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
	protected Canvas canvas;
	
	/**
	 * get current canvas
	 * @return
	 */
	public Canvas getCanvas()
	{
		return canvas;
	}
	
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
