package ServletAction.SysSetPackages.LogPackages;

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
 * User: lenovo
 * Date: 13-8-13
 * Time: 下午3:48
 * 操作日志
 */
@WebServlet(name = "LogAction",urlPatterns = {"/Action/Log.do"})
public class LogAction extends HttpServlet
{
    private Str_code_switch tostr=null;
    private FileTarget Log=null;
    private Format_Date nowdate=null;
    private UserPurview UserProxy=null;
    public void init()
    {
        tostr=new Str_code_switch();
        Log=new AdapterFile(new ImgAdapter());
        nowdate=new Format_Date();
        UserProxy=new ProxyUserPurview();
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String UserStr = UserProxy.UserProxy (request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String logmethod=tostr.to_utf_8(request.getParameter("logmethod"));
        if(logmethod.equals("clear"))//清空
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String logname=tostr.to_utf_8(request.getParameter("logname"));
            if(checkStr.isNull(logname))
            {
                out.print ("{\"err\":\"抱歉，请勿非法修改参数\",\"msg\":\"\"}");
                return;
            }
            //清空日志
            Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",logname+".html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>日志被 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+"清空</div>\r\n",false);
            //记录日志
            Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+"清空了名为 "+logname+" 的日志</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
            out.print ("{\"err\":\"\",\"msg\":\"日志清空成功\"}");
        }
        else if(logmethod.equals("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String logname=tostr.to_utf_8(request.getParameter("logname"));
            if(checkStr.isNull(logname))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            if(Log.FileDel(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+logname+".html"))
            {
                //记录日志
                Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+"删除了名为 "+logname+" 的日志</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                out.print ("{\"err\":\"\",\"msg\":\"日志删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }
}
