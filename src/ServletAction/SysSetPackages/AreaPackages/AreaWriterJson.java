package ServletAction.SysSetPackages.AreaPackages;

import Com.SharePackages.Field_Str;
import Com.SharePackages.Format_Date;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.AreaSetPackages.Area_Pro;
import Com.SysSetPackages.AreaSetPackages.SingleArea;

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
 * User: lenovo
 * Date: 13-8-23
 * Time: 上午10:48
 * 打印省份数据
 */
@WebServlet (name = "AreaWriterJson", urlPatterns = {"/Action/AreaWriter.do"})
public class AreaWriterJson extends HttpServlet {
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
        String areamark = tostr.to_utf_8 (request.getParameter ("areamark"));
        if (areamark.equals ("plist"))//打印省份数据
        {
            String FieldStr = "*",
                    TableName = "province",
                    sqlwhere = "";
            String provtype = tostr.to_utf_8 (request.getParameter ("provtype"));
            String provid = tostr.to_utf_8 (request.getParameter ("provid"));
            if (! checkStr.isNull (provtype))
            {
                sqlwhere = "provincestatus=0";
            }
            if (checkStr.checkIsNum (provid))
            {
                sqlwhere = "provinceid=" + provid;
            }
            Iterator ProvinceIt = SingleArea.getInstance ().getArea ().ProvinceList ("y",
                    "", FieldStr, TableName, "", sqlwhere, "provinceid", "asc").iterator ();
            Str.append ("[");
            while (ProvinceIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) ProvinceIt.next ();
                if (pro != null) {
                    Str.append ("{").append ("\"provid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").append ("\"provname\":").append ("\"").append (pro.getName ()).append ("\"").append (",").append ("\"provjp\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").append ("\"cclock\":").append ("\"").append (pro.getStatus ()).append ("\"").append ("}").append (",");
                }
                else {
                    Str.append ("{").append ("\"provid\":").append ("\"\"").append (",").append ("\"provname\":").append ("\"\"").append (",").append ("\"provjp\":").append ("\"\"").append (",").append ("\"cclock\":").append ("\"\"").append ("}").append (",");
                }
                ProvinceIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]"); out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("clist"))//打印地区数据
        {
            String proid = tostr.to_utf_8 (request.getParameter ("proid"));
            String FieldStr = "*",
                TableName = "city",
                SqlWhere = "";
            if (checkStr.checkIsNum (proid)) {
                SqlWhere = "provinceid=" + proid;
            }
            Iterator ProvinceIt = SingleArea.getInstance ().getArea ().CityList ("y",
                    "", FieldStr, TableName, "", SqlWhere, "cityid", "asc").iterator ();
            Str.append ("[");
            while (ProvinceIt.hasNext ())
            {
                Area_Pro pro = (Area_Pro) ProvinceIt.next ();
                if (pro != null)
                {
                    Str.append ("{")
                            .append ("\"cityid\":").append ("\"").append (pro.getId ()).append ("\"").append (",")
                            .append ("\"cityname\":").append ("\"").append (pro.getName ()).append ("\"").append (",")
                            .append ("\"cityjp\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",")
                            .append ("\"citystatus\":").append ("\"").append (pro.getStatus ()).append ("\"")
                            .append ("}").append (",");
                }
                else
                {
                    Str.append ("{")
                            .append ("\"cityid\":").append ("\"\"").append (",")
                            .append ("\"cityname\":").append ("\"\"").append (",")
                            .append ("\"cityjp\":").append ("\"\"").append (",")
                            .append ("\"citystatus\":").append ("\"\"")
                            .append ("}").append (",");
                }
                ProvinceIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("alist"))//打印地区数据
        {
            String cityid = tostr.to_utf_8 (request.getParameter ("cityid"));
            String FieldStr = "*",
                TableName = "area",
                SqlWhere = "";
            if (checkStr.checkIsNum (cityid)) {
                SqlWhere = "cityid=" + cityid;
            }
            Iterator ProvinceIt = SingleArea.getInstance ().getArea ().AreaList ("y", "",
                    FieldStr, TableName, "", SqlWhere, "areaid", "asc").iterator ();
            Str.append ("[");
            while (ProvinceIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) ProvinceIt.next ();
                    if (pro != null) {
                    Str.append ("{").append ("\"areaid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").append ("\"areaname\":").append ("\"").append (pro.getName ()).append ("\"").append (",").append ("\"areajp\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").append ("\"areastatus\":").append ("\"").append (pro.getStatus ()).append ("\"").append ("}").append (",");
                } else {
                    Str.append ("{").append ("\"areaid\":").append ("\"\"").append (",").append ("\"areaname\":").append ("\"\"").append (",").append ("\"areajp\":").append ("\"\"").append (",").append ("\"areastatus\":").append ("\"\"").append ("}").append (",");
                } ProvinceIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("treelist"))//打印省市区三级树列表
        {
            String sqlwhere = "t.parent_id=0";
            String parent_id = tostr.to_utf_8 (request.getParameter ("id"));//获取节点的ID
            if (checkStr.checkIsNum (parent_id))
                sqlwhere = "t.parent_id=" + parent_id;
            String TableStr = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                    "select c.cityid as pid,c.cityname as pname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0 union all " +
                    "select a.areaid as pid,a.areaname as pname,a.cityid as parent_id,3 as jb,a.areapy as py,a.areastatus as status from area a where a.areastatus=0) t";

            String AreaTreeJson = SingleArea.getInstance ().getArea ().
                    AreaTreeEdit(SingleArea.getInstance().getArea().
                                    AreaTreeList("", "*", TableStr, sqlwhere,
                                            "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc"),
                            null, 0);
            out.print (AreaTreeJson);
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("s_treelist"))//打印省市区三级树列表
        {
            String sqlwhere = "t.parent_id=0";
            String parent_id = tostr.to_utf_8 (request.getParameter ("id"));//获取节点的ID
            if (checkStr.checkIsNum (parent_id))
                sqlwhere = "t.parent_id=" + parent_id;
            String TableStr = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                              "select c.cityid as pid,c.cityname as pname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0 union all " +
                              "select a.areaid as pid,a.areaname as pname,a.cityid as parent_id,3 as jb,a.areapy as py,a.areastatus as status from area a where a.areastatus=0) t";

            String AreaTreeJson = SingleArea.getInstance ().getArea ().
                    AreaTreeEdit (SingleArea.getInstance ().getArea ().
                            AreaTreeList ("", "*", TableStr, sqlwhere,
                                    "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc"), null, 0);
            out.print (AreaTreeJson);
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("m_treelist"))//修改用到
        {
            //打印选择了的地区
            String provinceid = tostr.to_utf_8 (request.getParameter ("provinceid"));
            String cityid = tostr.to_utf_8 (request.getParameter ("cityid"));
            String TableName = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                               "select c.cityid as pid,c.cityname as pname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0 union all " +
                               "select a.areaid as pid,a.areaname as pname,a.cityid as parent_id,3 as jb,a.areapy as py,a.areastatus as status from area a where a.areastatus=0) t";
            //打印省分
            String sqlwhere = "t.parent_id=0";
            String parent_id = tostr.to_utf_8 (request.getParameter ("id"));//获取节点的ID
            if (checkStr.checkIsNum (parent_id))
            {
                sqlwhere = "t.parent_id=" + parent_id;
            }
            String TableStr = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                              "select c.cityid as pid,c.cityname as pname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0 union all " +
                              "select a.areaid as pid,a.areaname as pname,a.cityid as parent_id,3 as jb,a.areapy as py,a.areastatus as status from area a where a.areastatus=0) t";
            if (! checkStr.checkIsNum (provinceid) || ! checkStr.checkIsNum (cityid))
            {
                String AreaTreeJson = SingleArea.getInstance ().getArea ().
                        AreaTreeEdit (SingleArea.getInstance ().getArea ().
                                AreaTreeList ("", "*", TableStr, sqlwhere,
                                        "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc"), null, 0);
                out.print (AreaTreeJson);
                out.flush ();
                out.close ();
            }
            else
            {
                String AreaTreeJson = SingleArea.getInstance ().getArea ()
                        .AreaTreeEdit (SingleArea.getInstance ().getArea ()
                                .AreaTreeList ("", "*", TableStr, sqlwhere,
                                        "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc"),
                                SingleArea.getInstance ().getArea ().
                                        AreaTreeList ("", "*", TableName, "",
                                                "start with t.pid=" + provinceid + " CONNECT BY t.parent_id=PRIOR t.pid", "t.pid", "asc"),
                                Integer.parseInt (cityid));
                out.print (AreaTreeJson);
                out.flush ();
                out.close ();
            }

        }
        else if(areamark.equals ("add_treelist"))//添加地区时候的树组件，只打印省市二级
        {
            //打印选择了的地区
            String provid = tostr.to_utf_8 (request.getParameter ("provid"));
            String TableName = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                               "select c.cityid as cid,c.cityname as cname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0) t";
            //打印省分
            String sqlwhere = "t.parent_id=0";
            String parent_id = tostr.to_utf_8 (request.getParameter ("id"));//获取节点的ID
            if (checkStr.checkIsNum (parent_id))
                sqlwhere = "t.parent_id=" + parent_id;
            String TableStr = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                              "select c.cityid as cid,c.cityname as cname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0) t";
            if (! checkStr.checkIsNum (provid))
            {
                String AreaTreeJson = SingleArea.getInstance ().getArea ().
                        AreaTreeCom(SingleArea.getInstance().getArea().
                                AreaTreeList("", "*", TableStr, sqlwhere,
                                        "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid",
                                        "asc"), null, parent_id);
                out.print (AreaTreeJson);
                out.flush ();
                out.close ();
            }
            else
            {
                String AreaTreeJson = SingleArea.getInstance ().getArea ()
                        .AreaTreeCom (SingleArea.getInstance ().getArea ()
                                .AreaTreeList ("", "*", TableStr, sqlwhere,
                                        "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc"),
                                SingleArea.getInstance ().getArea ().
                                        AreaTreeList ("", "*", TableName, "",
                                                "start with t.pid=" + provid + " CONNECT BY t.parent_id=PRIOR t.pid", "t.pid", "asc"),parent_id);
                out.print (AreaTreeJson);
                out.flush ();
                out.close ();
            }
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String areamark = tostr.to_utf_8 (request.getParameter ("areamark"));
        if (areamark.equals ("mlist"))//打印省份数据
        {
            String TableStr = "(select p.provinceid as pid,p.provincename as pname,0 as parent_id,1 as jb,p.provincepy as py,p.provincestatus as status from province p where p.provincestatus=0 union all " +
                              "select c.cityid as pid,c.cityname as pname,c.provinceid as parent_id,2 as jb,c.citypy as py,c.citystatus as status from city c where c.citystatus=0 union all " +
                              "select a.areaid as pid,a.areaname as pname,a.cityid as parent_id,3 as jb,a.areapy as py,a.areastatus as status from area a where a.areastatus=0) t";
            String areaid = tostr.to_utf_8 (request.getParameter ("areaid"));
            if (!checkStr.checkIsNum (areaid))
            {
                Str.append ("{").
                        append ("\"areaid\":").append ("\"\"").append (",").
                        append ("\"areaname\":").append ("\"\"").append (",").
                        append ("\"jb\":").append ("\"\"").append (",").
                        append ("\"status\":").append ("\"\"").
                        append ("}").append (",");
            }
            else
            {
                Iterator AreaIt = SingleArea.getInstance ().getArea ().
                        AreaTreeList ("", "*", TableStr, "t.pid="+areaid,
                                "start with t.parent_id=0 CONNECT BY t.parent_id= PRIOR t.pid", "t.pid", "asc").iterator ();

                while (AreaIt.hasNext ())
                {
                    Area_Pro pro = (Area_Pro) AreaIt.next ();
                    if (pro != null)
                    {
                        Str.append ("{").
                                append ("\"areaid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").
                                append ("\"areaname\":").append ("\"").append (checkStr.unkillHtml (pro.getName ())).append ("\"").append (",").
                                append ("\"jb\":").append ("\"").append (pro.getJb ()).append ("\"").append (",").
                                append ("\"status\":").append ("\"").append (pro.getStatus ()).append ("\"").
                                append ("}").append (",");
                    }
                    else
                    {
                        Str.append ("{").
                                append ("\"areaid\":").append ("\"\"").append (",").
                                append ("\"areaname\":").append ("\"\"").append (",").
                                append ("\"jb\":").append ("\"\"").append (",").
                                append ("\"status\":").append ("\"\"").
                                append ("}").append (",");
                    }
                    AreaIt.remove ();
                }
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
        else if (areamark.equals ("prvlist"))//打印省份数据
        {
            String FieldStr = "p.*",
                    TableName = "province p",
                    sqlwhere = "p.provincestatus=0";
            Iterator ProvinceIt = SingleArea.getInstance ().getArea ().ProvinceList ("y",
                    "", FieldStr, TableName, "", sqlwhere, "provinceid", "asc").iterator ();
            Str.append("[");
            while (ProvinceIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) ProvinceIt.next ();
                if (pro != null) {
                    Str.append ("{").
                            append ("\"provid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").
                            append ("\"provname\":").append ("\"").append (pro.getName ()).append ("\"").append (",").
                            append ("\"provjp\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").
                            append ("\"cclock\":").append ("\"").append (pro.getStatus ()).append ("\"").
                            append ("}").append (",");
                }
                else {
                    Str.append ("{").
                            append ("\"provid\":").append ("\"\"").append (",").
                            append ("\"provname\":").append ("\"\"").append (",").
                            append ("\"provjp\":").append ("\"\"").append (",").
                            append ("\"cclock\":").append ("\"\"").
                            append ("}").append (",");
                }
                ProvinceIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("citylist"))//打印市份数据
        {
            String FieldStr = "c.*",
                    TableName = "city c",
                    sqlwhere = "c.citystatus=0";
            String provid=tostr.to_utf_8 (request.getParameter ("provid"));
            if(checkStr.checkIsNum (provid))
            {
                sqlwhere+=" and c.provinceid="+provid;
            }
            Iterator CityIt = SingleArea.getInstance ().getArea ().CityList ("y",
                    "", FieldStr, TableName, "", sqlwhere, "cityid", "asc").iterator ();
            Str.append("[");
            while (CityIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) CityIt.next ();
                if (pro != null) {
                    Str.append ("{").
                            append ("\"cityid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").
                            append ("\"cityname\":").append ("\"").append (pro.getName ()).append ("\"").append (",").
                            append ("\"citypy\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").
                            append ("\"citystatus\":").append ("\"").append (pro.getStatus ()).append ("\"").
                            append ("}").append (",");
                }
                else {
                    Str.append ("{").
                            append ("\"cityid\":").append ("\"\"").append (",").
                            append ("\"cityname\":").append ("\"\"").append (",").
                            append ("\"citypy\":").append ("\"\"").append (",").
                            append ("\"citystatus\":").append ("\"\"").
                            append ("}").append (",");
                }
                CityIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("arealist"))//地区区数据
        {
            String FieldStr = "a.*",
                    TableName = "area a",
                    sqlwhere = "a.areastatus=0";
            String cityid=tostr.to_utf_8 (request.getParameter ("cityid"));
            if(checkStr.checkIsNum (cityid))
            {
                sqlwhere+=" and a.cityid="+cityid;
            }
            Iterator AreaIt = SingleArea.getInstance ().getArea ().AreaList ("y",
                    "", FieldStr, TableName, "", sqlwhere, "areaid", "asc").iterator ();
            Str.append("[");
            while (AreaIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) AreaIt.next ();
                if (pro != null) {
                    Str.append ("{").
                            append ("\"areaid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").
                            append ("\"areaname\":").append ("\"").append (pro.getName ()).append ("\"").append (",").
                            append ("\"areapy\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").
                            append ("\"areastatus\":").append ("\"").append (pro.getStatus ()).append ("\"").
                            append ("}").append (",");
                }
                else {
                    Str.append ("{").
                            append ("\"areaid\":").append ("\"\"").append (",").
                            append ("\"areaname\":").append ("\"\"").append (",").
                            append ("\"areapy\":").append ("\"\"").append (",").
                            append ("\"areastatus\":").append ("\"\"").
                            append ("}").append (",");
                }
                AreaIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (areamark.equals ("villagelist"))//小区数据
        {
            String FieldStr = "v.*",
                    TableName = "village v",
                    sqlwhere = "v.villagestatus=0";
            String areaid=tostr.to_utf_8 (request.getParameter ("areaid"));
            if(checkStr.checkIsNum (areaid))
            {
                sqlwhere+=" and v.areaid="+areaid;
            }
            Iterator VillageIt = SingleArea.getInstance ().getArea ().VillageList ("y",
                    "", FieldStr, TableName, "", sqlwhere, "villageid", "asc").iterator ();
            Str.append("[");
            while (VillageIt.hasNext ()) {
                Area_Pro pro = (Area_Pro) VillageIt.next ();
                if (pro != null) {
                    Str.append ("{").
                            append ("\"villageid\":").append ("\"").append (pro.getId ()).append ("\"").append (",").
                            append ("\"villagename\":").append ("\"").append (pro.getName ()).append ("\"").append (",").
                            append ("\"villagepy\":").append ("\"").append (pro.getNamepy ()).append ("\"").append (",").
                            append ("\"villagestatus\":").append ("\"").append (pro.getStatus ()).append ("\"").
                            append ("}").append (",");
                }
                else {
                    Str.append ("{").
                            append ("\"villageid\":").append ("\"\"").append (",").
                            append ("\"villagename\":").append ("\"\"").append (",").
                            append ("\"villagepy\":").append ("\"\"").append (",").
                            append ("\"villagestatus\":").append ("\"\"").
                            append ("}").append (",");
                }
                VillageIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }
}
