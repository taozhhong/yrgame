package ServletAction.SysSetPackages.MgUserPackages;

import Com.SharePackages.Field_Str;
import Com.SharePackages.Format_Date;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.UserSetPackages.UserPackages.SingleUser;
import Com.SysSetPackages.UserSetPackages.UserPackages.User_Pro;

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
 * User: T410
 * Date: 13-11-7
 * Time: 上午8:59
 * 打印管理用户
 */
@WebServlet (name = "MgUserWriterJson", urlPatterns = {"/Action/MgUserWriter.do"})
public class MgUserWriterJson extends HttpServlet {
    private Str_code_switch tostr = null;
    private Format_Date nowdate = null;

    public void init () {
        tostr = new Str_code_switch ();
        nowdate = new Format_Date ();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String mgmark = tostr.to_utf_8 (request.getParameter ("mgmark"));
        if (mgmark.equals ("mglist"))//打印分页数据
        {
            String FieldStr = "m.*,r.rotename as rotename," +
                              "s.storename as storename,s.director as director," +
                    "s.sex as sex,s.tel as tel," +
                              "s.status as status",
                    TableName = "rote r,manger_user m,store s",
                    SqlWhere = " and m.userid=s.storeid and m.roteid=r.roteid";
            String myuserid = tostr.to_utf_8 (request.getParameter ("myuserid"));//自己的ID
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));//用户编号
            String logingname = tostr.to_utf_8 (request.getParameter ("logingname"));//员工工号
            String namepy = tostr.to_utf_8 (request.getParameter ("namepy"));//员工姓名或简拼
            String sex = tostr.to_utf_8 (request.getParameter ("sex"));//性别
            String roteid = tostr.to_utf_8 (request.getParameter ("roteid"));// 角色
            String tel = tostr.to_utf_8 (request.getParameter ("tel"));//联系电话
            String userstatus = tostr.to_utf_8 (request.getParameter ("userstatus"));//员工状态
            String lock = tostr.to_utf_8 (request.getParameter ("lock"));//用户锁定
            String start_time = tostr.to_utf_8 (request.getParameter ("start_time"));// 加入公司时间 大于
            String end_time = tostr.to_utf_8 (request.getParameter ("end_time"));// 加入公司时间 小于
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if (checkStr.checkIsNum (myuserid))
                SqlWhere += " and m.userid<>" + myuserid;
            if (checkStr.checkIsNum (userid))
                SqlWhere += " and m.userid=" + userid;
            if (! checkStr.isNull (logingname))
                SqlWhere += " and instr(m.loginname,'" + logingname + "')>0";
            if (! checkStr.isNull (namepy))
                SqlWhere += " and (instr(s.storepy,'" + namepy.toUpperCase () + "')>0 or instr(s.storename,'" + namepy + "')>0)";
            if (checkStr.checkIsNum (sex))
                SqlWhere += " and p.sex=" + sex;
            if (checkStr.checkIsNum (roteid))
                SqlWhere += " and m.roteid=" + roteid;
            if (! checkStr.isNull (tel))
                SqlWhere += " and instr(s.tel,'" + tel + "')>0";
            if (checkStr.checkIsNum (lock))
                SqlWhere += " and m.islock=" + lock;
            if (checkStr.checkIsNum (userstatus))
                SqlWhere += " and s.status=" + userstatus;
            if (! checkStr.isNull (start_time))
                SqlWhere += " and to_char(m.logintime,'yyyy-MM-dd') >= '" + start_time + "'";
            if (! checkStr.isNull (end_time))
                SqlWhere += " and to_char(m.logintime,'yyyy-MM-dd') <= '" + end_time + "'";
            if (! checkStr.checkIsNum (ShowPage))
                ShowPage = "1";
            if (! checkStr.checkIsNum (PageSize))
                PageSize = "20";
            Iterator UserIt = SingleUser.getInstance ().getUserPage ().MgUserPageList (FieldStr, TableName, SqlWhere, "m.userid", "desc", Integer.parseInt (ShowPage), Integer.parseInt (PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleUser.getInstance ().getUserPage ().getTotalRecord ()).append (",\"rows\":[");
            while (UserIt.hasNext ())
            {
                User_Pro pro = (User_Pro) UserIt.next ();
                if (pro != null)
                {
                    Str.append("{").
                            append ("\"userid\":").append ("\"").append (pro.getUserid()).append ("\"").append(",").
                            append ("\"loginname\":").append ("\"").append (pro.getLoginname()).append ("\"").append(",").
                            append ("\"rotename\":").append ("\"").append (pro.getRotename()).append ("\"").append (",").
                            append ("\"storename\":").append ("\"").append (pro.getStorename()).append ("\"").append (",").
                            append ("\"director\":").append ("\"").append (pro.getDirector()).append ("\"").append (",").
                            append ("\"sex\":").append ("\"").append (Field_Str.User_Sex[pro.getSex ()]).append ("\"").append (",").
                            append ("\"tel\":").append ("\"").append (checkStr.IsStrNull(pro.getTelnum())).append ("\"").append (",").
                            append ("\"status\":").append ("\"").append (Field_Str.User_State[pro.getStatus ()]).append ("\"").append (",").
                            append ("\"islock\":").append ("\"").append (pro.getIslock()).append ("\"").append (",").
                            append ("\"isprive\":").append ("\"").append (pro.getIsprive()).append ("\"").append (",").
                            append ("\"logintime\":").append ("\"").append (nowdate.set_date(pro.getLogintime(), "yyyy/MM/dd HH:mm")).append("\"").append (",").
                            append ("\"loginip\":").append ("\"").append (checkStr.IsStrNull(pro.getLoginip())).append ("\"").append (",").
                            append ("\"lastlogintime\":").append ("\"").append (nowdate.set_date(pro.getLastlogintime(), "yyyy/MM/dd HH:mm")).append ("\"").append (",").
                            append ("\"lastloginip\":").append ("\"").append (checkStr.IsStrNull(pro.getLastloginip())).append ("\"").append (",").
                            append ("\"regtime\":").append ("\"").append (nowdate.set_date (pro.getRegtime (), "yyyy/MM/dd HH:mm")).append ("\"").
                            append ("}").append (",");
                }
                else
                {
                    Str.append ("{").
                            append ("\"userid\":").append ("\"\"").append(",").
                            append ("\"loginname\":").append ("\"\"").append(",").
                            append ("\"rotename\":").append ("\"\"").append (",").
                            append ("\"storename\":").append ("\"\"").append (",").
                            append("\"director\":").append ("\"\"").append (",").
                            append ("\"sex\":").append ("\"\"").append (",").
                            append ("\"tel\":").append ("\"\"").append (",").
                            append ("\"status\":").append ("\"\"").append (",").
                            append ("\"islock\":").append ("\"\"").append (",").
                            append ("\"isprive\":").append ("\"\"").append(",").
                            append("\"logintime\":").append ("\"\"").append (",").
                            append ("\"loginip\":").append ("\"\"").append (",").
                            append("\"lastlogintime\":").append ("\"\"").append(",").
                            append ("\"lastloginip\":").append ("\"\"").append (",").
                            append("\"regtime\":").append ("\"\"").append ("}").
                            append (",");
                }
                UserIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}"); out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String mgmark = tostr.to_utf_8 (request.getParameter ("mgmark"));
        if (mgmark.equals ("editlist"))//修改数据
        {
            String userid = tostr.to_utf_8 (request.getParameter ("userid"));
            String FieldStr = "t.*",
                TableName = "manger_user t",
                SqlWhere = "";
            if (checkStr.checkIsNum (userid)) {
                SqlWhere = "t.userid=" + userid;
            }
            Iterator UserIt = SingleUser.getInstance ().getUserDb ().MgUserList ("y", "1", FieldStr, TableName, "", SqlWhere, "t.userid", "asc").iterator ();
            while (UserIt.hasNext ())
            {
                User_Pro pro = (User_Pro) UserIt.next ();
                if (pro != null)
                {
                    Str.append ("{").
                            append ("\"userid\":").append ("\"").append (pro.getUserid ()).append ("\"").append(",").
                            append ("\"n_userid\":").append ("\"").append (pro.getUserid ()).append ("\"").append (",").
                            append ("\"store\":").append ("\"").append (pro.getUserid ()).append ("\"").append (",").
                            append ("\"loginname\":").append ("\"").append (pro.getLoginname()).append ("\"").append (",").
                            append ("\"roteid\":").append ("\"").append (pro.getRoteid()).append ("\"").append (",").
                            append ("\"mglock\":").append ("\"").append(pro.getIslock()).append ("\"").append (",").
                            append("\"mgpriv\":").append ("\"").append (pro.getIsprive()).append ("\"").
                            append ("}").append (",");
                }
                else
                {
                    Str.append ("{").
                            append ("\"userid\":").append ("\"\"").append(",").
                            append ("\"n_userid\":").append ("\"\"").append (",").
                            append ("\"store\":").append ("\"\"").append (",").
                            append ("\"loginname\":").append ("\"\"").append (",").
                            append ("\"roteid\":").append ("\"\"").append (",").
                            append ("\"mglock\":").append ("\"\"").append(",").
                            append("\"mgpriv\":").append ("\"\"").
                            append ("}").append (",");
                }
                UserIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }
}
