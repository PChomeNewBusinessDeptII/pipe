package tw.findbook;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.yiabi.puppy.restful.utility.URLUtil;

public class FindbookTw {
	public File downloadImage(String isbn, String dstFilePath) throws IOException {
		String url = "http://static.findbook.tw/image/book/" + isbn + "/large/ruten";
		URLUtil.saveAs(new URL(url), dstFilePath);
		File dst = new File(dstFilePath);
		if(dst.exists()) {
			return dst;
		} else {
			return null;
		}
	}
	
	public JSONObject getBasicInfo(String isbn)  throws IOException, JSONException {
		JSONObject info = new JSONObject();
		String url = "http://findbook.tw/book/" + isbn + "/basic";
		String html = URLUtil.getText(url);
		
		if(html!=null  &&  html.length()>0) {
			Document doc = Jsoup.parse(html);
			Elements bookProfile = doc.select(".book-profile");
			
			String bookName = bookProfile.select("h1").text();
			String cover = bookProfile.select(".cover").attr("src");
			
			String[] author_publisher_publishDate = bookProfile.select("p").eq(0).text().split(", ");
			String author = author_publisher_publishDate[0];
			String publisher = author_publisher_publishDate[1];
			String publishDate = author_publisher_publishDate[2];
			
			//String intro = bookProfile.select(".body div").text();
			
			info.put("name", bookName);
			info.put("cover", cover);
			info.put("author", author.split("：")[1]);
			info.put("publisher", publisher.split("：")[1]);
			info.put("publishDate", publishDate.split("：")[1]);
			info.put("isbn", isbn);
			//info.put("intro", intro);
			info.put("resultCode", 200);
		} else {
			info.put("resultCode", 500);
		}
		return info;
	}
}
