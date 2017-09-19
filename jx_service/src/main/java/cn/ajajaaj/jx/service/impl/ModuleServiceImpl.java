package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import com.opensymphony.xwork2.ModelDriven;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Module;
import cn.ajajaaj.jx.domain.Module;
import cn.ajajaaj.jx.service.ModuleService;
import cn.ajajaaj.utils.Page;

public class ModuleServiceImpl implements ModuleService{

private BaseDao baseDao ;
	



	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}




	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {
		
		return baseDao.findPage(hql,page, entityClass, params);
	}




	@Override
	public Module get(Class<Module> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}




	@Override
	public List<Module> find(String hql, Class<Module> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}




	@Override
	public void saveOrUpdate(Module entity) {
		baseDao.saveOrUpdate(entity);

	}




	@Override
	public  void deleteById(Class<Module> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}




	@Override
	public  void delete(Class<Module> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
