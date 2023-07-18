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
					user.setAccount(rs.getString("account"));
					user.setPassword(rs.getString("password"));
					user.setEncryptedKey(rs.getString("master_key"));
					user.setIv(rs.getString("iv"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}

	public int insert(User user) throws Exception {
		Connection con = getConnection();

		PreparedStatement st = con.prepareStatement(
				"insert into users (account, password, master_key, iv) values(?, ?, ?, ?)");
		st.setString(1, user.getAccount());
		st.setString(2, user.getPassword());
		st.setString(3, user.getEncryptedKey());
		st.setString(4, user.getIv());

		int line = st.executeUpdate();

		st.close();
		con.close();
		return line;
	}

}
