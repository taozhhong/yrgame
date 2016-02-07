<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="nowdate" class="Com.SharePackages.Format_Date" scope="request"/>
<%@ page import="Com.ArticlePackages.Article_Pro,Com.ArticlePackages.SingleArticle" %>
<%@ page import="Com.SharePackages.checkStr,java.util.Iterator" %>
<!DOCTYPE html>
<html xmlns="/www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>我想游游戏－好玩的棋牌电玩游戏平台</title>
<meta name="keywords" content="我想游游戏,捕鱼,斗地主,梭哈,金花,水果机"/>
<meta name="description" content="信誉棋牌游戏,真正赚金的游戏-渔人。"/>
<link type="image/gif" rel="icon" title="" href="/images/animated_favicon1.gif"/>
<link href="/style1/css.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="/js/jcarousellite_1.0.1.js"></script>
</head> 
<body> 

<!--头部开始-->
<%@ include file="/include/top.jsp"%>
<!--头部结束-->
<div id="main">
	 <%@ include file="/include/left.jsp"%>
	<div class="main_right">
		<div class="banner">
			<!--banner开始-->
			<div align="center">
				<script type="text/javascript">
    var pic_width = 769; //图片宽度
    var pic_height = 262; //图片高度
    var button_pos = 4; //按扭位置 1左 2右 3上 4下
    var stop_time = 5000; //图片停留时间(1000为1秒钟)
    var show_text = 0; //是否显示文字标签 1显示 0不显示
    var txtcolor = "000000"; //文字色
    var bgcolor = "DDDDDD"; //背景色
    var imag = new Array();
    var link = new Array();
    var text = new Array();
    imag[1] = "images/banner1.jpg";
    link[1] = "#";
    text[1] = "标题 1";
    imag[2] = "images/banner2.jpg";
    link[2] = "#";
    text[2] = "标题 2";
    imag[3] = "images/banner3.jpg";
    link[3] = "#";
    text[3] = "标题 3";
    imag[4] = "images/banner4.jpg";
    link[4] = "#";
    text[4] = "标题 4";
    imag[5] = "images/banner5.jpg";
    link[5] = "#";
    text[5] = "标题 5";
    //可编辑内容结束
    var swf_height = show_text == 1 ? pic_height + 20 : pic_height;
    var pics = "", links = "", texts = "";
    for (var i = 1; i < imag.length; i++) {
        pics = pics + ("|" + imag[i]);
        links = links + ("|" + link[i]);
        texts = texts + ("|" + text[i]);
    }
    pics = pics.substring(1);
    links = links.substring(1);
    texts = texts.substring(1);
    document.write('<object classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="/fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cabversion=6,0,0,0" width="' + pic_width + '" height="' + swf_height + '">');
    document.write('<param name="movie" value="images/focus.swf">');
    document.write('<param name="quality" value="high"><param name="wmode" value="opaque">');
    document.write('<param name="FlashVars" value="pics=' + pics + '&links=' + links + '&texts=' + texts + '&pic_width=' + pic_width + '&pic_height=' + pic_height + '&show_text=' + show_text + '&txtcolor=' + txtcolor + '&bgcolor=' + bgcolor + '&button_pos=' + button_pos + '&stop_time=' + stop_time + '">');
    document.write('<embed src="images/focus.swf" FlashVars="pics=' + pics + '&links=' + links + '&texts=' + texts + '&pic_width=' + pic_width + '&pic_height=' + pic_height + '&show_text=' + show_text + '&txtcolor=' + txtcolor + '&bgcolor=' + bgcolor + '&button_pos=' + button_pos + '&stop_time=' + stop_time + '" quality="high" width="' + pic_width + '" height="' + swf_height + '" allowScriptAccess="sameDomain" type="application/x-shockwave-flash" pluginspage="/www.macromedia.com/go/getflashplayer" />');
    document.write('</object>');
				</script>
			</div>
			<!--banner结束-->
		</div>
		<div class="dynamic_box p20">
			<div class="news">
				<div class="tt">
					<a href="/news/list/cate/0">more></a><span>渔人动态</span>
				</div>
				<div class="box">
					<ul>
                        <%
                            Iterator NewsIt=SingleArticle.getInstance().getArticleDb().ArticleList("",
                                    "8","*","article a",
                                    "a.*","a.cateid=0 and a.islock=0",
                                    "a.articleid","desc").iterator();
                            while (NewsIt.hasNext())
                            {
                                Article_Pro pro=(Article_Pro)NewsIt.next();
                                if(pro!=null)
                                {
                        %>
						<li><span><%=nowdate.set_date(pro.getAddtime(),"yyyy-MM-dd")%></span> <a href='/news/article/id/<%=pro.getCateid()%>/<%=pro.getArticleid()%>'><%=pro.getArticletitle()%></a></li>
                        <%
                                }
                                NewsIt.remove();
                            }
                        %>
					</ul>
				</div>
			</div>
			<div class="user_top">
				<div class="container1">
					<div class="tt">
						<a href="#"></a><span>财富排行榜</span>
					</div>
					<div class="box">
						<dl>
							<dt class="n1" runat="server">1</dt>
							<dd><span>34451.00</span>ID:aaa</dd>
							<dt class="n2" runat="server">2</dt>
							<dd><span>29392.00</span>ID:aaa</dd>
							<dt class="n3" runat="server">3</dt>
							<dd><span>24797.00</span>ID:aaa</dd>
							<dt class="n4" runat="server">4</dt>
							<dd><span>24683.00</span>ID:aaa</dd>
							<dt class="n5" runat="server">5</dt>
							<dd><span>22528.00</span>ID:aaa</dd>
							<dt class="n6" runat="server">6</dt>
							<dd><span>20467.00</span>ID:aaa</dd>
							<dt class="n7" runat="server">7</dt>
							<dd><span>16277.00</span>ID:aaa</dd>
							<dt class="n8" runat="server">8</dt>
							<dd><span>16132.00</span>ID:aaa</dd>
							<dt class="n9" runat="server">9</dt>
							<dd><span>14948.00</span>ID:aaa</dd>
							<dt class="n10" runat="server">10</dt>
							<dd><span>12608.00</span>ID:aaa</dd>
							<div class="remove">
							</div>
						</dl>
					</div>
				</div>
			</div>
			<div class="remove">
			</div>
		</div>
	</div>
	<div class="remove">
	</div>
	<div class="hot_game">
		<div class="tt">
			<a href="/download">more></a><span>热门游戏</span>
		</div>
		<div class="box">
			<div class="l">
				<a href="#" id="btn_focus_prev"></a>
			</div>
			<div class="dome1">
				<div class="game_list">
					<ul>
						<!--热门游戏内容循环开始-->
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="李逵劈鱼"></a>
							</div>
							<img src="/images/2010.png" width="100" alt="李逵劈鱼" height="100"/>
						</div>
						<a href="/download" style=" color:#e76000;">李逵劈鱼</a>
						<p>
							李逵捕鱼是游戏中心精心推出的另一款捕鱼游戏，在保留了传统街机版本的界面...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="水果连线"></a>
							</div>
							<img src="/images/655.jpg" width="100" alt="水果连线" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">水果连线</a>
						<p>
							全球风靡的游戏，玩家控制启动停止，横竖斜面三个一样的图案即中奖...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="飞禽走兽"></a>
							</div>
							<img src="/images/123.png" width="100" alt="飞禽走兽" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">飞禽走兽</a>
						<p>
							飞禽走兽类似传统的水果机，是玩家是否压中开奖区域计算游戏输赢...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="斗地主"></a>
							</div>
							<img src="/images/200.png" width="100" alt="斗地主" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">斗地主</a>
						<p>
							斗地主是一款风靡全球的棋牌类游戏。是玩家两人合伙斗一人来计算游戏输赢...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="对战牛牛"></a>
							</div>
							<img src="/images/27.png" width="100" alt="对战牛牛" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">对战牛牛</a>
						<p>
							《对战牛牛》又名斗牛，是流行于我国浙南一带的牌类游戏玩法，由四人共同参与游戏...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="水果乐园"></a>
							</div>
							<img src="/images/115.png" width="100" alt="水果乐园" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">水果乐园</a>
						<p>
							超好玩的电玩游戏，西瓜，橘子，芒果，转到押注的图案即算中奖...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="二人梭哈"></a>
							</div>
							<img src="/images/24.png" width="100" alt="二人梭哈" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">港式梭哈</a>
						<p>
							梭哈游戏主要流行于我国广东、香港、澳门，由于游戏简单，激烈，既含有技巧也有...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="十三张"></a>
							</div>
							<img src="/images/7.jpg" width="100" alt="十三张" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">十三张</a>
						<p>
							是扑克游戏的一种。 有人说是俄罗斯扑克。该游戏适于 2、3 或 4 名玩家。 通常由四人一起玩...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="德州扑克"></a>
							</div>
							<img src="/images/3.jpg" width="100" alt="德州扑克" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">德州扑克</a>
						<p>
							德克萨斯扑克全称Texas Hold’em poker，中文简称德州扑克。是玩家对玩家的公共牌类游戏...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="扎金花"></a>
							</div>
							<img src="/images/6.png" width="100" alt="金花" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">扎金花</a>
						<p>
							又叫三张牌是在全国广泛流传的一种民间多人纸牌游戏、是玩家对玩家的公共牌类游戏...
						</p>
						</li>
						<li>
						<div class="pic">
							<div class="bg">
								<a href="/download" title="二人麻将"></a>
							</div>
							<img src="/images/358.png" width="100" alt="二人麻将" height="100"/>
						</div>
						<a href="#" style=" color:#e76000;">二人麻将</a>
						<p>
							二人麻将是最受欢迎的二人麻将对战游戏,规则简单,画面精美,有趣好玩,娱乐性强...
						</p>
						</li>
						<!--热门游戏内容循环结束-->
					</ul>
					<script type="text/javascript">
               $(function () {
                   var clearTimer = null;
                   var SleepTime = 2500;   //停留时长，单位毫秒
                   $(".game_list").jCarouselLite({
                       btnNext: "#btn_focus_next",
                       btnPrev: "#btn_focus_prev",
                       visible: 3,
                       scroll: 1,
                       speed: 500, //滚动速度，单位毫秒
                       auto: 2500,
                       mouseOver: true
                   });
               });
					</script>
				</div>
			</div>
			<div class="r">
				<a href="#" id="btn_focus_next"></a>
			</div>
		</div>
	</div>
</div>
<!--底部开始-->
<%@ include file="/include/foot.jsp"%>
<!--底部结束-->
</body>
</html>
