<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));
    if (! checkStr.checkIsNum (memberid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>会员资料修改</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#form').form('load', '/Action/MemberWriter.do?membermark=mlist&memberid=<%=memberid%>&random=' + Math.random());
            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/Member.do",
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
                            $(window.parent.$("#UserList").datagrid('reload'));
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
        <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">修改</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">

            <tr class="title">
                <td colspan="4">基本信息</td>
            </tr>
            <tr>
                <td>登录邮件:</td>
                <td>
                    <input type="hidden" value="edit" name="membermethod"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" name="memberid"/>
                    <input type="text" name="loginname" id="loginname"
                           class="easyui-validatebox"
                           maxlength="30"
                           disabled
                           data-options="required:true,validType:'length[1,30]',
                           invalidMessage:'请输入登录名',missingMessage:'请输入登录名'"/>
                </td>
                <td>真实姓名:</td>
                <td><input type="text" name="realname" id="realname"
                           class="easyui-validatebox"
                           maxlength="6"
                           disabled
                           data-options="required:true,validType:'length[2,6]',
                           invalidMessage:'请输入真实姓名',missingMessage:'请输入真实姓名'"/>
                </td>
            </tr>
            <tr>
                <td>联系手机:</td>
                <td>
                    <input type="text" name="tel" id="tel"
                           class="easyui-numberbox"
                           maxlength="11"
                           />
                </td>
                <td>出生年月:</td>
                <td>
                    <input type="text" id="birdate" name="birdate"
                           class="easyui-datebox"
                           />
                </td>
            </tr>
            <tr>
                <td>QQ:</td>
                <td>
                    <input type="text" name="qq" id="qq"
                           class="easyui-numberbox"
                           maxlength="15"/>
                </td>
                <td>Email:</td>
                <td>
                    <input type="text" name="email" id="email"
                           class="easyui-validatebox"
                           maxlength="30"/>
                </td>
            </tr>
            <tr>
                <td>会员状态:</td>
                <td>
                    <select id="islock" class="easyui-combobox" name="islock" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false,panelHeight:60">
                        <%
                            for (int i = 0; i < Field_Str.Islock.length; i++)
                            {
                        %>
                        <option value="<%=i%>"><%=Field_Str.Islock[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
                <td>身份证号:</td>
                <td>
                    <input type="text" name="idnum" id="idnum"
                           class="easyui-validatebox"
                           maxlength="18"/>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>