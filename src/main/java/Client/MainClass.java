package Client;

import java.util.ArrayList;
import java.util.List;

import DAO.UserDao;
import DTO.UserDto;
import db.DBConnection;

public class MainClass {
	public static void main(String[] args) {
		// 1. DB Driver Loading
		DBConnection.initConnection();

		// INSERT
		// UserDao.insert("son7", "손흥민", 30);
		
		// UPDATE
		// UserDao.update("son7", "Sony", 20);
		
		// DELETE
		// UserDao.delete("son7");

		// SELECT
		// 단일 데이터(object)
		// UserDto user = UserDao.search("son7");
		// System.out.println(user);

		// 다중 데이터(List)
		List<UserDto> list = UserDao.select();

		for (UserDto user : list) {
			System.out.println(user.toString());
		}

	}
}
