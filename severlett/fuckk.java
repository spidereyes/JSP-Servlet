package severlett;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class fuckk
 */
//@WebServlet("/")
public class fuckk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fuckk() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String username = request.getParameter("uname");
//		String password = request.getParameter("pwd");
		Cookie c[] = request.getCookies();
		response.setHeader("Content-Type", "text/html");
		response.setContentType("text/html;charset=GBK");
		response.getWriter().println("method:" + request.getMethod() + "<br>");
		if(c!=null)
		{
			response.getWriter().println("c is not NULL");
			for(Cookie a : c)
			{
				response.getWriter().println(a.getName()+":"+a.getValue());
				if("username".equalsIgnoreCase(a.getName()))
				{
					response.setStatus(302);
					response.setHeader("location", "http://localhost:8080/test/fuck");
				}
			}
		}
		else
		{
			File file = new File("G:\\所有java项目和程序\\test\\WebContent\\index.html");
			FileReader reader = new FileReader(file);
			BufferedReader bufferReader = new BufferedReader(reader);
			String line = null; 
			while((line = bufferReader.readLine()) != null) {
				response.getWriter().print(line);
			}
//			response.getWriter().println("<!DOCTYPE html>\r\n" + 
//					"<html>\r\n" + 
//					"<head>\r\n" + 
//					"<meta charset=\"UTF-8\">\r\n" + 
//					"<title>Insert title here</title>\r\n" + 
//					"</head>\r\n" + 
//					"<body>\r\n" + 
//					"<form action=\"fuck\" method=\"post\">\r\n" + 
//					"<input type=\"username\" name=\"uname\"><br>\r\n" + 
//					"<input type=\"password\" name=\"pwd\"><br>\r\n" + 
//					"<input type=\"submit\">\r\n" + 
//					"</form>\r\n" + 
//					"</body>\r\n" + 
//					"</html>");
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
