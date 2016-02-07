package Com.SysSetPackages.RoteSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: lenovo
 * Date: 13-8-10
 * Time: 下午2:42
 * nlsp_rote表字段
 */
public class Rote_Pro {
    private int roteid;//角色ID[主键-根据序列跟触发器自动增长]
    private String rotename;//角色名称
    private String b_mkid;//模块大类ID[多个模块用&相连]
    private String s_mkid;//模块小类ID[多个大小用&相连,模块与模块之间用“|”相连]
    private String optid;//操作权限ID[多个操作权限用&相连,小模块用小模块之间用“|”相连]
    private String remark;//备注

    public Rote_Pro () {
    }

    public int getRoteid () {
        return roteid;
    }

    public void setRoteid (int roteid) {
        this.roteid = roteid;
    }

    public String getRotename () {
        return rotename;
    }

    public void setRotename (String rotename) {
        this.rotename = rotename;
    }

    public String getB_mkid () {
        return b_mkid;
    }

    public void setB_mkid (String b_mkid) {
        this.b_mkid = b_mkid;
    }

    public String getS_mkid () {
        return s_mkid;
    }

    public void setS_mkid (String s_mkid) {
        this.s_mkid = s_mkid;
    }

    public String getOptid () {
        return optid;
    }

    public void setOptid (String optid) {
        this.optid = optid;
    }

    public String getRemark () {
        return remark;
    }

    public void setRemark (String remark) {
        this.remark = remark;
    }
}
