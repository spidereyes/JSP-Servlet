package bean;

public class ShopinforBean extends BaseBean {
	private String username;
	private String shopname;
	private String shopavatar;
	private String shoplocation;
	private String shopdesc;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getShopavatar() {
		return shopavatar;
	}

	public void setShopavatar(String shopavatar) {
		this.shopavatar = shopavatar;
	}

	public String getShoplocation() {
		return shoplocation;
	}

	public void setShoplocation(String shoplocation) {
		this.shoplocation = shoplocation;
	}

	public String getShopdesc() {
		return shopdesc;
	}

	public void setShopdesc(String shopdesc) {
		this.shopdesc = shopdesc;
	}

	@Override
	public String BeanCheck() {
		// TODO Auto-generated method stub
		if("".equals(username) || "".equals(shopname) || "".equals(shoplocation) || "".equals(shopdesc))
			return "�����˺š����ơ�λ�á���������Ϊ��";
		if(shopavatar==null || !super.CheckPicture(shopavatar))
			return "����ͼƬ��ʽ����";
		return "OK";
	}

}
