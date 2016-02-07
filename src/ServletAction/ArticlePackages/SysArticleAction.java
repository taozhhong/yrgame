package ServletAction.ArticlePackages;

import Com.ArticlePackages.Article_Pro;
import Com.ArticlePackages.SingleArticle;
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
 * Date: 14-7-20
 * Time: 下午3:33
 * 添加文章
 */
@WebServlet(name = "SysArticleAction",urlPatterns = {"/Action/SysArticle.do"})
public class SysArticleAction extends HttpServlet
{
    private Str_code_switch tostr     = null;
    private FileTarget      Log       = null;
    private Format_Date     nowdate   = null;
    private Article_Pro     pro       = null;
    private UserPurview     UserProxy = null;

    public void init()
    {
        tostr = new Str_code_switch();
        Log = new AdapterFile(new ImgAdapter());
        nowdate = new Format_Date();
        pro = new Article_Pro();
        UserProxy = new ProxyUserPurview();
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String UserStr = UserProxy.UserProxy(request);
        if (checkStr.isNull(UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String artmethod = tostr.to_utf_8(request.getParameter("artmethod"));
        if(artmethod.equals("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String cateid=tostr.to_utf_8(request.getParameter("cateid"));
            String title=checkStr.killHtml(tostr.to_utf_8(request.getParameter("title")));
            String xh_editor=tostr.to_utf_8(request.getParameter("xh_editor"));
            String islock=tostr.to_utf_8(request.getParameter("islock"));
            String status=tostr.to_utf_8(request.getParameter("status"));
            if(!checkStr.checkIsNum(cateid) || !checkStr.checkIsNum(islock) ||
                    !checkStr.checkIsNum(status) || checkStr.isNull(title)
                    || checkStr.isNull(xh_editor))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setCateid(Integer.parseInt(cateid));
            pro.setArticletitle(title);
            pro.setContent(xh_editor);
            pro.setIslock(Integer.parseInt(islock));
            pro.setStatus(Integer.parseInt(status));
            if(SingleArticle.getInstance().getArticleDb().ArticleAction("add",pro,"")>0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了 "+title+" 的文章</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"文章发布成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，发布失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if(artmethod.equals("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String artid=tostr.to_utf_8(request.getParameter("artid"));
            if(!checkStr.checkIsNum(artid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setArticleid(Integer.parseInt(artid));
            if(SingleArticle.getInstance().getArticleDb().ArticleAction("del",pro,"")>0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除了编号为 "+artid+" 的文章</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"文章删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if(artmethod.equals("edit"))//修改
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String artid=tostr.to_utf_8(request.getParameter("artid"));
            String cateid=tostr.to_utf_8(request.getParameter("cateid"));
            String title=checkStr.killHtml(tostr.to_utf_8(request.getParameter("title")));
            String xh_editor=tostr.to_utf_8(request.getParameter("xh_editor"));
            String islock=tostr.to_utf_8(request.getParameter("islock"));
            String status=tostr.to_utf_8(request.getParameter("status"));
            if(!checkStr.checkIsNum(artid) ||!checkStr.checkIsNum(cateid)
                    || !checkStr.checkIsNum(islock) ||
                    !checkStr.checkIsNum(status) || checkStr.isNull(title)
                    || checkStr.isNull(xh_editor))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setArticleid(Integer.parseInt(artid));
            pro.setCateid(Integer.parseInt(cateid));
            pro.setArticletitle(title);
            pro.setContent(xh_editor);
            pro.setIslock(Integer.parseInt(islock));
            pro.setStatus(Integer.parseInt(status));
            if(SingleArticle.getInstance().getArticleDb().ArticleAction("edit",pro,"")>0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了编号为 "+artid+" 的文章</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_Admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"文章修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }

}
