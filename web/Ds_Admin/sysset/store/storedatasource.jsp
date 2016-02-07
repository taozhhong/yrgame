<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));
    if (! checkStr.checkIsNum (storeid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>商户数据源</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function() {
            $('#form').form('load', '/Action/ParameWriter.do?pmark=dlist&storeid=<%=storeid%>&parameid=0&random=' + Math.random());
            $('#save').bind("click",function(){
                $('#form').form('submit', {
                    url:"/Action/User.do",
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
                            $(window.parent.$("#StoreList").datagrid('clearSelections'));
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
            <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="form" method="post">
           <table class="table-edit"  width="98%" align="center">
           		<tr class="title"><td colspan="2">数据源信息</td></tr>
                   <tr>
                       <td style="text-align: right;" height="22">唯一标识符:</td>
                       <td><%=storeid%></td>
                   </tr>

                    <tr><td style="width: 35%;text-align: right;">连接数据库IP:</td>
                    <td>
                    <input type="hidden" name="usermethod" value="data"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                        <input type="hidden" value="<%=storeid%>" name="key"/>
                    <input type="hidden" name="datastatus"/>
                    <input type="hidden" name="parameid" value="0"/>
                    <input type="hidden" value="<%=storeid%>" name="storeid"/>
                    <input type="text" name="dataip" id="dataip"
                           class="easyui-validatebox"
                           data-options="required:true,
                           validType:'length[7,200]',
                           invalidMessage:'请输入连接数据库IP',
                           missingMessage:'请输入连接数据库IP'"/>
                </td>
				</tr>
               <tr>
                   <td style="text-align: right;">连接数据库驱动:</td>
                   <td>
                       <input type="text" name="datadirver" id="datadirver"
                              class="easyui-validatebox"
                              data-options="required:true,
                               validType:'length[2,200]',
                               invalidMessage:'连接数据库驱动',
                               missingMessage:'连接数据库驱动'"/>
                   </td>
               </tr>
                <tr>
                    <td style="text-align: right;">连接数据库用户名:</td>
                    <td>
                        <input type="text" name="datauser" id="datauser"
                               class="easyui-validatebox"
                               data-options="required:true,
                               validType:'length[2,10]',
                               invalidMessage:'请输入连接数据库用户名',
                               missingMessage:'请输入连接数据库用户名'"/>
                    </td>
                </tr>
               <tr>
                   <td style="text-align: right;">连接数据库密码:</td>
                   <td>
                       <input type="text" name="datapwd" id="datapwd"
                              class="easyui-validatebox"
                              data-options="required:true,
                              validType:'length[1,15]',
                               invalidMessage:'请输入连接数据库密码',
                               missingMessage:'请输入连接数据库密码'"/>
                   </td>
               </tr>
               <tr>
                   <td style="text-align: right;">请输入连接数据库的库名:</td>
                   <td>
                       <input type="text" name="dataname" id="dataname"
                              class="easyui-validatebox"
                              data-options="required:true,
                              validType:'length[1,15]',
                               invalidMessage:'请输入连接数据库的库名',
                               missingMessage:'请输入连接数据库的库名'"/>
                   </td>
               </tr>
               <tr>
                   <td style="text-align: right;">是否检测充值用户的ID:</td>
                   <td>
                       <select id="isck" class="easyui-combobox" name="isck" style="width:150px;*width: 155px;"
                               data-options="required:true,
                            invalidMessage:'是否检测充值用户的ID',
                            missingMessage:'是否检测充值用户的ID',
                            panelHeight:40,
                            editable:false">
                           <option value="0">是</option>
                           <option value="1">否</option>
                       </select>
                   </td>
               </tr>
               <tr>
                   <td style="text-align: right;">请输入检测用户ID的数据的库名:</td>
                   <td>
                       <input type="text" name="datauid" id="datauid"
                              class="easyui-validatebox"
                              data-options="required:true,
                              validType:'length[1,15]',
                               invalidMessage:'请输入检测用户ID的数据的库名',
                               missingMessage:'请输入检测用户ID的数据的库名'"/>
                       注：如没有，请填写连接数据库的库名
                   </td>
               </tr>

           </table>
       </form>
	</div>
</body>
</html>