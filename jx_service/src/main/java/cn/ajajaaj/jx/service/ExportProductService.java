package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.ExportProduct;
import cn.ajajaaj.utils.Page;

public interface ExportProductService {
	//分页查找所有功能
		public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
		
		public ExportProduct  get(Class<ExportProduct> entityClass, Serializable id);
		
		/**
		 * 查找所有
		 * @paramhql
		 * @paramentityClass
		 * @paramparams
		 * @return
		 */
		public  List<ExportProduct> find(String hql, Class<ExportProduct>entityClass, Object[] params);
		/**
		 * 保存模块
		 * @param model
		 */
		public void saveOrUpdate(ExportProduct entity);
		
		//单条删除，按id
			public  void deleteById(Class<ExportProduct> entityClass, Serializable id);
			//批量删除
			public  void delete(Class<ExportProduct> entityClass, Serializable[] ids);
}
