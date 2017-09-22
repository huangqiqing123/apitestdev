package test.study.easymock;

import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class IServiceTest {

	static IService service1 = null;
	
	@BeforeClass
	public static void init(){
		service1 = EasyMock.createMock("service1",IService.class);//面向接口编程时，在未知实现类的时候，即可开展单元测试。
		EasyMock.expect(service1.getCalcResult())
		.andReturn(123).times(2)
			.andReturn(124)
				.andThrow(new RuntimeException("抛异常了..."));
		
		EasyMock.replay(service1);
	}
	
	@Test
	public void getCalcResultWithReturn123() {
		Assert.assertEquals(123, service1.getCalcResult());// 第1次调用
		Assert.assertEquals(123, service1.getCalcResult());// 第2次调用
	}
	@Test
	public void getCalcResultWithReturn124() {
		Assert.assertEquals(124, service1.getCalcResult());// 第3次调用
	}
	@Test(expected=RuntimeException.class)
	public void getCalcResultWithThrowRuntimeException() {
		service1.getCalcResult();//第4次调用
	}
	
	@AfterClass
	public static void end(){		
		EasyMock.verify(service1);
	}
}
