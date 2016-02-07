package ServletAction.MemberServlet;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.Field_Str;
import Com.SharePackages.Format_Date;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-5-22
 * Time: 上午10:46
 * 打印会员数据
 */
@WebServlet(name = "MemberWriterJson",urlPatterns = {"/Action/MemberWriter.do"})
public class MemberWriterJson extends HttpServlet
{
    private Str_code_switch tostr   = null;
    private Format_Date     nowdate = null;

    public void init()
    {
        tostr = new Str_code_switch();
        nowdate = new Format_Date();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String membermark = tostr.to_utf_8 (request.getParameter ("membermark"));
        if (membermark.equals ("pagelist"))//会员分页
        {
            String FieldStr = "m.*",
                    TableName = "member m",
                    SqlWhere = " and m.memberid<>0";
            String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));//会员ID
            String logname = tostr.to_utf_8 (request.getParameter ("logname"));//登录邮箱
            String name = tostr.to_utf_8 (request.getParameter ("name"));//真实姓名
            String tel = tostr.to_utf_8 (request.getParameter ("tel"));//手机号码
            String qq = tostr.to_utf_8 (request.getParameter ("qq"));//QQ号码
            String status = tostr.to_utf_8 (request.getParameter ("status"));//会员锁定
            String reg_start_time = tostr.to_utf_8 (request.getParameter ("reg_start_time"));// 注册时间 大于
            String reg_end_time = tostr.to_utf_8 (request.getParameter ("reg_end_time"));// 注册时间 小于
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if(checkStr.checkIsNum(memberid))
                SqlWhere += " and m.memberid=" + memberid;
            if(!checkStr.isNull(logname))
                SqlWhere += " and instr(m.loginname,'" + logname + "')>0";
            if(!checkStr.isNull(name))
                SqlWhere += " and instr(m.realname,'" + name + "')>0";
            if(!checkStr.isNull(tel))
                SqlWhere += " and instr(m.tel,'" + tel + "')>0";
            if(checkStr.checkIsNum(qq))
                SqlWhere += " and m.qq='"+qq+"'";
            if(checkStr.checkIsNum(status))
                SqlWhere += " and m.status=" + status;
            if (! checkStr.isNull (reg_start_time))
                SqlWhere += " and to_char(m.regtime,'yyyy-MM-dd') >= '" + reg_start_time + "'";
            if (! checkStr.isNull (reg_end_time))
                SqlWhere += " and to_char(m.regtime,'yyyy-MM-dd') <= '" + reg_end_time + "'";
            if (! checkStr.checkIsNum(ShowPage))
                ShowPage = "1";
            if (! checkStr.checkIsNum (PageSize))
                PageSize = "30";
            Iterator MemberIt = SingleMember.getInstance().getMemberPage().
                    MemberPageList(FieldStr, TableName, SqlWhere, "m.memberid", "desc",
                            Integer.parseInt(ShowPage),
                            Integer.parseInt(PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleMember.getInstance().getMemberPage().getTotalRecord ()).append (",\"rows\":[");
            while (MemberIt.hasNext())
            {
                Member_Pro pro=(Member_Pro)MemberIt.next();
                if(pro!=null)
                {
                    Str.append("{")
                            .append("\"code\":").append ("\"").append(pro.getMemberid()).append ("\"").append(",")
                            .append("\"memberid\":").append ("\"").append(pro.getMemberid()).append ("\"").append (",")
                            .append("\"nick\":").append ("\"").append(pro.getNick()).append ("\"").append(",")
                            .append("\"loginname\":").append ("\"").append(checkStr.String2Json(pro.getLoginname())).append("\"").append (",")
                            .append("\"tjmemberid\":").append ("\"").append(pro.getTjmemberid()).append("\"").append (",")
                            .append("\"realname\":").append ("\"").append(checkStr.String2Json(pro.getRealname())).append("\"").append (",")
                            .append("\"sex\":").append ("\"").append(pro.getSex()).append("\"").append (",")
                            .append("\"birdate\":").append("\"").append(checkStr.IsStrNull(pro.getBirdate())).append("\"").append (",")
                            .append("\"tel\":").append ("\"").append(checkStr.String2Json(pro.getTel())).append("\"").append (",")
                            .append("\"qq\":").append ("\"").append(checkStr.IsStrNull(pro.getQq())).append("\"").append (",")
                            .append("\"email\":").append ("\"").append(checkStr.IsStrNull(pro.getEmail())).append("\"").append (",")
                            .append("\"idnum\":").append ("\"").append(checkStr.IsStrNull(pro.getIdnum())).append("\"").append (",")
                            .append("\"headimg\":").append ("\"").append(checkStr.IsStrNull(pro.getHeadimg())).append("\"").append (",")
                            .append("\"note\":").append("\"").append (checkStr.String2Json(pro.getNote())).append("\"").append (",")
                            .append("\"status\":").append ("\"").append (pro.getStatus()).append("\"").append (",")
                            .append("\"regip\":").append ("\"").append (pro.getRegip()).append("\"").append(",")
                            .append("\"regtime\":").append ("\"").append (nowdate.set_date(pro.getRegtime(),"yyyy-MM-dd HH:mm:ss")).append("\"")
                            .append("}").append (",");
                }
                else
                {
                    Str.append ("{")
                            .append ("\"code\":").append ("\"\"").append (",")
                            .append ("\"memberid\":").append ("\"\"").append (",")
                            .append ("\"nick\":").append ("\"\"").append (",")
                            .append ("\"loginname\":").append ("\"\"").append (",")
                            .append ("\"tjmemberid\":").append ("\"\"").append (",")
                            .append ("\"realname\":").append ("\"\"").append (",")
                            .append ("\"sex\":").append ("\"\"").append (",")
                            .append ("\"birdate\":").append ("\"\"").append (",")
                            .append ("\"tel\":").append ("\"\"").append (",")
                            .append ("\"qq\":").append ("\"\"").append (",")
                            .append ("\"email\":").append ("\"\"").append (",")
                            .append ("\"idnum\":").append ("\"\"").append (",")
                            .append ("\"headimg\":").append ("\"\"").append (",")
                            .append ("\"note\":").append ("\"\"").append (",")
                            .append ("\"status\":").append ("\"\"").append (",")
                            .append ("\"regip\":").append ("\"\"").append (",")
                            .append ("\"regtime\":").append ("\"\"")
                            .append ("}").append (",");
                }
                MemberIt.remove();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String membermark = tostr.to_utf_8 (request.getParameter ("membermark"));
        if (membermark.equals ("mlist"))//打印修改基本资料数据
        {
            String memberid = tostr.to_utf_8 (request.getParameter ("memberid"));
            String FieldStr = "m.*",
                    TableName = "member m",
                    SqlWhere = "";
            if (checkStr.checkIsNum (memberid))
            {
                SqlWhere = "m.memberid=" + memberid;
            }
            Iterator MemberIt = SingleMember.getInstance ().getMemberDb ().MemberList
                    ("y", "1", FieldStr, TableName, "", SqlWhere, "m.memberid", "asc")
                    .iterator ();
            while (MemberIt.hasNext ())
            {
                Member_Pro pro = (Member_Pro) MemberIt.next ();
                if(pro!=null)
                {
                    Str.append ("{")
                    .append("\"memberid\":").append("\"").append(pro.getMemberid()).append ("\"").append (",")
                        .append("\"nick\":").append ("\"").append(pro.getNick()).append ("\"").append(",")
                        .append("\"loginname\":").append ("\"").append(checkStr.String2Json(pro.getLoginname())).append("\"").append (",")
                        .append("\"tjmemberid\":").append ("\"").append(pro.getTjmemberid()).append("\"").append (",")
                        .append("\"realname\":").append ("\"").append(checkStr.String2Json(pro.getRealname())).append("\"").append (",")
                        .append("\"sex\":").append ("\"").append(pro.getSex()).append("\"").append (",")
                        .append("\"birdate\":").append("\"").append(checkStr.IsStrNull(pro.getBirdate())).append("\"").append (",")
                        .append("\"tel\":").append ("\"").append(checkStr.String2Json(pro.getTel())).append("\"").append (",")
                        .append("\"qq\":").append ("\"").append(checkStr.IsStrNull(pro.getQq())).append("\"").append (",")
                        .append("\"email\":").append ("\"").append(checkStr.IsStrNull(pro.getEmail())).append("\"").append (",")
                        .append("\"idnum\":").append ("\"").append(checkStr.IsStrNull(pro.getIdnum())).append("\"").append (",")
                        .append("\"headimg\":").append ("\"").append(checkStr.IsStrNull(pro.getHeadimg())).append("\"").append (",")
                        .append("\"note\":").append("\"").append (checkStr.String2Json(pro.getNote())).append("\"").append (",")
                        .append("\"status\":").append ("\"").append (pro.getStatus()).append("\"").append (",")
                        .append("\"regip\":").append ("\"").append (pro.getRegip()).append("\"").append(",")
                        .append("\"regtime\":").append ("\"").append (nowdate.set_date(pro.getRegtime(),"yyyy-MM-dd HH:mm:ss")).append("\"")
                        .append("}").append(",");
                }
                else
                {
                    Str.append ("{").
                         append("\"memberid\":").append ("\"\"").append (",")
                        .append ("\"nick\":").append ("\"\"").append (",")
                        .append ("\"loginname\":").append ("\"\"").append (",")
                        .append ("\"tjmemberid\":").append ("\"\"").append (",")
                        .append ("\"realname\":").append ("\"\"").append (",")
                        .append ("\"sex\":").append ("\"\"").append (",")
                        .append ("\"birdate\":").append ("\"\"").append (",")
                        .append ("\"tel\":").append ("\"\"").append (",")
                        .append ("\"qq\":").append ("\"\"").append (",")
                        .append ("\"email\":").append ("\"\"").append (",")
                        .append ("\"idnum\":").append ("\"\"").append (",")
                        .append ("\"headimg\":").append ("\"\"").append (",")
                        .append ("\"note\":").append ("\"\"").append (",")
                        .append ("\"status\":").append ("\"\"").append (",")
                        .append ("\"regip\":").append ("\"\"").append (",")
                        .append ("\"regtime\":").append ("\"\"").
                        append("}").append (",");
                }
                MemberIt.remove();
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
    }
}
