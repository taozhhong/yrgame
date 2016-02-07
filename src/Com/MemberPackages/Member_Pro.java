package Com.MemberPackages;

/**
 * Created by Administrator on 2015/9/14.
 */
public class Member_Pro
{
    private int memberid;//会员ID[主键-根据序列自动增长]
    private String loginname;//登录名(索引)
    private String loginpwd;//登录密码(索引)
    private String nick;//称昵
    private String bankpwd;//银行密码
    private int tjmemberid;//推荐人ID
    private String realname;//真实姓名
    private int sex;//性别(0-男,1-女)(索引)
    private String birdate;//出生年月
    private String tel;//手机号码
    private String qq;//QQ
    private String email;//电子邮件
    private String idnum;//身份证号码
    private String headimg;//头像
    private String note;//备注
    private int status;//人员状态(0-正常，1-锁定，2-休眠)(索引)
    private String regip;//注册IP
    private String regtime;//注册时间

    public Member_Pro()
    {
    }

    public int getMemberid()
    {
        return memberid;
    }

    public void setMemberid(int memberid)
    {
        this.memberid = memberid;
    }

    public String getLoginname()
    {
        return loginname;
    }

    public void setLoginname(String loginname)
    {
        this.loginname = loginname;
    }

    public String getLoginpwd()
    {
        return loginpwd;
    }

    public void setLoginpwd(String loginpwd)
    {
        this.loginpwd = loginpwd;
    }

    public String getNick()
    {
        return nick;
    }

    public void setNick(String nick)
    {
        this.nick = nick;
    }

    public String getBankpwd()
    {
        return bankpwd;
    }

    public void setBankpwd(String bankpwd)
    {
        this.bankpwd = bankpwd;
    }

    public int getTjmemberid()
    {
        return tjmemberid;
    }

    public void setTjmemberid(int tjmemberid)
    {
        this.tjmemberid = tjmemberid;
    }

    public String getRealname()
    {
        return realname;
    }

    public void setRealname(String realname)
    {
        this.realname = realname;
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

    public String getTel()
    {
        return tel;
    }

    public void setTel(String tel)
    {
        this.tel = tel;
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

    public String getIdnum()
    {
        return idnum;
    }

    public void setIdnum(String idnum)
    {
        this.idnum = idnum;
    }

    public String getHeadimg()
    {
        return headimg;
    }

    public void setHeadimg(String headimg)
    {
        this.headimg = headimg;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
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
}
