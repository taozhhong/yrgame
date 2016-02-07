package Com.SysSetPackages.AreaSetPackages;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-15
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public interface AreaDb_Interface {
    /**
     * 对表地区增删改操作
     *
     * @param MarkStr  标识符(add-添加操作，del-删除操作,edit-修改操作)
     * @param Pro      表nlsp_area中字段
     * @param SqlWhere 删除条件
     * @return int 如果0表示操作失败,大于0表示操作成功
     */
    public int AreaAction(String MarkStr, Area_Pro Pro, String SqlWhere);

    /**
     * 省列表
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
    public List<Area_Pro> ProvinceList(String Pk, String TopInt, String FiledStr, String TableName, String
            C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType);


    /**
     * 市列表
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
    public List<Area_Pro> CityList(String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr,
                                   String SqlWhere, String OrderyFiled, String OrderyType);

    /**
     * 区列表
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
    public List<Area_Pro> AreaList(String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr,
                                   String SqlWhere, String OrderyFiled, String OrderyType);


    /**
     * 小区列表
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
    public List<Area_Pro> VillageList(String Pk, String TopInt, String FiledStr, String TableName, String C_FiledStr, String SqlWhere, String OrderyFiled, String OrderyType);


    /**
     * 树列表
     *
     * @param TopInt      查询前几条数据,如果为空的话,表示全部数据
     * @param FiledStr    查询字段,如果查询全部则用*号表示,否则用字段名表示.多表查询,刚表名.字段名的方式
     * @param TableName   表名,支持多表查询
     * @param SqlWhere    查询条件,不带where
     * @param StartWith   树查询的start with 语句
     * @param OrderyFiled 排序字段段.支持多字段查询.如:a.desc,b
     * @param OrderyType  排序方式
     * @return List对象
     */
    public List<Area_Pro> AreaTreeList(String TopInt, String FiledStr, String TableName, String SqlWhere, String StartWith, String OrderyFiled, String OrderyType);

    /**
     * 异步加载JSON,应用于列表或者菜单
     *
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param CityId     地区ID
     * @return String
     */
    public String AreaTreeStr(List<Area_Pro> AreaTreeIt, List<Area_Pro> AreaIdIt, int CityId);


    /**
     * 异步加载JSON,应用于添加地区
     *
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param parent_id 用户点击菜单的ID
     * @return String
     */
    public String AreaTreeCom(List<Area_Pro> AreaTreeIt, List<Area_Pro> AreaIdIt, String parent_id);


    /**
     * 异步加载JSON,应用于员工基本信息地区修改
     * @param AreaTreeIt 树查询结果集
     * @param AreaIdIt   要修改的地区的父节点
     * @param CityId 地区ID
     * @return String
     */
    public String AreaTreeEdit(List<Area_Pro> AreaTreeIt,
                               List<Area_Pro> AreaIdIt,
                               int CityId);
}
