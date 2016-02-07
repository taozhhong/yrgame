package Com.ArticlePackages;

/**
 * Created with IntelliJ IDEA.
 * User: Administrator
 * Date: 14-7-20
 * Time: 下午2:12
 * To change this template use File | Settings | File Templates.
 */
public class FactoryArticle implements CreateArticle
{
    @Override
    public ArticleDb_Interface getArticleDb()
    {
        return new ArticleDb();
    }

    @Override
    public ArticlePage_Interface getArticlePage()
    {
        return new ArticlePage();
    }
}
