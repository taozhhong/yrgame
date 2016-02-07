package ServletAction.SysSetPackages.UserPackages;

import Com.SharePackages.Field_Str;
import Com.SharePackages.Format_Date;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
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
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 13-10-31
 * Time: 上午9:24
 * 打印员工JSON
 */
@WebServlet (name = "UserWriterJson", urlPatterns = {"/Action/UserWriter.do"})
public class UserWriterJson extends HttpServlet {
    private Str_code_switch tostr = null;
    private Format_Date nowdate = null;
    private UserPurview UserProxy = null;
    public void init () {
        tostr = new Str_code_switch ();
        nowdate = new Format_Date ();
        UserProxy = new ProxyUserPurview();

    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String umark = tostr.to_utf_8 (request.getParameter ("umark"));
        if (umark.equals ("plist"))//打印分页数据
        {
            String FieldStr = "s.*,p.provincename as provincename,p.provinceid as provinceid," +
                              "c.cityname as cityname,c.cityid as cityid,a.areaname as areaname,"+
                              "nvl((select m.userid from manger_user m where m.userid=s.storeid),0) as ismg",
                    TableName = "province p,city c,area a,store s",
                    SqlWhere = " and p.provinceid=c.provinceid and c.cityid=a.cityid and a.areaid=s.areaid";
            String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));//商户编号
            String storepy = tostr.to_utf_8 (request.getParameter ("storepy"));//商户名称或简拼
            String director = tostr.to_utf_8 (request.getParameter ("director"));//负责人
            String tel = tostr.to_utf_8 (request.getParameter ("tel"));//联系电话
            String status = tostr.to_utf_8 (request.getParameter ("status"));//商户状态
            String mgstr = tostr.to_utf_8 (request.getParameter ("mgstr"));//管理员映射用户
            String egstr = tostr.to_utf_8 (request.getParameter ("egstr"));//管理员映射用户修改
            String m_userid = tostr.to_utf_8 (request.getParameter ("m_userid"));
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if (checkStr.checkIsNum (storeid))
                SqlWhere += " and s.storeid=" + storeid;
            if (! checkStr.isNull (storepy))
                SqlWhere += " and (instr(s.storepy,'" + storepy.toUpperCase () + "')>0 or instr(s.storename,'" + storepy + "')>0)";
            if (! checkStr.isNull (director))
                SqlWhere += " and instr(s.director,'" + director + "')>0";
            if (! checkStr.isNull (tel))
                SqlWhere += " and instr(s.tel,'" + tel + "')>0";
            if (checkStr.checkIsNum (status))
                SqlWhere += " and s.status=" + status;
            if (mgstr.equals ("y"))
                SqlWhere += " and s.storeid not in(select userid from manger_user) and s.status=0";
            if (egstr.equals ("y"))
                SqlWhere += " and s.storeid not in(select userid from manger_user where userid<>" + m_userid + ")";
            if (! checkStr.checkIsNum (ShowPage))
                ShowPage = "1";
            if (! checkStr.checkIsNum (PageSize))
                PageSize = "50";
            Iterator UserIt = SingleUser.getInstance ().getUserPage ().UserPageList (FieldStr, TableName, SqlWhere,
                                                                                     "s.storeid", "desc",
                                                                                     Integer.parseInt (ShowPage),
                                                                                     Integer.parseInt (PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleUser.getInstance ().getUserPage ().getTotalRecord ()).append (",\"rows\":[");
            while (UserIt.hasNext ()) {
                User_Pro pro = (User_Pro) UserIt.next ();
                if (pro != null)
                {
                    Str.append("{")
                            .append("\"storeid\":").append("\"").append (pro.getStoreid()).append ("\"").append(",")
                            .append("\"storename\":").append("\"").append(checkStr.String2Json(pro.getStorename())).append("\"").append(",")
                            .append("\"storepy\":").append("\"").append(pro.getStorepy()).append("\"").append(",")
                            .append("\"director\":").append("\"").append(checkStr.String2Json(pro.getDirector())).append("\"").append(",")
                            .append("\"sex\":").append("\"").append(Field_Str.User_Sex[pro.getSex()]).append("\"").append(",")
                            .append("\"birdate\":").append("\"").append(nowdate.set_date(pro.getBirdate(),"yyyy-MM-dd")).append("\"").append(",")
                            .append("\"education\":").append("\"").append(Field_Str.User_Edu[pro.getEducation()]).append("\"").append(",")
                            .append("\"tel\":").append("\"").append(checkStr.IsStrNull(pro.getTelnum())).append("\"").append(",")
                            .append("\"qq\":").append("\"").append(checkStr.IsStrNull(pro.getQq())).append("\"").append(",")
                            .append("\"email\":").append("\"").append(checkStr.IsStrNull(pro.getEmail())).append("\"").append(",")
                            .append("\"areaname\":").append("\"").append(pro.getProvincename() + pro.getCityname() + pro.getAreaname()).append("\"").append(",")
                            .append("\"provinceid\":").append("\"").append(pro.getProvinceid()).append("\"").append(",")
                            .append("\"cityid\":").append("\"").append(pro.getCityid()).append("\"").append(",")
                            .append("\"areaid\":").append("\"").append(pro.getAreaid()).append("\"").append(",")
                            .append("\"status\":").append("\"").append(Field_Str.User_State[pro.getStatus()]).append("\"").append(",")
                            .append("\"divide\":").append("\"").append(pro.getDivide()*100).append("%").append("\"").append(",")
                            .append("\"regip\":").append("\"").append(checkStr.IsStrNull(pro.getRegip())).append("\"").append(",")
                            .append("\"regtime\":").append("\"").append(nowdate.set_date(pro.getRegtime(), "yyyy/MM/dd HH:mm")).append("\"").append(",")
                            .append("\"note\":").append("\"").append(checkStr.IsStrNull(pro.getNodes())).append("\"").append(",")
                            .append("\"ismg\":").append(pro.getIsmg()).append("}")
                            .append(",");
                }
                else
                {
                    Str.append("{")
                            .append ("\"storeid\":").append ("\"\"").append (",")
                            .append ("\"storename\":").append ("\"\"").append (",")
                            .append ("\"storepy\":").append ("\"\"").append (",")
                            .append ("\"director\":").append ("\"\"").append(",")
                            .append("\"sex\":").append ("\"\"").append (",")
                            .append ("\"birdate\":").append ("\"\"").append (",")
                            .append ("\"education\":").append ("\"\"").append (",")
                            .append ("\"tel\":").append ("\"\"").append (",")
                            .append ("\"qq\":").append ("\"\"").append (",")
                            .append ("\"email\":").append ("\"\"").append (",")
                            .append ("\"areaname\":").append ("\"\"").append (",")
                            .append ("\"provinceid\":").append ("\"\"").append (",")
                            .append ("\"cityid\":").append ("\"\"").append (",")
                            .append ("\"areaid\":").append ("\"\"").append (",")
                            .append ("\"status\":").append ("\"\"").append (",")
                            .append ("\"divide\":").append ("\"\"").append(",")
                            .append ("\"regip\":").append ("\"\"").append(",")
                            .append ("\"regtime\":").append ("\"\"").append (",")
                            .append ("\"note\":").append ("\"\"").append (",")
                            .append ("\"ismg\":").append ("\"\"").append ("}")
                            .append (",");
                }
                UserIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if(umark.equals ("olist"))//订单添加这里显示
        {
            String UserStr = UserProxy.UserSelect(request);
            String FieldStr = "s.*,'' as provincename,0 as provinceid," +
                    "'' as cityname,0 as cityid,'' as areaname,"+
                    "0 as ismg",
                    TableName = "store s",
                    SqlWhere = " and s.status=0";
            if(!checkStr.isNull(UserStr) && UserStr.contains(","))
            {
                if(UserStr.split(",")[8].equals("1"))//如果是1表示只能查看自己的订单
                    SqlWhere+=" and s.storeid="+UserStr.split(",")[0];
            }
            String storepy = tostr.to_utf_8 (request.getParameter ("storepy"));//商户名称或简拼
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if (! checkStr.isNull (storepy))
                SqlWhere += " and (instr(s.storepy,'" + storepy.toUpperCase () + "')>0 or instr(s.storename,'" + storepy + "')>0)";
            if (! checkStr.checkIsNum (ShowPage))
                ShowPage = "1";
            if (! checkStr.checkIsNum (PageSize))
                PageSize = "50";
            Iterator UserIt = SingleUser.getInstance ().getUserPage ().UserPageList (FieldStr, TableName, SqlWhere,
                    "s.storeid", "desc",
                    Integer.parseInt (ShowPage),
                    Integer.parseInt (PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleUser.getInstance ().getUserPage ().getTotalRecord ()).append (",\"rows\":[");
            while (UserIt.hasNext ()) {
                User_Pro pro = (User_Pro) UserIt.next ();
                if (pro != null)
                {
                    Str.append("{")
                            .append("\"storeid\":").append("\"").append (pro.getStoreid()).append ("\"").append(",")
                            .append("\"storename\":").append("\"").append(checkStr.String2Json(pro.getStorename())).append("\"").append(",")
                            .append("\"storepy\":").append("\"").append(pro.getStorepy()).append("\"").append(",")
                            .append("\"director\":").append("\"").append(checkStr.String2Json(pro.getDirector())).append("\"").append(",")
                            .append("\"sex\":").append("\"").append(Field_Str.User_Sex[pro.getSex()]).append("\"").append(",")
                            .append("\"birdate\":").append("\"").append(nowdate.set_date(pro.getBirdate(),"yyyy-MM-dd")).append("\"").append(",")
                            .append("\"education\":").append("\"").append(Field_Str.User_Edu[pro.getEducation()]).append("\"").append(",")
                            .append("\"tel\":").append("\"").append(checkStr.IsStrNull(pro.getTelnum())).append("\"").append(",")
                            .append("\"qq\":").append("\"").append(checkStr.IsStrNull(pro.getQq())).append("\"").append(",")
                            .append("\"email\":").append("\"").append(checkStr.IsStrNull(pro.getEmail())).append("\"").append(",")
                            .append("\"areaname\":").append("\"").append(pro.getProvincename() + pro.getCityname() + pro.getAreaname()).append("\"").append(",")
                            .append("\"provinceid\":").append("\"").append(pro.getProvinceid()).append("\"").append(",")
                            .append("\"cityid\":").append("\"").append(pro.getCityid()).append("\"").append(",")
                            .append("\"areaid\":").append("\"").append(pro.getAreaid()).append("\"").append(",")
                            .append("\"status\":").append("\"").append(Field_Str.User_State[pro.getStatus()]).append("\"").append(",")
                            .append("\"divide\":").append("\"").append(pro.getDivide()*100).append("%").append("\"").append(",")
                            .append("\"regip\":").append("\"").append(checkStr.IsStrNull(pro.getRegip())).append("\"").append(",")
                            .append("\"regtime\":").append("\"").append(nowdate.set_date(pro.getRegtime(), "yyyy/MM/dd HH:mm")).append("\"").append(",")
                            .append("\"note\":").append("\"").append(checkStr.IsStrNull(pro.getNodes())).append("\"").append(",")
                            .append("\"ismg\":").append(pro.getIsmg()).append("}")
                            .append(",");
                }
                else
                {
                    Str.append("{")
                            .append ("\"storeid\":").append ("\"\"").append (",")
                            .append ("\"storename\":").append ("\"\"").append (",")
                            .append ("\"storepy\":").append ("\"\"").append (",")
                            .append ("\"director\":").append ("\"\"").append(",")
                            .append("\"sex\":").append ("\"\"").append (",")
                            .append ("\"birdate\":").append ("\"\"").append (",")
                            .append ("\"education\":").append ("\"\"").append (",")
                            .append ("\"tel\":").append ("\"\"").append (",")
                            .append ("\"qq\":").append ("\"\"").append (",")
                            .append ("\"email\":").append ("\"\"").append (",")
                            .append ("\"areaname\":").append ("\"\"").append (",")
                            .append ("\"provinceid\":").append ("\"\"").append (",")
                            .append ("\"cityid\":").append ("\"\"").append (",")
                            .append ("\"areaid\":").append ("\"\"").append (",")
                            .append ("\"status\":").append ("\"\"").append (",")
                            .append ("\"divide\":").append ("\"\"").append(",")
                            .append ("\"regip\":").append ("\"\"").append(",")
                            .append ("\"regtime\":").append ("\"\"").append (",")
                            .append ("\"note\":").append ("\"\"").append (",")
                            .append ("\"ismg\":").append ("\"\"").append ("}")
                            .append (",");
                }
                UserIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String umark = tostr.to_utf_8 (request.getParameter ("umark"));
        if (umark.equals ("mlist"))//打印修改数据
        {
            String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));
            String FieldStr = "s.*",
                TableName = "store s",
                SqlWhere = "";
            if (checkStr.checkIsNum (storeid)) {
                SqlWhere = "s.storeid=" + storeid;
            }
            Iterator UserIt = SingleUser.getInstance ().getUserDb ().UserList ("y", "1", FieldStr, TableName, "", SqlWhere, "s.storeid", "asc").iterator ();
            while (UserIt.hasNext ()) {
                User_Pro pro = (User_Pro) UserIt.next ();
                if (pro != null) {
                    Str.append("{").
                            append("\"storeid\":").append ("\"").append(pro.getStoreid()).append ("\"").append(",").
                            append("\"storename\":").append ("\"").append(checkStr.String2Json(pro.getStorename())).append ("\"").append (",").
                            append("\"director\":").append("\"").append (checkStr.String2Json(pro.getDirector())).append ("\"").append (",").
                            append("\"sex\":").append ("\"").append (pro.getSex()).append ("\"").append (",").
                            append ("\"birdate\":").append ("\"").append (nowdate.set_date(pro.getBirdate(), "yyyy-MM-dd")).append ("\"").append (",").
                            append ("\"edu\":").append ("\"").append (pro.getEducation()).append ("\"").append (",").
                            append ("\"tel\":").append ("\"").append (checkStr.IsStrNull(pro.getTelnum())).append ("\"").append (",").
                            append ("\"qq\":").append ("\"").append (checkStr.IsStrNull (pro.getQq())).append ("\"").append (",").
                            append ("\"email\":").append ("\"").append (checkStr.IsStrNull (pro.getEmail())).append ("\"").append (",").
                            append ("\"areaid\":").append ("\"").append (pro.getAreaid()).append ("\"").append (",").
                            append ("\"status\":").append ("\"").append(pro.getStatus()).append ("\"").append (",").
                            append("\"divide\":").append ("\"").append (pro.getDivide()*100).append ("\"").append (",").
                            append ("\"nodes\":").append ("\"").append (checkStr.IsStrNull (pro.getNodes ())).append ("\"").
                            append("}").append (",");
                }
                else
                {
                    Str.append("{").
                            append("\"storeid\":").append ("\"\"").append(",").
                            append("\"storename\":").append ("\"\"").append (",").
                            append("\"director\":").append ("\"\"").append (",").
                            append("\"sex\":").append ("\"\"").append (",").
                            append("\"birdate\":").append ("\"\"").append (",").
                            append("\"edu\":").append ("\"\"").append (",").
                            append("\"tel\":").append ("\"\"").append(",").
                            append("\"qq\":").append("\"\"").append (",").
                            append("\"email\":").append ("\"\"").append (",").
                            append("\"areaid\":").append ("\"\"").append (",").
                            append("\"status\":").append ("\"\"").append (",").
                            append("\"divide\":").append ("\"\"").append(",").
                            append("\"nodes\":").append ("\"\"").
                            append("}").append(",");
                }
                UserIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
    }
}
