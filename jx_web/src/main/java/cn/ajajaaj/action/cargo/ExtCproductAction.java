package cn.ajajaaj.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.ExtCproduct;
import cn.ajajaaj.jx.domain.Factory;
import cn.ajajaaj.jx.service.ExtCproductService;
import cn.ajajaaj.jx.service.FactoryService;
import cn.ajajaaj.utils.Page;

public class ExtCproductAction extends BaseAction implements ModelDriven<ExtCproduct> {
	private ExtCproduct model = new ExtCproduct();

	@Override
	public ExtCproduct getModel() {
		return model;
	}

	private ExtCproductService extCproductService;

	public void setExtCproductService(ExtCproductService extCproductService) {
		this.extCproductService = extCproductService;
	}

	private FactoryService factoryService;

	public void setFactoryService(FactoryService factoryService) {
		this.factoryService = factoryService;
	}

	private Page page = new Page();

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String list() {
		extCproductService.findPage("from ExtCproduct", page, ExtCproduct.class, null);

		page.setUrl("extCproductAction_list");

		super.push(page);
		return "list";
	}

	public String toview() {
		ExtCproduct extCproduct = extCproductService.get(ExtCproduct.class, model.getId());
		super.push(extCproduct);
		return "toview";
	}

	public String tocreate() {
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='附件'", Factory.class,
				null);

		super.put("factoryList", factoryList);

		extCproductService.findPage("from ExtCproduct where contractProduct.id=?", page, ExtCproduct.class,
				new Object[] { model.getContractProduct().getId() });
		// 设置分页组件的url
		page.setUrl("extCproductAction_tocreate");
		// 放入栈顶
		super.push(page);
		return "tocreate";
	}

	public String insert() {
		extCproductService.saveOrUpdate(model);
		return tocreate();
	}

	public String toupdate() {
		// 1.加载当前要更新的对象
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());
		super.push(obj);
		// 2.得到用于生产附件的厂家列表
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='附件'", Factory.class,
				null);
		// 放入值栈中
		super.put("factoryList", factoryList);

		return "toupdate";
	}

	public String update() {
		// 1.根据id,得到要更新的对象
		ExtCproduct obj = extCproductService.get(ExtCproduct.class, model.getId());

		// 2.页面修改的属性，就要更新值
		obj.setFactory(model.getFactory());
		obj.setFactoryName(model.getFactoryName());
		obj.setProductNo(model.getProductNo());
		obj.setProductImage(model.getProductImage());
		obj.setCnumber(model.getCnumber());
		obj.setPackingUnit(model.getPackingUnit());
		obj.setPrice(model.getPrice());
		obj.setOrderNo(model.getOrderNo());
		obj.setProductDesc(model.getProductDesc());
		obj.setProductRequest(model.getProductRequest());
		// 3.更新
		extCproductService.saveOrUpdate(obj);
		return tocreate();

	}

	public String delete() {
		extCproductService.deleteById(ExtCproduct.class, model.getId());

		return tocreate();

	}
}
