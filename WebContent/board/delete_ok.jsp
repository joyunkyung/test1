<%@page import="magic.board.BoardDBBean"%>
<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String pageNum = request.getParameter("pageNum");

	int b_id = Integer.parseInt(request.getParameter("b_id"));
	String b_pwd = request.getParameter("b_pwd");
	
	BoardDBBean db = BoardDBBean.getInstance();
	int re = db.deleteBoard(b_id, b_pwd);
	
	if(re == 1){
		//response.sendRedirect("list.jsp");
		response.sendRedirect("list.jsp?pageNum="+pageNum);
	}else if(re == 0){
%>
	<script>
		alert("비밀번호가 맞지 않습니다.");
		history.go(-1);
	</script>
<%
	}else if(re == -1){
%>
	<script>
		alert("삭제에 실패하였습니다.");
		history.go(-1);
	</script>
<%
	}
%>





