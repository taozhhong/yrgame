$(function () {
    // 在这里赋值, 用于解决 FF 里多个 iframe 的bug
    //$("#logo").get(0).contentWindow.document.location.href = "../logo/logo.html";
    //$("#topbar").get(0).contentWindow.document.location.href = "../topbar/topbar.html";
    //$("#menu").get(0).contentWindow.document.location.href = "../menu/menu.html";
    //$("#tabindex").get(0).contentWindow.document.location.href = "../tabindex/tabindex.html";

    $('#logout').click(function () {
        top.location.href = "../login.html";
    });

    $("#tree").tree({
        url: "../data/menu.json",
        onClick: doMenuClick
    });

    function doMenuClick(node) {
        if ($("#tree").tree("isLeaf", node.target) == false)
            return;

        var id = node.id;
        var text = node.text;
        if (!id) return;

        var elTab = parent.$('#mainTabs');
        if (elTab.tabs('exists', text)) {
            elTab.tabs('select', text);
        } else {
            // FIXME: iframe 必须包在 div 里, 否则会出现多余的滚动条
            // 如果新tab页能够设置style, 就不必这个多余的div了
            var url = '../' + id + '/' + id + '.html';
            var content = '<div style="width:100%;height:100%;overflow:hidden;">' +
                '<iframe src="' + url + '" scrolling="auto" style="width:100%;height:100%;border:0;" ></iframe></div>';

            elTab.tabs('add', {
                title: text,
                content: content,
                // href: url,
                closable: true
            });
        }
    }
});

function showAbout() {
    $.messager.alert("系统版本号 v0.3", "设计: jxc<br/>邮箱: taozhhong@126.com");
}
