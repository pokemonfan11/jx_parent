package cn.ajajaaj.jx.quartz;

import java.util.Date;

public class JobTest {
	
	public void execute(){
		System.out.println("执行了调度:"+new Date());
	}
	
}
