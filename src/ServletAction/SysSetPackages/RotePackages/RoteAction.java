package ServletAction.SysSetPackages.RotePackages;

import Com.SharePackages.*;
import Com.SysSetPackages.RoteSetPackages.Rote_Pro;
import Com.SysSetPackages.RoteSetPackages.SingleRote;
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
 * Date: 13-8-14
 * Time: 上午10:26
 * 对权限表的操作
 */
@WebServlet (name = "RoteAction", urlPatterns = {"/Action/Rote.do"})
public class RoteAction extends HttpServlet {
    private Str_code_switch tostr = null;
    private FileTarget Log = null;
    private Format_Date nowdate = null;
    private Rote_Pro pro = null;
    private UserPurview UserProxy = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        pro = new Rote_Pro ();
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
        String rotemethod = tostr.to_utf_8 (request.getParameter ("rotemethod"));
        if (rotemethod.equals ("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String rotename = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("rotename")));//角色名称
            String b_roteauth[] = request.getParameterValues ("b_roteauth");//管理大版块
            String nodes=checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("nodes")));//备注
            if (checkStr.isNull (rotename) || b_roteauth == null)
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            String s_roteauth[] = new String[b_roteauth.length];
            String roteopt[][] = new String[b_roteauth.length][];
            /*获取对应小版块的ID
              checkbox的name以s_roteauth_版块ID构成
             *获取对应版块的操作功能权限
              checkbox的name以roteopt_大版块ID_小版块ID构成
             */
            for (int i = 0; i < b_roteauth.length; i++)
            {
                if (checkStr.isNull (tostr.ArrayToString (request.getParameterValues ("s_roteauth_" + b_roteauth[i]))))
                {
                    out.print ("{\"err\":\"请至少选择一个小版块\",\"msg\":\"\"}");
                    return;
                }
                else
                {
                    s_roteauth[i] = tostr.ArrayToString (request.getParameterValues ("s_roteauth_" + b_roteauth[i]));
                    roteopt[i] = new String[request.getParameterValues ("s_roteauth_" + b_roteauth[i]).length];//动态初始化二维数据

                    for (int ii = 0; ii < request.getParameterValues ("s_roteauth_" + b_roteauth[i]).length; ii++)
                    {
                        if (checkStr.isNull (tostr.ArrayToString (request.getParameterValues ("roteopt_" + b_roteauth[i] + "_" + request.getParameterValues ("s_roteauth_" + b_roteauth[i])[ii]))))
                        {
                            roteopt[i][ii] = "-1";
                        }
                        else
                        {
                            roteopt[i][ii] = tostr.ArrayToString (request.getParameterValues ("roteopt_" + b_roteauth[i] + "_" + request.getParameterValues ("s_roteauth_" + b_roteauth[i])[ii]));
                            //System.out.println("opt:"+tostr.ArrayToString (request.getParameterValues ("roteopt_"+b_roteauth[i]+"_"+request.getParameterValues ("s_roteauth_"+b_roteauth[i])[ii])));
                        }
                    }
                }
            }
            StringBuilder S_RoteAutoStr = new StringBuilder ("");
            StringBuilder OptStr = new StringBuilder ("");
            for (String anAuthopt : s_roteauth) {
                S_RoteAutoStr.append (anAuthopt).append ("|");
            }
            //System.out.println(Str.toString());
            /**
             * 格式化操作权限
             * 如：0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|~0&1&2&|0&1&2&|~0&1&2&|0&1&2&|
             */
            for (int i = 0; i < b_roteauth.length; i++) {
                for (int j = 0; j < roteopt[i].length; j++) {
                    OptStr.append (roteopt[i][j]).append ("|");
                    if (j == roteopt[i].length - 1) {
                        OptStr.append ("~");
                    }
                }
            }
            pro.setRotename (rotename);
            pro.setB_mkid (tostr.ArrayToString (b_roteauth));
            pro.setS_mkid (S_RoteAutoStr.toString ());
            pro.setOptid (OptStr.toString ());
            pro.setRemark (nodes);
            if (SingleRote.getInstance ().getReoteDb ().RoteAction ("add", pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了名为 " + rotename + " 的角色</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                S_RoteAutoStr.delete (0, S_RoteAutoStr.length ());//清空
                OptStr.delete (0, OptStr.length ());//清空
                out.print ("{\"err\":\"\",\"msg\":\"角色添加成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
            }
            //System.out.println(Str1.toString());
        }
        else if (rotemethod.equals ("mod"))//编辑
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String rotename = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("rotename")));//角色名称
            String b_roteauth[] = request.getParameterValues ("b_roteauth");//管理大版块
            String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));
            String nodes=checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("nodes")));//备注
            if (checkStr.isNull (rotename) || b_roteauth == null ||
                ! checkStr.checkIsNum (roteid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            String s_roteauth[] = new String[b_roteauth.length];
            String roteopt[][] = new String[b_roteauth.length][];
            /*获取对应小版块的ID
              checkbox的name以s_roteauth_版块ID构成
             *获取对应版块的操作功能权限
              checkbox的name以roteopt_大版块ID_小版块ID构成
             */
            for (int i = 0; i < b_roteauth.length; i++)
            {
                if (checkStr.isNull (tostr.ArrayToString (request.getParameterValues ("s_roteauth_" + b_roteauth[i]))))
                {
                    out.print ("{\"err\":\"请至少选择一个小版块\",\"msg\":\"\"}");
                    return;
                }
                else
                {
                    s_roteauth[i] = tostr.ArrayToString (request.getParameterValues ("s_roteauth_" + b_roteauth[i]));
                    roteopt[i] = new String[request.getParameterValues ("s_roteauth_" + b_roteauth[i]).length];//动态初始化二维数据

                    for (int ii = 0; ii < request.getParameterValues ("s_roteauth_" + b_roteauth[i]).length; ii++)
                    {
                        if (checkStr.isNull (tostr.ArrayToString (request.getParameterValues ("roteopt_" + b_roteauth[i] + "_" + request.getParameterValues ("s_roteauth_" + b_roteauth[i])[ii]))))
                        {
                            roteopt[i][ii] = "-1";
                        }
                        else
                        {
                            roteopt[i][ii] = tostr.ArrayToString (request.getParameterValues ("roteopt_" + b_roteauth[i] + "_" + request.getParameterValues ("s_roteauth_" + b_roteauth[i])[ii]));
                            //System.out.println("opt:"+tostr.ArrayToString (request.getParameterValues ("roteopt_"+b_roteauth[i]+"_"+request.getParameterValues ("s_roteauth_"+b_roteauth[i])[ii])));
                        }
                    }
                }
            }
            StringBuilder S_RoteAutoStr = new StringBuilder ("");
            StringBuilder OptStr = new StringBuilder ("");
            for (String anAuthopt : s_roteauth) {
                S_RoteAutoStr.append (anAuthopt).append ("|");
            }
            //System.out.println(Str.toString());
            /**
             * 格式化操作权限
             * 如：0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|0&1&2&|~0&1&2&|0&1&2&|~0&1&2&|0&1&2&|
             */
            for (int i = 0; i < b_roteauth.length; i++) {
                for (int j = 0; j < roteopt[i].length; j++) {
                    OptStr.append (roteopt[i][j]).append ("|");
                    if (j == roteopt[i].length - 1) {
                        OptStr.append ("~");
                    }
                }
            }
            pro.setRoteid (Integer.parseInt (roteid));
            pro.setRotename (rotename);
            pro.setB_mkid (tostr.ArrayToString (b_roteauth));
            pro.setS_mkid (S_RoteAutoStr.toString ());
            pro.setOptid (OptStr.toString ());
            pro.setRemark (nodes);
            if (SingleRote.getInstance ().getReoteDb ().RoteAction ("mod", pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了ID为 " + roteid + " 的角色</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                S_RoteAutoStr.delete (0, S_RoteAutoStr.length ());//清空
                OptStr.delete (0, OptStr.length ());//清空
                out.print ("{\"err\":\"\",\"msg\":\"角色信息修改成功\"}");
            }
            else {
                out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (rotemethod.equals ("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));
            if (! checkStr.checkIsNum (roteid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setRoteid (Integer.parseInt (roteid));
            if (SingleRote.getInstance ().getReoteDb ().RoteAction ("del", pro, "") > 0)
            {
                //记录日志
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除了ID为 " + roteid + " 的角色</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"角色删除成功\"}");
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
