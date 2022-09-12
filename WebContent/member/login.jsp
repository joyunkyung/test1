<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<table border="1" align="center">
		<form method="post" action="loginOk.jsp">
			<tr height="30">
				<td width="100">사용자 ID</td>
				<td width="100">
					<input type="text" name="mem_uid">
				</td>
			</tr>
			<tr height="30">
				<td width="100">비밀번호</td>
				<td width="100">
					<input type="password" name="mem_pwd">
				</td>
			</tr>
			<tr height="30">
				<td colspan="2" align="center">    
				   <input type="submit" value="로 그 인">
				      	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" value="회원가입" 
				   		onclick="javascript:window.location='register.jsp'">
				</td>
			</tr>
		</form>
	</table>
</body>
</html>