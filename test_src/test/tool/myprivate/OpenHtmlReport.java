package test.tool.myprivate;

import test.tool.path.PathTool;

public class OpenHtmlReport {
	
	
	/*
	 * 使用IE打开html文件
	 */
	public static void openHtmlReport(){
		
		String junitReportPath = PathTool.getRealPath()+"test_report/junit/html/index.html";	
		String coverageReportPath = PathTool.getRealPath()+"test_report/coverage/index.html";
		System.out.println("junitReportPath="+junitReportPath);
		System.out.println("coverageReportPath="+coverageReportPath);

			try {
				//调用IE打开测试结果
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + junitReportPath);   
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + coverageReportPath);
			}catch (Exception e) {
				e.printStackTrace();
			}	
		}	
	
	/*
	 * 入口函数，ant执行时会调用该方法打开测试报告。
	 */
	public static void main(String[] args) {
	
		System.out.println("测试用例执行完毕，打开测试报告！");
		openHtmlReport();
	}
	}
