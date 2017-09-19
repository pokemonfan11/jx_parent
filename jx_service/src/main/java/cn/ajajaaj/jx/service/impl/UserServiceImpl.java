package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.mail.MailMessage;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import cn.ajajaaj.common.SysConstant;
import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.User;
import cn.ajajaaj.jx.service.UserService;
import cn.ajajaaj.utils.Encrypt;
import cn.ajajaaj.utils.Page;
import cn.ajajaaj.utils.UtilFuns;

public class UserServiceImpl implements UserService{
	private BaseDao baseDao;
	private SimpleMailMessage mailMessage;
	private JavaMailSenderImpl mailSender;
	
	
	
	
	public void setMailMessage(SimpleMailMessage mailMessage) {
		this.mailMessage = mailMessage;
	}

	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page<User> findPage(String hql, Page<User> page, Class<User> entityClass, Object[] params) {
		
		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public List<User> find(String hql, Class<User> entityClass, Object[] params) {
		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public User get(Class<User> entityClass, Serializable id) {
		return baseDao.get(entityClass, id);
	}

	@Override
	public void saveOrUpdate(final User entity) {
		if(UtilFuns.isEmpty(entity.getId())){
			String id = UUID.randomUUID().toString();
			entity.setId(id);
			entity.getUserInfo().setId(id);
			
			final String pwd = randomPwd();
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//发送邮件
					try {
						/*MailUtil.sendMail(entity.getUserInfo().getEmail(), "欢迎加入世纪佳缘网", "您的账号是:"
								+entity.getUserName()+",登录密码是:"+pwd);*/
						mailMessage.setTo("pokemonfan11@sina.com");
						mailMessage.setSubject("主题傻逼");
						mailMessage.setText("正文傻逼");
						mailSender.send(mailMessage);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}).start();

			
			
			entity.setPassword(Encrypt.md5(SysConstant.DEFAULT_PASS, entity.getUserName()));
			baseDao.saveOrUpdate(entity);
		}else{
			User user = baseDao.get(User.class, entity.getId());
			user.setDept(entity.getDept());
			user.setUserName(entity.getUserName());
			user.setState(entity.getState());
			baseDao.saveOrUpdate(user);
		}
	}
	
	public String randomPwd(){
		String pwd = "";
		String values="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVMXYZ0123456789.!@#$%^&*()_+";
		/**
		 * 产生随机数的方式：
		 * 1 Random
		 * 2 Math：随机性更高
		 */
//		Math.random();//0-0.999999999999999999
		
		Random random = new Random();
		for(int i = 0;i<6;i++){
			int index = random.nextInt(values.length());
			pwd+=values.charAt(index);
		}
		return pwd;
	}

	
	@Override
	public void saveOrUpdateAll(Collection<User> entitys) {
		
	}

	@Override
	public void deleteById(Class<User> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<User> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

	@Override
	public User findUserByName(String username) {
		List<User> list = baseDao.find("from User where userName=?", User.class, new String[]{username});
		if(list!=null&&list.size()>0){
			return list.get(0);
		}else
		return null;
	}

}
