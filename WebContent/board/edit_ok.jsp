<%@page import="magic.board.BoardDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<jsp:useBean class="magic.board.BoardBean" id="board"></jsp:useBean>
<jsp:setProperty property="*" name="board"/>
<%
	//int b_id = Integer.parseInt(request.getParameter("b_id"));
	//String b_pwd = request.getParameter("b_pwd");

	String pageNum = request.getParameter("pageNum");
	
	BoardDBBean db = BoardDBBean.getInstance();
	//int re = db.deleteBoard(b_id, b_pwd);
	int re = db.editBoard(board);
	
	if(re == 1){
		//response.sendRedirect("list.jsp");
		response.sendRedirect("list.jsp?pageNum="+pageNum);
	}else if(re == 0){
%>
	<script>
		alert("��й�ȣ�� ���� �ʽ��ϴ�.");
		history.go(-1);
	</script>
<%
	}else if(re == -1){
%>
	<script>
		alert("������ �����Ͽ����ϴ�.");
		history.go(-1);
	</script>
<%
	}
%>





