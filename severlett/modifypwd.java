package severlett;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.AdminBean;
import utils.JdbcUtils;
/**
 * Servlet implementation class modifypwd
 */
@WebServlet("/modifypwd")
public class modifypwd extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public modifypwd() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		request.getRequestDispatcher("WEB-INF/Modifypwd.jsp").include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		String username = request.getParameter("username");
		String oldpassword = request.getParameter("opassword");
		String newpassword = request.getParameter("npassword");
		String confirmpwd = request.getParameter("cpassword");
		AdminBean ab = new AdminBean();
		ab.setUsername(username);
		ab.setPassword(newpassword);
		if("OK".equals(ab.BeanCheck()) && newpassword.equals(confirmpwd))
		{
			String[] values = { username };
			try 
			{
				ArrayList abs = JdbcUtils.executequery("select password from admin_ where username = ?", AdminBean.class, values);
				String pwd = ((AdminBean) abs.get(0)).getPassword();
				if(oldpassword.equals(pwd))
				{
					String[] values1 = { newpassword, username };
					JdbcUtils.executenoquery("update admin_ set password = ? where username = ?", values1);
					Cookie msg = new Cookie("msg", "修改成功");
					msg.setMaxAge(5);
					Cookie defaultusername = new Cookie("user", username);
					defaultusername.setMaxAge(5);
					response.addCookie(msg);
					response.addCookie(defaultusername);
					response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/login");
				}
				else 
				{
					request.setAttribute("msg", "您的账号或密码有误");
					request.getRequestDispatcher("WEB-INF/Modifypwd.jsp").include(request, response);
				}
			} catch (Exception e) {
				request.setAttribute("msg", "您的账号有误");
				request.getRequestDispatcher("WEB-INF/Modifypwd.jsp").include(request, response);
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			request.setAttribute("msg", "请按正确格式输入内容");
			request.getRequestDispatcher("WEB-INF/Modifypwd.jsp").include(request, response);
		}
	}

}
