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
					<h1>회원 정보 수정</h1>
					'*' 표시 항목은 필수 입력 항목입니다.
				</td>
			</tr>
			<tr height="30">
				<td width="80">User ID</td>
				<td><%= uid %></td>
			</tr>
			<tr height="30">
				<td width="80">암호</td>
				<td><input type="password" name="mem_pwd" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">암호 확인</td>
				<td><input type="password" name="pwd_check" size="20">*</td>
			</tr>
			<tr height="30">
				<td width="80">이    름</td>
				<td><input type="text" name="mem_name" value="<%= mb.getMem_name() %>" size="20"></td>
			</tr>
			<tr height="30">
				<td width="80">E-mail</td>
				<td><input type="text" name="mem_email" value="<%= mb.getMem_email() %>" size="30">*</td>
			</tr>
			<tr height="30">
				<td width="80">주    소</td>
				<td><input type="text" name="mem_address" value="<%= mb.getMem_address() %>" size="40"></td>
			</tr>
			<tr height="30">
				<td colspan="2">
					<input type="button" value=" 수정 " onclick="update_check_ok()">
					<input type="reset" value=" 다시입력 ">
					<input type="button" value=" 가입안함 " onclick="javascript:window.location='login.jsp'">
				</td>
			</tr>
		</form>
	</table>
</body>
</html>










