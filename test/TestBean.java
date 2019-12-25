package test;

import static org.junit.jupiter.api.Assertions.*;
import bean.BaseBean;
import bean.ShopinforBean;
import utils.OftenUse;

import org.junit.jupiter.api.Test;

class TestBean {

	@Test
	void test() {
		OftenUse ou = new OftenUse();
		String a = ou.getRandomString(8);
		String b = ou.getRandomString(8);
		System.out.println(a);
		System.out.println(b);
	}

}
