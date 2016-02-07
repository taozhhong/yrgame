package ServletAction.MemberServlet;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.Md5;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created by Administrator on 2015/9/14.
 * 会员注册
 */
@WebServlet(name = "MemberRegAction",urlPatterns = {"/Member/RegAction.do"})
public class MemberRegAction extends HttpServlet
{
    private Str_code_switch tostr = null;
    private Member_Pro m_pro=null;

    public void init()
    {
        tostr=new Str_code_switch();
        m_pro=new Member_Pro();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String regmethod = tostr.to_utf_8 (request.getParameter ("regmethod"));
        if (regmethod.equals ("valiloginname"))//验证登录名
        {
            String loginname= checkStr.killHtml(tostr.to_utf_8(request.getParameter("loginname")));//登录名
            if(checkStr.isNull(loginname))
            {
                out.print ("{\"err\":\"请输入用户名\",\"msg\":\"\"}");
                return;
            }
            Iterator MemberIt= SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                    "loginname='" + loginname + "'", "m.memberid", "desc").iterator();
            while (MemberIt.hasNext())
            {
                Member_Pro pro=(Member_Pro)MemberIt.next();
                if(pro!=null)//表示登录帐号已注册
                {
                    out.print ("{\"err\":\"该用户名已被注册\",\"msg\":\"\"}");
                }
                else
                {
                    out.print ("{\"err\":\"\",\"msg\":\"该用户名可以使用\"}");
                }
                MemberIt.remove();
            }
        }
        else if(regmethod.equals ("valinick"))//验证称昵
        {
            String nick= checkStr.killHtml(tostr.to_utf_8(request.getParameter("nick")));//称昵
            if(checkStr.isNull(nick))
            {
                out.print ("{\"err\":\"请输入称昵\",\"msg\":\"\"}");
                return;
            }
            Iterator MemberIt= SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                    "nick='" + nick + "'", "m.memberid", "desc").iterator();
            while (MemberIt.hasNext())
            {
                Member_Pro pro=(Member_Pro)MemberIt.next();
                if(pro!=null)//表示登录帐号已注册
                    out.print ("{\"err\":\"该称昵已被注册\",\"msg\":\"\"}");
                else
                    out.print ("{\"err\":\"\",\"msg\":\"该称昵可以使用\"}");
                MemberIt.remove();
            }
        }
        else if(regmethod.equals ("reg"))//用户注册
        {
            String loginname= checkStr.killHtml(tostr.to_utf_8(request.getParameter("txtAccounts")));//登录名
            String nick= checkStr.killHtml(tostr.to_utf_8(request.getParameter("txtNickname")));//称昵
            String txtSex=tostr.to_utf_8(request.getParameter("txtSex"));
            String txtLogonPass=tostr.to_utf_8(request.getParameter("txtLogonPass"));
            String txtLogonPass2=tostr.to_utf_8(request.getParameter("txtLogonPass2"));
            String txtInsureEmail=tostr.to_utf_8(request.getParameter("txtInsureEmail"));
            String txtSpreader=tostr.to_utf_8(request.getParameter("txtSpreader"));
            //System.out.println(loginname+","+nick+","+txtSex+","+txtLogonPass+","+txtLogonPass2+","+txtInsureEmail);
            if(checkStr.isNull(loginname) || checkStr.isNull(nick)
                    || !checkStr.checkIsNum(txtSex) || checkStr.isNull(txtLogonPass)
                    || checkStr.isNull(txtLogonPass2)|| checkStr.checkEmail(txtInsureEmail)==false)
            {
                response.sendRedirect("/regtip.jsp?regcode=-5");
                //out.print ("{\"err\":\"请输入完整的注册信息\",\"msg\":\"\"}");
                return;
            }
            if(!txtLogonPass.equals(txtLogonPass2))
            {
                response.sendRedirect("/regtip.jsp?regcode=-4");
                //out.print ("{\"err\":\"登录密码输入不一致\",\"msg\":\"\"}");
                return;
            }

            m_pro.setLoginname(loginname);
            m_pro.setNick(nick);
            m_pro.setLoginpwd(Md5.md5(txtLogonPass));
            m_pro.setEmail(txtInsureEmail);
            m_pro.setSex(Integer.parseInt(txtSex));
            m_pro.setTjmemberid(checkStr.checkIsNum(txtSpreader)?Integer.parseInt(txtSpreader):0);
            m_pro.setRegip(checkStr.getIp(request));
            int o_backint =SingleMember.getInstance().getMemberDb().MemberAction("add",m_pro,"");
            /*String tipmsg="";
            switch (o_backint)
            {
                case -2:
                    tipmsg="抱歉，出错异常，注册失败";
                    break;
                case -1:
                    tipmsg="用户名或称昵已被注册";
                    break;
                case 0:
                    tipmsg="抱歉，注册失败";
                    break;
                default:
                    tipmsg="注册成功";
            }*/
            response.sendRedirect("/regtip.jsp?regcode="+o_backint);
        }
        else
        {
            response.sendRedirect("/regtip.jsp?regcode=-6");
        }
    }
}
