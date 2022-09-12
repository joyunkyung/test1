<%
	session.invalidate(); //세션 삭제
	response.sendRedirect("login.jsp");
%>