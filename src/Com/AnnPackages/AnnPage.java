package Com.AnnPackages;

import Com.DbConPackages.linkdb_call;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-10
 * Time: 下午7:55
 * To change this template use File | Settings | File Templates.
 */
public class AnnPage implements AnnPage_Interface
{
    private static int TotalRecord=0, TotalPage = 0;
    private linkdb_call       conn      = null;
    private CallableStatement CallStmet = null;
    private ResultSet         rs        = null;

    public AnnPage ()
    {
        conn=new linkdb_call ();
    }

    @Override
    public int getTotalPage ()
    {
        return TotalPage;
    }

    /**
     * 返回总记录数
     *
     * @return int
     */
    @Override
    public int getTotalRecord ()
    {
        return TotalRecord;
    }



    /**
     * 系统公告分页
     *
     * @param FieldStr--字段名
     * @param TableName--表名,支持多表查询
     * @param SqlWhere--查询条件,要带and
     * @param OrderField--排序字段
     * @param OrderType--排序方式
     * @param NowPape--当前页
     * @param PageSize--每页条数
     * @return List<Ann_Pro>
     */
    @Override
    public List<Ann_Pro> SysAnnPageList (String FieldStr, String TableName, String SqlWhere, String OrderField, String OrderType, int NowPape, int PageSize)
    {
        List<Ann_Pro> list=new ArrayList<Ann_Pro> ();
        String CallSql="{call DataPageCursor.DataPage(?,?,?,?,?,?,?,?,?,?)}";
        try
        {
            CallStmet=conn.callableStatement(CallSql);
            CallStmet.setString(1,FieldStr);
            CallStmet.setString(2,TableName);
            CallStmet.setString(3,SqlWhere);
            CallStmet.setString(4,OrderField);
            CallStmet.setString(5,OrderType);
            CallStmet.setInt(6,NowPape);
            CallStmet.setInt(7,PageSize);
            CallStmet.registerOutParameter(8, OracleTypes.INTEGER);
            CallStmet.registerOutParameter(9, OracleTypes.INTEGER);
            CallStmet.registerOutParameter(10, OracleTypes.CURSOR);
            CallStmet.executeQuery();
            TotalRecord=CallStmet.getInt(8);//总记录数
            TotalPage=CallStmet.getInt(9);//总页数
            rs=(ResultSet)CallStmet.getObject(10);
            if(rs.next())
            {
                do
                {
                    Ann_Pro pro=new Ann_Pro();
                    pro.setAnnid (rs.getInt("sysannid"));
                    pro.setAnntitle (rs.getString ("sysanntitle"));
                    pro.setAnncon (rs.getString ("sysanncon"));
                    pro.setIslock (rs.getInt ("islock"));
                    pro.setAnndate (rs.getString("sysanndate"));
                    list.add (pro);
                }
                while (rs.next());
            }
            else
            {
                list.add(null);
            }
        }
        catch(SQLException e)
        {
            list.add(null);
            System.out.println("SysAnnPageList"+e.getMessage());
        }
        finally
        {
            conn.CloseRs(rs);
            conn.clearParameters();
            conn.CloseConn();
        }
        return list;
    }


}
