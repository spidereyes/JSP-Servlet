package bean;

public class CollectionsBean extends BaseBean {
	private String username;
	private int dishid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getDishid() {
		return dishid;
	}
	public void setDishid(int dishid) {
		this.dishid = dishid;
	}
	@Override
	public String BeanCheck() {
		// TODO Auto-generated method stub
		if(username == null || "".equals(username))
			return "收藏者的账号不可为空";
		return null;
	}

}
