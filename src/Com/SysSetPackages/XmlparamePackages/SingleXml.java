package Com.SysSetPackages.XmlparamePackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-26
 * Time: 下午4:10
 * To change this template use File | Settings | File Templates.
 */
public class SingleXml
{
    private static CreateXml factory=null;

    private SingleXml()
    {
    }

    public synchronized static CreateXml getInstance()
    {
        if(factory==null)
        {
            try
            {
                Class c=Class.forName("Com.SysSetPackages.XmlparamePackages.FactoryXml");
                factory=(CreateXml)c.newInstance();
            }
            catch (ClassNotFoundException e)
            {
                System.out.print("Xml_getInstance"+e.getMessage());
            }
            catch (InstantiationException e)
            {
                System.out.print("Xml_getInstance"+e.getMessage());
            }
            catch (IllegalAccessException e)
            {
                System.out.print("Xml_getInstance"+e.getMessage());
            }
        }
        return factory;
    }
}
