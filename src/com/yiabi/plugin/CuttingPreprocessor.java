package com.yiabi.plugin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

public class CuttingPreprocessor {
	
	public static Element preprocess(Element _doc, String target) {
		
		Element doc = _doc;
		
		Element articleBody = doc.select("div[itemprop=articleBody]").first();
		if(articleBody != null) {
			doc = articleBody;
		}

		doc.select("meter").remove();
		
		// remove all script and noscript tags
		Elements scripts = doc.select("script, noscript");
		//System.err.printf("%d script tags were removed\n", scripts.size());
		scripts.remove();
		
		// remove all style tags
		Elements styles = doc.select("style");
		//System.err.printf("%d style tags were removed\n", styles.size());
		styles.remove();
		
		// this usually means menu
		Elements ullia = doc.select("ul:has(li>a)");
		//System.err.printf("%d ul>li>a[href] were removed\n", ullia.size());
		ullia.remove();
		
		/*
		 * This should be very powerful, but for unknown reason, 
		 * we will get wrong output on sohu.com. Thus, I remove these codes
		 * 
		Elements aj = doc.select("a[href^=javascript]");
		//System.err.printf("%d a[href^=javascript] were removed\n", aj.size());
		aj.remove();
		*/
		
		Elements empty = doc.select("a:empty, p:empty");
		//System.err.printf("%d empty a and p tags were removed\n", empty.size());
		empty.remove();
		
		
		removeComments(doc);
		
		
		if (target.indexOf("yahoo.com") > 0)  {
			doc.select("div.imgDesc").remove();
			doc.select("div.hud").remove();
			doc.select("div.imgMeta").remove();
			Elements mainText = doc.select("#mediaarticlebody");
			if(mainText.first() != null) {
				doc = mainText.first();
			}
		}
		
		if (target.indexOf("wsj.com") > 0)  {   
			doc.select("div.videoDescription").remove();
			doc.select("div.iconFram, div.loginpanel").remove();
			
			Elements mainText = doc.select("#bodytext");
			if(mainText.first() != null) {
				doc = mainText.first();
			}
			
			for( Element firstWord : doc.select("div")) {
				if(firstWord.ownText().length() == 1) {
					//System.err.println(firstWord.ownText().length()+" the first word in wsj:" + firstWord.ownText());
					firstWord.unwrap();
				}
			}
		}
		
		if(target.indexOf("tw.travel.yahoo.com") > 0) {
			
			Elements mainText = doc.select("#ytritarticle");
			if(mainText.first() != null) {
				doc = mainText.first();
			}
			doc.select("table.pic-show").remove();
			
		} else if (target.indexOf("sina.com.cn") > 0) {
			
			doc.select("div.blkBreadcrumbNav,div.nav_weibo,div.wb_rec,div#sinashareto").remove();
			doc.select("div.artInfo a[href]").remove();
			Elements mainText = doc.select("#artibody");
			if(mainText.first() != null) {
				doc = mainText.first();
			}
			//isEnableResultLineTrimming = true;
			
		} else if (target.indexOf("libertytimes.com.tw") > 0) {
			
			doc.select("thead,div#breadcrumb,div#nav_live").remove();
			
		} else if (target.indexOf("usatoday.com") > 0) {
			
			doc.select("div.util-bar-flyout-heading,div.util-bar-share-summary,div.inline-share-tools").remove();
			
		} else if (target.indexOf("ipeen.com.tw") > 0) {
			
			Element mainText = doc.select("div[itemprop=description] div.description").first();
			if(mainText != null) {
				doc = mainText;
				//doc.select("div").unwrap();
			}
			
			doc.select("ul,li").unwrap(); // thead is a tag
			doc.select("div.cmmbtm").unwrap();  // thead is a tag
			
        } else if (target.indexOf("chinatimes.com") > 0) {
        	
			 doc.select("ul,li,div.a_k,nav,div.art_btntools,div.title").remove(); // thead is a tag
			 
        } else if (target.indexOf("msn.com.tw") > 0) {
        	Element mainText = doc.select(".newsArticle").first();
			if(mainText != null) {
				doc = mainText;
				//doc.select("div").unwrap();
			}
			Elements aj = doc.select("a[href^=javascript]");
			//System.err.printf("%d a[href^=javascript] were removed\n", aj.size());
			aj.remove();
			doc.select("ul,li,div.album-thumbs,div.keywords-module,div.list-module,div.Aside-module,div.aside").remove(); 
			
        } else if (target.indexOf("wantgoo.com") > 0) {
        	
			doc.select("h5,h6").remove();	
			
		} else if (target.indexOf("udn.com") > 0) {
			Element mainText = doc.select("#story").first();
			if(mainText != null) {
				doc = mainText;
			}
		} else if (target.indexOf("times.hinet.net") > 0) {
			doc.select("a.newsshow_keyword").unwrap();
		}
		
		
		
		
		if (target.indexOf("mypaper.pchome.com.tw") > 0) {

		    Element startTextElement = doc.select("div#imageSection1").first();
		    if(startTextElement!=null){
		    	startTextElement.prepend("start_yiabitxt");

		    	doc = Jsoup.parse(startTextElement.html());
		    	Element endTextElement = doc.select("div#ArticleMapTitle").first();
		    	if(endTextElement!=null){
			    	//startTextElement.prepend("start_yiabitxt");
			    	endTextElement.before("end_yiabitxt");
			    	String TotalHTML = doc.html();
			    	//System.out.println("my org paper doc= "+ TotalHTML);
			    	
			    	TotalHTML = TotalHTML.replaceFirst("start_yiabitxt", "<start_yiabitxt>");
			     	
			    	TotalHTML = TotalHTML.replaceFirst("end_yiabitxt", "</start_yiabitxt>");
			    //	System.out.println("my after paper doc= "+ TotalHTML);

			    	doc = Jsoup.parse(TotalHTML);
			    	//System.out.println("my paper doc= "+ doc.html());
			    	 Element yiabitxt = doc.select("start_yiabitxt").first();
			    	doc = Jsoup.parse(yiabitxt.html());
			    	//System.err.println("--------111111111111111--------------");
			    	//System.err.println(doc.html());
			    	//System.err.println("--------2222222222222222--------------");
					
					for (Element link : doc.select("a[href]")) {
						if (link.text().trim().length() == 0) {
							link.remove();
						}
					} 		
		    	}
		    } else{
			    
	
			    Elements mypaperTextElement = doc.select("div#imageSection1");
			    if ((mypaperTextElement!=null) && (mypaperTextElement.size()>0))  {
			    	
			    	doc = Jsoup.parse(mypaperTextElement.get(0).html());
			    	//System.err.println("**********2. Get html from div.imageSection1");
			    }
			    doc.select("div.intautr, div#boxForumPost, div.comments-body, div.extended, aside, div#nvbar, div#reply_list_data, div#boxFolder, "
			    		+ "div#boxMySpace, div#boxAdmboards, div#boxNewArticle, div#boxSearch, div#boxCategory, div#boxNewComment, div#boxVisitor, "
			    		+ "div#divThird, div.blkar01, div.ad_ec012, div.date, div#sa_tit, div#banner, div#sixi, div.memberlogin, div#ArticleMapTitle").remove();
		    }    	
	    	//System.err.println("**********2. Get html from div.imageSection1");
	    //	System.out.println("my paper doc= "+ doc.html());
	    	
	    	/* 
	    	 * added on 5/2
	    	 * If the most text are wrapped with too many divs, the text will not
	    	 * be kept after pipe
	    	 */
	    	doc.select("div").prepend(MikePipe.NewLineStr).unwrap();
		}
		
		
		
		/* 
		 * Note!!!
		 * some hidden divs are used to locate main content, so we place this part in the end 
		 */
		Elements hide = doc.select("*[style*=display:none]");
		//System.err.printf("%d hidden div and span were removed\n", hide.size());
		hide.remove();

		return doc;
	}

	public static Node removeComments(Node node) {
        for (int i = 0; i < node.childNodes().size();) {
            Node child = node.childNode(i);
            if (child.nodeName().equals("#comment"))
                child.remove();
            else {
                removeComments(child);
                i++;
            }
        }
        return node;
	}
}
