<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String bid = tostr.to_utf_8 (request.getParameter ("bid"));
    String sid = tostr.to_utf_8 (request.getParameter ("sid"));
    UserPurview UserProxy = new ProxyUserPurview();
    String UserProxyStr = UserProxy.UserProxy (request);
    if (checkStr.isNull (UserProxyStr))
    {
        out.print("抱歉，您无权操作此模块");
        return;
    }
%>