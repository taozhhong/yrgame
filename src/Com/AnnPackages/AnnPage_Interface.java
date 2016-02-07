package Com.AnnPackages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: T410
 * Date: 14-4-10
 * Time: 下午7:54
 * To change this template use File | Settings | File Templates.
 */
public interface AnnPage_Interface
{
    /*
    * 返回分页的总页数
    * @return int
    */
    public int getTotalPage();


    /**
     * 返回总记录数
     * @return int
     */
    public int getTotalRecord();



    /**
     * 系统公告分页
     * @param FieldStr--字段名
     * @param TableName--表名,支持多表查询
     * @param SqlWhere--查询条件,要带and
     * @param OrderField--排序字段
     * @param OrderType--排序方式
     * @param NowPape--当前页
     * @param PageSize--每页条数
     * @return List<Ann_Pro>
     */
    public List<Ann_Pro> SysAnnPageList(String FieldStr, String TableName,
                                        String SqlWhere, String OrderField,
                                        String OrderType, int NowPape,
                                        int PageSize);



}
