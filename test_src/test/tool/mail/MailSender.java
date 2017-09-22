package test.tool.mail;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/*
 * 邮件发送类
 */
public class MailSender{

  /**
  * 以HTML格式发送邮件,可带附件，本方法可作为对外接口提供。
  * @param mailInfo 待发送的邮件信息 
  */  
    public static boolean sendHtmlMailWithLocalAttachments(MailSenderInfo mailInfo) {
		// 判断是否需要身份认证
		MyAuthenticator authenticator = null;
		Properties pro = mailInfo.getProperties();
		// 如果需要身份认证，则创建一个密码验证器
		if (mailInfo.isValidate()) {
			authenticator = new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
		}
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session
		Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
		try {
			// 根据session创建一个邮件消息
			Message mailMessage = new MimeMessage(sendMailSession);
			
			// 设置发送人
			mailMessage.setFrom(new InternetAddress(mailInfo.getFromAddress()));
			
			// 设置接收人
			String toAddress = mailInfo.getToAddress();
			if(toAddress.contains(";")){
				String toAddresses [] = toAddress.split(";");
				for (int i = 0; i < toAddresses.length; i++) {				
					mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddresses[i]));
				}
			}else{		
				mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(toAddress));
			}
			
			//设置抄送人
			String ccAddress = mailInfo.getCcAddress();
			if(ccAddress.contains(";")){
				String ccAddresses [] = ccAddress.split(";");
				for (int i = 0; i < ccAddresses.length; i++) {				
					mailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddresses[i]));
				}
			}else{		
				mailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(ccAddress));
			}
			// 设置邮件消息的主题
			mailMessage.setSubject(mailInfo.getSubject());
			// 设置邮件消息发送的时间
			mailMessage.setSentDate(new Date());
			// MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
			Multipart mainPart = new MimeMultipart();
			// 创建一个包含HTML内容的MimeBodyPart
			BodyPart html = new MimeBodyPart();
			// 设置HTML内容
			html.setContent(mailInfo.getContent(), "text/html; charset=GBK");
			mainPart.addBodyPart(html);

			// 添加附件
			addAttachment(mailInfo, mainPart);
			
			// 将MiniMultipart对象设置为邮件内容
			mailMessage.setContent(mainPart);

			// 发送邮件
			Transport.send(mailMessage);
			return true;
		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
		return false;
	} 
    
    /*
     * 添加邮件附件,用本地的文件作为附件
     */
    private static void addAttachment(MailSenderInfo mailInfo,Multipart mainPart) throws MessagingException{
    	   
    	   String[] attachFileNames = mailInfo.getAttachFileNames();
    	   if(attachFileNames!=null&&attachFileNames.length>0){
    		   for (int i = 0; i < attachFileNames.length; i++) {
    			   MimeBodyPart mdp=new MimeBodyPart();
    			   
    			   //FileDataSource/DataHandler 会用到 activation-1.1.jar，jdk6中已经包含了该jar中内容，不再需要单独下载 activation-1.1.jar
    			   FileDataSource fds=new FileDataSource(attachFileNames[i]);
    			   DataHandler dh=new DataHandler(fds);
    			   mdp.setDataHandler(dh);
    			 try {
    				//保持附件名称与原文件名称一致的写法，MimeUtility.encodeText()可以解决中文附件乱码。
    				 mdp.setFileName(MimeUtility.encodeText(fds.getName()));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
    			   mainPart.addBodyPart(mdp);
    		}
    	   }
    }
} 
