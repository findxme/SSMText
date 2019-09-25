<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%
    request.setAttribute("ctx",request.getContextPath());
    %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="${ctx}/user/login2" method="post">
	用户名：<input type="text" name="username"/></br>
	密码：<input type="text" name="password"/>
	<button type="submit">登陆</button>
	</form>
</body>
</html>