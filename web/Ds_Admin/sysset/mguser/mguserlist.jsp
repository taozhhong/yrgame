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
    <title>用户管理</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        function AddMgUser() {
            OpenDetail('用户添加', 'mguseradd.jsp?bid=<%=bid%>&sid=<%=sid%>', 600, 340);
        }
        function EditMgUser() {
            var selected = $('#MgUserList').datagrid('getSelected');
            if (selected) {
                OpenDetail('用户修改', 'mguseredit.jsp?userid=' + selected.userid + '&bid=<%=bid%>&sid=<%=sid%>', 600, 380);
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        //设置密码
        function EditMgUserPwd() {
            var selected = $('#MgUserList').datagrid('getSelected');
            if (selected) {
                OpenDetail('设置密码', 'mguserpwdedit.jsp?userid=' + selected.userid + '&loginname=' + selected.loginname + '&bid=<%=bid%>&sid=<%=sid%>', 500, 400);
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }


        $(function () {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#MgUserList').datagrid('load');
            });

            $('#MgUserList').datagrid({
                title: '用户管理',
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
                pageSize: 20,//每页显示的记录条数，默认为10
                toolbar: '#user_toolbar',
                onBeforeLoad: function (data) {
                },//数据加载前
                onLoadError: function (data) {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess: function (data) {
                    if (eval(data).total <= 0) {
                        $('#MgUserList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                url: '/Action/MgUserWriter.do?mgmark=mglist&myuserid=<%=UserProxyStr.split(",")[0]%>',
                idField: 'userid',
                columns: [
                    [
                        {title: '用户编号', field: 'userid', width: 70, align: 'center'},
                        {title: '登录账号', field: 'loginname', width: 90, align: 'center'},
                        {title: '角色', field: 'rotename', width: 100, align: 'center'},
                        {title: '商户名称', field: 'storename', width: 180, align: 'center'},
                        {title: '负责人', field: 'director', width: 60, align: 'center'},
                        {title: '手机', field: 'tel', width: 100, align: 'center'},
                        {title: '商户状态', field: 'status', width: 60, align: 'center'},
                        {title: '用户锁定', field: 'islock', width: 60, align: 'center',
                            styler: function (value, row, index) {
                                if (value == '1') {
                                    return 'background-color:#ffee00;color:red;';
                                }
                            },
                            formatter:function(value,rec){
                                if(value=='0')
                                    return '正常';
                                else
                                    return '锁定';
                            }
                        },
                        {title: '查看单据', field: 'isprive', width: 60, align: 'center',
                            styler: function (value, row, index) {
                                if (value == '0') {
                                    return 'background-color:#ffee00;color:red;';
                                }
                            },
                            formatter:function(value,rec){
                                if(value=='0')
                                    return '查看全部';
                                else
                                    return '查看自己';
                            }
                        },
                        {title: '最后登录时间', field: 'logintime', width: 110, align: 'center'},
                        {title: '最后登录IP', field: 'loginip', width: 100, align: 'center'},
                        {title: '上次登录时间', field: 'lastlogintime', width: 110, align: 'center'},
                        {title: '上次登录IP', field: 'lastloginip', width: 100, align: 'center'},
                        {title: '添加时间', field: 'regtime', width: 110, align: 'center'}
                    ]
                ],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }

            });
            $('#qsubmit').bind("click", function () {
                $('#MgUserList').datagrid('load', {
                    mgmark: 'mglist',
                    logingname: $('#logingname').val(),
                    userid: $('#userid').numberbox('getValue'),
                    namepy: $('#namepy').val(),
                    tel: $('#tel').val(),
                    roteid: $('#roteid').combobox('getValue'),
                    userstatus: $('#userstatus').combobox('getValue'),
                    lock: $('#lock').combobox('getValue'),
                    start_time: $("#start_time").datebox("getValue"),
                    end_time: $("#end_time").datebox("getValue")
                });
            });
        });

        function DelMgUser() {

            var selected = $('#MgUserList').datagrid('getSelected');
            if (selected)
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除该用户吗?<br>删除之后该用户下的所有数据将一同删除！</span>', function (r) {
                    if (r)
                    {
                        $.post('/Action/MgUser.do?userid=' + selected.userid + '&bid=<%=bid%>&sid=<%=sid%>&mgmethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $('#MgUserList').datagrid('deleteRow', $('#MgUserList').datagrid('getRowIndex', selected));
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
                    <b>用户编号</b>
                    <input type="text" id="userid" name="userid"
                           class="easyui-numberbox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>登录帐号</b>
                    <input type="text" id="logingname" name="logingname" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>商户名称或简拼</b>
                    <input type="text" id="namepy" name="namepy" class="easyui-validatebox"/>
                </td>
            </tr>

            <tr>
                <td style="line-height: 16px;">
                    <b>所属角色</b>
                    <select id="roteid" class="easyui-combobox" name="roteid"
                            style="width:150px;*width: 155px;"
                            data-options="
                                        url:'/Action/RoteWriter.do?rotemark=list',
                                        valueField:'roteid',
                                        textField:'rotename',
                                        editable:true">
                    </select>
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
                    <b>用户状态</b>
                    <select id="userstatus" class="easyui-combobox" name="userstatus" style="width:150px;*width: 155px;"
                            data-options="required:false,
                            editable:false,
                            panelHeight:60
                            ">
                        <option value="">==全部==</option>
                        <option value="0">正常</option>
                        <option value="1">锁定</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="line-height: 16px;">
                    <b>用户锁定</b>
                    <select id="lock" class="easyui-combobox" name="lock" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false,panelHeight:60">
                        <option value="">==全部==</option>
                        <%
                            for (int i = 0; i < Field_Str.Islock.length; i++) {
                        %>
                        <option value="<%=i%>"><%=Field_Str.Islock[i]%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="line-height: 20px;">
                    <b>最后登录时间</b><span class="operator"><a name="time-opt" opt="date"></a></span><br>
                    大于<input type="text" id="start_time" name="start_time" class="easyui-datebox"
                             style="width: 126px;*width:130px;"/>

                    <div style="font-size: 1px; height: 5px;"></div>
                    小于<input type="text" id="end_time" name="end_time" class="easyui-datebox"
                             style="width: 126px;*width:130px;"/>
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
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="AddMgUser();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-add'">新增</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1"))) {
        %>
        <a id="delete" href="javascript:void('');" onclick="DelMgUser();" class="easyui-linkbutton" plain="true"
           icon="icon-cancel">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="EditMgUser();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">修改资料</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "3"))) {
        %>
        <a id="submit" href="javascript:void('');" onclick="EditMgUserPwd();" class="easyui-linkbutton" plain="true"
           icon="icon-pwd">设置密码</a>
        <%
            }
        %>
    </div>

    <table id="MgUserList"></table>
</div>
</body>
</html>