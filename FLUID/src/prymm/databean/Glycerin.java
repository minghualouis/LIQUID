package prymm.databean;

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
		viscosity = 12100*Math.exp((-1233+temperature)*temperature/(9900+70*temperature));
	}

}
