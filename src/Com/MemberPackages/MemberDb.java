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
public class MemberDb implements MemberDb_Interface
{
    private linkdb_call conn = null;
    private CallableStatement CallStmet = null;
    private ResultSet rs = null;

    public MemberDb()
    {
        conn=new linkdb_call();
    }

    /**
     * 注册会员增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,edit-修改操作)
     * @param Pro      表money_scale中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    @Override
    public int MemberAction(String MarkStr, Member_Pro Pro, String SqlWhere)
    {
        int backint = 0;
        String CallSql = "{call Member_Sp_Action(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        try {
            CallStmet = conn.callableStatement(CallSql);
            CallStmet.setString (1, MarkStr);
            CallStmet.setInt(2, Pro.getMemberid());
            CallStmet.setString(3, Pro.getLoginname());
            CallStmet.setString(4, Pro.getLoginpwd());
            CallStmet.setString(5, Pro.getNick());
            CallStmet.setString(6, Pro.getBankpwd());
            CallStmet.setInt(7, Pro.getTjmemberid());
            CallStmet.setString(8, Pro.getRealname());
            CallStmet.setInt(9, Pro.getSex());
            CallStmet.setString(10, Pro.getBirdate());
            CallStmet.setString(11,Pro.getTel());
            CallStmet.setString(12,Pro.getQq());
            CallStmet.setString(13,Pro.getEmail());
            CallStmet.setString(14,Pro.getIdnum());
            CallStmet.setString(15,Pro.getHeadimg());
            CallStmet.setString(16,Pro.getNote());
            CallStmet.setInt(17, Pro.getStatus());
            CallStmet.setString(18,Pro.getRegip());
            CallStmet.setString (19, SqlWhere);
            CallStmet.registerOutParameter(20, OracleTypes.INTEGER);
            CallStmet.executeUpdate ();
            backint = CallStmet.getInt (20);
        }
        catch (SQLException e) {
            System.out.println ("MemberAction" + e.getMessage ());
        }
        finally {
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return backint;
    }

    /**
     * 会员列表
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
    public List<Member_Pro> MemberList(String Pk, String TopInt, String FiledStr,
                                       String TableName, String C_FiledStr,
                                       String SqlWhere, String OrderyFiled,
                                       String OrderyType)
    {
        List<Member_Pro> list = new ArrayList<Member_Pro>();
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
            System.out.println ("MemberList" + e.getMessage ());
        }
        finally {
            conn.CloseRs (rs);
            conn.clearParameters ();
            conn.CloseConn ();
        }
        return list;
    }
}
