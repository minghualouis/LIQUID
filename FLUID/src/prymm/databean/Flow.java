package prymm.databean;

/**
 * This class is created for getting inherited by actual tpe of fluid that user has chozen 
 * @author Minghua
 *
 */
class Flow 
{
	/**
	 * Container size defined by user. 
	 */
	private int xDim;
	private int ydIM;
	
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
	public Flow(int xDim, int ydIM, Fluid flowType) {
		super();
		this.xDim = xDim;
		this.ydIM = ydIM;
		this.flowType = flowType;
		this.initialFlow();
	}
	
	
	/**
	 * 
	 */
	private void initialFlow() {
		// TODO Auto-generated method stub
		
	}





	public int getxDim() {
		return xDim;
	}

	public void setxDim(int xDim) {
		this.xDim = xDim;
	}

	public int getYdIM() {
		return ydIM;
	}

	public void setYdIM(int ydIM) {
		this.ydIM = ydIM;
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
