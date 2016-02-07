package Com.DbConPackages;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-9-30
 * Time: 16:17:35
 */
public class linkdb_call {
    private CallableStatement CallStmet = null;

    private jdbc con = null;

    private Connection conn = null;

    public linkdb_call () {
        con = new jdbc (); conn = con.getPool ();
    }

    public CallableStatement callableStatement (String sql) {
        try {
            CallStmet = conn.prepareCall (sql);
        }
        catch (SQLException e) {
            System.out.println ("调用存储过程" + e.getMessage ());
        } return CallStmet;
    }

    public void CloseConn () {
        try {
            if (conn != null) {
                conn.close (); conn = null;
            }
        }
        catch (SQLException e) {
            System.out.println ("CallStmet:conn" + e.getMessage ());
        }
    }

    public void CloseRs (ResultSet rs) {
        try
        {
            if (rs != null)
            {
                rs.close ();
                rs = null;
            }
        }
        catch (SQLException e)
        {
            System.out.println ("CallStmet:闭关记录集" + e.getMessage ());
        }
    }

    public void clearParameters () {
        try {
            if (CallStmet != null) {
                CallStmet.clearParameters ();
            }
        }
        catch (SQLException e) {
            System.out.println ("CallStmet:关闭stmet时" + e.getMessage ());
        }
    }

    public void setAutoCommit (boolean bool) {
        try {
            conn.setAutoCommit (bool);
        }
        catch (SQLException e) {
            System.out.println ("setAutoCommit:" + e.getMessage ());
        }
    }

    public void Commit () {
        try {
            conn.commit ();
        }
        catch (SQLException e) {
            System.out.println ("Commit:" + e.getMessage ());
        }
    }

    public void Rollback () {
        try {
            conn.rollback ();
        }
        catch (SQLException e) {
            System.out.println ("Rollback:" + e.getMessage ());
        }
    }
}
