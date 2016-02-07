<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="tostr" class="Com.SharePackages.Str_code_switch" scope="request"/>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview" %>
<%@ page import="Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview" %>
<%@ page import="Com.SysSetPackages.RoteSetPackages.Rote_Pro,Com.SysSetPackages.RoteSetPackages.SingleRote" %>
<%@ page import="Com.SharePackages.checkStr,Com.SharePackages.Field_Str,java.util.Iterator" %>
<%@include file="/Ds_Admin/include/userproxy.jsp" %>
<%
    String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));
    if (! checkStr.checkIsNum (roteid)) {
        out.print ("抱歉，缺少参数");
        return;
    }
    Iterator RoteIt = SingleRote.getInstance ().getReoteDb ().RoteList ("y", "1", "t.*", "rote t",
            "", "t.roteid=" + roteid, "t.roteid", "desc").iterator ();
    while (RoteIt.hasNext ()) {
        Rote_Pro pro = (Rote_Pro) RoteIt.next ();
        if (pro != null) {
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>角色修改</title>
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript">
        $(function () {
            $("input[name='b_roteauth']:checked").each(function () {
                $("input[name='s_roteauth_" + $(this).val() + "']").removeAttr("disabled");
                var b_id = $(this).val();
                $("input[name='s_roteauth_" + b_id + "']:checked").each(function () {
                    $("input[name='roteopt_" + b_id + "_" + $(this).val() + "']").removeAttr("disabled");
                });

            });

            $('#save').bind("click", function () {
                $('#form').form('submit', {
                    url: "/Action/Rote.do",
                    onSubmit: function () {
                        var bool = true;
                        if ($("input[name='b_roteauth']:checked").length <= 0) {
                            bool = false;
                        }
                        else {
                            $("input[name='b_roteauth']:checked").each(function () {
                                if ($("input[name='s_roteauth_" + $(this).val() + "']:checked").length <= 0)
                                //if(!$("input[name='roteopt_"+$(this).val()+"']").attr("checked"))
                                {
                                    bool = false;
                                }
//                                else
//                                {
//                                    var b_id=$(this).val();
//                                    $("input[name='s_roteauth_"+$(this).val()+"']:checked").each(function(){
//                                        if($("input[name='roteopt_"+b_id+"_"+$(this).val()+"']:checked").length<=0)
//                                        {
//                                            bool=false;
//                                        }
//                                    });
//                                }
                            });
                        }
                        var flag = $(this).form('validate');
                        if (flag && bool) {
                            $.mask();
                            return true;
                        }
                        else {
                            $.messager.alert('系统提示', '抱歉，请完整的配置角色', 'info');
                            return false;
                        }
                    },
                    success: function (msg) {
                        $.mask.hide();
                        var jsondata = eval('(' + msg + ')');
                        if (jsondata.err == "")//表示没有出错
                        {
                            $(window.parent.$('#gd').window('close'));
                            $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                            $(window.parent.$("#RoteList").datagrid('reload'));
                        }
                        else {
                            $.messager.alert('系统提示', jsondata.err, 'error');
                        }
                    }
                });
            });
        });
        function CheckBMenuBox(checkid) {
            if ($("#b_roteauth_" + checkid).is(":checked")) {
                $("input[name='s_roteauth_" + checkid + "']").removeAttr("disabled");
            }
            else {
                $("input[name='s_roteauth_" + checkid + "']").removeAttr("checked");
                $("input[name='s_roteauth_" + checkid + "']").attr("disabled", "disabled");
                $("input[name^='roteopt_" + checkid + "']").removeAttr("checked");
                $("input[name^='roteopt_" + checkid + "']").attr("disabled", "disabled");
            }
        }
        function CheckSMenuBox(check_b_id, check_s_id) {
            if ($("#s_roteauth_" + check_b_id + "_" + check_s_id).is(":checked")) {
                $("input[name='roteopt_" + check_b_id + "_" + check_s_id + "']").removeAttr("disabled");
            }
            else {
                $("input[name='roteopt_" + check_b_id + "_" + check_s_id + "']").removeAttr("checked");
                $("input[name='roteopt_" + check_b_id + "_" + check_s_id + "']").attr("disabled", "disabled");
            }
        }
    </script>
</head>
<body class="easyui-layout">
<div region="north" style="height:31px;overflow:hidden;" split="false" border="false">
    <div class="datagrid-toolbar">
        <a id="save" icon="icon-save" href="#" class="easyui-linkbutton" plain="true">修改</a>
    </div>
</div>
<div region="center" style="overflow:auto;padding:5px;" border="false">
    <form id="form" method="post">
        <table class="table-edit" width="98%" align="center">
            <tr class="title">
                <td colspan="2">角色信息</td>
            </tr>
            <tr>
                <td style="width: 20%;text-align: right;">角色名称:</td>
                <td>
                    <input type="hidden" id="rotemethod" name="rotemethod" value="mod"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <input type="hidden" value="<%=pro.getRoteid()%>" name="roteid" id="roteid"/>
                    <input type="text" name="rotename" value="<%=checkStr.unkillHtml(pro.getRotename())%>" id="rotename"
                           class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,8]',invalidMessage:'不能少于2个汉字',missingMessage:'请至少输入2个汉字'"
                           maxlength="8"/>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">角色权限:</td>
                <td>
                    <ul id="tt1" class="easyui-tree" data-options="animate:true">
                        <%
                            for (int i = 0; i < Field_Str.Menu_Type.length; i++) {
                        %>
                        <li data-options="state:<%=checkStr.IsArrayEquals(pro.getB_mkid ().split ("&"),String.valueOf (i))?"'open'":"'closed'"%>">
                            <span><input type="checkbox"
                                         name="b_roteauth" <%=checkStr.IsArrayEquals (pro.getB_mkid ().split ("&"), String.valueOf (i)) ? "checked" : ""%>
                                         value="<%=i%>" id="b_roteauth_<%=i%>" onclick="CheckBMenuBox('<%=i%>');"/><span
                                    style="vertical-align:bottom;"><%=Field_Str.Menu_Type[i]%></span></span>
                            <ul>
                                <%
                                    for (int ii = 0; ii < Field_Str.Menu_Small_Type[i].length; ii++) {
                                %>
                                <li data-options="state:<%=checkStr.IsArrayEquals(pro.getB_mkid ().split ("&"),pro.getS_mkid().split("\\|"),String.valueOf(i),String.valueOf (ii))?"'open'":"'closed'"%>">
                                    <span><input type="checkbox"
                                                 name="s_roteauth_<%=i%>" <%=checkStr.IsArrayEquals (pro.getB_mkid ().split ("&"), pro.getS_mkid ().split ("\\|"), String.valueOf (i), String.valueOf (ii)) ? "checked" : ""%>
                                                 value="<%=ii%>" id="s_roteauth_<%=i%>_<%=ii%>"
                                                 onclick="CheckSMenuBox('<%=i%>','<%=ii%>')" disabled="disabled"/><span
                                            style="vertical-align:bottom;"><%=Field_Str.Menu_Small_Type[i][ii]%></span></span>
                                    <ul>
                                        <%
                                            for (int iii = 0; iii < Field_Str.Menu_Opt[i][ii].split (",").length; iii++) {
                                        %>
                                        <li>
                                            <span><input type="checkbox"
                                                         name="roteopt_<%=i%>_<%=ii%>" <%=checkStr.IsArrayEquals (pro.getB_mkid ().split ("&"), pro.getS_mkid ().split ("\\|"), pro.getOptid ().split ("~"), String.valueOf (i), String.valueOf (ii), String.valueOf (iii)) ? "checked" : ""%>
                                                         value="<%=iii%>" id="roteopt_<%=i%>_<%=ii%>"
                                                         disabled="disabled"/><span
                                                    style="vertical-align:bottom;"><%=Field_Str.Menu_Opt[i][ii].split (",")[iii]%></span></span>
                                        </li>
                                        <%
                                            }
                                        %>
                                    </ul>
                                </li>
                                <%
                                    }
                                %>
                            </ul>
                        </li>
                        <%
                            }
                        %>
                    </ul>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">角色备注:</td>
                <td><textarea style="width:85%;height: 50px;" name="nodes" id="nodes"
                              oninput="Aboutcount(this,'NodesMsg',500);"
                              onpropertychange="Aboutcount(this,'NodesMsg',500);"><%=checkStr.IsStrNull(pro.getRemark ())%></textarea><br><br>
                    最多只能输入500个字符(1个汉字等于2个字符)，现在已输入<span id="NodesMsg">0</span>个字符
                </td>
            </tr>

        </table>
    </form>
</div>
</body>
</html>
<%
        }
        else {
            out.print ("抱歉，您要修改的角色有可能被删除");
        }
        RoteIt.remove ();
    }

%>