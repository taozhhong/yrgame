package ServletAction.LoginPackages;

import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016-01-11.
 * 验证验证码
 */
@WebServlet(name = "CheckCode",urlPatterns = {"/Action/CheckCode.do"})
public class CheckCode extends HttpServlet
{
    private Str_code_switch tostr = null;
    public void init()
    {
        tostr = new Str_code_switch ();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        response.setHeader ("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter ();
        String regmethod=tostr.to_utf_8(request.getParameter("regmethod"));
        if(regmethod.equals("iscode"))//验证验证码
        {
            String logincode = tostr.to_utf_8(request.getParameter("logincode"));
            if (checkStr.isNull(logincode))
            {
                out.print ("{\"err\":\"验证码不能为空\",\"msg\":\"\"}");
                return;
            }
            HttpSession session = request.getSession(true);
            if(session.getAttribute("randomImageStr")==null)
            {
                out.print ("{\"err\":\"验证码已失效\",\"msg\":\"\"}");
                return;
            }
            String ImgCode =session.getAttribute("randomImageStr").toString();
            if(!logincode.toLowerCase().equals(tostr.to_utf_8(ImgCode)))
                out.print ("{\"err\":\"验证码输入不一致\",\"msg\":\"\"}");
            else
                out.print ("{\"err\":\"\",\"msg\":\"验证码相同\"}");
        }
    }

}
