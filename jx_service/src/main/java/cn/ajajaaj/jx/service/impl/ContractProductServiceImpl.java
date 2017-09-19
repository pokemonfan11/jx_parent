package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.domain.ContractProduct;
import cn.ajajaaj.jx.domain.ExtCproduct;
import cn.ajajaaj.jx.service.ContractProductService;
import cn.ajajaaj.utils.Page;
import cn.ajajaaj.utils.UtilFuns;

public class ContractProductServiceImpl implements ContractProductService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public ContractProduct get(Class<ContractProduct> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ContractProduct entity) {
		if (StringUtils.isBlank(entity.getId())) {
			// 新增
			// 需要做什么事情？
			// 计算这个新增货物的总价，并且将这个总价赋值给购销合同
			// 1 当单价和数量不为null的时候，计算这个价格，更新货物的总价和当前购销合同的总价 “数量” !=null&& “数量”!=""
			Double amount = 0d;
			// if(entity.getCnumber()!=null&&!"".equals(entity.getCnumber()))
			if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice())) {
				amount = entity.getCnumber() * entity.getPrice();
				entity.setAmount(amount);
			}
			// 2 根据id查找购销合同
			Contract contract = baseDao.get(Contract.class, entity.getContract().getId());
			// 现在的购销合同的总价 = 原始价格 + 当前货物的价格
			contract.setTotalAmount(contract.getTotalAmount() + amount);
			// 更新合同
			baseDao.saveOrUpdate(contract);
			// 保存操作
			baseDao.saveOrUpdate(entity);
		} else {
			// 获取货物
			ContractProduct contractProduct = baseDao.get(ContractProduct.class, entity.getId());
			// 设置修改价格
			Double amount = 0d;
			if (UtilFuns.isNotEmpty(contractProduct.getCnumber()) && UtilFuns.isNotEmpty(contractProduct.getPrice())) {
				amount = contractProduct.getCnumber() * contractProduct.getPrice();
			}
			// 购销合同呢?
			Contract contract = contractProduct.getContract();
			// 购销合同的金额 = 购销合同原来的总价 - 货物原来的价格 + 新的价格
			contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount() + amount);
			// 更新当前货物的总价
			contractProduct.setAmount(amount);
			// 更新合同
			baseDao.saveOrUpdate(contract);
			// 更新货物
			baseDao.saveOrUpdate(contractProduct);
		}

	}

	@Override
	public void deleteById(Class<ContractProduct> entityClass, Serializable id) {
		// 修改价格
		// 1 获取货物的信息
		ContractProduct contractProduct = baseDao.get(ContractProduct.class, id);
		// 2获取货物对应的合同信息
		Contract contract = contractProduct.getContract();

		// 3货物货物对应的附件
		Set<ExtCproduct> extCproducts = contractProduct.getExtCproducts();

		// 4修改合同金额： 购销合同金额 = 购销合同金额 - 货物的价格-附件的价格
		contract.setTotalAmount(contract.getTotalAmount() - contractProduct.getAmount());

		for (ExtCproduct cxtC : extCproducts) {
			contract.setTotalAmount(contract.getTotalAmount() - cxtC.getAmount());
		}
		// 更新合同
		baseDao.saveOrUpdate(contract);
		// 删除货物和附件，由于设置了级联操作
		baseDao.deleteById(entityClass, id);

	}

	@Override
	public void delete(Class<ContractProduct> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
