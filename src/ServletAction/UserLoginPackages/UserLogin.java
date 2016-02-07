package ServletAction.UserLoginPackages;

import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.UserPackages.SingleUser;
import Com.SysSetPackages.UserSetPackages.UserPackages.User_Pro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-12
 * Time: 下午1:09
 * 用户登录
 */
@WebServlet (name = "UserLogin", urlPatterns = {"/Action/UserLogin.do"})
public class UserLogin extends HttpServlet
{
    private Str_code_switch tostr = null;
    private FileTarget Log = null;
    private Format_Date nowdate = null;
    private User_Pro UserPro = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        UserPro = new User_Pro ();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setContentType ("text/html;charset=utf-8");
        PrintWriter out = response.getWriter ();
        String loginname = tostr.to_utf_8 (request.getParameter ("loginname"));
        String loginpsd = tostr.to_utf_8 (request.getParameter ("loginpsd"));
        if (checkStr.isNull (loginname) || checkStr.isNull (loginpsd))
        {
            out.print ("{\"err\":\"请您输入用户名或者密码\",\"msg\":\"\"}");
            return;
        }
        Iterator UserIt = SingleUser.getInstance ().getUserDb ().UserLoginList ("y", "1",
                "userid,loginname", "manger_user", "",
                "loginname='" + loginname + "' and logpwd='" + Md5.md5 (loginpsd) + "' and islock=0", "userid", "desc").iterator ();
        while (UserIt.hasNext ())
        {
            User_Pro pro = (User_Pro) UserIt.next ();
            if (pro != null)//用户正常登录
            {
                //更新登录时间跟IP
                UserPro.setLoginip (request.getRemoteAddr ());
                UserPro.setUserid (pro.getUserid ());
                SingleUser.getInstance ().getUserDb ().UserAction ("loginup", UserPro, "");
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", pro.getUserid () + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + pro.getLoginname () + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "登录系统，登录IP: " + request.getRemoteAddr () + "</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + pro.getUserid () + "_systemlog.html"), false);
                String CookieValue = tostr.encipher (String.valueOf (pro.getUserid () + "," + pro.getLoginname ()));
                //把cookie放入内存中,用来判断用户是否正常登录
                Cookie name = new Cookie ("PayStoreSystemCookieValue", CookieValue);
                name.setPath ("/");
                name.setMaxAge(Integer.parseInt(checkStr.Parame(request,0)));
                name.setHttpOnly (true);
                response.addCookie (name);
                out.print ("{\"err\":\"\",\"msg\":\"用户正常登录\"}");
            }
            else//用户不存在
            {
                out.print ("{\"err\":\"抱歉，用户名或密码不正确\",\"msg\":\"\"}");
            }
            UserIt.remove ();
        }
    }
}
