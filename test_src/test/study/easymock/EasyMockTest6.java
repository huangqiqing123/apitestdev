package test.study.easymock;

import org.easymock.EasyMock;
import org.junit.Test;

/*
 * easymock中提供对于类的mock功能，我们可以方便的mock这个类的某些方法，指定预期的行为以便测试
 *这个类的调用者。这种场景下被mock的类在测试案例中扮演的是次要测试对象或者说依赖的角色，主要测试对
 *象是这个mock类的调用者。但是有时候我们需要将这个测试类作为主要测试对象，我们希望这个类中的部分
 *(通常是大部分)方法保持原有的正常行为，只有个别方法被我们mock掉以便测试。
 */
public class EasyMockTest6 {

	@Test
	public void testPartialMock() {
		Service service = EasyMock.createMockBuilder(Service.class).addMockedMethod("needMockMethod").createMock();
		service.needMockMethod();
		EasyMock.expectLastCall();
		EasyMock.replay(service);
		service.execute();
		EasyMock.verify(service);
	}
}

class Service {
	public void execute() {
		actualMethod();
		needMockMethod();
	}

	void actualMethod() {
		System.out.println("call actualMethod()");
	}

	public void needMockMethod() {
		System.out.println("call needMockMethod()");
	}
}