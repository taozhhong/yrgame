package ServletAction.SysSetPackages.MgUserPackages;

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
 * User: T410
 * Date: 13-11-7
 * Time: 上午9:56
 * 用户操作
 */
@WebServlet (name = "MgUserAction", urlPatterns = {"/Action/MgUser.do"})
public class MgUserAction extends HttpServlet {
    private Str_code_switch tostr = null;
    private FileTarget Log = null;
    private User_Pro pro = null;
    private Format_Date nowdate = null;
    private UserPurview UserProxy = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        pro = new User_Pro ();
        UserProxy = new ProxyUserPurview ();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String UserStr = UserProxy.UserProxy (request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String mgmethod = tostr.to_utf_8 (request.getParameter ("mgmethod"));
        if (mgmethod.equals ("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String userid = tostr.to_utf_8(request.getParameter("userid"));//员工编号
            String loginname = checkStr.killHtml(tostr.to_utf_8(request.getParameter("loginname")));//登录帐号
            String loginpwd = tostr.to_utf_8 (request.getParameter ("loginpwd"));//登录密码
            String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));//角色ID
            String mglock = tostr.to_utf_8 (request.getParameter ("mglock"));//用户锁定
            String mgpriv = tostr.to_utf_8(request.getParameter("mgpriv"));//用户查看单据
            if (! checkStr.checkIsNum (userid) || checkStr.isNull (loginname) ||
                checkStr.isNull (loginpwd) || ! checkStr.checkIsNum (roteid) ||
                ! checkStr.checkIsNum (mglock) || !checkStr.checkIsNum(mgpriv))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid (Integer.parseInt (userid));
            pro.setLoginname(loginname);
            pro.setLoginpasd(Md5.md5(loginpwd));
            pro.setRoteid(Integer.parseInt(roteid));
            pro.setIslock(Integer.parseInt(mglock));
            pro.setIsprive(Integer.parseInt(mgpriv));
            pro.setLoginip (checkStr.getIp(request));
            pro.setLastloginip(checkStr.getIp(request));
            int o_backint = SingleUser.getInstance ().getUserDb ().MgUserAction("add", pro, "");
            switch (o_backint)
            {
                case 0:
                    out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
                    break;
                case - 1:
                    out.print ("{\"err\":\"抱歉，映射的商户或者登录帐号被占用\",\"msg\":\"\"}");
                    break;
                default:
                    Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了登录帐号为 " + loginname + " 的用户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                    out.print ("{\"err\":\"\",\"msg\":\"用户添加成功\"}");
            }
        }
        else if (mgmethod.equals ("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));
            if (! checkStr.checkIsNum (userid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid (Integer.parseInt (userid));
            if (SingleUser.getInstance ().getUserDb ().MgUserAction ("mgdel", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除了用户编号为 " + userid + " 的用户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"管理员删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (mgmethod.equals ("mod"))//修改
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String loginname = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("loginname")));//登录帐号
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));//老_员工编号
            String n_userid = tostr.to_utf_8 (request.getParameter ("n_userid"));//新_员工编号
            String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));//角色ID
            String mglock = tostr.to_utf_8 (request.getParameter ("mglock"));//用户锁定
            String mgpriv = tostr.to_utf_8 (request.getParameter ("mgpriv"));//查看单据
            if (! checkStr.checkIsNum (userid) || ! checkStr.checkIsNum (n_userid)
                || ! checkStr.checkIsNum (roteid) || ! checkStr.checkIsNum (mglock)
                    || !checkStr.checkIsNum(mgpriv))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid (Integer.parseInt (userid));
            pro.setNuserid (Integer.parseInt (n_userid));
            pro.setRoteid (Integer.parseInt (roteid));
            pro.setIslock (Integer.parseInt (mglock));
            pro.setIsprive(Integer.parseInt(mgpriv));
            int o_backint = SingleUser.getInstance ().getUserDb ().MgUserAction("mguseredit", pro, "");
            switch (o_backint) {
                case 0:
                    out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
                    break;
                case - 1:
                    out.print ("{\"err\":\"抱歉，映射的商户被占用\",\"msg\":\"\"}");
                    break;
                default:
                    Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了登录帐号为 " + loginname + " 的商户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                    out.print ("{\"err\":\"\",\"msg\":\"用户信息修改成功\"}");
            }
        }
        else if (mgmethod.equals ("setpwd"))//修改密码
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "3")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));
            String newpass = tostr.to_utf_8 (request.getParameter ("newpass"));
            String repass = tostr.to_utf_8 (request.getParameter ("repass"));
            if (! checkStr.checkIsNum (userid) || checkStr.isNull (newpass) ||
                checkStr.isNull (repass) || ! newpass.equals (repass))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid (Integer.parseInt (userid));
            pro.setLoginpasd (Md5.md5 (newpass));
            if (SingleUser.getInstance ().getUserDb ().MgUserAction ("setpwd", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "设置了用户编号为 " + userid + " 的密码</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"密码修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"\",\"msg\":\"抱歉，密码修改失败，请重试\"}");
            }
        }
        else if(mgmethod.equals("ware"))//修改管理仓库
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "3")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));
            String warestr[]=request.getParameterValues ("warestr");
            if(!checkStr.checkIsNum(userid) || warestr==null)
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setUserid(Integer.parseInt(userid));
            if(SingleUser.getInstance().getUserDb().UserAction("setware",pro,"")>0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了用户编号为 " + userid + " 的管理仓库</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"管理仓库修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"\",\"msg\":\"抱歉，修改失败，请重试\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}
