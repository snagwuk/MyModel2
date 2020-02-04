<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>List</title>
</head>
<body>

<p class="w3-left" style="padding-left:30px;">
</p>
<div class="w3-container">
<span class="w3-center w3-Large">
<h3> 게시판(전체글 :${count}) : <%=session.getAttribute("pageNum") %></h3>
</span>
<p class="w3-right w3-padding-right-Large"> <a href="${pageContext.request.contextPath}/board/writeForm">글쓰기</a></p>

<c:if test="${count==0}">
	<table class="table-bordered" width="700">
	<tr class="w3-grey">
		<td align="center"> 게시판에 저장된 글이 없습니다.</td>
	</tr>
	</table>
</c:if>

<c:if test="${count!=0}">
<table class="w3-table-all" border="1">
<tr class="w3-orange">
	<td align="center"> 번호</td>
	<td align="center"> 제목</td>
	<td align="center"> 작성자</td>
	<td align="center"> 작성일</td>
	<td align="center"> 조회</td>
	<td align="center"> IP</td>
	<td align="center"> filename</td>
	<td align="center"> filesize</td>
</tr>
<c:forEach var="article" items="${li}">
	<tr class="w3-yellow">
		<td align="center"> ${number}
			<c:set var="number" value = "${number-1}"/>
		</td>
		<td align="center">
		
		<c:if test="${article.re_level > 0 }">
			<img src="${pageContext.request.contextPath}/img/level.gif" width="${30*article.re_level}" height="16">
			<img src="${pageContext.request.contextPath}/img/re.gif">
		</c:if>
		<c:if test="${article.re_level == 0 }">
			<img src="${pageContext.request.contextPath}/img/level.gif" width="${0}" height="16">
		</c:if>
		 <a href="${pageContext.request.contextPath}/board/content?num=${article.num}">
		${article.subject}</a></td>
		<td align="center"> ${article.writer}</td>
		<td align="center"> ${article.reg_date}</td>
		<td align="center"> ${article.readcount}</td>
		<td align="center"> ${article.ip}</td>
		<td align="center"> ${article.filename}</td>
		<td align="center"> ${article.filesize}</td>
	</tr>

</c:forEach>
</table>

<p align="center">
	<c:if test="${startPage > bottomLine}">
	     <a  href="list?pageNum=${startPage - bottomLine}">[이전]</a>
	</c:if>
	<c:forEach var="i" begin="${startPage}" end="${endPage}">
	    <a  href="list?pageNum=${i}">${i}</a> 
	</c:forEach>
	<c:if test="${endPage < pageCount}">
		     <a  href="list?pageNum=${startPage + bottomLine}">[다음]</a>
	</c:if>
</p>


</c:if>
</div>


</body>
</html>