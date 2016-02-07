package ServletAction.SysSetPackages.ParamePackages;

import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.XmlparamePackages.SingleXml;
import Com.SysSetPackages.XmlparamePackages.Xml_Pro;

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
 * Date: 13-8-27
 * Time: 上午10:55
 * 打印参数列表
 */
@WebServlet(name = "ParameWriterJson",urlPatterns = {"/Action/ParameWriter.do"})
public class ParameWriterJson extends HttpServlet
{
    private Str_code_switch tostr=null;
    public void init()
    {
        tostr=new Str_code_switch();
    }

    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder Str=new StringBuilder("");
        String pmark=tostr.to_utf_8(request.getParameter("pmark"));
        if(pmark.equals("list"))//打印参数数据
        {
            int i=0;
            Iterator ParameIt= SingleXml.getInstance().getXml().ReadXml(request.getSession().
                    getServletContext().getRealPath("/") + "Ds_Admin/config/parame.xml").iterator();
            Str.append("[");
            while (ParameIt.hasNext())
            {
                Xml_Pro pro=(Xml_Pro)ParameIt.next();
                if(pro!=null)
                {
                    Str.append("{")
                            .append("\"parameid\":").append("\"").append(i).append("\"").append(",")
                            .append("\"paramesm\":").append("\"").append(pro.getXmlsm()).append("\"").append(",")
                            .append("\"paramevalue\":").append("\"").append(pro.getXmlvalue()).append("\"")
                            .append("}").append(",");
                    i++;
                }
                else
                {
                    Str.append("{")
                            .append("\"parameid\":").append("\"\"").append(",")
                            .append("\"paramesm\":").append("\"\"").append(",")
                            .append("\"paramevalue\":").append("\"\"")
                            .append("}").append(",");
                }
                ParameIt.remove();
            }
            Str.delete(Str.length() - 1, Str.length());
            Str.append("]");
            out.print(Str.toString());
            Str.delete(0,Str.length());//清空
            out.flush ();
            out.close ();
        }
    }

    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader("content-type","text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        StringBuilder Str=new StringBuilder("");
        String pmark=tostr.to_utf_8(request.getParameter("pmark"));
        if(pmark.equals("mlist"))//打印修改参数数据
        {
            String parameid=tostr.to_utf_8 (request.getParameter ("parameid"));

            if(checkStr.checkIsNum (parameid))
            {
                Iterator ParameIt=SingleXml.getInstance().getXml().SearchXml(request.getSession().
                        getServletContext().getRealPath("/")+"Ds_Admin/config/parame.xml",Integer.parseInt(parameid)).iterator();
                while (ParameIt.hasNext())
                {
                    Xml_Pro pro=(Xml_Pro)ParameIt.next();
                    if(pro!=null)
                    {
                        Str.append ("{").
                                append ("\"parameid\":").append ("\"").append (parameid).append ("\"").append (",")
                                .append ("\"paramesm\":").append ("\"").append (checkStr.unkillHtml (pro.getXmlsm ())).append ("\"").append (",")
                                .append ("\"paramevalue\":").append ("\"").append (checkStr.IsStrNull (pro.getXmlvalue ())).append ("\"")
                                .append ("}").append (",");
                    }
                    else
                    {
                        Str.append ("{")
                                .append ("\"parameid\":").append ("\"\"").append (",")
                                .append ("\"paramesm\":").append ("\"\"").append (",")
                                .append ("\"paramevalue\":").append ("\"\"")
                                .append ("}").append (",");
                    }
                    ParameIt.remove ();
                }
            }
            else
            {
                Str.append ("{")
                        .append ("\"parameid\":").append ("\"\"").append (",")
                        .append ("\"paramesm\":").append ("\"\"").append (",")
                        .append ("\"paramevalue\":").append ("\"\"")
                        .append ("}").append (",");
            }
            Str.delete (Str.length () - 1, Str.length ());
            out.print (Str.toString ());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
        else if(pmark.equals("dlist"))//打印修改数据源参数数据
        {
            String parameid=tostr.to_utf_8 (request.getParameter ("parameid"));
            String storeid=tostr.to_utf_8 (request.getParameter ("storeid"));
            if(checkStr.checkIsNum (parameid) || checkStr.checkIsNum (storeid))
            {
                Iterator ParameIt=SingleXml.getInstance().getXml().SearchDataSourceXml(request.getSession().
                        getServletContext().getRealPath("/") + "Ds_Admin/datasource/data_" + storeid + ".xml", Integer
                        .parseInt(parameid)).iterator();
                while (ParameIt.hasNext())
                {
                    Xml_Pro pro=(Xml_Pro)ParameIt.next();
                    if(pro!=null)
                    {
                        Str.append ("{")
                                .append("\"parameid\":").append ("\"").append (parameid).append("\"").append(",")
                                .append("\"dataip\":").append ("\"").append (checkStr.String2Json(pro.getXmlip())).append ("\"").append(",")
                                .append("\"datadirver\":").append ("\"").append (checkStr.String2Json(pro.getXmldriver())).append ("\"").append(",")
                                .append("\"datauser\":").append ("\"").append (checkStr.String2Json(pro.getXmlsm())).append ("\"").append(",")
                                .append("\"datapwd\":").append ("\"").append (checkStr.String2Json (pro.getXmlvalue())).append ("\"").append(",")
                                .append("\"dataname\":").append ("\"").append (checkStr.String2Json (pro.getXmlkm())).append ("\"").append(",")
                                .append("\"isck\":").append ("\"").append (pro.getIsck()).append ("\"").append(",")
                                .append("\"datauid\":").append ("\"").append (checkStr.String2Json (pro.getXmluidkm())).append ("\"").append(",")
                                .append("\"datastatus\":").append ("\"").append (0).append("\"")
                                .append("}").append (",");
                    }
                    else
                    {
                        Str.append("{")
                                .append("\"parameid\":").append("\"\"").append(",")
                                .append("\"dataip\":").append("\"\"").append(",")
                                .append("\"datadirver\":").append("\"\"").append(",")
                                .append("\"datauser\":").append("\"\"").append(",")
                                .append("\"datapwd\":").append("\"\"").append (",")
                                .append("\"dataname\":").append("\"\"").append(",")
                                .append("\"isck\":").append("\"\"").append(",")
                                .append("\"datauid\":").append("\"\"").append(",")
                                .append("\"datastatus\":").append ("1")
                                .append("}").append (",");
                    }
                    ParameIt.remove ();
                }
            }
            else
            {
                Str.append ("{")
                        .append("\"parameid\":").append("\"\"").append(",")
                        .append("\"dataip\":").append("\"\"").append(",")
                        .append("\"datadirver\":").append("\"\"").append(",")
                        .append("\"datauser\":").append("\"\"").append(",")
                        .append("\"datapwd\":").append("\"\"").append(",")
                        .append("\"dataname\":").append("\"\"").append(",")
                        .append("\"isck\":").append("\"\"").append(",")
                        .append("\"datauid\":").append("\"\"").append(",")
                        .append("\"datastatus\":").append ("1")
                        .append("}").append (",");
            }
            Str.delete(Str.length() - 1, Str.length());
            out.print(Str.toString());
            Str.delete (0, Str.length ());//清空
            out.flush ();
            out.close ();
        }
    }
}
