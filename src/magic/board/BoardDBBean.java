package magic.board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDBBean {
	//생성자를 priavte로 만들어 접근을 막는다
	private static BoardDBBean instance=new BoardDBBean();
					//최초 한번만 new 연산자를 통하여 메모리에 할당
	public static BoardDBBean getInstance() {// getInstance(): 객체생성(최초에 할당된 하나의 메모리를 계속 씀//new랑 다름)
		return instance;
		//getInstance 메소드를 통해 한번만 생성된 객체를 가져온다.
	}
	
	//데이터베이스 연결
	public Connection getConnection() throws Exception {//throws Exception: 예외 떠 넘기기
		Context ctx = new InitialContext();
		DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/oracle");
		return ds.getConnection();
	}
	
	public int insertBoard(BoardBean board) throws Exception {
		int re=-1; // 결과값 분기처리 하기 위해 변수 선언			
		Connection conn=null; //dbcp 연결위한 참조변수 선언
		PreparedStatement pstmt=null; //SQL문 사용하기 위한 참조변수 선언
		ResultSet rs=null; //쿼리 결과값 받기 위한 참조변수 선언
//		String sql="INSERT INTO boardt VALUES(?,?,?,?)";
		String sql="";
		int number;
		int id = board.getB_id();
		int ref = board.getB_ref();
		int step = board.getB_step();
		int level = board.getB_level();
		
		try {
			conn = getConnection();
			sql = "select max(b_id) from boardt";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				number = rs.getInt(1)+1;
			} else {
				number = 1;
			}
			
			if (id != 0) {
				sql = "UPDATE BOARDT SET b_step=b_step+1 WHERE b_ref=? AND b_step > ?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, ref);
				pstmt.setInt(2, step);
				pstmt.executeUpdate();
				step=step+1;
				level=level+1;
			} else {
				ref=number;
				step=0;
				level=0;
			}
//			System.out.println("@@@### number ===>"+number);
//			System.out.println("@@@### board.getB_name() ===>"+board.getB_name());
//			System.out.println("@@@### board.getB_email() ===>"+board.getB_email());
//			System.out.println("@@@### board.getB_title() ===>"+board.getB_title());
//			System.out.println("@@@### board.getB_content() ===>"+board.getB_content());
//			System.out.println("@@@### board.getB_date() ===>"+board.getB_date());
//			System.out.println("@@@### board.getB_hit() ===>"+board.getB_hit());
//			System.out.println("@@@### board.getB_pwd() ===>"+board.getB_pwd());
			
//			sql="INSERT INTO boardt VALUES(?,?,?,?,?,?,?,?,?)";
			sql="INSERT INTO boardt VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, number);
			pstmt.setString(2, board.getB_name());
			pstmt.setString(3, board.getB_email());
			pstmt.setString(4, board.getB_title());
			pstmt.setString(5, board.getB_content());
			pstmt.setTimestamp(6, board.getB_date());
			pstmt.setInt(7, board.getB_hit());
			pstmt.setString(8, board.getB_pwd());
			pstmt.setString(9, board.getB_ip());
			pstmt.setInt(10, ref);
			pstmt.setInt(11, step);
			pstmt.setInt(12, level);
			pstmt.executeUpdate();
			
			re=1;
		}catch(SQLException ex){
			System.out.println("�߰� ����");
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
	
//	public ArrayList<BoardBean> listBoard() throws Exception{
	public ArrayList<BoardBean> listBoard(String pageNumber) throws Exception{
		Connection conn=null;
		Statement stmt=null;
		ResultSet rs=null;
		ResultSet pageSet=null;
		
		int dbCount=0;
		int absolutePage=1;
		
		String db_mem_pwd;
		String sql=" SELECT b_id \r\n" + 
					"     , b_name\r\n" + 
					"     , b_email\r\n" + 
					"     , b_title\r\n" + 
					"     , b_content\r\n" + 
					"     , b_date\r\n" + 
					"     , b_hit\r\n" + 
					"     , b_pwd\r\n" + 
					"     , b_ip\r\n" + 
					"     , b_ref\r\n" + 
					"     , b_step\r\n" + 
					"     , b_level\r\n" + 
					"  FROM BOARDT\r\n" + 
					" ORDER BY b_ref desc, b_step asc";
		String sql2="SELECT count(b_id) FROM BOARDT";
		ArrayList<BoardBean> boardList = new ArrayList<BoardBean>();
		
		try {
			conn = getConnection();
//			stmt = conn.createStatement();
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			pageSet = stmt.executeQuery(sql2);
			
			if (pageSet.next()) {
				dbCount = pageSet.getInt(1);
				pageSet.close();
			}
			
			if (dbCount % BoardBean.pageSize == 0) {	//80 % 10 = 0
				BoardBean.pageCount = dbCount / BoardBean.pageSize;	// 80 / 10
			} else {	//84 % 10 = 4
				BoardBean.pageCount = dbCount / BoardBean.pageSize + 1;	// 80 / 10 + 1
			}
			
			if (pageNumber != null) {
				BoardBean.pageNum = Integer.parseInt(pageNumber);
				absolutePage = (BoardBean.pageNum - 1) * BoardBean.pageSize + 1;
			}
			
			rs = stmt.executeQuery(sql);
			
			if (rs.next()) {
				rs.absolute(absolutePage);
				int count = 0;
	//			while (rs.next()) {
					while (count < BoardBean.pageSize) {
						BoardBean board = new BoardBean();
						
						board.setB_id(rs.getInt(1));
						board.setB_name(rs.getString(2));
						board.setB_email(rs.getString(3));
						board.setB_title(rs.getString(4));
						board.setB_content(rs.getString(5));
						board.setB_date(rs.getTimestamp(6));
						board.setB_hit(rs.getInt(7));
						board.setB_pwd(rs.getString(8));
						board.setB_ip(rs.getString(9));
						board.setB_ref(rs.getInt(10));
						board.setB_step(rs.getInt(11));
						board.setB_level(rs.getInt(12));
						
						boardList.add(board);
						
						if (rs.isLast()) {
							break;
						} else {
							rs.next();
						}
						
						count++;
					}
			}
		}catch(SQLException ex){
			System.out.println("��ȸ ����");
			ex.printStackTrace();
		}finally{
			try{
				if(rs != null) rs.close();
				if(stmt != null) stmt.close();
				if(conn != null) conn.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return boardList;
	}
	
	public BoardBean getBoard(int bid, boolean hitadd) throws Exception {
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		BoardBean board=null;
		
		String sql="";
		try {
			conn = getConnection();
			
			if (hitadd == true) {
				sql="UPDATE BOARDT SET b_hit=b_hit+1 WHERE b_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, bid);
				pstmt.executeUpdate();
			}
			
			sql=" SELECT b_id \r\n" + 
			"     , b_name\r\n" + 
			"     , b_email\r\n" + 
			"     , b_title\r\n" + 
			"     , b_content\r\n" + 
			"     , b_date\r\n" + 
			"     , b_hit\r\n" + 
			"     , b_pwd\r\n" + 
			"     , b_ip\r\n" +
			"     , b_ref\r\n" + 
			"     , b_step\r\n" + 
			"     , b_level\r\n" +
			"  FROM BOARDT\r\n" + 
			" WHERE b_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, bid);
			rs = pstmt.executeQuery();
			
			//while or if
			if (rs.next()) {
				board = new BoardBean();
				board.setB_id(rs.getInt(1));
				board.setB_name(rs.getString(2));
				board.setB_email(rs.getString(3));
				board.setB_title(rs.getString(4));
				board.setB_content(rs.getString(5));
				board.setB_date(rs.getTimestamp(6));
				board.setB_hit(rs.getInt(7));
				board.setB_pwd(rs.getString(8));
				board.setB_ip(rs.getString(9));
				board.setB_ref(rs.getInt(10));
				board.setB_step(rs.getInt(11));
				board.setB_level(rs.getInt(12));
			}
		}catch(SQLException ex){
			System.out.println("��ȸ ����");
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
		
		return board;
	}
	
	public int deleteBoard(int b_id, String b_pwd) throws Exception {
		int re=-1;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		String pwd="";
		
		try {
			conn = getConnection();
			sql = "SELECT b_pwd FROM BOARDT WHERE b_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, b_id);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pwd = rs.getString(1);
				
				if (pwd.equals(b_pwd)) {
					sql = "DELETE FROM BOARDT WHERE b_id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setInt(1, b_id);
					pstmt.executeUpdate();
					re=1;
				} else {
					re=0;
				}
			}
		}catch(SQLException ex){
			System.out.println("���� ����");
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

	public int editBoard(BoardBean board) throws Exception {
		int re=-1;
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		String sql="";
		String pwd="";
		
		try {
			conn = getConnection();
			sql = "SELECT b_pwd FROM BOARDT WHERE b_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, board.getB_id());
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				pwd = rs.getString(1);
				
				if (pwd.equals(board.getB_pwd())) {
					sql = "UPDATE BOARDT SET b_name=?, b_email=?, b_title=?, b_content=? WHERE b_id=?";
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, board.getB_name());
					pstmt.setString(2, board.getB_email());
					pstmt.setString(3, board.getB_title());
					pstmt.setString(4, board.getB_content());
					pstmt.setInt(5, board.getB_id());
					pstmt.executeUpdate();
					re=1;
				} else {
					re=0;
				}
			}
		}catch(SQLException ex){
			System.out.println("���� ����");
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











