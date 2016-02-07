<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
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
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnUpdate").bind("click",function(){
                var bool=true;
                if($.trim($("#txtOldPass").val())=="")
                {
                    bool=false;
                    $("#txtOldPassTip").html("请输入您的原始密码！");
                }
                else
                {
                    $("#txtOldPassTip").html("");
                }
                if($.trim($("#txtNewPass").val()).length<6 || $.trim($("#txtNewPass").val()).length>20)
                {
                    bool=false;
                    $("#txtNewPassTip").html("请输入新密码，至少需要6位！");
                }
                else
                {
                    $("#txtNewPassTip").html("");
                }
                if($.trim($("#txtNewPass").val())!=$.trim($("#txtNewPass2").val()))
                {
                    bool=false;
                    $("#txtNewPass2Tip").html("新密码输入不一致！");
                }
                if(bool)
                {
                    $("#btnUpdate").attr({"disabled":"disabled"});
                    $("#form1").submit();
                }
            });
        });
    </script>
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
            <form name="form1" action="/Action/ModifyPass.do" id="form1" method="post" onsubmit="return false;">
            <div class="midrigtpos">当前位置：首页 &gt; 会员中心 &gt; 修改登录密码</div>
            <ul>
                <li>
                    <p class="pwd">原登录密码：</p>
                    <input name="txtOldPass" id="txtOldPass" class="input_m" type="password" maxlength="20">
                    <span id="txtOldPassTip"></span>
                </li>
                <li>
                    <p class="pwd">新登录密码：</p>
                    <input name="txtNewPass" id="txtNewPass" class="input_m" type="password" maxlength="20">
                    <span id="txtNewPassTip"></span>
                </li>
                <li>
                    <p class="pwd">确认登录密码：</p>
                    <input name="txtNewPass2" id="txtNewPass2" class="input_m" type="password" maxlength="20">
                    <span id="txtNewPass2Tip"></span>
                </li>
                <li>
                    <p class="pwd">&nbsp;</p>
                    <input name="btnUpdate" value="确 定" id="btnUpdate" type="submit"> &nbsp;<input name="button" value="取 消" type="reset">
                </li>
            </ul>
            <div class="line30"></div>
            <div class="ts">
                <p><font color="#398700">【温馨提示】</font></p>
                <p>登录密码修改成功后，请重新用新密码登录！</p>
            </div>
                </form>
        </div>
    </div>
    <div style="clear:both;"></div>
</div>
<!--底部开始-->
<%@ include file="/include/foot.jsp" %>
<!--底部结束-->
</body>
</html>