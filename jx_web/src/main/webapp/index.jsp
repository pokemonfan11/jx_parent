<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<script type="text/javascript">
     window.location.href = "login.action";		//javascript页面跳转
</script>
<body>
		<%-- <jsp:forward page="/login/login.jsp"></jsp:forward> --%>
		<%-- <jsp:forward page="${pageContext.request.contextPath}/test.action">test</jsp:forward> --%>
		<a href="${pageContext.request.contextPath}/login">aaa</a>
</body>
</html>