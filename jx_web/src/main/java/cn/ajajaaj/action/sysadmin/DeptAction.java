package cn.ajajaaj.action.sysadmin;

import java.util.List;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.Dept;
import cn.ajajaaj.jx.service.DeptService;
import cn.ajajaaj.utils.Page;

public class DeptAction extends BaseAction implements ModelDriven<Dept> {
	private Dept model = new Dept();

	private DeptService deptService;

	Page page = new Page();

	public void setDeptService(DeptService deptService) {
		this.deptService = deptService;
	}

	public String list() throws Exception {
		String hql = "from Dept";
		deptService.findPage(hql, page, Dept.class, null);

		page.setUrl("deptAction_list");
		super.push(page);
		return "list";
	}

	public String toview() throws Exception {

		Dept dept = deptService.get(Dept.class, model.getId());

		super.push(dept);

		return "toview";
	}

	public String tocreate() throws Exception {
		String hql = "from Dept";
		
		List<Dept> deptList = deptService.find(hql, Dept.class, null);

		super.put("deptList",deptList);

		return "tocreate";
	}
	public String insert() throws Exception {
		deptService.saveOrUpdate(model);
		return SUCCESS;
	}
	public String toupdate() throws Exception {
		List<Dept> deptList = deptService.find("from Dept where state=1",Dept.class, null);
		
		Dept dept = deptService.get(Dept.class, model.getId());
		
		deptList.remove(dept);
		
		super.push(deptList);
		super.put("deptList", deptList);
		
		
		return "toupdate";
	}
	public String update() throws Exception {
		deptService.saveOrUpdate(model);
		return SUCCESS;
	}
	public String delete() throws Exception {
		System.out.println(model.getId());
		String[] ids = model.getId().split(", ");
		//deptService.deleteById(Dept.class, model.getId());
		deptService.delete(Dept.class, ids);
		return SUCCESS;
	}
	@Override
	public Dept getModel() {
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
