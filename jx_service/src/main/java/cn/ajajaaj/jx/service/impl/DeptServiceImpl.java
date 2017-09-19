package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cn.ajajaaj.common.SysConstant;
import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Dept;
import cn.ajajaaj.jx.service.DeptService;
import cn.ajajaaj.utils.Page;

public class DeptServiceImpl implements DeptService{
	
	private BaseDao baseDao ;
	



	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}




	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {
		
		return baseDao.findPage(hql,page, entityClass, params);
	}




	@Override
	public Dept get(Class<Dept> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}




	@Override
	public List<Dept> find(String hql, Class<Dept> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}




	@Override
	public void saveOrUpdate(Dept entity) {
//		if(entity.getId()!=null&&!entity.getId().equals(""))
		if(StringUtils.isBlank(entity.getId())){
			//默认新增的部门都是可用的
			//当项目中使用0/1这样的数字表示状态的时候，可以使用静态常量，也可以使用枚举
//			entity.setState(1);
			entity.setState(SysConstant.ENABLED);//enabled可用
			//新增
			baseDao.saveOrUpdate(entity);
		}else{
			//修改
			Dept dept = baseDao.get(Dept.class, entity.getId());
			
			dept.setDeptName(entity.getDeptName());
			dept.setParent(entity.getParent());
			
			baseDao.saveOrUpdate(dept);
		}

	}




	@Override
	public  void deleteById(Class<Dept> entityClass, Serializable id) {
		//查询所有子部门
		List<Dept> childList = baseDao.find("from Dept where parent.id=?", entityClass, new Serializable[]{id});
		//遍历递归子部门
		if(childList!=null&&childList.size()!=0){
			for (Dept dept : childList) {
				deleteById(entityClass,dept.getId());
				
			}
		}
		//删除部门
		if(baseDao.get(entityClass, id)!=null)
		{
		/*baseDao.deleteById(entityClass, id);*/
			Dept dept = baseDao.get(entityClass, id);
			dept.setState(SysConstant.DISABLED);
			baseDao.saveOrUpdate(dept);
		}
	}




	@Override
	public  void delete(Class<Dept> entityClass, Serializable[] ids) {
		for (Serializable id : ids) {
			deleteById(entityClass, id);
		}
	}
}
