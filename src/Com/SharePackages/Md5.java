package Com.SharePackages;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Cipher;
import java.security.MessageDigest;


public class Md5 {
    private static String cKey = "1234567890abcDEF";

    //解密
    public static String Decrypt (String sSrc) {
        try {
            //判断Key是否正确
            if (cKey == null) {
                System.out.print ("Key为空null"); return null;
            }
            //判断Key是否为16位
            if (cKey.length () != 16) {
                System.out.print ("Key长度不是16位");
                return null;
            }
            byte[] raw = cKey.getBytes ("ASCII"); SecretKeySpec skeySpec = new SecretKeySpec (raw, "AES");
            Cipher cipher = Cipher.getInstance ("AES"); cipher.init (Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte (sSrc);
            try {
                byte[] original = cipher.doFinal (encrypted1); String originalString = new String (original);
                return originalString;
            }
            catch (Exception e) {
                System.out.println (e.toString ()); return null;
            }
        }
        catch (Exception ex) {
            System.out.println (ex.toString ()); return null;
        }
    }

    //判断Key是否正确(加密)
    public static String getMD5ofStr (String sSrc) {
        try {
            if (cKey == null) {
                System.out.print ("Key为空null");
                return null;
            }
            //判断Key是否为16位
            if (cKey.length () != 16) {
                System.out.print ("Key长度不是16位");
                return null;
            }
            byte[] raw = cKey.getBytes ("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec (raw, "AES");
            Cipher cipher = Cipher.getInstance ("AES");
            cipher.init (Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal (sSrc.getBytes ());
            return byte2hex (encrypted).toLowerCase ();
        }
        catch (Exception ex) {
            System.out.println (ex.toString ());
            return null;
        }
    }

    public static byte[] hex2byte (String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length ();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt (strhex.substring (i * 2, i * 2 + 2), 16);
        }
        return b;
    }

    public static String byte2hex (byte[] b) {
        String hs = "", stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString (b[n] & 0XFF));
            if (stmp.length () == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase ();
    }


    /**
     * MD5加密
     *
     * @param source 要加密码的字符串
     * @return String 加密之后的字符串
     */
    public static String md5 (String source) {

        StringBuffer sb = new StringBuffer (32); try {
            MessageDigest md = MessageDigest.getInstance ("MD5");
            byte[] array = md.digest (source.getBytes ("utf-8"));

            for (int i = 0; i < array.length; i++) {
                sb.append (Integer.toHexString ((array[i] & 0xFF) | 0x100).toUpperCase ().substring (1, 3));
            }
        }
        catch (Exception e) {
            return null;
        } return sb.toString ();
    }

//    public static void main(String few[])
//    {
//    	System.out.println(new MD5().getMD5ofStr("123456"));
//    	
//    }
}

