<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="common_left">
    <!--左侧通用开始-->
    <div class="shortcut-box">
        <div class="download">
            <a href="/download"><strong>游戏大厅下载</strong>
                <p>
                    版本号：15.09.01 软件大小：3.70M<br/>
                    发布时间：2015/09/01
                </p>
                <span>本游戏适合18岁以上的玩家</span></a>
        </div>
        <div class="reg">
            <a href="/reg/gamereg">立即注册</a>
        </div>
        <div class="recharge">
            <a href="http://www.40zf.com/wxy/bank" target="_blank">账户充值</a>
        </div>
    </div>
    <div class="container1 p20">
        <div class="tt">
            <span>常见问题</span>
        </div>
        <div class="box">
            <ul class="li1">
                <%
                    Iterator QuaIt=SingleArticle.getInstance().getArticleDb().ArticleList("",
                            "4","*","article a",
                            "a.*","a.cateid=1 and a.islock=0",
                            "a.articleid","desc").iterator();
                    while (QuaIt.hasNext())
                    {
                        Article_Pro quapro=(Article_Pro)QuaIt.next();
                        if(quapro!=null)
                        {
                %>
                <li><a href="/news/article/id/<%=quapro.getCateid()%>/<%=quapro.getArticleid()%>"><%=quapro.getArticletitle()%></a></li>
                <%
                        }
                        QuaIt.remove();
                    }
                %>
            </ul>
        </div>
    </div>
    <div class="service p20">
        <a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=10057357&site=qq&menu=yes"><img border="0" src="/images/qq.gif" alt="点击这里给我发消息" title="点击这里给我发消息"></a>
        <div class="remove">
        </div>
    </div>
    <!--左侧通用结束-->
</div>
