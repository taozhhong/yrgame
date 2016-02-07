package ServletAction.MemberServlet;

import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2015-11-24.
 * 随机注册用户
 */
@WebServlet(name = "RadomReg",urlPatterns = {"/Action/RadomRegMember.do"})
public class RadomReg extends HttpServlet
{
    private FileTarget Log   = null;
    private Str_code_switch tostr = null;
    private Member_Pro m_pro=null;


    public void init()
    {
        Log = new AdapterFile(new ImgAdapter());
        tostr = new Str_code_switch();
        m_pro=new Member_Pro();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        int sucessuser=0;//导入成功的用户
        //读取用户昵称文件
        String ReadNickStr = Log.ReadFile(request.getServletContext().getRealPath("/") + "nick/nick.txt","utf-8");
        for (int i = 0; i < ReadNickStr.split("\\r\\n").length; i++)
        {
            m_pro.setLoginname("ds"+(i+1));
            m_pro.setNick(ReadNickStr.split("\\r\\n")[i]);
            m_pro.setLoginpwd(Md5.md5("000123"));
            m_pro.setBankpwd(Md5.md5("000123"));
            m_pro.setSex(Math.random()>0.5?0:1);
            m_pro.setTjmemberid(0);
            m_pro.setRegip(checkStr.getIp(request));
            int o_backint = SingleMember.getInstance().getMemberDb().MemberAction("add",m_pro,"");
            if(o_backint>0)
                sucessuser++;
        }
        out.println("共需要导入:"+ReadNickStr.split("\\r\\n").length+"个用户，导入成功:"+sucessuser+"个");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}
