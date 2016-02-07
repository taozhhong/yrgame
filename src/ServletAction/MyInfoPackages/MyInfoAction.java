package ServletAction.MyInfoPackages;

import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;
import Com.SysSetPackages.UserSetPackages.UserPackages.SingleUser;
import Com.SysSetPackages.UserSetPackages.UserPackages.User_Pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-21
 * Time: 上午11:14
 * 修改自己的资料
 */
@WebServlet(name = "MyInfoAction",urlPatterns = {"/Action/MyInfo.do"})
public class MyInfoAction extends HttpServlet
{
    private Str_code_switch tostr     = null;
    private FileTarget      Log       = null;
    private Format_Date     nowdate   = null;
    private User_Pro        pro       = null;
    private UserPurview     UserProxy = null;

    public void init()
    {
        tostr = new Str_code_switch();
        Log = new AdapterFile(new ImgAdapter());
        nowdate = new Format_Date();
        pro = new User_Pro();
        UserProxy = new ProxyUserPurview();
    }


    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String UserStr = UserProxy.UserCookie(request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String mymethod = tostr.to_utf_8 (request.getParameter ("mymethod"));
        if (mymethod.equals ("editpwd"))//修改自己的密码
        {
            String oldpass=tostr.to_utf_8(request.getParameter("oldpass"));
            String newpass=tostr.to_utf_8(request.getParameter("newpass"));
            String repass=tostr.to_utf_8(request.getParameter("repass"));
            if ( checkStr.isNull (oldpass) || checkStr.isNull (newpass) ||
                    checkStr.isNull (repass))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            if(! newpass.equals (repass))
            {
                out.print ("{\"err\":\"两次密码输入不一致\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid(Integer.parseInt(UserStr.split(",")[0]));
            pro.setLoginname(Md5.md5(oldpass));
            pro.setLoginpasd(Md5.md5(newpass));
            int backint= SingleUser.getInstance().getUserDb().MgUserAction("my_editpwd", pro, "");
            switch (backint)
            {
                case -1:
                    out.print ("{\"err\":\"旧密码输入不正确\",\"msg\":\"\"}");
                    break;
                case 0:
                    out.print ("{\"err\":\"抱歉，密码修改失败，请重试\",\"msg\":\"\"}");
                    break;
                default:
                    Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了自己的密码</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                    out.print ("{\"err\":\"\",\"msg\":\"密码修改成功\"}");
            }
        }
        else if(mymethod.equals ("editinfo"))//修改自己的资料
        {
            String director=checkStr.killHtml(tostr.to_utf_8(request.getParameter("director")));//负责人
            String sex = tostr.to_utf_8(request.getParameter("sex"));// 性别
            String birdate = tostr.to_utf_8(request.getParameter("birdate"));// 出生年月
            String edu = tostr.to_utf_8(request.getParameter("edu"));// 学历
            String tel = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("tel")));//联系电话
            String qq = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("qq"))); //联系QQ
            String email = checkStr.killHtml(tostr.to_utf_8(request.getParameter("email")));//电子邮件
            String nodes = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("nodes"))); //备注
            if (! checkStr.checkIsNum (sex) || !checkStr.isMobile(tel)
                    || checkStr.isNull (birdate)
                    || ! checkStr.checkIsNum (edu))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setStoreid(Integer.parseInt(UserStr.split(",")[0]));
            pro.setDirector(director);
            pro.setSex(Integer.parseInt (sex));
            pro.setBirdate (birdate);
            pro.setEducation (Integer.parseInt (edu));
            pro.setTelnum (tel);
            pro.setQq(qq);
            pro.setEmail (email);
            pro.setNodes (nodes);
            if (SingleUser.getInstance ().getUserDb ().UserAction ("my_editinfo", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了自己的基本资料</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"个人资料修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，个人资料修改失败\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}
