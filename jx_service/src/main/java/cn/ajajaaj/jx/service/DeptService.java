package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Dept;
import cn.ajajaaj.utils.Page;

public interface DeptService {
	//分页查找所有功能
	public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
	
	public Dept  get(Class<Dept> entityClass, Serializable id);
	
	/**
	 * 查找所有
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public  List<Dept> find(String hql, Class<Dept>entityClass, Object[] params);
	/**
	 * 保存部门
	 * @param model
	 */
	public void saveOrUpdate(Dept entity);
	
	//单条删除，按id
		public  void deleteById(Class<Dept> entityClass, Serializable id);
		//批量删除
		public  void delete(Class<Dept> entityClass, Serializable[] ids);

}
