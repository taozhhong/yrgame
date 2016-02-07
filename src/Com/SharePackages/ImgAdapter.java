package Com.SharePackages;


import oracle.sql.CHAR;
import org.im4java.core.CompositeCmd;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-9
 * Time: 10:45:56
 */
public class ImgAdapter {
    private String GraphicsPath = "D:\\Program Files\\GraphicsMagick-1.3.19-Q8";

    /**
     * 检测文件目录是否存在
     *
     * @param filepath:文件路径
     * @return true:文件目录存在;false:不存在
     */
    public boolean IsFileExists(String filepath)
    {
        File file = new File(filepath);
        return file.exists();
    }


    /**
     * 创建目录
     *
     * @param filepath 文件路径
     * @return true:创建目录成功;false失败;
     */
    public boolean MakeDir(String filepath)
    {
        if (!IsFileExists(filepath))
        {
            File file = new File (filepath);
            return file.mkdirs ();
        } else {
            return true;
        }
    }


    /**
     * 判断目录是否为空
     *
     * @param folderpath 目录路径
     * @return boolean true:不为空 false:为空
     */
    public boolean FolderIsNull (String folderpath) {
        if (MakeDir (folderpath)) {
            File[] files = new File (folderpath).listFiles ();
            return files.length > 0 ? true : false;
        } else {
            return false;
        }
    }

    /**
     * 目录或目录图片列表
     *
     * @param path      路径
     * @param searchkey 文件名关键字
     * @return Map
     */
    public Map<String, Serializable> FolderList (String path, String searchkey) {
        Map<String, Serializable> filemap = new HashMap<String, Serializable> ();
        if (IsFileExists (path))//判断路径是否存在
        {
            File[] files = null; if (checkStr.isNull (searchkey)) {
            files = new File (path).listFiles ();
        } else {
            files = new File (path).listFiles (new SearchFileFilter (searchkey));
        } assert files != null; if (files.length > 0) {
            filemap.put ("filename", files);
        } else {
            filemap.put ("filename", null);
        }
        } else {
            filemap.put ("filename", null);
        } return filemap;
    }

    public void copyFile (String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File (oldPath);
            if (oldfile.exists ()) {
                //文件存在时
                InputStream inStream = new FileInputStream (oldPath);   //读入原文件
                FileOutputStream fs = new FileOutputStream (newPath);
                byte[] buffer = new byte[14444 * 5]; int length;
                while ((byteread = inStream.read (buffer)) != - 1) {
                    bytesum += byteread;//字节数 文件大小
                    //System.out.println(bytesum);
                    fs.write (buffer, 0, byteread);
                } inStream.close ();
            }
        }
        catch (Exception e) {
            System.out.println ("复制单个文件操作出错" + e.getMessage ()); e.printStackTrace ();
        }

    }


    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public boolean CopyFolder (String oldPath, String newPath) {
        try {
            MakeDir (newPath);//如果文件夹不存在,则建立新文件夹
            File a = new File (oldPath); String[] file = a.list ();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith (File.separator)) {
                    temp = new File (oldPath + file[i]);
                } else {
                    temp = new File (oldPath + File.separator + file[i]);
                }

                if (temp.isFile ()) {
                    FileInputStream fileinputstream = new FileInputStream (temp);//读取文件
                    InputStreamReader inputStramReader = new InputStreamReader (fileinputstream, "UTF-8");
                    BufferedReader bufferedReader = new BufferedReader (inputStramReader);

                    FileOutputStream fos = new FileOutputStream (newPath + "/" + (temp.getName ()).toString ());
                    OutputStreamWriter out = new OutputStreamWriter (fos, "UTF-8");
                    BufferedWriter bufferout = new BufferedWriter (out); String line = null;
                    while ((line = bufferedReader.readLine ()) != null) {
                        bufferout.write (line + "\n");
                    }

                    bufferout.flush (); bufferout.close (); fos.close ();

//                    bufferedReader.close();
//                    inputStramReader.close();
//                    fileinputstream.close();

                }
            }

            return true;

        }
        catch (Exception e) {
            System.out.println ("复制整个文件夹内容操作出错"); e.printStackTrace (); return false;
        }
    }

    /**
     * 删除图片的方法
     *
     * @param ImgPath 图片的路径
     * @return boolean true:删除成功,false:删除失败
     */
    public boolean ImgDel (String ImgPath) {
        if (IsFileExists (ImgPath))//判断文件是否存在
        {
            File file = new File(ImgPath);
            if (file.delete())
                return true;
            else
                return false;
        }
        else
            return false;
    }


    /**
     * 对文件进行写操作
     *
     * @param file:file对象
     * @param filecontent:写入文件的内容
     * @param bool:true:追回写入,false:覆盖
     * @return:void
     */
    public void WriteFile (File file, String filecontent, boolean bool) {
        try
        {
            FileWriter out = new FileWriter (file, bool);
            BufferedWriter bufferout = new BufferedWriter (out);
            bufferout.write (filecontent + "\r\n");
            bufferout.flush ();
            bufferout.close ();
            out.close ();
        }
        catch (IOException e) {
            System.out.print ("内部文件写入出错" + e.getMessage ());
        }
    }


    /**
     * 判断文件是否存在,接着对文件进行写入
     *
     * @param filepath:文件路径
     * @param filename:文件名
     * @param filecontent:要入文件的内容
     * @param wrbool:true:追加写入,false:覆盖
     * @return boolean 文件是否写入成功
     */
    public boolean CreateFile (String filepath, String filename, String filecontent, boolean wrbool)
    {
        boolean bool;
        try {
            File file = new File (filepath, filename);
            if (file.exists ())//存在
            {
                WriteFile (file, filecontent, wrbool);
                bool = true;
            }
            else
            {
                if (file.createNewFile ())
                {
                    WriteFile (file, filecontent, wrbool);
                    bool = true;
                }
                else
                {
                    bool = false;
                }
            }
        }
        catch (IOException e)
        {
            bool = false;
            System.out.print ("外部文件写入出错" + e.getMessage ());
        }
        return bool;
    }


    /**
     * 对文件进行写操作,以UTF-8的编码写入
     *
     * @param file:file对象
     * @param filecontent:写入文件的内容
     * @param bool:true:追回写入,false:覆盖
     * @return:void
     */
    public void WriteFile_UTF (File file, String filecontent, boolean bool) {
        try {
            FileOutputStream fos = new FileOutputStream (file, bool);
            OutputStreamWriter out = new OutputStreamWriter (fos, "UTF-8");
//            FileWriter out=new FileWriter(file,bool);
            BufferedWriter bufferout = new BufferedWriter (out);
            bufferout.write (filecontent);
            bufferout.flush ();
            bufferout.close ();
            fos.close ();

        }
        catch (IOException e) {

            System.out.print ("UTF内部文件写入出错" + e.getMessage ());
        }
    }

    /**
     * 对文件进行读操作
     *
     * @param file 对象
     * @param CharStr 编码方式
     */
    public String ReadFile (File file,String CharStr)
    {
        StringBuffer sb = new StringBuffer ("");
        //String context="";
        try {
            FileInputStream fos = new FileInputStream (file);
            InputStreamReader in =null;
            if(checkStr.isNull(CharStr))
                in = new InputStreamReader (fos);
            else
                in = new InputStreamReader (fos,CharStr);
            BufferedReader bufferin = new BufferedReader (in);
            String str = "";
            while ((str = bufferin.readLine ()) != null)
            {
                //读取一行文字数据
                //context += str;
                sb.append (str).append("\r\n");
            }
        }
        catch (IOException e)
        {
            System.out.print ("UTF内部文件读取出错" + e.getMessage ());
        }
        return sb.toString ();
    }

    /**
     * 对文件进行读操作
     *
     * @param file 对象
     */
    public String ReadFile (File file)
    {
        StringBuffer sb = new StringBuffer ("");
        //String context="";
        try {
            FileInputStream fos = new FileInputStream (file);
            InputStreamReader in = new InputStreamReader (fos);
            BufferedReader bufferin = new BufferedReader (in);
            String str = "";
            while ((str = bufferin.readLine ()) != null)
            {
                //读取一行文字数据
                //context += str;
                sb.append (str).append("\r\n");
            }
        }
        catch (IOException e)
        {
            System.out.print ("UTF内部文件读取出错" + e.getMessage ());
        }
        return sb.toString ();
    }

    /**
     * 读取文件内容
     *
     * @param filepath 文件路径
     * @return String 文件内容
     */
    public String Is_ReadFile (String filepath)
    {
        String Str = "";
        File file = new File (filepath);
        if (file.exists ())
        {
            Str = ReadFile (file);
        }
        else
        {
            Str = "";
        }
        return Str;
    }

    /**
     * 读取文件内容
     *
     * @param filepath 文件路径
     * @param charcode 编码方式
     * @return String 文件内容
     */
    public String Is_ReadFile (String filepath,String charcode)
    {
        if(!checkStr.isNull(getCharSet(filepath)))
            charcode=getCharSet(filepath);
        String Str = "";
        File file = new File (filepath);
        if (file.exists ())
        {
            Str = ReadFile (file,charcode);
        }
        else
        {
            Str = "";
        }
        return Str;
    }

    /**
     * 获取txt文件编码
     * @param fileName 文件全路径
     * @return 编码方式
     */
    private String getCharSet(String fileName)
    {
        String charset = "GBK";
        byte[] first3Bytes = new byte[3];
        try {
            boolean checked = false;
            File file=new File(fileName);
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            bis.mark(0);
            int read = bis.read(first3Bytes, 0, 3);
            if (read == -1) {
                return charset; //文件编码为 ANSI
            } else if (first3Bytes[0] == (byte) 0xFF
                    && first3Bytes[1] == (byte) 0xFE) {
                charset = "UTF-16LE"; //文件编码为 Unicode
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xFE
                    && first3Bytes[1] == (byte) 0xFF) {
                charset = "UTF-16BE"; //文件编码为 Unicode big endian
                checked = true;
            } else if (first3Bytes[0] == (byte) 0xEF
                    && first3Bytes[1] == (byte) 0xBB
                    && first3Bytes[2] == (byte) 0xBF) {
                charset = "UTF-8"; //文件编码为 UTF-8
                checked = true;
            }
            bis.reset();
            if (!checked) {
                int loc = 0;
                while ((read = bis.read()) != -1) {
                    loc++;
                    if (read >= 0xF0)
                        break;
                    if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
                        break;
                    if (0xC0 <= read && read <= 0xDF) {
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) // 双字节 (0xC0 - 0xDF)
                            // (0x80
                            // - 0xBF),也可能在GB编码内
                            continue;
                        else
                            break;
                    } else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
                        read = bis.read();
                        if (0x80 <= read && read <= 0xBF) {
                            read = bis.read();
                            if (0x80 <= read && read <= 0xBF) {
                                charset = "UTF-8";
                                break;
                            } else
                                break;
                        } else
                            break;
                    }
                }
            }
            bis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return charset;
    }

    /**
     * 判断文件是否存在,接着对文件进行写入
     *
     * @param filepath:文件路径
     * @param filename:文件名
     * @param filecontent:要入文件的内容
     * @param wrbool:true:追回写入,false:覆盖
     * @return boolean 文件是否写入成功
     */
    public boolean CreateFile_UTF (String filepath, String filename, String filecontent, boolean wrbool) {
        boolean bool; try {
            File file = new File (filepath, filename);
            if (file.exists ())//存在
            {
                WriteFile_UTF (file, filecontent, wrbool);
                bool = true;
            } else {
                if (file.createNewFile ()) {
                    WriteFile_UTF (file, filecontent, wrbool);
                    bool = true;
                } else {
                    bool = false;
                }
            }
        }
        catch (IOException e) {
            bool = false; System.out.print ("UTF外部文件写入出错" + e.getMessage ());
        }

        return bool;
    }

    public boolean UserWriter (String filepath, String filename, String filecontent,
                               boolean wrbool) {
        boolean bool; try {
            File file = new File (filepath, filename);
            if (file.exists ())//存在
            {
                if (file.length () > 3000000) {
                    String FileName = file.getName (); int ii = FileName.indexOf (".");
                    String qExt = FileName.substring (0, ii) + "_1";//取文件名的前面部分
                    String sExt = FileName.substring (ii + 1, FileName.length ());//取文件名的后缀
                    copyFile (filepath + FileName, filepath + qExt + "." + sExt);//复制一个文件
                    WriteFile (file, filecontent + "\r\n<div style='width:100%;height:30px;line-height:30px;font-size:12px;border-bottom:solid 1px #000000;text-align:center;'><a href='" + qExt + "." + sExt + "' target='_blank'>下一页</a></div>", false);
                    bool = true;
                } else {
                    String OldStr = Is_ReadFile (filepath + filename);
                    WriteFile (file, filecontent + OldStr, wrbool);
                    bool = true;
                }
            } else {
                if (file.createNewFile ()) {
                    WriteFile (file, filecontent, wrbool); bool = true;
                } else {
                    bool = false;
                }
            }
        }
        catch (IOException e) {
            bool = false; System.out.print ("UTF外部文件写入出错" + e.getMessage ());
        } return bool;
    }


    /**
     * 获取文件大小
     *
     * @param FilePath 文件路径
     * @return long 字节
     */
    public String getFileSize (String FilePath) {
        DecimalFormat df = new DecimalFormat ("#.00");
        String filesize = "";
        try {
            File file = new File (FilePath); if (file.exists ())//文件存在
            {
                FileInputStream fis = new FileInputStream (file);
                filesize = df.format ((double) fis.available () / 1048576);
            } else {
                System.out.println ("getFileSize");
            }
        }
        catch (IOException e) {
            System.out.print ("getFileSize" + e.getMessage ());
        } return filesize;
    }

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
    public String CreateThumb (String OriginalPath, String OriginalImgName,
            String ThumbPath, String ThumbImgName,
            int Width, int Height)
    {
        String ThumbImg = "";
        try
        {
            if (MakeDir (ThumbPath))
            {
                if (IsFileExists (OriginalPath + OriginalImgName))
                {
                    File _file = new File (OriginalPath + OriginalImgName);////读入大图文件
                    Image src = javax.imageio.ImageIO.read (_file); // construct image
                    int imageWideth = src.getWidth (null); // get wideth of image
                    int imageHeight = src.getHeight (null);// get height of image
                    int hh = (int) (imageHeight * Height / imageWideth);//根据宽度算出高度
                    if (imageWideth >= Width)
                    {
                        IMOperation op = new IMOperation ();
                        op.addImage (OriginalPath + OriginalImgName);
                        op.resize (Width, hh);
                        op.addImage (ThumbPath + ThumbImgName);
                        //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
                        ConvertCmd convert = new ConvertCmd (true);
                        convert.setSearchPath (GraphicsPath);
                        convert.run (op);
                        ThumbImg = CutImg (ThumbPath, ThumbImgName, Width, Height);//进行裁剪;
                    }
                    else
                    {
                        if (hh > Height)
                        {
                            copyFile (OriginalPath + OriginalImgName, ThumbPath + ThumbImgName);
                            ThumbImg = CutImg (ThumbPath, ThumbImgName, Width, Height);//进行裁剪;
                        }
                        else
                        {
                            copyFile (OriginalPath + OriginalImgName, ThumbPath + ThumbImgName);
                            ThumbImg = OriginalImgName;
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.print ("W_H_CreateThumb:" + e.getMessage ());
        }
        catch (InterruptedException e)
        {
            System.out.print ("W_H_CreateThumb:" + e.getMessage ());
        }
        catch (IM4JavaException e)
        {
            System.out.print ("W_H_CreateThumb:" + e.getMessage ());
        }
        return ThumbImg;
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
    public String CreateThumb (String OriginalPath, String OriginalImgName,
            String ThumbPath, String ThumbImgName,
            String SuffixStr, int Width, int Height)
    {
        String ThumbImg = ""; int ii = ThumbImgName.indexOf (".");
        String qExt = ThumbImgName.substring (0, ii) + SuffixStr;//取文件名的前面部分
        String sExt = "jpg";//文件类型
        try
        {
            if (MakeDir (ThumbPath))
            {
                if (IsFileExists (OriginalPath + OriginalImgName))
                {
                    File _file = new File (OriginalPath + OriginalImgName);////读入大图文件
                    Image src = javax.imageio.ImageIO.read (_file); // construct image
                    int imageWideth = src.getWidth (null); // get wideth of image
                    int imageHeight = src.getHeight (null);// get height of image
                    int hh = (int) (imageHeight * Height / imageWideth);//根据宽度算出高度
                    if (imageWideth > Width)
                    {
                        IMOperation op = new IMOperation (); op.addImage (OriginalPath + OriginalImgName);
                        op.resize (Width, hh); op.addImage (ThumbPath + qExt + "." + sExt);
                        //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
                        ConvertCmd convert = new ConvertCmd (true);
                        convert.setSearchPath (GraphicsPath);
                        convert.run (op);
                        ThumbImg = CutImg (OriginalPath, qExt + "." + sExt, Width, Height);//进行裁剪;
                    }
                    else
                    {
                        if (hh > Height)
                        {
                            ThumbImg = CutImg (OriginalPath, qExt + "." + sExt, Width, Height);//进行裁剪;
                        }
                        else
                        {
                            ThumbImg = qExt + "." + sExt;
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            System.out.print ("Suffix_CreateThumb:" + e.getMessage ());
        }
        catch (InterruptedException e)
        {
            System.out.print ("Suffix_CreateThumb:" + e.getMessage ());
        }
        catch (IM4JavaException e)
        {
            System.out.print ("Suffix_CreateThumb:" + e.getMessage ());
        }
        return ThumbImg;
    }

    /**
     * 根据宽度缩放图片,且保存并覆盖原图
     *
     * @param OriginalPath    源图片路径,不带图片名称
     * @param OriginalImgName 原图片名称
     * @param Width           缩放后的图片宽度
     * @return String 返回缩放后的图片名称
     */
    public String CreateThumb (String OriginalPath, String OriginalImgName,
            int Width)
    {
        String ThumbImg = OriginalImgName;
        try
        {
            if (IsFileExists (OriginalPath + OriginalImgName))
            {
                File _file = new File (OriginalPath + OriginalImgName);////读入大图文件
                Image src = javax.imageio.ImageIO.read (_file); // construct image
                int imageWideth = src.getWidth (null); // get wideth of image
                if (imageWideth > Width)
                {
                    IMOperation op = new IMOperation ();
                    op.addImage (OriginalPath + OriginalImgName);
                    op.resize (Width, null);
                    op.addImage (OriginalPath + OriginalImgName);
                    //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
                    ConvertCmd convert = new ConvertCmd (true);
                    convert.setSearchPath (GraphicsPath);
                    convert.run (op);
                }
            }
        }
        catch (IOException e)
        {
            System.out.print ("Width_CreateThumb:" + e.getMessage ());
        }
        catch (InterruptedException e)
        {
            System.out.print ("Width_CreateThumb:" + e.getMessage ());
        }
        catch (IM4JavaException e)
        {
            System.out.print ("Width_CreateThumb:" + e.getMessage ());
        }
        return ThumbImg;
    }

    /**
     * 从坐标0,0开始裁切图片,且保存并覆盖原图
     *
     * @param OriginalPath    原图的图片路径,不包括图片文件名
     * @param OriginalImgName 原图名称
     * @param ThumbWidth      缩略图的宽
     * @param ThumbHeight     缩略图的高
     * @return String(缩略图的名称)
     */
    public String CutImg (String OriginalPath, String OriginalImgName, int ThumbWidth, int ThumbHeight) {
        String OutImgName = OriginalImgName;
        try {
            IMOperation op = new IMOperation (); op.addImage (OriginalPath + OriginalImgName);
            /**
             * width：裁剪的宽度
             * height：裁剪的高度
             * 0：裁剪的横坐标
             * 0：裁剪的竖坐标
             */
            op.crop (ThumbWidth, ThumbHeight, 0, 0);
            op.addImage (OriginalPath + OriginalImgName);
            //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
            ConvertCmd convert = new ConvertCmd (true);
            convert.setSearchPath (GraphicsPath);
            convert.run (op);
        }
        catch (IOException e) {
            System.out.print ("origin_CutImg:" + e.getMessage ());
        }
        catch (InterruptedException e) {
            System.out.print ("origin_CutImg:" + e.getMessage ());
        }
        catch (IM4JavaException e) {
            System.out.print ("origin_CutImg:" + e.getMessage ());
        }
        return OutImgName;
    }

    /**
     * 从坐标X,Y开始裁切图片,且保存并覆盖原图
     *
     * @param OriginalPath    原图的图片路径,不包括图片文件名
     * @param OriginalImgName 原图名称
     * @param ThumbWidth      缩略图的宽
     * @param ThumbHeight     缩略图的高
     * @return String(缩略图的名称)
     */
    public String CutImg (String OriginalPath, String OriginalImgName,
                          int ThumbWidth, int ThumbHeight, int X, int Y) {
        String OutImgName = OriginalImgName;
        try {
            IMOperation op = new IMOperation ();
            op.addImage (OriginalPath + OriginalImgName);
            /**
             * width：裁剪的宽度
             * height：裁剪的高度
             * X：裁剪的横坐标
             * Y：裁剪的竖坐标
             */
            op.crop (ThumbWidth, ThumbHeight, X, Y);
            op.addImage (OriginalPath + OriginalImgName);
            //IM4JAVA是同时支持ImageMagick和GraphicsMagick的，如果为true则使用GM，如果为false支持IM。
            ConvertCmd convert = new ConvertCmd (true);
            convert.setSearchPath (GraphicsPath);
            convert.run (op);
        }
        catch (IOException e) {
            System.out.print ("appoint_CutImg:" + e.getMessage ());
        }
        catch (InterruptedException e) {
            System.out.print ("appoint_CutImg:" + e.getMessage ());
        }
        catch (IM4JavaException e) {
            System.out.print ("appoint_CutImg:" + e.getMessage ());
        } return OutImgName;
    }


    /**
     * 给图片加文字水印
     *
     * @param StrPath  源图片路径
     * @param TextCont 文件字内
     */
    public void WaterMarkText (String StrPath, String TextCont) {

        IMOperation op = new IMOperation ();
        op.font ("微软雅黑");
        op.gravity ("southeast");
        op.pointsize (18).fill ("#BCBFC8").draw ("text 0,0 " + TextCont);//("x1 x2 x3 x4") x1 格式，x2 x轴距离 x3 y轴距离  x4名称
        op.addImage ();
        op.addImage ();
        ConvertCmd convert = new ConvertCmd (true);
        convert.setSearchPath (GraphicsPath);
        try {
            convert.run (op, StrPath, StrPath);
        }
        catch (Exception e) {
            System.out.print ("WaterMarkText:" + e.getMessage ());
        }
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
    public void WaterMarkImg (String SrcImagePath, String SrcImageName,
                              String LogoImagePath, String DestImagePath,
                              String gravity, int dissolve)
    {
        IMOperation op = new IMOperation ();
        op.gravity (gravity);
        op.dissolve (dissolve);
        op.addImage (SrcImagePath + SrcImageName);
        op.addImage (LogoImagePath);
        op.addImage (DestImagePath + SrcImageName);
        CompositeCmd convert = new CompositeCmd (true);
        convert.setSearchPath (GraphicsPath);
        try
        {
            convert.run (op);
        }
        catch (IOException e)
        {
            System.out.print ("WaterMarkImg:" + e.getMessage ());
        }
        catch (InterruptedException e)
        {
            System.out.print ("WaterMarkImg:" + e.getMessage ());
        }
        catch (IM4JavaException e)
        {
            System.out.print ("WaterMarkImg:" + e.getMessage ());
        }
    }


    /**
     * 文件重命名
     * @param OldFileName 想要命名的文件名(路径+文件名)
     * @param NewFileName 新的文件名(路径+文件名)
     * @return boolean true:成功 false 失败
     */
    public boolean FileReName(String OldFileName,String NewFileName)
    {
        File ff = new File(OldFileName);
        if(ff.renameTo(new File(NewFileName)))
            return true;
        else
            return false;
    }
}
