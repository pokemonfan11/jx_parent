package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.ExportProduct;
import cn.ajajaaj.jx.service.ExportProductService;
import cn.ajajaaj.utils.Page;

public class ExportProductServiceImpl implements ExportProductService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public ExportProduct get(Class<ExportProduct> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<ExportProduct> find(String hql, Class<ExportProduct> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(ExportProduct entity) {
		baseDao.saveOrUpdate(entity);

	}

	@Override
	public void deleteById(Class<ExportProduct> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<ExportProduct> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
