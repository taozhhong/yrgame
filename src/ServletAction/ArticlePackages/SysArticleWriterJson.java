package ServletAction.ArticlePackages;

import Com.ArticlePackages.Article_Pro;
import Com.ArticlePackages.SingleArticle;
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
 * Date: 14-7-20
 * Time: 下午2:26
 * 打印文章列表
 */
@WebServlet(name = "SysArticleWriterJson",urlPatterns = {"/Action/SysArticleWriter.do"})
public class SysArticleWriterJson extends HttpServlet
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
        String artmark = tostr.to_utf_8 (request.getParameter ("artmark"));
        if (artmark.equals ("pagelist"))//标签分页
        {
            String FieldStr = "a.*",
                    TableName = "article a",
                    SqlWhere = " and a.articleid<>0";
            String artid = tostr.to_utf_8 (request.getParameter ("artid"));//标签编号
            String theme = tostr.to_utf_8 (request.getParameter ("theme"));//标签名称
            String cateid = tostr.to_utf_8 (request.getParameter ("cateid"));//标签状态
            String islock = tostr.to_utf_8 (request.getParameter ("islock"));//标签状态
            String status = tostr.to_utf_8 (request.getParameter ("status"));//标签状态
            String ShowPage = tostr.to_utf_8 (request.getParameter ("page")); //页码,起始值 1
            String PageSize = tostr.to_utf_8 (request.getParameter ("rows")); //每页显示行
            if(checkStr.checkIsNum(artid))
            {
                SqlWhere += " and a.articleid=" + artid;
            }
            if(!checkStr.isNull(theme))
            {
                SqlWhere += " and instr(a.articletitle,'" + theme + "')>0";
            }
            if(checkStr.checkIsNum(cateid))
            {
                SqlWhere += " and a.cateid=" + cateid;
            }
            if(checkStr.checkIsNum(islock))
            {
                SqlWhere += " and a.islock=" + islock;
            }
            if(checkStr.checkIsNum(status))
            {
                SqlWhere += " and a.status=" + status;
            }
            if (! checkStr.checkIsNum(ShowPage))
            {
                ShowPage = "1";
            }
            if (! checkStr.checkIsNum (PageSize))
            {
                PageSize = "30";
            }
            Iterator ArtIt= SingleArticle.getInstance().getArticlePage().ArticlePageList(
                    FieldStr, TableName, SqlWhere,
                    "a.articleid", "desc", Integer.parseInt(ShowPage),
                    Integer.parseInt(PageSize)).iterator();
            Str.append ("{\"total\":").append (SingleArticle.getInstance ().getArticlePage().getTotalRecord ()).
                    append(",\"totalpage\":").append (SingleArticle.getInstance ().getArticlePage().getTotalPage ()).
                    append (",\"rows\":[");
            while (ArtIt.hasNext())
            {
                Article_Pro pro=(Article_Pro)ArtIt.next();
                if(pro!=null)
                {
                    Str.append ("{").
                            append ("\"articleid\":").append ("\"").append (pro.getArticleid ()).append ("\"").append(",").
                            append("\"catename\":").append ("\"").append (Field_Str.ArticleType[pro.getCateid()]).append("\"").append (",").
                            append ("\"articletitle\":").append ("\"").append (checkStr.unkillHtml(checkStr.String2Json(pro.getArticletitle()))).append ("\"").append (",").
                            append ("\"islock\":").append ("\"").append(pro.getIslock()).append("\"").append (",").
                            append("\"status\":").append ("\"").append(pro.getStatus()).append("\"").append (",").
                            append("\"addtime\":").append ("\"").append (nowdate.set_date (pro.getAddtime(), "yyyy-MM-dd HH:mm:ss")).append("\"").
                            append("}").append (",");
                }
                else
                {
                    Str.append ("{").
                            append ("\"articleid\":").append ("\"\"").append(",").
                            append("\"catename\":").append ("\"\"").append (",").
                            append ("\"articletitle\":").append ("\"\"").append (",").
                            append ("\"islock\":").append ("\"\"").append (",").
                            append ("\"status\":").append ("\"\"").append (",").
                            append("\"addtime\":").append ("\"\"").append ("}").
                            append(",");
                }
                ArtIt.remove();
            }
            Str.delete (Str.length () - 1, Str.length ());
            Str.append("]}");
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
        String artmark = tostr.to_utf_8 (request.getParameter ("artmark"));

        if (artmark.equals ("list"))//文章修改
        {
            String artid = tostr.to_utf_8 (request.getParameter ("artid"));
            String FieldStr = "a.*",
                    TableName = "article a",
                    SqlWhere = "";
            if (checkStr.checkIsNum (artid))
            {
                SqlWhere = "a.articleid=" + artid;
            }
            Iterator ArtIt = SingleArticle.getInstance ().getArticleDb ().
                    ArticleList ("y", "1", FieldStr, TableName, "", SqlWhere, "a.articleid",
                             "desc").iterator ();
            while (ArtIt.hasNext ())
            {
                Article_Pro pro = (Article_Pro) ArtIt.next ();
                if(pro!=null)
                {
                    Str.append ("{").
                            append ("\"articleid\":").append ("\"").append (pro.getArticleid ()).append ("\"").append(",").
                            append("\"cateid\":").append ("\"").append (pro.getCateid()).append("\"").append (",").
                            append ("\"articletitle\":").append ("\"").append (checkStr.unkillHtml(checkStr.String2Json(pro.getArticletitle()))).append ("\"").append (",").
                            append ("\"content\":").append ("\"").append (checkStr.String2Json(pro.getContent())).append ("\"").append (",").
                            append ("\"islock\":").append ("\"").append(pro.getIslock()).append("\"").append (",").
                            append("\"status\":").append ("\"").append(pro.getStatus()).append("\"").append (",").
                            append("\"addtime\":").append ("\"").append (nowdate.set_date (pro.getAddtime(), "yyyy-MM-dd HH:mm:ss")).append("\"").
                            append("}").append (",");
                }
                else
                {
                    Str.append ("{").
                            append ("\"articleid\":").append ("\"\"").append(",").
                            append("\"cateid\":").append ("\"\"").append (",").
                            append ("\"articletitle\":").append ("\"\"").append (",").
                            append ("\"content\":").append ("\"\"").append (",").
                            append ("\"islock\":").append ("\"\"").append (",").
                            append ("\"status\":").append ("\"\"").append (",").
                            append("\"addtime\":").append ("\"\"").append ("}").
                            append(",");
                }
                ArtIt.remove();
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }
}
