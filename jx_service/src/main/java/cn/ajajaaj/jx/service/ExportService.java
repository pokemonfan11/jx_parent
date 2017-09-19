package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Export;
import cn.ajajaaj.utils.Page;

public interface ExportService {
	//分页查找所有功能
		public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
		
		public Export  get(Class<Export> entityClass, Serializable id);
		
		/**
		 * 查找所有
		 * @paramhql
		 * @paramentityClass
		 * @paramparams
		 * @return
		 */
		public  List<Export> find(String hql, Class<Export>entityClass, Object[] params);
		/**
		 * 保存模块
		 * @param model
		 */
		public void saveOrUpdate(Export entity);
		
		//单条删除，按id
			public  void deleteById(Class<Export> entityClass, Serializable id);
			//批量删除
			public  void delete(Class<Export> entityClass, Serializable[] ids);
}
