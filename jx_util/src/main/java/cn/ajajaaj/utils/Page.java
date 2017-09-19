package cn.ajajaaj.utils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.ajajaaj.common.SysConstant;


 
/**
 * 分页辅助类：对分页的基本数据进行一个简单的封装
 * 用来传递分页参数和查询参数params
 */
public class Page<T> {
    private int pageNo = 1;							//页码，默认是第一页
    private int pageSize = SysConstant.PAGE_SIZE;	//每页显示的记录数，默认是10
    private int totalRecord;						//总记录数
    private int totalPage;							//总页数
    private List<T> results;						//对应的当前页记录
    private Map<String, Object> params = new HashMap<String, Object>();		//其他的参数我们把它分装成一个Map对象
 
    public int getPageNo() {
       return pageNo;
    }
 
    public void setPageNo(int pageNo) {
       this.pageNo = pageNo;
    }
 
    public int getPageSize() {
       return pageSize;
    }
 
    public void setPageSize(int pageSize) {
       this.pageSize = pageSize;
    }
 
    public int getTotalRecord() {
       return totalRecord;
    }
 
    public void setTotalRecord(int totalRecord) {
       this.totalRecord = totalRecord;
       //在设置总页数的时候计算出对应的总页数，在下面的三目运算中加法拥有更高的优先级，所以最后可以不加括号。
       int totalPage = totalRecord%pageSize==0 ? totalRecord/pageSize : totalRecord/pageSize + 1;
       this.setTotalPage(totalPage);
    }
 
    public int getTotalPage() {
       return totalPage;
    }
 
    public void setTotalPage(int totalPage) {
       this.totalPage = totalPage;
    }
 
    public List<T> getResults() {
       return results;
    }
 
    public void setResults(List<T> results) {
       this.results = results;
    }
   
    public Map<String, Object> getParams() {
       return params;
    }
   
    public void setParams(Map<String, Object> params) {
       this.params = params;
    }
 
    public String toString() {
       StringBuilder builder = new StringBuilder();
       builder.append("Page [pageNo=").append(pageNo).append(", pageSize=").append(pageSize).append(", results=").append(results).append(", totalPage=").append(totalPage).append(", totalRecord=").append(totalRecord).append("]");
       return builder.toString();
    }
 
	/* 页面链接 */
    public String url;		//分页按钮中的转向链接
    public void setUrl(String url) {
    	this.url = url;
    }

    public String links;
	public String getLinks() {
		String str="";
		//当前页
		int curPageNo = this.pageNo;		
		//添加样式		
		str+="<span class='noprint' style='padding:5px;'>";
		//放置隐藏域
		str+="<input type='hidden' id='pageNo' name='page.pageNo' value='1'>";		
		//分页调用哪个方法：turnPage(pageNo,url)
		str+="第"+curPageNo+"页    / 共"+totalPage+"页总共"+totalRecord+"条记录每页"+pageSize+"条记录";	
		str+="<a href='#' onclick='turnPage(1,\""+url+"\")'> [首页] </a>";
		//上一页
		
		str+="<a href='#' onclick='turnPage("+(curPageNo-1>1?curPageNo-1:1)+",\""+url+"\")'> [上一页] </a>";
		//下一页
		
		str+="<a href='#' onclick='turnPage("+(curPageNo+1>totalPage?totalPage:curPageNo+1)+",\""+url+"\")'> [下一页] </a>";
		//末页
		str+="<a href='#' onclick='turnPage("+totalPage+",\""+url+"\")'> [末页] </a>";
		
		return str;

	}
	

}