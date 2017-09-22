package test.tool.sca;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContextEvent;
import org.loushang.sca.ContextLoaderListener;

public class L5InitSCA {

	private static boolean isInited = false;

	/*
	 * 初始化之前，首先判断是否是否已经初始化，如果已经初始化，则直接返回。
	 * （批量执行测试用例时用）
	 */
	public static synchronized void init() {

		if (isInited) {
			return;
		} else {
			// 自定义ServletContext，重写getRealPath(String arg0)，使其返回当前工程的根物理路径+arg0。
			MyServletContext myServletContext = new MyServletContext();
			Map<String, String> initParameters = new HashMap<String, String>();
			
			//设置sca配置存储类型是xml
			initParameters.put("assemblytype", "xml");
			myServletContext.setInitParameters(initParameters);
			
			ServletContextEvent servletContextEvent = new ServletContextEvent(myServletContext);// 将自定义servletContext对象传递给ServletContextEvent
			ContextLoaderListener listener = new ContextLoaderListener();
			listener.contextInitialized(servletContextEvent);// ContextLoaderListener在初始化的时候，将ServletContextEvent传递给ContextLoaderListener
			isInited = true;
		}
	}
}
/*
* 框架org.loushang.sca.ContextLoaderListener类实现了接口ServletContextListener，并在web.xml中做为监听器进行了配置，
* 当应用服务器启动时，会自动执行contextInitialized(ServletContextEvent contextEvent)方法，在该方法中，会首先判断sca服务配置类型（xml、db），
* 然后根据相应的配置类型完成sca服务配置文件的加载，和初始化sca服务工作。
* 
* 
* 
*/
