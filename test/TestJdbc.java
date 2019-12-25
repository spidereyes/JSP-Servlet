package test;

import static org.junit.jupiter.api.Assertions.*;

import utils.JdbcUtils;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.junit.jupiter.api.Test;

class TestJdbc {
	
	@Test
	void test() {
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			Connection con = JdbcUtils.getConnection();
			ps = con.prepareStatement("select * from admin_");
			rs = ps.executeQuery();
			while(rs.next()) {
				System.out.println(rs.getInt("password"));
				System.out.println(rs.getString("username"));
				System.out.println(rs.getString("password"));
				System.out.println(rs.getString(3));
			}
			JdbcUtils.free(rs, ps, con);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
