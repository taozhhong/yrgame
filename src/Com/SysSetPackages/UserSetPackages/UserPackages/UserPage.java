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
 * Time: 下午9:18
 * To change this template use File | Settings | File Templates.
 */
public class UserPage implements UserPage_Interface
{
    private static int TotalRecord = 0, TotalPage = 0;
    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;

    public UserPage () {
        conn = new linkdb_call ();
    }

    @Override
    public int getTotalPage () {
        return TotalPage;
    }

    /**
     * 返回总记录数
     *
     * @return int
     */
    @Override
    public int getTotalRecord () {
        return TotalRecord;
    }

    /**
     * 分页
     *
     * @param FieldStr--字段名
     * @param TableName--表名,支持多表查询
     * @param SqlWhere--查询条件,要带and
     * @param OrderField--排序字段
     * @param OrderType--排序方式
     * @param NowPape--当前页
     * @param PageSize--每页条数
     * @return List<User_Pro>
     */
    @Override
    public List<User_Pro> UserPageList (String FieldStr, String TableName, String SqlWhere, String OrderField, String OrderType, int NowPape, int PageSize) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
        String CallSql = "{call DataPageCursor.DataPage(?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, FieldStr);
            CallStmet.setString (2, TableName);
            CallStmet.setString (3, SqlWhere);
            CallStmet.setString (4, OrderField);
            CallStmet.setString (5, OrderType);
            CallStmet.setInt (6, NowPape);
            CallStmet.setInt (7, PageSize);
            CallStmet.registerOutParameter (8, OracleTypes.INTEGER);
            CallStmet.registerOutParameter (9, OracleTypes.INTEGER);
            CallStmet.registerOutParameter (10, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            TotalRecord = CallStmet.getInt (8);//总记录数
            TotalPage = CallStmet.getInt (9);//总页数
            rs = (ResultSet) CallStmet.getObject (10);
            if (rs.next ())
            {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setStoreid(rs.getInt("storeid"));
                    pro.setStorename(rs.getString("storename"));
                    pro.setDirector(rs.getString("director"));
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
                    pro.setDivide(rs.getFloat("divide"));
                    pro.setRegip(rs.getString("regip"));
                    pro.setRegtime(rs.getString ("regtime"));
                    pro.setProvincename(rs.getString("provincename"));
                    pro.setCityname(rs.getString("cityname"));
                    pro.setAreaname(rs.getString("areaname"));
                    pro.setIsmg (rs.getInt ("ismg"));
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
            System.out.println ("UserPageList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }

    /**
     * 管理用户分页
     *
     * @param FieldStr--字段名
     * @param TableName--表名,支持多表查询
     * @param SqlWhere--查询条件,要带and
     * @param OrderField--排序字段
     * @param OrderType--排序方式
     * @param NowPape--当前页
     * @param PageSize--每页条数
     * @return List<User_Pro>
     */
    @Override
    public List<User_Pro> MgUserPageList (String FieldStr, String TableName, String SqlWhere, String OrderField, String OrderType, int NowPape, int PageSize) {
        List<User_Pro> list = new ArrayList<User_Pro> ();
        String CallSql = "{call DataPageCursor.DataPage(?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement (CallSql);
            CallStmet.setString (1, FieldStr);
            CallStmet.setString (2, TableName);
            CallStmet.setString (3, SqlWhere);
            CallStmet.setString (4, OrderField);
            CallStmet.setString (5, OrderType);
            CallStmet.setInt (6, NowPape);
            CallStmet.setInt (7, PageSize);
            CallStmet.registerOutParameter (8, OracleTypes.INTEGER);
            CallStmet.registerOutParameter (9, OracleTypes.INTEGER);
            CallStmet.registerOutParameter (10, OracleTypes.CURSOR);
            CallStmet.executeQuery ();
            TotalRecord = CallStmet.getInt (8);//总记录数
            TotalPage = CallStmet.getInt (9);//总页数
            rs = (ResultSet) CallStmet.getObject (10);
            if (rs.next ()) {
                do {
                    User_Pro pro = new User_Pro ();
                    pro.setUserid(rs.getInt("userid"));
                    pro.setLoginname(rs.getString("loginname"));
                    pro.setIslock(rs.getInt("islock"));
                    pro.setIsprive(rs.getInt("isprive"));
                    pro.setLogintime(rs.getString("logintime"));
                    pro.setLoginip(rs.getString("loginip"));
                    pro.setLastlogintime(rs.getString("lastlogintime"));
                    pro.setLastloginip(rs.getString("lastloginip"));
                    pro.setRegtime(rs.getString("regtime"));
                    pro.setRotename(rs.getString("rotename"));
                    pro.setStorename(rs.getString("storename"));
                    pro.setDirector(rs.getString("director"));
                    pro.setSex(rs.getInt("sex"));
                    pro.setTelnum(rs.getString("tel"));
                    pro.setStatus(rs.getInt("status"));
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
            System.out.println ("MgUserPageList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
