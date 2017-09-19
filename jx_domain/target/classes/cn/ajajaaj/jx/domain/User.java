package cn.ajajaaj.jx.domain;

import java.util.HashSet;
import java.util.Set;

public class User extends BaseEntity {
	private String id;
	private Dept dept;    //用户与部门    多对一
	private UserInfo userInfo;//用户与扩展信息   一对一
	private String userName;//用户名
	private String password;//密码，要加密
	private Integer state;// 状态
	private Set<Role> roles = new HashSet<Role>();//角色
	//用户与角色是的多对多的关系
		public Set<Role> getRoles() {
			return roles;
		}
		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public UserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}	
	
}
