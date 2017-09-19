package cn.ajajaaj.utils;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtil {
	
	/**
	 * 
	 * 
	 * @param toAddress 接收者
	 * @param subject 主题
	 * @param text 邮件内容
	 * @throws Exception 
	 */
	public static void sendMail(String toAddress,String subject,String text) throws Exception{
		//1 设置邮件接收的参数
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.163.com");
		props.put("mail.smtp.auth", "true");
		//2 获取连接
		Session session = Session.getInstance(props);
		
		//3 创建邮件
		MimeMessage mimeMessage = new MimeMessage(session);
		//4 设置发送者
		InternetAddress address = new InternetAddress("pokemonfan11@163.com");
		mimeMessage.setFrom(address);
		
		//5 设置接收者
		InternetAddress toAddress2 = new InternetAddress(toAddress);
		mimeMessage.setRecipient(RecipientType.TO, toAddress2);
		
		//6 设置邮件内容
		mimeMessage.setSubject(subject);
		mimeMessage.setText(text);
		mimeMessage.saveChanges();
		//7 坐火箭
		Transport transport = session.getTransport("smtp");
		transport.connect("smtp.163.com", "pokemonfan11@163.com", "tujiren222");
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		
		transport.close();
		
	}
	
	
	public static void main(String[] args) {
		
		try {
			
			MailUtil.sendMail("pokemonfan11@sina.com", "约吗?", "一起学习");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
