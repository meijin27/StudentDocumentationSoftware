package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

public class UserDAO extends DAO {

	public User search(String login) {
		User user = null;

		try (Connection con = getConnection()) {

			PreparedStatement st = con.prepareStatement(
					"select * from Users where account=?");
			st.setString(1, login);

			try (ResultSet rs = st.executeQuery()) {

				while (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setAccount(rs.getString("account"));
					user.setPassword(rs.getString("password"));
					user.setEncryptedKey(rs.getString("master_key"));
					user.setSecondEncryptedKey(rs.getString("second_master_key"));
					user.setIv(rs.getString("iv"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public int accountInsert(User user) throws Exception {
		Connection con = null;
		PreparedStatement st = null;
		int line = 0;
		try {
			con = getConnection();
			con.setAutoCommit(false);

			st = con.prepareStatement(
					"insert into users (account, password, master_key, iv) values(?, ?, ?, ?)");
			st.setString(1, user.getAccount());
			st.setString(2, user.getPassword());
			st.setString(3, user.getEncryptedKey());
			st.setString(4, user.getIv());

			line = st.executeUpdate();
			con.commit();

		} catch (Exception e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
			throw e;
		} finally {
			if (st != null) {
				try {
					st.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return line;
	}

	public void updateLastLogin(int userId) throws Exception {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(
					"UPDATE users SET last_login_at = CURRENT_TIMESTAMP WHERE id = ?");
			st.setInt(1, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void updatetime(int userId) throws Exception {
		try (Connection con = getConnection()) {
			PreparedStatement st = con.prepareStatement(
					"UPDATE users SET updated_at = CURRENT_TIMESTAMP WHERE id = ?");
			st.setInt(1, userId);
			st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void addOperationLog(int userId, String operation) throws SQLException {
		String sql = "INSERT INTO operation_logs (user_id, operation) VALUES (?, ?)";

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setInt(1, userId);
			pstmt.setString(2, operation);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
}