/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.53
 * Generated at: 2016-01-12 14:01:28 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.member;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import Com.SharePackages.checkStr;
import Com.SharePackages.Field_Str;
import java.util.Iterator;

public final class ModifyPass_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("/include/top.jsp", Long.valueOf(1452602257915L));
    _jspx_dependants.put("/include/member_left.jsp", Long.valueOf(1452602804515L));
    _jspx_dependants.put("/include/foot.jsp", Long.valueOf(1443359240835L));
    _jspx_dependants.put("/include/cookie_name.jsp", Long.valueOf(1452570253595L));
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write('\r');
      out.write('\n');
      Com.SharePackages.Str_code_switch tostr = null;
      tostr = (Com.SharePackages.Str_code_switch) _jspx_page_context.getAttribute("tostr", javax.servlet.jsp.PageContext.REQUEST_SCOPE);
      if (tostr == null){
        tostr = new Com.SharePackages.Str_code_switch();
        _jspx_page_context.setAttribute("tostr", tostr, javax.servlet.jsp.PageContext.REQUEST_SCOPE);
      }
      out.write("\r\n");
      out.write("\r\n");

    Cookie[] name1=request.getCookies();
    String cookie_game_value="";
    if(name1!=null)
    {
        for(int i=0;i<name1.length;i++)
        {
            Cookie c=name1[i];
            if(c.getName().equals("537u_Cookie"))
            {
                cookie_game_value=tostr.decrypt(c.getValue());
                break;

            }
            else
            {
                cookie_game_value="";
            }
        }
    }

      out.write('\r');
      out.write('\n');

    if(checkStr.isNull(cookie_game_value))
    {
        response.sendRedirect("/reg/gamelogin");
        return;
    }

      out.write("\r\n");
      out.write("<!DOCTYPE html>\r\n");
      out.write("<html xmlns=\"/www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n");
      out.write("    <title>我想游游戏－好玩的棋牌电玩游戏平台</title>\r\n");
      out.write("    <meta name=\"keywords\" content=\"我想游游戏,捕鱼,斗地主,梭哈,金花,水果机\"/>\r\n");
      out.write("    <meta name=\"description\" content=\"信誉棋牌游戏,真正赚金的游戏-渔人。\"/>\r\n");
      out.write("    <link href=\"/style1/css.css\" rel=\"stylesheet\" type=\"text/css\"/>\r\n");
      out.write("    <script type=\"text/javascript\" src=\"/js/jquery-1.4.2.min.js\"></script>\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("        $(document).ready(function() {\r\n");
      out.write("            $(\"#btnUpdate\").bind(\"click\",function(){\r\n");
      out.write("                var bool=true;\r\n");
      out.write("                if($.trim($(\"#txtOldPass\").val())==\"\")\r\n");
      out.write("                {\r\n");
      out.write("                    bool=false;\r\n");
      out.write("                    $(\"#txtOldPassTip\").html(\"请输入您的原始密码！\");\r\n");
      out.write("                }\r\n");
      out.write("                else\r\n");
      out.write("                {\r\n");
      out.write("                    $(\"#txtOldPassTip\").html(\"\");\r\n");
      out.write("                }\r\n");
      out.write("                if($.trim($(\"#txtNewPass\").val()).length<6 || $.trim($(\"#txtNewPass\").val()).length>20)\r\n");
      out.write("                {\r\n");
      out.write("                    bool=false;\r\n");
      out.write("                    $(\"#txtNewPassTip\").html(\"请输入新密码，至少需要6位！\");\r\n");
      out.write("                }\r\n");
      out.write("                else\r\n");
      out.write("                {\r\n");
      out.write("                    $(\"#txtNewPassTip\").html(\"\");\r\n");
      out.write("                }\r\n");
      out.write("                if($.trim($(\"#txtNewPass\").val())!=$.trim($(\"#txtNewPass2\").val()))\r\n");
      out.write("                {\r\n");
      out.write("                    bool=false;\r\n");
      out.write("                    $(\"#txtNewPass2Tip\").html(\"新密码输入不一致！\");\r\n");
      out.write("                }\r\n");
      out.write("                if(bool)\r\n");
      out.write("                {\r\n");
      out.write("                    $(\"#btnUpdate\").attr({\"disabled\":\"disabled\"});\r\n");
      out.write("                    $(\"#form1\").submit();\r\n");
      out.write("                }\r\n");
      out.write("            });\r\n");
      out.write("        });\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("\r\n");
      out.write("<body>\r\n");
      out.write("\r\n");
      out.write("<!--头部开始-->\r\n");
      out.write("\r\n");
      out.write("<div id=\"head\">\r\n");
      out.write("    <div class=\"topbar\">\r\n");
      out.write("        <div class=\"logo\">\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"sideBar\">\r\n");
      out.write("            <div class=\"user\">\r\n");
      out.write("                <a href=\"/reg/gamelogin\" class=\"login\">账号登陆</a><a href=\"/reg/gamereg\">注册帐号</a>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div class=\"top_menu\">\r\n");
      out.write("        <ul>\r\n");
      out.write("            <li class=\"curr\"><a href=\"/\">网站首页</a></li>\r\n");
      out.write("            <li><a href=\"/member\">用户中心</a></li>\r\n");
      out.write("            <li><a href=\"/download\">游戏下载</a></li>\r\n");
      out.write("            <li><a href=\"http://www.40zf.com/wxy/bank\">充值中心</a></li>\r\n");
      out.write("            <!--<li><a href=\"Popularize.html\">玩家推广</a></li>\r\n");
      out.write("            <li><a href=\"Roulette.html\">幸运抽奖</a></li>-->\r\n");
      out.write("            <li><a href=\"/news/list/cate/0\">棋牌资讯</a></li>\r\n");
      out.write("            <li><a href=\"/news/help/cate/1\">帮助中心</a></li>\r\n");
      out.write("        </ul>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>");
      out.write("\r\n");
      out.write("<!--头部结束-->\r\n");
      out.write("<div class=\"midbox\">\r\n");
      out.write("    ");
      out.write("<div class=\"content_left\">\r\n");
      out.write("\r\n");
      out.write("    <div class=\"userinfoset\">\r\n");
      out.write("        <ul>\r\n");
      out.write("            <li><a href=\"/member\">会员中心</a></li>\r\n");
      out.write("\r\n");
      out.write("            <li><a href=\"/reg/modpass\">修改登录密码</a></li>\r\n");
      out.write("            <!--<li><a href=\"ModifyInsurePass.html\">修改银行密码</a> </li>\r\n");
      out.write("             <li><a href=\"ReInsurePass.html\">找回银行密码</a> </li>-->\r\n");
      out.write("            <li><a href=\"/download\">游戏下载</a></li>\r\n");
      out.write("\r\n");
      out.write("            <li><a href=\"http://www.40zf.com/wxy/bank\">充值中心</a></li>\r\n");
      out.write("            <li><a href=\"/news/help/cate/1\">新手帮助</a></li>\r\n");
      out.write("            <li><a href=\"/news/cust/id/3/41\">关于我们</a></li>\r\n");
      out.write("            <li><a href=\"/news/cust/id/4/42\">联系我们</a></li>\r\n");
      out.write("        </ul>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("    <!-- 个人中心 管理 -->\r\n");
      out.write("    <div class=\"midright\">\r\n");
      out.write("        <div class=\"manage\">\r\n");
      out.write("            <form name=\"form1\" action=\"/Action/ModifyPass.do\" id=\"form1\" method=\"post\" onsubmit=\"return false;\">\r\n");
      out.write("            <div class=\"midrigtpos\">当前位置：首页 &gt; 会员中心 &gt; 修改登录密码</div>\r\n");
      out.write("            <ul>\r\n");
      out.write("                <li>\r\n");
      out.write("                    <p class=\"pwd\">原登录密码：</p>\r\n");
      out.write("                    <input name=\"txtOldPass\" id=\"txtOldPass\" class=\"input_m\" type=\"password\" maxlength=\"20\">\r\n");
      out.write("                    <span id=\"txtOldPassTip\"></span>\r\n");
      out.write("                </li>\r\n");
      out.write("                <li>\r\n");
      out.write("                    <p class=\"pwd\">新登录密码：</p>\r\n");
      out.write("                    <input name=\"txtNewPass\" id=\"txtNewPass\" class=\"input_m\" type=\"password\" maxlength=\"20\">\r\n");
      out.write("                    <span id=\"txtNewPassTip\"></span>\r\n");
      out.write("                </li>\r\n");
      out.write("                <li>\r\n");
      out.write("                    <p class=\"pwd\">确认登录密码：</p>\r\n");
      out.write("                    <input name=\"txtNewPass2\" id=\"txtNewPass2\" class=\"input_m\" type=\"password\" maxlength=\"20\">\r\n");
      out.write("                    <span id=\"txtNewPass2Tip\"></span>\r\n");
      out.write("                </li>\r\n");
      out.write("                <li>\r\n");
      out.write("                    <p class=\"pwd\">&nbsp;</p>\r\n");
      out.write("                    <input name=\"btnUpdate\" value=\"确 定\" id=\"btnUpdate\" type=\"submit\"> &nbsp;<input name=\"button\" value=\"取 消\" type=\"reset\">\r\n");
      out.write("                </li>\r\n");
      out.write("            </ul>\r\n");
      out.write("            <div class=\"line30\"></div>\r\n");
      out.write("            <div class=\"ts\">\r\n");
      out.write("                <p><font color=\"#398700\">【温馨提示】</font></p>\r\n");
      out.write("                <p>登录密码修改成功后，请重新用新密码登录！</p>\r\n");
      out.write("            </div>\r\n");
      out.write("                </form>\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("    <div style=\"clear:both;\"></div>\r\n");
      out.write("</div>\r\n");
      out.write("<!--底部开始-->\r\n");
      out.write("\r\n");
      out.write("<div id=\"foot\">\r\n");
      out.write("    <div class=\"box\">\r\n");
      out.write("        <div class=\"bottom_logo\">\r\n");
      out.write("            <img src=\"/images/bottom_logo.png\" alt=\"最好玩的棋牌平台\" width=\"158\" height=\"72\"/>\t\t</div>\r\n");
      out.write("        <div class=\"copyright\">\r\n");
      out.write("            健康游戏公告：抵制不良游戏 拒绝盗版游戏 注意自我保护 谨防受骗上当 适度游戏益脑 沉迷游戏伤身 合理安排时间 享受健康生活<br/>\r\n");
      out.write("            禁止任何利用本平台游戏进行赌博的行为，让我们共同净化游戏环境，一旦发现有违反用户协议的行为，我们将会冻结此玩家账号\r\n");
      out.write("            <br/>\r\n");
      out.write("            Copyright © 2015 Www.jiaqp.Com Asiasoft Inc.All Rights Reserved\r\n");
      out.write("            <a href=\"http://www.jiaqp.com\">渔人游戏中心</a> 版权所有 浙网文[2015]0342-112号 浙B2-20080029\t\t</div>\r\n");
      out.write("        <div class=\"remove\">\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</div>\r\n");
      out.write("\r\n");
      out.write("<!--底部结束-->\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
