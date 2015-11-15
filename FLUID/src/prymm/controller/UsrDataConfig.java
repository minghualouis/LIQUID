package prymm.controller;

/**
 * Interface for UI to call to initialize the fluid settings
 * @author Minghua
 *
 */
public class UsrDataConfig
{
	private static int totalNumber = 0;
	private static UsrDataConfig usrdata = new UsrDataConfig();
	
	private String fluidType;
	private String viscosity;
	private String temperature;
	private String barrierShape;
	private String initialForce;
	private String initialSpeed;
	private String containerSize;
	private boolean isEntryAdded;
	private boolean isExitAdded;
	private int length;
	private int width;
	
	
	
	/**
	 * Singleton pattern used here, to retrieve only a UsrDataConfig
	 * @return
	 */
	public static UsrDataConfig getUsrDataConfig()
	{
		if (totalNumber != 0) 
		{
			return usrdata;
		}
		else {
			usrdata = new UsrDataConfig();
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
	
	public void setSize()
	{
		String containerSize =  this.getContainerSize();
		String[] sizeText = containerSize.split("x");

		this.setLength(Integer.parseInt(sizeText[0].trim()));
		this.setWidth(Integer.parseInt(sizeText[1].trim()));
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
		setSize();
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

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
	
	
	
}
