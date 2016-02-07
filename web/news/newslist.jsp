<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<jsp:useBean id="nowdate" class="Com.SharePackages.Format_Date" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.Field_Str,Com.SharePackages.checkStr,java.util.Iterator" %>
<%
    String cateid=tostr.to_utf_8(request.getParameter("cateid"));
    if(!checkStr.checkIsNum(cateid))
        cateid="0";
%>
<!DOCTYPE html>
<html xmlns="/www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>我想游游戏－好玩的棋牌电玩游戏平台</title>
<meta name="keywords" content="我想游游戏,捕鱼,斗地主,梭哈,金花,水果机"/>
<meta name="description" content="信誉棋牌游戏,真正赚金的游戏-渔人。"/>
    <link type="image/gif" rel="icon" title="" href="/images/animated_favicon1.gif"/>
    <link href="/style1/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/jcarousellite_1.0.1.js"></script>
</head> 
<body>
<!--头部开始-->
<%@ include file="/include/top.jsp"%>
<!--头部结束-->
<div id="main">

    <%@ include file="/include/left.jsp"%>
  <div class="main_right">
   
      
    <div class="main_box">
      <div class="location"><span><p>棋牌资讯</p></span>
        <div class="en">
       <!--当前位置-->
        <span>您的位置：我想游 &gt; <%=Field_Str.ArticleType[Integer.parseInt(cateid)]%></span>
        <!--当前位置结束-->
        Chess news</div>
      </div>
      <div class="news_list">
        <ul>
            <%
                Iterator NewsIt=SingleArticle.getInstance().getArticleDb().ArticleList("",
                        "20","*","article a",
                        "a.*","a.cateid="+cateid+" and a.islock=0",
                        "a.articleid","desc").iterator();
                while (NewsIt.hasNext())
                {
                    Article_Pro pro=(Article_Pro)NewsIt.next();
                    if(pro!=null)
                    {

            %>
            <li><span><%=nowdate.set_date(pro.getAddtime(),"yyyy-MM-dd")%></span> <a href='/news/article/id/<%=pro.getCateid()%>/<%=pro.getArticleid()%>'><%=pro.getArticletitle()%></a></li>
            <%
                    }
                    NewsIt.remove();
                }
            %>

                        
                        
   	    </ul>
          </div>
          <!--分页代码结束-->
    </div>
  </div>
  <div class="remove"></div>
  
  </div>
<!--底部开始-->
<%@ include file="/include/foot.jsp"%>
<!--底部结束-->
</body>
</html>
