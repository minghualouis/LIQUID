package prymm.test;

import org.junit.Test;


public class GeneralTest {

	@Test
	public void testArray()
	{
		int[][] test1 = new int[5][7];
		System.out.println(test1[1].length);
	}
	
	@Test
	public void testNegativeValue()
	{
		double number = 1.2d;
		double negativeNum = -number;
		System.out.println(negativeNum < 0);
		
	}
}
