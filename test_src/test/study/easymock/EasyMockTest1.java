package test.study.easymock;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
//import org.loushang.bsp.security.context.GetBspInfo;
//import org.loushang.bsp.security.context.impl.BspInfoImpl;
//import org.loushang.bsp.security.data.SecurityConstants;

import test.study.junit4.JDemo;

public class EasyMockTest1 {
	
	/*
	 * 设置模拟对象期望被调用次数，如果与期望值不一致，则视为测试失败。
	 */
	@Test
	public void test1(){	
		
		//创建一个JDemo的模拟
		JDemo demo = EasyMock.createMock("Jdemo1",JDemo.class);//为mock对象命名，在当测试案例因为某个mock对象的状态或行为不符合要求而失败的时候，在异常信息里面可以输出这个mock对象的名称。
		
		//设置该模拟对象的某个方法的返回值
		EasyMock.expect(demo.getB()).andReturn(123).times(3);//不明确指定调用次数时，默认值为1		

		//将该模拟对象设置成回放状态
		EasyMock.replay(demo);
		
		//调用模拟对象
		Assert.assertEquals(123, demo.getB());//第1次调用模拟对象
		Assert.assertFalse(1231==demo.getB());//第2次调用模拟对象
		
		//执行验证，如果不执行验证，则不进行“预期调用次数”与“实际调用次数”的一致性比较。
		EasyMock.verify(demo);
	}
	/*
	 * 设置回放时抛出异常
	 */
	@Test(expected=RuntimeException.class)
	public void test2(){	
			
		JDemo demo = EasyMock.createMock(JDemo.class);//创建一个JDemo的模拟
		EasyMock.expect(demo.division(2,0)).andThrow(new RuntimeException("我是异常..."));//指定预期抛出的异常	
		EasyMock.replay(demo);//将该模拟对象设置成回放状态
		demo.division(2,0);
		EasyMock.verify(demo);
	}
	/*
	 * 设置模拟对象可被调用次数（区间）
	 */
	@Test
	public void test3(){				
		JDemo demo = EasyMock.createMock(JDemo.class);
		EasyMock.expect(demo.division(4,2)).andReturn(2).times(1,2);//设置调用次数（区间1---2）	
		EasyMock.replay(demo);
		Assert.assertEquals(2,demo.division(4,2));
//		Assert.assertEquals(2,demo.division(4,2));
//		Assert.assertEquals(2,demo.division(4,2));
		EasyMock.verify(demo);
	}
	/*
	 * 无返回值方法的调用
	 */
	@Test(expected=RuntimeException.class)
	public void test4(){				
		JDemo demo = EasyMock.createMock(JDemo.class);
		demo.setA(0);
		EasyMock.expectLastCall().andThrow(new RuntimeException("我是异常...")).times(1);
		EasyMock.replay(demo);
		demo.setA(0);
		EasyMock.verify(demo);
	}
	/*
	 * 灵活的参数配置
	 */
	@Test
	public void test5(){		
		JDemo demo = EasyMock.createMock(JDemo.class);
		EasyMock.expect(demo.division(EasyMock.anyInt(),EasyMock.anyInt())).andReturn(2);//任意整数都返回2，类似还有anyObject()、isA(String.class))、isNull()、same()、startsWith()等诸多实现	
		EasyMock.replay(demo);
		Assert.assertEquals(2,demo.division(2,2));
		EasyMock.verify(demo);
	}
	/*
	 * 测试静态方法的限制
	 */
	@Ignore("忽略本测试方法执行")
	@Test
	public void test6(){		
		JDemo demo = EasyMock.createMock(JDemo.class);
		EasyMock.expect(demo.helloStatic("黄启庆")).andReturn("Hello 黄启庆");//easymock暂时无法模拟对象的静态方法，遇到需要模拟静态方法时，可以考虑使用其他mock(jmockit)框架。	
		EasyMock.replay(demo);
		Assert.assertEquals("Hello 黄启庆",demo.helloStatic("黄启庆"));
		EasyMock.verify(demo);
	}
	/*
	 * 测试Final方法的限制
	 */
	@Ignore("忽略本测试方法执行")
	@Test
	public void test7(){		
		JDemo demo = EasyMock.createMock(JDemo.class);
		EasyMock.expect(demo.helloFinal("黄启庆")).andReturn("Hello 黄启庆");	
		EasyMock.replay(demo);
		Assert.assertEquals("Hello 黄启庆",demo.helloFinal("黄启庆"));
		EasyMock.verify(demo);
	}
	/*
	 * 使用IMocksControl批量模拟对象
	 */
	@Ignore("忽略本测试方法执行")
	@Test
	public void test10(){	

		//模拟多个mock对象
		IMocksControl control = EasyMock.createControl();
		Connection mockConnection = control.createMock(Connection.class);
		Statement mockStatement = control.createMock(Statement.class);
		ResultSet mockResultSet = control.createMock(ResultSet.class);
		control.replay();
		control.verify();
	}
	/*
	 * 测试实例
	 * BspInfo org.loushang.bsp.security.context.GetBspInfo.getBspInfo(HttpServletRequest request)
	 */
//	@Test
//	public  void testGetBspInfo(){
//		
//		BspInfoImpl bspInfo = new BspInfoImpl();//构建 bspInfo
//		bspInfo.setUserName("黄启庆");
//		
//		//模拟HttpServletRequest、HttpSession对象
//		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
//		HttpSession session = EasyMock.createMock(HttpSession.class);
//		EasyMock.expect(session.getAttribute(SecurityConstants.SECURITY_USERINFO_KEY)).andReturn(bspInfo);
//		EasyMock.expect(request.getSession()).andReturn(session);
//		EasyMock.replay(session);
//		EasyMock.replay(request);
//		
//		//执行断言
//		Assert.assertEquals("黄启庆", GetBspInfo.getBspInfo(request).getUserName());
//		EasyMock.verify(session);
//		EasyMock.verify(request);
//	}
}