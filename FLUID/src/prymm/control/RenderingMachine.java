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
	 * bug fix
	 */
	public void bugfix()
	{
		System.out.println("This is a test for cherry picking - bugfix ");
	}
}
