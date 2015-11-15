package prymm.databean;

import prymm.model.Fluid;

/**
 * Class Water for creating water object
 * @author Minghua
 *
 */
public class Water extends Fluid 

{

	public Water(double temperature)
	{
		super(temperature);
		type = "Water";
		viscosity = 1.790*Math.exp((-1230-temperature)*temperature/(36100+360*temperature));
	}

}
