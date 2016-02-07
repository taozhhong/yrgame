package Com.SysSetPackages.UserSetPackages.UserPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午9:21
 * To change this template use File | Settings | File Templates.
 */
public class SingleUser {
    private static CreateUser factory = null;

    private SingleUser () {
    }

    public synchronized static CreateUser getInstance () {
        if (factory == null) {
            try {
                Class c = Class.forName ("Com.SysSetPackages.UserSetPackages.UserPackages.FactoryUser");
                factory = (CreateUser) c.newInstance ();
            }
            catch (ClassNotFoundException e) {
                System.out.print ("User_getInstance" + e.getMessage ());
            }
            catch (InstantiationException e) {
                System.out.print ("User_getInstance" + e.getMessage ());
            }
            catch (IllegalAccessException e) {
                System.out.print ("User_getInstance" + e.getMessage ());
            }
        } return factory;
    }
}
