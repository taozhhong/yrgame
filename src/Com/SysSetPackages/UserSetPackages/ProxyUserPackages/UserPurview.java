package Com.SysSetPackages.UserSetPackages.ProxyUserPackages;



import javazoom.upload.MultipartFormDataRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-13
 * Time: 8:46:41
 */
public interface UserPurview {

    /**
     * 判断用户是否可用
     *
     * @param Request HttpServletRequest对象
     * @return String 返回空字符表示用户不可用，不是空字符表示用户可用
     */
    public String UserSelect(HttpServletRequest Request);

    /**
     * 判断用户对版块是否有权限
     *
     * @param Request HttpServletRequest对象
     * @return String 返回空字符表示用户没有操作权限，不是空字符表示用户有操作权限
     */
    public String UserProxy(HttpServletRequest Request);

    /**
     * 带上传操作时判断用户对版块是否有操作权限
     *
     * @param Request MultipartFormDataRequest对象
     * @return String 返回空字符表示用户没有操作权限，不是空字符表示用户有操作权限
     */
    public String UserUploadProxy(MultipartFormDataRequest MRequest, HttpServletRequest Request);


    /**
     * 判断用户对版块是否有操作功能权限
     *
     * @param ProxyStr 以“，”号相连的字符串 如：pro.getManagerid() + "," + pro.getLoginname() + ","
     *                 + pro.getLogintime()+","+pro.getLoginip()+","+pro.getBmkid()
     *                 +","+pro.getSmkid()+","+pro.getOptid();
     * @param MRequest  MultipartFormDataRequest对象
     * @param OptId    操作权限ID
     * @return String 返回空字符表示用户没有操作功能，不是空字符表示用户有操作功能
     */
    public String UserUploadOptProxy(MultipartFormDataRequest MRequest, String ProxyStr, String OptId);



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
    public String UserOptProxy(HttpServletRequest Request, String ProxyStr, String OptId);


    /**
     * 获取用户登录存在cookie里面的值
     *
     * @param Request HttpServletRequest对象
     * @return String
     */
    public String UserCookie(HttpServletRequest Request);



    /**
     * 获取网页端登录用户存在cookie里面的值
     *
     * @param Request HttpServletRequest对象
     * @return String
     */
    public String WebUserCookie(HttpServletRequest Request);


    /**
     * 判断网页端用户是否可用
     *
     * @param Request HttpServletRequest对象
     * @return String 返回空字符表示用户不可用，不是空字符表示用户可用
     */
    public String WebUserSelect(HttpServletRequest Request);



}
