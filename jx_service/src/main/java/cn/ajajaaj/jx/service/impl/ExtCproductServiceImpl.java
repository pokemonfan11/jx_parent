package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.domain.ExtCproduct;
import cn.ajajaaj.jx.service.ExtCproductService;
import cn.ajajaaj.utils.Page;
import cn.ajajaaj.utils.UtilFuns;

public class ExtCproductServiceImpl implements ExtCproductService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtCproduct entity) {
		if (StringUtils.isBlank(entity.getId())) {
			// 新增附件的业务
			/**
			 * 1 将附件的数据插入数据库 2 修改购销合同的金额 3 货物的金额要不要修改？答：不同
			 */
			// 1 判断数量和单价是否为空
			Double amount = 0d;
			if (UtilFuns.isNotEmpty(entity.getCnumber()) && UtilFuns.isNotEmpty(entity.getPrice())) {
				amount = entity.getCnumber() * entity.getPrice();
			}
			// 2 修改当前附件的金额
			entity.setAmount(amount);
			// 3 修改购销合同的金额
			// Contract contract =
			// entity.getContractProduct().getContract();//不行的原因：当前的entity是瞬时态对象，瞬时态对象是无法进行对象导航检索的
			Contract contract = baseDao.get(Contract.class, entity.getContractProduct().getContract().getId());
			contract.setTotalAmount(contract.getTotalAmount() + amount);

			// /修改合同
			baseDao.saveOrUpdate(contract);
			// 新增附件
			baseDao.saveOrUpdate(entity);
		} else {
			// 会使用hibernate的一级缓存，直接从缓存中获取数据
			ExtCproduct ext = baseDao.get(ExtCproduct.class, entity.getId());
			// 计算价格
			Double amount = 0d;
			if (UtilFuns.isNotEmpty(ext.getCnumber()) && UtilFuns.isNotEmpty(ext.getPrice())) {
				amount = ext.getCnumber() * ext.getPrice();
			}
			// 先修改购销合同的金额 --方式一
			// Contract contract = baseDao.get(Contract.class,
			// ext.getContractProduct().getContract().getId());
			// 方式二
			ExtCproduct extCproduct = new ExtCproduct();
			Contract contract = extCproduct.getContractProduct().getContract();

			// 购销合同总金额 = 购销合同总金额 - 附件原始金额 +附件信的总额
			contract.setTotalAmount(contract.getTotalAmount() - ext.getAmount() + amount);

			// 修改附件对应的金额
			ext.setAmount(amount);

			// 修改购销合同
			baseDao.saveOrUpdate(contract);

			// 设置属性
			baseDao.saveOrUpdate(ext);
		}
		//
	}

	@Override
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id) {
		// 1 获取附件信息
		ExtCproduct extCproduct = baseDao.get(ExtCproduct.class, id);
		// 2 获取购销合同信息
		Contract contract = extCproduct.getContractProduct().getContract();
		// 3 计算金额
		contract.setTotalAmount(contract.getTotalAmount() - extCproduct.getAmount());

		// 更新
		baseDao.saveOrUpdate(contract);
		// 删除附件
		baseDao.deleteById(entityClass, id);

	}

	@Override
	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
