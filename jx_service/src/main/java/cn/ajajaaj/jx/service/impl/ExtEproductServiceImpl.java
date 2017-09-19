package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.ExtEproduct;
import cn.ajajaaj.jx.service.ExtEproductService;
import cn.ajajaaj.utils.Page;

public class ExtEproductServiceImpl implements ExtEproductService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public ExtEproduct get(Class<ExtEproduct> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<ExtEproduct> find(String hql, Class<ExtEproduct> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExtEproduct entity) {
		baseDao.saveOrUpdate(entity);

	}

	@Override
	public void deleteById(Class<ExtEproduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExtEproduct> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
