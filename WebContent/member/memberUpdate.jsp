<%@page import="magic.member.MemberBean"%>
<%@page import="magic.member.MemberDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String uid = (String) session.getAttribute("uid");

	MemberDBBean manager = MemberDBBean.getInstance();
	MemberBean mb = manager.getMember(uid);
%>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
	<script language="JavaScript" src="script.js" charset="utf-8"></script>
</head>
<body>
	<table border="1" align="center">
		<form name="upd_frm" method="post" action="memberUpdateOk.jsp">
			<tr height="50">
				<td colspan="2">
					<h1>ȸ�� ���� ����</h1>
					'*' ǥ�� �׸��� �ʼ� �Է� �׸��Դϴ�.
				</td>
			</tr>
			<tr height="30">
				<td width="80">User ID</td>
				<td><%= uid %></td>
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
				<td><input type="text" name="mem_name" value="<%= mb.getMem_name() %>" size="20"></td>
			</tr>
			<tr height="30">
				<td width="80">E-mail</td>
				<td><input type="text" name="mem_email" value="<%= mb.getMem_email() %>" size="30">*</td>
			</tr>
			<tr height="30">
				<td width="80">��    ��</td>
				<td><input type="text" name="mem_address" value="<%= mb.getMem_address() %>" size="40"></td>
			</tr>
			<tr height="30">
				<td colspan="2">
					<input type="button" value=" ���� " onclick="update_check_ok()">
					<input type="reset" value=" �ٽ��Է� ">
					<input type="button" value=" ���Ծ��� " onclick="javascript:window.location='login.jsp'">
				</td>
			</tr>
		</form>
	</table>
</body>
</html>










