package prymm.model;

import prymm.controller.UsrDataConfig;
import prymm.databean.Glycerin;
import prymm.databean.Water;

public class FluidFactory
{
	public static Fluid getFluid(String fluidType, double temperature)
	{
		if ("Water".equals(fluidType)) 
		{
			Fluid currentFluid = new Water(temperature);
			return currentFluid;
		}
		else if("Glycerin".equals(fluidType))
		{
			Fluid currentFluid = new Glycerin(temperature);
			return currentFluid;
		}
		else
		{
			// user defined fluid type
			String viscosityText = UsrDataConfig.getUsrDataConfig().getViscosity();
			Fluid currentFluid = new Fluid(viscosityText);
			return currentFluid;
		}
		
	}
}
