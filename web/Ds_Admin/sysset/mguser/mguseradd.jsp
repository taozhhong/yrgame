<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户添加</title>
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

            $("#store").combogrid({
                url: "/Action/UserWriter.do?umark=plist&mgstr=y",
                loadMsg: 'loading...',
                method: 'post',
                editable: 'true',
                panelWidth: 485,
                panelHeight: 200,
                rownumbers: false,
                fitColumns: false,
                border: false,
                pageSize: 20,//每页显示的记录条数，默认为10
                singleSelect: true,
                pagination: true,
                idField: 'storeid',
                textField: 'storename',
                required:true,
                validType:'length[2,25]',
                invalidMessage:'请选择映射的商户',
                missingMessage:'请输入商户名称或简拼进行查询',
                onBeforeLoad: function (data) {
                },//数据加载前
                onLoadError: function (data) {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess: function (data) {
                    if (eval(data).total <= 0) {
                        $('#store').combogrid("grid").datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                columns: [
                    [
                        {title: '商户编号', field: 'storeid', width: 70, align: 'center'},
                        {title: '商户名称', field: 'storename', width: 90, align: 'center'},
                        {title: '负责人', field: 'director', width: 80, align: 'center'},
                        {title: '负责人电话', field: 'tel', width: 90, align: 'center'},
                        {title: '商户状态', field: 'status', width: 60, align: 'center'}
                    ]
                ],
                keyHandler: {
                    query: function (q) {
                        $('#store').combogrid("grid").datagrid("reload", {'namepy': q });
                        $('#store').combogrid("setValue", q);
                    }
                },
                onClickRow: function (rowIndex, data) {
                    $('#userid').val(data.storeid);
                }
            });
            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/MgUser.do",
                    onSubmit: function () {
                        var flag = $(this).form('validate');
                        if (flag) {
                            if ($('#userid').val() == "")
                            {
                                $.messager.alert('系统提示', '请选择映射的商户', 'info');
                                return false;
                            }
                            else
                            {
                                $.mask();
                                return true;
                            }
                        }
                        else
                        {
                            return false;
                        }
                    },
                    success: function (msg) {
                        $.mask.hide();
                        var jsondata = eval('(' + msg + ')');
                        if (jsondata.err == "")//表示没有出错
                        {
                            $(window.parent.$('#gd').window('close'));
                            $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                            $(window.parent.$("#MgUserList").datagrid('reload'));
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
        <a id="save" icon="icon-save" class="easyui-linkbutton" plain="true">保存</a>
    </div>
</div>
<div region="center" style="overflow:auto;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">
            <tr>
                <td colspan="4" style="height: 20px;color: #FF0000;">注：一个商户只能映射一个用户，不能重复映射。登录帐号添加之后将不能修改</td>
            </tr>
            <tr>
                <td>映射商户:</td>
                <td colspan="3"><select id="store" name="store"
                                        style="width:245px;"></select></td>
            </tr>
            <tr>
                <td>登录帐号:</td>
                <td>
                    <input type="hidden" value="add" name="mgmethod"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" id="userid" name="userid"/>
                    <input type="text" name="loginname" id="loginname"
                           class="easyui-validatebox"
                           maxlength="10"
                           data-options="required:true,validType:'length[6,10]',
                              invalidMessage:'请输入6-10位登录帐号',
                              missingMessage:'请输入6-10位登录帐号'"/>
                </td>
                <td>登录密码:</td>
                <td><input type="password" name="loginpwd" id="loginpwd"
                           class="easyui-validatebox"
                           maxlength="15"
                           data-options="required:true,validType:'length[6,15]',
                              invalidMessage:'请输入6-15位登录密码',
                              missingMessage:'请输入6-15位登录密码'"/>
                </td>
            </tr>

            <tr>
                <td>所属角色:</td>
                <td>
                    <select id="roteid" class="easyui-combobox" name="roteid"
                            style="width:150px;*width: 155px;"
                            data-options="
                                        required:true,
                                        url:'/Action/RoteWriter.do?rotemark=list',
                                        valueField:'roteid',
                                        textField:'rotename',
                                        editable:false">

                    </select>
                </td>
                <td>用户锁定:</td>
                <td>
                    <select id="mglock" class="easyui-combobox" name="mglock" style="width:150px;*width: 155px;"
                            data-options="required:true,
                            invalidMessage:'请选择用户状态',
                            missingMessage:'请选择用户状态',
                            panelHeight:40,
                            editable:false">
                        <option value="0">正常</option>
                        <option value="1">锁定</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>查看订单:</td>
                <td colspan="3">
                    <select id="mgpriv" class="easyui-combobox" name="mgpriv" style="width:150px;*width: 155px;"
                            data-options="required:true,
                            invalidMessage:'请选择商户查看单据的权限',
                            missingMessage:'请选择商户查看单据的权限',
                            panelHeight:40,
                            editable:false">
                        <option value="0">允许查看全部</option>
                        <option value="1">允许查看自己</option>
                    </select>
                    <span style="color: #FF0000;">注：订单是指充值记录</span>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>