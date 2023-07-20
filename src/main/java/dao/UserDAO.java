package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;

import bean.User;

public class UserDAO extends DAO {

	@FunctionalInterface
	public interface SqlConsumer<T> {
		void accept(T t) throws SQLException;
	}

	public void executeSqlOperation(SqlConsumer<Connection> operation) throws Exception {
		Connection con = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);

			operation.accept(con);

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
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public User loginSearch(String login) {
		AtomicReference<User> userRef = new AtomicReference<>();
		try {
			executeSqlOperation(con -> {
				try (PreparedStatement st = con.prepareStatement(
						"select * from Users where account=?")) {
					st.setString(1, login);
					try (ResultSet rs = st.executeQuery()) {
						if (rs.next()) {
							User user = new User();
							user.setId(rs.getInt("id"));
							user.setAccount(rs.getString("account"));
							user.setPassword(rs.getString("password"));
							user.setEncryptedKey(rs.getString("master_key"));
							user.setSecretQuestion(rs.getString("secret_question"));
							user.setStudentType(rs.getString("student_type"));
							user.setIv(rs.getString("iv"));
							userRef.set(user);
						}
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

		return userRef.get();
	}

	public int accountInsert(User user) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"insert into users (account, password, master_key, iv) values(?, ?, ?, ?)")) {
				st.setString(1, user.getAccount());
				st.setString(2, user.getPassword());
				st.setString(3, user.getEncryptedKey());
				st.setString(4, user.getIv());

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public int updateSecret(User user) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"UPDATE users SET secret_question = ?, secret_answer = ? WHERE id = ?")) {
				st.setString(1, user.getSecretQuestion());
				st.setString(2, user.getSecretAnswer());
				st.setInt(3, user.getId());

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public void addLoginHistory(int userId) throws Exception {
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"INSERT INTO login_history (user_id, login_time) VALUES (?, CURRENT_TIMESTAMP)")) {
				st.setInt(1, userId);
				st.executeUpdate();
			}
		});
	}

	public void addOperationLog(int userId, String operation) throws Exception {
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"INSERT INTO operation_logs (user_id, operation) VALUES (?, ?)")) {
				st.setInt(1, userId);
				st.setString(2, operation);
				st.executeUpdate();
			}
		});
	}
}
