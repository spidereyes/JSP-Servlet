package dao;

import java.util.ArrayList;

import bean.AdminBean;

public class AdminDAOImp extends BaseDAO implements AdminDAO {

	/**
	 *  插入的每个豆子不需内容满满 
	*/
	@Override
	public int addUser(AdminBean[] adminbs) 
	{ 
		// TODO Auto-generated method stub
		String sql = "insert into admin_ (username, password, email, identity, isforget) values ";
		Object[] params = new Object[adminbs.length * 5 + 5];
		int j = 0;
		for(int i = 0; i < adminbs.length; i++)
		{
			params[j++] = adminbs[i].getUsername();
			params[j++] = adminbs[i].getPassword();
			params[j++] = adminbs[i].getEmail();
			params[j++] = adminbs[i].getIdentity();
			params[j++] = adminbs[i].getIsforget(); 
			sql += "(?, ?, ?, ?, ?)";
			if(i != adminbs.length-1)
				sql += ",";
		}
	    return IDUoperate(sql, params);
	}
	
	/**
	 * 把一堆任意属性为空的豆子作为参数
	*/
	@Override
	public int deleteUsers(AdminBean[] adminbs) 
	{
		String sql = "delete from admin_ where";
		Object[] params = new Object[adminbs.length * 5 + 5];
		int j = 0;
		for(int i = 0; i < adminbs.length; i++) {
			Boolean hasand = false;
			if(adminbs[i].getUsername() != null) {
				sql += " username = ? and";
				hasand = true;
				params[j++] = adminbs[i].getUsername();
			}
			if(adminbs[i].getPassword() != null) {
				sql += " password = ? and";
				hasand = true;
				params[j++] = adminbs[i].getPassword();
			}
			if(adminbs[i].getEmail() != null) {
				sql += " email = ? and";
				hasand = true;
				params[j++] = adminbs[i].getEmail();
			}
			if(adminbs[i].getIdentity() != -1) {
				sql += " identity = ? and";
				hasand = true;
				params[j++] = adminbs[i].getIdentity();
			}
			if(hasand)
				sql = sql.substring(0, sql.length()-3);
			if(i != adminbs.length - 1)
				sql += "or";
		}
		return IDUoperate(sql, params);
//		System.out.println(sql);
//		System.out.println("params: ");
//		for(int i = 0; i < params.length; i++)
//		{
//			System.out.print(params[i]);
//		}
	}
	
	/**
	 *  每个豆子通过主键找到记录并用该豆子修改其余信息，豆子没有主键不会进行修改
	*/
	@Override
	public int modifyUsers(AdminBean[] adminbs) 
	{
		// TODO Auto-generated method stub
		int allupdate = 0;
		for(int i = 0; i < adminbs.length; i++)
		{
			if(adminbs[i].getUsername() != null) // 每个豆子必须主键不为空否则不进行修改
			{
				int j = 0;
				Object[] params = new Object[5];
				Boolean hasdolt = false;
				String sql = "update admin_ set ";
				if(adminbs[i].getPassword() != null) {
					sql += " password = ?,";
					hasdolt = true;
					params[j++] = adminbs[i].getPassword();
				}
				if(adminbs[i].getEmail() != null) {
					sql += " email = ?,";
					hasdolt = true;
					params[j++] = adminbs[i].getEmail();
				}
				if(adminbs[i].getIdentity() != -1) {
					sql += " identity = ?,";
					hasdolt = true;
					params[j++] = adminbs[i].getIdentity();
				}
				if(adminbs[i].getIsforget() != null) {
					sql += " isforget = ?,";
					hasdolt = true;
					params[j++] = adminbs[i].getIsforget();
				}
				if(hasdolt) // ���޸�����
				{
					sql = sql.substring(0, sql.length()-1);
					sql += " where username = ?";
					params[j++] = adminbs[i].getUsername();
					allupdate += IDUoperate(sql, params);
				}
			}
		}
		return allupdate;
	}
	
	/**
	 *单表的查询，传入查询用的键值即可
	 */
	@Override
	public ArrayList queryUser(String[] parameters, String[] values)
	{
		String sql = "select * from admin_";
		Object[] params = {};
		if(parameters != null && values != null && parameters.length > 0 && values.length < 0) 
		{
			sql = "select * from admin_ where ";
			params = new Object[parameters.length * 2];
			int j = 0;
			for(int i = 0; i < parameters.length; i++)
			{
				sql += parameters[i] + " = ? ";
				if(i != parameters.length - 1)
					sql += " and ";
				params[j++] = values[i];
			}
		}
		System.out.println(sql);
		return queryObjs(sql, AdminBean.class, params);
	}
}
