package severlett;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.mail.Message;
import javax.mail.Transport;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AdminBean;
import utils.JdbcUtils;
import utils.MailMessage;
import utils.OftenUse;
/**
 * Servlet implementation class forgetpwd
 */
@WebServlet(urlPatterns = {"/forgetpwd"}, initParams = { @WebInitParam(name="forgetpwdjsp", value="WEB-INF/Forgetpwd.jsp"),
		@WebInitParam(name="canforgetpwdjsp", value="WEB-INF/Forgetpwdcan.jsp") })
public class forgetpwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String Dispatcher;
    private String canDispatcher;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public forgetpwd() {
        super();
        // TODO Auto-generated constructor stub
    }
    @Override
    public void init() throws ServletException {
        Dispatcher = getInitParameter("forgetpwdjsp");
        canDispatcher = getInitParameter("canforgetpwdjsp");
        // TODO Auto-generated constructor stub
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		if(request.getParameter("un") != null && request.getParameter("token") != null) 
		{
			String[] values = { request.getParameter("un") };
			try {
				ArrayList obj = JdbcUtils.executequery("select isforget from admin_ where username = ?", AdminBean.class, values);
				if(request.getParameter("token").equals(((AdminBean)obj.get(0)).getIsforget())) 
				{
					request.setAttribute("token", request.getParameter("token"));
					request.setAttribute("defaultusername", request.getParameter("un"));
					request.getRequestDispatcher(canDispatcher).include(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				request.getRequestDispatcher(Dispatcher).include(request, response);
				e.printStackTrace();
			}
		}
		else
		{
			request.getRequestDispatcher(Dispatcher).include(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		if("modify".equals(request.getParameter("type"))) 
		{
			String username = request.getParameter("username");
			String token = request.getParameter("token");
			String[] values = { token };
			try {
				ArrayList obj = JdbcUtils.executequery("select username from admin_ where isforget = ?", AdminBean.class, values);
				if(request.getParameter("username").equals(((AdminBean)obj.get(0)).getUsername())) {
					if(request.getParameter("npassword").length()<=18&&request.getParameter("npassword").length()>=8
							&&request.getParameter("npassword").equals(request.getParameter("cpassword"))) {
						String newtoken = OftenUse.getRandomString(10);
						String[] values1 = { request.getParameter("npassword"), newtoken, username };
						JdbcUtils.executenoquery("update admin_ set password = ?, isforget = ? where username = ?", values1);
						Cookie c = new Cookie("msg", "找回密码成功");
						Cookie user = new Cookie("user", request.getParameter("username"));
						c.setMaxAge(5);
						user.setMaxAge(5);
						response.addCookie(c);
						response.addCookie(user);
						response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/login");
					}
					else {
						request.setAttribute("msg", "输入的格式有误");
						request.setAttribute("defaultusername", request.getParameter("username"));
						request.setAttribute("token", token);
						request.getRequestDispatcher(canDispatcher).include(request, response);
					}
				}
				else {
					request.setAttribute("msg", "账号有误");
					request.setAttribute("defaultusername", request.getParameter("username"));
					request.setAttribute("token", token);
					request.getRequestDispatcher(canDispatcher).include(request, response);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/forgetpwd");
				e.printStackTrace();
			}
		}
		else 
		{
			String username = request.getParameter("username");
			String[] values = { username };
			try {
				String newforget = OftenUse.getRandomString(10);
				String[] values1 = { newforget, username };
				JdbcUtils.executenoquery("update admin_ set isforget = ? where username = ?", values1);
				ArrayList obj = JdbcUtils.executequery("select username, email, isforget from admin_ where username = ?", AdminBean.class, values);
				AdminBean ab = (AdminBean)obj.get(0);
				MailMessage mm = new MailMessage();
				mm.init("CUGB");
				String content = "<p>账号"+ab.getUsername()+"正在通过"+ab.getEmail()+"邮箱找回密码，请确认无误后点击下面链接</p>";
				content+="<a href=\"http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+
						"/forgetpwd?un="+ab.getUsername()+"&token="+ab.getIsforget()+"\">找回密码</a>";
				Message msg = mm.createMessage(ab.getEmail(), "找回密码", content);
				Transport.send(msg);
				if(ab.getEmail().contains("@qq.com"))
				{
					response.sendRedirect("https://mail.qq.com/");
				}
				else if(ab.getEmail().contains("@cugb.edu.cn"))
				{
					response.sendRedirect("https://mail.cugb.edu.cn/");
				}
			} catch (Exception e) {
				request.setAttribute("msg", "您的账号未设置邮箱");
				request.getRequestDispatcher(Dispatcher).include(request, response);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
