package test.study.junit4;

import static org.junit.Assert.*;
import org.junit.Test;

public class JDemoTest3 {

	//测试0 0的情况
	@Test	public void add1(){
		JDemo demo = new JDemo();
		assertEquals(0, demo.add(0, 0));
	}
	
	//测试正数、负数的情况
	@Test	public void add2(){
		JDemo demo = new JDemo();
		assertEquals("断言失败",10, demo.add(1, -1));
		//assertEquals(10, demo.add(1, -1));
	}
	
	//测试正数、正数的情况
	@Test	public void add3(){
		JDemo demo = new JDemo();
		assertEquals(2, demo.add(1, 1));
	}
}


