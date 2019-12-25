package severlett;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class xsstest
 */
@WebServlet("/xsstest")
public class xsstest extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public xsstest() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		String name = request.getParameter("name");
//		String name = Optional.ofNullable(request.getParameter("name")).map(value -> value.replaceAll("<", "&lt;")).map(value -> value.replaceAll(">", "&gt;")).orElse("Guest");
		PrintWriter out = response.getWriter();
		out.print("<!DOCTYPE html>");
		out.print("<html>");
		out.print("<head>");
		out.print("<title>hello</title>");
		out.print("</head>");
		out.print("<body>");
		out.printf("<h1>Hello! %s!</h1>", name);
		out.print("</body>");
		out.print("</html>");
		ArrayList a = Collections.list(request.getHeaderNames());
		for(Object name1: a) {
			out.printf("%s: %s<br>", name1, request.getHeader((String)name1));
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
