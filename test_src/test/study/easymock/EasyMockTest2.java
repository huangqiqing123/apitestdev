package test.study.easymock;

import org.easymock.EasyMock;
import org.junit.Test;

public class EasyMockTest2 {

	/*
	 * 普通mock
	 */
	@Test
	public void testMock() {
		Business business = new Business();
		IService1 service1 = EasyMock.createMock("service1", IService1.class);// createMock
		business.setService1(service1);
		service1.method2();
		EasyMock.expectLastCall();
		service1.method1();
		EasyMock.expectLastCall();
		EasyMock.replay(service1);
		business.executeService1();
		EasyMock.verify(service1);
	}

	/*
	 * strict mock,如果实际调用顺序与模拟时指定的顺序不一致，会抛异常。
	 * 对于同一个mock对象，strict模式下多个方法之间的调用顺序在record阶段和replay阶段下是需要保持一致的
	 */
	@Test
	public void testStrictMock() {
		Business business = new Business();
		IService1 service1 = EasyMock.createStrictMock("service1",IService1.class);// createStrictMock
		business.setService1(service1);
		
		
		service1.method2();// method2
		EasyMock.expectLastCall();
		
		service1.method1();// method1
		EasyMock.expectLastCall();
		
		EasyMock.replay(service1);
		business.executeService1();
		EasyMock.verify(service1);
	}
	
	/*
	 * createNiceMock
	 */	
	@Test
	public void testNiceMock() {
		Business business = new Business();
		IService1 service1 = EasyMock.createNiceMock("service1",IService1.class);//createNiceMock
		business.setService1(service1);
		service1.method2();// method2
		EasyMock.expectLastCall();
		service1.method1();// method1
		EasyMock.expectLastCall();
		EasyMock.replay(service1);
		business.executeService1();
		EasyMock.verify(service1);
	}
	/*
	 * createNiceMock可以简写如下
	 */
	@Test
	public void testNiceMock_Simple() {
		Business business = new Business();
		IService1 service1 = EasyMock.createNiceMock("service1", IService1.class);
		business.setService1(service1);
		EasyMock.replay(service1);
		business.executeService1();
		EasyMock.verify(service1);
	}
}
