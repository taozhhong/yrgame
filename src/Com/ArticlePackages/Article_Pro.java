package Com.ArticlePackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:01
 * To change this template use File | Settings | File Templates.
 */
public class Article_Pro
{
    private int articleid;
    private int cateid;
    private String articletitle;
    private String content;
    private int islock;
    private int status;
    private String addtime;
    public Article_Pro()
    {

    }

    public int getArticleid()
    {
        return articleid;
    }

    public void setArticleid(int articleid)
    {
        this.articleid = articleid;
    }

    public int getCateid()
    {
        return cateid;
    }

    public void setCateid(int cateid)
    {
        this.cateid = cateid;
    }

    public String getArticletitle()
    {
        return articletitle;
    }

    public void setArticletitle(String articletitle)
    {
        this.articletitle = articletitle;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public int getIslock()
    {
        return islock;
    }

    public void setIslock(int islock)
    {
        this.islock = islock;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public String getAddtime()
    {
        return addtime;
    }

    public void setAddtime(String addtime)
    {
        this.addtime = addtime;
    }
}
