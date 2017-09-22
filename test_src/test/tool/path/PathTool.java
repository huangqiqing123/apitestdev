package test.tool.path;

public class PathTool {

	/*
	 * 获取项目物理根路径，如：D:/workspace/APITest/
	 */
	public static String getRealPath() {

		String url = getClassesPath().replace("WEB-INF/classes/", "");
		//System.out.println("getRealPath()="+url);
		return url;
	}

	/*
	 * 获取项目classes路径,如：D:/workspace/APITest/WEB-INF/classes/
	 */
	public static String getClassesPath() {

		String url = PathTool.class.getResource("/").getPath();
		url = url.replaceAll("%20", " ").substring(1);

		// 生成测试覆盖率时，取值可能会是WEB-INF\instrumented_classes\，故此处加此校验。

		if (url.indexOf("instrumented_classes") >= 0) {
			url = url.replace("instrumented_classes", "classes");
		}
		//System.out.println("getClassesPath()="+url);
		return url;
	}

	/*
	 * 获取项目测试数据存放路径，如：D:/workspace/APITest/WEB-INF/classes/test_case_data/
	 */
	public static String getTestDataPath() {
		return getClassesPath() + "test_case_data/";
	}
}
