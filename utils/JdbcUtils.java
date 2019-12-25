package utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import bean.AdminBean;

public class JdbcUtils {
	private static DataSource myds = null;
	static{
		InputStream in = JdbcUtils.class.getClassLoader().getResourceAsStream("dbcpconfig.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			myds = BasicDataSourceFactory.createDataSource(prop);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		Connection con = null;
		try {
			con = myds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
	public static int executenoquery(String sql, String[] values) throws SQLException {
		Connection con = getConnection();
		PreparedStatement ps = con.prepareStatement(sql);
		ParameterMetaData pm = ps.getParameterMetaData();
		for(int i = 1; i <= pm.getParameterCount(); i++)
			ps.setString(i, values[i-1]);
		int num = ps.executeUpdate();
		free(null, ps, con);
		return num;
	}
	public static ArrayList executequery(String sql, Class classname, String[] values) throws SQLException {
		ArrayList objlist = new ArrayList();
		Connection con = getConnection();
		ResultSet rs = null;
		PreparedStatement ps = con.prepareStatement(sql);
		ParameterMetaData pm = ps.getParameterMetaData();
		for(int i = 1; i <= pm.getParameterCount(); i++)
			ps.setString(i, values[i-1]);
		rs = ps.executeQuery();
		while(rs.next())
		{
			Object newinstance = InitObj(rs, classname);
			objlist.add(newinstance);
		}
		free(rs, ps, con);
		return objlist;
	}
	public static void free(ResultSet rs, PreparedStatement ps, Connection con) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private static Object InitObj(ResultSet rs, Class classname) throws SQLException {
		Object instance;
		try {
			instance = classname.newInstance();
			Method[] mthd = instance.getClass().getMethods();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				String column = rsmd.getColumnLabel(i);
				column = column.substring(0, 1).toUpperCase().concat(column.substring(1).toLowerCase());
				System.out.println(column);
				String methodname = "set" + column;
				for(int j = 0; j < mthd.length; j++)
				{
					if(mthd[j].getName().equals(methodname))
					{
						System.out.println(methodname);
						mthd[j].invoke(instance, rs.getObject(i));
						break;
					}
				}
			}
			return instance;
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
