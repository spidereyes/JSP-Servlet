package dao;

import java.util.ArrayList;

import bean.AdminBean;

public interface AdminDAO {
	public ArrayList queryUser(String[] parameters, String[] values);
	
	public int addUser(AdminBean[] adminbs);
	
	public int deleteUsers(AdminBean[] adminbs);
	
	public int modifyUsers(AdminBean[] adminbs);
}