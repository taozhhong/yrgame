package Com.SysSetPackages.RoteSetPackages;

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
 * Time: 下午8:23
 * To change this template use File | Settings | File Templates.
 */
public class RotePage implements RotePage_Interface {
    private static int TotalRecord = 0, TotalPage = 0;
    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;

    public RotePage () {
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
     * @return List<Rote_Pro>
     */
    @Override
    public List<Rote_Pro> RotePageList (String FieldStr, String TableName, String SqlWhere, String OrderField, String OrderType, int NowPape, int PageSize) {
        List<Rote_Pro> list = new ArrayList<Rote_Pro> ();
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
            TotalRecord = CallStmet.getInt (8);//总记录数 TotalPage = CallStmet.getInt (9);//总页数
            rs = (ResultSet) CallStmet.getObject (10);
            if (rs.next ()) {
                do {
                    Rote_Pro pro = new Rote_Pro ();
                    pro.setRoteid (rs.getInt ("roteid"));
                    pro.setRotename (rs.getString ("rotename"));
                    pro.setB_mkid (rs.getString ("b_mkid"));
                    pro.setS_mkid (rs.getString ("s_mkid"));
                    pro.setOptid (rs.getString ("optid"));
                    pro.setRemark (rs.getString("remark"));
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
            System.out.println ("RotePageList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
