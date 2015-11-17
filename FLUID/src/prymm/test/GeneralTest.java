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
	
	@Test
	public void testParseInt()
	{
		String number = "123 ";
		int n = Integer.parseInt(number);
		System.out.println(n);
		
	}
	
	@Test
	public void testMod() {
		// TODO Auto-generated method stub
		int[] a = new int[]{19,83, 46, 87,  5, 64, 21, 4, 60, 27, 43, 81, 96, 7 ,37, 12, 63, 18, 56, 13, 59, 86, 26, 53, 76, 92, 36, 25, 49};
	
	}
	@Test
	public void testArrayBound()
	{
		int[] a = new int[]{1,2,3};
		System.out.println(a[-1]);
		
	}
	
	@Test
	public void testDivision()
	{
		double r = 5 / 3.0;
		System.out.println(r);
	}
}
