<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.Field_Str,Com.SharePackages.checkStr,java.util.Iterator" %>
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
<title>用户注册－我想游游戏－好玩的棋牌电玩游戏平台</title>
<meta name="keywords" content="渔人棋牌游戏,捕鱼,斗地主,梭哈,金花,水果机"/>
<meta name="description" content="信誉棋牌游戏,真正赚金的游戏-渔人。"/>
    <link href="/style1/css.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/js/Common.js"></script>
    <script type="text/javascript">
        $(document).ready(
                function(){
                    var loginmsg="",nickmsg="";
                    $("#txtAccounts").blur(function(){
                        if(GetByteLength($.trim($("#txtAccounts").val()))>20 ||
                                GetByteLength($.trim($("#txtAccounts").val()))<6)
                        {
                            loginmsg="{'err':'用户名长度只能在6-20个字符之间','msg':''}";
                            $("#txtAccountsTip").html("用户名长度只能在6-20个字符之间");
                        }
                        else
                        {
                            //进行用户名重复的验证
                            $.ajax({
                                type:"POST",
                                url:"/Member/RegAction.do",
                                data:"regmethod=valiloginname&loginname="+$.trim($("#txtAccounts").val()),
                                async: true,
                                beforeSend:function(){
                                    $("#txtAccountsTip").html("正在验证...");
                                },//执行ajax前执行loading函数.直到success
                                success:function(msg){
                                    loginmsg=msg;
                                    var jsondata = eval('(' + msg + ')');
                                    if(jsondata.err == "")//表示没有出错
                                    {
                                        $("#txtAccountsTip").html("用户名可以使用");
                                    }
                                    else
                                    {
                                        $("#txtAccountsTip").html("用户名已被占用");
                                    }
                                } //成功时执行Response函数
                            });
                        }
                    });
                    $("#txtNickname").blur(function(){
                        //用户称昵验证
                        if($.trim($("#txtNickname").val()).length<3 || $.trim($("#txtNickname").val()).length>8)
                        {
                            nickmsg={'err':'游戏称昵长度只能是3-8个字符或汉字之间','msg':''};
                            $("#txtNicknameTip").html("游戏称昵长度只能是3-8个字符或汉字之间");
                            return;
                        }
                        else
                        {
                            //进行称昵重复的验证
                            $.ajax({
                                type:"POST",
                                url:"/Member/RegAction.do",
                                data:"regmethod=valinick&nick="+$.trim($("#txtNickname").val()),
                                async: true,
                                beforeSend:function(){
                                    $("#txtNicknameTip").html("正在验证...");
                                },//执行ajax前执行loading函数.直到success
                                success:function(msg){
                                    nickmsg=msg;
                                    var jsondata = eval('(' + msg + ')');
                                    if(jsondata.err == "")//表示没有出错
                                    {
                                        $("#txtNicknameTip").html("称昵可以使用");
                                    }
                                    else
                                    {
                                        $("#txtNicknameTip").html("称昵已被占用");
                                    }
                                } //成功时执行Response函数
                            });
                        }
                    });
                    $("#btnRegister").bind("click",function(){
                        if($("#txtAccounts").val()=="")
                        {
                            $("#txtAccountsTip").html("请输入用户名");
                            return;
                        }
                        if($("#txtNickname").val()=="")
                        {
                            $("#txtNicknameTip").html("请输入称昵");
                            return;
                        }
                        if(eval('(' + loginmsg + ')').err!="")
                        {
                            $("#txtAccountsTip").html(eval('(' + loginmsg + ')').err);
                            return;
                        }
                        else
                        {
                            $("#txtAccountsTip").html(eval('(' + loginmsg + ')').msg);
                        }
                        if(eval('(' + nickmsg + ')').err!="")
                        {
                            $("#txtNicknameTip").html(eval('(' + nickmsg + ')').err);
                            return;
                        }
                        else
                        {
                            $("#txtNicknameTip").html(eval('(' + nickmsg + ')').msg);
                        }
                        //姓别验证
                        if($('input:radio[name="txtSex"]:checked').val()==null)
                        {
                            $("#txtSexTip").html("请选择性别");
                            return;
                        }
                        else
                        {
                            $("#txtSexTip").html("");
                        }
                        //用户密码验证
                        if($.trim($("#txtLogonPass").val()).length<6 || $.trim($("#txtLogonPass").val()).length>20)
                        {
                            $("#txtLogonPassTip").html("不能使用过于简单的密码");
                            return;
                        }
                        else
                        {
                            $("#txtLogonPassTip").html("");
                        }

                        //检测二次密码输入是否一致
                        if($.trim($("#txtLogonPass").val())!=$.trim($("#txtLogonPass2").val()))
                        {
                            $("#txtLogonPass2Tip").html("两次密码输入不一致");
                            return;
                        }
                        else
                        {
                            $("#txtLogonPass2Tip").html("");
                        }

                        //用户银行密码验证
                        if(!CheckEmail($.trim($("#txtInsureEmail").val())))
                        {
                            $("#txtInsureEmailTip").html("请输入有效的邮箱");
                            return;
                        }
                        else
                        {
                            $("#txtInsureEmailTip").html("");
                        }


                        //服务条款check框
                        if(!$("#chk_agree").attr("checked"))
                        {
                            $("#id_agree").html("请选择注册条款");
                            return;
                        }
                        else
                        {
                            $("#id_agree").html("");
                        }
                        if(loginmsg!="" && nickmsg!="")
                        {
                            $("#btnRegister").attr({"disabled":"disabled"});
                            $("#regform").submit();
                        }
                        else
                        {
                            alert("抱歉，无法获取信息，注册失败");
                        }
                    });
                });
    </script>
</head>
<body>

<!--头部开始-->
<%@ include file="/include/top.jsp"%>
<!--头部结束-->
<div id="main">
    <%@ include file="/include/left.jsp"%>
  <div class="main_right">
    <div class="main_box">
      <div class="location"><span>
      <p>会员注册</p>
      </span>
        <div class="en">
       <!--当前位置-->
       Register</div>
      </div>

      <div class="login_box">
          <form method="post" id="regform" action="/Member/RegAction.do" onsubmit="return false;">
          <ul>
          <LI>
            <span class="span1">用户名：</span>
            <span class="span2"><input type="text" name="txtAccounts" id="txtAccounts" maxlength="10" style="line-height:30px; height:30px; width:250px; border: solid 1px #cfcfcf"></span>
              <span class="span3" id="txtAccountsTip">由6-20位字符组成，中文算2个字符</span>
          </LI>
          <LI>
            <span class="span1">昵称：</span>
            <span class="span2"><input type="text" name="txtNickname" maxlength="8" id="txtNickname" style="line-height:30px; height:30px; width:250px; border:  solid 1px #cfcfcf"></span>
            <span class="span3"><span id="txtNicknameTip">由3-8位字符或汉字之间</span></span>
            </LI>
          <LI>
            <span class="span1">性别：</span>
            <span class="span2" style="padding-top: 5px;"><input type="radio" name="txtSex" value="0">男
            <input type="radio" name="txtSex" value="1">女</span>
              <span class="span3" id="txtSexTip">请选择性别</span>
</LI>
          <LI>
            <span class="span1">密码：</span>
                <span class="span2"><input type="password" name="txtLogonPass" id="txtLogonPass" maxlength="20" style="line-height:30px; height:30px; width:250px; border:  solid 1px #cfcfcf"></span>
                <span class="span3" id="txtLogonPassTip">由6-20位字符组成</span>
            </LI>
          <LI>
            <span class="span1">确认密码：</span>
              <span class="span2"><input type="password" name="txtLogonPass2" id="txtLogonPass2" maxlength="20" style="line-height:30px; height:30px; width:250px; border:  solid 1px #cfcfcf"></span>
              <span class="span3" id="txtLogonPass2Tip">请再次输入密码</span>
            </LI>
          <LI>
              <span class="span1">邮箱：</span>
              <span class="span2"><input type="text" name="txtInsureEmail" id="txtInsureEmail" maxlength="30" style="line-height:30px; height:30px; width:250px; border:  solid 1px #cfcfcf"></span>
              <span class="span3" id="txtInsureEmailTip">用于找回密码之用</span>
            </LI>
          <LI><span class="span1">推荐人ID：</span>
            <span class="span2"><input type="text" name="txtSpreader" id="txtSpreader" maxlength="10" style="line-height:30px; height:30px; width:250px; border:  solid 1px #cfcfcf"></span>
              <span class="span3" id="txtSpreaderTip">如无推荐人，责无需填写</span>
          </LI>
          <LI><span class="span1"></span>
            <input type="checkbox" id="chk_agree" name="chk_agree">
          我已阅读并同意<a href="#"> 《都市棋牌游戏用户服务协议》</a><span id="id_agree" class="id_agree"></span></LI>
          <LI><span class="span1"></span>
              <input type="hidden" name="regmethod" value="reg"/>
            <input type="submit" name="btnRegister" id="btnRegister" value="立即注册">
          </LI>
        </ul>
              </form>
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