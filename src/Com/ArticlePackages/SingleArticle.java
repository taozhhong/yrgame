package Com.ArticlePackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class SingleArticle
{
    private static CreateArticle factory = null;

    private SingleArticle ()
    {
    }

    public synchronized static CreateArticle getInstance ()
    {
        if (factory == null)
        {
            try
            {
                Class c = Class.forName ("Com.ArticlePackages.FactoryArticle");
                factory = (CreateArticle) c.newInstance ();
            }
            catch (ClassNotFoundException e)
            {
                System.out.print ("Article_getInstance" + e.getMessage ());
            }
            catch (InstantiationException e)
            {
                System.out.print ("Article_getInstance" + e.getMessage ());
            }
            catch (IllegalAccessException e)
            {
                System.out.print ("Article_getInstance" + e.getMessage ());
            }
        }
        return factory;
    }
}
