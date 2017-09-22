import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.swing.JOptionPane;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.easymock.EasyMock;
import org.loushang.bsp.security.context.GetBspInfo;
import org.loushang.bsp.security.context.impl.BspInfoImpl;
import org.loushang.bsp.security.data.SecurityConstants;
import test.tool.sca.L5InitSCA;

public class T1 {

	public static void main(String[] args) {
		
		L5InitSCA.init();
		
		//创建模拟对象
		HttpServletRequest request = EasyMock.createMock(HttpServletRequest.class);
		HttpSession session = EasyMock.createMock(HttpSession.class);
		//设置对象行为-录制
		BspInfoImpl bsp = new BspInfoImpl();
		bsp.setUserName("黄启庆");
		EasyMock.expect(session.getAttribute(SecurityConstants.SECURITY_USERINFO_KEY)).andReturn(bsp);
		EasyMock.expect(request.getSession()).andReturn(session);
		//进入回放状态
		EasyMock.replay(request,session);
		System.out.println(GetBspInfo.getBspInfo(request).getUserName());
	}
}
