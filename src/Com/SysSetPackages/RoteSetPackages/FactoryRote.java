package Com.SysSetPackages.RoteSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午8:27
 * To change this template use File | Settings | File Templates.
 */
public class FactoryRote implements CreateRote {
    public FactoryRote () {
    }

    @Override
    public RoteDb_Interface getReoteDb () {
        return new RoteDb ();
    }

    @Override
    public RotePage_Interface getRotePage () {
        return new RotePage ();
    }
}
