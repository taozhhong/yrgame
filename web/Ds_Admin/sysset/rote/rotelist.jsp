<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色设置</title>
    <link rel="stylesheet" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        function AddRote() {
            OpenDetail('角色添加', 'roteadd.jsp?bid=<%=bid%>&sid=<%=sid%>', 540, 580);
        }
        function EditRote() {
            var selected = $('#RoteList').datagrid('getSelected');
            if (selected) {
                OpenDetail('角色修改', 'roteedit.jsp?roteid=' + selected.roteid + '&bid=<%=bid%>&sid=<%=sid%>', 540, 580);
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        $(function () {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#RoteList').datagrid('load');
            });
            $('#RoteList').datagrid({
                title: '角色管理',
                iconCls: 'icon-save',//图标
                nowrap: true,
                autoRowHeight: false,
                border: false,
                striped: true,
                collapsible: true,//是否可折叠的
                loadMsg: 'loading...',
                pagination: true,
                rownumbers: false,
                singleSelect: true,
                fit: true,
                toolbar: '#rote_toolbar',
                onBeforeLoad: function (data) {
                },//数据加载前
                onLoadError: function (data) {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess: function (data) {
                    if (eval(data).total <= 0) {
                        $('#RoteList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                pageSize: 20,//每页显示的记录条数，默认为10
                url: '/Action/RoteWriter.do?rotemark=pagelist',
                idField: 'roteid',
                columns: [
                    [
                        {field: 'roteid', title: '编号', width: 70, align: 'center'},
                        {field: 'rotename', title: '角色名称', width: 160, align: 'center'},
                        {field: 'remork', title: '备注', width: 300, align: 'center'}
                    ]
                ],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }
            });
        });
        function DelRote() {
            var selected = $('#RoteList').datagrid('getSelected');
            if (selected) {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该角色吗?<br>删除之后属于该角色下的管理用户将一同删除！</span>', function (r) {
                    if (r) {
                        if (selected.roteid == "") {
                            $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                            return false;
                        }
                        $.post('/Action/Rote.do?roteid=' + selected.roteid + '&bid=<%=bid%>&sid=<%=sid%>&rotemethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $('#RoteList').datagrid('deleteRow', $('#RoteList').datagrid('getRowIndex', selected));
                            }
                            else {
                                $.messager.alert('系统提示', jsondata.err, 'error');
                            }
                        });
                    }
                });
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }

    </script>
</head>
<body class="easyui-layout">
<div region="center" border="false">
    <div class="datagrid-toolbar" id="rote_toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="AddRote();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1"))) {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelRote();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="EditRote();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改</a>
        <%
            }
        %>
    </div>
    <table id="RoteList"></table>
</div>
</body>
</html>