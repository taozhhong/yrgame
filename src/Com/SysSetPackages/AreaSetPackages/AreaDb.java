package Com.SysSetPackages.AreaSetPackages;

import Com.DbConPackages.linkdb_call;
import Com.SharePackages.checkStr;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-16
 * Time: 上午10:02
 * To change this template use File | Settings | File Templates.
 */
public class AreaDb implements AreaDb_Interface {

    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;

    public AreaDb () {
        conn = new linkdb_call ();
    }

    /**
     * 对表nlsp_Area增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,edit-修改操作)
     * @param Pro      表nlsp_area中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    @Override
    public int AreaAction (String MarkStr, Area_Pro Pro, String SqlWhere) {
        int backint = 0;
        String CallSql = "{call area_sp_action(?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, MarkStr);
            CallStmet.setInt (2, Pro.getId ());
            CallStmet.setString (3, Pro.getName ());
            CallStmet.setString (4, Pro.getNamepy ());
            CallStmet.setInt (5, Pro.getPid ());
            CallStmet.setInt (6, Pro.getStatus ());
            CallStmet.setString (7, SqlWhere);
            CallStmet.registerOutParameter (8, OracleTypes.INTEGER);
            CallStmet.executeUpdate ();
            backint = CallStmet.getInt (8);
        }
        catch (SQLException e) {
            System.out.println ("AreaAction" + e.getMessage ());
        }
        finally {
            conn.clearParameters (); conn.CloseConn ();
        }
        return backint;
    }

    /**
     * 省列表
     *
     * @param Pk          是否注键,如果是,那么产生的select的语句是不一样的.
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param C_FiledStr  当不用主键排序时，C_FiledStr表示子查询查询字估
     * @param SqlWhere    查询条件,不带where
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    @Override
    public List<Area_Pro> ProvinceList (String Pk, String TopInt,
                                        String FiledStr, String TableName,
                                        String C_FiledStr, String SqlWhere,
                                        String OrderyFiled, String OrderyType) {
        List<Area_Pro> list = new ArrayList<Area_Pro> ();
        String CallSql = "{Call DataListCursor_Pkg_DataList.DataList(?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, Pk);
            CallStmet.setString (2, TopInt);
            CallStmet.setString (3, FiledStr);
            CallStmet.setString (4, TableName);
            CallStmet.setString (5, C_FiledStr);
            CallStmet.setString (6, SqlWhere);
            CallStmet.setString (7, OrderyFiled);
            CallStmet.setString (8, OrderyType);
            CallStmet.registerOutParameter (9, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            rs = (ResultSet) CallStmet.getObject (9);
            if (rs.next ())
            {
                do {
                    Area_Pro pro = new Area_Pro ();
                    pro.setId (rs.getInt ("provinceid"));
                    pro.setName (rs.getString ("provincename"));
                    pro.setNamepy (rs.getString ("provincepy"));
                    pro.setStatus (rs.getInt ("provincestatus"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else
            {
                list.add (null);
            }
        }
        catch (SQLException e)
        {
            list.add (null);
            System.out.println ("ProvinceList" + e.getMessage ());
        }
        finally
        {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 市列表
     *
     * @param Pk          是否注键,如果是,那么产生的select的语句是不一样的.
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param C_FiledStr  当不用主键排序时，C_FiledStr表示子查询查询字估
     * @param SqlWhere    查询条件,不带where
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    @Override
    public List<Area_Pro> CityList (String Pk, String TopInt, String FiledStr,
                                    String TableName, String C_FiledStr,
                                    String SqlWhere, String OrderyFiled,
                                    String OrderyType) {
        List<Area_Pro> list = new ArrayList<Area_Pro> ();
        String CallSql = "{Call DataListCursor_Pkg_DataList.DataList(?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, Pk);
            CallStmet.setString (2, TopInt);
            CallStmet.setString (3, FiledStr);
            CallStmet.setString (4, TableName);
            CallStmet.setString (5, C_FiledStr);
            CallStmet.setString (6, SqlWhere);
            CallStmet.setString (7, OrderyFiled);
            CallStmet.setString (8, OrderyType);
            CallStmet.registerOutParameter (9, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            rs = (ResultSet) CallStmet.getObject (9);
            if (rs.next ())
            {
                do {
                    Area_Pro pro = new Area_Pro ();
                    pro.setId (rs.getInt ("cityid"));
                    pro.setName (rs.getString ("cityname"));
                    pro.setNamepy (rs.getString ("citypy"));
                    pro.setStatus (rs.getInt ("citystatus"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else
            {
                list.add (null);
            }
        }
        catch (SQLException e)
        {
            list.add (null);
            System.out.println ("CityList" + e.getMessage ());
        }
        finally
        {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 列表
     *
     * @param Pk          是否注键,如果是,那么产生的select的语句是不一样的.
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param C_FiledStr  当不用主键排序时，C_FiledStr表示子查询查询字估
     * @param SqlWhere    查询条件,不带where
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    @Override
    public List<Area_Pro> AreaList (String Pk, String TopInt,
                                    String FiledStr, String TableName,
                                    String C_FiledStr, String SqlWhere,
                                    String OrderyFiled, String OrderyType)
    {
        List<Area_Pro> list = new ArrayList<Area_Pro> ();
        String CallSql = "{Call DataListCursor_Pkg_DataList.DataList(?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, Pk);
            CallStmet.setString (2, TopInt);
            CallStmet.setString (3, FiledStr);
            CallStmet.setString (4, TableName);
            CallStmet.setString (5, C_FiledStr);
            CallStmet.setString (6, SqlWhere);
            CallStmet.setString (7, OrderyFiled);
            CallStmet.setString (8, OrderyType);
            CallStmet.registerOutParameter (9, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            rs = (ResultSet) CallStmet.getObject (9);
            if (rs.next ())
            {
                do
                {
                    Area_Pro pro = new Area_Pro ();
                    pro.setId (rs.getInt ("areaid"));
                    pro.setName (rs.getString ("areaname"));
                    pro.setNamepy (rs.getString ("areapy"));
                    pro.setStatus (rs.getInt ("areastatus"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else
            {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("AreaList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 小区列表
     *
     * @param Pk          是否注键,如果是,那么产生的select的语句是不一样的.
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param C_FiledStr  当不用主键排序时，C_FiledStr表示子查询查询字估
     * @param SqlWhere    查询条件,不带where
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    @Override
    public List<Area_Pro> VillageList (String Pk, String TopInt,
            String FiledStr, String TableName,
            String C_FiledStr, String SqlWhere,
            String OrderyFiled, String OrderyType)
    {
        List<Area_Pro> list = new ArrayList<Area_Pro> ();
        String CallSql = "{Call DataListCursor_Pkg_DataList.DataList(?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, Pk);
            CallStmet.setString (2, TopInt);
            CallStmet.setString (3, FiledStr);
            CallStmet.setString (4, TableName);
            CallStmet.setString (5, C_FiledStr);
            CallStmet.setString (6, SqlWhere);
            CallStmet.setString (7, OrderyFiled);
            CallStmet.setString (8, OrderyType);
            CallStmet.registerOutParameter (9, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            rs = (ResultSet) CallStmet.getObject (9);
            if (rs.next ())
            {
                do
                {
                    Area_Pro pro = new Area_Pro ();
                    pro.setId (rs.getInt ("villageid"));
                    pro.setName (rs.getString ("villagename"));
                    pro.setNamepy (rs.getString ("villagepy"));
                    pro.setStatus (rs.getInt ("villagestatus"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else
            {
                list.add (null);
            }
        }
        catch (SQLException e)
        {
            list.add (null);
            System.out.println ("VillageList" + e.getMessage ());
        }
        finally
        {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 树列表
     *
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param SqlWhere    查询条件,不带where
     * @param StartWith   树查询的start with 语句
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    @Override
    public List<Area_Pro> AreaTreeList (String TopInt, String FiledStr,
                                        String TableName, String SqlWhere,
                                        String StartWith, String OrderyFiled,
                                        String OrderyType)
    {
        List<Area_Pro> list = new ArrayList<Area_Pro> ();
        String CallSql = "{call TreeListCursor_Pkg_TreeList.TreeList(?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, TopInt);
            CallStmet.setString (2, FiledStr);
            CallStmet.setString (3, TableName);
            CallStmet.setString (4, SqlWhere);
            CallStmet.setString (5, StartWith);
            CallStmet.setString (6, OrderyFiled);
            CallStmet.setString (7, OrderyType);
            CallStmet.registerOutParameter (8, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            rs = (ResultSet) CallStmet.getObject (8);
            if (rs.next ())
            {
                do
                {
                    Area_Pro pro = new Area_Pro ();
                    pro.setId (rs.getInt ("pid"));
                    pro.setName (rs.getString ("pname"));
                    pro.setParent_id (rs.getInt ("parent_id"));
                    pro.setJb (rs.getInt ("jb"));
                    pro.setNamepy (rs.getString ("py"));
                    pro.setStatus (rs.getInt("status"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else
            {
                list.add (null);
            }
        }
        catch (SQLException e)
        {
            list.add (null);
            System.out.print ("AreaTreeList" + e.getMessage ());
        }
        finally
        {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 异步加载JSON,应用于列表或者菜单
     *
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param CityId     地区ID
     * @return String
     */
    @Override
    public String AreaTreeStr (List<Area_Pro> AreaTreeIt,
                               List<Area_Pro> AreaIdIt, int CityId)
    {
        String departmentTree = "";
        //遍历整个树，寻找根节点
        for (Area_Pro pro : AreaTreeIt)
        {
            boolean bool = true;
            if (pro != null)
            {
                departmentTree += "{\"id\":" + pro.getId () + ",\"text\":\"" + pro.getName () + "\",\"jb\":\""+pro.getJb ()+"\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                if (AreaIdIt != null)
                {
                    for (Area_Pro apro : AreaIdIt)
                    {
                        if (apro != null)
                        {
                            if (pro.getId () == apro.getId ())//要修改的省份ID
                            {
                                bool = false;
                                if (apro.getParent_id () == 0)
                                {
                                    //调用FindChild方法，开始遍历整个树，寻找当前节点的子节点。
                                    String child = FindChild (apro.getId (), AreaIdIt, CityId);
                                    if (! child.equals (""))
                                    {
                                        departmentTree += "," + child + "},";
                                    }
                                    else
                                    {
                                        departmentTree += "},";
                                    }
                                }
                            }
                        }
                    }
                }
                if (bool)
                {
                    if (pro.getJb () < 4)//省市区小区四级，如果不是4的话，表示都有下级菜单
                    {
                        departmentTree += ",\"state\":\"closed\"},";
                    }
                    else
                    {
                        departmentTree += ",\"state\":\"open\"},";
                    }
                }
            }
            else
            {
                departmentTree += "{\"id\":0,\"text\":\"\",\"jb\":\"0\",\"py\":\"\",\"status\":\"0\"},";
            }
        }
        if (! departmentTree.equals (""))
        {
            departmentTree = departmentTree.substring (0, departmentTree.length () - 1);
            departmentTree = "[" + departmentTree + "]";
        }
        return departmentTree;
    }


    /**
     * 异步加载JSON,应用于添加或修改地区
     *
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param parent_id 用户点击菜单的ID
     * @return String
     */
    public String AreaTreeCom (List<Area_Pro> AreaTreeIt,
                               List<Area_Pro> AreaIdIt,
                               String parent_id)
    {
        //{"id":"0,0","text":"顶级地区","py":"","status":"0","state":"open"},
        String departmentTree = "";
        if(checkStr.isNull (parent_id))
        {
            departmentTree="{\"id\":\"0,0\",\"text\":\"顶级地区\",\"py\":\"\",\"status\":\"0\",\"state\":\"open\"},";
        }
        //遍历整个树，寻找根节点
        for (Area_Pro pro : AreaTreeIt)
        {
            boolean bool = true;
            if (pro != null)
            {
                departmentTree += "{\"id\":\"" + pro.getId ()+","+pro.getJb () + "\",\"text\":\"" + pro.getName () + "\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                if (AreaIdIt != null)
                {
                    for (Area_Pro apro : AreaIdIt)
                    {
                        if (apro != null)
                        {
                            if (pro.getId () == apro.getId ())//要修改的省份ID
                            {
                                bool = false;
                                if (apro.getParent_id () == 0)
                                {
                                    departmentTree +=","+"\"state\":\"open\"";
                                    //调用FindChild方法，开始遍历整个树，寻找当前节点的子节点。
                                    String child = FindChildCom (apro.getId (), AreaIdIt);
                                    if (! child.equals (""))
                                        departmentTree += "," + child + "},";
                                    else
                                        departmentTree += "},";
                                }
                            }
                        }
                    }
                }
                if (bool)
                {
                    if (pro.getJb () < 2)//省市区三级，如果不是1的话，表示都有下级菜单
                        departmentTree += ",\"state\":\"closed\"},";
                    else
                        departmentTree += ",\"state\":\"open\"},";
                }
            }
            else
            {
                departmentTree += "{\"id\":\"-1,-1,-1\",\"text\":\"\",\"py\":\"\",\"status\":\"0\"},";
            }
        }
        if (! departmentTree.equals (""))
        {
            departmentTree = departmentTree.substring (0, departmentTree.length () - 1);
            departmentTree = "[" + departmentTree + "]";
        }
        return departmentTree;
    }

    /**
     * 异步加载JSON,应用于地区修改
     *
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param CityId  地区ID
     * @return String
     */
    @Override
    public String AreaTreeEdit (List<Area_Pro> AreaTreeIt, List<Area_Pro> AreaIdIt, int CityId)
    {
        String departmentTree = "";
        //遍历整个树，寻找根节点
        for (Area_Pro pro : AreaTreeIt)
        {
            boolean bool = true;
            if (pro != null)
            {
                departmentTree += "{\"id\":" + pro.getId () + ",\"text\":\"" + pro.getName () + "\",\"jb\":\""+pro.getJb ()+"\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                if (AreaIdIt != null)
                {
                    for (Area_Pro apro : AreaIdIt)
                    {
                        if (apro != null)
                        {
                            if (pro.getId () == apro.getId ())//要修改的省份ID
                            {
                                bool = false;
                                if (apro.getParent_id () == 0)
                                {
                                    //调用FindChildEdit方法，开始遍历整个树，寻找当前节点的子节点。
                                    String child = FindChildEdit (apro.getId (), AreaIdIt, CityId);
                                    if (! child.equals (""))
                                    {
                                        departmentTree += "," + child + "},";
                                    }
                                    else
                                    {
                                        departmentTree += "},";
                                    }
                                }
                            }
                        }
                    }
                }
                if (bool)
                {
                    if (pro.getJb () < 3)//省市区小区3级，如果不是3的话，表示都有下级菜单
                    {
                        departmentTree += ",\"state\":\"closed\"},";
                    }
                    else
                    {
                        departmentTree += ",\"state\":\"open\"},";
                    }
                }
            }
            else
            {
                departmentTree += "{\"id\":0,\"text\":\"\",\"jb\":\"0\",\"py\":\"\",\"status\":\"0\"},";
            }
        }
        if (! departmentTree.equals (""))
        {
            departmentTree = departmentTree.substring (0, departmentTree.length () - 1);
            departmentTree = "[" + departmentTree + "]";
        }
        return departmentTree;
    }

    /**
     * 查找子节点
     *
     * @param Id       父类ID
     * @param AreaIdIt 树形封装List对象
     * @param CityId   地区ID
     * @return 子类JSON
     */
    private static String FindChild (int Id, List<Area_Pro> AreaIdIt, int CityId)
    {
        Boolean flag = false;
        String departmentChild = "";
        for (Area_Pro pro : AreaIdIt) {
            String anotherChild = "";
            if (pro.getParent_id () == Id)//寻找到子节点
            {
                anotherChild = "\"id\":\"" + pro.getId () + "\",\"text\":\"" + pro.getName () + "\",\"jb\":\""+pro.getJb ()+"\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                if (pro.getId () != CityId)
                {
                    if (pro.getJb () < 4)
                    {
                        anotherChild += ",\"state\":\"closed\"";
                    }
                }
                String child = FindChild (pro.getId (), AreaIdIt, CityId);
                if (! child.equals (""))
                {
                    //anotherChild += ",\"state\":\"closed\"";
                    anotherChild = anotherChild + "," + child;
                }
                else {
                    anotherChild = anotherChild + child;
                }
                //if (anotherChild.substring (0,anotherChild.length () - 1) .contains (","))
                //{
                //anotherChild = anotherChild.substring (anotherChild.length() - 1);
                //}
                anotherChild = "{" + anotherChild + "}"; departmentChild += anotherChild + ",";
            }
            else {
                flag = false;
            }
        }
        if (! departmentChild.equals ("")) {
            departmentChild = departmentChild.substring (0, departmentChild.length () - 1);
            departmentChild = "\"children\":[" + departmentChild + "]";
        }
        return departmentChild;
    }


    /**
     * 查找子节点
     *
     * @param Id       父类ID
     * @param AreaIdIt 树形封装List对象
     * @return 子类JSON
     */
    private static String FindChildCom (int Id, List<Area_Pro> AreaIdIt)
    {
        Boolean flag = false;
        String departmentChild = "";
        for (Area_Pro pro : AreaIdIt) {
            String anotherChild = "";
            if (pro.getParent_id () == Id)//寻找到子节点
            {
                anotherChild = "\"id\":\"" +  pro.getId ()+","+pro.getJb () + "\",\"text\":\"" + pro.getName () + "\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                /*if (pro.getId () != CityId)
                {
                    if (pro.getJb () < 2)
                    {
                        anotherChild += ",\"state\":\"closed\"";
                    }
                }*/
                String child = FindChildCom (pro.getId (), AreaIdIt);
                if (! child.equals (""))
                    //anotherChild += ",\"state\":\"closed\"";
                    anotherChild = anotherChild + "," + child;
                else
                    anotherChild = anotherChild + child;
                //if (anotherChild.substring (0,anotherChild.length () - 1) .contains (","))
                //{
                //anotherChild = anotherChild.substring (anotherChild.length() - 1);
                //}
                anotherChild = "{" + anotherChild + "}";
                departmentChild += anotherChild + ",";
            }
            else {
                flag = false;
            }
        }
        if (! departmentChild.equals ("")) {
            departmentChild = departmentChild.substring (0, departmentChild.length () - 1);
            departmentChild = "\"children\":[" + departmentChild + "]";
        }
        return departmentChild;
    }


    /**
     * 查找子节点,只查找到省市区三级
     *
     * @param Id       父类ID
     * @param AreaIdIt 树形封装List对象
     * @param CityId   地区ID
     * @return 子类JSON
     */
    private static String FindChildEdit (int Id, List<Area_Pro> AreaIdIt, int CityId)
    {
        Boolean flag = false;
        String departmentChild = "";
        for (Area_Pro pro : AreaIdIt) {
            String anotherChild = "";
            if (pro.getParent_id () == Id)//寻找到子节点
            {
                anotherChild = "\"id\":\"" + pro.getId () + "\",\"text\":\"" + pro.getName () + "\",\"jb\":\""+pro.getJb ()+"\",\"py\":\""+pro.getNamepy ()+"\",\"status\":\""+pro.getStatus ()+"\"";
                if (pro.getId () != CityId)
                {
                    if (pro.getJb () < 3)
                    {
                        anotherChild += ",\"state\":\"closed\"";
                    }
                }
                String child = FindChildEdit (pro.getId (), AreaIdIt, CityId);
                if (! child.equals (""))
                {
                    //anotherChild += ",\"state\":\"closed\"";
                    anotherChild = anotherChild + "," + child;
                }
                else {
                    anotherChild = anotherChild + child;
                }
                //if (anotherChild.substring (0,anotherChild.length () - 1) .contains (","))
                //{
                //anotherChild = anotherChild.substring (anotherChild.length() - 1);
                //}
                anotherChild = "{" + anotherChild + "}";
                departmentChild += anotherChild + ",";
            }
            else {
                flag = false;
            }
        }
        if (! departmentChild.equals (""))
        {
            departmentChild = departmentChild.substring (0, departmentChild.length () - 1);
            departmentChild = "\"children\":[" + departmentChild + "]";
        }
        return departmentChild;
    }
}
