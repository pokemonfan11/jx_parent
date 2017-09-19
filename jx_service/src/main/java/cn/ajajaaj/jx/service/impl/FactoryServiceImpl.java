package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Factory;
import cn.ajajaaj.jx.service.FactoryService;
import cn.ajajaaj.utils.Page;

public class FactoryServiceImpl implements FactoryService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public Factory get(Class<Factory> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<Factory> find(String hql, Class<Factory> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Factory entity) {
		baseDao.saveOrUpdate(entity);

	}

	@Override
	public void deleteById(Class<Factory> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Factory> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
