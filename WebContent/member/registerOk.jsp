<%@page import="java.sql.Timestamp"%>
<%@page import="magic.member.MemberDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean class="magic.member.MemberBean" id="mb"></jsp:useBean>
<jsp:setProperty property="*" name="mb"/>
<%
	//mb.setMem_regdate(new Timestamp(System.currentTimeMillis()));
	MemberDBBean manager = MemberDBBean.getInstance();

	if(manager.confirmID(mb.getMem_uid()) == 1){
%>
	<script>
		alert("�ߺ��Ǵ� ���̵� �����մϴ�.");
		history.back();
	</script>
<%
	}else{
		int re = manager.insertMember(mb);
		
		if(re == 1){
%>
	<script>
			alert("ȸ�������� ���ϵ帳�ϴ�.\nȸ������ �α��� ���ּ���.");
			document.location.href="login.jsp";
	</script>
<%
		}else{
%>
	<script>
			alert("ȸ�����Կ� �����߽��ϴ�.");
			document.location.href="login.jsp";
	</script>
<%
		}
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







