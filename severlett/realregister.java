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

import utils.JdbcUtils;
import bean.AdminBean;
/**
 * Servlet implementation class realregister
 */
@WebServlet("/realregister")
public class realregister extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public realregister() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		String[] values = { request.getParameter("un") };
		try 
		{
			ArrayList obj = JdbcUtils.executequery("select isforget from admin_ where username = ?", AdminBean.class, values);
			String isforget = ((AdminBean)obj.get(0)).getIsforget();
			if(isforget != null && isforget.equals(request.getParameter("token")))
			{
				JdbcUtils.executenoquery("update admin_ set active = 1 where username = ?", values);
				Cookie c = new Cookie("msg", "激活成功");
				Cookie user = new Cookie("user", request.getParameter("un"));
				c.setMaxAge(5);
				user.setMaxAge(5);
				response.addCookie(c);
				response.addCookie(user);
				response.sendRedirect("http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/login");
			}
			else 
			{
				request.setAttribute("msg", "您的账号不匹配");
				request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
			}
		} 
		catch (SQLException e)
		{
			// TODO Auto-generated catch block
			request.setAttribute("msg", "您的账号有误");
			request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
			e.printStackTrace();
		}
		catch (Exception e)
		{
			request.setAttribute("msg", "您的账号有误");
			request.getRequestDispatcher("WEB-INF/Register.jsp").include(request, response);
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
