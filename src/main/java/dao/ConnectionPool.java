package dao;

import java.io.PrintWriter;
import java.io.StringWriter;
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
	// TomcatでJavaのシステムプロパティを設定する場合のコード
	private static final String DATABASE_NAME = "student_documentation_software";
	private static final String PROPATIES = "?characterEncoding=UTF-8&serverTimezone=Asia/Tokyo";
	private static final String DATABASE_URL = "jdbc:mysql://localhost/" + DATABASE_NAME + PROPATIES;
	private static final String DATABASE_USER = System.getProperty("DATABASE_USER");
	private static final String DATABASE_PASS = System.getProperty("DATABASE_PASS");

	private static HikariConfig config = new HikariConfig();
	private static HikariDataSource ds;

	static {
		try {
			config.setJdbcUrl(DATABASE_URL);
			config.setUsername(DATABASE_USER);
			config.setPassword(DATABASE_PASS);
			config.setDriverClassName("com.mysql.cj.jdbc.Driver");
			ds = new HikariDataSource(config);
		} catch (Exception e) {
			// ここでエラーハンドリングを行います
			// ログにエラーを記録したり、エラーを再スローしたりします
			logger.log(Level.SEVERE, "Failed to initialize the database connection pool", e);
			// スタックトレースをログに出力
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw));
			logger.severe(sw.toString());
			throw new ExceptionInInitializerError(e);
		}
	}

	private ConnectionPool() {
	}

	public static Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}
