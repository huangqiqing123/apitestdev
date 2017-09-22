package test.study.junit4;

public class JDemo{

	private int a;
	private int b;
	int result;
	
	/*
	 * 执行加法运算
	 */
	public int add(int a,int b){
		try {
			System.out.println("开始计算，请耐心等待。。。");
			Thread.sleep(1000);
			result=a+b;
			Thread.sleep(1000);
			System.out.println("计算完毕，结果="+result);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}
	/*
	 * 执行除法运算
	 */
	public int division(int a,int b){
		
		return result=a/b;
	}
	/*
	 * 静态方法，用于测试easymock的限制
	 */
	public static String helloStatic(String who){
		return "Hello "+who;
	}
	/*
	 * final方法，用于测试easymock的限制
	 */
	public final String helloFinal(String who){
		return "Hello "+who;
	}
//------------set/get method----------------
	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}
	
}
