package ServletAction.SysSetPackages.UserPackages;

import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;
import Com.SysSetPackages.UserSetPackages.UserPackages.SingleUser;
import Com.SysSetPackages.UserSetPackages.UserPackages.User_Pro;
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
 * User: T410
 * Date: 13-11-1
 * Time: 下午7:26
 * 员工表操作
 */
@WebServlet (name = "UserAction", urlPatterns = {"/Action/User.do"})
public class UserAction extends HttpServlet {
    private Str_code_switch tostr = null;
    private FileTarget Log = null;
    private Format_Date nowdate = null;
    private User_Pro pro = null;
    private Xml_Pro xmlpro=null;
    private UserPurview UserProxy = null;

    public void init ()
    {
        tostr = new Str_code_switch ();
        Log = new AdapterFile (new ImgAdapter ());
        nowdate = new Format_Date ();
        pro = new User_Pro ();
        xmlpro=new Xml_Pro();
        UserProxy = new ProxyUserPurview ();
    }

    protected void doPost (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String UserStr = UserProxy.UserProxy (request);
        if (checkStr.isNull (UserStr))
        {
            out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
            return;
        }
        String usermethod = tostr.to_utf_8 (request.getParameter ("usermethod"));
        if (usermethod.equals ("add"))//添加
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "0")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String storename = checkStr.killHtml(tostr.to_utf_8(request.getParameter("storename")));//商户名称
            String director = checkStr.killHtml(tostr.to_utf_8(request.getParameter("director")));// 负责人
            String sex = tostr.to_utf_8 (request.getParameter ("sex"));// 性别
            String birdate = tostr.to_utf_8 (request.getParameter ("birdate"));// 出生年月
            String edu = tostr.to_utf_8(request.getParameter("edu"));// 学历
            String tel = checkStr.killHtml(tostr.to_utf_8(request.getParameter("tel")));//联系电话
            String qq = checkStr.killHtml(tostr.to_utf_8(request.getParameter("qq"))); //联系QQ
            String email = checkStr.killHtml(tostr.to_utf_8(request.getParameter("email")));//电子邮件
            String areaid = tostr.to_utf_8(request.getParameter("areaid")); //所在地
            String nodes = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("nodes"))); //备注
            String divide = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("divide")));//分成比例
            String status = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("status")));//备注

            if (checkStr.isNull (storename) || checkStr.isNull (director) ||
                ! checkStr.checkIsNum (sex) || ! checkStr.checkIsPoint(divide)
                || checkStr.isNull (birdate) || ! checkStr.checkIsNum (edu)
                || ! checkStr.checkIsNum (areaid) || ! checkStr.checkIsNum (status))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setStorename (storename);
            pro.setDirector(director);
            pro.setStorepy(GetPinYin.getPinYinHeadChar(storename).toUpperCase());
            pro.setSex(Integer.parseInt(sex));
            pro.setBirdate(birdate);
            pro.setEducation(Integer.parseInt(edu));
            pro.setTelnum(tel);
            pro.setQq(qq);
            pro.setEmail(email);
            pro.setAreaid(Integer.parseInt(areaid));
            pro.setStatus(Integer.parseInt(status));
            pro.setNodes(nodes);
            pro.setRegip(checkStr.getIp(request));
            pro.setDivide((float)checkStr.Divide(Double.parseDouble(divide),100,4,1));
            if (SingleUser.getInstance ().getUserDb ().UserAction ("add", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "添加了名为 " + storename + " 的商户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"商户信息添加成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (usermethod.equals ("del"))//删除
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "1")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String storeid = tostr.to_utf_8 (request.getParameter ("storeid"));
            if (checkStr.isNull (storeid))
            {
                out.print ("{\"err\":\"抱歉，请选择一条数据\",\"msg\":\"\"}");
                return;
            }
            pro.setStoreid(Integer.parseInt(storeid));
            if (SingleUser.getInstance ().getUserDb ().UserAction ("del", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "删除为商户编号为 " + storeid + " 的商户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                Log.FileDel(request.getSession ().getServletContext ().getRealPath ("/")+"datasource/data_"+storeid+".xml");
                out.print ("{\"err\":\"\",\"msg\":\"商户删除成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，删除失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (usermethod.equals ("mod"))//修改
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "2")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String storename = checkStr.killHtml(tostr.to_utf_8(request.getParameter("storename")));//商户名称
            String director = checkStr.killHtml(tostr.to_utf_8(request.getParameter("director")));// 负责人
            String sex = tostr.to_utf_8(request.getParameter("sex"));// 性别
            String birdate = tostr.to_utf_8 (request.getParameter ("birdate"));// 出生年月
            String edu = tostr.to_utf_8(request.getParameter("edu"));// 学历
            String tel = checkStr.killHtml(tostr.to_utf_8(request.getParameter("tel")));//联系电话
            String qq = checkStr.killHtml(tostr.to_utf_8(request.getParameter("qq"))); //联系QQ
            String email = checkStr.killHtml(tostr.to_utf_8(request.getParameter("email")));//电子邮件
            String areaid = tostr.to_utf_8(request.getParameter("areaid")); //所在地
            String nodes = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("nodes"))); //备注
            String divide = checkStr.killHtml (tostr.to_utf_8 (request.getParameter ("divide")));//分成比例
            String status = checkStr.killHtml(tostr.to_utf_8(request.getParameter("status")));//备注
            String storeid = checkStr.killHtml(tostr.to_utf_8(request.getParameter("storeid")));//商务ID
            if (checkStr.isNull (storename) || checkStr.isNull (director) ||
                    ! checkStr.checkIsNum (sex) || ! checkStr.checkIsPoint(divide)
                    || checkStr.isNull (birdate) || ! checkStr.checkIsNum (edu)
                    || ! checkStr.checkIsNum (areaid) || ! checkStr.checkIsNum (status)
                    || ! checkStr.checkIsNum (storeid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            pro.setStoreid(Integer.parseInt(storeid));
            pro.setStorename(storename);
            pro.setStorepy(GetPinYin.getPinYinHeadChar(storename).toUpperCase());
            pro.setDirector(director);
            pro.setSex(Integer.parseInt(sex));
            pro.setBirdate(birdate);
            pro.setEducation(Integer.parseInt(edu));
            pro.setTelnum(tel);
            pro.setQq(qq);
            pro.setEmail(email);
            pro.setAreaid(Integer.parseInt(areaid));
            pro.setStatus(Integer.parseInt(status));
            pro.setNodes(nodes);
            pro.setDivide((float)checkStr.Divide(Double.parseDouble(divide),100,4,1));
            if (SingleUser.getInstance ().getUserDb ().UserAction ("mod", pro, "") > 0)
            {
                Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "修改了编号为 " + storeid + " 的商户</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                out.print ("{\"err\":\"\",\"msg\":\"商户基本信息修改成功\"}");
            }
            else
            {
                out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
            }
        }
        else if (usermethod.equals ("data"))//数据源设置
        {
            if (checkStr.isNull (UserProxy.UserOptProxy (request, UserStr, "4")))
            {
                out.print ("{\"err\":\"抱歉，您无权操作此功能\",\"msg\":\"\"}");
                return;
            }
            String parameid = checkStr.killHtml(tostr.to_utf_8(request.getParameter("parameid")));
            String storeid = checkStr.killHtml(tostr.to_utf_8(request.getParameter("storeid")));//商户ID
            String dataip = checkStr.killHtml(tostr.to_utf_8(request.getParameter("dataip")));//连接数据库IP
            String datadirver = checkStr.killHtml(tostr.to_utf_8(request.getParameter("datadirver")));//连接数据库驱动
            String datauser = checkStr.killHtml(tostr.to_utf_8(request.getParameter("datauser")));//连接数据库用户
            String datapwd = checkStr.killHtml(tostr.to_utf_8(request.getParameter("datapwd")));//连接数据库密码
            String dataname = checkStr.killHtml(tostr.to_utf_8(request.getParameter("dataname")));//连接数据库的库名
            String isck = checkStr.killHtml(tostr.to_utf_8(request.getParameter("isck")));//是否检测充值用户的ID
            String datauid = checkStr.killHtml(tostr.to_utf_8(request.getParameter("datauid")));//请输入检测用户ID的数据的库名
            String datastatus = checkStr.killHtml(tostr.to_utf_8(request.getParameter("datastatus")));//数据源状态,0-添加,1-修改
            if(!checkStr.checkIsNum(storeid) || checkStr.isNull(dataname)
                    || checkStr.isNull(dataip) || checkStr.isNull(datauser)
                    || checkStr.isNull(datapwd)|| !checkStr.checkIsNum(datastatus)
                    || checkStr.isNull(datadirver) ||!checkStr.checkIsNum(isck)
                    || checkStr.isNull(datauid))
            {
                out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                return;
            }
            xmlpro.setXmlkey(storeid);
            xmlpro.setXmlkm(dataname);
            xmlpro.setXmlip(dataip);
            xmlpro.setXmldriver(datadirver);
            xmlpro.setXmlsm(datauser);
            xmlpro.setXmlvalue(datapwd);
            xmlpro.setIsck(isck);
            xmlpro.setXmluidkm(datauid);
            if(Integer.parseInt(datastatus)==1)//添加
            {
                if(SingleXml.getInstance().getXml().AddDataSourceXml(request.getSession().getServletContext().getRealPath("/")+"datasource/data_"+storeid+".xml",xmlpro))
                {
                    //记录日志
                    Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+" 添加了名为为 data_"+storeid+".xml的数据源</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                    out.print ("{\"err\":\"\",\"msg\":\"数据源添加成功\"}");
                }
                else
                {
                    out.print ("{\"err\":\"抱歉，添加失败，请重试\",\"msg\":\"\"}");
                }
            }
            else
            {
                if(!checkStr.checkIsNum(parameid))
                {
                    out.print ("{\"err\":\"请输入完整的信息\",\"msg\":\"\"}");
                    return;
                }
                if(SingleXml.getInstance().getXml().EditDataSourceXml(request.getSession().getServletContext()
                        .getRealPath("/") + "datasource/data_" + storeid + ".xml", xmlpro,Integer.parseInt(parameid)))
                {
                    //记录日志
                    Log.CreateFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/",UserStr.split(",")[0]+"_systemlog.html","<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 ["+UserStr.split(",")[1]+"]于"+nowdate.cu_datetime("yyyy-MM-dd HH:mm:ss")+" 修改了名为为 data_"+storeid+".xml的数据源</div>\r\n"+Log.ReadFile(request.getSession().getServletContext().getRealPath("/")+"Ds_admin/log/"+UserStr.split(",")[0]+"_systemlog.html"),false);
                    out.print ("{\"err\":\"\",\"msg\":\"数据源修改成功\"}");
                }
                else
                {
                    out.print ("{\"err\":\"抱歉，修改失败，请重试\",\"msg\":\"\"}");
                }
            }
        }
        else
        {
            out.print ("{\"err\":\"请勿非法访问\",\"msg\":\"\"}");
        }
    }

}
