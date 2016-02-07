package Com.SysSetPackages.AreaSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-16
 * Time: 下午1:54
 * To change this template use File | Settings | File Templates.
 */
public class SingleArea {
    private static CreateArea factory = null;

    private SingleArea () {

    }

    public synchronized static CreateArea getInstance () {
        if (factory == null) {
            try {
                Class c = Class.forName ("Com.SysSetPackages.AreaSetPackages.FactoryArea");
                factory = (CreateArea) c.newInstance ();
            }
            catch (ClassNotFoundException e) {
                System.out.print ("Area_getInstance" + e.getMessage ());
            }
            catch (InstantiationException e) {
                System.out.print ("Area_getInstance" + e.getMessage ());
            }
            catch (IllegalAccessException e) {
                System.out.print ("Area_getInstance" + e.getMessage ());
            }
        } return factory;
    }
}
