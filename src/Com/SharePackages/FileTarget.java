package Com.SharePackages;


import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileTarget {
    public boolean IsFileExists(String paramString);

    public boolean FileDel(String paramString);

    public boolean MakeDir(String paramString);

    public boolean FolderIsNull(String FolderPath);

    public Map FolderList(String path, String searchkey);

    public void WriteFile(File paramFile, String paramString, boolean paramBoolean);

    public boolean CreateFile(String filepath, String filename, String filecontent, boolean wrbool);

    public boolean copyFolder(String paramString1, String paramString2);

    public String ReadFile(String paramString);

    public String ReadFile(String paramString, String CharCode);

    public boolean UserPrivewFile(String filepath, String filename, String filecontent, boolean wrbool);

    public boolean CreateFile_UTF(String filepath, String filename, String filecontent, boolean wrbool);

    public String FileSize(String filepath);

    /**
     * 根据尺寸产生缩略图
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param ThumbPath       缩放后图片的路径,不带图片名称
     * @param ThumbImgName    缩略图名称
     * @param Width           缩放后的图片宽度
     * @param Height          缩放后的图片高度
     * @return String 返回缩放后的图片名称
     */
    public String CreateThumb(String OriginalPath, String OriginalImgName, String ThumbPath, String ThumbImgName, int
            Width, int Height);


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
    public String CreateThumb(String OriginalPath, String OriginalImgName, String ThumbPath, String ThumbImgName,
                              String SuffixStr, int Width, int Height);


    /**
     * 根据宽度缩放图片,且保存并覆盖原图
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param Width           缩放后的图片宽度
     * @return String 返回缩放后的图片名称
     */
    public String CreateThumb(String OriginalPath, String OriginalImgName, int Width);

    /**
     * 给图片加文字水印
     *
     * @param StrPath  源图片路径
     * @param TextCont 文件字内
     */
    public void WaterMarkText(String StrPath, String TextCont);


    public void WaterMarkImg(String SrcImagePath, String SrcImageName, String LogoImagePath,
                             String DestImagePath, String gravity, int dissolve);

    /**
     * 文件重命名
     * @param OldFileName 想要命名的文件名(路径+文件名)
     * @param NewFileName 新的文件名(路径+文件名)
     * @return boolean true:成功 false 失败
     */
    public boolean FileReName(String OldFileName, String NewFileName);
}
