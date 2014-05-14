package com.yiabi.plugin;

import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;
import de.l3s.boilerpipe.extractors.KeepEverythingExtractor;

// =====================================================================
// Purpose: 	JY's degin: filter some html tag to improve cutting
// Parameters:
// Return:
// Remark:		
// Author:		JY, Bryan
// =====================================================================
//http://mypaper.pchome.com.tw/zen/post/1327455704

public class MikePipe {
	public static final String NewLineStr = "xYBx";
	public static String filterCM (String rawHtml, String target, String selector){
		
		if(rawHtml == null) {
			return "";
		}
    	
		
		boolean isEnableResultLineTrimming = false; // this will make result more compact
		String TotalHTML = "";
		String OgText = null;
		
		String ParsedText = null;
		target = target.toLowerCase();
		
		Document doc = Jsoup.parse(rawHtml);
		
		Elements metaOgDesc = doc.select("meta[property=og:description]");
	    if (metaOgDesc.size()>0) {
	    	OgText = metaOgDesc.attr("content");
	    	//System.err.println(OgText);
	    } else {
	    	//System.err.println("No [Open Graph Protocol] (meta og:xxxx ) SEO !! \n");
	    }
	    
	    /*
	     * Use customized rules to extract the main body
	     */
	    if(selector != null) {
			Elements mainText = doc.select(selector);
			if(mainText.first() != null) {
				doc = Jsoup.parse(mainText.first().html());
				doc.select("div").prepend(MikePipe.NewLineStr).append(MikePipe.NewLineStr).unwrap();
			}
		}
	    

	    //System.err.printf("\n%s\n\n", rawHtml);
		//System.err.printf("----- before preprocess--------------\n\n");
		//System.err.printf("\n%s\n\n", doc.html());
		//System.err.printf("----------------after preprocess-----------------------------\n\n");
		
		Element ele = CuttingPreprocessor.preprocess(doc, target);
		
		ele.select("font,b,span,strong,form,center").unwrap();
		ele.select("p").prepend(NewLineStr).append(NewLineStr).unwrap();
		ele.select("h1,h2,h3,h4,h5,h6").prepend(" " + NewLineStr).append(NewLineStr).unwrap();
		// 5/2 delete u tag from the string
		ele.select("wbr,embed,img,header,footer,iframe,select,input,ins,div.footer,div.footer1,div.footer2,div#footer").remove();
		ele.select("o|p, O|P").remove();
		
		// added on 5/2
		ele.select("em").unwrap();
		
		for( Element element : ele.select("br") ) {
		    element.replaceWith(new TextNode(NewLineStr, null));
		}
		// replaced with two lines
		for( Element element : ele.select("hr") ) {
		    element.replaceWith(new TextNode(NewLineStr+NewLineStr, null));
		}
		
		//System.err.printf("-----HTML----------before unwrap div ----------------------------\n\n");
		//System.err.printf("\n%s\n\n", ele.html());
		//System.err.printf("------------------------------------------------------------\n\n");


		TotalHTML = ele.html();
		TotalHTML = CuttingPostprocessor.postprocess(TotalHTML, target);
		TotalHTML = TotalHTML.replaceAll("&nbsp;", "");


		//System.err.printf("-----HTML-----------------------------------------\n\n");
		//System.err.printf("\n%s\n\n", TotalHTML);
		//System.err.printf("------------------------------------------------------------\n\n");
		
      
		try {
		        ParsedText = boilerpipe(TotalHTML, target);
		        //ParsedText = boilerpipe(rawHtml, target);
		        
		        
				//System.err.printf("**Result before recover*****************************************************\n\n");
				//System.err.printf("ParsedText=\n%s\n\n", ParsedText);
				//System.err.printf("*************************************************************\n\n");
				


				// recover =NewLineStr= to new line
		        ParsedText = ParsedText.replaceAll(NewLineStr, "\n");
		        
		        // trim each lines
		        if (isEnableResultLineTrimming) {
		        	TrimHTMLResult(ParsedText);
		        }
		        
				// remove extra new line
				ParsedText = ParsedText.replaceAll("\n\\s*\n\\s*\n\\s*\n", "\n\n");
				// replace new lines to <br>
				//ParsedText = ParsedText.replaceAll("\n", "<br>");

		        // compare with og text
				
		        if (OgText != null) {
		           String tmpResult = TrimHTMLResult(ParsedText);
		           if (OgText.length() > tmpResult.length()) {
		        	   ParsedText =  OgText;
		           }
		        }
		        
				//System.err.printf("**Result after recover*****************************************************\n");
				//System.err.printf("%s", ParsedText);
				//System.err.printf("*************************************************************\n");
				
		} catch (Exception e) {
			  //System.err.println(e.getStackTrace());
		}
		return ParsedText;
  }
	
	

          
  
  private static String TrimHTMLResult (String ParsedText) {
	  ArrayList<String> doclines = new ArrayList<String>();
      StringBuffer tmpTotalHTML = new StringBuffer();
      StringTokenizer TotalLines = new StringTokenizer(ParsedText,"\n");
      while (TotalLines.hasMoreElements()) {
   	   String tmpStr = (String) TotalLines.nextElement();
      	   doclines.add(tmpStr.trim());
      }
      // combine ArrayList to doc
      for (int i=0; i<doclines.size(); i++) {
   	   tmpTotalHTML.append(doclines.get(i));
   	   tmpTotalHTML.append("\n");
      }
      ParsedText = tmpTotalHTML.toString();
      return ParsedText;
  }
  
  
  
	public static String boilerpipe(String content, String target) {

		String article = "";
		String keepEverything = "";

		try {
			article = ArticleExtractor.INSTANCE.getText(content);

			if (target.contains("mypaper.pchome.com.tw")) {
				if (article.length() == 0) {
					keepEverything = KeepEverythingExtractor.INSTANCE
							.getText(content);
					return keepEverything;
				}
			}
		} catch (BoilerpipeProcessingException e) {
			e.printStackTrace();
		}
		return article;
	}
}
