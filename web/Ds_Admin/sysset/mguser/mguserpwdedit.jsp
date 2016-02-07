<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String userid = tostr.to_utf_8 (request.getParameter ("userid"));
    String loginname = tostr.to_utf_8 (request.getParameter ("loginname"));
    if (! checkStr.checkIsNum (userid) || checkStr.isNull (loginname)) {
        out.print ("抱歉，缺少参数");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>设置密码</title>
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
                    url: "/Action/MgUser.do",
                    onSubmit: function () {
                        var flag = $(this).form('validate');
                        if (flag) {
                            if ($('#newpass').val() != $('#repass').val()) {
                                $.messager.alert('系统提示', '两次密码不一至！请重新输入', 'warning');
                                flag = false;
                            }
                            else {
                                $.mask();
                            }
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
<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar">
        <a id="save" icon="icon-save" class="easyui-linkbutton" plain="true">设置</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">
            <tr class="title">
                <td colspan="2">密码信息</td>
            </tr>
            <tr>
                <td style="width: 32%;text-align: right;height: 22px;">登录帐号:</td>
                <td>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" value="<%=userid%>" name="userid"/>
                    <input type="hidden" name="mgmethod" value="setpwd"/>
                    <%=loginname%>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">新密码:</td>
                <td>
                    <input type="password" name="newpass" id="newpass"
                           class="easyui-validatebox"
                           maxlength="15"
                           data-options="required:true,validType:'length[6,15]',
                              invalidMessage:'请输入6-15位登录密码',
                              missingMessage:'请输入6-15位登录密码'"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">确认新密码:</td>
                <td>
                    <input type="password" name="repass" id="repass"
                           class="easyui-validatebox"
                           maxlength="15"
                           data-options="required:true,validType:'length[6,15]',
                              invalidMessage:'请输入6-15位确认密码',
                              missingMessage:'请输入6-15位确认密码'"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>