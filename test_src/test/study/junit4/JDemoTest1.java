package test.study.junit4;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
/*
 * ===Junit4学习===
 *1、超时设置 timeout
 *2、异常断言
 *3、忽略某个测试方法的执行
 *4、@BeforeClass、@AfterClass、@Before、@After、@Ignore、@Test
*/
public class JDemoTest1 {
	

	@BeforeClass
	public static void beforeClass1() {
		System.out.println("执行@BeforeClass1");
	}
	//静态初始化块始终是最先执行的。
	static{
		System.out.println(" 静态初始化块");
	}
	@BeforeClass
	public static void beforeClass2() {
		System.out.println("执行@BeforeClass2");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("执行@AfterClass");
	}

	@Before
	public void before1() {
		System.out.println("执行@before1");
	}
	@Before
	public void before2() {
		System.out.println("执行@before2");
	}

	@After
	public void after1(){
		System.out.println("执行@after1");
	}
	@After
	public void after2() {
		System.out.println("执行@after2");
	}

	@Test(timeout=1500)//设置本测试方法执行时间（单位毫秒），如果超过指定时间，则视为执行失败。
	public final void add() {
		System.out.println("执行 add");
		JDemo demo = new JDemo();
		assertEquals(6, demo.add(3, 3));
	}
	
	@Ignore("忽略本测试方法执行")
	@Test
	public final void add2() {
		System.out.println("执行 add");
		JDemo demo = new JDemo();
		assertEquals(6, demo.add(3, 3));
	}
	
	@Test(expected=ArithmeticException.class)//预期抛出ArithmeticException，如果不抛出，则视为执行失败。
	public final void division(){
		System.out.println("执行 division");
		JDemo demo = new JDemo();
		demo.division(2,0);//除数设为0，执行运算时会抛出ArithmeticException。
	}
	
	@Test(expected=ArithmeticException.class)//预期抛出ArithmeticException，如果不抛出，则视为执行失败。
	public final void division2(){
		System.out.println("执行 division");
		JDemo demo = new JDemo();
		demo.division(2,3);
	}
}


