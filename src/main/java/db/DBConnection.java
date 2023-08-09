package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	public static void initConnection() {
		try { 
			// 1. lib 폴더에 추가한 라이브러리를 불러온다.
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loading Success"); // 드라이버 로딩 성공
		} catch (ClassNotFoundException e) {
			System.out.println("BD Driver를 찾지 못했습니다."); // 드라이버 로딩 실패
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() { // DB 접속 정보
		Connection conn = null;
		
		try {
			// 불러온 라이브러리의 getConnection() 메서드를 사용해 DB에 접속한다.
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/studydb", "root", "1234");
			                                  //DB 포로토콜    IP        PROT DB명       사용자명   비밀번호
			System.out.println("Connection Success"); // DB 연결 성공
		} catch (SQLException e) {
			System.out.println("DB를 연결하지 못했습니다."); // DB 연결 실패 
			e.printStackTrace();
		}
		return conn;
	}
}
