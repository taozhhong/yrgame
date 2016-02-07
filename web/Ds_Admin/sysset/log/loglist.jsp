<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>日志</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        function ViewLog()
        {
            var selected = $('#LogList').datagrid('getSelected');
            if (selected){
                window.open("../../log/"+selected.filename+".html");
            }
            else
            {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }

        function ClearLog()
        {
            var selected = $('#LogList').datagrid('getSelected');
            if (selected)
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要清空该日志吗?<br>清空之后数据将不能恢复！</span>', function (r) {
                    if (r)
                    {
                        $.post('/Action/Log.do?logname=' + selected.filename + '&bid=<%=bid%>&sid=<%=sid%>&logmethod=clear', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $.messager.alert('系统提示', jsondata.msg, 'error');
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

        function DelLog()
        {
            var selected = $('#LogList').datagrid('getSelected');
            if (selected)
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该日志吗(只能删除别人的日志，当前自己的日志不能删除)?<br>删除之后数据将不能恢复！</span>', function (r) {
                    if (r)
                    {
                        $.post('/Action/Log.do?logname=' + selected.filename + '&bid=<%=bid%>&sid=<%=sid%>&logmethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $('#LogList').datagrid('deleteRow', $('#LogList').datagrid('getRowIndex', selected));
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

        $(function(){
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#LogList').datagrid('load');
            });

            $('#LogList').datagrid({
                title:'日志列表',
                iconCls:'icon-save',//图标
                nowrap: true,
                autoRowHeight: false,
                striped: true,
                collapsible:true,//是否可折叠的
                loadMsg:'loading...',
                pagination:true,
                rownumbers:true,
                pageSize: 20,//每页显示的记录条数，默认为10
                singleSelect:true,
                fit:true,
                toolbar:'#log_toolbar',
                onBeforeLoad:function(data)
                {
                    $("#LogList").mask();
                },//数据加载前
                onLoadError:function(data)
                {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess:function(data)
                {
                    $("#LogList").mask("hide");
                    if(eval(data).total<=0)
                    {
                        $('#LogList').datagrid('deleteRow',0);
                    }
                },//数据加载完闭
                url:'/Action/LogWriter.do?logmark=list',
                idField:'userid',
                columns:[[
                    {field:'filename',title:'日志文件名称',width:200,align:'center'},
                    {field:'filetime',title:'最后操作时间',width:150,align:'center'}
                ]]
            });
            $('#querysubmit').bind("click",function(){
                $('#form').form('submit',{
                    url: "/Action/LogWriter.do?logmark=list",
                    success:function(data){
                        $('#LogList').datagrid('loadData', $.parseJSON(data));
                    }
                });
            });
        });
    </script>
</head>
<body class="easyui-layout">
    <div region="east" title="查询条件" iconCls="icon-search" icon="icon-forward" style="width:180px;overflow:auto;" split="true" border="true" >
        <form id="form" method="post" >
            <table class="table-edit" width="100%" >
                <tr><td style="line-height: 16px;"><b>管理员编号</b><span class="operator"><a name="num-opt" opt="all"></a></span>
                    <input type="text" id="mgid" name="mgid" class="easyui-validatebox" onkeyup="this.value=this.value.replace(/\D/g,'');" onafterpaste="this.value=this.value.replace(/\D/g,'');"/>
                </td></tr>
            </table>
            <div class="datagrid-toolbar">
                <a id="querysubmit" href="#" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
            </div>
        </form>
    </div>
    <div data-options="region:'center'" border="false" style="overflow:hidden">
        <div class="datagrid-toolbar" id="log_toolbar">
            <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
               data-options="iconCls:'icon-reload'">刷新</a>

            <%
                if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
                {
            %>
            <a id="add" href="javascript:void('');" onclick="ViewLog();" class="easyui-linkbutton" plain="true" data-options="iconCls:'icon-ok'">查看日志</a>
            <%
                }
                if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1")))
                {
            %>
            <a id="delete" href="javascript:void('');" onclick="DelLog();" class="easyui-linkbutton" plain="true" icon="icon-cancel">删除日志</a>
            <%
                }
                if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2")))
                {
            %>
            <a id="clear" href="javascript:void('');" onclick="ClearLog();" class="easyui-linkbutton" plain="true" icon="icon-reload">清空日志</a>
            <%
                }
            %>
        </div>
        <table id="LogList"></table>
    </div>
</body>
</html>