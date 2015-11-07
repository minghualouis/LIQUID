package prymm.control;

import prymm.databean.Flow;
import prymm.databean.SingleDrop;

public class InitialForce {

	public static void initialForce(SingleDrop[][] allDrops2, int direction)
	{
		// if user configure the water or glycerin, default speed will be used
		String speedText = UsrDataConfig.getUsrDataConfig().getInitialSpeed();
		double speed = Double.valueOf(speedText);
//		if (!UsrDataConfig.getUsrDataConfig().getFluidType().equals("User Defined")) {
//			speed = 110;
//		}
		switch (direction) 
		{
		case Flow.LEFT:
			for (int i = 0; i < allDrops2.length; i++) 
			{
				allDrops2[i][0].setxVel(speed);
				allDrops2[i][0].setyVel(0);
				allDrops2[i][0].setDensity(1);
			}
			break;
			
		case Flow.RIGHT:
			int lastCol = allDrops2[0].length - 1;
			for (int i = 0; i < allDrops2.length; i++) 
			{
				allDrops2[i][lastCol].setxVel(-speed);
				allDrops2[i][lastCol].setyVel(0);
				allDrops2[i][lastCol].setDensity(1);
			}
			break;

		case Flow.TOP:
			for (int i = 0; i < allDrops2[0].length; i++) 
			{
				allDrops2[0][i].setxVel(0);
				allDrops2[0][i].setyVel(speed);
				allDrops2[0][i].setDensity(1);
			}
			break;

		case Flow.BOTTOM:
			int lastRow = allDrops2.length - 1;
			for (int i = 0; i < allDrops2.length; i++) 
			{
				allDrops2[lastRow][i].setxVel(0);
				allDrops2[lastRow][i].setyVel(-speed);
				allDrops2[lastRow][i].setDensity(1);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * update all drops by adding pipe entry
	 * @param width
	 * @param length
	 * @param allDrops2
	 * @param direction
	 */
	public static void updatePipeEntry(int width, int length,
			SingleDrop[][] allDrops2, int direction) 
	{
		int upperSize = width * 2 / 5; // velocity of upper 40% of initial drops should be set zero
		int downSize = width * 3 / 5; // velocity of lower 40% of initial drops should be set zero
		int leftSize = length * 2 / 5; // velocity of upper 40% of initial drops should be set zero
		int rightSize = length * 3 / 5; // velocity of lower 40% of initial drops should be set zero
		switch (direction) 
		{
		case Flow.LEFT:
			// let pipe entry be 20% of the width 

			for (int i = 0; i < upperSize; i++) 
			{
				allDrops2[i][0].setxVel(0);
				allDrops2[i][0].setyVel(0);
				allDrops2[i][0].setDensity(0);
			}
			for (int i = downSize; i < allDrops2.length; i++) 
			{
				allDrops2[i][0].setxVel(0);
				allDrops2[i][0].setyVel(0);
				allDrops2[i][0].setDensity(0);
			}
			break;
			
		case Flow.RIGHT:
			int lastCol = allDrops2[0].length - 1;
			for (int i = 0; i < upperSize; i++) 
			{
				allDrops2[i][lastCol].setxVel(0);
				allDrops2[i][lastCol].setyVel(0);
				allDrops2[i][lastCol].setDensity(0);
			}
			for (int i = downSize; i < allDrops2.length; i++) 
			{
				allDrops2[i][lastCol].setxVel(0);
				allDrops2[i][lastCol].setyVel(0);
				allDrops2[i][lastCol].setDensity(0);
			}
			break;

		case Flow.TOP:
			for (int i = 0; i < leftSize; i++) 
			{
				allDrops2[0][i].setxVel(0);
				allDrops2[0][i].setyVel(0);
				allDrops2[0][i].setDensity(0);
			}
			for (int i = rightSize; i < allDrops2[0].length; i++) 
			{
				allDrops2[0][i].setxVel(0);
				allDrops2[0][i].setyVel(0);
				allDrops2[0][i].setDensity(0);
			}
			break;

		case Flow.BOTTOM:
			int lastRow = allDrops2.length - 1;
			for (int i = 0; i < leftSize; i++) 
			{
				allDrops2[lastRow][i].setxVel(0);
				allDrops2[lastRow][i].setyVel(0);
				allDrops2[lastRow][i].setDensity(0);
			}
			for (int i = rightSize; i < allDrops2[0].length; i++) 
			{
				allDrops2[lastRow][i].setxVel(0);
				allDrops2[lastRow][i].setyVel(0);
				allDrops2[lastRow][i].setDensity(0);
			}
			break;
		default:
			break;
		}
	}

	/**
	 * update all drops by adding pipe exit
	 * @param width
	 * @param length
	 * @param allDrops2
	 * @param direction
	 */
	public static void updatePipeExit(int width, int length,
			SingleDrop[][] allDrops2, int direction) 
	{
		int upperSize = width * 2 / 5; // velocity of upper 40% of initial drops should be set zero
		int downSize = width * 3 / 5; // velocity of lower 40% of initial drops should be set zero
		int leftSize = length * 2 / 5; // velocity of upper 40% of initial drops should be set zero
		int rightSize = length * 3 / 5; // velocity of lower 40% of initial drops should be set zero
		switch (direction) 
		{
		case Flow.LEFT:
			int lastCol = allDrops2[0].length - 1;
			for (int i = 0; i < upperSize; i++) 
			{
				allDrops2[i][lastCol].setxVel(0);
				allDrops2[i][lastCol].setyVel(0);
				allDrops2[i][lastCol].setDensity(0);
			}
			for (int i = downSize; i < allDrops2.length; i++) 
			{
				allDrops2[i][lastCol].setxVel(0);
				allDrops2[i][lastCol].setyVel(0);
				allDrops2[i][lastCol].setDensity(0);
			}
			break;
			
		case Flow.RIGHT:
			// let pipe entry be 20% of the width 

			for (int i = 0; i < upperSize; i++) 
			{
				allDrops2[i][0].setxVel(0);
				allDrops2[i][0].setyVel(0);
				allDrops2[i][0].setDensity(0);
			}
			for (int i = downSize; i < allDrops2.length; i++) 
			{
				allDrops2[i][0].setxVel(0);
				allDrops2[i][0].setyVel(0);
				allDrops2[i][0].setDensity(0);
			}
			break;

		case Flow.TOP:
			int lastRow = allDrops2.length - 1;
			for (int i = 0; i < leftSize; i++) 
			{
				allDrops2[lastRow][i].setxVel(0);
				allDrops2[lastRow][i].setyVel(0);
				allDrops2[lastRow][i].setDensity(0);
			}
			for (int i = rightSize; i < allDrops2[0].length; i++) 
			{
				allDrops2[lastRow][i].setxVel(0);
				allDrops2[lastRow][i].setyVel(0);
				allDrops2[lastRow][i].setDensity(0);
			}
			break;

		case Flow.BOTTOM:
			for (int i = 0; i < leftSize; i++) 
			{
				allDrops2[0][i].setxVel(0);
				allDrops2[0][i].setyVel(0);
				allDrops2[0][i].setDensity(0);
			}
			for (int i = rightSize; i < allDrops2[0].length; i++) 
			{
				allDrops2[0][i].setxVel(0);
				allDrops2[0][i].setyVel(0);
				allDrops2[0][i].setDensity(0);
			}
			break;
		default:
			break;
		}
	}

}
