package prymm.databean;

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
	public Fluid(String fluidType)
	{
		this.type = fluidType;
		// check the type of the fluid
		if ("Water".equalsIgnoreCase(fluidType)) 
		{
			this.viscosity = ;
		}
		else if ("Glycerin".equalsIgnoreCase(fluidType)) 
		{
			this.viscosity = ;
		}
		else
		{
			// get the value configured by user for viscosity
			
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
	
	
}
