<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
	<script language="JavaScript" src="script.js" charset="utf-8"></script>
</head>
<body>
	<table border="1" align="center">
		<form name="reg_frm" method="post" action="registerOk.jsp">
			<tr height="50">
				<td colspan="2">
					<h1>ȸ�� ���� ��û</h1>
					'*' ǥ�� �׸��� �ʼ� �Է� �׸��Դϴ�.
				</td>
			</tr>
			<tr height="30">
				<td width="80">User ID</td>
				<td><input type="text" name="mem_uid" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">��ȣ</td>
				<td><input type="password" name="mem_pwd" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">��ȣ Ȯ��</td>
				<td><input type="password" name="pwd_check" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">��    ��</td>
				<td><input type="text" name="mem_name" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">E-mail</td>
				<td><input type="text" name="mem_email" size="30">*</td>
			</tr>
			<tr height="30">
				<td width="80">��    ��</td>
				<td><input type="text" name="mem_address" size="40"></td>
			</tr>
			<tr height="30">
				<td colspan="2">
					<input type="button" value=" ��� " onclick="check_ok()">
					<input type="reset" value=" �ٽ��Է� ">
					<input type="button" value=" ���Ծ��� " onclick="javascript:window.location='login.jsp'">
				</td>
			</tr>
		</form>
	</table>
</body>
</html>










