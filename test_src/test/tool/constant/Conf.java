package test.tool.constant;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import test.tool.properties.PropertiesTool;

public class Conf {

	//加载API测试框架自身的配置文件
	private static final Properties conf = PropertiesTool.load_property_file("conf.properties");

	public static final boolean isPrintSql = conf.get("isPrintSql").toString().toLowerCase().equals("true")?true:false;
	public static String log_file_path = null;
	static{
		try {
			//解决读取的property文件中的中文乱码，此处编码应与conf.properties的文件编码一致。
			log_file_path = new String(conf.getProperty("log_file_path").getBytes("ISO8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log_file_path = conf.getProperty("log_file_path");
		}
	}
	
	//邮件相关
	public static final String mail_server_host = conf.getProperty("mail_server_host");//mail.inspur.com  smtp.163.com
	public static final String mail_server_port = conf.getProperty("mail_server_port");//端口号 25
	public static final boolean mail_isValidate = conf.getProperty("isValidate").equals("true")?true:false;
	public static final String mail_username = conf.getProperty("username");//邮箱的登录用户名，@的前半部分
	public static final String mail_password = conf.getProperty("password");// 邮箱密码
	public static final String mail_from_address =  conf.getProperty("from_address");//发送方地址
	public static final String mail_to_address = conf.getProperty("to_address");//接收方地址
	public static final String mail_cc_address = conf.getProperty("cc_address");//抄送方地址
}
