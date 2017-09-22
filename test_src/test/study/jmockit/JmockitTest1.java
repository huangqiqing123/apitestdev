package test.study.jmockit;

import junit.framework.Assert;
import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;

import org.junit.Test;

/**
 * 最简单的Mock示例；我们要在需要mock的类加上注解@Mocked或者@NonStrict；
 * 录制Expectations可有可没有。没有录制时，被mock的方法只会返回一个默认的值。
 * 验证Verifications可以有，也可以没有。
 */
public class JmockitTest1 {
	

	@Test
	public void test_non_final_static_Method() {
		
		MockUp<Tool> mockup = new MockUp<Tool>(){
			
			@Mock  
			public String non_final_static_Method() {   
				
				return "hello";		
			}

		};
		System.out.println(new Tool().non_final_static_Method());
	}
	

}