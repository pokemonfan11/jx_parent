package cn.ajajaaj.jx.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import cn.ajajaaj.jx.dao.BaseDao;
import cn.ajajaaj.jx.domain.Contract;
import cn.ajajaaj.jx.domain.ContractProduct;
import cn.ajajaaj.jx.domain.Export;
import cn.ajajaaj.jx.domain.ExportProduct;
import cn.ajajaaj.jx.domain.ExtCproduct;
import cn.ajajaaj.jx.domain.ExtEproduct;
import cn.ajajaaj.jx.service.ExportService;
import cn.ajajaaj.utils.Page;
import cn.ajajaaj.utils.UtilFuns;

public class ExportServiceImpl implements ExportService {

	private BaseDao baseDao;

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public Page findPage(String hql, Page page, Class entityClass, Object[] params) {

		return baseDao.findPage(hql, page, entityClass, params);
	}

	@Override
	public Export get(Class<Export> entityClass, Serializable id) {

		return baseDao.get(entityClass, id);
	}

	@Override
	public List<Export> find(String hql, Class<Export> entityClass, Object[] params) {

		return baseDao.find(hql, entityClass, params);
	}

	@Override
	public void saveOrUpdate(Export entity) {
		if(StringUtils.isBlank(entity.getId())){
			//新增
			//准备合同和确认书号 11JK1080 11JK1081 11JK1080
			//分割购销合同的id
			String[] ids = entity.getContractIds().split(", ");
			//遍历，拼接合同和确认书号
			String customerContract = "";
			for(String id:ids){
				Contract contract = baseDao.get(Contract.class, id);
				customerContract+=contract.getContractNo();
				//修改购销合同的状态
				contract.setState(2);
				baseDao.saveOrUpdate(contract);
			}
			//报运单日期
			entity.setInputDate(new Date());
			//新增的报运单是 草稿
			entity.setState(0);
			
			//数据搬家
			//得先查找合同下的所有的货物'a','b','c'     
			//现在的ContractIds()：a, b, c 
			//数组['a','b','c']
			//1 手动拼接
			//2使用util
			//方式一：手动拼接
			/*String str="";
			for(String id:ids){
				str+="'"+id+"',";
			}
			//删除最后一个逗号
			str=str.substring(0, str.length()-1);*/
			//方式二:工具类
			String str = UtilFuns.joinInStr(ids);
			//拼接hql语句
			String hql ="from ContractProduct where contract.id in ("+str+")";
			
			//执行查询
			List<ContractProduct>cpList = baseDao.find(hql, ContractProduct.class, null);
			Set<ExportProduct>epSet = new HashSet<ExportProduct>();
			
			for(ContractProduct cp:cpList){
				//创建EP
				ExportProduct ep = new ExportProduct();
				//工厂、货号、包装单位、数量、单价
				ep.setFactory(cp.getFactory());
				ep.setProductNo(cp.getProductNo());
				ep.setPackingUnit(cp.getPackingUnit());
				ep.setCnumber(cp.getCnumber());
				ep.setPrice(cp.getPrice());
				//设置报运单信息
				ep.setExport(entity);
				//报运货物集合添加报运货物
				epSet.add(ep);
				
				//整附件
				Set<ExtCproduct>extCproducts = cp.getExtCproducts();
				Set<ExtEproduct>extEproducts = new HashSet<ExtEproduct>();
				
				for(ExtCproduct extC:extCproducts){
					ExtEproduct extE = new ExtEproduct();
					
					//复制属性:只要对象的属性名一致，就自动将属性拷贝
					//第一个参数：数据源
					//第二个参数：目标对象
					BeanUtils.copyProperties(extC, extE);
					//由于两个对象的id名字一致，也会拷贝，所以得将ExtE中的id属性清空
					extE.setId(null);
					//设置附件所属货物
					extE.setExportProduct(ep);
					//附件集合添加附件
					extEproducts.add(extE);
				}
				//附件集合与货物产生关系
				ep.setExtEproducts(extEproducts);
			}
			//epList与Export产生关系
			entity.setExportProducts(epSet);
			
			baseDao.saveOrUpdate(entity);
		}else{
//			修改
			Export export = baseDao.get(Export.class, entity.getId());
			
			
			baseDao.saveOrUpdate(export);
		}
		

	}

	@Override
	public void deleteById(Class<Export> entityClass, Serializable id) {
		baseDao.deleteById(entityClass, id);
	}

	@Override
	public void delete(Class<Export> entityClass, Serializable[] ids) {
		baseDao.delete(entityClass, ids);
	}

}
