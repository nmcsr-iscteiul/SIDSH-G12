package JOAO.esii;

/** This class represents simple methods to be tested by JUnit
 * 
 * @author Frederico Correia
 * @author João Pinto
 *
 */

public class testClass {
	
	/**
	 * 
	 * @return the decimal value of the sum between 5 and 4 
	 */
	public double test1() {
		int a = 5;
		int b = 4;
		int x = a + b;
		double y = (double) x;
		return y;
		
	}
	
	/**
	 * 
	 * @return 4 element of an array of ints
	 */
	public int test2() {
		 int[] intArray = new int[]{ 1,2,3,4,5,6,7,8,9,10 };
		 return intArray[4];
		
	}
	
	/**
	 * 
	 * @return 'Hello World'
	 */
	public String test3() {
		return "Hello World";
	}
}
