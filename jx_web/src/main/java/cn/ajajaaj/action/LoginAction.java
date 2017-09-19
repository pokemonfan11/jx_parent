package cn.ajajaaj.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import cn.ajajaaj.common.SysConstant;
import cn.ajajaaj.jx.domain.User;

/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2016年9月17日
 */
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;



	//SSH传统登录方式
	public String login() throws Exception {
		
//		if(true){
//			String msg = "登录错误，请重新填写用户名密码!";
//			this.addActionError(msg);
//			throw new Exception(msg);
//		}
//		User user = new User(username, password);
//		User login = userService.login(user);
//		if (login != null) {
//			ActionContext.getContext().getValueStack().push(user);
//			session.put(SysConstant.CURRENT_USER_INFO, login);	//记录session
//			return SUCCESS;
//		}
		
		try {
			//shiro subject 门面
			//获取subject
			Subject subject = SecurityUtils.getSubject();
			//判断是否登录
			User loginUser = (User)subject.getPrincipal();
			if(loginUser!=null){
				return SUCCESS;
			}
			//封装用户名密码 
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			//将封装好的用户名密码交给shiro 框架登录
			subject.login(token);
			//登录成功后 取得用户存入session
			User principal = (User)subject.getPrincipal();
			session.put(SysConstant.CURRENT_USER_INFO, principal);
		} catch (Exception e) {
			request.put("errInfo", "对不起，登录失败");
			e.printStackTrace();
			return LOGIN;
		}
			return SUCCESS;
		
		
	}
	public String logout() throws Exception {
		session.remove(SysConstant.CURRENT_USER_INFO);
		SecurityUtils.getSubject().logout();
		return "logout";
	}
	public String main(){
		return SUCCESS;
		
	}
	

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

