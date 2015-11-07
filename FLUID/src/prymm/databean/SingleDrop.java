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
	 * Used for checking if a single drop is a barrier or not
	 */
	private boolean isEnable;
	
	
	/**
	 * disable the drop, used for setting barrier
	 */
	public void disableDrop()
	{
		this.setDensity(0);
		this.setxVel(0);
		this.setyVel(0);
	}
	
	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	private SingleDot[][] dots = new SingleDot[3][3];

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

	public SingleDot[][] getDots() {
		return dots;
	}

	public void setDots(SingleDot[][] dots) {
		this.dots = dots;
	}
	
	
}
