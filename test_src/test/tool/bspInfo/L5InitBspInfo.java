package test.tool.bspInfo;

import org.loushang.bsp.security.context.BspInfo;
import org.loushang.bsp.security.context.SecurityContext;
import org.loushang.bsp.security.context.SecurityContextHolderStrategy;
import org.loushang.bsp.security.context.SecurityContextImpl;
import org.loushang.bsp.security.context.ThreadLocalSecurityContextHolderStrategy;

public class L5InitBspInfo {

	/*
	 * 初始化当前登录用户信息 BspInfo
	 * 使用方式：
	 * BspInfoImpl bspInfo = new BspInfoImpl();
	 * bspInfo.setDepartmentOrganId("O01");
	 * L5InitBspInfo.init(bspInfo);
	 */
	public static synchronized void init(BspInfo bspInfo) {

		SecurityContext context = new SecurityContextImpl();//安全上下文
		context.setAuthentication(bspInfo);//设置认证后用户的信息
		
		//通过线程变量方式来存储安全上下文
		SecurityContextHolderStrategy threadLocalSecurityContextHolderStrategy = new ThreadLocalSecurityContextHolderStrategy();
		threadLocalSecurityContextHolderStrategy.setContext(context);
	}
	}
/*
* SecurityContext：安全上下文
* SecurityContextHolder：安全上下文持有者
* Authentication：认证后的信息
*/
