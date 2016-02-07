package Com.AnnPackages;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-10
 * Time: 下午8:02
 * To change this template use File | Settings | File Templates.
 */
public class FactoryAnn implements CreateAnn
{
    public FactoryAnn () {}

    @Override
    public AnnDb_Interface getAnnDb ()
    {
        return new AnnDb ();
    }

    @Override
    public AnnPage_Interface getAnnPage ()
    {
        return new AnnPage ();
    }
}
