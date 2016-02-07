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
    <title>文章列表</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        $(function () {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#ArtList').datagrid('load');
            });
            $('#ArtList').datagrid({
                title: '文章列表',
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
                        $('#ArtList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                pageSize: 30,//每页显示的记录条数，默认为10
                url: '/Action/SysArticleWriter.do?artmark=pagelist',
                idField: 'articleid',
                columns: [
                    [
                        {field: 'articleid', title: '文章ID', width: 60, align: 'center'},
                        {field: 'catename', title: '文章分类', width: 80, align: 'center'},
                        {field: 'articletitle', title: '文章标题', width: 400, align: 'left'},
                        {field: 'islock', title: '是否审核', width: 60, align: 'center',
                            formatter:function(value,rec){
                                if(value==0)
                                    return '正常';
                                else
                                    return '锁定';
                            },
                            styler:function(value,row,index){
                                if(value==1)
                                    return 'background-color:#ffee00;color:red;';
                            }
                        },
                        {field: 'status', title: '状态', width: 50, align: 'center',
                            formatter:function(value,rec){
                                if(value==0)
                                    return '正常';
                                else
                                    return '推荐';
                            },
                            styler:function(value,row,index){
                                if(value==1)
                                    return 'background-color:#ffee00;color:red;';
                            }
                        },
                        {field: 'addtime', title: '添加时间', width: 120, align: 'center'}
                    ]
                ],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }
            });
            $('#qsubmit').bind("click", function () {
                $('#ArtList').datagrid('load', {
                    artmark: 'pagelist',
                    artid: $('#artid').val(),
                    theme: $('#theme').val(),
                    cateid: $('#cateid').combobox('getValue'),
                    islock: $('#islock').combobox('getValue'),
                    status: $('#status').combobox('getValue')
                });
            });
            //输入名称或简拼时,按键释放时触发
            $("#artid,#theme").keyup(function () {
                $('#ArtList').datagrid('load', {
                    artmark: 'pagelist',
                    artid: $('#artid').val(),
                    theme: $('#theme').val(),
                    cateid: $('#cateid').combobox('getValue'),
                    islock: $('#islock').combobox('getValue'),
                    status: $('#status').combobox('getValue')
                });
            });
        });

        function AddArticle()
        {
            OpenDetail('文章添加', 'articleadd.jsp?bid=<%=bid%>&sid=<%=sid%>', 950, 600);
        }
        function EditArticle()
        {
            var selected = $('#ArtList').datagrid('getSelected');
            if (selected) {
                OpenDetail('文章修改', 'articleedit.jsp?artid='+selected.articleid+'&bid=<%=bid%>&sid=<%=sid%>', 950, 600);
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }

        }
        function DelArticle() {
            var selected = $('#ArtList').datagrid('getSelected');
            if (selected)
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除您所选的文章吗?<br>删除之后将不可恢复！</span>', function (r) {
                    if (r)
                    {
                        $.post('/Action/SysArticle.do?artid=' + selected.articleid+'&bid=<%=bid%>&sid=<%=sid%>&artmethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $("#ArtList").datagrid('reload');
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
<div region="east" title="查询条件" iconCls="icon-search"
     style="width:180px;overflow:auto;" split="true" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="100%">
            <tr>
                <td style="line-height: 14px;">
                    <b>文章ID</b>
                    <input type="text" id="artid" name="artid"
                           class="easyui-numberbox"/>
                </td>
            </tr>

            <tr>
                <td style="line-height: 14px;">
                    <b>文章标题</b>
                    <input type="text" id="theme" name="theme" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 16px;">
                    <b>文章类型</b>
                    <select id="cateid" class="easyui-combobox" name="cateid" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false">
                        <option value="">==全部==</option>
                        <%
                            for(int i=0;i<Field_Str.ArticleType.length;i++)
                            {
                        %>
                        <option value="<%=i%>"><%=Field_Str.ArticleType[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="line-height: 16px;">
                    <b>是否锁定</b>
                    <select id="islock" class="easyui-combobox" name="islock" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false,panelHeight:60">
                        <option value="">==全部==</option>
                        <option value="0">正常</option>
                        <option value="1">锁定</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="line-height: 16px;">
                    <b>是否推荐</b>
                    <select id="status" class="easyui-combobox" name="status" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false,panelHeight:60">
                        <option value="">==全部==</option>
                        <option value="0">正常</option>
                        <option value="1">推荐</option>
                    </select>
                </td>
            </tr>
        </table>
        <div class="datagrid-toolbar" align="left">
            <a id="qsubmit" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
        </div>
    </form>
</div>
<div region="center" border="false">
    <div class="datagrid-toolbar" id="toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="AddArticle();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">添加</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="DelArticle();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="EditArticle();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改</a>
        <%
            }
        %>

    </div>
    <table id="ArtList"></table>
</div>
</body>
</html>