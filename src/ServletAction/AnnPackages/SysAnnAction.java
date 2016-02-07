package ServletAction.AnnPackages;

import Com.AnnPackages.Ann_Pro;
import Com.AnnPackages.SingleAnn;
import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;

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
 * Date: 14-4-12
 * Time: 下午2:34
 * 操作系统公告
 */
@WebServlet (name = "SysAnnAction",urlPatterns = {"/Action/SysAnn.do"})
public class SysAnnAction extends HttpServlet
{
    private Str_code_switch tostr     = null;
    private FileTarget      Log       = null;
    private Format_Date     nowdate   = null;
    private Ann_Pro         pro       = null;
    private UserPurview     UserProxy = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        pro = new Ann_Pro ();
        UserProxy = new ProxyUserPurview ();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String UserStr = UserProxy.UserProxy (request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String sysmethod = tostr.to_utf_8 (request.getParameter ("sysmethod"));
        if (sysmethod.equals ("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String anntitle=checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("anntitle")));
            String islock=tostr.to_utf_8(request.getParameter("islock"));
            String content=tostr.to_utf_8(request.getParameter("content"));
            if(checkStr.isNull (anntitle) || checkStr.isNull (content)
               || !checkStr.checkIsNum (islock))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setAnntitle (anntitle);
            pro.setIslock (Integer.parseInt (islock));
            pro.setAnncon (content);
            if(SingleAnn.getInstance ().getAnnDb ().SysAnnDb ("add",pro,"")>0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了系统公告</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"系统公告发布成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，发布失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (sysmethod.equals ("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String sysannid=tostr.to_utf_8 (request.getParameter ("sysannid"));
            if(!checkStr.checkIsNum (sysannid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setAnnid (Integer.parseInt (sysannid));
            if(SingleAnn.getInstance ().getAnnDb ().SysAnnDb ("del",pro,"")>0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除为编号为 " + sysannid + " 的公告</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"系统公告删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (sysmethod.equals ("edit"))//修改
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String anntitle=checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("anntitle")));
            String islock=tostr.to_utf_8(request.getParameter("islock"));
            String content=tostr.to_utf_8(request.getParameter("content"));
            String sysannid=tostr.to_utf_8 (request.getParameter ("sysannid"));
            if(checkStr.isNull (anntitle) || checkStr.isNull (content)
               || !checkStr.checkIsNum (islock) || !checkStr.checkIsNum (sysannid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setAnnid (Integer.parseInt (sysannid));
            pro.setAnntitle (anntitle);
            pro.setIslock (Integer.parseInt (islock));
            pro.setAnncon (content);
            if(SingleAnn.getInstance ().getAnnDb ().SysAnnDb ("mod",pro,"")>0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了编号为 " + sysannid + " 的公告</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"系统公告修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}
