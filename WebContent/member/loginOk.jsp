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
	
/* getParameter()�޼����� ��� StringŸ���� �����ϴ¹ݸ�,
getAttribute()�� Object Ÿ���� �����ϱ� ������ �ַ� �� ��ü�� �ٸ� Ŭ������ �޾ƿö� ���ȴ�.
����, getParameter()�� ������������ ���۹��� request������ ���� �о����
getAttribute()�� ��� setAttribute () �Ӽ��� ���� ������ ������ ������ null���� �����Ѵ�. */
	
	if(mb == null){//mb == null ->ȸ������x
%>
	<script>
		alert("�������� �ʴ� ȸ��");
		history.go(-1); //�ٷ� ����
	</script>
<%
	}else{
		String name = mb.getMem_name();
		
		if(check == 1){
			session.setAttribute("uid", id); //uid�� �Ӽ����� id�� ����
			session.setAttribute("name", name);
			session.setAttribute("Member", "yes");
			response.sendRedirect("main.jsp"); //�ش� �������� �̵�
		}else if(check == 0){
%>
	<script>
				alert("��й�ȣ�� ���� �ʽ��ϴ�.");
				history.go(-1); //�ٷ� ����
	</script>
<%
		}else{
%>
	<script>
				alert("���̵� ���� �ʽ��ϴ�.");
				history.go(-1); //�ٷ� ����
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








