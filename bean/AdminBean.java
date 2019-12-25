package bean;

public class AdminBean extends BaseBean {
	private String username;
	private String password;
	private int identity = -1;
	private String email;
	private String isforget;
	private int active;
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getIdentity() {
		return identity;
	}
	public void setIdentity(int indentity) {
		this.identity = indentity;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIsforget() {
		return isforget;
	}
	public void setIsforget(String isforget) {
		this.isforget = isforget;
	}
	
	@Override
	public String BeanCheck()
	{
		if(!super.CheckLen116(username))
			return "账号格式有误";
		if(!super.CheckLen816(password))
			return "密码格式有误";
		if(email!=null&&!super.CheckEmail(email))
			return "邮箱格式有误";
		return "OK";
	}
}
