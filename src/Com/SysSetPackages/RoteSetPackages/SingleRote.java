package Com.SysSetPackages.RoteSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午8:28
 * To change this template use File | Settings | File Templates.
 */
public class SingleRote {
    private static CreateRote factory = null;

    private SingleRote () {
    }

    public synchronized static CreateRote getInstance () {
        if (factory == null) {
            try {
                Class c = Class.forName ("Com.SysSetPackages.RoteSetPackages.FactoryRote");
                factory = (CreateRote) c.newInstance ();
            }
            catch (ClassNotFoundException e) {
                System.out.print ("Rote_getInstance" + e.getMessage ());
            }
            catch (InstantiationException e) {
                System.out.print ("Rote_getInstance" + e.getMessage ());
            }
            catch (IllegalAccessException e) {
                System.out.print ("Rote_getInstance" + e.getMessage ());
            }
        } return factory;
    }
}
