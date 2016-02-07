package Com.SharePackages;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

public class Str_code_switch {
    public String to_gb2312 (String s) {
        return disposal (s, "gb2312");
    }

    public String to_utf_8 (String s) {
        return disposal (s, "UTF-8");
    }

    private String disposal (String str, String code) {
        if (null == str) {
            return "";
        } try {
            str = new String (str.trim ().getBytes ("ISO-8859-1"), code);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace ();
        }

        return str;
    }

    public String ex_encode_utf8 (String str) {
        if (checkStr.isNull (str)) {
            str = "";
        } else {
            try {
                str = URLEncoder.encode (str, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                System.out.print ("encode:" + e.getMessage ());
            }
        } return str;
    }

    public String ex_noencode_utf8 (String str) {
        if (checkStr.isNull (str)) {
            str = "";
        } else {
            try {
                str = new String (str.trim ().getBytes ("ISO-8859-1"), "UTF-8");
            }
            catch (UnsupportedEncodingException ex) {
                System.out.print ("noencode:" + ex.getMessage ());
            }
        } return str;
    }

    public static String encipher (String source)
    {
        if(!checkStr.isNull (source))
        {
            StringBuilder value = new StringBuilder (50);
            try
            {
                byte[] strByte = source.getBytes ("utf-8");
                for (byte aStrByte : strByte)
                {
                    value.append (aStrByte).append ("%");
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage ());
            }
            return value.toString ();
        }
        else
        {
            return "";
        }
    }

    public static String decrypt (String ciphertext) {
        String sub = "";
        int count = 0;
        for (int i = 0; i < ciphertext.length (); i++)
            if (ciphertext.charAt (i) == '%') count++;
        byte[] strByte = new byte[count];
        int ch = ciphertext.length ();
        int j = 0;
        for (int i = 0; i < ch; i++) {
            if (ciphertext.charAt (i) == '%') {
                strByte[(j++)] = Byte.parseByte (sub); sub = "";
            } else {
                sub = sub + ciphertext.charAt (i);
            }
        }
        String out = null;
        try {
            out = new String (strByte, "utf-8");
        }
        catch (Exception ignored) {
        } return out;
    }

    public String RanMod ()
    {
        Random num = new Random ();
        //int n = Math.abs(num.nextInt());//if you want to get 1~8 then you can %8
        int n2 = (int) (Math.random () * 200);
        int n3 = (int) (Math.random () * 200);
        //System.out.println(String.valueOf(n2));
        String str = "";
        for (int i = 0; i < 6; i++)
        {
            char c = (char) (Math.random () * 26 + 'a');
            str = str + c;
        }
        return n2 + str + n3;
    }

    public String RanPsd () {
        int n = 0;
        n = (int) (Math.random () * (9999 - 1000));
        n = n + 1000;
        return String.valueOf (n);
    }


    public String MobRanMod ()
    {
        Random num = new Random ();
        //int n = Math.abs(num.nextInt());//if you want to get 1~8 then you can %8
        int n2 = (int) (Math.random () * 200);
        int n3 = (int) (Math.random () * 200);
        //System.out.println(String.valueOf(n2));
        return String.valueOf (n2) + String.valueOf (n3);
    }

    public String Arr_str (String FileName, String[] arrID) {
        String StringId = "";
        if (arrID.length == 1) {
            StringId = FileName + "=" + arrID[0];
        } else {
            for (String anArrID : arrID) {
                StringId = FileName + "=" + anArrID + " or " + StringId;
            }
            StringId = StringId.substring (0, StringId.length () - 4);
        }
        return StringId;
    }

    public String ArrayToString (String[] ArrayId)
    {
        StringBuilder StringId = new StringBuilder ("");
        if (ArrayId != null)
        {
            for (String aArrayId : ArrayId)
            {
                if (! checkStr.isNull (to_utf_8 (aArrayId)))
                {
                    StringId.append (to_utf_8 (aArrayId)).append ("&");
                }
                else
                {
                    StringId.append ("&");
                }
            }
            return StringId.toString ().substring (0, StringId.toString ().length () - 1);
        }
        else
        {
            return "";
        }
    }


    public String ArrayToString_NoUtf_8 (String[] ArrayId)
    {
        StringBuilder StringId = new StringBuilder ("");
        if (ArrayId != null)
        {
            for (String aArrayId : ArrayId)
            {
                if (! checkStr.isNull (to_utf_8 (aArrayId)))
                {
                    StringId.append (aArrayId).append ("&");
                }
                else
                {
                    StringId.append ("&");
                }
            }
            return StringId.toString ().substring (0, StringId.toString ().length () - 1);
        }
        else
        {
            return "";
        }
    }


    public String ArrayToStr (String[] ArrayId)
    {
        StringBuilder StringId = new StringBuilder ("");
        if (ArrayId != null)
        {
            for (String aArrayId : ArrayId)
            {
                if (! checkStr.isNull (to_utf_8 (aArrayId)))
                {
                    StringId.append(to_utf_8 (aArrayId)).append (",");
                }
                else
                {
                    StringId.append (",");
                }
            }
            return StringId.toString ().substring (0, StringId.toString ().length () - 1);
        }
        else
        {
            return "";
        }
    }

    public String ArrayToStrSym (String[] ArrayId)
    {
        StringBuilder StringId = new StringBuilder ("");
        if (ArrayId != null)
        {
            for (String aArrayId : ArrayId)
            {
                if (! checkStr.isNull (to_utf_8 (aArrayId)))
                {
                    StringId.append("'").append(to_utf_8 (aArrayId)).append("'").append (",");
                }
                else
                {
                    StringId.append (",");
                }
            }
            return StringId.toString ().substring (0, StringId.toString ().length () - 1);
        }
        else
        {
            return "";
        }
    }


    public String ArrayChange (String[] ArrayStr) {
        String Strid = "", StrFilename = "";
        if (ArrayStr != null)
        {
            if (ArrayStr.length == 1)
            {
                if (ArrayStr[0].contains ("~"))
                {
                    Strid =ArrayStr[0].split ("~")[0]+ ",";
                    StrFilename = ArrayStr[0].split ("~")[1] + ",";
                }
            }
            else
            {
                for (int i = 0; i < ArrayStr.length; i++)
                {
                    Strid = ArrayStr[i].split ("~")[0] + "," + Strid;
                    StrFilename = ArrayStr[i].split ("~")[1] + "," + StrFilename;
                }
                Strid = Strid.substring (0, Strid.length ()-1);
                StrFilename = StrFilename.substring (0, StrFilename.length ()-1);
            }
        }
        return Strid + "|" + StrFilename;
    }

    public String ArrayToParames (String FileName, String[] arrID) {
        String StringId = "";
        if (arrID.length == 1) {
            StringId = FileName + "=" + arrID[0];
        }
        else {
            for (int i = 0; i < arrID.length; i++) {
                StringId = FileName + "=" + arrID[i] + "&" + StringId;
            }
            StringId = StringId.substring (0, StringId.length () - 1);
        }
        return StringId;
    }


    public String ArrayToStrDelTrim (String[] ArrayId)
    {
        StringBuilder StringId = new StringBuilder ("");
        if (ArrayId != null)
        {
            for (String aArrayId : ArrayId)
            {
                if (! checkStr.isNull (to_utf_8 (aArrayId)))
                {
                    StringId.append(to_utf_8 (aArrayId)).append (",");
                }
            }
            return StringId.toString ().substring (0, StringId.toString ().length () - 1);
        }
        else
        {
            return "";
        }
    }

    /**
     * 加密手机电话号码
     * 加密规则：
     * 2<->6,3<->5互换位置
     * 将字母解析成数字
     *
     * @param photostr 手机号码
     * @return 经过加密了的手机号码
     */
    public String encodePhone (String photostr) {
        if (photostr.length () != 12) {
            return "";
        } else {
            char tow, six, three, five;
            char str[] = photostr.toCharArray ();
            //互换2和6的值
            tow = str[1];
            six = str[5];
            str[1] = six;
            str[5] = tow;
            //互换3和5的值
            three = str[2];
            five = str[4];
            str[2] = five;
            str[4] = three;
            //将全部号码转换为对应的字符
            //数字	1	2	3	4	5	6	7	8	9	0
            //字母	a	c	g	h	k	o	w	q	P	b
            for (int i = 0; i < str.length; i++) {
                switch (str[i]) {
                    case '1': str[i] = 'a';
                        break;
                    case '2': str[i] = 'c';
                        break;
                    case '3': str[i] = 'g';
                        break;
                    case '4': str[i] = 'h';
                        break;
                    case '5':
                        str[i] = 'k';
                        break;
                    case '6':
                        str[i] = 'o';
                        break;
                    case '7':
                        str[i] = 'w';
                        break;
                    case '8':
                        str[i] = 'q';
                        break;
                    case '9':
                        str[i] = 'p';
                        break;
                    case '0':
                        str[i] = 'b';
                        break;
                }
            }
            String strv = new String (str);
            StringBuilder sb = new StringBuilder ();
            //任意位置插入三个随机数字,在最后的字符串前加上”~”
            sb.append (strv).insert ((int) (Math.random () * 10), String.valueOf ((int) (Math.random () * 900) + 100));
            return "~" + sb.toString ();
        }
    }

}