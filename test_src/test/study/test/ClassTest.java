package test.study.test;

import test.tool.stacktrace.StackTraceTool;

public class ClassTest {
	
	public static void main(String[] arg)
	{
		System.out.println(StackTraceTool.getCurrentClassName());
		System.out.println(StackTraceTool.getCurrentFileName());
		System.out.println(StackTraceTool.getCurrentLineNumber());
		System.out.println(StackTraceTool.getCurrentMethodName());
	}
	

}
