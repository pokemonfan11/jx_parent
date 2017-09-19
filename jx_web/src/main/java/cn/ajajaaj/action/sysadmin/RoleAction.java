package cn.ajajaaj.action.sysadmin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.Module;
import cn.ajajaaj.jx.domain.Role;
import cn.ajajaaj.jx.service.ModuleService;
import cn.ajajaaj.jx.service.RoleService;
import cn.ajajaaj.utils.Page;

public class RoleAction extends BaseAction implements ModelDriven<Role> {
	private Role model = new Role();

	@Override
	public Role getModel() {
		return model;
	}

	private String moduleIds;
	public void setModuleIds(String moduleIds) {
		this.moduleIds = moduleIds;
	}

	private RoleService roleService;

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	private ModuleService moduleService;

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String list() {
		roleService.findPage("from Role", page, Role.class, null);

		page.setUrl("roleAction_list");

		super.push(page);
		return "list";
	}

	public String toview() {
		Role role = roleService.get(Role.class, model.getId());
		super.push(role);
		return "toview";
	}

	public String tocreate() {

		return "tocreate";
	}

	public String insert() {
		roleService.saveOrUpdate(model);
		return SUCCESS;
	}

	public String toupdate() {
		Role role = roleService.get(Role.class, model.getId());
		super.push(role);
		return "toupdate";
	}

	public String update() {
		Role role = roleService.get(Role.class, model.getId());
		role.setName(model.getName());
		role.setRemark(model.getRemark());
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		roleService.delete(Role.class, ids);
		return SUCCESS;
	}

	public String tomodule() {
		Role role = roleService.get(Role.class, model.getId());

		this.push(role);

		return "tomodule";
	}

	public String roleModuleJsonStr() throws Exception {
		List<Module> moduleList = moduleService.find("from Module", Module.class, null);
		
		Role role = roleService.get(Role.class, model.getId());
		
		Set<Module> modules = role.getModules();
		
		String str = "[";
		int size = moduleList.size();
		for(Module m:moduleList){
			size--;
			String flag ;
			if(modules.contains(m)){
				flag="true";
			}else{
				flag="false";
			}
			
			str+="{";
			str+="id:'"+m.getId()+"',";
			str+="pId:'"+m.getParentId()+"',";
			str+="name:'"+m.getName()+"',";
			str+="checked:'"+flag+"'";
			
			str+="}";
			
			if(size>0){
				str+=",";
			}
		}
		str+="]";
		System.out.println(str);
		//将内容写进response流
		HttpServletResponse response = ServletActionContext.getResponse();
		//设置输入的内容格式和字符集编码
		response.setContentType("application/json;charset=UTF-8"); 
		response.setHeader("cache-control","no-cache");
		//输出json内容
		response.getWriter().write(str);
		
		return NONE;

	}
	public String module() throws Exception {
		//根据角色ID 获得对应角色
		Role role = roleService.get(Role.class, model.getId());
		
		//将模块信息分组
		String[] ids = moduleIds.split(",");
		
		//遍历 勾选的所有模块id 查找对应模块
		Set<Module> moduleSet = new HashSet<Module>();
		for (String id : ids) {
			Module module = moduleService.get(Module.class, id);
			moduleSet.add(module);
			
		}
		//将得到的所有勾选的模块赋予角色并保存
		role.setModules(moduleSet);
		
		roleService.saveOrUpdate(role);
		return SUCCESS;
	}
}
