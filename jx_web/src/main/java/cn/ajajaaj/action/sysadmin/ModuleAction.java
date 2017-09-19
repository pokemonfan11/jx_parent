package cn.ajajaaj.action.sysadmin;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.Module;
import cn.ajajaaj.jx.service.DeptService;
import cn.ajajaaj.jx.service.ModuleService;
import cn.ajajaaj.utils.Page;

public class ModuleAction extends BaseAction implements ModelDriven<Module> {
	private Module model = new Module();

	private ModuleService moduleService;

	Page page = new Page();

	public void setModuleService(ModuleService moduleService) {
		this.moduleService = moduleService;
	}

	public String list() throws Exception {
		String hql = "from Module";
		moduleService.findPage(hql, page, Module.class, null);

		page.setUrl("moduleAction_list");
		super.push(page);
		return "list";
	}

	public String toview() throws Exception {

		Module module = moduleService.get(Module.class, model.getId());

		super.push(module);

		return "toview";
	}

	public String tocreate() throws Exception {
		String hql = "from Module";

		List<Module> moduleList = moduleService.find(hql, Module.class, null);

		super.put("moduleList", moduleList);

		return "tocreate";
	}

	public String insert() throws Exception {
		moduleService.saveOrUpdate(model);
		return SUCCESS;
	}

	public String update() throws Exception {
		// 1.根据id,得到要更新的对象
		Module obj = moduleService.get(Module.class, model.getId());
		// 2.页面修改的属性，就要更新值
		obj.setName(model.getName());
		obj.setLayerNum(model.getLayerNum());
		obj.setCpermission(model.getCpermission());
		obj.setCurl(model.getCurl());
		obj.setCtype(model.getCtype());
		obj.setState(model.getState());
		obj.setBelong(model.getBelong());
		obj.setCwhich(model.getCwhich());
		obj.setRemark(model.getRemark());
		obj.setOrderNo(model.getOrderNo());
		// 3.更新
		moduleService.saveOrUpdate(obj);
		return SUCCESS;

	}

	public String toupdate() throws Exception {
		Module module = moduleService.get(Module.class, model.getId());
		super.push(module);
		return "toupdate";
	}

	public String delete() throws Exception {
		// 1.获取删除记录的id集合
		String ids[] = model.getId().split(", ");
		// 2.调用业务方法，删除记录
		moduleService.delete(Module.class, ids);
		return SUCCESS;

	}

	@Override
	public Module getModel() {
		// TODO Auto-generated method stub
		return model;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

}
