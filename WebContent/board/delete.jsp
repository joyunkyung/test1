<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String pageNum = request.getParameter("pageNum");

	int b_id = Integer.parseInt(request.getParameter("b_id"));
%>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
	<script language="JavaScript" src="board.js" charset="utf-8"></script>
</head>
<body>
	<center>
		<h1>�� �� �� �� ��</h1>
		<form name="del_frm" method="post" action="delete_ok.jsp?b_id=<%= b_id %>&pageNum=<%= pageNum %>">
			<table>
				<tr height="50">
					<td colspan="2" align="left">
						<b>
							>> ��ȣ�� �Է��ϼ���. <<
						</b>
					</td>
				</tr>
				<tr height="50">
					<td width="80">
						��&nbsp;&nbsp;ȣ
					</td>
					<td>
						<input type="password" name="b_pwd" size="12" maxlength="12">
					</td>
				</tr>
				<tr height="50">
					<td colspan="2" align="center">
						<input type="button" value="�ۻ���" onclick="delete_ok()">&nbsp;&nbsp;&nbsp;
						<input type="reset" value="�ٽ��ۼ�">&nbsp;
						<input type="button" value="�۸��" onclick="location.href='list.jsp?pageNum=<%= pageNum %>'">&nbsp;&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</form>
	</center>
</body>
</html>










