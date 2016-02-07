package Com.SharePackages;

import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-3-27
 * Time: 下午3:51
 * 日志输出
 */
public class Log4jWriter
{
    private static Logger logger = Logger.getLogger (Log4jWriter.class);

    public Log4jWriter ()
    {
    }

    public static void getLogMsg(String LogCon)
    {
        logger.info (LogCon);
    }
}
