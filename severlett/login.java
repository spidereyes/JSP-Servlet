package severlett;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AdminBean;
import utils.JdbcUtils;
/**
 * Servlet implementation class login
 */
@WebServlet(urlPatterns = {"/login"}, initParams= { @WebInitParam(name="logindispatcher", value="WEB-INF/Login.jsp") })
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String logindisp;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    @Override
    public void init() {
        logindisp = getInitParameter("logindispatcher");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(request.getSession().getAttribute("user") == null) 
		{
			response.setContentType("text/html; charset=utf-8");
			request.getRequestDispatcher("WEB-INF/Login.jsp").include(request, response);
		}
		else
		{
			response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		Cookie temp;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remember = request.getParameter("rememberme");
		AdminBean ab = new AdminBean();
		ab.setUsername(username);
		ab.setPassword(password);
		if("OK".equals(ab.BeanCheck()))
		{
			String[] values = { username };
			try {
				ArrayList pwdresult = JdbcUtils.executequery("select password, active from admin_ where username = ?", AdminBean.class, values);
				if(((AdminBean) pwdresult.get(0)).getActive() == 1)
				{
					if(password.equals(((AdminBean)pwdresult.get(0)).getPassword()))
					{
						request.getSession().setAttribute("user", username);
						if("seven".equals(remember))
						{
							Cookie[] cookies = request.getCookies();
							for(Cookie cookie:cookies)
							{
								if("JSESSIONID".equals(cookie.getName()))
								{
									temp = new Cookie("JSESSIONID", cookie.getValue());
									temp.setMaxAge(7*60*60*24);
									response.addCookie(temp);
									break;
								}
							}
						}
						response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/index");
					}
					else
					{
						request.setAttribute("errormsg", "账号或密码有误");
						request.getRequestDispatcher(logindisp).include(request, response);
					}
				}
				else
				{
					request.setAttribute("errormsg", "账号未激活");
					request.getRequestDispatcher(logindisp).include(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("errormsg", "账号有误");
				request.getRequestDispatcher(logindisp).include(request, response);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else 
		{
			request.setAttribute("errormsg", ab.BeanCheck());
			request.getRequestDispatcher(logindisp).include(request, response);
		}
	}
}
