package test.tool.sca;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import test.tool.path.PathTool;

/*
 * 如果被测试API接口使用了Loushang5框架的SCA服务，则自定义一个ServletContext接口的实现类，
 * 并重写其getRealPath(String arg0)方法， 在getRealPath(String arg0)中，指定sca配置文件所在物理路径。
 */

public class MyServletContext implements ServletContext{

	//自定义initParameters，容器启动时的初始化参数，用于传递sca配置文件存储类型（xml、db），测试时，固定写成xml。
	private Map<String, String>  initParameters;
	public void setInitParameters(Map<String, String> initParameters) {
		this.initParameters = initParameters;
	}
	
	/*
	 * 重写getRealPath(String arg0)，使其返回自定义的路径
	 */
	public String getRealPath(String arg0) {

		return PathTool.getRealPath()+arg0; 
	}
	
	/*
	 * 重写getInitParameter()方法
	 */
	public String getInitParameter(String arg0) {
		
		return initParameters.get(arg0);
	}
	public Object getAttribute(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@SuppressWarnings("unchecked")
	public Enumeration getAttributeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public ServletContext getContext(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getContextPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public Enumeration getInitParameterNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMajorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getMimeType(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getMinorVersion() {
		// TODO Auto-generated method stub
		return 0;
	}

	public RequestDispatcher getNamedDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public RequestDispatcher getRequestDispatcher(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public URL getResource(String arg0) throws MalformedURLException {
		// TODO Auto-generated method stub
		return null;
	}

	public InputStream getResourceAsStream(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Set getResourcePaths(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServerInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public Servlet getServlet(String arg0) throws ServletException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getServletContextName() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getServletNames() {
		// TODO Auto-generated method stub
		return null;
	}

	public Enumeration getServlets() {
		// TODO Auto-generated method stub
		return null;
	}

	public void log(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void log(Exception arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	public void log(String arg0, Throwable arg1) {
		// TODO Auto-generated method stub
		
	}

	public void removeAttribute(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void setAttribute(String arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}

}
