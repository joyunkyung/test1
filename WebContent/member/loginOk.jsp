<%@page import="magic.member.MemberBean"%>
<%@page import="magic.member.MemberDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String id = request.getParameter("mem_uid");
	String pwd = request.getParameter("mem_pwd");
	MemberDBBean manager = MemberDBBean.getInstance();
	int check = manager.userCheck(id, pwd);
	MemberBean mb = manager.getMember(id);
	
	if(mb == null){
%>
	<script>
		alert("�������� �ʴ� ȸ��");
		history.go(-1);
	</script>
<%
	}else{
		String name = mb.getMem_name();
		
		if(check == 1){
			session.setAttribute("uid", id);
			session.setAttribute("name", name);
			session.setAttribute("Member", "yes");
			response.sendRedirect("main.jsp");
		}else if(check == 0){
%>
	<script>
				alert("��й�ȣ�� ���� �ʽ��ϴ�.");
				history.go(-1);
	</script>
<%
		}else{
%>
	<script>
				alert("���̵� ���� �ʽ��ϴ�.");
				history.go(-1);
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








