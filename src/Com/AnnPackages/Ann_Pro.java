package Com.AnnPackages;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-10
 * Time: 下午7:49
 * To change this template use File | Settings | File Templates.
 */
public class Ann_Pro
{
    private int annid;
    private String anntitle;
    private String anncon;
    private int islock;
    private String anndate;

    public Ann_Pro ()
    {

    }

    public int getAnnid ()
    {
        return annid;
    }

    public void setAnnid (int annid)
    {
        this.annid = annid;
    }

    public String getAnntitle ()
    {
        return anntitle;
    }

    public void setAnntitle (String anntitle)
    {
        this.anntitle = anntitle;
    }

    public String getAnncon ()
    {
        return anncon;
    }

    public void setAnncon (String anncon)
    {
        this.anncon = anncon;
    }

    public int getIslock ()
    {
        return islock;
    }

    public void setIslock (int islock)
    {
        this.islock = islock;
    }

    public String getAnndate ()
    {
        return anndate;
    }

    public void setAnndate (String anndate)
    {
        this.anndate = anndate;
    }

}
