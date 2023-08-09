package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DTO.UserDto;
import db.DBClose;
import db.DBConnection;

public class UserDao {
	// INSERT(추가)
	public static boolean insert(String id, String name, int age) {
		// 전송하고 실행할 쿼리(Query)
		String sql = "INSERT INTO USER(id, name, age)" + " values(?,?,?)";

		Connection conn = null;
		PreparedStatement psmt = null;

		int count = 0;

		try {
			// DB 접속(DB정보)
			conn = DBConnection.getConnection();

			// prepareStatement 객체의 인자에 전송/실행할 쿼리를 넣어준다.
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id); // set함수를 사용하여 쿼리(sql)의 '?'에 값을 세팅한다.
			psmt.setString(2, name);
			psmt.setInt(3, age);

			count = psmt.executeUpdate(); // 실행할 쿼리를 보낸 후 정수형 결과를 반환 받는다.
											// Insert, Update, Delete 쿼리를 실행하고 결과를 받아오려면 executeUpdate 메소드를 사용해야한다.
			System.out.println("Insert Success!");
		} catch (SQLException e) {
			System.out.println("Insert Fail!");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
			System.out.println("DBClose");
		}

		return count > 0 ? true : false; // 추가 성공 시 true를 반환하고 실패시 false를 반환한다.
	}

	// 삭제
	public static boolean delete(String id) {
		String sql = "DELETE FROM USER WHERE ID = ? ";

		Connection conn = null;
		PreparedStatement psmt = null;

		int count = 0;

		try {
			conn = DBConnection.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			count = psmt.executeUpdate();

			System.out.println("Delete Success!");
		} catch (SQLException e) {
			System.out.println("Delete Fail!");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
			System.out.println("DBClose");
		}

		return count > 0 ? true : false;
	}

	// 수정
	public static boolean update(String id, String name, int age) {

		String sql = " UPDATE USER " + " SET name = ?, age = ? " + " where id = ?";

		Connection conn = null;
		PreparedStatement psmt = null;

		int count = 0;

		try {
			conn = DBConnection.getConnection();

			psmt = conn.prepareStatement(sql);
			psmt.setString(1, name);
			psmt.setInt(2, age);
			psmt.setNString(3, id);

			count = psmt.executeUpdate();
			System.out.println("Update Success!");

		} catch (SQLException e) {
			System.out.println("Update Fail!");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, null);
			System.out.println("DBClose");
		}

		return count > 0 ? true : false;
	}

	// Select(검색)
	// 1개의 object 데이터를 취득할 경우 
	public static UserDto search(String id) {
		String sql = "SELECT *" + " FROM USER " + "WHERE ID = ?";

		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null; // 결과를 받아 저장하는 객체

		UserDto user = null; // user 정보를 담는 변수(UserDto)

		conn = DBConnection.getConnection();

		try {
			psmt = conn.prepareStatement(sql);
			psmt.setString(1, id);

			// SELECT 쿼리를 보내 실행 후 결과를 받아올 때 executeQuery() 메서드를 호출한다.
			// 반환 값(조회한 데이터)들을 ResultSet 타입 객체에 담는다.
			rs = psmt.executeQuery();

			// 반환 값이 있다면!
			if (rs.next()) { // 조회한 레코드의 각 칼럼 값을 받아 온다.
				String User_id = rs.getString("id");
				String User_name = rs.getString("name");
				int User_age = rs.getInt("age");
				String User_joindate = rs.getString("joindate");

				user = new UserDto(User_id, User_name, User_age, User_joindate); // 받아온 값을 UserDto 객체에 넣는다.
				System.out.println("reserch Success!");
			}
		} catch (SQLException e) {
			System.out.println("reserch Fail!");
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		return user; // UserDto 객체 반환 
	}
	
	// 다수의 데이터를 취득할 경우
	public static List<UserDto> select(){
		String sql = "SELECT * " + " FROM USER";
		
		Connection conn = null;
		PreparedStatement psmt = null;
		ResultSet rs = null;
		
		List<UserDto> list = new ArrayList<UserDto>(); // UserDto 객체 리스트
		
		conn = DBConnection.getConnection();
		
		try {
			psmt = conn.prepareStatement(sql);
			
			rs = psmt.executeQuery();
			
			// 받아올 값이 있을 때까지 반복
			while(rs.next()) { // 조회한 레코드의 각 칼럼 값을 받아 온다.
				String User_id = rs.getString(1);
				String User_name = rs.getString(2);
				int User_age = rs.getInt(3);
				String User_joindate = rs.getString(4);
				
				UserDto user = new UserDto(User_id, User_name, User_age, User_joindate); // 받아온 값을 UserDto 객체에 넣는다.
				
				list.add(user); // UserDto 객체를 리스트에 추가 한다.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBClose.close(conn, psmt, rs);
		}
		
		return list; // 리스트를 반환한다.
	}
}
