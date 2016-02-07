package Com.SysSetPackages.UserSetPackages.ProxyUserPackages;



import javazoom.upload.MultipartFormDataRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 8:48:47
 */
public class ProxyUserPurview implements UserPurview {
    private UserPurview realuser = null;

    public ProxyUserPurview () {
        realuser = new RealUserPurview ();
    }


    /**
     * 代理方法--查询用户是否可用
     *
     * @param Request 请求对象
     * @return String
     */
    public String UserSelect (HttpServletRequest Request) {
        return realuser.UserSelect (Request);
    }


    /**
     * 判断用户是否有权限操作相应的模展示会
     *
     * @param Request 请求对象
     * @return String：不为空表示有权限 为空:无权限
     */
    @Override
    public String UserProxy (HttpServletRequest Request) {
        return realuser.UserProxy (Request);
    }

    /**
     * 判断用户是否有权限操作相应的模展示会
     *
     * @param Request 请求对象
     * @return String：不为空表示有权限 为空:无权限
     */
    @Override
    public String UserUploadProxy (MultipartFormDataRequest MRequest, HttpServletRequest Request) {
        return realuser.UserUploadProxy (MRequest, Request);
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
        return realuser.UserUploadOptProxy(MRequest,ProxyStr,OptId);
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
    public String UserOptProxy (HttpServletRequest Request, String ProxyStr, String OptId) {
        return realuser.UserOptProxy (Request, ProxyStr, OptId);
    }

    /**
     * 判断Cookie是否有值
     *
     * @param Request HttpServletRequest对角
     * @return String：不为空表示登录 为空:没有登录
     */
    @Override
    public String UserCookie (HttpServletRequest Request) {
        return realuser.UserCookie (Request);
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
        return realuser.WebUserCookie(Request);
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
        return realuser.WebUserSelect(Request);
    }

}
