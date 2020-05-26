package tests;


import static org.junit.jupiter.api.Assertions.assertEquals;


import JOAO.esii.testClass;

import org.junit.jupiter.api.Test;

class testClassTest {

	
	@Test
	void testTest1() {
		testClass t = new testClass();
		double d = t.test1();
		assertEquals(9.0, d);
	}
	
	@Test
	void testTest2() {
		testClass t = new testClass();
		int i = t.test2();
		assertEquals(5, i);
	}
	
	@Test
	void testTest3() {
		testClass t = new testClass();
		String s = t.test3();
		assertEquals("Hello World", s);
	}
	
	
}
