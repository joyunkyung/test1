<%@page import="magic.member.MemberBean"%>
<%@page import="magic.member.MemberDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean class="magic.member.MemberBean" id="mb"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
	String uid = (String) session.getAttribute("uid");
	mb.setMem_uid(uid);

	MemberDBBean manager = MemberDBBean.getInstance();
	int re = manager.updateMember(mb);
	
	if(re == 1){
%>
	<script>
		alert("�Է��Ͻ� ��� ȸ�� ������ �����Ǿ����ϴ�.");
		document.location.href="main.jsp";
	</script>
<%
	}else{
%>
	<script>
		alert("������ ���еǾ����ϴ�.");
		history.go(-1);
	</script>
<%
	}
%>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>

</body>
</html>








