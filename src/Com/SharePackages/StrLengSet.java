package Com.SharePackages;

/**
 * 字符长度和获取特定字符
 *
 * @author fengxu
 */
public class StrLengSet {
    private String appchar = "";//后面追回的字符

    public void setAppchar (String appchar) {
        this.appchar = appchar.intern ();
    }


    /**
     * 返回字符串长度
     *
     * @param str
     * @return int
     */
    public int getStrLeng (String str) {
        return str.getBytes ().length;
    }

    /**
     * @param str  String	字符串
     * @param leng number	长度
     * @return String        返回字符串+"..."
     */
    public String setStrLeng (String str, int leng) {
        int lengg = 0;

        StringBuffer stttt = new StringBuffer ("");

        if (str.getBytes ().length > leng) {
            for (int i = 0; i < leng; ) {
                if ((int) str.charAt (lengg) > 126) {
                    i += 2;
                } else {
                    i++;
                }
                stttt.append (str.charAt (lengg)); lengg++;
            }
        } else {
            return str;
        }
        return stttt.append (appchar).toString ();
    }


    public String setStrLeng1 (String str, int leng) {
        int lengg = 0;
        StringBuffer stttt = new StringBuffer ("");

        if (str.getBytes ().length > leng) {
            for (int i = 0; i < leng; ) {
                if ((int) str.charAt (lengg) > 126) {
                    i += 2;
                } else {
                    i++;
                } stttt.append (str.charAt (lengg)); lengg++;
            }
        } else {
            return str;
        } return stttt.append (appchar).toString ();
    }

    //删除字符追加符号
    public String delLeng (String str, int leng) {
        return setStrLeng (str, leng);
    }
}
