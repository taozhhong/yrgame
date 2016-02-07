package Com.SharePackages;


import java.io.File;
import java.util.Map;


public class AdapterFile implements FileTarget {
    private ImgAdapter img;

    public AdapterFile (ImgAdapter img) {
        this.img = img;
    }


    /**
     * 检测文件目录是否存在,此方法ImgAdapte有的.
     *
     * @param filepath:文件路径
     * @return true:文件目录存在;false:不存在
     */
    public boolean IsFileExists (String filepath) {
        return img.IsFileExists (filepath);
    }


    /**
     * 删除文件，此方法ImgAdapte有
     *
     * @param filepath 文件路径（带文件名）
     * @return true:删除成功;false:删除失败
     */
    public boolean FileDel (String filepath) {
        if (IsFileExists (filepath))
            return img.ImgDel (filepath);
        else
            return false;
    }

    /**
     * 创建目录,此方法ImgAdapte有的.
     *
     * @param filepath 文件路径
     * @return true:创建目录成功;false失败;
     */
    public boolean MakeDir (String filepath) {
        return img.MakeDir (filepath);
    }

    /**
     * 判断目录是否为空,此方法ImgAdapte有的.
     *
     * @param FolderPath 目录路径
     * @return true:有文件;false:没文件;
     */
    public boolean FolderIsNull (String FolderPath) {
        return img.FolderIsNull (FolderPath);
    }

    /**
     * 返回文件或者目录列表
     *
     * @param path 路径
     * @return map
     */
    public Map FolderList (String path, String searchkey) {
        return img.FolderList (path, searchkey);
    }


    /**
     * 对文件进行写操作,此方法ImgAdapte有的.
     *
     * @param file:file对象
     * @param filecontent:写入文件的内容
     * @param bool:true:追回写入,false:覆盖
     * @return:void
     */
    public void WriteFile (File file, String filecontent, boolean bool) {
        img.WriteFile (file, filecontent, bool);
    }


    /**
     * 判断文件是否存在,接着对文件进行写入,此方法ImgAdapte有的.
     *
     * @param filepath:文件路径
     * @param filename:文件名
     * @param filecontent:要入文件的内容
     * @param wrbool:true:追回写入,false:覆盖
     * @return boolean 文件是否写入成功
     */
    public boolean CreateFile (String filepath, String filename, String filecontent, boolean wrbool) {
        return img.CreateFile (filepath, filename, filecontent, wrbool);
    }


    /**
     * 复制整个文件夹内容,此方法是ImgAdapte没有的
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public boolean copyFolder (String oldPath, String newPath) {
        return img.CopyFolder (oldPath, newPath);

    }

    /**
     * 读取文件内容
     *
     * @param FilePath 文件路径
     * @return String 文件内容
     */
    public String ReadFile (String FilePath) {
        return img.Is_ReadFile (FilePath);
    }

    /**
     * 读取文件内容
     *
     * @param FilePath 文件路径
     * @param CharCode 编码方式
     * @return String 文件内容
     */
    public String ReadFile (String FilePath,String CharCode) {
        return img.Is_ReadFile (FilePath,CharCode);
    }


    public boolean UserPrivewFile (String filepath, String filename, String filecontent, boolean wrbool) {
        return img.UserWriter (filepath, filename, filecontent, wrbool);
    }

    public boolean CreateFile_UTF (String filepath, String filename, String filecontent, boolean wrbool) {
        return img.CreateFile_UTF (filepath, filename, filecontent, wrbool);
    }


    /**
     * 获取文件大小
     *
     * @param filepath 文件路径
     * @return long 字节
     */
    public String FileSize (String filepath) {
        return img.getFileSize (filepath);
    }


    /**
     * 根据尺寸产生缩略图
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param ThumbPath       缩放后图片的路径,不带图片名称
     * @param ThumbImgName    缩略图名称
     * @param width           缩放后的图片宽度
     * @param height          缩放后的图片高度
     * @return String 返回缩放后的图片名称
     */
    public String CreateThumb (String OriginalPath, String OriginalImgName, String ThumbPath, String ThumbImgName, int width, int height) {
        return img.CreateThumb (OriginalPath, OriginalImgName, ThumbPath, ThumbImgName, width, height);
    }

    /**
     * 根据尺寸产生缩略图,并且带上指定后缀,并把图片类型改为jpg
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param ThumbPath       缩放后图片的路径,不带图片名称
     * @param ThumbImgName    缩略图名称
     * @param SuffixStr       图片后缀
     * @param Width           缩放后的图片宽度
     * @param Height          缩放后的图片高度
     * @return String 返回缩放后的图片名称
     */
    @Override
    public String CreateThumb (String OriginalPath, String OriginalImgName, String ThumbPath, String ThumbImgName, String SuffixStr, int Width, int Height) {
        return img.CreateThumb (OriginalPath, OriginalImgName, ThumbPath, ThumbImgName, SuffixStr, Width, Height);
    }

    /**
     * 根据宽度缩放图片,且保存并覆盖原图
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param Width           缩放后的图片宽度
     * @return String 返回缩放后的图片名称
     */
    @Override
    public String CreateThumb (String OriginalPath, String OriginalImgName, int Width) {
        return img.CreateThumb (OriginalPath, OriginalImgName, Width);
    }

    /**
     * 给图片加文字水印
     *
     * @param StrPath  源图片路径
     * @param TextCont 文件字内
     */
    @Override
    public void WaterMarkText (String StrPath, String TextCont) {
        img.WaterMarkText (StrPath, TextCont);
    }

    /**
     * 图片水印
     *
     * @param SrcImagePath  源图片路径
     * @param SrcImageName  源图片名称
     * @param LogoImagePath 水印图片
     * @param DestImagePath 生成图片路径
     * @param gravity       图片位置
     * @param dissolve      水印透明度
     */
    @Override
    public void WaterMarkImg (String SrcImagePath, String SrcImageName, String LogoImagePath, String DestImagePath, String gravity, int dissolve) {
        img.WaterMarkImg (SrcImagePath, SrcImageName, LogoImagePath, DestImagePath, gravity, dissolve);
    }

    /**
     * 文件重命名
     *
     * @param OldFileName 想要命名的文件名(路径+文件名)
     * @param NewFileName 新的文件名(路径+文件名)
     * @return boolean true:成功 false 失败
     */
    @Override
    public boolean FileReName(String OldFileName, String NewFileName)
    {
        return img.FileReName(OldFileName,NewFileName);
    }


}