package cn.ajajaaj.common;

import org.apache.log4j.Logger;


/*
 * 系统全局常量配置类
 */
public class SysConstant {
	private static Logger log = Logger.getLogger(SysConstant.class);

	public static Integer DISABLED = 0;	//当前用户session name
	public static Integer ENABLED = 1;	//当前用户session name
	public static String CURRENT_USER_INFO = "_CURRENT_USER";	//当前用户session name
	public static String USE_DATABASE = "oracle";				//使用的数据库 Oracle/SQLServer
	public static String USE_DATABASE_VER = "oracle10XE";				//使用的数据库版本 10g/2000

	public static String DEFAULT_PASS = "123456";				//默认密码
	public static int PAGE_SIZE = 4;							//分页时一页显示多少条记录
}
