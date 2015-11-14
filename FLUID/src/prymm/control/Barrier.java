package prymm.control;

import prymm.databean.SingleDrop;

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
		System.out.println(allDrops2.length + "------" + allDrops2[0].length);
		SingleDrop center = allDrops2[xCenter][yCenter];
		center.disableDrop();
		if (shape.equals("Circular")) 
		{
			int radius = 4;
			// set velocity of related single drops to zero, radius is set to 4 by default
			// disable drops of a circular shape
			for (int i = -radius + 1; i <= radius - 1; i++) 
			{
				for (int j = -radius + 1; j <= radius - 1; j++) 
				{
					allDrops2[xCenter + i][yCenter + j].disableDrop();
				}
			}
			allDrops2[xCenter + radius][yCenter].disableDrop();
			allDrops2[xCenter - radius][yCenter].disableDrop();
			allDrops2[xCenter][yCenter + radius].disableDrop();
			allDrops2[xCenter][yCenter - radius].disableDrop();
		}
		else if (shape.equals("Rectangular")) 
		{
			// set velcocity of related single drops to zero
			// disable drops of a rectangular shape
			int sideLength = 4;
			for (int i = -sideLength; i <= sideLength; i++) 
			{
				for (int j = -sideLength; j <= sideLength; j++) 
				{
					allDrops2[xCenter + i][yCenter + j].disableDrop();
				}
			}
		}
	}
	
	
}
