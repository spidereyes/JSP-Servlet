package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import dao.AdminDAO;
import dao.AdminDAOImp;
import bean.AdminBean;



class TestDAO {

	@Test
	void test() {
		AdminBean ab = new AdminBean();
		ab.setUsername("10041763");
		ab.setPassword("password");
		ab.setEmail("2643841145@qq.com");
//		ab.setIdentity(0);
//		ab.setIsforget("14523652");
		
		AdminBean ab2 = new AdminBean();
		ab2.setUsername("10041764");
		ab2.setPassword("passwodr");
//		ab2.setEmail("2643841145@qq.com");
//		ab2.setIdentity(0);
//		ab2.setIsforget("14523652");
		
//		AdminBean ab3 = new AdminBean();
//		ab3.setUsername("10041765");
		AdminBean[] abs = { ab, ab2 };
		AdminDAO adao = new AdminDAOImp();
//		String params = { "username" }
//		System.out.println(adao.modifyUsers(abs));
//		System.out.println(adao.addUser(abs));
		String[] parameters = {"username", "password"};
		String[] values = {"10041762", "passwd"};
		System.out.println(((AdminBean) adao.queryUser(null, values).get(0)).getUsername());
		System.out.println(((AdminBean) adao.queryUser(parameters, values).get(0)).getEmail());
	}

}
