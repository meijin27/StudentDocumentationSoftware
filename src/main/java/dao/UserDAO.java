package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import bean.User;
import tool.CustomLogger;

public class UserDAO extends DAO {
	private static final Logger logger = CustomLogger.getLogger(UserDAO.class);

	@FunctionalInterface
	public interface SqlConsumer<T> {
		void accept(T t) throws SQLException;
	}

	public void executeSqlOperation(SqlConsumer<Connection> operation) {
		Connection con = null;
		try {
			con = getConnection();
			con.setAutoCommit(false);

			operation.accept(con);

			con.commit();
		} catch (SQLException e) {
			if (con != null) {
				try {
					con.rollback();
				} catch (SQLException e1) {
					logger.log(Level.SEVERE, "Failed to rollback transaction", e1);
					System.exit(1); // JVM を即座に終了させる
				}
			}
			logger.log(Level.SEVERE, "Database operation failed", e);
			throw new RuntimeException("Database operation failed", e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Failed to establish a connection", e);
			throw new RuntimeException("Failed to establish a connection", e);
		} catch (Error e) {
			logger.log(Level.SEVERE, "An unexpected error occurred", e);
			System.exit(1); // JVM を即座に終了させる
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					logger.log(Level.SEVERE, "Failed to close connection", e);
				}
			}
		}
	}

	public User loginSearch(String account) {
		AtomicReference<User> userRef = new AtomicReference<>();
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"select * from Users where account=? and is_deleted=FALSE")) {
				st.setString(1, account);
				try (ResultSet rs = st.executeQuery()) {
					if (rs.next()) {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setAccount(rs.getString("account"));
						user.setPassword(rs.getString("password"));
						user.setMasterKey(rs.getString("master_key"));
						user.setSecretQuestion(rs.getString("secret_question"));
						user.setBirthYear(rs.getString("birth_year"));
						user.setStudentType(rs.getString("student_type"));
						user.setIv(rs.getString("iv"));
						userRef.set(user);
					}
				}
			}
		});
		return userRef.get();
	}

	public User createSearch(String account) throws Exception {
		AtomicReference<User> userRef = new AtomicReference<>();
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement("select * from Users where account = ?")) {
				st.setString(1, account);
				try (ResultSet rs = st.executeQuery()) {
					if (rs.next()) {
						User user = new User();
						user.setId(rs.getString("id"));
						user.setAccount(rs.getString("account"));
						userRef.set(user);
					}
				}
			}
		});
		return userRef.get();
	}

	private String getField(String field, String id) {
		AtomicReference<String> ref = new AtomicReference<>();
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"select " + field + " from Users where id=? and is_deleted=FALSE")) {
				st.setString(1, id);
				try (ResultSet rs = st.executeQuery()) {
					if (rs.next()) {
						ref.set(rs.getString(field));
					}
				}
			}
		});
		return ref.get();
	}

	public int accountInsert(User user) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"insert into users (account, password, master_key, iv) values(?, ?, ?, ?)")) {
				st.setString(1, user.getAccount());
				st.setString(2, user.getPassword());
				st.setString(3, user.getMasterKey());
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
					"UPDATE users SET secret_question = ?, secret_answer = ?, second_master_key = ? WHERE id = ? and is_deleted=FALSE")) {
				st.setString(1, user.getSecretQuestion());
				st.setString(2, user.getSecretAnswer());
				st.setString(3, user.getSecondMasterKey());
				st.setString(4, user.getId());

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public int updateFirstSetting(User user) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"UPDATE users SET last_name = ?, first_name = ?, tel = ?, post_code = ?, address = ?, birth_year = ?, birth_month = ?, birth_day = ?, student_type = ?, class_name = ?, student_number = ?, school_year = ?, class_number = ? WHERE id = ? and is_deleted=FALSE")) {
				st.setString(1, user.getLastName());
				st.setString(2, user.getFirstName());
				st.setString(3, user.getTel());
				st.setString(4, user.getPostCode());
				st.setString(5, user.getAddress());
				st.setString(6, user.getBirthYear());
				st.setString(7, user.getBirthMonth());
				st.setString(8, user.getBirthDay());
				st.setString(9, user.getStudentType());
				st.setString(10, user.getClassName());
				st.setString(11, user.getStudentNumber());
				st.setString(12, user.getSchoolYear());
				st.setString(13, user.getClassNumber());
				st.setString(14, user.getId());

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public int updateVocationalTraineeSetting(User user) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"UPDATE users SET name_PESO = ?, supply_number = ?, attendance_number = ?, employment_insurance = ? WHERE id = ? and is_deleted=FALSE")) {
				st.setString(1, user.getNamePESO());
				st.setString(2, user.getSupplyNumber());
				st.setString(3, user.getAttendanceNumber());
				st.setString(4, user.getEmploymentInsurance());
				st.setString(5, user.getId());

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	private int updateField(String field, String value, String id) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"UPDATE users SET " + field + " = ? WHERE id = ? and is_deleted=FALSE")) {
				st.setString(1, value);
				st.setString(2, id);

				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public int accountDeleted(String userId) throws Exception {
		final int[] line = { 0 };
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"UPDATE Users SET is_deleted = TRUE WHERE id = ?")) {
				st.setString(1, userId);
				line[0] = st.executeUpdate();
			}
		});

		return line[0];
	}

	public void addLoginLog(String userId, String ipAddress) throws Exception {
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"INSERT INTO login_logs (user_id, login_time, ip_address) VALUES (?, CURRENT_TIMESTAMP, ?)")) {
				st.setString(1, userId);
				st.setString(2, ipAddress);
				st.executeUpdate();
			}
		});
	}

	public void addOperationLog(String userId, String operation) throws Exception {
		executeSqlOperation(con -> {
			try (PreparedStatement st = con.prepareStatement(
					"INSERT INTO operation_logs (user_id, operation) VALUES (?, ?)")) {
				st.setString(1, userId);
				st.setString(2, operation);
				st.executeUpdate();
			}
		});
	}

	// Getter methods

	public String getAccount(String id) {
		return getField("account", id);
	}

	public String getPassword(String id) {
		return getField("password", id);
	}

	public String getMasterKey(String id) {
		return getField("master_key", id);
	}

	public String getSecondMasterKey(String id) {
		return getField("second_master_key", id);
	}

	public String getIv(String id) {
		return getField("iv", id);
	}

	public String getSecretQuestion(String id) {
		return getField("secret_question", id);
	}

	public String getSecretAnswer(String id) {
		return getField("secret_answer", id);
	}

	public String getLastName(String id) {
		return getField("last_name", id);
	}

	public String getFirstName(String id) {
		return getField("first_name", id);
	}

	public String getTel(String id) {
		return getField("tel", id);
	}

	public String getPostCode(String id) {
		return getField("post_code", id);
	}

	public String getAddress(String id) {
		return getField("address", id);
	}

	public String getBirthYear(String id) {
		return getField("birth_year", id);
	}

	public String getBirthMonth(String id) {
		return getField("birth_month", id);
	}

	public String getBirthDay(String id) {
		return getField("birth_day", id);
	}

	public String getStudentType(String id) {
		return getField("student_type", id);
	}

	public String getClassName(String id) {
		return getField("class_name", id);
	}

	public String getStudentNumber(String id) {
		return getField("student_number", id);
	}

	public String getSchoolYear(String id) {
		return getField("school_year", id);
	}

	public String getClassNumber(String id) {
		return getField("class_number", id);
	}

	public String getNamePESO(String id) {
		return getField("name_PESO", id);
	}

	public String getAttendanceNumber(String id) {
		return getField("attendance_number", id);
	}

	public String getEmploymentInsurance(String id) {
		return getField("employment_insurance", id);
	}

	public String getSupplyNumber(String id) {
		return getField("supply_number", id);
	}

	// Setter methods
	public int updatePassword(User user) throws Exception {
		return updateField("password", user.getPassword(), user.getId());
	}

	public int updateMasterKey(User user) throws Exception {
		return updateField("master_key", user.getMasterKey(), user.getId());
	}

	public int updateSecondMasterKey(User user) throws Exception {
		return updateField("second_master_key", user.getSecondMasterKey(), user.getId());
	}

	public int updateSecretQuestion(User user) throws Exception {
		return updateField("secret_question", user.getSecretQuestion(), user.getId());
	}

	public int updateSecretAnswer(User user) throws Exception {
		return updateField("secret_answer", user.getSecretAnswer(), user.getId());
	}

	public int updateLastName(User user) throws Exception {
		return updateField("last_name", user.getLastName(), user.getId());
	}

	public int updateFirstName(User user) throws Exception {
		return updateField("first_name", user.getFirstName(), user.getId());
	}

	public int updateTel(User user) throws Exception {
		return updateField("tel", user.getTel(), user.getId());
	}

	public int updatePostCode(User user) throws Exception {
		return updateField("post_code", user.getPostCode(), user.getId());
	}

	public int updateAddress(User user) throws Exception {
		return updateField("address", user.getAddress(), user.getId());
	}

	public int updateBirthYear(User user) throws Exception {
		return updateField("birth_year", user.getBirthYear(), user.getId());
	}

	public int updateBirthMonth(User user) throws Exception {
		return updateField("birth_month", user.getBirthMonth(), user.getId());
	}

	public int updateBirthDay(User user) throws Exception {
		return updateField("birth_day", user.getBirthDay(), user.getId());
	}

	public int updateStudentType(User user) throws Exception {
		return updateField("student_type", user.getStudentType(), user.getId());
	}

	public int updateClassName(User user) throws Exception {
		return updateField("class_name", user.getClassName(), user.getId());
	}

	public int updateStudentNumber(User user) throws Exception {
		return updateField("student_number", user.getStudentNumber(), user.getId());
	}

	public int updateSchoolYear(User user) throws Exception {
		return updateField("school_year", user.getSchoolYear(), user.getId());
	}

	public int updateClassNumber(User user) throws Exception {
		return updateField("class_number", user.getClassNumber(), user.getId());
	}

	public int updateNamePESO(User user) throws Exception {
		return updateField("name_peso", user.getNamePESO(), user.getId());
	}

	public int updateAttendanceNumber(User user) throws Exception {
		return updateField("attendance_number", user.getAttendanceNumber(), user.getId());
	}

	public int updateEmploymentInsurance(User user) throws Exception {
		return updateField("employment_insurance", user.getEmploymentInsurance(), user.getId());
	}

	public int updateSupplyNumber(User user) throws Exception {
		return updateField("supply_number", user.getSupplyNumber(), user.getId());
	}

}
