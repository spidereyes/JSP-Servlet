package dao;

import java.util.ArrayList;

import bean.AdminBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;

import utils.JdbcUtils;

public abstract class BaseDAO {
	
	public ArrayList queryObjs(String sql, Class classname, Object[] params)
	{
		ArrayList objlist = new ArrayList();
		PreparedStatement ps = null;
		ResultSet rs = null;
		Connection con = null;
		con = JdbcUtils.getConnection();
		try {
			ps = con.prepareStatement(sql);
			ParameterMetaData pm = ps.getParameterMetaData();
			for(int i = 1; i <= pm.getParameterCount(); i++)
				ps.setObject(i, params[i-1]);
			rs = ps.executeQuery();
			while(rs.next())
			{
				Object newinstance = InitObj(rs, classname);
				objlist.add(newinstance);
			}
			System.out.println(objlist);
			return objlist;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.free(rs, ps, con);
		}
		return null;
	}
	public int IDUoperate(String sql, Object[] params)
	{
		int modifycount = 0;
		Connection con = JdbcUtils.getConnection();
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(sql);
			ParameterMetaData pm = ps.getParameterMetaData();
			for(int i = 1; i <= pm.getParameterCount(); i++)
				ps.setObject(i, params[i-1]);
			modifycount = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JdbcUtils.free(null, ps, con);
		}
		return modifycount;
	}
	
	private Object InitObj(ResultSet rs, Class classname) throws SQLException {
		// TODO Auto-generated method stub
		Object newinstance;
		try {
			newinstance = classname.newInstance();
			Method[] methods = classname.getMethods();
			ResultSetMetaData rsmd = rs.getMetaData();
			for(int i = 1; i <= rsmd.getColumnCount(); i++)
			{
				String column = rsmd.getColumnLabel(i);
				column = column.substring(0, 1).toUpperCase().concat(column.substring(1).toLowerCase());
				String methodname = "set" + column;
				for(int j = 0; j < methods.length; j++)
				{
					if(methodname.equals(methods[j].getName()))
					{
						methods[j].invoke(newinstance, rs.getObject(i)); // 对新对象进行set操作
						break;
					}
				}
			}
			return newinstance;
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
