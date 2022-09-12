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
	
/* getParameter()메서드의 경우 String타입을 리턴하는반면,
getAttribute()는 Object 타입을 리턴하기 때문에 주로 빈 객체나 다른 클래스를 받아올때 사용된다.
또한, getParameter()는 웹브라우저에서 전송받은 request영역의 값을 읽어오고
getAttribute()의 경우 setAttribute () 속성을 통한 설정이 없으면 무조건 null값을 리턴한다. */
	
	if(mb == null){//mb == null ->회원정보x
%>
	<script>
		alert("존재하지 않는 회원");
		history.go(-1); //바로 이전
	</script>
<%
	}else{
		String name = mb.getMem_name();
		
		if(check == 1){
			session.setAttribute("uid", id); //uid의 속성값을 id로 셋팅
			session.setAttribute("name", name);
			session.setAttribute("Member", "yes");
			response.sendRedirect("main.jsp"); //해당 페이지로 이동
		}else if(check == 0){
%>
	<script>
				alert("비밀번호가 맞지 않습니다.");
				history.go(-1); //바로 이전
	</script>
<%
		}else{
%>
	<script>
				alert("아이디가 맞지 않습니다.");
				history.go(-1); //바로 이전
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








