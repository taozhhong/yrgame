package Com.SysSetPackages.UserSetPackages.UserPackages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午9:17
 * To change this template use File | Settings | File Templates.
 */
public interface UserPage_Interface {
    /*
    * 返回分页的总页数
    * @return int
    */
    public int getTotalPage();


    /**
     * 返回总记录数
     *
     * @return int
     */
    public int getTotalRecord();

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
    public List<User_Pro> UserPageList(String FieldStr, String TableName, String SqlWhere, String OrderField, String
            OrderType, int NowPape, int PageSize);


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
    public List<User_Pro> MgUserPageList(String FieldStr, String TableName, String SqlWhere, String OrderField,
                                         String OrderType, int NowPape, int PageSize);
}
