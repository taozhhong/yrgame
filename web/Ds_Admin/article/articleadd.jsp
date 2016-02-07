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
    <title>文章添加</title>
    <link rel="stylesheet" href="/Ds_Admin/css/web.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/<%=checkStr.Parame(request,2)%>/easyui.css">
    <link rel="stylesheet" type="text/css" href="/Ds_Admin/css/icon.css">
    <script type="text/javascript" src="/Ds_Admin/js/jquery-1.4.2.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/ajaxfileupload.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/validata.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/mask.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/xheditor/xheditor-1.2.1.min.js"></script>
    <script type="text/javascript" src="/Ds_Admin/js/xheditor/xheditor_lang/zh-cn.js"></script>
    <script type="text/javascript">

        //xbhEditor编辑器图片上传回调函数
        function insertUpload(msg) {
            var _msg = msg.toString();
            //var _picture_name = _msg.substring(_msg.lastIndexOf("/") + 1);
            //var _picture_path = Substring(_msg);
            var _str = "<div style='width:200px; float: left;text-align: center;border: 1px #cccccc solid;padding: 2px; margin-right: 5px;'>" +
                    "<div style='width:200px;margin-bottom: 5px;'><img src='"+msg+"' width='200'></div>" +
                    "<div style='width: 200px; height: 20px; text-align: center;'><input type='radio' name='pictures' id='pictures' onclick='RadioClick(this);' value='" + msg + "'/>设为封面</div>" +
                    "</div>";
            $("#xh_editor").append(_msg);
            //$("#uploadList").append(_str);
        }

        //处理服务器返回到回调函数的字符串内容,格式是JSON的数据格式.
        function Substring(s) {
            return s.substring(s.substring(0, s.lastIndexOf("/")).lastIndexOf("/"), s.length);
        }
        $(function() {
            //初始化xhEditor编辑器插件  
            $('#xh_editor').xheditor({
                tools: 'full',
                skin: 'default',
                upMultiple: true,
                upImgUrl: "/Action/XheditorLoad.do",
                upImgExt: "jpg,jpeg,gif,bmp,png",
                onUpload: insertUpload,
                cleanPaste:3,
                html5Upload: false
            });

            $('#save').bind("click",function(){
                $('#form').form('submit', {
                    url:"/Action/SysArticle.do",
                    onSubmit: function(){
                        var flag = $(this).form('validate');
                        if (flag) {
                            if($("#xh_editor").val()=="")
                            {
                                $.messager.alert('系统提示', '请输入文章内容', 'error');
                                flag=false;
                            }
                            else
                            {
                                $("#xh_editor").value = $("#xh_editor").val();
                                $.mask();
                            }
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
                            $(window.parent.$("#ArtList").datagrid('reload'));
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
            <tr class="title"><td colspan="2">文章信息</td></tr>
            <tr>
                <td style="width:8%;text-align: right;">文章分类:</td>
                <td style="line-height: 18px;">
                    <input type="hidden" name="artmethod" value="add"/>
                    <input type="hidden" value="<%=bid%>" name="bid"/>
                    <input type="hidden" value="<%=sid%>" name="sid"/>
                    <select id="cateid" name="cateid" class="easyui-combobox"
                            style="width: 150px;*width: 155px;"
                            data-options="required:false,
                            editable:false">
                        <%
                            for(int i=0;i< Field_Str.ArticleType.length;i++)
                            {
                        %>
                        <option value="<%=i%>"><%=Field_Str.ArticleType[i]%></option>
                        <%
                            }
                        %>
                    </select>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">文章标题:</td>
                <td style="width:92%;">
                    <input type="text" name="title" id="title"
                           class="easyui-validatebox"
                           data-options="required:true,validType:'length[2,20]',
                           invalidMessage:'不能少于2个汉字',
                           missingMessage:'请至少输入2个汉字'"
                           style="width:600px;"
                           maxlength="50"/>
                </td>
            </tr>


            <tr>
                <td style="text-align: right;">文章内容:</td>
                <td>
                    <textarea id="xh_editor" name="xh_editor"
                                          style="width:100%; height: 400px; border: 1px"></textarea>
                </td>
            </tr>
            <tr>
                <td style="text-align: right;">是否审核:</td>
                <td>
                    <select id="islock" name="islock" class="easyui-combobox"
                            style="width: 150px;*width: 155px;"
                            data-options="required:false,
                            editable:false,panelHeight:40,">
                        <option value="0">正常</option>
                        <option value="1">锁定</option>
                    </select>
                </td>

            </tr>
            <tr>
                <td style="text-align: right;">文章状态:</td>
                <td>
                    <select id="status" name="status" class="easyui-combobox"
                            style="width: 150px;*width: 155px;"
                            data-options="required:false,
                            editable:false,panelHeight:40,">
                        <option value="0">正常</option>
                        <option value="1">推荐</option>
                    </select>
                </td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>