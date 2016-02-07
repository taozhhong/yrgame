package ServletAction.LoginPackages;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.Md5;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Administrator on 2016-01-12.
 * 会员修改登录密码
 */
@WebServlet(name = "MemberModifyPass",urlPatterns = {"/Action/ModifyPass.do"})
public class MemberModifyPass extends HttpServlet
{
    private Str_code_switch tostr=null;
    private UserPurview UserProxy = null;
    private Member_Pro mpro=null;

    public void init()
    {
        tostr=new Str_code_switch();
        UserProxy = new ProxyUserPurview();
        mpro=new Member_Pro();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String UserStr = UserProxy.WebUserSelect (request);
        if (checkStr.isNull (UserStr))
        {
            response.sendRedirect("/reg/gamelogin");//重新登录
            return;
        }
        String txtOldPass = tostr.to_utf_8(request.getParameter("txtOldPass"));
        String txtNewPass = tostr.to_utf_8(request.getParameter("txtNewPass"));
        String txtNewPass2 = tostr.to_utf_8(request.getParameter("txtNewPass2"));
        if (checkStr.isNull(txtOldPass) || checkStr.isNull(txtNewPass)
                || checkStr.isNull(txtNewPass2))
        {
            response.sendRedirect("/member/MemberTip.jsp?errcode=-1");//请输入密码信息
            return;
        }
        if (!txtNewPass.equals(txtNewPass2))
        {
            response.sendRedirect("/member/MemberTip.jsp?errcode=-2");//两次新密码输入不一致
            return;
        }
        Iterator MemberIt = SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                "m.memberid=" + UserStr.split(",")[0] + " and loginpwd='" + Md5.md5(txtOldPass) + "'", "m.memberid", "desc").iterator();
        while (MemberIt.hasNext())
        {
            Member_Pro pro = (Member_Pro) MemberIt.next();
            if (pro != null)//表示旧密码正确
            {
                mpro.setLoginpwd(Md5.md5(txtNewPass));
                mpro.setMemberid(Integer.parseInt(UserStr.split(",")[0]));
                if(SingleMember.getInstance().getMemberDb().MemberAction("setpwd",mpro,"")>0)
                {
                    response.sendRedirect("/member/MemberTip.jsp?errcode=0");//修改成功
                }
                else
                {
                    response.sendRedirect("/member/MemberTip.jsp?errcode=-3");//密码更新失败
                }
            }
            else
            {
                response.sendRedirect("/member/MemberTip.jsp?errcode=-4");//旧密码输入错误
            }
        }
    }
}
