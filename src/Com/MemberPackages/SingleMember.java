package Com.MemberPackages;

/**
 * Created by Administrator on 2015/9/14.
 */
public class SingleMember
{
    private static CreateMember factory = null;

    private SingleMember()
    {
    }

    public synchronized static CreateMember getInstance ()
    {
        if (factory == null) {
            try {
                Class c = Class.forName ("Com.MemberPackages.FactoryMember");
                factory = (CreateMember) c.newInstance ();
            }
            catch (ClassNotFoundException e) {
                System.out.print ("Member_getInstance" + e.getMessage ());
            }
            catch (InstantiationException e) {
                System.out.print ("Member_getInstance" + e.getMessage ());
            }
            catch (IllegalAccessException e) {
                System.out.print ("Member_getInstance" + e.getMessage ());
            }
        }
        return factory;
    }
}
