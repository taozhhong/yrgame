package Com.AnnPackages;


/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-10
 * Time: 下午8:02
 * To change this template use File | Settings | File Templates.
 */
public class SingleAnn
{
    private static CreateAnn factory = null;

    private SingleAnn ()
    {
    }

    public synchronized static CreateAnn getInstance ()
    {
        if (factory == null)
        {
            try
            {
                Class c = Class.forName ("Com.AnnPackages.FactoryAnn");
                factory=(CreateAnn)c.newInstance();
            }
            catch (ClassNotFoundException e)
            {
                System.out.print("Ann_getInstance"+e.getMessage());
            }
            catch (InstantiationException e)
            {
                System.out.print("Ann_getInstance"+e.getMessage());
            }
            catch (IllegalAccessException e)
            {
                System.out.print("Ann_getInstance"+e.getMessage());
            }
        }
        return factory;
    }
}
