package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Role;
import cn.ajajaaj.utils.Page;

public interface RoleService {
	//分页查找所有功能
	public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
	
	public Role  get(Class<Role> entityClass, Serializable id);
	
	/**
	 * 查找所有
	 * @paramhql
	 * @paramentityClass
	 * @paramparams
	 * @return
	 */
	public  List<Role> find(String hql, Class<Role>entityClass, Object[] params);
	/**
	 * 保存部门
	 * @param model
	 */
	public void saveOrUpdate(Role entity);
	
	//单条删除，按id
		public  void deleteById(Class<Role> entityClass, Serializable id);
		//批量删除
		public  void delete(Class<Role> entityClass, Serializable[] ids);

}
