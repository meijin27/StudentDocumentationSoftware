package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import tool.CustomLogger;

public class ConnectionPool {
	private static final Logger logger = CustomLogger.getLogger(ConnectionPool.class);

	// DB接続用定数
	private static final String DATABASE_NAME = "student_documentation_software";
	private static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String URL = "jdbc:mySQL://localhost/" + DATABASE_NAME + PROPATIES;
	private static final String USER = "root";
	private static final String PASS = "";

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		try {
			config.setJdbcUrl(URL);
			config.setUsername(USER);
			config.setPassword(PASS);
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");
			ds = new HikariDataSource(config);
		} catch (Exception e) {
			// ここでエラーハンドリングを行います
			// ログにエラーを記録したり、エラーを再スローしたりします
			logger.log(Level.SEVERE, "Failed to initialize the database connection pool", e);
			throw new ExceptionInInitializerError(e);
		}
	}

	private ConnectionPool() {
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
