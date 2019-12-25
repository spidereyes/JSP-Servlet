package bean;

public class DishesBean extends BaseBean {
	private int dishid;
	private String dishname;
	private String dishdesc;
	private String shopusername;
	private int dishsells;
	private String dishprice;
	private String dishcomment;
	private String dishpic1;
	private String dishpic2;
	private String dishpic3;
	private String dishtype1;
	private String dishtype2;
	private String dishtype3;
	
	public int getDishid() {
		return dishid;
	}

	public void setDishid(int dishid) {
		this.dishid = dishid;
	}
	public String getDishname() {
		return dishname;
	}

	public void setDishname(String dishname) {
		this.dishname = dishname;
	}

	public String getDishdesc() {
		return dishdesc;
	}

	public void setDishdesc(String dishdesc) {
		this.dishdesc = dishdesc;
	}

	public String getShopusername() {
		return shopusername;
	}

	public void setShopusername(String shopusername) {
		this.shopusername = shopusername;
	}

	public int getDishsells() {
		return dishsells;
	}

	public void setDishsells(int dishsells) {
		this.dishsells = dishsells;
	}

	public String getDishprice() {
		return dishprice;
	}

	public void setDishprice(String dishprice) {
		this.dishprice = dishprice;
	}

	public String getDishcomment() {
		return dishcomment;
	}

	public void setDishcomment(String dishcomment) {
		this.dishcomment = dishcomment;
	}

	public String getDishpic1() {
		return dishpic1;
	}

	public void setDishpic1(String dishpic1) {
		this.dishpic1 = dishpic1;
	}

	public String getDishpic2() {
		return dishpic2;
	}

	public void setDishpic2(String dishpic2) {
		this.dishpic2 = dishpic2;
	}

	public String getDishpic3() {
		return dishpic3;
	}

	public void setDishpic3(String dishpic3) {
		this.dishpic3 = dishpic3;
	}

	public String getDishtype1() {
		return dishtype1;
	}

	public void setDishtype1(String dishtype1) {
		this.dishtype1 = dishtype1;
	}

	public String getDishtype2() {
		return dishtype2;
	}

	public void setDishtype2(String dishtype2) {
		this.dishtype2 = dishtype2;
	}

	public String getDishtype3() {
		return dishtype3;
	}

	public void setDishtype3(String dishtype3) {
		this.dishtype3 = dishtype3;
	}
	@Override
	public String BeanCheck() {
		// TODO Auto-generated method stub
		if("".equals(dishname)||"".equals(dishdesc)||"".equals(shopusername))
			return "菜品名称、描述、商铺名字段不可为空";
		if(!super.isDouble(dishprice))
			return "菜品价格必须为小数";
		if(!super.CheckPicture(dishpic1) || (dishpic2 != null && !super.CheckPicture(dishpic2) || (dishpic3 != null && !super.CheckPicture(dishpic3))))
			return "请检查第一张图片是否上传及图片格式是否正确";
		return "OK";
	}

}
