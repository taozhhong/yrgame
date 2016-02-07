package Com.SysSetPackages.AreaSetPackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 13-8-15
 * Time: 下午3:28
 * 表nlsp_province nlsp_city nlsp_area中字段
 */
public class Area_Pro {
    private int id;//地区ID
    private String name;//地区名称
    private String namepy;//地区首字母拼音
    private int status;//地区状态
    private int pid;//上级地区ID

    private int parent_id;//上级ID
    private int jb;//极别

    public Area_Pro () {
    }

    public int getId () {
        return id;
    }

    public void setId (int id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getNamepy () {
        return namepy;
    }

    public void setNamepy (String namepy) {
        this.namepy = namepy;
    }

    public int getStatus () {
        return status;
    }

    public void setStatus (int status) {
        this.status = status;
    }

    public int getPid () {
        return pid;
    }

    public void setPid (int pid) {
        this.pid = pid;
    }

    public int getParent_id () {
        return parent_id;
    }

    public void setParent_id (int parent_id) {
        this.parent_id = parent_id;
    }

    public int getJb () {
        return jb;
    }

    public void setJb (int jb) {
        this.jb = jb;
    }
}
