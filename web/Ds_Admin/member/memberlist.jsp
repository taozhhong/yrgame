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
    <title>注册会员管理</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript">
        //修改资料
        function EditMember() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected) {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                if (ids.length <= 0)
                {
                    $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                }
                else if (ids.length > 1)
                {
                    $.messager.alert('系统提示', '抱歉，请不要选择多行数据', 'warning');
                }
                else
                {
                    OpenDetail('资料修改', 'memberedit.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 580, 340);
                }
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        //设置密码
        function EditPwd() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected) {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                if (ids.length <= 0)
                {
                    $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                }
                else if (ids.length > 1)
                {
                    $.messager.alert('系统提示', '抱歉，请不要选择多行数据', 'warning');
                }
                else
                {
                    OpenDetail('密码重置', 'memberpwdedit.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 500, 400);
                }
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        //修改真实姓名
        function EditRealName() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected) {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                if (ids.length <= 0)
                {
                    $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
                }
                else if (ids.length > 1)
                {
                    $.messager.alert('系统提示', '抱歉，请不要选择多行数据', 'warning');
                }
                else
                {
                    OpenDetail('资料真实姓名', 'membernameedit.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 580, 340);
                }
            }
            else {
                $.messager.alert('系统提示', '请先选择一行数据', 'info');
            }
        }
        //设置经纪人
        function EditBokey() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected.length <= 0)
            {
                $.messager.alert('系统提示', '抱歉，请至少选择一行数据', 'warning');
            }
            else
            {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                OpenDetail('经纪人设置', 'borkeyset.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 500, 400);

            }
        }
        //设置贵宾用户
        function EditVip() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected.length <= 0)
            {
                $.messager.alert('系统提示', '抱歉，请至少选择一行数据', 'warning');
            }
            else
            {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                OpenDetail('贵宾用户设置', 'vipset.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 500, 400);

            }
        }
        //设置超级用户
        function EditSup() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected.length <= 0)
            {
                $.messager.alert('系统提示', '抱歉，请至少选择一行数据', 'warning');
            }
            else
            {
                var ids = [];
                for (var i = 0; i < selected.length; i++)
                {
                    ids.push(selected[i].code);
                }
                OpenDetail('超级用户设置', 'supset.jsp?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>', 500, 400);

            }
        }
        $(function () {
            //刷新按钮
            $('#reload').bind('click', function () {
                $('#UserList').datagrid('load');
            });

            $('#UserList').datagrid({
                title: '会员管理',
                iconCls: 'icon-save',//图标
                nowrap: true,
                autoRowHeight: true,
                border: false,
                striped: true,
                collapsible: true,//是否可折叠的
                loadMsg: 'loading...',
                pagination: true,
                rownumbers: false,
                singleSelect: false,
                fit: true,
                pageSize: 30,//每页显示的记录条数，默认为10
                toolbar: '#toolbar',
                onBeforeLoad: function (data) {
                },//数据加载前
                onLoadError: function (data) {
                    $.messager.alert('系统提示', '抱歉，数据加载出错', 'info');
                },//数据加载出错
                onLoadSuccess: function (data) {
                    if (eval(data).total <= 0) {
                        $('#UserList').datagrid('deleteRow', 0);
                    }
                    //$.mask().hide();
                },//数据加载完闭
                url: '/Action/MemberWriter.do?membermark=pagelist',
                idField: 'code',
                frozenColumns: [
                    [
                        {field: 'code', checkbox: true}
                    ]
                ],
                columns: [
                    [
                        {title: '会员ID', field: 'memberid', width: 60, align: 'center'},
                        {title: '登录帐号', field: 'loginname', width: 140, align: 'center'},
                        {title: '称昵', field: 'nick', width: 80, align: 'center'},
                        {title: '推荐人ID', field: 'tjmemberid', width: 80, align: 'center',
                            formatter:function(value,rec){
                                if(value==0)
                                    return '未有推荐人';
                            }
                        },
                        {title: '真实姓名', field: 'realname', width: 80, align: 'center'},
                        {title: '姓别', field: 'sex', width: 50, align: 'center',
                            formatter:function(value,rec){
                                if(value==0)
                                    return '男';
                                else if(value==1)
                                    return '女';
                                else
                                    return '未知';
                            }
                        },
                        {title: '生日', field: 'birdate', width: 80, align: 'center'},
                        {title: '联系手机', field: 'tel', width: 100, align: 'center'},
                        {title: 'QQ', field: 'qq', width: 80, align: 'center'},
                        {title: 'E_mail', field: 'email', width: 150, align: 'center'},
                        {title: '身份证号', field: 'idnum', width: 200, align: 'center'},
                        {title: '是否有头像', field: 'headimg', width: 80, align: 'center',
                            formatter:function(value,rec){
                                if(value=="")
                                    return '未有头像';
                                else
                                    return '有头像';
                            }
                        },
                        {title: '状态', field: 'status', width: 40, align: 'center',
                            styler:function(value,row,index){
                                if(value=='1' || value=='2')
                                {
                                    return 'background-color:#ffee00;color:red;';
                                }
                            },
                            formatter:function(value,rec){
                                if(value==0)
                                    return '正常';
                                else if(value==1)
                                    return '锁定';
                                else if(value==2)
                                    return '休眠';
                                else
                                    return '未知';
                            }
                        },
                        {title: '备注', field: 'note', width: 400, align: 'center'},
                        {title: '注册IP', field: 'regip', width: 100, align: 'center'},
                        {title: '注册时间', field: 'regtime', width: 120, align: 'center'}
                    ]
                ],
                rowStyler: function (index, row) {
                    return 'height:28px;';
                }
            });
            $('#qsubmit').bind("click", function () {
                $('#UserList').datagrid('load', {
                    membermark: 'pagelist',
                    memberid:$('#memberid').val(),
                    logemail: $('#logemail').val(),
                    name: $('#name').val(),
                    phone: $('#phone').val(),
                    tel: $("#tel").val(),
                    qq: $("#qq").val(),
                    islock: $('#islock').combobox('getValue'),
                    isvip: $('#isvip').combobox('getValue'),
                    issup: $('#issup').combobox('getValue'),
                    borkeyid: $('#borkeyid').combobox('getValue'),
                    isactivation: $('#isactivation').combobox('getValue'),
                    reg_start_time: $("#reg_start_time").datebox("getValue"),
                    reg_end_time: $("#reg_end_time").datebox("getValue"),
                    login_start_time: $("#login_start_time").datebox("getValue"),
                    login_end_time: $("#login_end_time").datebox("getValue")
                });
            });
            //输入电子邮件时,按键释放时触发
            $("#memberid,#logemail,#name,#photo,#tel").keyup(function () {
                $('#UserList').datagrid('load', {
                    membermark: 'pagelist',
                    memberid:$('#memberid').val(),
                    logname: $('#logname').val(),
                    name: $('#name').val(),
                    tel: $("#tel").val(),
                    qq: $("#qq").val(),
                    status: $('#status').combobox('getValue'),
                    reg_start_time: $("#reg_start_time").datebox("getValue"),
                    reg_end_time: $("#reg_end_time").datebox("getValue")
                });
            });
        });

        function DelUser() {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected.length <= 0)
            {
                $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
            }
            else
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除您所选的注册会员吗?<br>删除之后相关数据将一同删除！</span>', function (r) {
                    if (r)
                    {
                        var ids = [];
                        for (var i = 0; i < selected.length; i++)
                        {
                            ids.push(selected[i].code);
                        }
                        $.post('/Action/Member.do?memberid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>&membermethod=del', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $("#UserList").datagrid('load');
                            }
                            else {
                                $.messager.alert('系统提示', jsondata.err, 'error');
                            }
                            //close();
                        });
                    }
                });
            }
        }
        function DelHead()
        {
            var selected = $('#UserList').datagrid('getSelections');
            if (selected.length <= 0)
            {
                $.messager.alert('系统提示', '抱歉，请先选择一行数据', 'warning');
            }
            else
            {
                $.messager.confirm('系统提示', '<span style="line-height: 20px;">您确定要删除您所选的注册会员的头像吗?<br>删除之后相将不可恢复！</span>', function (r) {
                    if (r)
                    {
                        var ids = [];
                        for (var i = 0; i < selected.length; i++)
                        {
                            ids.push(selected[i].code);
                        }
                        $.post('/Action/SysMember.do?userid=' + ids.join(",") + '&bid=<%=bid%>&sid=<%=sid%>&usermethod=del_head', function (msg) {
                            var jsondata = eval('(' + msg + ')');
                            if (jsondata.err == "")//表示没有出错
                            {
                                $("#UserList").datagrid('load');
                            }
                            else {
                                $.messager.alert('系统提示', jsondata.err, 'error');
                            }
                            //close();
                        });
                    }
                });
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
                    <b>会员ID</b>
                    <input type="text" id="memberid" name="memberid"
                           class="easyui-numberbox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>登录名</b>
                    <input type="text" id="logname" name="logname" class="easyui-validatebox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>真实姓名</b>
                    <input type="text" id="name" name="name" class="easyui-validatebox"/>
                </td>
            </tr>

            <tr>
                <td style="line-height: 14px;">
                    <b>联系手机</b>
                    <input type="text" id="tel" name="tel"
                           class="easyui-numberbox"/>
                </td>
            </tr>
            <tr>
                <td style="line-height: 14px;">
                    <b>联系QQ</b>
                    <input type="text" id="qq" name="qq"
                           class="easyui-numberbox"/>
                </td>
            </tr>

            <tr>
                <td style="line-height: 16px;">
                    <b>会员状态</b>
                    <select id="status" class="easyui-combobox" name="status" style="width:150px;*width: 155px;"
                            data-options="required:false,editable:false,panelHeight:60">
                        <option value="">==全部==</option>
                        <option value="0">正常</option>
                        <option value="1">锁定</option>
                        <option value="2">休眠</option>
                    </select>
                </td>
            </tr>






            <tr>
                <td style="line-height: 20px;">
                    <b>注册时间</b><span class="operator"><a name="reg-time-opt" opt="date"></a></span><br>
                    大于<input type="text" id="reg_start_time" name="reg_start_time" class="easyui-datebox"
                             style="width: 126px;*width:130px;"/>

                    <div style="font-size: 1px; height: 5px;"></div>
                    小于<input type="text" id="reg_end_time" name="reg_end_time" class="easyui-datebox"
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
    <div class="datagrid-toolbar" id="toolbar">
        <a id="reload" href="javascript:void('');" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-reload'">刷新</a>
        <%
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "0")))
            {
        %>
        <a id="add" href="javascript:void('');" onclick="DelUser();" class="easyui-linkbutton" plain="true"
           data-options="iconCls:'icon-cancel'">删除</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "1")))
            {
        %>
        <a id="delete" href="javascript:void('');" onclick="EditMember();" class="easyui-linkbutton" plain="true"
           icon="icon-edit">资料修改</a>
        <%
            }
            if (! checkStr.isNull (UserProxy.UserOptProxy (request, UserProxyStr, "2"))) {
        %>
        <a id="delete" href="javascript:void('');" onclick="EditPwd();" class="easyui-linkbutton" plain="true"
           icon="icon-pwd">密码重置</a>
        <%
            }
        %>
    </div>

    <table id="UserList"></table>
</div>
</body>
</html>