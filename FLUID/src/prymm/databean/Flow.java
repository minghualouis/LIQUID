package prymm.databean;

import prymm.control.Barrier;
import prymm.control.InitialForce;
import prymm.control.UsrDataConfig;

/**
 * This class is created for getting inherited by actual tpe of fluid that user has chozen 
 * @author Minghua
 *
 */
public class Flow 
{
	/**
	 * Define the direction of initial force
	 */
	public static final int LEFT = 0;
	public static final int RIGHT = 1;
	public static final int TOP = 2;
	public static final int BOTTOM = 3;
	/**
	 * Container size defined by user. 
	 */
	private int xDim;
	private int yDim;
	
	/**
	 * All the points for simulate fluid, store values of each drop
	 */
	private SingleDrop[][] allDrops = null;
	
	/**
	 * basic type of fluid
	 * Water, Glycerin are the basic type, others can be configured using userinput data - viscosity
	 */
	private Fluid flowType = null;
	
	
	/**
	 * Constructor for Flow initialization
	 * @param xDim
	 * @param ydIM
	 * @param flowType
	 */
	public Flow(int xDim, int yDim, Fluid flowType) {
		super();
		this.xDim = xDim;
		this.yDim = yDim;
		this.flowType = flowType;
		this.initialFlow();
	}
	
	
	/**
	 * initialize all the single drops
	 */
	private void initialFlow() 
	{
		int xdim = this.getxDim();
		int ydim = this.getyDim();
		allDrops = new SingleDrop[xdim][ydim];
		setInitialForce(allDrops);
	}

	/**
	 * initialize initial force based on direction of the fluid
	 * @param allDrops2
	 */
	private void setInitialForce(SingleDrop[][] allDrops2)
	{
		String initialForceDirection = UsrDataConfig.getUsrDataConfig().getInitialForce();
		switch (initialForceDirection)
		{
		case "Left":
			InitialForce.initialForce(allDrops2, Flow.LEFT);
			break;
		case "Right":
			InitialForce.initialForce(allDrops2, Flow.RIGHT);
			break;
		case "Top":
			InitialForce.initialForce(allDrops2, Flow.TOP);
			break;			
		case "Bottom":
			InitialForce.initialForce(allDrops2, Flow.BOTTOM);
			break;
		default:
			break;
		}
		// update pipe if user check pipe operation
		setInitialPipe(allDrops2);
		
		// add barrier
		addBarrier(allDrops2);
	}

	/**
	 * Add barrier to all drops by setting their velocity to zero
	 * @param allDrops2
	 */
	private void addBarrier(SingleDrop[][] allDrops2) 
	{
		String barrierShape = UsrDataConfig.getUsrDataConfig().getBarrierShape();
		Barrier barrier = new Barrier(barrierShape);
		barrier.addBarrier(allDrops2);
	}


	/**
	 * Update the pipe if user check pipe options
	 * @param allDrops2
	 */
	private boolean setInitialPipe(SingleDrop[][] allDrops2)
	{
		boolean isEntryExist = UsrDataConfig.getUsrDataConfig().isEntryAdded();
		boolean isExitExist = UsrDataConfig.getUsrDataConfig().isExitAdded();
		if (!isExitExist && !isEntryExist) 
		{
			return false;
		}
		if (isEntryExist) 
		{
			int width = UsrDataConfig.getUsrDataConfig().getWidth();
			int length = UsrDataConfig.getUsrDataConfig().getLength();
			String direction = UsrDataConfig.getUsrDataConfig().getInitialForce();
			switch (direction)
			{
			case "Left":
				InitialForce.updatePipeEntry(width, length, allDrops2, Flow.LEFT);
				break;
			case "Right":
				InitialForce.updatePipeEntry(width, length, allDrops2, Flow.RIGHT);
				break;
			case "Top":
				InitialForce.updatePipeEntry(width, length, allDrops2, Flow.TOP);
				break;			
			case "Bottom":
				InitialForce.updatePipeEntry(width, length, allDrops2, Flow.BOTTOM);
				break;
			default:
				break;
			}
			
		}
		if (isExitExist) 
		{
			int width = UsrDataConfig.getUsrDataConfig().getWidth();
			int length = UsrDataConfig.getUsrDataConfig().getLength();
			String direction = UsrDataConfig.getUsrDataConfig().getInitialForce();
			switch (direction)
			{
			case "Left":
				InitialForce.updatePipeExit(width, length, allDrops2, Flow.RIGHT);
				break;
			case "Right":
				InitialForce.updatePipeExit(width, length, allDrops2, Flow.LEFT);
				break;
			case "Top":
				InitialForce.updatePipeExit(width, length, allDrops2, Flow.BOTTOM);
				break;			
			case "Bottom":
				InitialForce.updatePipeExit(width, length, allDrops2, Flow.TOP);
				break;
			default:
				break;
			}
		}
		return true;
	}


	public int getxDim() {
		return xDim;
	}

	public void setxDim(int xDim) {
		this.xDim = xDim;
	}

	public int getyDim() {
		return yDim;
	}

	public void setyDim(int yDim) {
		this.yDim = yDim;
	}

	public SingleDrop[][] getAllDrops() {
		return allDrops;
	}

	public void setAllDrops(SingleDrop[][] allDrops) {
		this.allDrops = allDrops;
	}

	public Fluid getFlowType() {
		return flowType;
	}

	public void setFlowType(Fluid flowType) {
		this.flowType = flowType;
	}
	
	
	
}
