package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.ContractProduct;
import cn.ajajaaj.utils.Page;

public interface ContractProductService {
	// 分页查找所有功能
	public abstract Page findPage(String hql, Page page, Class entityClass, Object[] params);

	public ContractProduct get(Class<ContractProduct> entityClass, Serializable id);

	/**
	 * 查找所有
	 * 
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public List<ContractProduct> find(String hql, Class<ContractProduct> entityClass, Object[] params);

	/**
	 * 保存模块
	 * 
	 * @param model
	 */
	public void saveOrUpdate(ContractProduct entity);

	// 单条删除，按id
	public void deleteById(Class<ContractProduct> entityClass, Serializable id);

	// 批量删除
	public void delete(Class<ContractProduct> entityClass, Serializable[] ids);
}
