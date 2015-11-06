package prymm.databean;

/**
 * This class represents single drop of liquid in the lattice
 * @author Minghua
 *
 */
public class SingleDrop
{
	/**
	 * Velocity for a single drop of liquid
	 */
	private double xVel;
	private double yVel;
	
	/**
	 * Density for the current drop
	 */
	private double density;
	
	/**
	 * Temperature of the fluid
	 * @return
	 */
	private double temperature;
	

	public double getxVel() {
		return xVel;
	}

	public void setxVel(double xVel) {
		this.xVel = xVel;
	}

	public double getyVel() {
		return yVel;
	}

	public void setyVel(double yVel) {
		this.yVel = yVel;
	}

	public double getDensity() {
		return density;
	}

	public void setDensity(double density) {
		this.density = density;
	}
	
	
}
