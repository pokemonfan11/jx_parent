package jx_util;

import java.util.Properties;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class test {

	public static void main(String[] args) throws Exception {
		//设置邮件发送相关的属性
		Properties  props = new Properties();
		//设置邮箱主机地址 smtp.qq.com  smtp.126.com   smtp.163.com 
		props.put("mail.smtp.host", "smtp.163.com");
		//设置验证是否打开
		props.put("mail.smtp.auth", "true");
		//获取与邮件服务器的连接
		Session session = Session.getDefaultInstance(props);
		//创建邮件
		MimeMessage mimeMessage = new MimeMessage(session);
		//设置发送地址
		InternetAddress address = new InternetAddress("pokemonfan11@163.com");
		mimeMessage.setFrom(address);
		//设置接收地址
		InternetAddress toAddress = new InternetAddress("pokemonfan11@sina.com");
		//to:直接发送者
		//cc:抄送
		//bcc:密送
		mimeMessage.setRecipient(RecipientType.TO, toAddress);
		//设置邮件标题
		mimeMessage.setSubject("约吗?");
		//设置邮件内容
		mimeMessage.setText("晚上10点操场见？");
		//坐火箭：发送
		Transport transport = session.getTransport("smtp");
		//获取连接
		transport.connect("pokemonfan11@163.com", "tujiren222");
		//发送
		transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
		//关闭连接
		transport.close();
		System.out.println("success");
	}
}