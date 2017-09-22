package test.study.easymock;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Ignore;
import org.junit.Test;

/*
EasyMock.createStrictMock()方法实际上内部是生成一个新的strict control，然后再创建mock对象。
Service1 service1 = EasyMock.createStrictMock("service1", Service1.class);
Service2 service2 = EasyMock.createStrictMock("service2", Service2.class);
这里实际是创建了两个strict control，而easymock是不会跨control进行顺序检测的。
在实际使用过程中，我们会有大量的场景需要检测多个mock之间的调用顺序(按说如果没有特殊要求，
一般的测试场景默认都应该如此),这种情况下就必须使用control, 而且必须是同一个strict control才能满足要求。
 */
public class EasyMockTest3 {

	@Test
	public void testWithoutStrictControlInWrongOrder() {
	Business business = new Business();
	IService1 service1 = EasyMock.createStrictMock("IService1", IService1.class);
	IService2 service2 = EasyMock.createStrictMock("IService2", IService2.class);
	business.setService1(service1);
	business.setService2(service2);
	
	//record阶段，首先调用service2的method3/method4，然后调用service1的method1/method2
	service2.method3();
	EasyMock.expectLastCall();
	service2.method4();
	EasyMock.expectLastCall();
	service1.method1();
	EasyMock.expectLastCall();
	service1.method2();
	EasyMock.expectLastCall();
	
	//回放阶段，首先调用service1的method1/method2，然后调用service2的method3/method4
	EasyMock.replay(service1, service2);
	business.executeService1And2();
	EasyMock.verify(service1, service2);
	}
	
//	@Ignore("忽略本测试方法执行")
	@Test
	public void testWithStrictControlInWrongOrder() {
		Business business = new Business();
		IMocksControl mocksControl = EasyMock.createStrictControl();
		IService1 service1 = mocksControl.createMock(IService1.class);
		IService2 service2 = mocksControl.createMock(IService2.class);
		business.setService1(service1);
		business.setService2(service2);
		
		//record阶段，首先调用service2的method3/method4，然后调用service1的method1/method2
		service2.method3();
		EasyMock.expectLastCall();
		service2.method4();
		EasyMock.expectLastCall();
		service1.method1();
		EasyMock.expectLastCall();
		service1.method2();
		EasyMock.expectLastCall();
		
		//回放阶段，首先调用service1的method1，然后调用service2的method3
		mocksControl.replay();
		business.executeService1And2();
		mocksControl.verify();
	}
}
