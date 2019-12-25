package severlett;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String confirmpwd = request.getParameter("cpassword");
		AdminBean ab = new AdminBean();
		ab.setUsername(username);
		ab.setPassword(password);
		if("OK".equals(ab.BeanCheck()) && password.equals(confirmpwd))
		{
			String[] values = { username };
			try 
			{
				ArrayListJdbcUtils.executequery("select password from admin_ where username = ?", AdminBean.class, values);
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
