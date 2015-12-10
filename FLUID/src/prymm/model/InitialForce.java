package prymm.model;

import prymm.controller.UsrDataConfig;

public class InitialForce {

	/**
	 * Set initial force as per the direction and update all single drops within
	 * @param allDrops2
	 * @param direction
	 */
	public static void initialForce(SingleDrop[][] allDrops2, int direction)
	{
		// if user configure the water or glycerin, default speed will be used
		String speedText = UsrDataConfig.getUsrDataConfig().getInitialSpeed();
		double speed = Double.valueOf(speedText).doubleValue();
		
//		if (!UsrDataConfig.getUsrDataConfig().getFluidType().equals("User Defined")) {
//			speed = 110;
//		}
		switch (direction) 
		{
		case Flow.LEFT:
			
			for (int i = 0; i < UsrDataConfig.getUsrDataConfig().getWidth(); i++) 
			{
				allDrops2[0][i].setxVel(speed);
				
			}
//			for (int i = 1; i < UsrDataConfig.getUsrDataConfig().getLength()-2; i++) 
//			{
//				allDrops2[i][1].disableDrop();
//				allDrops2[i][UsrDataConfig.getUsrDataConfig().getWidth() - 2].disableDrop();
//			}
			break;
			
		case Flow.RIGHT:
			
			for (int i = 0; i < UsrDataConfig.getUsrDataConfig().getWidth(); i++) 
			{
				allDrops2[UsrDataConfig.getUsrDataConfig().getLength() - 1][i].setxVel(-speed);
			}
//			for (int i = 1; i < UsrDataConfig.getUsrDataConfig().getLength()-2; i++) 
//			{
//				allDrops2[i][1].disableDrop();
//				allDrops2[i][UsrDataConfig.getUsrDataConfig().getWidth() - 2].disableDrop();
//			}
//			break;

		case Flow.TOP:
			
			for (int i = 0; i < UsrDataConfig.getUsrDataConfig().getLength(); i++) 
			{
				allDrops2[i][UsrDataConfig.getUsrDataConfig().getWidth() - 1].setyVel(speed);
			}
//			for (int i = 1; i < UsrDataConfig.getUsrDataConfig().getWidth()-1; i++) 
//			{
//				allDrops2[1][i].disableDrop();
//				allDrops2[UsrDataConfig.getUsrDataConfig().getLength() - 2][i].disableDrop();
//			}
//			break;

		case Flow.BOTTOM:
			for (int i = 0; i < UsrDataConfig.getUsrDataConfig().getLength(); i++) 
			{
				allDrops2[i][0].setyVel(-speed);
			}
//			for (int i = 1; i < UsrDataConfig.getUsrDataConfig().getWidth()-1; i++) 
//			{
//				allDrops2[1][i].disableDrop();
//				allDrops2[UsrDataConfig.getUsrDataConfig().getLength() - 2][i].disableDrop();
//			}
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
		int upperSize = width * 3 / 7; // velocity of upper 40% of initial drops should be set zero
		int downSize = width * 3 / 7; // velocity of lower 40% of initial drops should be set zero
		int leftSize = length * 3 / 7; // velocity of upper 40% of initial drops should be set zero
		int rightSize = length * 4 / 7; // velocity of lower 40% of initial drops should be set zero
		switch (direction) 
		{
		case Flow.LEFT:
			// let pipe entry be 20% of the width 
			//Outer loop just to make pipe line thick and visible
			for(int j=1;j<4;j++){
				for (int i = width - 2; i >= width-upperSize; i--) 
				{
					allDrops2[j][i].disableDrop();
				}
				for (int i = downSize; i >= 1; i--) 
				{
					allDrops2[j][i].disableDrop();
				}
			}
			break;
			
		case Flow.RIGHT:
			for(int j=1;j<4;j++){
				for (int i = width - 2; i >= width -upperSize; i--) 
				{
					allDrops2[length - 1-j][i].disableDrop();
				}
				for (int i = downSize; i >= 1; i--) 
				{
					allDrops2[length - 1-j][i].disableDrop();
				}
			}
			break;

		case Flow.TOP:
			for(int j=1; j<4; j++){
				for (int i = 1; i < leftSize; i++) 
				{
					allDrops2[i][j].disableDrop();
				}
				for (int i = rightSize; i < length -2; i++) 
				{
					allDrops2[i][j].disableDrop();
				}
			}
				break;
			
		case Flow.BOTTOM:
			for(int j=1; j<4; j++){
				for (int i = 1; i < leftSize; i++) 
				{
					allDrops2[i][width - 1- j].disableDrop();
				}
				for (int i = rightSize; i < length -2; i++) 
				{
					allDrops2[i][width - 1-j].disableDrop();
				}
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
		int upperSize = width * 3 / 7; // velocity of upper 40% of initial drops should be set zero
		int downSize = width * 3 / 7; // velocity of lower 40% of initial drops should be set zero
		int leftSize = length * 3 / 7; // velocity of upper 40% of initial drops should be set zero
		int rightSize = length * 4 / 7; // velocity of lower 40% of initial drops should be set zero
		switch (direction) 
		{
		case Flow.LEFT:
			for(int j=1;j<4;j++){
				for (int i = width - 2; i >= width-upperSize; i--) 
				{
					allDrops2[j][i].disableDrop();
				}
				for (int i = downSize; i >= 1; i--) 
				{
					allDrops2[j][i].disableDrop();
				}
			}
			break;
			
		case Flow.RIGHT:
			// let pipe entry be 20% of the width 

			for(int j=1;j<4;j++){
				for (int i = width - 2; i >= width -upperSize; i--) 
				{
					allDrops2[length - 1-j][i].disableDrop();
				}
				for (int i = downSize; i >= 1; i--) 
				{
					allDrops2[length - 1-j][i].disableDrop();
				}
			}
			break;

		case Flow.TOP:
			for(int j=1; j<4; j++){
				for (int i = 1; i < leftSize; i++) 
				{
					allDrops2[i][j].disableDrop();
				}
				for (int i = rightSize; i < length -2; i++) 
				{
					allDrops2[i][j].disableDrop();
				}
			}
			break;

		case Flow.BOTTOM:
			for(int j=1; j<4; j++){
				for (int i = 1; i < leftSize; i++) 
				{
					allDrops2[i][width - 1- j].disableDrop();
				}
				for (int i = rightSize; i < length -2; i++) 
				{
					allDrops2[i][width - 1-j].disableDrop();
				}
			}
			break;
		default:
			break;
		}
	}

}
