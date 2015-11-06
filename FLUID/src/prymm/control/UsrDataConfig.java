package prymm.control;

/**
 * Interface for UI to call to initialize the fluid settings
 * @author Minghua
 *
 */
public class UsrDataConfig
{
	static int totalNumber = 0;
	
	private String fluidType;
	private String viscosity;
	private String temperature;
	private String barrierShape;
	private String initialForce;
	private String initialSpeed;
	private String containerSize;
	private boolean isEntryAdded;
	private boolean isExitAdded;
	
	/**
	 * Singleton pattern used here, to retrieve only a UsrDataConfig
	 * @return
	 */
	public static UsrDataConfig getUsrDataConfig()
	{
		UsrDataConfig usrdata;
		if (totalNumber == 0) 
		{
			usrdata = new UsrDataConfig();
			return usrdata;
		}
		else {
			return usrdata;
		}
	}
	
	/**
	 * Only allows user to get 
	 */
	private UsrDataConfig()
	{
		totalNumber++;
	}
	
	
	public String getFluidType() {
		return fluidType;
	}
	public void setFluidType(String fluidType) {
		this.fluidType = fluidType;
	}
	public String getViscosity() {
		return viscosity;
	}
	public void setViscosity(String viscosity) {
		this.viscosity = viscosity;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	public String getBarrierShape() {
		return barrierShape;
	}
	public void setBarrierShape(String barrierShape) {
		this.barrierShape = barrierShape;
	}
	public String getInitialForce() {
		return initialForce;
	}
	public void setInitialForce(String initialForce) {
		this.initialForce = initialForce;
	}
	public String getInitialSpeed() {
		return initialSpeed;
	}
	public void setInitialSpeed(String initialSpeed) {
		this.initialSpeed = initialSpeed;
	}
	public String getContainerSize() {
		return containerSize;
	}
	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}
	public boolean isEntryAdded() {
		return isEntryAdded;
	}
	public void setEntryAdded(boolean isEntryAdded) {
		this.isEntryAdded = isEntryAdded;
	}
	public boolean isExitAdded() {
		return isExitAdded;
	}
	public void setExitAdded(boolean isExitAdded) {
		this.isExitAdded = isExitAdded;
	}
	
	
	
}
