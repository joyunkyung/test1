<%@page import="java.net.InetAddress"%>
<%@page import="magic.board.BoardDBBean"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean class="magic.board.BoardBean" id="board"></jsp:useBean>
<jsp:setProperty property="*" name="board"/>
<%
	InetAddress address = InetAddress.getLocalHost();
	String ip = address.getHostAddress();

	board.setB_date(new Timestamp(System.currentTimeMillis()));
	//board.setB_ip(request.getRemoteAddr());
	board.setB_ip(ip);
	BoardDBBean db = BoardDBBean.getInstance();
	int re = db.insertBoard(board);
	
	if(re == 1){
		response.sendRedirect("list.jsp");
	}else{
		response.sendRedirect("write.jsp");
	}
%>
