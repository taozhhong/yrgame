package Com.SysSetPackages.AreaSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-16
 * Time: 下午1:49
 * To change this template use File | Settings | File Templates.
 */
public class FactoryArea implements CreateArea {
    public FactoryArea () {
    }


    @Override
    public AreaDb_Interface getArea () {
        return new AreaDb ();
    }
}
