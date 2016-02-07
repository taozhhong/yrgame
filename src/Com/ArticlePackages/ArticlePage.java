package Com.ArticlePackages;

import Com.DbConPackages.linkdb_call;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:11
 * To change this template use File | Settings | File Templates.
 */
public class ArticlePage implements ArticlePage_Interface
{
    private static int TotalRecord = 0, TotalPage = 0;
    private linkdb_call       conn      = null;
    private CallableStatement CallStmet = null;
    private ResultSet         rs        = null;

    public ArticlePage()
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
     * @param FieldStr--字段名
     * @param TableName--表名,支持多表查询
     * @param SqlWhere--查询条件,要带and
     * @param OrderField--排序字段
     * @param OrderType--排序方式
     * @param NowPape--当前页
     * @param PageSize--每页条数
     * @return List<Article_Pro>
     */
    @Override
    public List<Article_Pro> ArticlePageList(String FieldStr, String TableName, String SqlWhere, String OrderField,
                                             String OrderType, int NowPape, int PageSize)
    {
        List<Article_Pro> list = new ArrayList<Article_Pro>();
        String CallSql = "{call DataPageCursor.DataPage(?,?,?,?,?,?,?,?,?,?)}";
        try
        {
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
                    Article_Pro pro = new Article_Pro ();
                    pro.setArticleid (rs.getInt ("articleid"));
                    pro.setCateid (rs.getInt ("cateid"));
                    pro.setArticletitle (rs.getString ("articletitle"));
                    pro.setContent(rs.getString("content"));
                    pro.setIslock(rs.getInt("islock"));
                    pro.setStatus(rs.getInt("status"));
                    pro.setAddtime(rs.getString("addtime"));
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
            System.out.println ("ArticlePageList" + e.getMessage ());
        }
        finally
        {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
