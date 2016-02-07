package Com.SysSetPackages.XmlparamePackages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-26
 * Time: 下午3:56
 * To change this template use File | Settings | File Templates.
 */
public interface Xml_Interface
{
    /**
     * 添加xml
     * @param xmlpath xml文件路径(带文件名)
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    public boolean AddXml(String xmlpath, Xml_Pro Pro);




    /**
     * 读取xml
     * @param xmlpath xml文件路径(带文件名)
     * @return List
     */
    public List<Xml_Pro> ReadXml(String xmlpath);

    /**
     * 编辑xml
     * @param xmlpath xml文件路径(带文件名)
     * @param xmlid　xml标签ＩＤ
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    public boolean EditXml(String xmlpath, Xml_Pro Pro, int xmlid);

    /**
     * 删除xml
     * @param xmlpath
     * @param xmlid　xml标签ＩＤ
     * @return boolean true：成功 false：失败
     */
    public boolean DelXml(String xmlpath, int xmlid);


    /**
     * 读取xml的值
     * @param xmlpath xml文件路径(带文件名)
     * @return List
     */
    public List<Xml_Pro> SearchXml(String xmlpath, int xmlid);

    /**
     * 查找xml配置文件中的指定值
     * @param xmlpath xml文件路径
     * @param xmlid id
     * @return String 查找到的xml值
     */
    public String FindXmlValue(String xmlpath, int xmlid);


    /**
     * 添加数据源xml
     * @param xmlpath xml文件路径(带文件名)
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    public boolean AddDataSourceXml(String xmlpath, Xml_Pro Pro);

    /**
     * 编辑数据源xml
     * @param xmlpath xml文件路径(带文件名)
     * @param xmlid　xml标签ＩＤ
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    public boolean EditDataSourceXml(String xmlpath, Xml_Pro Pro, int xmlid);

    /**
     * 读取xml的值
     * @param xmlpath xml文件路径(带文件名)
     * @return List
     */
    public List<Xml_Pro> SearchDataSourceXml(String xmlpath, int xmlid);

}
