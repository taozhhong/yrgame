<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str,java.util.Iterator" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));
    String provinceid = tostr.to_utf_8 (request.getParameter ("provinceid"));
    String cityid = tostr.to_utf_8 (request.getParameter ("cityid"));
    if (! checkStr.checkIsNum (storeid) || ! checkStr.checkIsNum (provinceid)
        || ! checkStr.checkIsNum (cityid))
    {
        out.print ("抱歉，缺少参数");
        return;
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>商户修改</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {
            $('#form').form('load', '/Action/UserWriter.do?umark=mlist&storeid=<%=storeid%>&random=' + Math.random());

            $('#areaid').combotree({
                required: true,
                invalidMessage: '请选择户籍所在地',
                missingMessage: '请选择户籍所在地',
                valueField: 'id',
                textField: 'text',
                lines:true,
                animate: false,//动画效果
                url: '/Action/AreaWriter.do?areamark=m_treelist&provinceid=<%=provinceid%>&cityid=<%=cityid%>&random='+Math.random(),
                //选择树节点触发事件
                onSelect: function (node) {
                    //返回树对象
                    var tree = $(this).tree;
                    //选中的节点是否为叶子节点,如果不是叶子节点,清除选中
                    var isLeaf = tree('isLeaf', node.target);
                    if (!isLeaf) {
                        $('#areaid').combotree('clear');
                    }
                }
            });

            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/User.do",
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
                            $(window.parent.$("#StoreList").datagrid('load'));
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
<form id="form" method="post">

<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar">
        <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">修改</a>
    </div>
</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">

        <table class="table-edit" width="98%" align="center">

            <tr class="title">
                <td colspan="4">基本信息</td>
            </tr>
            <tr>
                <td>商户名称:</td>
                <td>
                    <input type="hidden" value="mod" name="usermethod"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" name="storeid"/>
                    <input type="text" name="storename" id="storename"
                           class="easyui-validatebox"
                           data-options="required:true,
                           validType:'length[1,30]',
                           invalidMessage:'请输入商户名称',
                           missingMessage:'请输入商户名称'"/>
                </td>
                <td>负责人:</td>
                <td><input type="text" name="director" id="director"
                           class="easyui-validatebox"
                           data-options="required:true,
                           validType:'length[2,5]',
                           invalidMessage:'请输入负责人姓名',
                           missingMessage:'请输入负责人姓名'"/>
                </td>
            </tr>
            <tr>
                <td>性别:</td>
                <td>
                    <select id="sex" class="easyui-combobox" name="sex"
                            style="width:150px;*width: 155px;"
                            data-options="required:true,
                                        invalidMessage:'请选择负责人性别',
                                        missingMessage:'请选择负责人性别',
                                        panelHeight:40,
                                        editable:false">
                        <%
                            for (int i = 0; i < Field_Str.User_Sex.length; i++) {
                        %>
                        <option value="<%=i%>"><%=Field_Str.User_Sex[i]%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
                <td>出生年月:</td>
                <td>
                    <input type="text" id="birdate" name="birdate"
                           class="easyui-datebox"
                           data-options="required:true,
                                       invalidMessage:'请选择出生年月',
                                       missingMessage:'请选择出生年月',
                                       editable:false"/>
                </td>
            </tr>
            <tr>
                <td>商户所在地:</td>
                <td>
                    <select id="areaid" class="easyui-combotree" name="areaid"
                            style="width:150px;*width: 155px;">
                    </select>
                </td>
                <td>学历:</td>
                <td>
                    <select id="edu" class="easyui-combobox" name="edu" style="width:150px;*width: 155px;"
                            data-options="required:true,
                            invalidMessage:'请选择学历',
                            missingMessage:'请选择学历',
                            panelHeight:130,
                            editable:false">
                        <%
                            for (int i = 0; i < Field_Str.User_Edu.length; i++) {
                        %>
                        <option value="<%=i%>"><%=Field_Str.User_Edu[i]%>
                        </option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td>联系电话:</td>
                <td>
                    <input type="text" name="tel" id="tel"
                           class="easyui-validatebox"
                           maxlength="15"/>
                </td>
                <td>联系QQ:</td>
                <td>
                    <input type="text" name="qq" id="qq"
                           class="easyui-numberbox"
                           data-options="min:1,precision:0"
                           maxlength="15"/>
                </td>
            </tr>
            <tr>
                <td>电子邮件:</td>
                <td>
                    <input type="text" name="email" id="email"
                           class="easyui-validatebox"
                           maxlength="30"/>
                </td>
                <td>分成比例:</td>
                <td>
                    <input type="text" name="divide" id="divide"
                           class="easyui-numberbox"
                           data-options="required:true,
                           min:0,
                           max:99,
                           precision:1,
                           invalidMessage:'请输入分成比例',
                           missingMessage:'最大不能超过100'"/> %
                </td>

            </tr>

            <tr>
                <td>商户状态:</td>
                <td>
                    <select id="status" class="easyui-combobox" name="status" style="width:150px;*width: 155px;"
                            data-options="required:true,
                            invalidMessage:'请选择商户状态',
                            missingMessage:'请选择商户状态',
                            panelHeight:40,
                            editable:false">
                        <%
                            for (int i = 0; i < Field_Str.User_State.length; i++) {
                        %>
                        <option value="<%=i%>"><%=Field_Str.User_State[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
                <td></td>
                <td></td>
            </tr>
            <tr>
                <td>备注:</td>
                <td colspan="3"><textarea style="width:85%;height: 50px;" name="nodes" id="nodes"
                                          oninput="Aboutcount(this,'NodesMsg',1000);"
                                          onpropertychange="Aboutcount(this,'NodesMsg',1000);"></textarea><br><br>
                    最多只能输入1000个字符(1个汉字等于2个字符)，现在已输入<span id="NodesMsg">0</span>个字符
                </td>
            </tr>
        </table>

    </div>
</form>
</body>
</html>