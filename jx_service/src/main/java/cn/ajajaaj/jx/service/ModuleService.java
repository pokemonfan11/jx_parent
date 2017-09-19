package cn.ajajaaj.jx.service;

import java.io.Serializable;
import java.util.List;

import cn.ajajaaj.jx.domain.Module;
import cn.ajajaaj.utils.Page;

public interface ModuleService {
	//分页查找所有功能
		public abstract Page findPage(String hql,Page page,Class entityClass,Object[] params);
		
		public Module  get(Class<Module> entityClass, Serializable id);
		
		/**
		 * 查找所有
		 * @paramhql
		 * @paramentityClass
		 * @paramparams
		 * @return
		 */
		public  List<Module> find(String hql, Class<Module>entityClass, Object[] params);
		/**
		 * 保存模块
		 * @param model
		 */
		public void saveOrUpdate(Module entity);
		
		//单条删除，按id
			public  void deleteById(Class<Module> entityClass, Serializable id);
			//批量删除
			public  void delete(Class<Module> entityClass, Serializable[] ids);
}
