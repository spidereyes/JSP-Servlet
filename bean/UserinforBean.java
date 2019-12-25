package bean;

public class UserinforBean extends BaseBean {
	private String username;
	private String nickname;
	private String gender;
	private String avatar;
	private String location;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String BeanCheck() {
		// TODO Auto-generated method stub
		if("".equals(username) || "".equals(nickname))
			return "�˺ż��ǳƲ���Ϊ��";
		if(!super.CheckGender(gender))
			return "�Ա����Ϊ�л�Ů";
		if(avatar != null && !super.CheckPicture(avatar))
			return "ͼƬ��ʽ����";
		return "OK";
	}

}
