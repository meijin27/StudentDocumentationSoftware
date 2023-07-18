package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {

	// DB接続用定数
	private static final String DATABASE_NAME = "StudentDocumentationSoftware";
	private static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;

	// DB接続用・ユーザ定数
	private static final String USER = "root";
	private static final String PASS = "";

	public Connection getConnection() {
		Connection conn = null;
		try {
			// MySQL に接続する
			Class.forName("com.mysql.cj.jdbc.Driver");
			// データベースに接続
			conn = DriverManager.getConnection(URL, USER, PASS);
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
