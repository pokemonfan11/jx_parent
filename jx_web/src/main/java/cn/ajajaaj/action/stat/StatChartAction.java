package cn.ajajaaj.action.stat;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.ajajaaj.action.BaseAction;
import cn.ajajaaj.jx.dao.common.SqlDao;
import cn.ajajaaj.utils.file.FileUtil;

public class StatChartAction extends BaseAction{
	// 为了减化，省略了Service,而直接引入sqlDao
	private SqlDao sqlDao;

	public void setSqlDao(SqlDao sqlDao) {
		this.sqlDao = sqlDao;
	}

	public String factorysale() throws FileNotFoundException {
		// 拼接sql语句
		String sql = "select factory_name,sum(amount) as samount from contract_product_c group by factory_name order by samount desc";
		// 查询
		List<String> list = sqlDao.executeSQL(sql);

		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<pie>");
		for (int i = 0; i < list.size(); i++) {
			sb.append("<slice title='" + list.get(i) + "'>" + list.get(++i) + "</slice>");
		}
		sb.append("</pie>");

		// 下载，输出
		String sPath = ServletActionContext.getRequest().getRealPath("/") + "stat/chart/factorysale";

		FileUtil fileUtil = new FileUtil();
		/**
		 * 第一个参数：路径 第二个参数：文件名 第三个参数：文件内容 第四个参数：编码
		 */
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "utf-8");

		return "factorysale";

	}

	public String productsale() throws Exception {
		// 1 编写sql
		String sql = "select * from (select product_no ,sum(cnumber) amount "
				+ "from contract_product_c group by product_no " + "order by amount desc) where rownum<16";
		// 2 执行sql
		List<String> list = sqlDao.executeSQL(sql);

		// 3 拼装数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");

		/**** 第一部分 ****/
		sb.append("<series>");
		for (int i = 0, j = 0; i < list.size(); i += 2, j++) {
			sb.append("<value xid='" + j + "'>" + list.get(i) + "</value>");
		}
		sb.append("</series>");
		/**** 第二部分 ****/
		sb.append("<graphs>");
		sb.append("<graph gid='30' color='#FFCC00' gradient_fill_colors='#111111, #1A897C'>");
		for (int i = 1, j = 0; i < list.size(); i += 2, j += 1) {
			sb.append("<value xid='" + j + "' description='' url=''>" + list.get(i) + "</value>");
		}
		sb.append("</graph>");
		sb.append("</graphs>");
		/**** 第三部分 ***/
		sb.append("<labels>");
		sb.append("<label lid='0'>");
		sb.append("<x>0</x>");
		sb.append("<y>20</y>");
		sb.append("<rotate></rotate>");
		sb.append("<width></width>");
		sb.append("<align>center</align>");
		sb.append("<text_color></text_color>");
		sb.append("<text_size></text_size>");
		sb.append("<text>");
		sb.append("<![CDATA[<b>产品销量排行</b>]]>");
		sb.append("</text>");
		sb.append("</label>");
		sb.append("</labels>");
		sb.append("</chart>");

		// 将内容写到服务器
		String sPath = ServletActionContext.getRequest().getRealPath("/") + "stat/chart/productsale";

		FileUtil fileUtil = new FileUtil();
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");

		return "productsale";
	}

	public String onlineinfo() throws Exception {
		// 1 查找数据
		String sql = "select a.*,nvl(b.c,0) from online_info_t a "
				+ "left join (select to_char(login_time,'hh24') a1,count(*) c "
				+ "from login_log_p group by to_char(login_time,'hh24')) b " + "on a.a1 = b.a1 order by a.a1 asc";
		// 2 执行sql，获取数据
		List<String> list = sqlDao.executeSQL(sql);

		// 3 拼装数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='UTF-8'?>");
		sb.append("<chart>");
		/******* 第一部分 *******/
		sb.append("<series>");
		for (int i = 0, j = 0; i < list.size(); i += 2, j++) {
			sb.append("<value xid='" + j + "'>" + list.get(i) + "</value>");
		}
		sb.append("</series>");

		/******* 第二部分 ******/
		sb.append("<graphs>");
		sb.append("<graph color='#00CC00' title=''>");
		for (int i = 1, j = 0; i < list.size(); i += 2, j++) {
			sb.append("<value xid='" + j + "'>" + list.get(i) + "</value>");
		}
		sb.append("</graph>");
		sb.append("</graphs>");

		sb.append("</chart>");
		// 将内容写到服务器
		String sPath = ServletActionContext.getRequest().getRealPath("/") + "stat/chart/onlineinfo";

		FileUtil fileUtil = new FileUtil();
		fileUtil.createTxt(sPath, "data.xml", sb.toString(), "UTF-8");
		return "onlineinfo";
	}

	public String productsaleJson() throws Exception {
		// 1 查询数据
		// 1 编写sql
		String sql = "select * from (select product_no ,sum(cnumber) amount "
				+ "from contract_product_c group by product_no " + "order by amount desc) where rownum<16";
		// 2 执行sql
		List<String> list = sqlDao.executeSQL(sql);

		// 3 拼装数据
		String[] colors = { "#FF0F00", "#FF6600", "#FF9E01", "#FCD202", "#F8FF01", "#B0DE09", "#04D215", "#0D8ECF",
				"#0D52D1", "#2A0CD0", "#8A0CCF", "#CD0D74", "#754DEB", "#DDDDDD", "#333333", "#000000" };

		StringBuffer sb = new StringBuffer();
		sb.append("[");
		/**
		 * { "country": "USA", "visits": 4025, "color": "#FF0F00" }
		 */
		for (int i = 0, j = 0; i < list.size(); i++, j++) {
			sb.append("{");
			sb.append("'product_no':'" + list.get(i) + "',");
			sb.append("'amount':" + list.get(++i) + ",");
			sb.append("'color':'" + colors[j] + "'");
			sb.append("},");
		}
		// 去除最后的,
		sb.delete(sb.length() - 1, sb.length());

		sb.append("]");

		//
		this.put("chartData", sb.toString());

		return "productsaleJson";
	}

}
