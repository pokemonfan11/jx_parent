package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.ExtCproduct;
import cn.ajajaaj.utils.Page;

public interface ExtCproductService {
	// 分页查找所有功能
	public abstract Page findPage(String hql, Page page, Class entityClass, Object[] params);

	public ExtCproduct get(Class<ExtCproduct> entityClass, Serializable id);

	/**
	 * 查找所有
	 * 
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public List<ExtCproduct> find(String hql, Class<ExtCproduct> entityClass, Object[] params);

	/**
	 * 保存模块
	 * 
	 * @param model
	 */
	public void saveOrUpdate(ExtCproduct entity);

	// 单条删除，按id
	public void deleteById(Class<ExtCproduct> entityClass, Serializable id);

	// 批量删除
	public void delete(Class<ExtCproduct> entityClass, Serializable[] ids);
}
