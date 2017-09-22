package test.study.junit4;

import org.junit.Assert;
import org.junit.Test;

public class TestAssert {

	@Test
	public void test1(){
		
		boolean b1 = 1<2;
		Assert.assertTrue("断言成功", b1);
	}
	@Test
	public void test2(){
		
		boolean b1 = 1>2;
		Assert.assertTrue("断言失败", b1);
	}
	@Test
	public void test3(){
		
		Assert.fail("失败了");
	}
	@Test
	public void test4(){		
		Assert.assertEquals("张三", "李四");
	}
	@Test
	public void test5(){		
		Assert.assertNull("我不是null");
	}
}
