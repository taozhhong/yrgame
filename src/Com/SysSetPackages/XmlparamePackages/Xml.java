package Com.SysSetPackages.XmlparamePackages;

import Com.SharePackages.AdapterFile;
import Com.SharePackages.FileTarget;
import Com.SharePackages.ImgAdapter;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-26
 * Time: 下午4:09
 * 对ＸＭＬ文件进行操作
 */
public class Xml implements Xml_Interface
{
    private FileTarget IsFile=null;

    public Xml()
    {
        IsFile=new AdapterFile(new ImgAdapter());
    }

    /**
     * 判断XML文件是否存在
     * @param file 要创建的XML名字和路进
     * @return boolean true:存在 false:不存在
     */
    private boolean ctrateXMlFile(String file)
    {
        if(!IsFile.IsFileExists(file))
        {
            Element carElement = new Element("web-app");//建立元素
            Document myDocument = new Document(carElement);//建立一个文档并指定根元素
            try
            {
                XMLOutputter outputter = new XMLOutputter();
                //outputter.output(myDocument,System.out);
                FileWriter writer = new FileWriter(file);
                outputter.output(myDocument, writer);
                writer.close();
                return true;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    /**
     * 添加xml
     *
     * @param xmlpath xml文件路径(带文件名)
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    @Override
    public boolean AddXml(String xmlpath,Xml_Pro Pro)
    {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(ctrateXMlFile(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = new Element("ConfigSys");//添加新元素
                Element newsm = new Element("ParameSm");//参数说明
                newsm.setText(Pro.getXmlsm());
                paramestr.addContent(newsm);
                Element newvalue = new Element("ParameValue");//参数值
                newvalue.setText(Pro.getXmlvalue());
                paramestr.addContent(newvalue);
                AllChildRen.add(paramestr);// 增加子元素
                Format format = Format.getPrettyFormat();
                format.setIndent("");
                format.setEncoding("UTF-8");
                XMLOutputter outp = new XMLOutputter(format);
                fo = new FileOutputStream(xmlpath);
                outp.output(doc, fo);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("AddXml"+e.getMessage());
                return false;
            }
            finally
            {
                try
                {
                    fi.close();
                    fo.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * 读取xml
     * @param xmlpath xml文件路径(带文件名)
     * @return List
     */
    @Override
    public List<Xml_Pro> ReadXml(String xmlpath)
    {
        List<Xml_Pro> list=new ArrayList<Xml_Pro>();
        FileInputStream fi = null;
        if(IsFile.IsFileExists(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = null;
                if(AllChildRen.size()<=0)
                {
                    list.add(null);
                }
                else
                {
                    for (int i = 0; i < AllChildRen.size(); i++)
                    {
                        Xml_Pro pro=new Xml_Pro();
                        paramestr = (Element) AllChildRen.get(i); // 得到第一本书元素
                        pro.setXmlsm(paramestr.getChild("ParameSm").getText());
                        pro.setXmlvalue(paramestr.getChild("ParameValue").getText());
                        list.add(pro);
                    }
                }
            }
            catch (Exception e)
            {
                list.add(null);
                System.out.println("ReadXml"+e.getMessage());
            }
            finally
            {
                try
                {
                    fi.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            list.add(null);
        }
        return list;
    }

    /**
     * 编辑xml
     *
     * @param xmlpath
     * @param xmlid　xml标签ＩＤ
     * @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    @Override
    public boolean EditXml(String xmlpath,Xml_Pro Pro,int xmlid)
    {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(ctrateXMlFile(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = (Element) AllChildRen.get(xmlid);
                Element newparamesm = paramestr.getChild("ParameSm");
                newparamesm.setText(Pro.getXmlsm());// 修改说明名为新的说明
                Element newpramevalue = paramestr.getChild("ParameValue");
                newpramevalue.setText(Pro.getXmlvalue());
                Format format = Format.getPrettyFormat();
                format.setIndent("");
                format.setEncoding("UTF-8");
                XMLOutputter outp = new XMLOutputter(format);
                fo = new FileOutputStream(xmlpath);
                outp.output(doc, fo);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("EditXml"+e.getMessage());
                return false;
            }
            finally
            {
                try
                {
                    fi.close();
                    fo.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * 删除xml
     *
     * @param xmlpath
     * @param xmlid　xml标签ＩＤ
     * @return boolean true：成功 false：失败
     */
    @Override
    public boolean DelXml(String xmlpath,int xmlid)
    {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(IsFile.IsFileExists(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                AllChildRen.remove(xmlid);// 删除指定位置的子元素
                //   String indent = " ";
                //   boolean newLines = true;
                //   XMLOutputter outp = new XMLOutputter(indent, newLines, "GBK");
                Format format = Format.getPrettyFormat();
                format.setIndent("");
                format.setEncoding("UTF-8");
                XMLOutputter outp = new XMLOutputter(format);
                fo = new FileOutputStream(xmlpath);
                outp.output(doc, fo);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("DelXml"+e.getMessage());
                return false;
            }
            finally
            {
                try
                {
                    fi.close();
                    fo.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * 读取xml
     *
     * @param xmlpath xml文件路径(带文件名)
     * @return List
     */
    @Override
    public List<Xml_Pro> SearchXml(String xmlpath, int xmlid)
    {
        List<Xml_Pro> list=new ArrayList<Xml_Pro>();
        FileInputStream fi = null;
        if(IsFile.IsFileExists(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = null;
                if(AllChildRen.size()<=0)
                {
                    list.add(null);
                }
                else
                {
                    for (int i = 0; i < AllChildRen.size(); i++)
                    {
                        Xml_Pro pro=new Xml_Pro();
                        if(i==xmlid)
                        {
                            paramestr = (Element) AllChildRen.get(i); // 得到第一本书元素
                            pro.setXmlsm(paramestr.getChild("ParameSm").getText());
                            pro.setXmlvalue(paramestr.getChild("ParameValue").getText());
                            list.add(pro);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                list.add(null);
                System.out.println("SearchXml"+e.getMessage());
            }
            finally
            {
                try
                {
                    fi.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            list.add(null);
        }
        return list;
    }

    /**
     * 查找xml配置文件中的指定值
     *
     * @param xmlpath xml文件路径
     * @param xmlid　xml标签ＩＤ
     * @return String 查找到的xml值
     */
    @Override
    public String FindXmlValue(String xmlpath, int xmlid)
    {
        String ParameValue="";//配置参数里面的值
        FileInputStream fi = null;
        if(IsFile.IsFileExists(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = null;
                for (int i = 0; i < AllChildRen.size(); i++)
                {
                    if(i==xmlid)
                    {
                        paramestr = (Element) AllChildRen.get(i); // 得到第一本书元素
                        ParameValue=paramestr.getChild("ParameValue").getText();
                        break;
                    }
                }
            }
            catch (Exception e)
            {
                System.out.println("FindXmlValue"+e.getMessage());
            }
            finally
            {
                try
                {
                    fi.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        return ParameValue;
    }

    /**
     * 添加数据源xml
     *
     * @param xmlpath xml文件路径(带文件名)
     * @param Pro
     * @return boolean true：成功 false：失败
     * @parame Xml_Pro xml属性字段对象
     */
    @Override
    public boolean AddDataSourceXml(String xmlpath, Xml_Pro Pro)
    {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(ctrateXMlFile(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = new Element("DataSource");//添加新元素
                Element dataip = new Element("DataIp");//连接数据库IP
                dataip.setText(Pro.getXmlip());
                paramestr.addContent(dataip);
                Element datadriver = new Element("DataDriver");//连接数据库驱动
                datadriver.setText(Pro.getXmldriver());
                paramestr.addContent(datadriver);
                Element datauser = new Element("DataUser");//连接数据库用户名
                datauser.setText(Pro.getXmlsm());
                paramestr.addContent(datauser);
                Element datapwd = new Element("DataPwd");//连接数据库密码
                datapwd.setText(Pro.getXmlvalue());
                paramestr.addContent(datapwd);
                Element dataname = new Element("DataName");//连接数据库库名
                dataname.setText(Pro.getXmlkm());
                paramestr.addContent(dataname);
                Element datakey = new Element("DataKey");//连接数据库标识符
                datakey.setText(Pro.getXmlkey());
                paramestr.addContent(datakey);
                Element dataisck = new Element("DataIsCk");//是否检测用户ID
                dataisck.setText(Pro.getIsck());
                paramestr.addContent(dataisck);
                Element datauidkm = new Element("DataUidKm");//检测用户ID数据库
                datauidkm.setText(Pro.getXmluidkm());
                paramestr.addContent(datauidkm);
                AllChildRen.add(paramestr);// 增加子元素
                Format format = Format.getPrettyFormat();
                format.setIndent("");
                format.setEncoding("UTF-8");
                XMLOutputter outp = new XMLOutputter(format);
                fo = new FileOutputStream(xmlpath);
                outp.output(doc, fo);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("AddDataSourceXml"+e.getMessage());
                return false;
            }
            finally
            {
                try
                {
                    fi.close();
                    fo.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            return false;
        }
    }

    /**
     * 编辑数据源xml
     *
     * @param xmlpath xml文件路径(带文件名)
     * @param Pro
     * @param xmlid   　xml标签ＩＤ  @parame Xml_Pro xml属性字段对象
     * @return boolean true：成功 false：失败
     */
    @Override
    public boolean EditDataSourceXml(String xmlpath, Xml_Pro Pro, int xmlid)
    {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        if(ctrateXMlFile(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = (Element) AllChildRen.get(xmlid);
                Element newparameip = paramestr.getChild("DataIp");
                newparameip.setText(Pro.getXmlip());// 修改连接ip
                Element newparamedriver = paramestr.getChild("DataDriver");
                newparamedriver.setText(Pro.getXmldriver());// 修改连接驱动
                Element newdatauser = paramestr.getChild("DataUser");
                newdatauser.setText(Pro.getXmlsm());// 修改连接用户名
                Element newdatapwd = paramestr.getChild("DataPwd");
                newdatapwd.setText(Pro.getXmlvalue());// 修改连接密码
                Element newdataname = paramestr.getChild("DataName");
                newdataname.setText(Pro.getXmlkm());// 修改连接库名
                Element newdataisck = paramestr.getChild("DataIsCk");
                newdataisck.setText(Pro.getIsck());// 是否检测用户ID
                Element newdatauidkm = paramestr.getChild("DataUidKm");
                newdatauidkm.setText(Pro.getXmluidkm());// 用户数据库
                Format format = Format.getPrettyFormat();
                format.setIndent("");
                format.setEncoding("UTF-8");
                XMLOutputter outp = new XMLOutputter(format);
                fo = new FileOutputStream(xmlpath);
                outp.output(doc, fo);
                return true;
            }
            catch (Exception e)
            {
                System.out.println("EditDataSourceXml"+e.getMessage());
                return false;
            }
            finally
            {
                try
                {
                    fi.close();
                    fo.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            return false;
        }
    }


    /**
     * 读取数据源xml的值
     *
     * @param xmlpath xml文件路径(带文件名)
     * @param xmlid
     * @return List
     */
    @Override
    public List<Xml_Pro> SearchDataSourceXml(String xmlpath, int xmlid)
    {
        List<Xml_Pro> list=new ArrayList<Xml_Pro>();
        FileInputStream fi = null;
        if(IsFile.IsFileExists(xmlpath))
        {
            try
            {
                fi = new FileInputStream(xmlpath);
                SAXBuilder sb = new SAXBuilder();
                Document doc = sb.build(fi);
                Element root = doc.getRootElement(); // 得到根元素
                List AllChildRen = root.getChildren(); // 得到根元素所有子元素的集合
                Element paramestr = null;
                if(AllChildRen.size()<=0)
                {
                    list.add(null);
                }
                else
                {
                    for (int i = 0; i < AllChildRen.size(); i++)
                    {
                        Xml_Pro pro=new Xml_Pro();
                        if(i==xmlid)
                        {
                            paramestr = (Element) AllChildRen.get(i); // 得到第一本书元素
                            pro.setXmlip(paramestr.getChild("DataIp").getText());
                            pro.setXmlsm(paramestr.getChild("DataUser").getText());
                            pro.setXmlvalue(paramestr.getChild("DataPwd").getText());
                            pro.setXmlkm(paramestr.getChild("DataName").getText());
                            pro.setXmldriver(paramestr.getChild("DataDriver").getText());
                            pro.setIsck(paramestr.getChild("DataIsCk").getText());
                            pro.setXmluidkm(paramestr.getChild("DataUidKm").getText());
                            list.add(pro);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                list.add(null);
                System.out.println("SearchDataSourceXml:"+e.getMessage());
            }
            finally
            {
                try
                {
                    fi.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            list.add(null);
        }
        return list;
    }
}
