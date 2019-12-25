package severlett;
import java.io.*;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


import javax.servlet.http.Cookie;

/**
 * Servlet implementation class fuck
 */
@MultipartConfig
//@WebServlet("/fuck")
public class fuck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fuck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Cookie c[] = request.getCookies();
		if(c == null)
		{
			String new_url = new String("http://localhost:8080/test");
			response.setStatus(response.SC_TEMPORARY_REDIRECT);
			response.setHeader("location", new_url);
		}
		else
		{
			for(Cookie a : c) {
				if("username".equalsIgnoreCase(a.getName()))
				{
					response.getWriter().println("Hello "+a.getValue());
				}
			}
			response.getWriter().println("There are all cookies");
		}
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		String a = new String("http://localhost:8080/test/");
//		response.getWriter().println(request.getHeader("Referer") + "<br>");
//		response.getWriter().println(request.getHeader("Referer").startsWith(a) + "<br>");
//		if(request.getHeader("Referer").startsWith("http://localhost:8080/test/")){
		request.setCharacterEncoding("utf-8"); // 只对post方法使用
		String username = request.getParameter("uname");
		String password = request.getParameter("pwd");
		response.setContentType("text/html; charset=utf-8");
//		response.addCookie();
		Cookie a = new Cookie("username", username);
		a.setMaxAge(3);
		response.addCookie(a);
//		response.getWriter().println("host:" + request.getRemoteAddr() + "<br>");
//		response.getWriter().println("username:" + username + "<br> password:" + password + "<br>");
//		response.getWriter().println("cookie:" + request.getCookies() + "<br>");
//		response.getWriter().println("contextpath:" + request.getContextPath()+ "<br>");
//		response.getWriter().println("method:" + request.getMethod()+ "<br>");
//		response.getWriter().println("requesturl:" + request.getRequestURL()+ "<br>");
//		response.getWriter().println("servletpath:" + request.getServletPath() + "<br>");
//		}
//		else
//		{
//			Cookie a = new Cookie("result", "登陆成功");
//			response.addCookie(a);
//			String new_url = new String("http://localhost:8080/test/");
//			response.setStatus(response.SC_TEMPORARY_REDIRECT);
//			response.setHeader("location", new_url);
//		}
		Part photo = request.getPart("filename");
		System.out.print("photo ok");
		String filename = getSubmittedFileName(photo);
		System.out.print("filename ok");
		write(photo, filename);
		System.out.print("write ok");
		doGet(request,response);
	}
	
	private String getSubmittedFileName(Part part) {
		String header = part.getHeader("Content-Disposition");
		Matcher matcher = Pattern.compile("filename=\"(.*)\"").matcher(header);
		matcher.find();
		String filename = matcher.group(1);
		if(filename.contains("\\")) {
			filename = filename.substring(filename.lastIndexOf("\\") + 1);
		}
		if(filename.contains("/")) {
			filename = filename.substring(filename.lastIndexOf("/") + 1);
		}
		while(filename.contains(":")) {
			filename = filename.substring(0, filename.lastIndexOf(":")) + filename.substring(filename.lastIndexOf(":") + 1);
		}
		return filename;
	}
	
	private void write(Part photo, String filename) throws IOException, FileNotFoundException {
		try(InputStream in = photo.getInputStream();
				OutputStream out = new FileOutputStream(String.format("G:/所有java项目和程序/test/uploads/%s", filename))) {
			System.out.println(filename);
			byte[] buffer =new byte[10240];
			int length = -1;
			while((length = in.read(buffer)) != -1)
			{
				out.write(buffer, 0, length);
			}
		}
	}
}

