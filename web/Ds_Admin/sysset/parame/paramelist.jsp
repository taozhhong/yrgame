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
    <title>参数列表</title>
    <link rel="stylesheet" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        function AddParame()
        {
            OpenDetail('参数增加','parameadd.jsp?bid=<%=bid%>&sid=<%=sid%>',600,350);
        }
        function EditParame()
        {
            var selected = $('#ParameList').datagrid('getSelected');
            if (selected){
                OpenDetail('参数修改','parameedit.jsp?parameid='+selected.parameid+'&bid=<%=bid%>&sid=<%=sid%>',600,350);
            }
            else
            {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        $(function() {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#ParameList').datagrid('load');
            });
            $('#ParameList').datagrid({
                title:'参数列表',
                iconCls:'icon-save',//图标
                nowrap: true,
                autoRowHeight: false,
                border:false,
                striped: true,
                collapsible:true,//是否可折叠的
                loadMsg:'loading...',
                pagination:false,
                rownumbers:false,
                singleSelect:true,
                fit:true,
                toolbar:'#parame_toolbar',
                onBeforeLoad:function(data)
                {
                },//数据加载前
                onLoadError:function(data)
                {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess:function(data)
                {
                    if (eval(data).rows[0].parameid=="") {
                        $('#ParameList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                url:'/Action/ParameWriter.do?pmark=list',
                idField:'parameid',
                columns:[[
                    {field:'parameid',title:'参数编号',width:70,align:'center'},
                    {field:'paramesm',title:'参数说明',width:480,align:'center'},
                    {field:'paramevalue',title:'参数值',width:470,align:'center'}
                ]],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }
            });
        });
        function DelUnit()
        {
            var selected = $('#ParameList').datagrid('getSelected');
            if (selected){
                $.messager.confirm('系统提示', '<span style="line-height: 20px;color: #FF0000;">您确定要删除该参数吗?<br>删除之后有可能造成参数位置不准确,从而导至程序参数异常！</span>', function(r) {
                    if (r) {
                        if (selected.parameid=="") {
                            $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                            return false;
                        }
                        $.post('/Action/Parame.do?parameid='+selected.parameid+'&paramevalue='+selected.paramevalue+'&bid=<%=bid%>&sid=<%=sid%>&paramemethod=del', function(msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $("#ParameList").datagrid('load');
                            }
                            else
                            {
                                $.messager.alert('系统提示', jsondata.err, 'error');
                            }
                            //close();
                        });
                    }
                });
            }
            else
            {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
    </script>
</head>
<body class="easyui-layout">
<div region="center" border="false">
    <div class="datagrid-toolbar" id='parame_toolbar'>
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="AddParame();" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1")))
            {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelUnit();" class="easyui-linkbutton" plain="true" icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2")))
            {
        %>
        <a id="submit" href="javascript:void('');" onclick="EditParame();" class="easyui-linkbutton" plain="true" icon="icon-edit">修改</a>
        <%
            }
        %>
    </div>
    <table id="ParameList"></table>
</div>
</body>
</html>