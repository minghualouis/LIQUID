package prymm.control;

import org.eclipse.swt.widgets.Canvas;

import prymm.databean.Flow;
import prymm.databean.SingleDrop;
import prymm.gui.FluidDefaultPage;

/**
 * Class rendering machine is used for calculation of all the single drops of flow in the application
 * @author Minghua
 *
 */
public class RenderingMachine implements Runnable{

	Flow currentFlow = null;
	
	public RenderingMachine(Flow initialFlow) {
		// TODO Auto-generated constructor stub
		currentFlow = initialFlow;
	}

	/**
	 * calculate all the drops in canvas
	 */
	private void doingCalculation() {
		// For fluid calculation
		double viscosity = currentFlow.getFlowType().getViscosity();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		double[][] density = new double[alldrops.length][alldrops[0].length]; // storing result
		
		// ...used for calculation
		renderingCanvas(density);
	}

	/**
	 * Display calculated density back to UI
	 */
	private void renderingCanvas(double[][] density)
	{
		// canvas which need for rendering result to UI
		Canvas currentCanvas = FluidDefaultPage.getCurrentPage().getCanvas();

	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.doingCalculation();
	}
	
	/**
	 * Cherry picking test
	 * feature1
	 */
	public void feature1()
	{
		System.out.println("This is a test for cherry picking - feature1 ");
	}

	/**
	 * Cherry picking test
	 * bug fix
	 */
	public void bugfix()
	{
		System.out.println("This is a test for cherry picking - bugfix ");
	}
}
