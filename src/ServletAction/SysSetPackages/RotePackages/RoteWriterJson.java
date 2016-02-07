package ServletAction.SysSetPackages.RotePackages;

import Com.SharePackages.Format_Date;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.RoteSetPackages.Rote_Pro;
import Com.SysSetPackages.RoteSetPackages.SingleRote;

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
 * Date: 13-8-14
 * Time: 下午2:01
 * 打印角色
 */
@WebServlet (name = "RoteWriterJson", urlPatterns = {"/Action/RoteWriter.do"})
public class RoteWriterJson extends HttpServlet {
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
        String rotemark = tostr.to_utf_8 (request.getParameter ("rotemark"));
        if (rotemark.equals ("pagelist"))//打印分页数据
        {
            String FieldStr = "r.*",
                    TableName = "rote r";
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if (! checkStr.checkIsNum (ShowPage)) {
                ShowPage = "1";
            }
            if (! checkStr.checkIsNum (PageSize)) {
                PageSize = "20";
            }
            Iterator RoteIt = SingleRote.getInstance ().getRotePage ().RotePageList (FieldStr, TableName, "", "r.roteid", "asc", Integer.parseInt (ShowPage), Integer.parseInt (PageSize)).iterator ();
            Str.append ("{\"total\":").append (SingleRote.getInstance ().getRotePage ().getTotalRecord ()).append (",\"rows\":[");

            while (RoteIt.hasNext ()) {
                Rote_Pro pro = (Rote_Pro) RoteIt.next ();
                if (pro != null) {
                    Str.append ("{").
                            append ("\"roteid\":").append ("\"").append (pro.getRoteid ()).append ("\"").append (",")
                            .append ("\"rotename\":").append ("\"").append (pro.getRotename ()).append ("\"").append (",")
                            .append ("\"remork\":").append ("\"").append (checkStr.IsStrNull (pro.getRemark ())).append ("\"")
                            .append ("}").append (",");
                }
                else {
                    Str.append ("{").append ("\"roteid\":").append ("\"\"").append (",")
                            .append ("\"rotename\":").append ("\"\"").append (",")
                            .append ("\"remork\":").append ("\"\"")
                            .append ("}").append (",");
                }
                RoteIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]}");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
        else if (rotemark.equals ("list"))//打印数据
        {
            Str.append ("[");
            Iterator RoteIt = SingleRote.getInstance ().getReoteDb ().RoteList ("y", "", "t.*", "rote t", "", "", "t.roteid", "asc").iterator ();
            while (RoteIt.hasNext ()) {
                Rote_Pro pro = (Rote_Pro) RoteIt.next ();
                if (pro != null) {
                    Str.append ("{")
                            .append ("\"roteid\":").append ("\"").append (pro.getRoteid ()).append ("\"").append (",")
                            .append ("\"rotename\":").append ("\"").append (pro.getRotename ()).append ("\"")
                            .append ("}").append (",");
                }
                else {
                    Str.append ("{")
                            .append ("\"roteid\":").append ("\"\"").append (",")
                            .append ("\"rotename\":").append ("\"\"")
                            .append ("}").append (",");
                }
                RoteIt.remove ();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append ("]");
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush (); out.close ();
        }
    }
}
