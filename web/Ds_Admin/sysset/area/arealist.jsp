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
<title>地区列表</title>
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
<script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
<script type="text/javascript">
function addArea() {
    OpenDetail('地区添加', 'areaadd.jsp?bid=<%=bid%>&sid=<%=sid%>', 580, 400);
}
function editArea() {
    var selected = $('#AreaList').treegrid('getSelected');
    if (selected) {
        OpenDetail('地区修改', 'areaedit.jsp?areaid=' + selected.id + '&bid=<%=bid%>&sid=<%=sid%>', 580, 400);
    }
    else {
        $.messager.alert('系统提示', '请先选择一行数据', 'info');
    }
}

$(function () {
    //刷新按钮
    $('#reload').bind('click', function () {
        $('#AreaList').treegrid('reload');
    });
    $('#AreaList').treegrid({
        title: '地区管理',
        iconCls: 'icon-save',//图标
        nowrap: true,
        border: false,
        autoRowHeight: false,
        striped: true,
        collapsible: true,//是否可折叠的
        loadMsg: 'loading...',
        //pagination:true,
        rownumbers: false,
        singleSelect: true,
        fit: true,
        toolbar: '#pro_toolbar',
        onBeforeLoad: function (data) {
        },//数据加载前
        onLoadError: function (data) {
            $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
        },//数据加载出错
        onLoadSuccess: function (row, data) {
            if (eval(data)[0].id == "") {
                $('#AreaList').treegrid('remove', 0);
            }
            //$.mask().hide();
        },//数据加载完闭
        idField: 'id',
        treeField: 'text',
        url: '/Action/AreaWriter.do?areamark=treelist',
        columns: [
            [
                {title: '编码', field: 'id', width: 60, align: 'center', hidden: 'true'},
                {title: '地区名称', field: 'text', width: 280, align: 'left'},
                {title: '极别', field: 'jb', width: 50, align: 'center',hidden: 'true'},
                {title: '地区简拼', field: 'py', width: 100, align: 'center'}
            ]
        ],
        rowStyler: function (index, row) {
            return 'height:28px;';
        }
    });
});

function DelArea() {
    var selected = $('#AreaList').treegrid('getSelected');
    if (selected) {
        $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该地区吗?<br>删除之后该地区下的所有数据将一同删除！</span>', function (r) {
            if (r) {
                if (selected.id == "") {
                    $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                    return false;
                }
                $.post('/Action/Area.do?areaid=' + selected.id +'&jb='+selected.jb+'&bid=<%=bid%>&sid=<%=sid%>&areamethod=del', function (msg) {
                    var jsondata = eval('(' + msg + ')');
                    if (jsondata.err == "")//表示没有出错
                    {
                        $('#AreaList').treegrid('remove', selected.id);
                        $('#AreaList').treegrid('reloadFooter');
                    }
                    else {
                        $.messager.alert('系统提示', jsondata.err, 'error');
                    }
                    //close();
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
<div region="center" icon="icon-forward" style="overflow:auto;" border="false">
    <div class="datagrid-toolbar" id="pro_toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0"))) {
        %>
        <a id="add" href="javascript:void('');" onclick="addArea();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1"))) {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelArea();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="editArea();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改</a>
        <%
            }
        %>
    </div>
    <table id="AreaList">
    </table>
</div>
</body>
</html>