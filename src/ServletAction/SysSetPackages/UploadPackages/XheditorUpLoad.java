package ServletAction.SysSetPackages.UploadPackages;

import Com.SharePackages.*;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.ProxyUserPurview;
import Com.SysSetPackages.UserSetPackages.ProxyUserPackages.UserPurview;
import javazoom.upload.MultipartFormDataRequest;
import javazoom.upload.UploadBean;
import javazoom.upload.UploadException;
import javazoom.upload.UploadFile;
import uploadutilities.FileMover;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-8
 * Time: 下午9:45
 * 编辑器上传图片
 */
@WebServlet (name = "XheditorUpLoad",urlPatterns = {"/Action/XheditorLoad.do"})
public class XheditorUpLoad extends HttpServlet
{
    private Str_code_switch tostr     = null;
    private FileTarget      Log       = null;
    private UploadBean      upBean    = null;
    private FileMover       fileMover = null;
    private Format_Date     nowdate   = null;
    private UserPurview     UserProxy = null;


    public void init()
    {
        tostr = new Str_code_switch();
        Log = new AdapterFile(new ImgAdapter());
        nowdate = new Format_Date();
        upBean = new UploadBean();
        fileMover = new FileMover();
        UserProxy = new ProxyUserPurview();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            String newfile = "", sLocalFileName = "", sExt = "",
                    dirpath = "upload/",
                    month = nowdate.cu_datetime("yyyy-MM");
            upBean.setStoremodel (0);
            upBean.setOverwrite (true);
            upBean.setFolderstore(request.getServletContext().getRealPath("/") + dirpath+month);//设置上传文件路径
            upBean.setParser (MultipartFormDataRequest.CFUPARSER);
            upBean.setOverwritepolicy ("nametimestamp");
            upBean.setFilesizelimit (1024 * 1024 * 10);//单位字节,10M
            upBean.setWhitelist ("*.jpg,*.gif,*.bmp,*.png");
            if (MultipartFormDataRequest.isMultipartFormData (request))
            {
                // Uses MultipartFormDataRequest to parse the HTTP request.
                MultipartFormDataRequest mrequest = new MultipartFormDataRequest (request);
                String UserStr = UserProxy.UserCookie (request);
                if (checkStr.isNull (UserStr))
                {
                    out.print ("{\"err\":\"抱歉，您无权操作此模块\",\"msg\":\"\"}");
                    return;
                }
                Hashtable files = mrequest.getFiles ();
                if ((files != null) && (! files.isEmpty ()))
                {
                    newfile = nowdate.cu_datetime ("yyyyMMddHHmmss");
                    UploadFile file = (UploadFile) files.get("filedata");
                    if (! checkStr.isNull (file.getFileName ()))
                    {
                        sLocalFileName = file.getFileName ();
                        //int ii = sLocalFileName.indexOf(".");//取文件名的后缀
                        //sExt = sLocalFileName.substring(ii + 1, sLocalFileName.length());
                        sExt = sLocalFileName.split ("\\.")[sLocalFileName.split ("\\.").length - 1];
                        upBean.addUploadListener (fileMover);//增加filMover监听
                        fileMover.setNewfilename (newfile + "." + sExt);//设置服务器上的文件名
                        try
                        {
                            upBean.store(mrequest,"filedata");
                            Log.CreateFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/", UserStr.split (",")[0] + "_systemlog.html", "<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;'>用户 [" + UserStr.split (",")[1] + "]于" + nowdate.cu_datetime ("yyyy-MM-dd HH:mm:ss") + "上传了名为 " + (newfile + "." + sExt) + " 的资讯图片,存放于" + dirpath + "目录下</div>\r\n" + Log.ReadFile (request.getSession ().getServletContext ().getRealPath ("/") + "Ds_admin/log/" + UserStr.split (",")[0] + "_systemlog.html"), false);
                            //产生缩略图
                            Log.CreateThumb(request.getSession().getServletContext().getRealPath("/")+dirpath+month+"/",
                                    newfile + "." + sExt,500);
                            out.print ("{\"err\":\"\",\"msg\":\"/"+dirpath+month+"/"+newfile + "." + sExt+"\"}");
                        }
                        catch (Exception e)
                        {
                            out.print ("{\"err\":\"抱歉，上传失败‚请检查图片格式跟大小\",\"msg\":\"\"}");
                        }
                    }
                    else
                    {
                        out.print ("{\"err\":\"抱歉，请选择正确的图片\",\"msg\":\"\"}");
                    }
                }
                else
                {
                    out.print ("{\"err\":\"抱歉，请选择正确的图片\",\"msg\":\"\"}");
                }
            }
        }
        catch (UploadException e)
        {
            System.out.print (e.getMessage ());
            out.print ("{\"err\":\"抱歉，请选择正确的图片路径\",\"msg\":\"\"}");
        }
    }
}
