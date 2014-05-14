package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.yiabi.puppy.restful.common.ServiceConstant;

public class ServletUtil {
	public static String getServletRequestBody(HttpServletRequest request) throws IOException {
		StringBuilder sb = new StringBuilder();
        String s = null;
        BufferedReader br = request.getReader();
        while ((s = br.readLine()) != null) {
            sb.append(s);
        }
        // do NOT close the reader here, or you won't be able to get the post data twice
        br.reset();

        br.close();
        return sb.toString();
	}
	
	public static void utf8Encoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		request.setCharacterEncoding("UTF-8");
    	
    	if (isSupportApplicationJson(request)) {
	        response.setContentType(ServiceConstant.CONTENT_TYPE);
	    } else {
	        // IE workaround
	        response.setContentType("text/plain; charset=UTF-8");
	    }
	}
	
	public static void setSessionAttribute(HttpServletRequest request, String attr, String value) {
		HttpSession session = request.getSession();
		if(session != null)
			session.setAttribute(attr, value);
	}
	
	private static boolean isSupportApplicationJson(HttpServletRequest request) {
		String header = request.getHeader("accept");
		if(header == null) // for apps
			return true;
		else {
			if (header.indexOf("application/json") != -1) {
		        return true;
		    } else {
		        // IE workaround
		        return false;
		    }
		}
	}
}
