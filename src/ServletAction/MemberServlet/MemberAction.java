package ServletAction.MemberServlet;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
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
 * User: Administrator
 * Date: 14-5-22
 * Time: 下午3:23
 * 后台会员表操作
 */
@WebServlet(name = "MemberAction",urlPatterns = {"/Action/Member.do"})
public class MemberAction extends HttpServlet
{
    private Str_code_switch tostr     = null;
    private FileTarget      Log       = null;
    private Format_Date     nowdate   = null;
    private Member_Pro      pro       = null;
    private UserPurview     UserProxy = null;

    public void init()
    {
        tostr = new Str_code_switch();
        Log = new AdapterFile(new ImgAdapter());
        nowdate = new Format_Date();
        pro = new Member_Pro();
        UserProxy = new ProxyUserPurview();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String UserStr = UserProxy.UserProxy (request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String membermethod = tostr.to_utf_8 (request.getParameter ("membermethod"));
        if(membermethod.equals ("del"))//删除会员
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));
            if (checkStr.isNull (memberid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            if (SingleMember.getInstance().getMemberDb().MemberAction ("del", pro, memberid) > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除ID为 " + memberid + " 的会员</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"会员删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (membermethod.equals ("edit"))//编辑资料
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));
            String email = tostr.to_utf_8 (request.getParameter ("email"));
            String tel = tostr.to_utf_8 (request.getParameter ("tel"));
            String qq = tostr.to_utf_8 (request.getParameter ("qq"));
            String birdate = tostr.to_utf_8 (request.getParameter ("birdate"));
            String islock = tostr.to_utf_8 (request.getParameter ("islock"));
            String idnum=tostr.to_utf_8(request.getParameter("idnum"));
            if(!checkStr.checkIsNum(memberid) || !checkStr.checkIsNum(islock))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setMemberid(Integer.parseInt(memberid));
            pro.setTel(tel);
            pro.setQq(qq);
            pro.setEmail(email);
            pro.setStatus(Integer.parseInt(islock));
            pro.setBirdate(birdate);
            pro.setIdnum(idnum);
            if (SingleMember.getInstance().getMemberDb().MemberAction("edit",pro,"")>0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了会员ID为 " + memberid + " 的基本资料</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"会员资料修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if(membermethod.equals("setpwd"))//重置密码
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));
            String newpass = tostr.to_utf_8 (request.getParameter ("newpass"));
            String repass = tostr.to_utf_8 (request.getParameter ("repass"));
            if (! checkStr.checkIsNum (memberid) || checkStr.isNull (newpass) ||
                    checkStr.isNull (repass) || ! newpass.equals (repass))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setMemberid (Integer.parseInt (memberid));
            pro.setLoginpwd (Md5.md5 (newpass));
            if (SingleMember.getInstance().getMemberDb().MemberAction ("setpwd", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "设置了会员ID为 " + memberid + " 的密码</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"密码修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，密码修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}