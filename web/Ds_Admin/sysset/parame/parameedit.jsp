<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String parameid=tostr.to_utf_8(request.getParameter("parameid"));
    if(!checkStr.checkIsNum(parameid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>参数修改</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#form').form('load', '/Action/ParameWriter.do?pmark=mlist&parameid=<%=parameid%>&random=' + Math.random());
            $('#save').bind("click",function(){
                $('#form').form('submit', {
                    url:"/Action/Parame.do",
                    onSubmit: function(){
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
                            $(window.parent.$("#ParameList").datagrid('reload'));
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
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >修改</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="form" method="post">
           <table class="table-edit"  width="98%" align="center">
           		<tr class="title"><td colspan="2">参数信息</td></tr>

	           	<tr><td style="width: 20%;text-align: right">参数说明:</td>
                    <td>
                    <input type="hidden" id="paramemethod" name="paramemethod" value="mod"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" name="parameid" value="<%=parameid%>"/>
                        <input type="text" name="paramesm" id="paramesm" class="easyui-validatebox"
                               data-options="required:true,
                           validType:'length[2,105]',
                           invalidMessage:'不能少于2个汉字',
                           missingMessage:'请至少输入2个汉字'"
                               maxlength="105" style="width: 320px;"/>
                    </td>

					</tr>
                <tr>
                    <td style="text-align: right">参数值:</td>
                    <td>
                        <input type="text" name="paramevalue" id="paramevalue"
                               class="easyui-validatebox"
                               data-options="required:true,
                               invalidMessage:'不能少于1个字符',
                               missingMessage:'请至少输入1个字符'"
                               style="width: 320px;"/>
                    </td>
                </tr>
           </table>
       </form>
	</div>
</body>
</html>