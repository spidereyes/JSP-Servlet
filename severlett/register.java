package severlett;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.mail.Transport;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import bean.AdminBean;
import utils.MailMessage;
import utils.OftenUse;
import utils.JdbcUtils;

/**
 * Servlet implementation class register
 */
@WebServlet(urlPatterns = { "/register" }, initParams = { @WebInitParam(name = "dispatcher", value = "Choose.jsp") })
public class register extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private final String Users = "G:/saveuser/";
    private final String Successpath = "register_success.view";
    private final String Errorpath = "register_error.view";
    private final Pattern passwordreg = Pattern.compile("^\\w{8,16}$");
    private final Pattern usernamereg = Pattern.compile("^\\w{1,16}$");
    private final Pattern emailreg = Pattern.compile("^[_a-z0-9]+@[a-z0-9]+[.][a-z0-9]+$");
    private String Dispatcher;
    /**
     * @see HttpServlet#HttpServlet()
     */
    @Override
    public void init() throws ServletException {
        Dispatcher = getInitParameter("dispatcher");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stubgetParameter
		request.setAttribute("type", request.getParameter("type"));
		response.setContentType("text/html; charset=utf-8");
		request.getRequestDispatcher("/WEB-INF/Register.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String cpassword = request.getParameter("cpassword");
		if(password.equals(cpassword) && password != null)
		{
			AdminBean ab = new AdminBean();
			ab.setUsername(username);
			ab.setEmail(email);
			ab.setIdentity(1);
			ab.setPassword(password);
			String isforget = OftenUse.getRandomString(10);
			ab.setIsforget(isforget);
			if(!"OK".equals(ab.BeanCheck()))
			{
				request.setAttribute("msg", ab.BeanCheck());
				request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
			}
			else
			{
				int resultcount;
				String[] values = {ab.getUsername(), ab.getPassword(), Integer.toString(ab.getIdentity()), ab.getEmail(), ab.getIsforget()};
				try {
					resultcount = JdbcUtils.executenoquery("insert into admin_(username, password, identity, email, isforget) values(?,?,?,?,?)", values);
				} catch (SQLException e1) {
					request.setAttribute("msg", "您的账号与他人重复");
					request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				MailMessage mm = new MailMessage();
				mm.init("CUGB");
				String content = "<p>您正在通过"+ab.getEmail()+"邮箱注册账号"+ab.getUsername()+"，确认无误后请点击下面链接以激活账号</p>";
				content+="<a href=\"http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+
						"/realregister?un="+ab.getUsername()+"&token="+ab.getIsforget()+"\">激活账号</a>";
				try
				{
					Message msg = mm.createMessage(email, "注册成为新用户", content);
					Transport.send(msg);
					if(email.contains("@cugb.edu.cn"))
						response.sendRedirect("https://mail.cugb.edu.cn/");
					else if(email.contains("@qq.com"))
						response.sendRedirect("https://mail.qq.com/");
				}
				catch (MessagingException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		else 
		{
			 request.setAttribute("msg", "两次密码输入不一致！");
			 request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
		}
		if("1".equals(request.getParameter("type"))) 
		{
			
		}
	}
	
	private boolean validateEmail(String email)
	{
		return email != null && emailreg.matcher(email).find();
	}
	
	private boolean validateUsername(String username)
	{
		return username != null && username.length() >= 6 && username.length() <= 18;
	}
	
	private boolean validatePassword(String password, String cpassword)
	{
		return password != null && password.length() >= 6 && password.length() <= 18 && password.equals(cpassword);
	}
}
