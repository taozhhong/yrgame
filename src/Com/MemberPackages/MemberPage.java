package Com.MemberPackages;

import Com.DbConPackages.linkdb_call;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public class MemberPage implements MemberPage_Interface
{
    private static int TotalRecord = 0, TotalPage = 0;
    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;


    public MemberPage()
    {
        conn=new linkdb_call();
    }

    @Override
    public int getTotalPage()
    {
        return TotalPage;
    }

    /**
     * 返回总记录数
     *
     * @return int
     */
    @Override
    public int getTotalRecord()
    {
        return TotalRecord;
    }

    /**
     * 分页
     *
     * @param FieldStr   --字段名
     * @param TableName  --表名,支持多表查询
     * @param SqlWhere   --查询条件,要带and
     * @param OrderField --排序字段
     * @param OrderType  --排序方式
     * @param NowPape    --当前页
     * @param PageSize   --每页条数
     * @return List<Member_Pro>
     */
    @Override
    public List<Member_Pro> MemberPageList(String FieldStr, String TableName, String SqlWhere, String OrderField, String OrderType, int NowPape, int PageSize)
    {
        List<Member_Pro> list = new ArrayList<Member_Pro>();
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
                do
                {
                    Member_Pro pro = new Member_Pro ();
                    pro.setMemberid(rs.getInt("memberid"));
                    pro.setLoginname(rs.getString("loginname"));
                    pro.setNick(rs.getString("nick"));
                    pro.setTjmemberid(rs.getInt("tjmemberid"));
                    pro.setRealname(rs.getString("realname"));
                    pro.setSex(rs.getInt("sex"));
                    pro.setBirdate(rs.getString("birdate"));
                    pro.setTel(rs.getString("tel"));
                    pro.setQq(rs.getString("qq"));
                    pro.setEmail(rs.getString("email"));
                    pro.setIdnum(rs.getString("idnum"));
                    pro.setHeadimg(rs.getString("headimg"));
                    pro.setNote(rs.getString("note"));
                    pro.setStatus(rs.getInt("status"));
                    pro.setRegip(rs.getString("regip"));
                    pro.setRegtime(rs.getString("regtime"));
                    list.add(pro);
                }
                while (rs.next ());
            }
            else {
                list.add (null);
            }
        }
        catch (SQLException e)
        {
            list.add (null);
            System.out.println ("BankPageList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
