<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.Field_Str,Com.SharePackages.checkStr,java.util.Iterator" %>
<%@ page import="Com.SharePackages.checkStr"%>
<!DOCTYPE html>
<html xmlns="/www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>用户注册－我想游游戏－好玩的棋牌电玩游戏平台</title>
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
            <div style="text-align: center;line-height: 300px;font-size: 20px;">
                <%
                    String regcode=tostr.to_utf_8(request.getParameter("regcode"));
                    if(checkStr.isNull(regcode))
                    {
                        out.println("请勿非法修改参数");
                    }
                    else
                    {
                        if(regcode.equals("-10"))
                            out.println("用户名或密码输入不正确");
                        else if(regcode.equals("-9"))
                            out.println("验证码输入不一致");
                        else if(regcode.equals("-8"))
                            out.println("验证码已失效");
                        else if(regcode.equals("-7"))
                            out.println("请输入完整的登录信息");
                        else if(regcode.equals("-6"))
                            out.println("请勿非法访问");
                        else if(regcode.equals("-5"))
                            out.println("请输入完整的注册信息");
                        else if(regcode.equals("-4"))
                            out.println("登录密码输入不一致");
                        else if(regcode.equals("-3"))
                            out.println("银行密码输入不一致");
                        else if(regcode.equals("-2"))
                            out.println("抱歉，出错异常，注册失败");
                        else if(regcode.equals("-1"))
                            out.println("用户名或称昵已被注册");
                        else if(regcode.equals("0"))
                            out.println("抱歉，注册失败");
                        else
                            out.println("注册成功");
                    }
                %>
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