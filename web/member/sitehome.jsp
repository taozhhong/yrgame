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
    <script type="text/javascript" src="/js/jcarousellite_1.0.1.js"></script>

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
            <div class="midrigtpos">当前位置：首页 &gt; 会员中心</div>
                <ul>
                    <%
                        Iterator MemberIt= SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                                "m.memberid=" + cookie_game_value.split(",")[0], "m.memberid", "desc").iterator();
                        while (MemberIt.hasNext())
                        {
                            Member_Pro pro=(Member_Pro)MemberIt.next();
                            if(pro!=null)//表示登录帐号已注册
                            {
                    %>
                    <div style="display: block;overflow: hidden;text-align:center;background: #F8F8F8;width: 742px;height: 40px;line-height: 40px;"><b>尊敬的“<span style="color:#FF0000;"><%=pro.getNick()%></span>” ,欢迎您来到用户个人中心</b></div>
                    <li><p class="info">昵称：</p><%=pro.getNick()%></li>
                    <li style="background: #F8F8F8;"><p class="info">性别：</p><%=Field_Str.User_Sex[pro.getSex()]%></li>
                    <li><p class="info">您的ID：</p><%=pro.getMemberid()%></li>
                    <li style="background: #F8F8F8;"><p class="info">邮箱：</p><%=pro.getEmail()%></li>
                    <%
                            }
                            MemberIt.remove();
                        }
                    %>
                </ul>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<!--底部开始-->
<%@ include file="/include/foot.jsp" %>
<!--底部结束-->
</body>
</html>