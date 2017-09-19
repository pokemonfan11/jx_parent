package cn.ajajaaj.action.cargo;

import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.ContractProduct;
import cn.ajajaaj.jx.domain.Factory;
import cn.ajajaaj.jx.service.ContractProductService;
import cn.ajajaaj.jx.service.FactoryService;
import cn.ajajaaj.utils.Page;

public class ContractProductAction extends BaseAction implements ModelDriven<ContractProduct> {
	private ContractProduct model = new ContractProduct();

	@Override
	public ContractProduct getModel() {
		return model;
	}

	private ContractProductService contractProductService;

	public void setContractProductService(ContractProductService contractProductService) {
		this.contractProductService = contractProductService;
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
		contractProductService.findPage("from ContractProduct", page, ContractProduct.class, null);

		page.setUrl("contractProductAction_list");

		super.push(page);
		return "list";
	}

	public String toview() {
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		super.push(contractProduct);
		return "toview";
	}

	public String tocreate() {
		// 工厂列表
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='货物'", Factory.class,
				null);

		super.put("factoryList", factoryList);
		// 2.当前购销合同下的所有货物列表
		contractProductService.findPage("from ContractProduct where contract.id=?", page, ContractProduct.class,
				new Object[] { model.getContract().getId() });
		page.setUrl("contractProductAction_tocreate");
		// 将page放入栈顶
		super.push(page);

		return "tocreate";
	}

	public String insert() {
		contractProductService.saveOrUpdate(model);
		return tocreate();
	}

	public String toupdate() {
		// 1.加载当前要更新的对象
		ContractProduct obj = contractProductService.get(ContractProduct.class, model.getId());
		super.push(obj);
		// 2.加载生产厂家
		List<Factory> factoryList = factoryService.find("from Factory where state=1 and ctype='货物'", Factory.class,
				null);
		super.put("factoryList", factoryList);

		return "toupdate";
	}

	public String update() {
		// 先设置属性,去service中计算
		// 1 根据id查找货物的信息
		ContractProduct contractProduct = contractProductService.get(ContractProduct.class, model.getId());
		// 2 将修改的属性赋值给contractProduct
		// 生产厂家
		contractProduct.setFactory(model.getFactory());
		contractProduct.setFactoryName(model.getFactoryName());
		// 货物照片
		contractProduct.setProductImage(model.getProductImage());
		// 数量
		contractProduct.setCnumber(model.getCnumber());
		// 装率
		contractProduct.setLoadingRate(model.getLoadingRate());
		// 单价
		contractProduct.setPrice(model.getPrice());
		// 货描
		contractProduct.setProductDesc(model.getProductDesc());
		// 货号
		contractProduct.setProductNo(model.getProductNo());
		// 包装单位
		contractProduct.setPackingUnit(model.getPackingUnit());
		// 箱数
		contractProduct.setBoxNum(model.getBoxNum());
		// 排序号
		contractProduct.setOrderNo(model.getOrderNo());
		// 要求
		contractProduct.setProductRequest(model.getProductRequest());

		contractProductService.saveOrUpdate(contractProduct);

		return tocreate();

	}

	public String delete() {
		contractProductService.deleteById(ContractProduct.class, model.getId());
		return tocreate();
	}
}
