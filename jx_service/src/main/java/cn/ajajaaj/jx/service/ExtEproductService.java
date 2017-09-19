package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.ExtEproduct;
import cn.ajajaaj.utils.Page;

public interface ExtEproductService {
	//分页查找所有功能
		public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
		
		public ExtEproduct  get(Class<ExtEproduct> entityClass, Serializable id);
		
		/**
		 * 查找所有
		 * @paramhql
		 * @paramentityClass
		 * @paramparams
		 * @return
		 */
		public  List<ExtEproduct> find(String hql, Class<ExtEproduct>entityClass, Object[] params);
		/**
		 * 保存模块
		 * @param model
		 */
		public void saveOrUpdate(ExtEproduct entity);
		
		//单条删除，按id
			public  void deleteById(Class<ExtEproduct> entityClass, Serializable id);
			//批量删除
			public  void delete(Class<ExtEproduct> entityClass, Serializable[] ids);
}
