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
    <title>系统公告列表</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        function AddSysAnn() {
            OpenDetail('系统公告发布', 'sysannadd.jsp?bid=<%=bid%>&sid=<%=sid%>', 800, 440);
        }
        function EditSysAnn() {
            var selected = $('#SysAnnList').datagrid('getSelected');
            if (selected) {
                OpenDetail('系统公告修改', 'sysannedit.jsp?sysannid=' + selected.sysannid+'&bid=<%=bid%>&sid=<%=sid%>', 800, 440);
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        $(function () {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#SysAnnList').datagrid('load');
            });
            $('#SysAnnList').datagrid({
                title: '系统公告管理',
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
                toolbar: '#toolbar',
                onBeforeLoad: function (data) {
                },//数据加载前
                onLoadError: function (data) {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess: function (data) {
                    if (eval(data).total <= 0) {
                        $('#SysAnnList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                pageSize: 30,//每页显示的记录条数，默认为10
                url: '/Action/SysAnnWriter.do?sysmark=plist',
                idField: 'sysannid',
                columns: [
                    [
                        {field: 'sysannid', title: '编号', width: 50, align: 'center'},
                        {field: 'sysanntitle', title: '公告标题', width: 360, align: 'center'},
                        {field: 'sysislock', title: '是否锁定', width: 80, align: 'center',
                            styler:function(value,row,index){
                                if (value =='锁定'){
                                    return 'background-color:#ffee00;color:red;';
                                }
                            }
                        },
                        {field: 'sysanndate', title: '发布时间', width: 120, align: 'center'}
                    ]
                ],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }
            });
        });
        function DelSysAnn() {
            var selected = $('#SysAnnList').datagrid('getSelected');
            if (selected) {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该公告吗?<br>删除之后将不可恢复！</span>', function (r) {
                    if (r) {
                        if (selected.sysannid == "") {
                            $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                            return false;
                        }
                        $.post('/Action/SysAnn.do?sysannid=' + selected.sysannid + '&bid=<%=bid%>&sid=<%=sid%>&sysmethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                                $('#SysAnnList').datagrid('deleteRow', $('#SysAnnList').datagrid('getRowIndex', selected));
                            else
                                $.messager.alert('系统提示', jsondata.err, 'error');
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
    <div class="datagrid-toolbar" id="toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="AddSysAnn();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1")))
            {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelSysAnn();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2")))
            {
        %>
        <a id="delete" href="javascript:void('');" onclick="EditSysAnn();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改</a>
        <%
            }
        %>
    </div>
    <table id="SysAnnList"></table>
</div>
</body>
</html>