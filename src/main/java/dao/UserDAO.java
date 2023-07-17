package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.User;

public class UserDAO extends DAO {

	public User search(String login, String password) {
		User user = null;

		try (Connection con = getConnection()) {

			PreparedStatement st = con.prepareStatement(
					"select * from User where login=? and password=?");
			st.setString(1, login);
			st.setString(2, password);

			try (ResultSet rs = st.executeQuery()) {

				while (rs.next()) {
					user = new User();
					user.setId(rs.getInt("id"));
					user.setLogin(rs.getString("login"));
					user.setPassword(rs.getString("password"));
					user.setStudentType(rs.getString("student"));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return user;
	}
}
