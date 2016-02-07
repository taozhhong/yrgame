package Com.SysSetPackages.XmlparamePackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-26
 * Time: 下午4:09
 * To change this template use File | Settings | File Templates.
 */
public class FactoryXml implements CreateXml
{
    public FactoryXml()
    {
    }

    @Override
    public Xml_Interface getXml()
    {
        return new Xml();
    }
}
