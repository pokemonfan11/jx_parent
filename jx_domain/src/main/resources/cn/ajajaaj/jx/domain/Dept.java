package cn.ajajaaj.jx.domain;

import java.io.Serializable;

public class Dept implements Serializable {
	private String id; //编号
	private String deptName;//部门名称
	
	private Dept parent;//父部门  自关联
	
	private Integer state;//状态
//getter和setter方法

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public Dept getParent() {
		return parent;
	}

	public void setParent(Dept parent) {
		this.parent = parent;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}
	
}