package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Factory;
import cn.ajajaaj.utils.Page;

public interface FactoryService {
	// 分页查找所有功能
	public abstract Page findPage(String hql, Page page, Class entityClass, Object[] params);

	public Factory get(Class<Factory> entityClass, Serializable id);

	/**
	 * 查找所有
	 * 
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params);

	/**
	 * 保存模块
	 * 
	 * @param model
	 */
	public void saveOrUpdate(Factory entity);

	// 单条删除，按id
	public void deleteById(Class<Factory> entityClass, Serializable id);

	// 批量删除
	public void delete(Class<Factory> entityClass, Serializable[] ids);
}
