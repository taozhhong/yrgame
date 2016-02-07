package Com.ArticlePackages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:10
 * To change this template use File | Settings | File Templates.
 */
public interface ArticlePage_Interface
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
     * @return List<Article_Pro>
     */
    public List<Article_Pro> ArticlePageList(String FieldStr, String TableName,
                                             String SqlWhere, String OrderField,
                                             String OrderType, int NowPape,
                                             int PageSize);
}
