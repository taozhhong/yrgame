package Com.MemberPackages;

import java.lang.reflect.Member;
import java.util.List;

/**
 * Created by Administrator on 2015/9/14.
 */
public interface MemberPage_Interface
{
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
     * @return List<Member_Pro>
     */
    public List<Member_Pro> MemberPageList(String FieldStr, String TableName,
                                       String SqlWhere, String OrderField,
                                       String OrderType, int NowPape,
                                       int PageSize);
}
