package test.study.jmockit;

public class Tool {
	
	public static String static_Method(){
		
		return "我是static方法";
	}
	
	public final String  final_Method(){
		
		return "我是final方法";
	}
	
	public final String  final_static_Method(){
		
		return "我是final-static方法";
	}
	
	public String non_final_static_Method(){
		
		return "我不是final-static方法";
	}
}
