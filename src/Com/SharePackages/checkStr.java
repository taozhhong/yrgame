package Com.SharePackages;

import Com.SysSetPackages.XmlparamePackages.SingleXml;
import Com.SysSetPackages.XmlparamePackages.Xml_Pro;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class checkStr {
    public static boolean isNull (String s)
    {
        return s == null || s.trim().equals("");
    }

    public static String IsStrNull (String s) {
        if (isNull (s)) {
            return "";
        } else {
            return unkillHtml (s);
        }
    }

    public static boolean isDir (String s) {
        Pattern p = Pattern.compile ("^[a-zA-Z]+[0-9\\.\\-_]*");
        return p.matcher (s).matches ();
    }

    public static boolean checkStr (String s) {
        String[] strl = {"&", "<", ">", "\n", "'", "/", "?", ";", "^", "`", "~", "!", "@", "#", "$", "%", "*", "(", ")", "{", "}", "|", "=", "+"};
        for (int i = 0; i < strl.length; i++) {
            if (s.indexOf (strl[i]) != - 1) {
                return false;
            }
        } return true;
    }

    public static boolean checkStr2 (String s) {
        String[] strl = {"&", "<", ">", "\n", ";", "^", "`", "~", "!"};
        for (int i = 0; i < strl.length; i++) {
            if (s.indexOf (strl[i]) != - 1) {
                return false;
            }
        } return true;
    }

    public static boolean checkId (String s) {
        if ((s == null) || (s.trim ().equals (""))) {
            return false;
        }

        return (s.length () < 15) && (s.length () >= 6) && (checkStr (s));
    }

    public static boolean checkPwd (String s1, String s2) {
        s1 = s1.trim (); s2 = s2.trim ();
        if ((s1 == null) || (s1.equals ("")) || (s2 == null) || (s2.equals ("")) || (! s1.equals (s2))) {
            return false;
        }
        return (s1.length () < 15) && (s1.length () >= 6) && (s1.equals (s2)) && (checkStr (s1));
    }

    public static boolean checkAsk (String s) {
        if ((s == null) || (s.trim ().equals (""))) {
            return false;
        }

        return (s.length () < 30) && (s.length () >= 3) && (checkStr (s));
    }

    public static boolean checkEmail (String s)
    {
        if ((s == null) || (s.trim ().equals ("")))
        {
            return false;
        }
        else
        {
            Pattern p = Pattern.compile ("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
            Matcher m = p.matcher (s);
            boolean b = m.matches ();
            return b;
        }
    }

    public static boolean checkUrl (String s) {
        s = s.toLowerCase ();
        if ((s.length () >= 60) || (s.length () < 5)) {
            return false;
        }
        Pattern p = Pattern.compile ("^[a-zA-Z]+://(\\w+(-\\w+)*)(\\.(\\w+(-\\w+)*))*(\\?\\S*)?$");
        Matcher m = p.matcher (s);
        boolean b = m.matches ();
        return b;
    }

    public static boolean checkTel (String s) {
        if (s.length () >= 40) {
            return false;
        }
        Pattern p = Pattern.compile ("((\\+)?[0-9]{0,4}(\\-)?[0-9]{6,8}(\\-[0-9]{3,5})?(\\,)?)*");
        Matcher m = p.matcher (s);
        boolean b = m.matches ();
        return b;
    }

    public static boolean checkCode (String Code) {
        Pattern p = Pattern.compile ("[0-9]{6}");
        Matcher m = p.matcher (Code);
        boolean b = m.matches ();
        return b;
    }

    public static boolean checkIsNum (String Code) {
        if (isNull (Code)) {
            return false;
        } else {
            try
            {
                Integer.parseInt(Code);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
    }

    public static boolean checkIsLong (String Code) {
        if (isNull (Code)) {
            return false;
        } else {
            try
            {
                Long.parseLong(Code);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
    }

    public static boolean checkIsPoint (String Code)
    {
        if(isNull(Code))
        {
            return false;
        }
        else
        {
            try
            {
                Float.parseFloat (Code);
                return true;
            }
            catch (NumberFormatException e)
            {
                return false;
            }
        }
    }

    public static boolean checkNum (String Code) {
        Pattern p = Pattern.compile ("[0-9]{1,5}");
        Matcher m = p.matcher (Code);
        boolean b = m.matches ();
        return b;
    }

    public static String killHtml (String in)
    {
        if(isNull(in))
        {
            return "";
        }
        else
        {
            StringBuffer out = new StringBuffer ();
            for (int i = 0; (in != null) && (i < in.length ()); i++)
            {
                char c = in.charAt (i);
                if (c == '\'')
                {
                    out.append ("&#039;");
                }
                else if (c == '"')
                {
                    out.append ("&#034;");
                }
                else if (c == '<')
                {
                    out.append ("&lt;");
                }
                else if (c == '>')
                {
                    out.append ("&gt;");
                }
                else if (c == '&')
                {
                    out.append ("&amp;");
                }
                else if (c == ' ')
                {
                    out.append ("&nbsp;");
                }
                else if (c == ',')
                {
                    out.append ("&sbquo;");
                }
                else if (c == '\n')
                {
                    out.append ("<br/>");
                }
                else
                    out.append (c);
            }
            return out.toString ();
        }
    }

    public static String unkillHtml (String s)
    {
        if(!isNull (s))
        {
            s = s.replaceAll ("&lt;", "<");
            s = s.replaceAll ("&gt;", ">");
            s = s.replaceAll ("&#039;", "'");
            s = s.replaceAll ("&#034;", "\"");
            s = s.replaceAll ("&amp;", "&");
            s = s.replaceAll ("&nbsp;", " ");
            s = s.replaceAll ("&sbquo;", ",");
            s = s.replaceAll ("<br/>", "\n");
            return s;
        }
        else
        {
            return "";
        }
    }


    /**
     * 验证是否是手机号格式
     * 该方法还不是很严谨,只是可以简单验证
     *
     * @param mobile 手机号码
     * @return true表示是正确的手机号格式, false表示不是正确的手机号格式
     */
    public static boolean isMobile (String mobile) {
        //当前运营商号段分配
        //中国移动号段 1340-1348 135 136 137 138 139 150 151 152 157 158 159 170 187 188 147
        //中国联通号段 130 131 132 155 156 185 186 145
        //中国电信号段 133 1349 153 180 189
        if (isNull (mobile))
        {
            return false;
        }
        else
        {
            String regular = "1[3,4,5,7,8]{1}\\d{9}";
            Pattern pattern = Pattern.compile (regular);
            boolean flag = false;
            Matcher matcher = pattern.matcher (mobile);
            flag = matcher.matches ();
            return flag;
        }

    }

    public static boolean isDate (String datestr) {
        if (datestr == null || datestr.trim ().equals ("")) {
            return false;
        } else {
            String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
            String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))" + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|" + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?" + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?(" + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?" + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
            if ((datestr != null)) {
                Pattern pattern = Pattern.compile (datePattern1);
                Matcher match = pattern.matcher (datestr);
                if (match.matches ()) {
                    pattern = Pattern.compile (datePattern2);
                    match = pattern.matcher (datestr);
                    return match.matches ();
                } else {
                    return false;
                }
            } return false;
        }
    }

    //替换表情
    public static String ReplaceQh (String str) {
        String r = "\\[em:(\\d+)\\]";
        str = str.replaceAll (r, "<img src=\"/images/sing/bp/$1.gif\"/>");
        return str;
    }

    /**
     * 在javabean下面获取 项目根目录 如G:webspan/web/
     *
     * @return String
     */
    public static String getBeanFilePath () {
        String LogFile = Thread.currentThread ().getContextClassLoader ().getResource ("/").getPath ();
        String LogPath = LogFile.substring (1, LogFile.length ()).substring (0, LogFile.length () - 17);
        return LogPath;
    }


    /**
     * 四舍五入取整
     *
     * @param Str 要进行操作的数据
     * @return BigDecimal
     */
    public static float getMathDecimal (String Str) {
        BigDecimal Decia = new BigDecimal (Str);
        return (Decia.setScale (4, BigDecimal.ROUND_HALF_UP).floatValue ()) * 100;
    }

    /**
     * 判断数据中的值是否跟特定值相同
     *
     * @param ArrayId 数组
     * @param EquVal  想要比较的值
     * @return boolean true：有相同 false:没有相同
     */
    public static boolean IsArrayEquals (String[] ArrayId, String EquVal) {
        boolean bool = false;
        for (String aArrayId : ArrayId)
        {
            if (aArrayId.equals (EquVal))
            {
                bool = true;
                break;
            }
        } return bool;
    }


    /**
     * 判断版块功能,先找查版块相同的标识符，得到标识符，
     * 在以标识符为ArrayId1数组下村，进行查找
     *
     * @param ArrayId  大版块数组 格式如下1，2，3
     * @param ArrayId1 小版块数组 格式如下 1&3&3|1&3|0&3 两者一一对应
     * @param EquVal   大版块ID
     * @param EquVal1  小版块ID
     * @return bool
     */
    public static boolean IsArrayEquals (String[] ArrayId, String[] ArrayId1, String EquVal, String EquVal1) {
        boolean bool = false;
        for (int i = 0; i < ArrayId.length; i++)
        {
            if (ArrayId[i].equals (EquVal))
            {
                for (int ii = 0; ii < ArrayId1[i].split ("&").length; ii++)
                {
                    if (ArrayId1[i].split ("&")[ii].equals (EquVal1))
                    {
                        bool = true
                        ; break;
                    }
                }
                break;
            }
        }
        return bool;
    }


    /**
     * 判断版块操作功能,先找查版块相同的标识符，得到标识符，
     * 在以标识符为ArrayId1数组下村，进行查找
     *
     * @param ArrayId  大版块数组 格式如下 0&1&2
     * @param ArrayId1 小版块数组 格式如下 1&3&3|1&3|0&3 两者一一对应
     * @param ArrayId1 小版块数组 格式如下 1&3&3|1&3|0&3~1&3&3|1&3|0&3~1&3&3|1&3|0&3 两者一一对应
     * @param EquVal   大版块ID
     * @param EquVal1  小版块ID
     * @param EquVal2  操作功能ID
     * @return bool
     */
    public static boolean IsArrayEquals (String[] ArrayId, String[] ArrayId1, String[] ArrayId2, String EquVal, String EquVal1, String EquVal2) {
        boolean bool = false;
        for (int i = 0; i < ArrayId.length; i++)
        {
            if (ArrayId[i].equals (EquVal))
            {
                for (int ii = 0; ii < ArrayId1[i].split ("&").length; ii++)
                {
                    if (ArrayId1[i].split ("&")[ii].equals (EquVal1))
                    {
                        for (int iii = 0; iii < ArrayId2[i].split ("\\|").length; iii++)
                        {
                            for (int iiii = 0; iiii < ArrayId2[i].split ("\\|")[ii].split ("&").length; iiii++)
                            {
                                if (ArrayId2[i].split ("\\|")[ii].split ("&")[iiii].equals (EquVal2))
                                {
                                    bool = true;
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            }
        }
        return bool;
    }

    /**
     * 相加
     * @param d1
     * @param d2
     * @return
     */
    public static double Sum(double d1,double d2)
    {
        BigDecimal db1 = new BigDecimal(Double.toString(d1));
        BigDecimal db2 = new BigDecimal(Double.toString(d2));
        return db1.add(db2).doubleValue();
    }

    /**
     * 提供（相对）精确的减法运算
     * @param d1
     * @param d2
     * @param scale 设置位数
     * @param roundingMode 表示四舍五入，可以选择其它的方式，例如去尾
     * @return 两个参数的差
     */
    public static double Sub(double d1,double d2,int scale,int roundingMode)
    {
        BigDecimal db1 = new BigDecimal(Double.toString(d1));
        BigDecimal db2 = new BigDecimal(Double.toString(d2));
        double mulval=db1.subtract(db2).doubleValue();
        BigDecimal bd=new BigDecimal(mulval);
        bd=bd.setScale(scale,roundingMode);
        return bd.doubleValue();
    }


    /**
     * 提供（相对）精确的乘法运算
     *
     * @param v1
     * @param v2
     * @param scale 设置位数
     * @param roundingMode 表示四舍五入，可以选择其它的方式，例如去尾
     * @return 两个参数的乘积
     */
    public static double Multiply(double v1,double v2,int scale,int roundingMode)
    {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        double mulval= b1.multiply (b2).doubleValue();
        BigDecimal bd=new BigDecimal(mulval);
        bd=bd.setScale(scale,roundingMode);
        return bd.doubleValue();
    }
    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。舍入模式采用用户指定舍入模式
     *
     * @param v1
     * @param v2
     * @param scale      表示需要精确到小数点以后几位
     * @param round_mode 表示用户指定的舍入模式
     * @return 两个参数的商
     */
    public static double Divide (double v1, double v2,
            int scale,
            int round_mode)
    {

        if (scale < 0)
        {
            return 0;
        }
        else
        {
            BigDecimal b1 = new BigDecimal (Double.toString (v1));
            BigDecimal b2 = new BigDecimal (Double.toString (v2));
            return b1.divide (b2, scale, round_mode).doubleValue ();
        }
    }







    /**
     * 获取系统参数的值
     * @param Request request对象,用来获取文件路径
     * @param ParameMark 参数编号
     * @return String
     */
    public static String Parame(HttpServletRequest Request,int ParameMark)
    {
        String ParameStr="";
        Iterator MesageIt= SingleXml.getInstance ().getXml().SearchXml(Request.getSession().
                getServletContext().getRealPath("/")+"Ds_Admin/config/parame.xml",ParameMark).iterator();
        while (MesageIt.hasNext())
        {
            Xml_Pro pro=(Xml_Pro)MesageIt.next();
            if(pro!=null)
                ParameStr=pro.getXmlvalue ();
            else
                ParameStr="";
            MesageIt.remove ();
        }
        return ParameStr;
    }
    /**
     * 返回信息提示语
     * @param Request request对象,用来获取文件路径
     * @param Insrt 插入语参数编号，如果不想用，可为空
     * @param ParameMark 信息提示的参数编号
     * @return String
     */
    public static String Message(HttpServletRequest Request,
                                 String Insrt,
                                 int ParameMark)
    {
        String Message="";
        Iterator MesageIt= SingleXml.getInstance ().getXml().SearchXml(Request.getSession().
                getServletContext().getRealPath("/")+"Ds_Admin/config/message.xml",ParameMark).iterator();
        while (MesageIt.hasNext())
        {
            Xml_Pro pro=(Xml_Pro)MesageIt.next();
            if(pro!=null)
            {
                Message=pro.getXmlvalue ();
            }
            else
            {
                Message="";
            }
            MesageIt.remove ();
        }
        if(isNull (Insrt))//表示用户不需要用插入语
        {
            return Message;
        }
        else
        {
            String InsertStr="";
            Iterator InsertIt= SingleXml.getInstance ().getXml().SearchXml(Request.getSession().
                    getServletContext().getRealPath("/")+"av_store/Ds_Admin/config/insert.xml",Integer.parseInt (Insrt)).iterator();
            while (InsertIt.hasNext())
            {
                Xml_Pro pro=(Xml_Pro)InsertIt.next();
                if(pro!=null)
                {
                    InsertStr=pro.getXmlvalue ();
                }
                else
                {
                    InsertStr="";
                }
                InsertIt.remove ();
            }
            return Message.replaceAll ("\\$",InsertStr);
        }
    }


    /**
     * 处理JSON数据中的特殊字符
     * @param s JSON数据
     * @return 处理过的数据
     */
    public static String String2Json(String s)
    {
        StringBuffer sb = new StringBuffer ();
        if(!checkStr.isNull (s))
        {
            for (int i=0; i<s.length(); i++)
            {
                char c = s.charAt(i);
                switch (c) {
                    case '\"':
                        sb.append("\\\"");
                        break;
                    case '\\':
                        sb.append("\\\\");
                        break;
                    case '/':
                        sb.append("\\/");
                        break;
                    case '\b':
                        sb.append("\\b");
                        break;
                    case '\f':
                        sb.append("\\f");
                        break;
                    case '\n':
                        sb.append("\\n");
                        break;
                    case '\r':
                        sb.append("\\r");
                        break;
                    case '\t':
                        sb.append("\\t");
                        break;
                    default:
                        sb.append(c);
                }
            }
            return sb.toString();
        }
        else
        {
            return "";
        }
    }

    /**
     * 获取IP地址
     * @param request HttpServletRequest对象
     * @return String
     */
    public static String getIp(HttpServletRequest request)
    {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 返回会员等级
     * @param VipStep 贵宾用户标识
     * @param SupStep 超级用户标识
     * @param Account 帐户金额
     * @param request 请求对象
     * @return String
     */
    public static int getMemberStep(int VipStep,int SupStep,float Account,
                                           HttpServletRequest request)
    {
        int StepValue=0;
        if(VipStep==1)
        {
            StepValue=0;
        }
        else
        {
            if(SupStep==1)
            {
                StepValue=1;
            }
            else
            {
                for(int i=0;i<checkStr.Parame(request,16).split("\\|").length;i++)
                {
                    if(Account>=Float.parseFloat(checkStr.Parame(request,16).split("\\|")[i].split("\\-")[0]) &&
                            Account<Float.parseFloat(checkStr.Parame(request,16).split("\\|")[i].split("\\-")[1]))
                    {
                        StepValue=i+checkStr.Parame(request,16).split("\\|").length-1;
                        break;
                    }
                }
            }
        }
        return StepValue;
    }

    /**
     * 判断输入的域名是否正确
     * @param RemoteName 域名
     * @return true 正确 false 错误
     */
    public static boolean CheckDomain(String RemoteName)
    {
        return !isNull(RemoteName) && RemoteName.contains(".");
    }


    /**
     * 返回拍卖加价数组的下标
     * @param request 请求参数
     * @param nowprice 当前拍卖价格
     * @return 数据下标
     */
    public static int CheckAuction(HttpServletRequest request,int nowprice)
    {
        int arr=0;
        String AuctionStr=checkStr.Parame(request,28);
        for(int i=0;i<AuctionStr.split("\\|").length;i++)
        {
            if(AuctionStr.split("\\|")[i].split("-")[0].contains("~"))
            {
                if(Math.max(Integer.parseInt(AuctionStr.split("\\|")[i].split("-")[0].split("~")[0]), nowprice)
                        == Math.min(nowprice, Integer.parseInt(AuctionStr.split("\\|")[i].split("-")[0].split("~")[1])))
                {
                    arr=i;
                    break;
                }
            }
            else
            {
                if(Math.max(Integer.parseInt(AuctionStr.split("\\|")[i].split("-")[0]), nowprice)
                        == Math.min(nowprice, nowprice+10))
                {
                    arr=i;
                    break;
                }
            }
        }
        return arr;
    }

    /**
     * 格式化价格 把大于10000以上的数据格式化为1万
     * @param price 当前格式
     * @return String
     */
    public static String FormatPrice(float price)
    {
        if(price>10000)
            return Divide(price,10000,2,4)+"万以上";
        else
            return price+"元以上";
    }



    /**
     * 返回买域名保证金
     * @param request 请求参数
     * @param domainprice 域名价格
     * @return 数据下标
     */
    public static String CheckBuDomain(HttpServletRequest request,float domainprice)
    {
        int arr=0;
        String DomainPriceStr=checkStr.Parame(request,39);
        for(int i=0;i<DomainPriceStr.split("\\|").length;i++)
        {
            if(DomainPriceStr.split("\\|")[i].split("-")[0].contains("~"))
            {
                if(Math.max(Integer.parseInt(DomainPriceStr.split("\\|")[i].split("-")[0].split("~")[0]), domainprice)
                        == Math.min(domainprice, Integer.parseInt(DomainPriceStr.split("\\|")[i].split("-")[0].split("~")[1])))
                {
                    arr=i;
                    break;
                }
            }
            else
            {
                if(Math.max(Integer.parseInt(DomainPriceStr.split("\\|")[i].split("-")[0]), domainprice)
                        == Math.min(domainprice, domainprice+10))
                {
                    arr=i;
                    break;
                }
            }
        }
        return DomainPriceStr.split("\\|")[arr].split("-")[1];
    }
}