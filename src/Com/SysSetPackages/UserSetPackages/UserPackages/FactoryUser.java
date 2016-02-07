package Com.SysSetPackages.UserSetPackages.UserPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午9:20
 * To change this template use File | Settings | File Templates.
 */
public class FactoryUser implements CreateUser {
    public FactoryUser () {
    }

    @Override
    public UserDb_Interface getUserDb () {
        return new UserDb ();
    }

    @Override
    public UserPage_Interface getUserPage () {
        return new UserPage ();
    }
}
