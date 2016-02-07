package Com.DbConPackages;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Future;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.tomcat.jdbc.pool.DataSource;
/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-9-30
 * Time: 16:16:57
 */
public class jdbc {
    private static DataSource pool = null;
    private Connection conn = null;
    public jdbc () {

        try {
            Context initCtx = new InitialContext ();
            Context envCtx = (Context) initCtx.lookup ("java:comp/env");
            pool = ((DataSource) envCtx.lookup ("jdbc/yrgame"));
            if (pool == null) System.err.println ("'DBPool' is an unknown DataSource");
        }
        catch (NamingException e) {
            System.out.println ("pool " + e.getMessage ());
        }
        /*super();
        try {
            //获取数据源
            pool = getInstance();

            //连接池同步
            Future<Connection> future = pool.getConnectionAsync();
            while (!future.isDone()) {
                // 等待连接池同步
                Thread.sleep(10);
            }

            // 获取连接池
            conn = future.get();
        } catch (Exception e) {
            System.out.println ("pool " + e.getMessage ());
        }*/
    }

    /**
     * 单例模式获取数据源
     * @return
     * @throws javax.naming.NamingException
     */
    private DataSource getInstance() throws NamingException {
        if (pool == null) {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            pool = (DataSource) envContext.lookup("jdbc/dsgame");
        }
        return pool;
    }

    public Connection getPool () {
        try {
            conn = pool.getConnection ();
        }
        catch (SQLException e) {
            System.out.print ("jdbcDataSource " + e.getMessage ());
        } return conn;
    }
}
