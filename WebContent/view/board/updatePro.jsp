<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${check==1}">
	<meta http-equiv="Refresh" content="0;url=list">

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
</c:if>
<c:if test="${check==0}">
	<script >
		alert("비밀번호가 맞지않습니다.");
		history.go(-1);
	</script>
</c:if>
<c:if test="${check==-1}">
	<script >
		alert("게시물이 없습니다.");
		history.go(-1);
	</script>
</c:if>
</body>
</html>
