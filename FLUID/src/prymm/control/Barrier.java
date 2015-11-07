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
		}
		else if (shape.equals("Rectangular")) 
		{
			// set velcocity of related single drops to zero
		}
	}
	
	
}
