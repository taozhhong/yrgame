package ServletAction.LoginPackages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2016-01-12.
 * 用户退回
 */
@WebServlet(name = "MemberLogOut",urlPatterns = {"/Action/LogOut.do"})
public class MemberLogOut extends HttpServlet
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        Cookie name=new Cookie("537u_Cookie","");
        name.setMaxAge(0);
        name.setPath("/");
        response.addCookie(name);
        response.sendRedirect("/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        doPost(request,response);
    }
}
