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

import com.yiabi.plugin.MikePipe;
import com.yiabi.puppy.restful.common.ServiceConstant;


@WebServlet({"/cuttingpipe"})
public class CuttingPipeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public CuttingPipeServlet() {
    	super();
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JSONObject output = new JSONObject();
		
		ServletUtil.utf8Encoding(request, response);
		
		try {
			JSONObject req = new JSONObject(ServletUtil.getServletRequestBody(request));
			String html = req.getString("html");
			String url = req.getString("url");
			String selector = req.has("selector") ? req.getString("selector") : null; // ex: jquery selector
			System.out.printf("/pipe/cuttingpipe Receive request from /pus with url=[%s],selecttor=[%s]\n", url, selector);
			
			String bookContent = MikePipe.filterCM(html, url, selector);
			
			output.put("bookContent", bookContent);
			output.put("resultCode", ServiceConstant.SUCCESS);
			
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				output.put("resultCode", ServiceConstant.INPUT_ERR);
				output.put("errMsg", e.getMessage());
			} catch (JSONException e1) {}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		PrintWriter out = response.getWriter();
		out.print(output);
		out.flush();
		out.close();
	}

}
