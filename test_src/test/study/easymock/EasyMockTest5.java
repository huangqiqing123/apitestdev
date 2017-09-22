package test.study.easymock;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import test.study.junit4.JDemo;

public class EasyMockTest5 {

	/*
	 *在easymock中，对于mock对象的同一个方法，可以为每一次的调用定制不同的行为。
	 *在record阶段，easymock会精确的记录我们录入的行为，基于每一次的方法调用。
	 */
	@Test
	public void testChangeBehavior() {

		JDemo demo = EasyMock.createMock(JDemo.class);
		EasyMock.expect(demo.getB())
			.andReturn(123).times(2)
				.andReturn(124)
					.andReturn(125).times(1)
						.andThrow(new RuntimeException("抛异常了..."));
		
		EasyMock.replay(demo);

		Assert.assertEquals(123, demo.getB());// 第1次调用
		Assert.assertEquals(123, demo.getB());// 第2次调用
		Assert.assertEquals(124, demo.getB());// 第3次调用
		Assert.assertEquals(125, demo.getB());// 第4次调用
		
		try {
			demo.getB();// 第5次调用模拟对象
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		EasyMock.verify(demo);
	}

}
