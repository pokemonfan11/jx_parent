package jx_web;


import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class test {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws Exception {
		//发送电子邮件--采用spring发送复杂邮件
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		
		//获取bean 
		JavaMailSender mailSender = (JavaMailSender) ctx.getBean("mailSender");
		
		//创建邮件
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		//发送复杂邮件---必须记住复杂邮件助手
		//第一个参数：邮件
		//第二个参数：是否是复杂邮件 true：复杂邮件  false：不是复杂邮件
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
		
		//设置发送地址
		helper.setFrom("pokemonfan11@163.com");
		//设置接收地址
		helper.setTo("pokemonfan11@sina.com");
		//设置标题
		helper.setSubject("一起学习");
		//设置内容--采用HTML的方式加图片
		//第一个参数：文本内容
		//第二个参数：是否是html,默认值不是
		helper.setText("<html><head><title>发送图片</title></head><body><h1>图片发送测试</h1><img src=cid:img></body></html>",true);
		
		FileSystemResource fsr = new FileSystemResource(new File("f://dog.jpg"));
		
		
		//将图片资源放到文件中
		//第一个参数：cid的值
		//第二个参数：FileSystemResource资源
		helper.addInline("img", fsr);
		
		//执行发送
		mailSender.send(mimeMessage);
		
		System.out.println("发送成功");
	}
}