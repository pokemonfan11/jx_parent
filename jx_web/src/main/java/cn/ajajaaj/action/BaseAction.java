package cn.ajajaaj.action;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @Description:
 * @author:     传智播客 java学院    传智.袁新奇
 * @version:    1.0
 * @Company:    http://java.itcast.cn 
 * @date:       2016年9月14日
 */

//通过RequestAware, SessionAware, ApplicationAware实行接口获得request,session,application对象，action中就可直接调用

public class BaseAction extends ActionSupport implements RequestAware, SessionAware, ApplicationAware{
	private static Logger log = Logger.getLogger(BaseAction.class);
	
	private static final long serialVersionUID = 1L;
	
	protected Map<String, Object> request;
	protected Map<String, Object> session;
	protected Map<String, Object> application;
	
	public Map<String, Object> getRequest() {
		return request;
	}
	
	public Map<String, Object> getSession() {
		return session;
	}
	
	public Map<String, Object> getApplication() {
		return application;
	}
	
	public void setRequest(Map<String, Object> request) {
		this.request = request;
	}
	
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setApplication(Map<String, Object> application) {
		this.application = application;
	}
	
	public void push(Object obj){
		ActionContext.getContext().getValueStack().push(obj);
	}
	
	public void put(String key,Object value){
		ActionContext.getContext().put(key, value);
	}
	/**
	 * 获取当前登录的用户对象
	 * @return
	 */
	/*public User getCurUser(){
		User user = (User)session.get(SysConstant.CURRENT_USER_INFO);
		return user;
	}*/
}
