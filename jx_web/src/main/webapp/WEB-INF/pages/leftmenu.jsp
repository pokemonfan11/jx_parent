<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<ul>

	<!-- 左侧菜单的实现思路：
	要求：根据当前登录的用户的权限，显示对应的菜单
	步骤：
	用户->角色->模块
	0 获取当前的用户	
	1 遍历当前用户对应的角色
	2 遍历每个角色对应的模块
                3如果当前这个模块还没有显示，就显示出来
             -->
	<!-- 定义一个变量接收已经显示的菜单 -->
	<c:set value="" var="aaa"></c:set>

	<!-- 1 获取当前用户对应的角色  -->
	<c:forEach var="role" items="${_CURRENT_USER.roles }">
		<!--  2 遍历角色的对应的模块 -->
		<%--${role.modules }: modules对应Role类中的属性的名字 --%>
		<c:forEach var="module" items="${role.modules }">
			<!-- 由于角色对应的模块有两种菜单：1 顶部菜单 2 左侧菜单；此处只要显示左侧菜单，那么需要通过判断，只显示左侧菜单
						ctype==0 顶部菜单
						ctype==1 左侧菜单
						当控制完左侧菜单的ctype之后，我们需要考虑的问题是：如何显示点击的模块对应的左侧菜单
							
						moduleName：来自HomeAction，用户在点击顶部菜单的时候，会将数据传入HomeAction中	
						module.remark来自Module对象，remark记录着你来自哪个模块	
					-->
			<c:if test="${module.ctype==1 && (moduleName eq module.remark) }">
				<!-- 由于一些角色对应的模块是重复的，言外之意，就是有些角色具有相同的模块 -->
				<c:if test="${fn:contains(aaa,module.name) eq false }">
					<!-- 此时当前的模块名字还没有显示，所以需要显示 -->
					<c:set var="aaa" value="${aaa },${module.name }" />
					<!--显示模块的名字 -->
					<li><a href="${ctx}/${module.curl}"
						onclick="linkHighlighted(this)" target="main" id="aa_1">${module.name }</a></li>
				</c:if>

			</c:if>

		</c:forEach>
	</c:forEach>
	<%--  <li><a href="${ctx}/${module.curl}" onclick="linkHighlighted(this)" target="main" id="aa_1">${module.cpermission }</a></li> --%>

</ul>
