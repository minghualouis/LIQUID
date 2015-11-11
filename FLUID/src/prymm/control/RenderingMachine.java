package prymm.control;

import prymm.databean.Flow;
import prymm.databean.SingleDrop;

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
	 * calculate the 
	 */
	public void doingCalculation() {
		// TODO Auto-generated method stub
		double viscosity = currentFlow.getFlowType().getViscosity();
		SingleDrop[][] alldrops = currentFlow.getAllDrops();
		// ...used for calculation
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
