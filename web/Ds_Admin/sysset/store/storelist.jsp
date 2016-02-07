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
<title>商户管理</title>
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
<link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
<script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
<script type="text/javascript">
function AddStore() {
    OpenDetail('商户添加', 'storeadd.jsp?bid=<%=bid%>&sid=<%=sid%>', 780, 430);
}
function EditStore() {
    var selected = $('#StoreList').datagrid('getSelected');
    if (selected) {
        OpenDetail('商户信息修改', 'storeedit.jsp?storeid=' + selected.storeid + '&provinceid='+selected.provinceid+'&cityid='+selected.cityid+'&bid=<%=bid%>&sid=<%=sid%>', 780, 430);
    }
    else {
        $.messager.alert('系统提示', '请先选择一行数据', 'info');
    }
}
function StoreKey() {
    var selected = $('#StoreList').datagrid('getSelected');
    if (selected) {
        OpenDetail('商户key', 'storekey.jsp?storeid=' + selected.storeid + '&bid=<%=bid%>&sid=<%=sid%>', 580, 330);
    }
    else {
        $.messager.alert('系统提示', '请先选择一行数据', 'info');
    }
}
function StoreDataSource() {
    var selected = $('#StoreList').datagrid('getSelected');
    if (selected) {
        OpenDetail('商户数据源', 'storedatasource.jsp?storeid=' + selected.storeid + '&bid=<%=bid%>&sid=<%=sid%>', 680, 400);
    }
    else {
        $.messager.alert('系统提示', '请先选择一行数据', 'info');
    }
}


$(function () {
    //刷新按钮
    $('#reload').bind('click', function () {
        $('#StoreList').datagrid('load');
    });
    
    $('#StoreList').datagrid({
        title: '商户管理',
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
        pageSize: 50,//每页显示的记录条数，默认为10
        toolbar: '#user_toolbar',
        onBeforeLoad: function (data) {
        },//数据加载前
        onLoadError: function (data) {
            $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
        },//数据加载出错
        onLoadSuccess: function (data) {
            if (eval(data).total <= 0) {
                $('#StoreList').datagrid('deleteRow', 0);
            }
            //$.mask().hide();
        },//数据加载完闭
        url: '/Action/UserWriter.do?umark=plist',
        idField: 'storeid',
        columns: [
            [
                {title: '商户编号', field: 'storeid', width: 70, align: 'center'},
                {title: '是否管理用户', field: 'ismg', width: 80, align: 'center',
                    styler: function (value, row, index) {
                        if (value > 0) {
                            return 'background-color:#ffee00;color:red;';
                        }
                    },
                    formatter: function (value, row, index) {
                        if (value > 0)
                            return '是';
                        else
                            return '否';
                    }
                },
                {title: '商户名称', field: 'storename', width: 90, align: 'center'},
                {title: '商户简拼', field: 'storepy', width: 60, align: 'center'},
                {title: '负责人', field: 'director', width: 60, align: 'center'},
                {title: '性别', field: 'sex', width: 40, align: 'center'},
                {title: '出生年月', field: 'birdate', width: 80, align: 'center'},
                {title: '文化程度', field: 'education', width: 70, align: 'center'},
                {title: '手机', field: 'tel', width: 90, align: 'center'},
                {title: 'QQ', field: 'qq', width: 80, align: 'center'},
                {title: '邮箱', field: 'email', width: 130, align: 'center'},
                {title: '户籍', field: 'areaname', width: 180, align: 'center'},
                {title: '省份编号', field: 'provinceid', width: 10, align: 'center', hidden: true},
                {title: '市级编号', field: 'cityid', width: 10, align: 'center', hidden: true},
                {title: '地区编号', field: 'areaid', width: 10, align: 'center', hidden: true},
                {title: '商户状态', field: 'status', width: 60, align: 'center'},
                {title: '分成比例', field: 'divide', width: 60, align: 'center'},
                {title: '添加IP', field: 'regip', width: 100, align: 'center'},
                {title: '添加时间', field: 'regtime', width: 100, align: 'center'},
                {title: '备注', field: 'note', width: 300, align: 'center'}
            ]
        ],
        rowStyler: function (index, row) {
            return 'height:28px;';
        }
    });
    $('#qsubmit').bind("click", function () {
        $('#StoreList').datagrid('load', {
            umark: 'plist',
            storepy: $('#storepy').val().toUpperCase(),
            storeid:  $('#storeid').numberbox('getValue'),
            director: $('#director').val(),
            tel: $('#tel').val(),
            status: $('#status').combobox('getValue')
        });
    });
});

function DelStore() {
    var selected = $('#StoreList').datagrid('getSelected');
    if (selected) {
        $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该商户吗?<br>删除之后属于该商户下的订单记录将一同删除！</span>', function (r) {
            if (r) {
                if (selected.storeid == "") {
                    $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                }
                else
                {
                    if(selected.ismg>0)
                    {
                        $.messager.alert('系统提示', '<span style="line-height: 20px;">抱歉，该商户是管理用户,请先删除管理用户，再进行商户删除</span>', 'warning');
                    }
                    else
                    {
                        $.post('/Action/User.do?storeid=' + selected.storeid + '&bid=<%=bid%>&sid=<%=sid%>&usermethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $('#StoreList').datagrid('deleteRow', $('#StoreList').datagrid('getRowIndex', selected));
                            }
                            else {
                                $.messager.alert('系统提示', jsondata.err, 'error');
                            }
                        });
                    }
                }
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
<div region="east" title="查询条件" iconCls="icon-search"
     style="width:180px;overflow:auto;" split="true" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="100%">
            <tr>
                <td style="line-height: 14px;">
                    <b>商户编号</b>
                    <input type="text" id="storeid" name="storeid"
                           class="easyui-numberbox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>商户名称或简拼</b>
                    <input type="text" id="storepy" name="storepy" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>负责人</b>
                    <input type="text" id="director" name="director" class="easyui-validatebox"/>
                </td>
            </tr>

            <tr>
                <td style="line-height: 14px;">
                    <b>联系电话</b>
                    <input type="text" id="tel" name="tel"
                           class="easyui-validatebox"/>
                </td>
            </tr>



            <tr>
                <td style="line-height: 16px;">
                    <b>商户状态</b>
                    <select id="status" class="easyui-combobox" name="status" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false">
                        <option value="">==全部==</option>
                        <%
                            for (int i = 0; i < Field_Str.User_State.length; i++) {
                        %>
                        <option value="<%=i%>"><%=Field_Str.User_State[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>

        </table>
        <div class="datagrid-toolbar" align="left">
            <a id="qsubmit" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
        </div>
    </form>
</div>
<div region="center" style="overflow:hidden;" border="false">
    <div class="datagrid-toolbar" id="user_toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0"))) {
        %>
        <a id="add" href="javascript:void('');" onclick="AddStore();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1"))) {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelStore();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="EditStore();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "3"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="StoreKey();" class="easyui-linkbutton" plain="true"
           icon="icon-verfiy">商户key</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "4"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="StoreDataSource();" class="easyui-linkbutton" plain="true"
           icon="icon-tj">数据源</a>
        <%
            }
        %>
    </div>

    <table id="StoreList"></table>
</div>
</body>
</html>