<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%
    UserPurview UserProxy = new ProxyUserPurview();
    String UserProxyStr = UserProxy.UserCookie (request);
    if (checkStr.isNull (UserProxyStr))
    {
        out.print ("抱歉，您无权操作此模块");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>修改资料</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#form').form('load', '/Action/UserWriter.do?umark=mlist&storid=<%=UserProxyStr.split(",")[0]%>&random=' + Math.random());

            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/MyInfo.do",
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
<form id="form" method="post">

<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar">
        <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">修改</a>
    </div>
</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
            <table class="table-edit" width="98%" align="center">

                <tr class="title">
                    <td colspan="4">基本信息</td>
                </tr>
                <tr>
                    <td>商户名称:</td>
                    <td>
                        <input type="text" name="storename" id="storename"
                               class="easyui-validatebox"
                               maxlength="15" disabled/>
                    </td>
                    <td>负责人:</td>
                    <td>
                        <input type="text" name="director" id="director"
                               class="easyui-validatebox"
                               data-options="required:true,
                                       invalidMessage:'请输入负责人',
                                       missingMessage:'请输入负责人'"
                               maxlength="5"/>
                    </td>
                </tr>
                <tr>
                    <td>性别:</td>
                    <td>
                        <input type="hidden" name="mymethod" value="editinfo"/>
                        <select id="sex" class="easyui-combobox" name="sex"
                                style="width:150px;*width: 155px;"
                                data-options="required:true,
                                        invalidMessage:'请选择员工性别',
                                        missingMessage:'请选择员工性别'">
                            <%
                                for (int i = 0; i < Field_Str.User_Sex.length; i++) {
                            %>
                            <option value="<%=i%>"><%=Field_Str.User_Sex[i]%>
                            </option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                    <td>出生年月:</td>
                    <td>
                        <input type="text" id="birdate" name="birdate"
                               class="easyui-datebox"
                               data-options="required:true,
                                       invalidMessage:'请选择出生年月',
                                       missingMessage:'请选择出生年月'"/>
                    </td>
                </tr>
                <tr>
                    <td>电子邮件:</td>
                    <td>
                        <input type="text" name="email" id="email"
                               class="easyui-validatebox"
                               maxlength="30"/>
                    </td>
                    <td>学历:</td>
                    <td>
                        <select id="edu" class="easyui-combobox" name="edu" style="width:150px;*width: 155px;"
                                data-options="required:true,invalidMessage:'请选择学历',missingMessage:'请选择学历',editable:false">
                            <%
                                for (int i = 0; i < Field_Str.User_Edu.length; i++) {
                            %>
                            <option value="<%=i%>"><%=Field_Str.User_Edu[i]%>
                            </option>
                            <%
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>联系电话:</td>
                    <td>
                        <input type="text" name="tel" id="tel"
                               class="easyui-validatebox"
                               data-options="required:true,
                                       invalidMessage:'请输入常用电话号码',
                                       missingMessage:'请输入常用电话号码'"
                               maxlength="15"/>
                    </td>
                    <td>联系QQ:</td>
                    <td>
                        <input type="text" name="qq" id="qq"
                               class="easyui-validatebox"
                               maxlength="15"/>
                    </td>
                </tr>

                <tr>
                    <td>备注:</td>
                    <td colspan="3"><textarea style="width:85%;height: 50px;" name="nodes" id="nodes"
                                              oninput="Aboutcount(this,'NodesMsg',1000);"
                                              onpropertychange="Aboutcount(this,'NodesMsg',1000);"></textarea><br><br>
                        最多只能输入1000个字符(1个汉字等于2个字符)，现在已输入<span id="NodesMsg">0</span>个字符
                    </td>
                </tr>

            </table>




</div>
</form>
</body>
</html>