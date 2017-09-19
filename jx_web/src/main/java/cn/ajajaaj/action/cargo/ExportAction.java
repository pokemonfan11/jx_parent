package cn.ajajaaj.action.cargo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.domain.Export;
import cn.ajajaaj.jx.domain.ExportProduct;
import cn.ajajaaj.jx.service.ContractService;
import cn.ajajaaj.jx.service.ExportProductService;
import cn.ajajaaj.jx.service.ExportService;
import cn.ajajaaj.utils.Page;
import cn.ajajaaj.utils.UtilFuns;

public class ExportAction extends BaseAction implements ModelDriven<Export> {
	private Export model = new Export();

	private ExportService exportService;

	private ExportProductService exportProductService;

	private ContractService contractService;

	Page page = new Page();

	private String[] mr_id;// id
	private Integer[] mr_changed;// 是否改变了
	private Integer[] mr_cnumber;// 数量
	private Double[] mr_grossWeight;// 毛重
	private Double[] mr_netWeight;// 净重
	private Double[] mr_sizeLength;// 长
	private Double[] mr_sizeWidth;// 宽
	private Double[] mr_sizeHeight;// 高
	private Double[] mr_exPrice;// 单价
	private Double[] mr_tax;// 税

	public void setMr_id(String[] mr_id) {
		this.mr_id = mr_id;
	}

	public void setMr_changed(Integer[] mr_changed) {
		this.mr_changed = mr_changed;
	}

	public void setMr_cnumber(Integer[] mr_cnumber) {
		this.mr_cnumber = mr_cnumber;
	}

	public void setMr_grossWeight(Double[] mr_grossWeight) {
		this.mr_grossWeight = mr_grossWeight;
	}

	public void setMr_netWeight(Double[] mr_netWeight) {
		this.mr_netWeight = mr_netWeight;
	}

	public void setMr_sizeLength(Double[] mr_sizeLength) {
		this.mr_sizeLength = mr_sizeLength;
	}

	public void setMr_sizeWidth(Double[] mr_sizeWidth) {
		this.mr_sizeWidth = mr_sizeWidth;
	}

	public void setMr_sizeHeight(Double[] mr_sizeHeight) {
		this.mr_sizeHeight = mr_sizeHeight;
	}

	public void setMr_exPrice(Double[] mr_exPrice) {
		this.mr_exPrice = mr_exPrice;
	}

	public void setMr_tax(Double[] mr_tax) {
		this.mr_tax = mr_tax;
	}

	public void setContractService(ContractService contractService) {
		this.contractService = contractService;
	}

	public void setExportService(ExportService exportService) {
		this.exportService = exportService;
	}

	public void setExportProductService(ExportProductService exportProductService) {
		this.exportProductService = exportProductService;
	}

	public String contractList() throws Exception {
		String hql = "from Contract where state=1";
		contractService.findPage(hql, page, Contract.class, null);

		page.setUrl("exportAction_contractList");
		super.push(page);
		return "contractList";
	}

	public String list() throws Exception {
		String hql = "from Export";
		exportService.findPage(hql, page, Export.class, null);

		page.setUrl("exportAction_list");
		super.push(page);
		return "list";
	}

	public String toview() throws Exception {

		Export export = exportService.get(Export.class, model.getId());

		super.push(export);

		return "toview";
	}

	public String tocreate() throws Exception {
		String hql = "from Export";

		List<Export> exportList = exportService.find(hql, Export.class, null);

		super.put("exportList", exportList);

		return "tocreate";
	}

	public String insert() throws Exception {
		exportService.saveOrUpdate(model);
		return contractList();
	}

	public String toupdate() throws Exception {
		// 1.根据id,得到要更新的对象
		Export export = exportService.get(Export.class, model.getId());
		super.push(export);
		// 获取报运单下所有的货物
		Set<ExportProduct> exportProducts = export.getExportProducts();
		// 拼接字符串
		StringBuffer sb = new StringBuffer();

		for (ExportProduct ep : exportProducts) {
			sb.append("addTRRecord('mRecordTable',");
			sb.append("'" + ep.getId() + "',");
			sb.append("'" + ep.getProductNo() + "',");
			sb.append("'" + ep.getCnumber() + "',");
			sb.append("'" + (ep.getGrossWeight() == null ? "" : ep.getGrossWeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getNetWeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeLength()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeWidth()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getSizeHeight()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getExPrice()) + "',");
			sb.append("'" + UtilFuns.convertNull(ep.getTax()) + "');");
		}

		// 将内容放入值栈
		this.put("mRecordData", sb.toString());

		return "toupdate";

	}

	public String update() throws Exception {
		Export export = exportService.get(Export.class, model.getId());

		// 报运号
		export.setCustomerContract(model.getCustomerContract());
		// 制单日期
		export.setInputDate(model.getInputDate());
		// 信用证号
		export.setLcno(model.getLcno());
		// 收货人及地址
		export.setConsignee(model.getConsignee());
		// 装运港
		export.setShipmentPort(model.getShipmentPort());
		// 目的港
		export.setDestinationPort(model.getDestinationPort());
		// 运输方式
		export.setTransportMode(model.getTransportMode());
		// 价格条件
		export.setPriceCondition(model.getPriceCondition());
		// 唛头
		export.setMarks(model.getMarks());
		// 备注
		export.setRemark(model.getRemark());

		// 更新报运单货物修改
		Set<ExportProduct> epSet = new HashSet<ExportProduct>();
		for (int i = 0; i < mr_id.length; i++) {
			ExportProduct ep = exportProductService.get(ExportProduct.class, mr_id[i]);
			if (mr_changed[i] != null && mr_changed[i] == 1) {
				ep.setCnumber(mr_cnumber[i]);
				ep.setGrossWeight(mr_grossWeight[i]);
				ep.setNetWeight(mr_netWeight[i]);
				ep.setSizeLength(mr_sizeLength[i]);
				ep.setSizeWidth(mr_sizeWidth[i]);
				ep.setSizeHeight(mr_sizeHeight[i]);
				ep.setExPrice(mr_exPrice[i]);
				ep.setTax(mr_tax[i]);

			}
			epSet.add(ep);
		}
		export.setExportProducts(epSet);

		exportService.saveOrUpdate(export);
		return SUCCESS;
	}

	public String delete() throws Exception {
		String[] ids = model.getId().split(", ");

		exportService.delete(Export.class, ids);

		return SUCCESS;

	}

	public String submit() throws Exception {
		String[] ids = model.getId().split(", ");

		for (String id : ids) {
			Export export = exportService.get(Export.class, id);

			export.setState(1);

			exportService.saveOrUpdate(export);

		}

		return list();

	}

	public String cancel() throws Exception {
		String[] ids = model.getId().split(", ");

		for (String id : ids) {
			Export export = exportService.get(Export.class, id);

			export.setState(0);

			exportService.saveOrUpdate(export);

		}

		return list();

	}

	@Override
	public Export getModel() {
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
