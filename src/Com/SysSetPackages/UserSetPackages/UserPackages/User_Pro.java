package Com.SysSetPackages.UserSetPackages.UserPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-11
 * Time: 下午9:02
 * 表nlsp_manager_user字段
 */
public class User_Pro {
    private int storeid;//商务编号
    private int userid;// 用户编号[主键-根据序列跟触发器自动增长]
    private int nuserid;//新用户编号
    private String loginname;// 登录名
    private String loginpasd;// 登录密码
    private String storename;// 商户名称
    private String storepy;// 商户名称简拼(索引)
    private String director;//负责人
    private int sex;// 性别[0-男，1-女]
    private String birdate;//出生年月(索引)
    private int education;//学历
    private String telnum;//手机号码
    private String qq;//QQ
    private String email;//电子邮件
    private float divide;//分成比例
    private int provinceid;//省份编码[外键,zp_province表中主键](索引)
    private int cityid;// 地级市编码[外键,zp_city表中主键](索引)
    private int areaid;// 县级地区表[外键,zp_area表中主键](索引)

    private int roteid;//角色ID[外键,zp_rote表中主键](索引)
    private String nodes;//备注
    private int status; // 商户状态(0-正常,1-锁定)(索引)
    private int islock;//用户状态[0-正常，1-锁定]
    private int isprive;//是否允许查看全部单据(0-允许，1-不允许[只查看自己操作的相关单据])(索引)
    private String logintime;//最后一次登录时间
    private String loginip;// 最后一次登录IP
    private String lastlogintime;//上次登录时间
    private String lastloginip;//上次登录IP
    private String regip;//注册IP
    private String regtime;//管理用户添加时间
    private String rotename;//角色名称
    private String bmkid;//模块大类ID[多个模块用&相连]
    private String smkid;//模块小类ID[多个大小用&相连,模块与模块之间用“|”相连]
    private String optid;//操作权限ID[多个操作权限用&相连,小模块用小模块之间用“|”相连]

    private String provincename; //省名称
    private String cityname;//市名称
    private String areaname; //区名称

    private int ismg;//是否管理用户
    public User_Pro () {
    }

    public int getStoreid()
    {
        return storeid;
    }

    public void setStoreid(int storeid)
    {
        this.storeid = storeid;
    }

    public int getUserid()
    {
        return userid;
    }

    public void setUserid(int userid)
    {
        this.userid = userid;
    }

    public int getNuserid()
    {
        return nuserid;
    }

    public void setNuserid(int nuserid)
    {
        this.nuserid = nuserid;
    }

    public String getLoginname()
    {
        return loginname;
    }

    public void setLoginname(String loginname)
    {
        this.loginname = loginname;
    }

    public String getLoginpasd()
    {
        return loginpasd;
    }

    public void setLoginpasd(String loginpasd)
    {
        this.loginpasd = loginpasd;
    }

    public String getStorename()
    {
        return storename;
    }

    public void setStorename(String storename)
    {
        this.storename = storename;
    }

    public String getStorepy()
    {
        return storepy;
    }

    public void setStorepy(String storepy)
    {
        this.storepy = storepy;
    }

    public String getDirector()
    {
        return director;
    }

    public void setDirector(String director)
    {
        this.director = director;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getBirdate()
    {
        return birdate;
    }

    public void setBirdate(String birdate)
    {
        this.birdate = birdate;
    }

    public int getEducation()
    {
        return education;
    }

    public void setEducation(int education)
    {
        this.education = education;
    }

    public String getTelnum()
    {
        return telnum;
    }

    public void setTelnum(String telnum)
    {
        this.telnum = telnum;
    }

    public String getQq()
    {
        return qq;
    }

    public void setQq(String qq)
    {
        this.qq = qq;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public float getDivide()
    {
        return divide;
    }

    public void setDivide(float divide)
    {
        this.divide = divide;
    }

    public int getProvinceid()
    {
        return provinceid;
    }

    public void setProvinceid(int provinceid)
    {
        this.provinceid = provinceid;
    }

    public int getCityid()
    {
        return cityid;
    }

    public void setCityid(int cityid)
    {
        this.cityid = cityid;
    }

    public int getAreaid()
    {
        return areaid;
    }

    public void setAreaid(int areaid)
    {
        this.areaid = areaid;
    }

    public int getRoteid()
    {
        return roteid;
    }

    public void setRoteid(int roteid)
    {
        this.roteid = roteid;
    }

    public String getNodes()
    {
        return nodes;
    }

    public void setNodes(String nodes)
    {
        this.nodes = nodes;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getIslock()
    {
        return islock;
    }

    public void setIslock(int islock)
    {
        this.islock = islock;
    }

    public int getIsprive()
    {
        return isprive;
    }

    public void setIsprive(int isprive)
    {
        this.isprive = isprive;
    }

    public String getLogintime()
    {
        return logintime;
    }

    public void setLogintime(String logintime)
    {
        this.logintime = logintime;
    }

    public String getLoginip()
    {
        return loginip;
    }

    public void setLoginip(String loginip)
    {
        this.loginip = loginip;
    }

    public String getLastlogintime()
    {
        return lastlogintime;
    }

    public void setLastlogintime(String lastlogintime)
    {
        this.lastlogintime = lastlogintime;
    }

    public String getLastloginip()
    {
        return lastloginip;
    }

    public void setLastloginip(String lastloginip)
    {
        this.lastloginip = lastloginip;
    }

    public String getRegip()
    {
        return regip;
    }

    public void setRegip(String regip)
    {
        this.regip = regip;
    }

    public String getRegtime()
    {
        return regtime;
    }

    public void setRegtime(String regtime)
    {
        this.regtime = regtime;
    }

    public String getRotename()
    {
        return rotename;
    }

    public void setRotename(String rotename)
    {
        this.rotename = rotename;
    }

    public String getBmkid()
    {
        return bmkid;
    }

    public void setBmkid(String bmkid)
    {
        this.bmkid = bmkid;
    }

    public String getSmkid()
    {
        return smkid;
    }

    public void setSmkid(String smkid)
    {
        this.smkid = smkid;
    }

    public String getOptid()
    {
        return optid;
    }

    public void setOptid(String optid)
    {
        this.optid = optid;
    }

    public String getProvincename()
    {
        return provincename;
    }

    public void setProvincename(String provincename)
    {
        this.provincename = provincename;
    }

    public String getCityname()
    {
        return cityname;
    }

    public void setCityname(String cityname)
    {
        this.cityname = cityname;
    }

    public String getAreaname()
    {
        return areaname;
    }

    public void setAreaname(String areaname)
    {
        this.areaname = areaname;
    }

    public int getIsmg()
    {
        return ismg;
    }

    public void setIsmg(int ismg)
    {
        this.ismg = ismg;
    }
}
