package ServletAction.SysSetPackages.AreaPackages;

import Com.SharePackages.*;
import Com.SysSetPackages.AreaSetPackages.Area_Pro;
import Com.SysSetPackages.AreaSetPackages.SingleArea;
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
 * User: lenovo
 * Date: 13-8-23
 * Time: 下午12:43
 * 地区操作
 */
@WebServlet (name = "AreaAction", urlPatterns = {"/Action/Area.do"})
public class AreaAction extends HttpServlet {
    private Str_code_switch tostr = null;
    private FileTarget Log = null;
    private Format_Date nowdate = null;
    private Area_Pro pro = null;
    private UserPurview UserProxy = null;

    public void init () {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        pro = new Area_Pro ();
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
        String areamethod = tostr.to_utf_8 (request.getParameter ("areamethod"));
        if (areamethod.equals ("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String actionname="";//操作标签符
            String parentid=tostr.to_utf_8 (request.getParameter("parentid"));
            String areaname = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("areaname")));
            if (checkStr.isNull (parentid) || checkStr.isNull (areaname)
                    || !parentid.contains (","))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setPid (Integer.parseInt (parentid.split (",")[0]));//上级ID
            pro.setName (areaname);
            pro.setStatus (0);
            pro.setNamepy (GetPinYin.getPinYinHeadChar (areaname).toUpperCase ());
            if(parentid.split (",")[1].equals ("0"))//添加省
            {
                actionname="addprovince";
            }
            else if(parentid.split (",")[1].equals ("1"))//添加市
            {
                actionname="addcity";
            }
            else if(parentid.split (",")[1].equals ("2"))//添加区
            {
                actionname="addarea";
            }
            else if(parentid.split (",")[1].equals ("3"))//添加小区
            {
                actionname="addvillage";
            }
            else
            {
                out.print ("{\"err\":\""+checkStr.Message (request,"",2)+"\",\"msg\":\"\"}");
                return;
            }
            if (SingleArea.getInstance ().getArea ().AreaAction (actionname, pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了名为 " + areaname + " 的地区</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"地区信息添加成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (areamethod.equals ("mod"))//编辑省
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String actionname="";//操作标签符
            String areaname = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("areaname")));
            String jb=tostr.to_utf_8 (request.getParameter("jb"));
            String areaid = tostr.to_utf_8 (request.getParameter ("areaid"));
            if (!checkStr.checkIsNum (jb) || checkStr.isNull (areaname)
                || ! checkStr.checkIsNum (areaid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setName (areaname);
            pro.setStatus (0);
            pro.setNamepy (GetPinYin.getPinYinHeadChar (areaname).toUpperCase ());
            pro.setId (Integer.parseInt (areaid));
            if(jb.equals ("1"))//修改省
            {
                actionname="editprovince";
            }
            else if(jb.equals ("2"))//修改市
            {
                actionname="editcity";
            }
            else if(jb.equals ("3"))//修改区
            {
                actionname="editarea";
            }
            else if(jb.equals ("4"))//修改小区
            {
                actionname="editvillage";
            }
            else
            {
                out.print ("{\"err\":\"抱歉，请勿非法修改参数\",\"msg\":\"\"}");
                return;
            }
            if (SingleArea.getInstance ().getArea ().AreaAction (actionname, pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "编辑了编码为 " + areaid + " 的地区</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"地区信息修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，地区信息修改失败\",\"msg\":\"\"}");
            }
        }
        else if (areamethod.equals ("del"))
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String actionname="";//操作标签符
            String jb=tostr.to_utf_8 (request.getParameter("jb"));
            String areaid = tostr.to_utf_8 (request.getParameter ("areaid"));
            if (! checkStr.checkIsNum (areaid) || !checkStr.checkIsNum (jb))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setId (Integer.parseInt (areaid));
            if(jb.equals ("1"))//删除省
            {
                actionname="delprovince";
            }
            else if(jb.equals ("2"))//删除市
            {
                actionname="delcity";
            }
            else if(jb.equals ("3"))//删除区
            {
                actionname="delarea";
            }
            else if(jb.equals ("4"))//删除小区
            {
                actionname="delvillage";
            }
            else
            {
                out.print ("{\"err\":\"抱歉，请勿非法访问\",\"msg\":\"\"}");
                return;
            }
            if (SingleArea.getInstance ().getArea ().AreaAction (actionname, pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除了编号为 " + areaid + " 的地区</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"地区信息删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}
