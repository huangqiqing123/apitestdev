package test.study.junit4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/*
 * 测试套件
 */

@RunWith(Suite.class)
@SuiteClasses({
	JDemoTest1.class,
	JDemoTest2.class
	})
public class Suite1 {

	//公开的不带有任何参数的构造函数
	public Suite1(){}
}
/*
1、创建一个空类作为测试套件的入口。 
2、使用注解 org.junit.runner.RunWith 和 org.junit.runners.Suite.SuiteClasses 修饰这个空类。 
3、将 org.junit.runners.Suite 作为参数传入注解 RunWith，以提示 JUnit 为此类使用套件运行器执行。 
4、将需要放入此测试套件的测试类组成数组作为注解 SuiteClasses 的参数。 
5、保证这个空类使用 public 修饰，而且存在公开的不带有任何参数的构造函数。 
*/
