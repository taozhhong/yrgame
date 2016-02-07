<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String userid=tostr.to_utf_8(request.getParameter("userid"));
    if (!checkStr.checkIsNum(userid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>管理仓库设置</title>
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
            $.ajax({
                type:'get',//可选get
                url:'/Action/MgUserWriter.do',//这里是接收数据的程序
                data:'mgmark=editlist&userid=<%=userid%>&random='+Math.random(),//多个参数用&连接
                //async:false,
                dataType:'Json',//服务器返回的数据类型 可选XML ,Json jsonp script html text等
                success:function(json){
                    var jsonstr=eval('(' + json + ')');
                    if(jsonstr.warestr!="")
                    {
                        $('#warestr').combobox({
                            url:'/Action/WareWriter.do?waremark=list',
                            valueField:'wareid',
                            textField:'warename',
                            multiple:true,
                            panelHeight:'200',
                            editable:false
                        });
                        $('#warestr').combobox('setValues',jsonstr.warestr.split(","));
                    }
                    else
                    {
                        $('#warestr').combobox({
                            url:'/Action/WareWriter.do?waremark=list',
                            valueField:'wareid',
                            textField:'warename',
                            multiple:true,
                            panelHeight:'200',
                            editable:false
                        });
                    }
                },
                error:function(){
                    $.messager.alert('系统提示', '抱歉，管理仓库加载错误，请刷新重试', 'info');
                }
            });

            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/MgUser.do",
                    onSubmit: function () {
                        var flag = $(this).form('validate');
                        if (flag) {
                            $.mask();
                        }
                        return flag;
                    },
                    success: function (msg) {
                        $.mask.hide();
                        var jsondata = eval('(' + msg + ')');
                        if (jsondata.err == "")//表示没有出错
                        {
                            $(window.parent.$('#gd').window('close'));
                            $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                            $(window.parent.$("#MgUserList").datagrid('load'));
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
        <a id="save" icon="icon-save" class="easyui-linkbutton" plain="true">设置</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">
            <tr class="title">
                <td colspan="2">仓库信息</td>
            </tr>
            <tr>
                <td style="width: 32%;text-align: right;height: 22px;">管理仓库:</td>
                <td>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" value="<%=userid%>" name="userid"/>
                    <input type="hidden" value="ware" name="mgmethod"/>
                    <select id="warestr" class="easyui-combobox"
                            name="warestr" style="width:150px;*width: 155px;">
                    </select>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>