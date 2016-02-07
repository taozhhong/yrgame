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
    <title>省份添加</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#parentid').combotree({
                required:true,
                invalidMessage:'请选择上级地区',
                missingMessage:'请选择上级地区',
                url:'/Action/AreaWriter.do?areamark=add_treelist',
                valueField: 'id',
                textField: 'text',
                lines:true,
                onBeforeExpand:function(node) {
                    $('#parentid').combotree("tree").tree("options").url = "/Action/AreaWriter.do?areamark=add_treelist&id=" + node.id.split(",")[0];
                },
                editable:false
            });
            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/Area.do",
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
                            $(window.parent.$("#AreaList").treegrid('reload'));
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
        <a id="save" icon="icon-save" href="javascript:void('');" class="easyui-linkbutton" plain="true">保存</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="95%" align="center">
            <tr class="title">
                <td colspan="2">地区信息</td>
            </tr>
            <tr>
                <td style="width: 32%;text-align: right;">上级地区:</td>
                <td>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" value="add" name="areamethod"/>
                    <select id="parentid" class="easyui-combotree" name="parentid"
                            style="width:150px;*width: 155px;">
                    </select>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">地区名称:</td>
                <td>
                    <input type="text" name="areaname" id="areaname" class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,15]',invalidMessage:'不能少于2个汉字！',missingMessage:'请至少输入2个汉字'"
                           maxlength="15"/>
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>