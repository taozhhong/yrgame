package ServletAction.UserLoginPackages;

import Com.SharePackages.AdapterFile;
import Com.SharePackages.FileTarget;
import Com.SharePackages.Format_Date;
import Com.SharePackages.ImgAdapter;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-12
 * Time: 下午3:25
 * 用户退出
 */
@WebServlet (name = "UserLoginOut", urlPatterns = {"/Action/LoginOut.do"})
public class UserLoginOut extends HttpServlet
{
    private FileTarget Log = null;
    private UserPurview UserProxy = null;
    private Format_Date nowdate = null;

    public void init () {
        Log = new AdapterFile (new ImgAdapter ());
        UserProxy = new ProxyUserPurview ();
        nowdate = new Format_Date ();
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String UserStr = UserProxy.UserCookie (request);
        //记录日志
        //Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "退出系统</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);

        Cookie name = new Cookie ("PayStoreSystemCookieValue", "");
        name.setPath ("/");
        name.setMaxAge(0);
        response.addCookie (name);
        response.sendRedirect ("/Ds_Admin/");
    }
}
