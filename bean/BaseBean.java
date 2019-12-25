package bean;

import java.util.regex.Pattern;

public abstract class BaseBean {
	private Pattern emailreg = Pattern.compile("^[_a-z0-9]+@([a-z0-9]+[.][a-z0-9]+|[a-z0-9]+[.][a-z0-9]+[.][a-z0-9]+)$", Pattern.CASE_INSENSITIVE);
	private Pattern len116reg = Pattern.compile("^\\w{1,16}$");
    private Pattern len816reg = Pattern.compile("^\\w{8,16}$");
    private Pattern doublereg = Pattern.compile("^\\d+\\.\\d{0,2}$");
    private Pattern picturesreg = Pattern.compile("^[^\\:/]+\\.(jpg|jpeg|png)$", Pattern.CASE_INSENSITIVE);

    public boolean CheckEmail(String email) {
    	return email != null && emailreg.matcher(email).find();
    }
    public boolean CheckLen116(String str) {
    	return str != null && len116reg.matcher(str).find();
    }
    public boolean CheckLen816(String str) {
    	return str != null && len816reg.matcher(str).find();
    }    
    public boolean isDouble(String str) {
    	return str != null && doublereg.matcher(str).find();
    }
    public boolean CheckPicture(String str) {
    	return str != null && picturesreg.matcher(str).find();
    }
    public boolean CheckGender(String str) {
    	return str != null && (str.equals("男") || str.equals("女"));
    }
    public abstract String BeanCheck();
}
