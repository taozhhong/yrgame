<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<jsp:useBean id="nowdate" class="Com.SharePackages.Format_Date" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.Field_Str,Com.SharePackages.checkStr,java.util.Iterator" %>
<%
    String cateid=tostr.to_utf_8(request.getParameter("cateid"));
    String artid=tostr.to_utf_8(request.getParameter("artid"));
    if(!checkStr.checkIsNum(cateid) || !checkStr.checkIsNum(artid))
    {
        out.println("抱歉，请勿非法修改参数");
        return;
    }
    Iterator ArtIt=SingleArticle.getInstance().getArticleDb().ArticleList("y",
            "1","a.*","article a",
            "","a.islock=0 and a.articleid="+artid,
            "a.articleid","desc").iterator();
    while (ArtIt.hasNext())
    {
        Article_Pro pro=(Article_Pro)ArtIt.next();
        if(pro!=null)
        {
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
      <div class="location"><span><p><%=Field_Str.ArticleType[Integer.parseInt(cateid)]%></p></span>
        <div class="en">
       <!--当前位置-->
        <span>您的位置：渔人棋牌 &gt; <%=Field_Str.ArticleType[Integer.parseInt(cateid)]%></span>
        <!--当前位置结束-->
        Chess news</div>
      </div>
      
      <div class="news_page">

        <div class="title"><h2><%=pro.getArticletitle()%></h2></div>
        <div class="date">类别：<%=Field_Str.ArticleType[Integer.parseInt(cateid)]%>   &nbsp;&nbsp;&nbsp;&nbsp; 来源：渔人棋牌   &nbsp;&nbsp;&nbsp;&nbsp; 发布于：<%=nowdate.set_date(pro.getAddtime(),"yyyy-MM-dd HH:mm:ss")%></div>
        <div class="content">
        <!--文章内容开始-->

            <%=pro.getContent()%>
          
          <!--文章内容结束-->
        </div>


      </div>
      
    </div>
  </div>
  <div class="remove"></div>
  
  </div>
<!--底部开始-->
<%@ include file="/include/foot.jsp"%>
<!--底部结束-->
</body>
</html>
<%

        }
        else
        {
            response.sendRedirect("/404.html");
        }
        ArtIt.remove();
    }
%>