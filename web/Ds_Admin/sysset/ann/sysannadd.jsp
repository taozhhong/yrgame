<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>系统公告发布</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#save').bind("click",function(){
                $('#form').form('submit', {
                    url:"/Action/SysAnn.do",
                    onSubmit: function () {
                        var flag = $(this).form('validate');
                        if (flag) {
                            $.mask();
                        }
                        return flag;
                    },
                    success:function(msg){
                        $.mask.hide();
                        var jsondata = eval('(' + msg + ')');
                        if (jsondata.err == "")//表示没有出错
                        {
                            $(window.parent.$('#gd').window('close'));
                            $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                            $(window.parent.$("#SysAnnList").datagrid('reload'));
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
        <a id="save" icon="icon-save" href="javascript:void('');" onclick="save();" class="easyui-linkbutton"
           plain="true">保存</a>
    </div>
</div>
<div region="center" style="padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">
            <tr class="title"><td colspan="4">公告信息</td></tr>
            <tr>
                <td style="width:14%;text-align: right;">公告标题:</td>
                <td>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" name="sysmethod" value="add"/>
                    <input type="text" name="anntitle" id="anntitle" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,50]',invalidMessage:'不能少于2个汉字',missingMessage:'请至少输入2个汉字'"
                           style="width:518px;"/>
                </td>

            </tr>

            <tr>
                <td style="text-align: right;">公告内容:</td>
                <td>
                    <textarea style="width:518px;height: 140px;" class="easyui-validatebox"
                              name="content" id="content"
                              data-options="required:true,validType:'length[10,500]',invalidMessage:'不能少于10个汉字',missingMessage:'请至少输入10个汉字'"
                              oninput="Aboutcount(this,'NodesMsg',1000);"
                              onpropertychange="Aboutcount(this,'NodesMsg',1000);"></textarea><br><br>
                    最多只能输入500个字符(1个汉字等于2个字符)，现在已输入<span id="NodesMsg">0</span>个字符
                </td>
            </tr>
            <tr>

                <td style="text-align: right;">是否锁定:</td>
                <td>
                    <select id="islock" name="islock" class="easyui-combobox"
                            style="width: 150px;*width: 155px;"
                            data-options="required:true,
                            invalidMessage:'请选择',
                            missingMessage:'请选择',
                            panelHeight:40,
                            editable:false">
                        <%
                            for(int i=0;i< Field_Str.Islock.length;i++)
                            {
                        %>
                        <option value="<%=i%>"><%=Field_Str.Islock[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>