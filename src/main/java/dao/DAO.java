package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class DAO {
	public Connection getConnection() throws SQLException {
		// データベースに接続
		return ConnectionPool.getConnection();
	}
}