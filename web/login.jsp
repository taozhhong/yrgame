<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="nowdate" class="Com.SharePackages.Format_Date" scope="request"/>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.checkStr,java.util.Iterator" %>
<%@include file="/include/cookie_name.jsp"%>
<%
    if(!checkStr.isNull(cookie_game_value))
    {
        response.sendRedirect("/member");
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
            //刷新验证码
            $("#Ycode,#Ycode_1").bind("click", function () {
                $("#Ycode").attr("src", "/Action/GetCode.do?s=" + Math.random());
            });
            //用户名
            $("#btnLogon").bind("click",function(){
                var bool=true;
                if($.trim($("#txtAccounts").val())=="")
                {
                    bool=false;
                    $("#_LogName").html("请输入登录帐号");
                }
                else
                {
                    $("#_LogName").html("");
                }
                if($.trim($("#txtLogonPass").val())=="")
                {
                    bool=false;
                    $("#_LogPsd").html("请输入密码");
                }
                else
                {
                    $("#_LogPsd").html("");
                }
                if($.trim($("#txtCode").val())=="")
                {
                    bool=false;
                    $("#_Code").html("请输入验证码");
                }
                else
                {
                    $("#_Code").html("");
                }
                if(bool)
                {
                    $("#btnLogon").attr({"disabled":"disabled"});
                    $("#logform").submit();
                }
            });
            //验证码
            $("#txtCode").focus(function(){
                $("#_Code").html("请输入验证码");
            });
            $("#txtCode").blur(function(){
                if($.trim($("#txtCode").val())!="" || $.trim($("#txtCode").val()).length==5)
                {
                    $.ajax({
                        type:"POST",
                        url:"/Action/CheckCode.do",
                        data:"regmethod=iscode&logincode="+$.trim($("#txtCode").val()),
                        beforeSend:function(){
                            $("#_Code").html("正在验证...");
                        },//执行ajax前执行loading函数.直到success
                        success:function(msg){
                            var jsondata = eval('(' + msg + ')');
                            if(jsondata.err == "")//表示没有出错
                                $("#_Code").html("");
                            else
                                $("#_Code").html(jsondata.err);
                        } //成功时执行Response函数
                    });
                }
                else
                {
                    $("#_Code").html("请输入验证码");
                }
            });
        });
    </script>
</head>

<body>

<!--头部开始-->
<%@ include file="/include/top.jsp" %>
<!--头部结束-->
<div id="main">
    <%@ include file="/include/left.jsp" %>
    <div class="main_right">
        <div class="main_box">
            <div class="location"><span><p>登录个人中心</p></span>

                <div class="en">
                    <!--当前位置-->
                    <span>您的位置：<a href="/">我想游游戏</a> &gt; 登录个人中心</span>
                    <!--当前位置结束-->
                    Login
                </div>
            </div>

            <div class="login_box">
                <form name="logform" action="/Action/MemberLogin.do" id="logform" method="post" onsubmit="return false;">
                <ul>
                    <li>
                        <div class="uesr input1"><input style="color: rgb(153, 153, 153);" name="txtAccounts"
                                                        id="txtAccounts" class="text"
                                                        onClick="if(value==defaultValue){value='';this.style.color='#666'}"
                                                        onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
                                                        type="text"></div>
                        <div class="msg" id="_LogName"></div>


                    </li>
                    <li>
                        <div class="pass input1"><input name="txtLogonPass" id="txtLogonPass" class="text"
                                                        type="password"></div>
                        <!--<span><a href="/User/ResetLoginPass.htmlx">忘记密码？</a></span>          </li>-->
                        <div class="msg" id="_LogPsd"></div>
                    </li>
                    <li>
                        <div class="verification">
                            <div class="enter"><input name="txtCode" id="txtCode"
                                                      onClick="if(value==defaultValue){value='';this.style.color='#666'}"
                                                      onBlur="if(!value){value=defaultValue;this.style.color='#999'}"
                                                      type="text" maxlength="5">
                            </div>
                            <div class="code"><span><img src="/Action/GetCode.do" id="Ycode"
                                                         style="cursor:pointer;border: 1px solid #333333;vertical-align:middle;" title="点击图片更换验证码"></span>
                            </div>
                        </div>
                        <div class="ll"><a href="javascript:void('')" id="Ycode_1">看不清楚？</a></div>
                        <div class="msg" id="_Code"></div>
                    </li>
                    <li>
                        <input name="btnLogon" id="btnLogon" src="/images/login_submit.gif"
                               style="border-width:0;" type="image">
                    </li>
                </ul>
                </form>
            </div>
        </div>
    </div>
    <div class="remove"></div>

</div>
<!--底部开始-->
<%@ include file="/include/foot.jsp" %>
<!--底部结束-->
</body>
</html>