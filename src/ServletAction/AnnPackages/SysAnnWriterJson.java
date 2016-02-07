package ServletAction.AnnPackages;

import Com.AnnPackages.Ann_Pro;
import Com.AnnPackages.SingleAnn;
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
 * User: T410
 * Date: 14-4-12
 * Time: 下午1:57
 * 打印系统公告
 */
@WebServlet (name = "SysAnnWriterJson",urlPatterns = {"/Action/SysAnnWriter.do"})
public class SysAnnWriterJson extends HttpServlet
{
    private Str_code_switch tostr    = null;
    private Format_Date     formdate = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        formdate = new Format_Date ();
    }


    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String sysmark = tostr.to_utf_8 (request.getParameter ("sysmark"));
        if (sysmark.equals ("plist"))
        {
            String sqlwhere="";
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            String islock=tostr.to_utf_8 (request.getParameter ("islock"));
            if (! checkStr.checkIsNum (ShowPage))
                ShowPage = "1";
            if (! checkStr.checkIsNum (PageSize))
                PageSize = "30";
            if(checkStr.checkIsNum (islock))
                sqlwhere=" and s.islock="+islock;
            Iterator AnnIt= SingleAnn.getInstance ().getAnnPage ().SysAnnPageList ("s.*", "sysann s",
                    sqlwhere, "s.sysannid", "desc",
                    Integer.parseInt (ShowPage), Integer.parseInt (PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleAnn.getInstance ().getAnnPage ().getTotalRecord ()).append (",\"rows\":[");
            while (AnnIt.hasNext ())
            {
                Ann_Pro pro=(Ann_Pro)AnnIt.next ();
                if(pro!=null)
                {
                    Str.append ("{")
                            .append ("\"sysannid\":").append ("\"").append (pro.getAnnid ()).append ("\"").append (",")
                            .append ("\"sysanntitle\":").append ("\"").append (checkStr.unkillHtml (checkStr.String2Json (pro.getAnntitle ()))).append ("\"").append (",")
                            .append ("\"sysislock\":").append ("\"").append (Field_Str.Islock[pro.getIslock ()]).append ("\"").append (",")
                            .append ("\"sysanndate\":").append ("\"").append (formdate.set_date (pro.getAnndate (),"yyyy-MM-dd HH:mm:ss")).append ("\"").append ("}")
                            .append (",");
                }
                else
                {
                    Str.append ("{")
                            .append ("\"sysannid\":").append ("\"\"").append (",")
                            .append ("\"sysanntitle\":").append ("\"\"").append (",")
                            .append ("\"sysislock\":").append ("\"\"").append (",")
                            .append ("\"sysanndate\":").append ("\"\"").append ("}")
                            .append (",");
                }
                AnnIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if (sysmark.equals ("homelist"))//首页公告列表
        {
            Iterator AnnIt=SingleAnn.getInstance ().getAnnDb().SysAnnList("", "3", "*", "sysann s", "s.*",
                    "s.islock=0", "s.sysannid", "desc").iterator ();
            Str.append ("[");
            while (AnnIt.hasNext ())
            {
                Ann_Pro pro=(Ann_Pro)AnnIt.next ();
                if(pro!=null)
                {
                    Str.append ("{")
                            .append ("\"sysannid\":").append ("\"").append (pro.getAnnid ()).append ("\"").append (",")
                            .append ("\"sysanntitle\":").append ("\"").append (checkStr.unkillHtml (checkStr.String2Json (pro.getAnntitle ()))).append ("\"").append (",")
                            .append ("\"sysislock\":").append ("\"").append (Field_Str.Islock[pro.getIslock ()]).append ("\"").append (",")
                            .append ("\"sysanndate\":").append ("\"").append (formdate.set_date (pro.getAnndate (),"yyyy-MM-dd HH:mm:ss")).append ("\"").append ("}")
                            .append (",");
                }
                else
                {
                    Str.append ("{")
                            .append ("\"sysannid\":").append ("\"\"").append (",")
                            .append ("\"sysanntitle\":").append ("\"\"").append (",")
                            .append ("\"sysislock\":").append ("\"\"").append (",")
                            .append ("\"sysanndate\":").append ("\"\"").append ("}")
                            .append (",");
                }
                AnnIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }

    protected void doGet (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        StringBuilder Str = new StringBuilder ("");
        String sysmark = tostr.to_utf_8 (request.getParameter ("sysmark"));
        if (sysmark.equals ("mlist"))
        {
            String sysannid=tostr.to_utf_8 (request.getParameter ("sysannid"));
            if(checkStr.checkIsNum (sysannid))
            {
                Iterator AnnIt=SingleAnn.getInstance ().getAnnDb ().SysAnnList ("y", "1", "s.*", "sysann s", "",
                        "s.sysannid=" + sysannid, "s.sysannid", "desc").iterator ();
                while (AnnIt.hasNext ())
                {
                    Ann_Pro pro=(Ann_Pro)AnnIt.next ();
                    if(pro!=null)
                    {
                        Str.append ("{")
                                .append ("\"sysannid\":").append ("\"").append (pro.getAnnid ()).append ("\"").append (",")
                                .append ("\"anntitle\":").append ("\"").append (checkStr.unkillHtml (checkStr.String2Json(pro.getAnntitle ()))).append ("\"").append (",")
                                .append ("\"content\":").append ("\"").append (checkStr.String2Json(pro.getAnncon ())).append ("\"").append (",")
                                .append ("\"islock\":").append ("\"").append (pro.getIslock ()).append ("\"")
                                .append ("}").append (",");
                    }
                    else
                    {
                        Str.append ("{")
                                .append ("\"sysannid\":").append ("\"\"").append (",")
                                .append ("\"anntitle\":").append ("\"\"").append (",")
                                .append ("\"content\":").append ("\"\"").append (",")
                                .append ("\"islock\":").append ("\"\"")
                                .append ("}").append (",");
                    }
                    AnnIt.remove ();
                }
            }
            else
            {
                Str.append ("{")
                        .append ("\"sysannid\":").append ("\"\"").append (",")
                        .append ("\"anntitle\":").append ("\"\"").append (",")
                        .append ("\"content\":").append ("\"\"").append (",")
                        .append ("\"islock\":").append ("\"\"")
                        .append ("}").append (",");
            }
        }
        Str.delete (Str.length () - 1, Str.length ());
        out.print (Str.toString ());
        Str.delete (0, Str.length ());//清空
        out.flush ();
        out.close ();
    }
}
