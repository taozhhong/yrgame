package ServletAction.SysSetPackages.LogPackages;

import Com.SharePackages.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-13
 * Time: 下午2:41
 * 打印日志列表
 */
@WebServlet(name = "LogWriterJson",urlPatterns = {"/Action/LogWriter.do"})
public class LogWriterJson extends HttpServlet
{
    private Str_code_switch tostr=null;
    private Format_Date nowdate=null;
    public void init()
    {
        tostr=new Str_code_switch();
        nowdate=new Format_Date();
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder Str=new StringBuilder("");
        String logmark=tostr.to_utf_8(request.getParameter("logmark"));
        if(logmark.equals("list"))//打印数据
        {
            String filename="";
            String mgid=tostr.to_utf_8(request.getParameter("mgid"));
            String ShowPage=tostr.to_utf_8(request.getParameter("page")); //页码,起始值 1
            String PageSize=tostr.to_utf_8(request.getParameter("rows")); //每页显示行
            if(!checkStr.checkIsNum(ShowPage))
                ShowPage="1";
            if(!checkStr.checkIsNum(PageSize))
                PageSize="30";
            if(!checkStr.checkIsNum(mgid))
                filename="";
            else
                filename=mgid+"_systemlog.html";
            FileTarget FileList=new AdapterFile(new ImgAdapter());
            Map valueCol=FileList.FolderList(request.getSession().getServletContext().
                    getRealPath("/")+"Ds_admin/log/",filename);
            File[] file=(File[])valueCol.get("filename");
            if(file!=null)
            {
                int totle=file.length;
                Str.append("{\"total\":").append(totle).append(",\"rows\":[");
                int totlePage=0;
                if(totle>0)
                {
                    if(totle%Integer.parseInt(PageSize)==0)
                        totlePage=totle/Integer.parseInt(PageSize); //共有多少页
                    else
                        totlePage=totle/Integer.parseInt(PageSize)+1;
                    int posion = ((Integer.parseInt(ShowPage)-1) * Integer.parseInt(PageSize)) + 1;//游标的位置 (当前页 - 1) * 页面大小 + 1
                    int endData=Integer.parseInt(PageSize)*Integer.parseInt(ShowPage);
                    for (int i = posion; i <= totle; i++)
                    {
                        if(i<=endData)
                        {
                            Str.append("{")
                                    .append("\"filename\":").append("\"").append(file[i-1].getName().split("\\.")[0]).append("\"").append(",")
                                    .append("\"filetime\":").append("\"").append(nowdate.centrum("yyyy-MM-dd HH:mm").format(new Date(file[i-1].lastModified()))).append("\"")
                                    .append("}").append(",");
                        }
                    }
                }
                else
                {
                    Str.append("{")
                            .append("\"filename\":").append("\"\"").append(",")
                            .append("\"filetime\":").append("\"\"")
                            .append("}").append(",");
                }
            }
            else
            {
                Str.append("{\"total\":").append(0).append(",\"rows\":[");
                Str.append("{")
                        .append("\"filename\":").append("\"\"").append(",")
                        .append("\"filetime\":").append("\"\"")
                        .append("}").append(",");
            }
            Str.delete(Str.length()-1,Str.length());
            Str.append("]}");
            out.print(Str.toString());
            Str.delete(0,Str.length());//清空
        }
    }
}
