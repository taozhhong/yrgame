package Com.SysSetPackages.UserSetPackages.ProxyUserPackages;



import Com.MemberPackages.Member_Pro;
import Com.MemberPackages.SingleMember;
import Com.SharePackages.Md5;
import Com.SharePackages.Str_code_switch;
import Com.SharePackages.checkStr;
import Com.SysSetPackages.UserSetPackages.UserPackages.SingleUser;
import Com.SysSetPackages.UserSetPackages.UserPackages.User_Pro;
import javazoom.upload.MultipartFormDataRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 8:51:51
 */
public class RealUserPurview implements UserPurview
{
    private Str_code_switch tostr = null;

    public RealUserPurview () {
        tostr = new Str_code_switch ();
    }


    /**
     * 真实查询用户是否可用
     *
     * @param Request 请求对象
     * @return String
     */
    public String UserSelect (HttpServletRequest Request) {
        String UserStr = "";
        String cookie_value = IsUserLogin (Request);
        if (! checkStr.isNull (cookie_value) || cookie_value.contains (","))
        {
            Iterator UserLogit = SingleUser.getInstance ().getUserDb ()
                    .UserUnList ("y", "1", "m.*,s.storename as storename,r.rotename as rotename," + "r.b_mkid as bmkid,r.s_mkid as smkid,r.optid as optid",
                            "manger_user m,rote r,store s", "",
                            "m.userid=" + cookie_value.split (",")[0] + " and m.loginname='" + cookie_value.split (",")[1] + "'" +
                            " and m.islock=0 and m.roteid=r.roteid and m.userid=s.storeid and s.status=0", "m.userid", "desc").iterator ();
            while (UserLogit.hasNext ())
            {
                User_Pro pro = (User_Pro) UserLogit.next ();
                if (pro != null)
                    UserStr = pro.getUserid () + "," + pro.getLoginname () + "," + pro.getLogintime () + "," + pro.getLoginip () + "," + pro.getBmkid () + "," + pro.getSmkid () + "," + pro.getOptid () + "," + pro.getStorename()+","+pro.getIsprive();
                else
                    UserStr = "";
                UserLogit.remove ();
            }
        }
        return UserStr;
    }

    /**
     * 判断用户对版块是否有权限
     *
     * @param Request HttpServletRequest对象
     * @return String 返回空字符表示用户没有操作功能，不是空字符表示用户有操作功能
     */
    @Override
    public String UserProxy (HttpServletRequest Request)
    {
        String UserStr = "";
        String B_MkId = tostr.to_utf_8 (Request.getParameter ("bid"));//大版块ID
        String S_MkId = tostr.to_utf_8 (Request.getParameter ("sid"));//小版块ID
        if (! checkStr.checkIsNum (B_MkId) || ! checkStr.checkIsNum (S_MkId))
        {
            UserStr = "";
        }
        else
        {
            String cookie_value = IsUserLogin (Request);
            if (! checkStr.isNull (cookie_value) || cookie_value.contains (","))
            {
                Iterator UserLogit = SingleUser.getInstance ().getUserDb ().
                        UserUnList ("y", "1", "m.*,s.storename as storename," +
                                              "r.rotename as rotename," +
                                              "r.b_mkid as bmkid,r.s_mkid as smkid," +
                                              "r.optid as optid",
                                "manger_user m,rote r,store s", "",
                                "m.userid=" + cookie_value.split (",")[0] + " " +
                                "and m.loginname='" + cookie_value.split (",")[1] + "' " +
                                "and m.islock=0 and m.roteid=r.roteid and s.status=0",
                                "m.userid", "desc").iterator ();
                while (UserLogit.hasNext ())
                {
                    User_Pro pro = (User_Pro) UserLogit.next ();
                    if (pro != null)
                    {
                        for (int i = 0; i < pro.getBmkid ().split ("&").length; i++)//大模块
                        {
                            if (pro.getBmkid ().split ("&")[i].equals (B_MkId))//有权限操作大模块
                            {
                                for (int ii = 0; ii < pro.getSmkid ().split ("\\|")[i].split ("&").length; ii++)//小模块
                                {
                                    if (pro.getSmkid ().split ("\\|")[i].split ("&")[ii].equals (S_MkId))//有权限操作小模块
                                    {
                                        UserStr = pro.getUserid () + "," + pro.getLoginname () + "," + pro.getLogintime () + "," + pro.getLoginip () + "," + pro.getBmkid () + "," + pro.getSmkid () + "," + pro.getOptid () + "," + pro.getStorename()+","+pro.getIsprive();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    UserLogit.remove ();
                }
            }
        }
        return UserStr;
    }

    /**
     * 判断用户对版块是否有操作权限
     *
     * @param Request MultipartFormDataRequest对象
     * @return String 返回空字符表示用户没有操作权限，不是空字符表示用户有操作权限
     */
    @Override
    public String UserUploadProxy (MultipartFormDataRequest MRequest,
                                   HttpServletRequest Request) {
        String UserStr = "";
        String B_MkId = tostr.to_utf_8 (MRequest.getParameter ("bid"));//大版块ID
        String S_MkId = tostr.to_utf_8 (MRequest.getParameter ("sid"));//小版块ID
        if (! checkStr.checkIsNum (B_MkId) || ! checkStr.checkIsNum (S_MkId))
        {
            UserStr = "";
        }
        else
        {
            String cookie_value = IsUserLogin (Request);
            if (! checkStr.isNull (cookie_value) || cookie_value.contains (",")) {
                Iterator UserLogit = SingleUser.getInstance ().getUserDb ().
                        UserUnList ("y", "1", "m.*,s.storename as storename," +
                                        "r.rotename as rotename,r.b_mkid as bmkid," +
                                        "r.s_mkid as smkid,r.optid as optid",
                                "manger_user m,rote r,store s", "",
                                "m.userid=" + cookie_value.split (",")[0] + "and m.loginname='" + cookie_value.split (",")[1] + "' and m.islock=0" +
                                        " and m.roteid=r.roteid and s.status=0", "m.userid", "desc").iterator ();
                while (UserLogit.hasNext ())
                {
                    User_Pro pro = (User_Pro) UserLogit.next ();
                    if (pro != null)
                    {
                        for (int i = 0; i < pro.getBmkid ().split ("&").length; i++)//大模块
                        {
                            if (pro.getBmkid ().split ("&")[i].equals (B_MkId))//有权限操作大模块
                            {
                                for (int ii = 0; ii < pro.getSmkid ().split ("\\|")[Integer.parseInt (B_MkId)].split ("&").length; ii++)//小模块
                                {
                                    if (pro.getSmkid ().split ("\\|")[Integer.parseInt (B_MkId)].split ("&")[ii].equals (S_MkId))//有权限操作小模块
                                    {
                                        UserStr = pro.getUserid () + "," + pro.getLoginname () + "," + pro.getLogintime () + "," + pro.getLoginip () + "," + pro.getBmkid () + "," + pro.getSmkid () + "," + pro.getOptid () + "," + pro.getStorename () +","+pro.getIsprive();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    UserLogit.remove ();
                }
            }
        }

        return UserStr;
    }

    /**
     * 判断用户对版块是否有操作功能权限
     *
     * @param MRequest MultipartFormDataRequest对象
     * @param ProxyStr 以“，”号相连的字符串 如：pro.getManagerid() + "," + pro.getLoginname() + ","
     *                 + pro.getLogintime()+","+pro.getLoginip()+","+pro.getBmkid()
     *                 +","+pro.getSmkid()+","+pro.getOptid();
     * @param OptId    操作权限ID
     * @return String 返回空字符表示用户没有操作功能，不是空字符表示用户有操作功能
     */
    @Override
    public String UserUploadOptProxy(MultipartFormDataRequest MRequest,
                                     String ProxyStr, String OptId)
    {
        String UserStr = "";
        String B_MkId = tostr.to_utf_8 (MRequest.getParameter ("bid"));//大版块ID
        String S_MkId = tostr.to_utf_8 (MRequest.getParameter ("sid"));//小版块ID
        if (! checkStr.checkIsNum (B_MkId) || ! checkStr.checkIsNum (S_MkId))
        {
            UserStr = "";
        }
        else
        {
            if (! checkStr.isNull (ProxyStr) || ProxyStr.contains (","))
            {
                for (int i = 0; i < ProxyStr.split (",")[4].split ("&").length; i++)//大模块
                {
                    if (ProxyStr.split (",")[4].split ("&")[i].equals (B_MkId))
                    {
                        for (int ii = 0; ii < ProxyStr.split (",")[5].split ("\\|")[Integer.parseInt (B_MkId)].split ("&").length; ii++)//小模块
                        {
                            if (ProxyStr.split (",")[5].split ("\\|")[Integer.parseInt (B_MkId)].split ("&")[ii].equals (S_MkId))//查询小类所在块版权中的标号
                            {
                                for (int iii = 0; iii < ProxyStr.split (",")[6].split ("~")[Integer.parseInt (B_MkId)].split ("\\|")[ii].split ("&").length; iii++)//操作功能权限
                                {
                                    if (ProxyStr.split (",")[6].split ("~")[Integer.parseInt (B_MkId)].split ("\\|")[ii].split ("&")[iii].equals (OptId))
                                    {
                                        UserStr = ProxyStr;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return UserStr;
    }

    /**
     * 判断用户对版块是否有操作功能权限
     *
     * @param ProxyStr 以“，”号相连的字符串 如：pro.getManagerid() + "," + pro.getLoginname() + ","
     *                 + pro.getLogintime()+","+pro.getLoginip()+","+pro.getBmkid()
     *                 +","+pro.getSmkid()+","+pro.getOptid();
     * @param Request  HttpServletRequest对象
     * @param OptId    操作权限ID
     * @return String 返回空字符表示用户没有操作功能，不是空字符表示用户有操作功能
     */
    @Override
    public String UserOptProxy (HttpServletRequest Request,
                                String ProxyStr, String OptId)
    {
        String UserStr = "";
        String B_MkId = tostr.to_utf_8 (Request.getParameter ("bid"));//大版块ID
        String S_MkId = tostr.to_utf_8 (Request.getParameter ("sid"));//小版块ID
        if (! checkStr.checkIsNum (B_MkId) || ! checkStr.checkIsNum (S_MkId))
        {
            UserStr = "";
        }
        else
        {
            if (! checkStr.isNull (ProxyStr) || ProxyStr.contains (","))
            {
                for (int i = 0; i < ProxyStr.split (",")[4].split ("&").length; i++)//大模块
                {
                    if (ProxyStr.split (",")[4].split ("&")[i].equals (B_MkId))
                    {
                        for (int ii = 0; ii < ProxyStr.split (",")[5].split ("\\|")[i].split ("&").length; ii++)//小模块
                        {
                            if (ProxyStr.split (",")[5].split ("\\|")[i].split ("&")[ii].equals (S_MkId))//查询小类所在块版权中的标号
                            {
                                for (int iii = 0; iii < ProxyStr.split (",")[6].split ("~")[i].split ("\\|")[ii].split ("&").length; iii++)//操作功能权限
                                {
                                    if (ProxyStr.split (",")[6].split ("~")[i].split ("\\|")[ii].split ("&")[iii].equals (OptId))
                                    {
                                        UserStr = ProxyStr;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return UserStr;
    }

    @Override
    public String UserCookie (HttpServletRequest Request) {
        return IsUserLogin (Request);
    }

    /**
     * 获取网页端登录用户存在cookie里面的值
     *
     * @param Request HttpServletRequest对象
     * @return String
     */
    @Override
    public String WebUserCookie(HttpServletRequest Request)
    {
        return null;
    }

    /**
     * 判断网页端用户是否可用
     *
     * @param Request HttpServletRequest对象
     * @return String 返回空字符表示用户不可用，不是空字符表示用户可用
     */
    @Override
    public String WebUserSelect(HttpServletRequest Request)
    {
        String UserStr = "";
        String cookie_value = IsWebUserLogin(Request);
        if (! checkStr.isNull (cookie_value) || cookie_value.contains (","))
        {
            Iterator MemberIt= SingleMember.getInstance().getMemberDb().MemberList("y", "1", "m.*", "member m", "",
                    "m.memberid=" + cookie_value.split(",")[0]+" and m.status=0", "m.memberid", "desc").iterator();
            while (MemberIt.hasNext ())
            {
                Member_Pro pro = (Member_Pro) MemberIt.next();
                if (pro != null)
                    UserStr = pro.getMemberid () + "," + pro.getLoginname () + "," +pro.getNick();
                else
                    UserStr = "";
                MemberIt.remove ();
            }
        }
        return UserStr;
    }


    /**
     * 判断用户是否登录，查看cookie是否有值
     *
     * @param Request 请求对象
     * @return String 存放在cookie里面的值
     */
    public String IsUserLogin (HttpServletRequest Request)
    {
        Cookie[] name1 = Request.getCookies ();
        String cookie_wm_value = "";
        if (name1 != null) {
            for (int i = 0; i < name1.length; i++)
            {
                Cookie c = name1[i];
                if (c.getName ().equals ("PayStoreSystemCookieValue"))
                {
                    cookie_wm_value = Str_code_switch.decrypt (c.getValue ());
                    break;

                }
                else
                {
                    cookie_wm_value = "";
                }
            }
        }
        return cookie_wm_value;
    }


    /**
     * 判断网页端用户是否登录，查看cookie是否有值
     *
     * @param Request 请求对象
     * @return String 存放在cookie里面的值
     */
    public String IsWebUserLogin (HttpServletRequest Request)
    {
        Cookie[] name1 = Request.getCookies ();
        String cookie_game_value = "";
        if (name1 != null) {
            for (int i = 0; i < name1.length; i++)
            {
                Cookie c = name1[i];
                if (c.getName ().equals ("537u_Cookie"))
                {
                    cookie_game_value = Str_code_switch.decrypt (c.getValue ());
                    break;

                }
                else
                {
                    cookie_game_value = "";
                }
            }
        }
        return cookie_game_value;
    }
}