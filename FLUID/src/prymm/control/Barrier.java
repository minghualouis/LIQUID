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

	public void addBarrier(SingleDrop[][] allDrops2)
	{
		String shape = this.getShape();
		if (shape.equals("Circular")) 
		{
			// set velocity of related single drops to zero
			int xCenter = UsrDataConfig.getUsrDataConfig().getLength() / 2;
			int yCenter = UsrDataConfig.getUsrDataConfig().getWidth() / 2;
			SingleDrop center = allDrops2[xCenter][yCenter];
			center.disableDrop();
			int radius = 3; // set radius for circular barrier
			
			
		}
		else if (shape.equals("Rectangular")) 
		{
			// set velcocity of related single drops to zero
			
		}
	}
	
	
}
