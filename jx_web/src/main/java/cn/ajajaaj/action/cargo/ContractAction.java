package cn.ajajaaj.action.cargo;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.common.SysConstant;
import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.domain.User;
import cn.ajajaaj.jx.print.ContractPrint;
import cn.ajajaaj.jx.service.ContractService;
import cn.ajajaaj.utils.Page;

public class ContractAction extends BaseAction implements ModelDriven<Contract> {
	private Contract model = new Contract();

	@Override
	public Contract getModel() {
		return model;
	}

	private ContractService contractService;

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String list() {
		String hql = "from Contract where 1=1";
		// 判断当前用户的权限
		User user = (User) this.getSession().get(SysConstant.CURRENT_USER_INFO);
		// 4 普通员工
		if (user.getUserInfo().getDegree() == 4) {
			hql += " and createBy='" + user.getId() + "'";
		}
		// 3管理本部门
		else if (user.getUserInfo().getDegree() == 3) {
			hql += " and createDept='" + user.getDept().getId() + "'";
		}
		// 2 管理所有的下属部门和人员
		else if (user.getUserInfo().getDegree() == 2) {

		}
		// 1跨部门跨人员进行管理
		else if (user.getUserInfo().getDegree() == 1) {

		}
		// 0 超管
		else {

		}

		contractService.findPage("from Contract", page, Contract.class, null);

		page.setUrl("contractAction_list");

		super.push(page);
		return "list";
	}

	public String toview() {
		Contract contract = contractService.get(Contract.class, model.getId());
		super.push(contract);
		return "toview";
	}

	public String tocreate() {

		return "tocreate";
	}

	public String insert() {
		User user = (User) session.get(SysConstant.CURRENT_USER_INFO);

		model.setCreateBy(user.getId());
		model.setCreateDept(user.getDept().getId());
		contractService.saveOrUpdate(model);
		return SUCCESS;
	}

	public String toupdate() {
		Contract contract = contractService.get(Contract.class, model.getId());
		super.push(contract);
		return "toupdate";
	}

	public String update() {
		contractService.saveOrUpdate(model);
		return SUCCESS;
	}

	public String delete() {
		String[] ids = model.getId().split(", ");
		contractService.delete(Contract.class, ids);
		return SUCCESS;
	}

	public String submit() throws Exception {
		if (model.getId() == null) {
			return SUCCESS;
		}

		String[] ids = model.getId().split(", ");

		for (String id : ids) {
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(1);

			contractService.saveOrUpdate(contract);
		}

		return SUCCESS;
	}

	public String cancel() throws Exception {
		if (model.getId() == null) {
			return SUCCESS;
		}
		String[] ids = model.getId().split(", ");

		for (String id : ids) {
			Contract contract = contractService.get(Contract.class, id);
			contract.setState(0);

			contractService.saveOrUpdate(contract);
		}

		return SUCCESS;
	}
	@SuppressWarnings("deprecation")
	public String print() throws Exception {
		//获取当前的购销合同
		Contract contract = contractService.get(Contract.class, model.getId());
		//获取path
		String path = ServletActionContext.getRequest().getRealPath("/");
		//获取response
		HttpServletResponse response = ServletActionContext.getResponse();
		//穿件ContractPrint对象
		ContractPrint cp = new ContractPrint();
		//打印
		cp.print(contract, path, response);
		
		return NONE;
	}

}
