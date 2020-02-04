<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div class="w3-container w3-display-middle w3-card w3-padding">
	<form method="post" action="deletePro" name="myform">
	<input type="hidden" name="num" value="${num}">
		<table width="360" class="table-bordered"
			align="center">
			<tr>
				<td colspan="2" height="39" align="center"><font size="+1" /><b>게시물
						삭제</b></font></td>
			</tr>
			<tr>
				<td width="200">비밀번호</td>
				<td width="400"><input type="password" name="passwd" size="8" 
					maxlength="12"></td>
			</tr>
			<tr height="30">
				<td colspan="4" class="w3-center">
				<input type="submit" value="글삭제">
				<input type="button" value="글목록"	onClick="document.location.href='list'">
				</td>
			</tr>

		</table>
	</form>
	</div>
</body>
</html>