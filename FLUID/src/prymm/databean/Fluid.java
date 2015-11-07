package prymm.databean;

import prymm.control.UsrDataConfig;

/**
 * Fluid initialization including type, viscosity
 * @author Minghua
 *
 */
public class Fluid {

	/**
	 * Determine the viscosity of the chozen fluid
	 * Can be changed by type of the fluid chozen by user
	 * Or by viscosity value set by user
	 */
	private double viscosity;
	
	/**
	 * Type of the fluid
	 */
	private String type;
	
	/**
	 * Constructor by Fluid type 
	 */
	private double temperature;
	
	public Fluid(String fluidType, double temperature)
	{
		this.type = fluidType;
		this.temperature = temperature;
		// check the type of the fluid
		if ("Water".equalsIgnoreCase(fluidType)) 
		{
			this.viscosity = 1.790*Math.exp((-1230-temperature)*temperature/(36100+360*temperature));
		}
		else if ("Glycerin".equalsIgnoreCase(fluidType)) 
		{
			this.viscosity = 12100*Math.exp((-1233+temperature)*temperature/(9900+70*temperature)); ;
		}
		else
		{
			// get the value configured by user for viscosity
			String viscosityText = UsrDataConfig.getUsrDataConfig().getViscosity();
			this.viscosity = Double.valueOf(viscosityText);
		}
	}
	
	public double getViscosity() {
		return viscosity;
	}
	public void setViscosity(double viscosity) {
		this.viscosity = viscosity;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public double getTemperature() {
		return temperature;
	}

	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	
	
}
