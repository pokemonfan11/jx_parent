package cn.ajajaaj.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.Dept;
import cn.ajajaaj.jx.domain.Role;
import cn.ajajaaj.jx.domain.User;
import cn.ajajaaj.jx.service.DeptService;
import cn.ajajaaj.jx.service.RoleService;
import cn.ajajaaj.jx.service.UserService;
import cn.ajajaaj.utils.Page;

public class UserAction extends BaseAction implements ModelDriven<User> {
	private User model = new User();

	@Override
	public User getModel() {
		return model;
	}

	// 用于接收多个复选框的值
	private String roleIds;

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	private DeptService deptService;

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	private UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String list() {
		userService.findPage("from User", page, User.class, null);
		page.setUrl("userAction_list");
		super.push(page);
		return "list";
	}

	public String toview() {
		User user = userService.get(User.class, model.getId());
		super.push(user);
		return "toview";
	}

	public String tocreate() {
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		List<User> userList = userService.find("from User where state=1", User.class, null);
		super.put("deptList", deptList);
		super.put("userList", userList);
		return "tocreate";
	}

	// 保存
	public String insert() throws Exception {
		userService.saveOrUpdate(model);
		return SUCCESS;
	}

	// 修改回显
	public String toupdate() throws Exception {
		List<Dept> deptList = deptService.find("from Dept where state=1", Dept.class, null);
		User user = userService.get(User.class, model.getId());
		super.put("deptList", deptList);
		super.push(user);
		return "toupdate";
	}

	// 执行修改
	public String update() throws Exception {
		userService.saveOrUpdate(model);
		return SUCCESS;
	}

	// 删除
	public String delete() throws Exception {
		// 1.获取删除记录的id集合
		String ids[] = model.getId().split(", ");
		// 2.调用业务方法，删除记录
		userService.delete(User.class, ids);
		return SUCCESS;
	}

	public String torole() throws Exception {
		// 查询所有角色
		List<Role> roleList = roleService.find("from Role", Role.class, null);
		// 查询用户拥有的角色
		User user = userService.get(User.class, model.getId());

		Set<Role> roles = user.getRoles();

		String userRoleStr = "";

		for (Role role : roles) {
			userRoleStr += "," + role.getName();
		}
		this.put("roleList", roleList);
		this.put("userRoleStr", userRoleStr);
		this.push(user);
		return "torole";
	}
	public String role() throws Exception {
		User user = userService.get(User.class, model.getId());
		
		String[] ids = roleIds.split(", ");
		
		Set<Role> set = new HashSet<Role>();
		
		for (String id : ids) {
			Role role = roleService.get(Role.class, id);
			set.add(role);
			
		}
		user.setRoles(set);
		userService.saveOrUpdate(user);
		
		
		return SUCCESS;
	}
}
