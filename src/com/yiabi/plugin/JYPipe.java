package com.yiabi.plugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import de.l3s.boilerpipe.extractors.ArticleExtractor;

// =====================================================================
// Purpose: 	JY's degin: filter some html tag to improve cutting
// Parameters:
// Return:
// Remark:		
// Author:		JY, Bryan
// =====================================================================
//http://mypaper.pchome.com.tw/zen/post/1327455704

public class JYPipe {
	public static String filterCM (String RawHTML, String Target){
    	
  	  final long serialVersionUID = 1L;
  	  final String NewLineStr = "xYBx";
  	  boolean isEnableResultLineTrimming = false; // this will make result more compact
  	  boolean isAddNewLineAfterDiv = true;
  	  boolean isUnWrapDiv = true;
      String TotalHTML="";
      String OgText = null;
  	  
		String HTMLTitle = "";
		Document doc=null;
		
		//System.out.println(RawHTML);
		if (RawHTML != null) {
//		   System.out.printf("\nRAW HTML: %s\n", RawHTML);
		   doc = Jsoup.parse(RawHTML);
		} 
		//System.out.printf("\n\n*doc.html after soap***********************************************\n\n");
		//System.out.printf("\n%s\n\n", doc.html());
		//System.out.printf("*************************************************************\n\n");

		String ParsedText = null;
		Target = Target.toLowerCase();
	
		// remove unwant contents by DIV class before unwrap DIV
		if (Target.indexOf("yahoo.com") > 0)  {   
		   // rules specific for yahoo Taiwan
		   for( Element element : doc.select("div.imgDesc") )
		   {
		       element.remove();
		   }		
		   for( Element element : doc.select("div.hud") )
		   {
		       element.remove();
		   }				
		   for( Element element : doc.select("div.imgMeta") )
		   {
		       element.remove();
		   }					
		}
		if (Target.indexOf("wsj.com") > 0)  {   
			   // rules specific for WSJ China
			   for( Element element : doc.select("div.videoDescription") )
			   {
			       element.remove();
			   }	
			   for( Element element : doc.select("div.iconFram, div.loginpanel") ) // iconFram is class
			   {
			       element.remove();
			   }	
			   for( Element element : doc.select("div#user-account, div#nav, div#tabdiv") ) // tabdiv is a id
			   {
			       element.remove();
			   }		
			   isAddNewLineAfterDiv = false;
		}
	//method 2
		if (Target.indexOf("mypaper.pchome.com.tw") > 0) {
			Elements metaOgDesc = doc.select("meta[property=og:description]");
		    if ((metaOgDesc!=null) && (metaOgDesc.size()>0))  {
		    	OgText = metaOgDesc.attr("content");
		    	System.out.println(OgText);
		    } else {
		    	System.out.println("No [Open Graph Protocol] (meta og:xxxx ) SEO !! \n");
		    }

		    Element startTextElement = doc.select("div#imageSection1").first();
		    if(startTextElement!=null){
		    	startTextElement.prepend("start_yiabitxt");

		    	doc = Jsoup.parse(startTextElement.html());
		    	Element endTextElement = doc.select("div#ArticleMapTitle").first();
		    	if(endTextElement!=null){
			    	//startTextElement.prepend("start_yiabitxt");
			    	endTextElement.before("end_yiabitxt");
			    	TotalHTML = doc.html();
			    	//System.out.println("my org paper doc= "+ TotalHTML);
			    	
			    	TotalHTML = TotalHTML.replaceFirst("start_yiabitxt", "<start_yiabitxt>");
			     	
			    	TotalHTML = TotalHTML.replaceFirst("end_yiabitxt", "</start_yiabitxt>");
			    //	System.out.println("my after paper doc= "+ TotalHTML);

			    	doc = Jsoup.parse(TotalHTML);
			    	//System.out.println("my paper doc= "+ doc.html());
			    	 Element yiabitxt = doc.select("start_yiabitxt").first();
			    	doc = Jsoup.parse(yiabitxt.html());
			    	
					for( Element element : doc.select("div,font,script,style,span") ) 
					{
					    element.unwrap();
					}	

					
					for (Element link : doc.select("a[href]")) {
						if (link.text().trim().length() == 0) {
							link.remove();
						}
					}
			  //  	System.out.println("**********1. Get html from div.imageSection1");
			  //  	System.out.println("my paper text= "+ doc.html());	    		
		    	}
		    }
//	    	Element endTextElement = doc.select("div#ArticleMapTitle").first();
//		    if ((startTextElement!=null) && endTextElement !=null)  {
//		    	startTextElement.prepend("start_yiabitxt");
//		    	endTextElement.before("end_yiabitxt");
//		    	TotalHTML = doc.html();
//		     	TotalHTML.replaceFirst("start_yiabitxt", "<start_yiabitxt>");
//		    	TotalHTML.replaceFirst("end_yiabitxt", "</start_yiabitxt>");
//		    	System.out.println("my paper doc= "+ doc.html());
//		    	doc = Jsoup.parse(TotalHTML);
//		    	System.out.println("my paper doc= "+ doc.html());
//		    	 Element yiabitxt = doc.select("start_yiabitxt").first();
//		    	doc = Jsoup.parse(yiabitxt.html());
//				for( Element element : doc.select("*") ) 
//				{
//				    element.unwrap();
//				}	
//
//		    	System.out.println("**********1. Get html from div.imageSection1");
//		    	System.out.println("my paper doc= "+ doc.html());
//		    }
		    else{
			    
	
			    Elements mypaperTextElement = doc.select("div#imageSection1");
			    if ((mypaperTextElement!=null) && (mypaperTextElement.size()>0))  {
			    	
			    	doc = Jsoup.parse(mypaperTextElement.get(0).html());
			    	System.out.println("**********2. Get html from div.imageSection1");
			    }
	
				
				for( Element element : doc.select("div.intautr,div#boxForumPost, div.comments-body,div.extended,aside,div#nvbar,div#reply_list_data,div#boxFolder,div#boxMySpace,div#boxAdmboards,div#boxNewArticle,div#boxSearch,div#boxCategory,div#boxNewComment,div#boxVisitor,div#divThird,div.blkar01,div.ad_ec012,div.date,div#sa_tit,div#banner,div#sixi,div.memberlogin,div#ArticleMapTitle,script,style") ) 
				{
				    element.remove();
				}	
				
				
				// remove empty a[href]
				Elements links = doc.select("a[href]"); 
				
				for (Element link : links) {
					if (link.text().trim().length() == 0) {
						link.remove();
					}
				}
				
		    }
			isEnableResultLineTrimming = true;		    	
	    	System.out.println("**********2. Get html from div.imageSection1");
	    //	System.out.println("my paper doc= "+ doc.html());
		}
		
//	//method 1
//			if (Target.indexOf("mypaper.pchome.com.tw") > 0) {
//			// check og data
//			Elements metaOgDesc = doc.select("meta[property=og:description]");
//		    if ((metaOgDesc!=null) && (metaOgDesc.size()>0))  {
//		    	OgText = metaOgDesc.attr("content");
//		    	System.out.println(OgText);
//		    } else {
//		    	System.out.println("No [Open Graph Protocol] (meta og:xxxx ) SEO !! \n");
//		    }
//		    
//
//		    Elements mypaperTextElement = doc.select("div#imageSection1");
//		    if ((mypaperTextElement!=null) && (mypaperTextElement.size()>0))  {
//		    	
//		    	doc = Jsoup.parse(mypaperTextElement.get(0).html());
//		    	System.out.println("**********Get html from div.imageSection1");
//		    }
//
//			
//			for( Element element : doc.select("div.intautr,div#boxForumPost, div.comments-body,div.extended,aside,div#nvbar,div#reply_list_data,div#boxFolder,div#boxMySpace,div#boxAdmboards,div#boxNewArticle,div#boxSearch,div#boxCategory,div#boxNewComment,div#boxVisitor,div#divThird,div.blkar01,div.ad_ec012,div.date,div#sa_tit,div#banner,div#sixi,div.memberlogin,div#ArticleMapTitle,script,style") ) 
//			{
//			    element.remove();
//			}	
//			
//			
//			// remove empty a[href]
//			Elements links = doc.select("a[href]"); 
//			
//			for (Element link : links) {
//				if (link.text().trim().length() == 0) {
//					link.remove();
//				}
//			}
//			
//			isEnableResultLineTrimming = true;
//		}
//		
		if (Target.indexOf("sina.com.cn") > 0) {
			for( Element element : doc.select("div.blkBreadcrumbNav,div.nav_weibo,div.wb_rec,div#sinashareto") ) // tabdiv is a id
			{
			       element.remove();
			}	
			for( Element element : doc.select("div.artInfo")) {
				
				Elements links = element.select("a[href]"); 
				
				for (Element link : links) {
					link.remove();
				}
			    
			}
			
			isEnableResultLineTrimming = true;
		}
		if (Target.indexOf("libertytimes.com.tw") > 0) {
			for( Element element : doc.select("thead,div#breadcrumb,div#nav_live") ) // thead is a tag
			{
			       element.remove();
			}
		}
		if (Target.indexOf("usatoday.com") > 0) {
			for( Element element : doc.select("div.util-bar-flyout-heading,div.util-bar-share-summary,div.inline-share-tools") ) // thead is a tag
			{
				   element.remove();
			}
		}
		if (Target.indexOf("ipeen.com.tw") > 0) {
			for( Element element : doc.select("ul,li") ) // thead is a tag
			{
			       element.unwrap();
			}
			for( Element element : doc.select("div.cmmbtm") ) // thead is a tag
			{
			       element.remove();
			}
			 
        }
		if (Target.indexOf("chinatimes.com") > 0) {
			for( Element element : doc.select("ul,li,div.a_k,nav,div.art_btntools,div.title") ) // thead is a tag
			{
			       element.remove();
			}		 
        }

		if (Target.indexOf("msn.com.tw") > 0) {
			for( Element element : doc.select("ul,li,div.album-thumbs,div.keywords-module,div.list-module,div.Aside-module,div.aside") ) // thead is a tag
			{
			       element.remove();
			}		 
        }
		if (Target.indexOf("wantgoo.com") > 0) {
			for( Element element : doc.select("h5,h6") ) 
			{
			       element.remove();
			}	
		}
		
		//System.out.printf("\n\n*doc.html after specific process***********************************************\n\n");
		//System.out.printf("\n%s\n\n", doc.html());
		//System.out.printf("*************************************************************\n\n");
	
		// unwrap unwant tags
		for( Element element : doc.select("font,b,span,strong,form,center") )
		{
		       element.unwrap();
		}				
		for( Element element : doc.select("p"))
		{
		
			   element.appendText(NewLineStr);
		       element.unwrap();
			
		}
		
		// rename tags, add <br> at front, back position
		for( Element element : doc.select("h1,h2,h3,h4,h5,h6") )
		{
			   
			   element.prependText(" " + NewLineStr);  // add space before br to prevent merged 
		       element.appendText(NewLineStr);
		       element.unwrap();
		}			

		// remove unwant tags
		for ( Element element : doc.select("wbr,embed,img,header,footer,iframe,u,select,input,ins,div.footer,div.footer1,div.footer2,div#footer") )
		{
		    element.remove();
		}
		
		// replace
		
		for( Element element : doc.select("br") )
		{
		    element.replaceWith(new TextNode(NewLineStr, null));
		}
		
		for( Element element : doc.select("hr") )   // replaced with two lines
		{
		    element.replaceWith(new TextNode(NewLineStr+NewLineStr, null));
		}
		
		// unwrap unwant tags
		if (isUnWrapDiv) {
		   for( Element element : doc.select("div") )
		   {
			   if (isAddNewLineAfterDiv) {
			      element.appendText(NewLineStr);
			   }
		       element.unwrap();
		   }
		}
		//removeComments(doc);
		TotalHTML = doc.html();
		
		TotalHTML = TotalHTML.replaceAll("&nbsp;", "");
		TotalHTML = TotalHTML.replaceAll("<o:p>", "");
		TotalHTML = TotalHTML.replaceAll("</o:p>", "");
		TotalHTML = TotalHTML.replaceAll("<O:P>", "");
		TotalHTML = TotalHTML.replaceAll("</O:P>", "");


		System.out.printf("--HTML------------------------------------------------------\n\n");
		System.out.printf("\n%s\n\n", TotalHTML);
		System.out.printf("------------------------------------------------------------\n\n");
		
      
		try {
		        ParsedText = ArticleExtractor.INSTANCE.getText(TotalHTML);

		        
		       // ParsedText = DefaultExtractor.INSTANCE.getText(TotalHTML);
		       // ParsedText = LargestContentExtractor.INSTANCE.getText(TotalHTML);
		       // ParsedText = NumWordsRulesExtractor.INSTANCE.getText(TotalHTML);
				System.out.printf("**Result before recover*****************************************************\n\n");
				System.out.printf("ParsedText=\n%s\n\n", ParsedText);
				System.out.printf("*************************************************************\n\n");


				// recover =NewLineStr= to new line
		        ParsedText = ParsedText.replaceAll(NewLineStr, "\n");
		        // remove Text before title
		        /*
		        if (HTMLTitle != null) {
		           int Pos_title = ParsedText.indexOf(HTMLTitle);
		           if (Pos_title > 0) {
		        	   ParsedText = ParsedText.substring(Pos_title, ParsedText.length());
		           }
		        }*/
		        // trim each lines
		        if (isEnableResultLineTrimming) {
		        	TrimHTMLResult(ParsedText);
		        }
		        
				// remove extra new line
				ParsedText = ParsedText.replaceAll("\n\n\n\n", "\n");
				// replace new lines to <br>
				//ParsedText = ParsedText.replaceAll("\n", "<br>");

		        // compare with og text
		        if (OgText != null) {
		           String tmpResult = TrimHTMLResult(ParsedText);
		           if (OgText.length() > tmpResult.length())	{
		        	   ParsedText =  OgText;
		           }
		        }
		        
				System.out.printf("**Result after recover*****************************************************<br><br>");
				System.out.printf("Title:%s<br><br>", HTMLTitle);
				System.out.printf("<br><br>%s<br><br>", ParsedText);
				System.out.printf("*************************************************************<br><br>");
				
		} catch (Exception e) {
			  System.out.println(e.getStackTrace());
		}
		return ParsedText;
  }
	
	

  private static void removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
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
}
