package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.ajajaaj.common.SysConstant;
import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Role;
import cn.ajajaaj.jx.service.RoleService;
import cn.ajajaaj.utils.Page;

public class RoleServiceImpl implements RoleService{
	
	private BaseDao baseDao ;
	



	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}




	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {
		
		return baseDao.findPage(hql,page, entityClass, params);
	}




	@Override
	public Role get(Class<Role> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}




	@Override
	public List<Role> find(String hql, Class<Role> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}




	@Override
	public void saveOrUpdate(Role entity) {
		baseDao.saveOrUpdate(entity);

	}




	@Override
	public  void deleteById(Class<Role> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}




	@Override
	public  void delete(Class<Role> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}
}
