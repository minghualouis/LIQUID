package prymm.model;

import prymm.controller.UsrDataConfig;

/**
 * Barrier class for setting range for all drops as per the shape of the barrier
 * @author Minghua
 *
 */
public class Barrier
{
	private String shape;

	public Barrier(String shape) {
		super();
		this.shape = shape;
	}

	public String getShape() {
		return shape;
	}

	public void setShape(String shape) {
		this.shape = shape;
	}

	/**
	 * set initial barrier for all drops, make drops in barrier area disabled
	 * By default, radius equals to 4 if shape is configured to circular;  side length equals to 8 if shape is configured to rectangular
	 * @param allDrops2
	 */
	public void addBarrier(SingleDrop[][] allDrops2)
	{
		String shape = this.getShape();
		int xCenter = UsrDataConfig.getUsrDataConfig().getLength() / 2;
		int yCenter = UsrDataConfig.getUsrDataConfig().getWidth() / 2;
		SingleDrop center = allDrops2[xCenter][yCenter];
		center.disableDrop();
		if (shape.equals("Circular")) 
		{
			int radius = 20;
			// set velocity of related single drops to zero, radius is set to 4 by default
			// disable drops of a circular shape
			for(int j=1; j<= radius; j++)
			{	
				for (int i = 0; i <= 360; i++) 
				{
					int x = xCenter+(int) Math.round((radius-j) * Math.cos(i));
					int y = yCenter+(int) Math.round((radius-j) * Math.sin(i));
					allDrops2[x][y].disableDrop();
				}
			}
		}
		else if (shape.equals("Rectangular")) 
		{
			// set velcocity of related single drops to zero
			// disable drops of a rectangular shape
			int sideLength = 20;
			for (int i = -sideLength; i <= sideLength; i++) 
			{
				for (int j = -sideLength; j <= sideLength; j++) 
				{
					allDrops2[xCenter + i][yCenter + j].disableDrop();
				}
			}
		}
		else if (shape.equals("InnerPipe")) 
		{
			// set velcocity of related single drops to zero
			// disable drops of a rectangular shape
			for(int i = xCenter - 40; i <= xCenter + 40; i ++)
			{
				allDrops2[i][yCenter + 30].disableDrop();
				allDrops2[i][yCenter - 30].disableDrop();
			}
			for(int j = 0; j < 25; j ++)
			{
				allDrops2[xCenter - 40][yCenter - 30 + j].disableDrop();
				allDrops2[xCenter - 40][yCenter + 30 - j].disableDrop();
			}
			
		}
	}
	
	
}
