package Com.SharePackages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-10-9
 * Time: 10:45:56
 */

public class Format_Date {

    /**
     * @param f
     * @return String 时间格式
     * @readme 当前时间, 自己定义格式
     */
    public String cu_datetime (String f)
    {
        Date d = new Date ();
        return centrum (f).format (d);
    }

    /**
     * @param d 自定义时间
     * @param f 时间格式
     * @return Sring
     * @readme 自定义时间与自定义格式
     */
    public String set_date (Date d, String f)
    {
        return centrum (f).format (d);
    }

    public String set_date (String d, String f)
    {
        try
        {
            SimpleDateFormat sdf = centrum ("yyyy-MM-dd HH:mm:ss");
            return centrum (f).format (sdf.parse (d));
        }
        catch (ParseException p)
        {
            System.out.println ("date format error:" + p.getMessage ());
            return null;
        }
    }

    /**
     * @param f String
     * @return SimpleDateFormat
     * @readme 传入参数, 时间格式.返回SimpleDateFormat对像
     */
    public SimpleDateFormat centrum (String f)
    {
        SimpleDateFormat sdf = new SimpleDateFormat (f);
        return sdf;
    }
    /**
     * 格式例子:
     * 1.yyyy-MM-dd hh:mm:ss
     */

    /**
     * @param d
     * @return String 天数
     * @readme 当前时间加上自定义要添加的天数
     */
    public String now_add_day (int d)
    {
        return day_center (new Date (), d);
    }

    /**
     * @param d   日期
     * @param day 天数
     * @return String
     * @readme 自定义日期加上自定义天数
     */
    public String set_add_day (Date d, int day)
    {
        return day_center (d, day);
    }

    /**
     * @param d   日期
     * @param day 天数
     * @return String
     * @readme 时间加上天数
     */
    private String day_center (Date d, int day)
    {
        SimpleDateFormat sdf = centrum ("yyyy-MM-dd HH:mm:ss");
        String d_f = sdf.format (d);
        Calendar c = Calendar.getInstance ();
        Date da = null;
        try
        {
            da = sdf.parse (d_f);
        }
        catch (ParseException e)
        {
            e.printStackTrace ();
        }
        c.setTime (da);
        c.add (Calendar.DAY_OF_MONTH, day);
        return sdf.format (c.getTime ());
    }

    /**
     * 比较当前日期和指定日期 return boolean
     * 如果当前日期大于指定日期返回true否则返回flase
     */
    public boolean dateCompare (String str)
    {
        boolean bea = false;
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String isDate = sdf.format (new Date ());
        Date date1;
        Date date0;
        try
        {
            date1 = sdf.parse (str);
            date0 = sdf.parse (isDate);
            if (date0.after (date1))
            {
                bea = true;
            }
        }
        catch (ParseException e)
        {
            System.out.print ("dateCompare:" + e.getMessage ());
        }
        return bea;
    }


    /**
     * 比较当前日期和指定日期,注：要比较日期的格式要跟当前日期格式一致
     *
     * @param StrDate:要比较日期的字符串
     * @param StrDateFormat:当前日期格式
     * @return boolean 如果当前日期大于指定日期返回true否则返回flase
     */
    public boolean dateCompare (String StrDate, String StrDateFormat)
    {
        boolean bea = false;
        SimpleDateFormat sdf = new SimpleDateFormat (StrDateFormat);
        String isDate = sdf.format (new Date ());
        Date date1;
        Date date0;
        try
        {
            date1 = sdf.parse (StrDate);
            date0 = sdf.parse (isDate);
            if (date0.after (date1))
            {
                bea = true;
            }
        }
        catch (ParseException e)
        {
            System.out.print ("dateCompare:" + e.getMessage ());
        }
        return bea;
    }

    public Date backdate (String dateString)
    {
        SimpleDateFormat sdf = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        Date hdate = null;
        try
        {
            hdate = sdf.parse (dateString);
        }
        catch (ParseException e)
        {
            e.printStackTrace ();
        }
        return hdate;
    }

    public String Format_date (String formdate, String nowdate)
    {
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat formatter1 = new SimpleDateFormat (formdate);
        String str = "";
        try
        {
            date = formatter.parse (nowdate);
            str = formatter1.format (date);
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return str;
    }


    //此方法计算时间毫秒
    public long fromDateStringToLong (String inVal)
    {
        Date date = null; //定义时间类型
        SimpleDateFormat inputFormat = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        try
        {
            date = inputFormat.parse (inVal); //将字符型转换成日期型
        }
        catch (Exception e)
        {
            e.printStackTrace ();
        }
        return date.getTime (); //返回毫秒数
    }

    public String FormDateC (String DateStr) {
        long second = 1000;
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long halfamonth = day * 15;
        long month = day * 30;
        long now = fromDateStringToLong (cu_datetime ("yyyy-MM-dd HH:mm:ss"));
        long diffValue = now - fromDateStringToLong (DateStr); String result = "";
        if (diffValue < 0)
        {
            result = "No_Time_Error";
        }
        else
        {
            int monthC = (int) (diffValue / month);
            int weekC = (int) (diffValue / (7 * day));
            int dayC = (int) (diffValue / day);
            int hourC = (int) (diffValue / hour);
            int minC = (int) (diffValue / minute);
            int secoC = (int) (diffValue / second);
            if (monthC >= 1) {
                result = monthC + "个月前";
            } else if (weekC >= 1) {
                result = weekC + "个星期前";
            } else if (dayC >= 1) {
                result = dayC + "天前";
            } else if (hourC >= 1) {
                result = hourC + "个小时前";
            } else if (minC >= 1) {
                result = minC + "分前";
            } else if (secoC >= 1) {
                result = secoC + "秒前";
            } else {
                result = "刚刚";
            }
        }
        return result;
    }


    public String FormDateEvent (String DateStr)
    {
        long second = 1000;
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long halfamonth = day * 15;
        long month = day * 30;
        long now = fromDateStringToLong (cu_datetime ("yyyy-MM-dd HH:mm:ss"));
        long diffValue = fromDateStringToLong (DateStr) - now;
        String result = "";
        if (diffValue < 0)
        {
            result = "No_Time_Error";
        }
        else
        {
            int monthC = (int) (diffValue / month);
            int weekC = (int) (diffValue / (7 * day));
            int dayC = (int) (diffValue / day);
            int hourC = (int) (diffValue / hour);
            int minC = (int) (diffValue / minute);
            int secoC = (int) (diffValue / second);
            if (monthC >= 1)
            {
                result = monthC + "个月";
            }
            else if (weekC >= 1)
            {
                result = weekC + "个星期";
            }
            else if (dayC >= 1)
            {
                result = dayC + "天";
            }
            else if (hourC >= 1)
            {
                result = hourC + "个小时";
            }
            else if (minC >= 1)
            {
                result = minC + "分";
            }
            else if (secoC >= 1)
            {
                result = secoC + "秒";
            }
            else
            {
                result = "刚刚";
            }
        }
        return result;
    }


    /**
     * 显示天
     *
     * @param DateStr 时间
     * @return 返回天数
     */
    public String FormDateDayEvent (String DateStr)
    {
        long minute = 1000 * 60;
        long hour = minute * 60;
        long day = hour * 24;
        long now = fromDateStringToLong (cu_datetime ("yyyy-MM-dd HH:mm:ss"));
        long diffValue = fromDateStringToLong (DateStr) - now;
        String result = "";
        if (diffValue < 0)
        {
            result = "No_Time_Error";
        }
        else
        {
            int dayC = (int) (diffValue / day);
            if (dayC >= 1)
            {
                result = dayC + "天";
            }
            else
            {
                result = "结束";
            }
        }
        return result;
    }

    /**
     * @param d      日期
     * @param secode 分数
     * @return String
     * @readme 时间加上分数
     */
    public String second_center (Date d, int secode) {
        SimpleDateFormat sdf = centrum ("yyyy-MM-dd HH:mm:ss");

        String d_f = sdf.format (d);

        Calendar c = Calendar.getInstance ();
        Date da = null;
        try {
            da = sdf.parse (d_f);
        }
        catch (ParseException e) {
            e.printStackTrace ();
        }
        c.setTime (da);
        c.add (Calendar.MINUTE, secode);
        return sdf.format (c.getTime ());
    }
}
