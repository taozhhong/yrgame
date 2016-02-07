package Com.SysSetPackages.UserSetPackages.UserPackages;

import Com.DbConPackages.linkdb_call;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午9:16
 * To change this template use File | Settings | File Templates.
 */
public class UserDb implements UserDb_Interface
{
    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;

    public UserDb ()
    {
        conn = new linkdb_call ();
    }

    /**
     * 对表nlsp_manager_user增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,up-修改操作)
     * @param Pro      表nlsp_manager_user中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    @Override
    public int UserAction (String MarkStr, User_Pro Pro, String SqlWhere)
    {
        int backint = 0;
        String CallSql = "{call Store_Sp_Action(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, MarkStr);
            CallStmet.setInt(2, Pro.getStoreid());
            CallStmet.setString (3, Pro.getStorename());
            CallStmet.setString (4, Pro.getStorepy());
            CallStmet.setString (5, Pro.getDirector());
            CallStmet.setInt(6, Pro.getSex());
            CallStmet.setString(7, Pro.getBirdate());
            CallStmet.setInt(8, Pro.getEducation());
            CallStmet.setString(9, Pro.getTelnum());
            CallStmet.setString(10, Pro.getQq());
            CallStmet.setString(11, Pro.getEmail());
            CallStmet.setInt(12, Pro.getAreaid());
            CallStmet.setString(13, Pro.getNodes());
            CallStmet.setInt(14, Pro.getStatus());
            CallStmet.setFloat(15, Pro.getDivide());
            CallStmet.setString(16, Pro.getRegip());
            CallStmet.setString (17, SqlWhere);
            CallStmet.registerOutParameter (18, OracleTypes.INTEGER);
            CallStmet.executeUpdate ();
            backint = CallStmet.getInt (18);
        }
        catch (SQLException e) {
            System.out.println ("UserAction" + e.getMessage ());
        }
        finally {
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return backint;
    }

    /**
     * 对表manger_user增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,up-修改操作)
     * @param Pro      表manger_user中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    @Override
    public int MgUserAction(String MarkStr, User_Pro Pro, String SqlWhere)
    {
        int backint = 0;
        String CallSql = "{call Manage_User_Sp_Action(?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, MarkStr);
            CallStmet.setInt(2, Pro.getUserid());
            CallStmet.setInt(3, Pro.getNuserid());
            CallStmet.setString (4, Pro.getLoginname());
            CallStmet.setString (5, Pro.getLoginpasd());
            CallStmet.setInt(6, Pro.getRoteid());
            CallStmet.setInt(7, Pro.getIslock());
            CallStmet.setInt(8, Pro.getIsprive());
            CallStmet.setString(9, Pro.getLoginip());
            CallStmet.setString(10, Pro.getLastloginip());
            CallStmet.setString (11, SqlWhere);
            CallStmet.registerOutParameter (12, OracleTypes.INTEGER);
            CallStmet.executeUpdate ();
            backint = CallStmet.getInt (12);
        }
        catch (SQLException e) {
            System.out.println ("MgUserAction" + e.getMessage ());
        }
        finally {
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return backint;
    }

    /**
     * 判断用户登录列表
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
    public List<User_Pro> UserLoginList (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
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
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setUserid (rs.getInt ("userid"));
                    pro.setLoginname (rs.getString ("loginname"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("UserLoginList" + e.getMessage ());
        }
        finally {
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
    public List<User_Pro> UserList (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
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
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setStoreid(rs.getInt("storeid"));
                    pro.setStorename(rs.getString("storename"));
                    pro.setDirector(rs.getString("director"));
                    pro.setSex(rs.getInt("sex"));
                    pro.setBirdate(rs.getString("birdate"));
                    pro.setEducation(rs.getInt("education"));
                    pro.setTelnum(rs.getString("tel"));
                    pro.setQq(rs.getString("qq"));
                    pro.setEmail(rs.getString("email"));
                    pro.setAreaid(rs.getInt("areaid"));
                    pro.setNodes(rs.getString("note"));
                    pro.setStatus(rs.getInt("status"));
                    pro.setDivide(rs.getFloat("divide"));
                    pro.setRegip(rs.getString("regip"));
                    pro.setRegtime(rs.getString("regtime"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("UserList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 部门，权限，用户表联合查询列表
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
    public List<User_Pro> UserUnList (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
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
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setUserid (rs.getInt ("userid"));
                    pro.setLoginname(rs.getString("loginname"));
                    pro.setStorename(rs.getString("storename"));
                    pro.setLogintime(rs.getString("logintime"));
                    pro.setLoginip(rs.getString("loginip"));
                    pro.setIsprive(rs.getInt("isprive"));
                    pro.setBmkid(rs.getString("bmkid"));
                    pro.setSmkid(rs.getString("smkid"));
                    pro.setOptid(rs.getString("optid"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("UserUnList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * province p,city c,area a depart d,person u 联合查询列表
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
    public List<User_Pro> User_P_C_A_D_List (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
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
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setUserid(rs.getInt("userid"));
                    pro.setStorename(rs.getString("storename"));
                    pro.setStorepy(rs.getString("storepy"));
                    pro.setSex(rs.getInt("sex"));
                    pro.setBirdate(rs.getString("birdate"));
                    pro.setEducation(rs.getInt("education"));
                    pro.setTelnum(rs.getString("tel"));
                    pro.setQq(rs.getString("qq"));
                    pro.setEmail(rs.getString("email"));
                    pro.setProvinceid(rs.getInt("provinceid"));
                    pro.setCityid(rs.getInt("cityid"));
                    pro.setAreaid(rs.getInt("areaid"));
                    pro.setNodes(rs.getString("note"));
                    pro.setStatus(rs.getInt("status"));
                    pro.setRegip(rs.getString("regip"));
                    pro.setRegtime(rs.getString("regtime"));
                    pro.setProvincename(rs.getString("provincename"));
                    pro.setCityname(rs.getString("cityname"));
                    pro.setAreaname(rs.getString("areaname"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("User_P_C_A_D_List" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 管理员列表
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
    public List<User_Pro> MgUserList (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
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
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setUserid(rs.getInt("userid"));
                    pro.setLoginname(rs.getString("loginname"));
                    pro.setRoteid(rs.getInt("roteid"));
                    pro.setIslock(rs.getInt("islock"));
                    pro.setIsprive(rs.getInt("isprive"));
                    list.add (pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e) {
            list.add (null);
            System.out.println ("MgUserList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
