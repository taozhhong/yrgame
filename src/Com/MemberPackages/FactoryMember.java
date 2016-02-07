package Com.MemberPackages;

/**
 * Created by Administrator on 2015/9/14.
 */
public class FactoryMember implements CreateMember
{
    public FactoryMember()
    {
    }

    @Override
    public MemberDb_Interface getMemberDb()
    {
        return new MemberDb();
    }

    @Override
    public MemberPage_Interface getMemberPage()
    {
        return new MemberPage();
    }
}
