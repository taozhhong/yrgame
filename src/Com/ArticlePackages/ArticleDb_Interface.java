package Com.ArticlePackages;


import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:08
 * To change this template use File | Settings | File Templates.
 */
public interface ArticleDb_Interface
{
    /**
     * 对表TagKey表增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,up-修改操作)
     * @param Pro      表Borkey中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    public int ArticleAction(String MarkStr, Article_Pro Pro, String SqlWhere);



    /**
     * 列表
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
    public List<Article_Pro>ArticleList(String Pk, String TopInt, String FiledStr,
                                        String TableName,
                                        String C_FiledStr, String SqlWhere,
                                        String OrderyFiled, String OrderyType);
}
