package magic.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class MemberDBBean {
	//생성자를 priavte로 만들어 접근을 막는다
	private static MemberDBBean instance = new MemberDBBean();
						//최초 한번만 new 연산자를 통하여 메모리에 할당
	public static MemberDBBean getInstance() {// getInstance(): 객체생성(최초에 할당된 하나의 메모리를 계속 씀//new랑 다름)
		return instance;
		//getInstance 메소드를 통해 한번만 생성된 객체를 가져온다.
	}
	
	//데이터베이스 연결
	public Connection getConnection() throws Exception {//throws Exception: 예외 떠 넘기기
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	//INSERT
	public int insertMember(MemberBean member) throws Exception {
		int re=-1;	// 결과값 분기처리 하기 위해 변수 선언			
		Connection conn=null;	//dbcp 연결위한 참조변수 선언  / 데이터베이스 연결
		PreparedStatement pstmt=null; 	//SQL문 사용하기 위한 참조변수 선언
		String sql="INSERT INTO MEMBERT VALUES(?,?,?,?,?,?)"; //쿼리를 변수로 차용
		
		try {//DB 처리를 위해 예외 발생을 위한 try_catch문 사용
			conn = getConnection(); //dbcp 연결해서 conn 참주변수로 받음
			pstmt = conn.prepareStatement(sql); //conn 객체에서 prepareStatement 메소드의 매개변수로 쿼리문 사용
			pstmt.setString(1, member.getMem_uid());
			pstmt.setString(2, member.getMem_pwd());
			pstmt.setString(3, member.getMem_name());
			pstmt.setString(4, member.getMem_email());
			pstmt.setTimestamp(5, member.getMem_regdate());
			pstmt.setString(6, member.getMem_address());
			pstmt.executeUpdate(); //SQL쿼리에 INSERT사용시 필요
			
			re=1;
		}catch(SQLException ex){
			System.out.println("추가 실패");
			ex.printStackTrace(); //오류 발생 시 오류 출력
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace(); //오류 발생 시 오류 출력
			}
		}
		
		return re;
	}
	
//회원가입시 id 중복확인
	public int confirmID(String id) throws Exception { //getConnection 때문에 Exception 처리
		int re=-1;// 결과값 분기처리 하기 위해 변수 선언
		Connection conn=null; //dbcp 연결위한 참조변수 선언 / 데이터베이스 연결
		PreparedStatement pstmt=null; //SQL문 사용하기 위한 참조변수 선언
		ResultSet rs=null;//  쿼리 결과값 받기 위한 참조변수 선언
		String sql="SELECT mem_uid FROM MEMBERT WHERE mem_uid=?"; //쿼리를 변수로 차용 //해당 id가 존재 하는지
		
		try { //DB 처리를 위해 예외 발생을 위한 try_catch문 사용
			conn = getConnection();//dbcp 연결해서 conn 참주변수로 받음
			pstmt = conn.prepareStatement(sql);//conn 객체에서 prepareStatement 메소드의 매개변수로 쿼리문 사용
			pstmt.setString(1, id); //쿼리 파라미터를 index로 받아서 처리
			rs = pstmt.executeQuery(); //select 조회문은 executeQuery 메소드 처리
			//while or if
			if (rs.next()) { //쿼리 결과값이 있으면 (결과값 여러개인 경우는 while문 사용)
				re=1; //아이디로 조회될때 (가입x)
			}else {
				re=-1;// 아이디 없을 때 (가입가능)
			}
		}catch(SQLException ex){ 
			System.out.println("조회 실패");
			ex.printStackTrace();//쿼리 조회하다가 오류날 때
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace(); //오류 발생 시 오류 출력
			}
		}
		
		return re;
	}
	
	// 로그인시 아이디 존재 확인 여부 메소드 & 비밀번호 확인
	public int userCheck(String id, String pwd) throws Exception {
		int re=-1;// 결과값 분기처리 하기 위해 변수 선언
		Connection conn=null; //dbcp 연결위한 참조변수 선언 / 데이터베이스 연결
		PreparedStatement pstmt=null; //SQL문 사용하기 위한 참조변수 선언
		ResultSet rs=null;//  쿼리 결과값 받기 위한 참조변수 선언
		String db_mem_pwd;
		String sql="SELECT MEM_PWD FROM MEMBERT WHERE mem_uid=?"; //해당 id에 pw가 일치하는지 확인하는 쿼리
		
		try {
			conn = getConnection();//dbcp 연결해서 conn 참주변수로 받음
			pstmt = conn.prepareStatement(sql);//conn 객체에서 prepareStatement 메소드의 매개변수로 쿼리문 사용
			pstmt.setString(1, id); //쿼리 파라미터를 index로 받아서 처리
			rs = pstmt.executeQuery(); //select 조회문은 executeQuery 메소드 처리
			//while or if
			if (rs.next()) {
				db_mem_pwd = rs.getString("mem_pwd");
				
				if(db_mem_pwd.equals(pwd)) {
					re=1; // 비밀번호 일치
				}else {
					re=0; // 비밀번호 불일치
				}
			}else { // 아이디가 존재하지 않음
				re=-1;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return re;
	}
	
	
	// 아이디가 일치하는 멤버의 정보를 얻어오는 메소드
	public MemberBean getMember(String id) throws Exception {
		//(getMember -> set으로 받음)
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//String sql = "select * from memberT where mem_uid=?"; //웬만하면 다 적어주는게 좋음
		String sql="SELECT mem_uid, mem_pwd, mem_name, mem_email, mem_regdate, mem_address"
				+ " FROM MEMBERT WHERE mem_uid=?";
		MemberBean member=null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			//while or if
			if (rs.next()) {
				member = new MemberBean();
				member.setMem_uid(rs.getString("mem_uid"));//member.setMem_uid(rs.getString(1));
				member.setMem_pwd(rs.getString("mem_pwd"));
				member.setMem_name(rs.getString("mem_name"));
				member.setMem_email(rs.getString("mem_email"));
				member.setMem_regdate(rs.getTimestamp("mem_regdate"));
				member.setMem_address(rs.getString("mem_address"));
			}
		}catch(SQLException ex){
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return member;
	}
	
	// 회원정보 수정 메서드 (UPDATE)
	public int updateMember(MemberBean member) throws Exception {
		int re=-1;
		Connection conn=null;
		PreparedStatement pstmt=null;
		String sql="UPDATE MEMBERT SET MEM_PWD=?, MEM_NAME=?, MEM_EMAIL=?, MEM_ADDRESS=? WHERE MEM_UID=?";
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			System.out.println("@@@### member.getMem_pwd() ===>"+member.getMem_pwd());
			System.out.println("@@@### member.getMem_name() ===>"+member.getMem_name());
			System.out.println("@@@### member.getMem_email() ===>"+member.getMem_email());
			System.out.println("@@@### member.getMem_address() ===>"+member.getMem_address());
			System.out.println("@@@### member.getMem_uid() ===>"+member.getMem_uid());
			
			pstmt.setString(1, member.getMem_pwd());
			pstmt.setString(2, member.getMem_name());
			pstmt.setString(3, member.getMem_email());
			pstmt.setString(4, member.getMem_address());
			pstmt.setString(5, member.getMem_uid());
			
			re = pstmt.executeUpdate(); //	SQL쿼리에 UPDATE사용시 필요
			System.out.println("@@@### re ===>"+re);
		}catch(SQLException ex){
			System.out.println("변경 실패");
			ex.printStackTrace();
		}finally{
			try{
				if(pstmt != null) pstmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return re;
	}
}














