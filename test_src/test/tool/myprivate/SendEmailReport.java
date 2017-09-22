package test.tool.myprivate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import test.tool.constant.Conf;
import test.tool.date.DateTool;
import test.tool.mail.MailSender;
import test.tool.path.PathTool;

public class SendEmailReport {
	
	/*
	 * 入口函数，ant调用　
	 */
	public static void main(String[] args) {
		
		System.out.println("测试用例执行完毕，发送邮件通知！");
		sendEmailReport();
	}
	
	/*
	 * 通过email发送测试报告
	 */
	public static void sendEmailReport(){
		
		//设置邮件信息
		test.tool.mail.MailSenderInfo mailInfo = new test.tool.mail.MailSenderInfo();
		mailInfo.setMailServerHost(Conf.mail_server_host);
		mailInfo.setMailServerPort(Conf.mail_server_port);
		mailInfo.setValidate(Conf.mail_isValidate);
		mailInfo.setUserName(Conf.mail_username);
		mailInfo.setPassword(Conf.mail_password);
		mailInfo.setFromAddress(Conf.mail_from_address);
		mailInfo.setToAddress(Conf.mail_to_address);
		mailInfo.setCcAddress(Conf.mail_cc_address);
		mailInfo.setSubject("测试报告(发送时间："+DateTool.getDateTimeWithSeconds()+")");
		
		//设置附件信息，共2个（Junit测试报告、覆盖率测试报告）
		String[] attachFileNames = new String[2];
		attachFileNames[0]=PathTool.getRealPath()+"test_report\\Junit测试报告.zip";	
		attachFileNames[1]=PathTool.getRealPath()+"test_report\\覆盖率测试报告.zip";	
		mailInfo.setAttachFileNames(attachFileNames);		
		
		//读取执行日志，并将其作为邮件正文。
		StringBuffer content = new StringBuffer();;	
		
		content.append("<li>测试报告见附件，解压后，可直接双击index.html进行查看！");
		content.append("<li>测试执行相关信息见邮件正文！<hr>");
		
		BufferedReader br = null;
		InputStreamReader inputStreamReader = null;
		try {
			inputStreamReader = new InputStreamReader(new FileInputStream(new File(Conf.log_file_path)),"GBK");//此种写法可有效解决读取文件中的中文显示乱码问题
			 br= new BufferedReader(inputStreamReader);
			 String line = "";
			 while ((line = br.readLine()) != null) {
				 if(line.indexOf("build_mail.xml")>=0){
					 break;
				 }
				 content.append("<br>");
				 content.append(line);
			 }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(br!=null){		
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}	
		mailInfo.setContent(content.toString());

		//以html格式发送，带附件
		MailSender.sendHtmlMailWithLocalAttachments(mailInfo);
		}
}
