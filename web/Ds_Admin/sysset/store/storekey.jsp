<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@ page import="Com.SharePackages.Md5" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));
    if (checkStr.isNull (storeid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>商户key</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {

            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/User.do",
                    onSubmit: function () {
                        var flag = $(this).form('validate');
                        if (flag) {
                            $.mask();
                        }
                        return flag;
                    },
                    success: function (msg) {
                        $.mask.hide();
                        var jsondata = eval('(' + msg + ')');
                        if (jsondata.err == "")//表示没有出错
                        {
                            $(window.parent.$('#gd').window('close'));
                            $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                            $(window.parent.$("#UserList").datagrid('load'));
                            $(window.parent.$("#UserList").datagrid('clearSelections'));
                        }
                        else {
                            $.messager.alert('系统提示', jsondata.err, 'error');
                        }
                    }
                });
            });
        });
    </script>
</head>
<body class="easyui-layout">

<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">

            <tr>
                <td colspan="2" height="22" style="color: #FF0000;">注：复制前请勿修改,不要有空格;由于加密算法,每次进入页面商户key都会不一样</td>
            </tr>
            <tr>
                <td style="text-align: right;width: 20%;" height="22">商户key:</td>
                <td>
                    <%
                        /**
                         * 商户key以三部分组成;随机数+,+商户ID
                         * 例:ade122,100000
                         * 在以ASE加密
                         */
                        String keystr=Md5.getMD5ofStr(tostr.RanPsd() + "," + storeid);
                        out.println(keystr);
                    %>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;" height="22">不带UID应用网址:</td>
                <td><%=checkStr.Parame(request,7)+"/pay/key/"+keystr%>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;" height="22">带UID应用网址:</td>
                <td><%=checkStr.Parame(request,7)+"/pay/uid/"+keystr+"/用户ID"%>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>