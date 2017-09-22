package test.study.easymock;

import org.easymock.EasyMock;
import org.easymock.IAnswer;
import org.junit.Assert;
import org.junit.Test;

public class EasyMockTest4 {

	/*
	 * -----------运行时的返回值---------
	 *这里我们通过EasyMock.expect(service.execute(EasyMock.anyInt()))来接受任意值的count参数输入，
	 *andAnswer(new IAnswer<Integer>() {}) 让我们可以指定一个IAnswer的实现类来给出返回值。在这个
	 *IAnswer的实现类中，我们通过EasyMock.getCurrentArguments()[0]获取到service.execute()方法的第一个
	 *参数，然后简单的运用count*2规则给出返回值。这里的EasyMock.getCurrentArguments()方法可以获取到
	 *运行时的参数列表，不过注意这个方法对重构不够友好，如果参数列表发生变化则必须手工修改对象的获取参
	 *数的代码。
	 */
	@Test
	public void testRuntimeReturn() {
		Business2 business = new Business2();
		IService3 service = EasyMock.createMock(IService3.class);
		business.setService(service);
		EasyMock.expect(service.execute(EasyMock.anyInt())).andAnswer(
				new IAnswer<Integer>() {
					public Integer answer() throws Throwable {
						Integer count = (Integer) EasyMock.getCurrentArguments()[0];//EasyMock.getCurrentArguments()返回Object数组
						return count * 2;
					}
				});
		EasyMock.replay(service);
		business.execute();
		EasyMock.verify(service);
	}
	/*
	 * 运行时抛出异常
	 */
	@Test
	public void testRuntimeException() {
		Business2 business = new Business2();
		IService3 service = EasyMock.createMock(IService3.class);
		business.setService(service);
		EasyMock.expect(service.execute(EasyMock.anyInt())).andAnswer(
				new IAnswer<Integer>() {
					public Integer answer() throws Throwable {
						Integer count = (Integer) EasyMock.getCurrentArguments()[0];
						throw new RuntimeException("count=" + count);
					}
				});
		
		EasyMock.replay(service);
		try {
			business.execute();
			Assert.fail("business.execute()会抛出异常，我在此处不会执行...");
		} catch (RuntimeException e) {
			Assert.assertTrue(e.getMessage().indexOf("count=") != -1);
			System.out.println("e.getMessage():" + e.getMessage());
			EasyMock.verify(service);
		}
	}
}
