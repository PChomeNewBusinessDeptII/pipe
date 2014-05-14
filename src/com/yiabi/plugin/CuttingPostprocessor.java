package com.yiabi.plugin;

public class CuttingPostprocessor {
	
	public static String postprocess(String html, String target) {
		if(target.indexOf("ipeen.com.tw") != -1) {
			html = html.replaceAll("<div>\\s*", MikePipe.NewLineStr).replaceAll("\\s*</div>", MikePipe.NewLineStr);
		}
		return html;
	}
}
