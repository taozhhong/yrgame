<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.MemberPackages.Member_Pro,Com.MemberPackages.SingleMember"%>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str,java.util.Iterator" %>
<%@include file="/include/cookie_name.jsp"%>
<%
    if(checkStr.isNull(cookie_game_value))
    {
        response.sendRedirect("/reg/gamelogin");
        return;
    }
%>
<!DOCTYPE html>
<html xmlns="/www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>我想游游戏－好玩的棋牌电玩游戏平台</title>
    <meta name="keywords" content="我想游游戏,捕鱼,斗地主,梭哈,金花,水果机"/>
    <meta name="description" content="信誉棋牌游戏,真正赚金的游戏-渔人。"/>
    <link href="/style1/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
</head>

<body>

<!--头部开始-->
<%@ include file="/include/top.jsp" %>
<!--头部结束-->
<div class="midbox">
    <%@ include file="/include/member_left.jsp" %>
    <!-- 个人中心 管理 -->
    <div class="midright">
        <div class="manage">
            <div class="midrigtpos">当前位置：首页 &gt; 会员中心 &gt; 信息提示</div>
            <div class="ts">
                <p style="font-size: 18px;color:#398700;padding-bottom: 10px;">【温馨提示】</p>
                <p style="padding-left: 20px;font-size: 14px;">
                    <%
                        String errcode=tostr.to_utf_8(request.getParameter("errcode"));
                        if(checkStr.isNull(errcode))
                        {
                            out.println("请勿非法修改参数");
                        }
                        else
                        {
                            if(errcode.equals("0"))
                                out.println("密码修改成功，请重新用新密码登录");
                            else if(errcode.equals("-1"))
                                out.println("请输入密码信息");
                            else if(errcode.equals("-2"))
                                out.println("两次新密码输入不一致");
                            else if(errcode.equals("-3"))
                                out.println("密码修改失败");
                            else if(errcode.equals("-4"))
                                out.println("旧密码输入错误");
                            else
                                out.println("未知情况");
                        }
                    %>
                </p>
            </div>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<!--底部开始-->
<%@ include file="/include/foot.jsp" %>
<!--底部结束-->
</body>
</html>