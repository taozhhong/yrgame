<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%
    UserPurview UserProxy = new ProxyUserPurview ();
    String UserStr = UserProxy.UserSelect (request);
    if (checkStr.isNull (UserStr) || ! UserStr.contains (",")) {
        out.print ("抱歉，请重新登录之后在操作");
        return;
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title><%=checkStr.Parame(request,1)%></title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css"/>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css"/>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css"/>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jqueryUtil.js"></script>
    <script type="text/javascript" src='/Ds_Admin/js/outlook2.js'></script>
    <script type="text/javascript" src='/Ds_Admin/js/validata.js'></script>
    <script type="text/javascript">
        function EditMyPwd() {
            OpenDetail('修改密码', '../myinfo/mypwdedit.jsp', 580, 350);
        }
        function EditMyInfo() {
            OpenDetail('修改资料', '../myinfo/myinfoedit.jsp', 780, 500);
        }
        $(function () {
            $('#loginOut').click(function () {
                $.messager.confirm('系统提示', '您确定要退出本次登录吗?', function (r) {
                    if (r) {
                        location.href = '/Action/LoginOut.do';
                    }
                });
            })
        });
    </script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
<noscript>
    <div style=" position:absolute; z-index:100000; height:2046px;top:0;left:0; width:100%; background:white; text-align:center;">
        <img src="/Ds_Admin/images/noscript.gif" alt='抱歉，请开启脚本支持！'/>
    </div>
</noscript>
<div region="north" split="true" border="false"
     style="overflow: hidden; height: 30px;background: url(/Ds_Admin/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;line-height: 20px;color: #fff; font-family: Verdana, '微软雅黑','黑体',serif;">
    <span style="float:right; padding-right:20px;" class="head">欢迎您 <%=UserStr.split (",")[1]%> <a
            href="javascript:void('');" id="editpass" class="top" onclick="EditMyPwd();">修改密码</a> <a href="javascript:void('');"
                                                                              onclick="EditMyInfo();"
                                                                              class="top" id="editinfo">修改基本资料</a> <a
            href="javascript:void('');" class="top" id="loginOut">安全退出</a></span>
    <span style="padding-left:10px; font-size: 14px; "><img src="/Ds_Admin/images/blocks.gif" width="20" height="20"
                                                            align="absmiddle"/> 欢迎您进入<%=checkStr.Parame(request,1)%></span>
</div>

<div region="west" hide="true" border="true" split="true" title="导航菜单" style="width:180px;" id="west">
    <div class="easyui-accordion" data-options="fit:true,border:false">
        <%
            for (int i = 0; i < UserStr.split (",")[4].split ("&").length; i++) {
        %>
        <div border="false" title="<%=Field_Str.Menu_Type[Integer.parseInt(UserStr.split(",")[4].split("&")[i])]%>"
             style="padding:10px;overflow:auto;">
            <ul class="easyui-tree">
                <%
                    //for (int ii = 0; ii < UserStr.split (",")[5].split ("\\|")[Integer.parseInt (UserStr.split (",")[4].split ("&")[i])].split ("&").length; ii++) {
                    for (int ii = 0; ii < UserStr.split (",")[5].split ("\\|")[i].split ("&").length; ii++) {
                %>
                <li>
                    <span><a href="javascript:void('');"
                             onclick="addTab('<%=Field_Str.Menu_Small_Type[Integer.parseInt(UserStr.split(",")[4].split("&")[i])][Integer.parseInt(UserStr.split(",")[5].split("\\|")[i].split("&")[ii])]%>','../<%=Field_Str.Menu_Type_Link[Integer.parseInt(UserStr.split(",")[4].split("&")[i])][Integer.parseInt(UserStr.split(",")[5].split("\\|")[i].split("&")[ii])]+"bid="+UserStr.split(",")[4].split("&")[i]+"&sid="+UserStr.split(",")[5].split("\\|")[i].split("&")[ii]%>');"><%=Field_Str.Menu_Small_Type[Integer.parseInt (UserStr.split (",")[4].split ("&")[i])][Integer.parseInt (UserStr.split (",")[5].split ("\\|")[i].split ("&")[ii])]%>
                    </a></span>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
        <%
            }
        %>
    </div>
</div>
<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
    <div id="tabs" class="easyui-tabs" fit="true" border="false">
        <div title="欢迎使用" style="padding:20px;overflow:hidden; color:red; ">
            <h1 style="font-size:24px;">* 欢迎您进入<%=checkStr.Parame(request,1)%></h1>
        </div>
    </div>
</div>


<!--修改密码窗口-->


<div id="mm" class="easyui-menu" style="width:150px;">
    <div id="mm-tabupdate">刷新</div>
    <div class="menu-sep"></div>
    <div id="mm-tabclose">关闭</div>
    <div id="mm-tabcloseall">全部关闭</div>
    <div id="mm-tabcloseother">除此之外全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-tabcloseright">当前页右侧全部关闭</div>
    <div id="mm-tabcloseleft">当前页左侧全部关闭</div>
    <div class="menu-sep"></div>
    <div id="mm-exit">退出</div>
</div>
</body>
</html>