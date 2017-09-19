package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.utils.Page;

public interface ContractService {
	// 分页查找所有功能
	public abstract Page findPage(String hql, Page page, Class entityClass, Object[] params);

	public Contract get(Class<Contract> entityClass, Serializable id);

	/**
	 * 查找所有
	 * 
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public List<Contract> find(String hql, Class<Contract> entityClass, Object[] params);

	/**
	 * 保存模块
	 * 
	 * @param model
	 */
	public void saveOrUpdate(Contract entity);

	// 单条删除，按id
	public void deleteById(Class<Contract> entityClass, Serializable id);

	// 批量删除
	public void delete(Class<Contract> entityClass, Serializable[] ids);
}
