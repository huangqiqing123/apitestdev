package test.tool.stacktrace;

public class StackTraceTool {

	/*
	 * 获取该接口调用者的 类名
	 */
	public static String getCurrentClassName(){        
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        return stacks[1].getClassName();
    } 
	/*
	 * 获取该接口调用者的 文件名
	 */
	public static String getCurrentFileName(){        
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        return stacks[1].getFileName();
    }
	/*
	 * 获取该接口调用者的 方法名
	 */
	public static String getCurrentMethodName(){        
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        return stacks[1].getMethodName();
    }
	/*
	 * 获取该接口调用者的 行号
	 */
	public static int getCurrentLineNumber(){        
        StackTraceElement[] stacks = new Throwable().getStackTrace();  
        return stacks[1].getLineNumber();
    }
}
