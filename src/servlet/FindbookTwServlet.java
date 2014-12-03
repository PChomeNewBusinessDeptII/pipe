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

import tw.findbook.FindbookTw;

import com.yiabi.puppy.restful.common.ServiceConstant;


@WebServlet({"/findbookByIsbn"})
public class FindbookTwServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public FindbookTwServlet() {
    	super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject output = new JSONObject();
		
		ServletUtil.utf8Encoding(request, response);
		
		try {
			//JSONObject req = new JSONObject(ServletUtil.getServletRequestBody(request));
			String isbn = request.getParameter("isbn");
			output = new FindbookTw().getBasicInfo(isbn);
		} catch (JSONException e) {
			try {
				output.put("resultCode", ServiceConstant.INPUT_ERR);
			} catch (JSONException e1) {}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				output.put("resultCode", ServiceConstant.FAIL);
				output.put("errMsg", e.getMessage());
			} catch (JSONException e1) {}
		}
		
		PrintWriter out = response.getWriter();
		out.print(output);
		out.flush();
		out.close();
	}

}
