package test.study.junit4;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Collection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/*
 * ===Junit4学习===
 *1、参数化
*/

//声明所使用的运行器，如果未声明，则默认使用默认运行器。
@RunWith(Parameterized.class)
public class JDemoTest2 {

	private int result;
	private int adddata_a;
	private int adddata_b;
	
	//参数化必须的构造方法，其中参数的顺序 对应 参数集中参数的顺序。
	public JDemoTest2(int result,int adddata_a,int adddata_b){
		
		this.adddata_a = adddata_a;
		this.adddata_b = adddata_b;
		this.result = result;		
	}
	
	@Test
	public void add(){
		JDemo demo = new JDemo();
		assertEquals(result, demo.add(adddata_a, adddata_b));
	}
	
	@SuppressWarnings("unchecked")
	@Parameters
	public static Collection getParameters(){
		
		return Arrays.asList(new Object[][]{
				{0,0,0},
				{0,1,-1},
				{2,1,1}
		});
	}
}
/*
参数化测试实例
1. 为准备使用参数化测试的测试类指定特殊的运行器 org.junit.runners.Parameterized。
2. 为测试类声明几个变量，分别用于存放期望值和测试所用数据。
3. 为测试类声明一个使用注解 org.junit.runners.Parameterized.Parameters 修饰的，返回值为 java.util.Collection 的公共静态方法，并在此方法中初始化所有需要测试的参数对。
4. 为测试类声明一个带有参数的公共构造函数，并在其中为步骤2中声明的几个变量赋值。
5. 编写测试方法，使用定义的变量作为参数进行测试。
*/
