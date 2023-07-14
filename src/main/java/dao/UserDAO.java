package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.User;

public class UserDAO extends DAO {

	public User search(String login, String password) throws Exception {
		User User = null;

		Connection con = getConnection();

		PreparedStatement st;

		st = con.prepareStatement(
				"select * from User where login=? and password=?");
		st.setString(1, login);
		st.setString(2, password);
		ResultSet rs = st.executeQuery();

		while (rs.next()) {
			User = new User();
			User.setId(rs.getInt("id"));
			User.setLogin(rs.getString("login"));
			User.setPassword(rs.getString("password"));
			User.setStudentType(rs.getString("student"));
		}

		st.close();
		con.close();

		return User;
	}
}
