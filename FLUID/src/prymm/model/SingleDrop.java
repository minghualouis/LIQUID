package prymm.model;

import prymm.databean.SingleDot;

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
	 * For nine dots needed for calculating each lattice(single drop)
	 */
	private SingleDot[][] dots = new SingleDot[3][3];
	
	public SingleDrop()
	{
		this.isEnable = true;
		for (int i = 0; i < dots.length; i++)
		{
			for (int j = 0; j < dots[0].length; j++) 
			{
				dots[i][j] = new SingleDot();
			}
		}
	}
	
	/**
	 * disable the drop, used for setting barrier
	 */
	public void disableDrop()
	{
		// Default setting for single drops
		this.setDensity(1);
		this.setxVel(0);
		this.setyVel(0);
		this.setEnable(false);
	}
	
	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	

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
