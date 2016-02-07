package ServletAction.SysSetPackages.ParamePackages;

import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;
import Com.SysSetPackages.XmlparamePackages.SingleXml;
import Com.SysSetPackages.XmlparamePackages.Xml_Pro;

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
 * Date: 13-8-27
 * Time: 上午9:39
 * 参数配置操作
 */
@WebServlet(name = "ParameAction",urlPatterns = {"/Action/Parame.do"})
public class ParameAction extends HttpServlet
{
    private Str_code_switch tostr=null;
    private FileTarget Log=null;
    private Format_Date nowdate=null;
    private Xml_Pro pro=null;
    private UserPurview UserProxy=null;
    public void init()
    {
        tostr=new Str_code_switch();
        Log=new AdapterFile(new ImgAdapter());
        nowdate=new Format_Date();
        pro=new Xml_Pro();
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

        String paramemethod=tostr.to_utf_8(request.getParameter("paramemethod"));
        if(paramemethod.equals("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String paramesm=checkStr.killHtml(tostr.to_utf_8(request.getParameter("paramesm")));
            String paramevalue=checkStr.killHtml(tostr.to_utf_8(request.getParameter("paramevalue")));
            if(checkStr.isNull(paramesm) || checkStr.isNull(paramevalue))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setXmlsm(paramesm);
            pro.setXmlvalue(paramevalue);
            if(SingleXml.getInstance().getXml().AddXml(request.getSession().getServletContext().getRealPath("/")+"Ds_Admin/config/parame.xml",pro))
            {
                //记录日志
                Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+" 添加了值为 "+paramevalue+" 的参数</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                out.print ("{\"err\":\"\",\"msg\":\"系统参数添加成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if(paramemethod.equals("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String parameid=tostr.to_utf_8(request.getParameter("parameid"));
            String paramevalue=tostr.to_utf_8(request.getParameter("paramevalue"));
            if(!checkStr.checkIsNum(parameid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            if(SingleXml.getInstance().getXml().DelXml(request.getSession()
                    .getServletContext().getRealPath("/")+"Ds_Admin/config/parame.xml",
                    Integer.parseInt(parameid)))
            {
                //记录日志
                Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+"删除了值为 "+paramevalue+" 的参数</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                out.print ("{\"err\":\"\",\"msg\":\"系统参数删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if(paramemethod.equals("mod"))//编辑
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String paramesm=checkStr.killHtml(tostr.to_utf_8(request.getParameter("paramesm")));
            String paramevalue=checkStr.killHtml(tostr.to_utf_8(request.getParameter("paramevalue")));
            String parameid=tostr.to_utf_8(request.getParameter("parameid"));
            if(checkStr.isNull(paramesm) || checkStr.isNull(paramevalue)
                    || !checkStr.checkIsNum(parameid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setXmlsm(paramesm);
            pro.setXmlvalue(paramevalue);
            if(SingleXml.getInstance().getXml().EditXml(request.getSession().getServletContext().getRealPath("/")+"Ds_Admin/config/parame.xml",pro,Integer.parseInt(parameid)))
            {
                //记录日志
                Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+"编辑了ID为 "+parameid+" 的参数</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                out.print ("{\"err\":\"\",\"msg\":\"系统参数修改成功\"}");
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
