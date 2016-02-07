package ServletAction.LoginPackages;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.Md5;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2016-01-12.
 * 用户登录
 */
@WebServlet(name = "MemberLoginAction",urlPatterns = {"/Action/MemberLogin.do"})
public class MemberLoginAction extends HttpServlet
{
    private Str_code_switch tostr =null;
    public void init()
    {
        tostr=new Str_code_switch();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String txtAccounts = checkStr.killHtml(tostr.to_utf_8(request.getParameter("txtAccounts")));
        String txtLogonPass = checkStr.killHtml(tostr.to_utf_8(request.getParameter("txtLogonPass")));
        String txtCode = tostr.to_utf_8(request.getParameter("txtCode"));
        if(checkStr.isNull(txtAccounts) || checkStr.isNull(txtLogonPass)
                || checkStr.isNull(txtCode))
        {
            response.sendRedirect("/regtip.jsp?regcode=-7");//请输入完整的登录信息
            return;
        }
        HttpSession session = request.getSession(true);
        if(session.getAttribute("randomImageStr")==null)
        {
            response.sendRedirect("/regtip.jsp?regcode=-8");//验证码已失效
            return;
        }
        String ImgCode =session.getAttribute("randomImageStr").toString();
        if(!txtCode.toLowerCase().equals(tostr.to_utf_8(ImgCode)))
        {
            response.sendRedirect("/regtip.jsp?regcode=-9");//验证码输入不一致
            return;
        }
        Iterator MemberIt= SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                "loginname='" + txtAccounts + "' and loginpwd='"+ Md5.md5(txtLogonPass)+"'", "m.memberid", "desc").iterator();
        while (MemberIt.hasNext())
        {
            Member_Pro pro=(Member_Pro)MemberIt.next();
            if(pro!=null)//表示登录帐号已注册
            {
                String Cookie_537u_Value = Str_code_switch.encipher(pro.getMemberid() + "," + pro.getLoginname() + "," + pro.getNick());
                Cookie game_cookie_value = new Cookie("537u_Cookie", Cookie_537u_Value);
                game_cookie_value.setPath("/");
                //game_cookie_value.setMaxAge(365*24*60*60);
                game_cookie_value.setHttpOnly(true);
                response.addCookie(game_cookie_value);
                response.sendRedirect("/member");
            }
            else
            {
                response.sendRedirect("/regtip.jsp?regcode=-10");//用户名或密码输入不正确
            }
            MemberIt.remove();
        }

    }
}
