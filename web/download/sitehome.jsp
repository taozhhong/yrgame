<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="nowdate" class="Com.SharePackages.Format_Date" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.checkStr,java.util.Iterator" %>
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
    <script type="text/javascript" src="/js/jcarousellite_1.0.1.js"></script></head>
<body> 
<!--头部开始-->
<%@ include file="/include/top.jsp"%>
<!--头部结束-->
<div id="main">
    <%@ include file="/include/left.jsp"%>
  <div class="main_right">
    <div class="main_box">
      <div class="location"><span><p>游戏下载</p></span>
        <div class="en">
       <!--当前位置-->
        <span>您的位置：渔人棋牌 &gt; 游戏下载</span>
        <!--当前位置结束-->
        Game download</div>
      </div>
      
      

      <div class="gamelist">
        <!--游戏大厅下载开始-->
        <div class="game_lobby">
          <div class="name">渔人棋牌游戏大厅</div>
          <div class="text">精简大厅：<strong>3.70</strong>M <br>
完整大厅：<strong>113.00</strong>M<br>
更新日期：<strong>2015/09/01</strong><br></div>
          <div class="down"><a href="#" target="_blank" class="d1">完整版下载</a> <a href="#" target="_blank" class="d2">精简版下载</a></div>
        </div>

        <!--游戏大厅下载结束-->
        <div class="game_list">
          <ul>
          <!--热门游戏内容循环开始-->


            <!--热门游戏内容循环结束-->
            <div class="remove"></div>
          </ul>
          <div class="remove"></div>
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
