package bean;

import java.util.HashMap;

public class OrdersBean extends BaseBean {
	private int orderid;
	private String username;
	private HashMap<Integer, Integer> dishid2num = new HashMap<Integer, Integer>();
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public HashMap<Integer, Integer> getDishid2num() {
		return dishid2num;
	}
	public void setDishid2num(HashMap<Integer, Integer> dishid2num) {
		this.dishid2num = dishid2num;
	}
	@Override
	public String BeanCheck() {
		// TODO Auto-generated method stub
		if("".equals(username) || username == null)
			return "下单者不可为空";
		return "OK";
	}

}
