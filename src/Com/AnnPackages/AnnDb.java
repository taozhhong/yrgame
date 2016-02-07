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
 * Time: 下午7:54
 * To change this template use File | Settings | File Templates.
 */
public class AnnDb implements AnnDb_Interface
{
    private linkdb_call       conn      = null;
    private CallableStatement CallStmet = null;
    private ResultSet         rs        = null;

    public AnnDb ()
    {
        conn=new linkdb_call ();
    }



    /**
     * 对表sysann增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,edit-修改操作)
     * @param Pro      表Seller_Pro中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    @Override
    public int SysAnnDb (String MarkStr, Ann_Pro Pro, String SqlWhere)
    {
        int backint=0;
        String CallSql="{call Sysann_Sp_Action(?,?,?,?,?,?,?)}";
        try
        {
            CallStmet=conn.callableStatement(CallSql);
            CallStmet.setString(1,MarkStr);
            CallStmet.setInt (2, Pro.getAnnid ());
            CallStmet.setString (3, Pro.getAnntitle ());
            CallStmet.setString (4, Pro.getAnncon ());
            CallStmet.setInt (5, Pro.getIslock ());
            CallStmet.setString(6,SqlWhere);
            CallStmet.registerOutParameter(7, OracleTypes.INTEGER);
            CallStmet.executeUpdate();
            backint=CallStmet.getInt(7);
        }
        catch(SQLException e)
        {
            System.out.println("SysAnnDb"+e.getMessage());
        }
        finally
        {
            conn.clearParameters();
            conn.CloseConn();
        }
        return backint;
    }



    /**
     * 系统公告列表
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
    public List<Ann_Pro> SysAnnList (String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType)
    {
        List<Ann_Pro> list=new ArrayList<Ann_Pro> ();
        String CallSql="{Call DataListCursor_Pkg_DataList.DataList(?,?,?,?,?,?,?,?,?)}";
        try
        {
            CallStmet=conn.callableStatement(CallSql);
            CallStmet.setString(1,Pk);
            CallStmet.setString(2,TopInt);
            CallStmet.setString(3,FiledStr);
            CallStmet.setString(4,TableName);
            CallStmet.setString(5,C_FiledStr);
            CallStmet.setString(6,SqlWhere);
            CallStmet.setString(7,OrderyFiled);
            CallStmet.setString(8,OrderyType);
            CallStmet.registerOutParameter(9,OracleTypes.CURSOR);
            CallStmet.executeQuery();
            rs=(ResultSet)CallStmet.getObject(9);
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
            System.out.println("SysAnnList"+e.getMessage());
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
