package prymm.databean;

import prymm.model.Fluid;

/**
 * Glycerin fluid type
 * @author Minghua
 *
 */
public class Glycerin extends Fluid 
{

	public Glycerin(double temperature) 
	{
		super(temperature);
		type = "Glycerin";
		viscosity = 30*1.790*Math.exp((-1230-temperature)*temperature/(36100+360*temperature));
	}

}
