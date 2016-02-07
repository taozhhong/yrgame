package Com.SharePackages;

public abstract interface Field_Str {
     //管理菜单大类
     public static final String Menu_Type[] = {"系统设置", "信息管理","会员管理"};

     //管理菜单小类
     public static final String Menu_Small_Type[][] = {
         {"地区管理", "角色管理", "商户管理", "用户管理","参数配置", "公告管理","日志管理"},
         {"文章管理"},
         {"会员管理"}
     };

     //操作功能
     public static final String Menu_Opt[][] = {
         {"添加,删除,修改", "添加,删除,修改", "添加,删除,修改,商户key,数据源", "添加,删除,修改,修改密码","添加,删除,修改", "添加,删除,修改",  "查看,删除,清空"},
         {"添加,删除,修改"},
         {"删除,资料修改,密码重置"}


     };

     //对应的操作后台
     public static final String Menu_Type_Link[][] = {
         {"sysset/area/arealist.jsp?", "sysset/rote/rotelist.jsp?", "sysset/store/storelist.jsp?", "sysset/mguser/mguserlist.jsp?","sysset/parame/paramelist.jsp?","sysset/ann/sysannlist.jsp?", "sysset/log/loglist.jsp?"},
         {"article/articlelist.jsp?"},
         {"member/memberlist.jsp?"}
     };


    //性别
    public static final String User_Sex[] = {"男", "女"};

    //学历
    public static final String User_Edu[] = {"小学", "初中", "高中/中专", "大专", "本科", "硕士", "博士"};

    //员工状态
    public static final String User_State[] = {"正常", "锁定"};

    //状态
    public static final String Islock[] = {"正常", "锁定","休眠"};

    //提款方式
    public static final String WeType[]={"银行转帐/转帐"};

    //文章类型
    public static final String ArticleType[] = {"棋牌资讯", "帮助中心","隐私安全","关于我们","联系我们"};

}
