/**
 * ==========================
 * 共用JS代码
 * ==========================
 */
/**
 * 判断是否有中文
 * @param s
 * @returns {boolean}true 有中文 false 没中文
 */
function ischinese(s) {
    if (s == "") {
        return false;
    }
    else {
        var ret = true;
        for (var i = 0; i < s.length; i++)
            ret = ret && (s.charCodeAt(i) >= 10000);
        return ret;
    }
}

/**
 *手机号码验证，验证13系列和150-159(154除外)、180、182、185、186、187、188、189几种号码，长度11位
 *@param string value 手机号码
 *@return bool
 */
function isMobel(value) {
    if (value == "") {
        alert(value);
    }
    else {
        if (/^13\d{9}$/g.test(value) || (/^15[0-35-9]\d{8}$/g.test(value)) ||
            (/^18[025-9]\d{8}$/g.test(value))) {

            return true;
        }
        else {
            return false;
        }
    }
}

/**
 * 返回字符长度,一个中文算二个字符
 * @param nameValue
 * @returns {number}
 */
function getValueLen(nameValue) {
    var nameStr = nameValue;
    var len = 0;
    for (var i = 0; i < nameStr.length; i++) {
        //str = markerStr.charAt(i);
        if (nameStr.charCodeAt(i) > 255 || nameStr.charCodeAt(i) < 0) {
            len += 2;
        } else {
            len++;
        }
    }
    return len;
}

/**
 * 限制文本框输入几个字
 * @param message 文本框的值
 * @param remain  显示字数的ID
 * @param maxleng 最多字数
 * @constructor
 */
function Aboutcount(message, remain, maxleng) {
    var max = maxleng;
    if (getValueLen(message.value) > max) {
        message.value = message.value.substring(0, max);
        $("#" + remain).html(0);
        $.messager.alert('系统提示', "内容不能超过" + max + "个字！", 'info');
    }
    else {
        $("#" + remain).html(getValueLen(message.value));
    }
}

/**
 * 限制文本框输入几个字
 * @param message 文本框的值
 * @param remain  显示字数的ID
 * @param maxleng 最多字数
 * @constructor
 */
function OnLoadAbCount(TextVal, Max, ShowT) {
    if (getValueLen(TextVal) > Max) {
        $(ShowT).html(0);
        $.messager.alert('系统提示', "内容不能超过" + Max + "个字！", 'info');
    }
    else {
        $(ShowT).html(getValueLen(TextVal));
    }
}

//删除图片
function DelImg() {
    $("#ImgTag").html("");
    $("#goodsimgpath").val("");
}

/**
 * 图片上传，不带参数
 */
function ajaxImgFileUpload() {
    $.ajaxFileUpload({
        url: '/Action/SingleUpload.do',             //需要链接到服务器地址
        secureuri: false,
        fileElementId: 'filename',                        //文件选择框的id属性
        dataType: 'text',
        //服务器返回的格式，可以是json
        success: function (data, status)             //相当于java中try语句块的用法
        {
            if (data != "")//data是从服务器返回来的值
            {
                if (data.indexOf("FileNull") != -1) {
                    $.messager.alert('系统提示', '请选择正确的图片', 'info');
                }
                else if (data.indexOf("FileTypeNull") != -1) {
                    $.messager.alert('系统提示', '抱歉，没有上传控件！', 'info');

                }
                else if (data.split(",")[0].indexOf("UpOk") != -1)//表示返回的是图片路径
                {
                    $("#goodsimgpath").val(data.split(",")[1]);
                    $("#ImgTag").html("<br><img src=\"/upload/goods/goods_thumb/" + data.split(",")[1] + "\" width=\"105\"><br>" +
                        "<a href=\"javascript:void('');\" onclick=\"DelImg()\">删除</a>");
                }
                else if (data.indexOf("UpErr") != -1) {
                    $.messager.alert('系统提示', '抱歉，上传失败,请检查图片格式跟大小！', 'info');

                }
                else if (data.indexOf("UpExce") != -1) {
                    $.messager.alert('系统提示', '抱歉，请选择正确的图片路径', 'info');
                }
                else {
                    $.messager.alert('系统提示', '抱歉，发生错误的请求！', 'error');
                }
            }
            else {
                $.messager.alert('系统提示', '抱歉，上传图片异常！', 'error');
            }
        },
        error: function (data, status, e)//相当于java中catch语句块的用法
        {
            $.messager.alert('系统提示', '抱歉，上传图片出错！', 'error');
        }
    });
}


/**
 * 图片上传，可以带参数的上传
 * @param vid　参数值
 * @param bid 大类ID
 * @param sid 小类ID
 * @param uptype 上传类型
 * @param urlpath　需要链接到服务器地址
 * @param imgwidth 图片的宽
 * @constructor
 */
function AjaxImgFileUpload(vid, bid, sid,uptype, urlpath, imgwidth) {
    $.ajaxFileUpload(
        {
            url: urlpath,//需要链接到服务器地址
            secureuri: false,
            fileElementId: 'filename_' + vid,//文件选择框的id属性
            dataType: 'text',
            data: {//加入的文本参数
                "filemark": vid,
                "bid": bid,
                "sid": sid,
                "uptype":uptype
            },  //服务器返回的格式，可以是json
            success: function (data, status)             //相当于java中try语句块的用法
            {
                var jsondata = eval('(' + data + ')');
                if (jsondata.err == "")//表示没有出错
                {
                    var marktype = jsondata.msg.split(",")[0];//即文件框后缀
                    var inputname = jsondata.msg.split(",")[0].split("_")[0];//文本框name
                    var dirpath = jsondata.msg.split(",")[1];//存放路径
                    var filename = jsondata.msg.split(",")[2];//文件名称
                    var datedir = jsondata.msg.split(",")[3];//根据日期建立的存放文件的目录
                    var inputstr = "<input type='hidden' name='" + inputname + "' value='" + datedir+"/"+filename + "'>";
                    $("#" + marktype + "ImgTag").html("<br><img src=\"/" + dirpath + filename + "\" width=" + imgwidth + "><br>" +
                        "<a href=\"javascript:void('');\" onclick=\"DelSupImg('" + marktype + "')\">删除</a>" + inputstr);
                }
                else {
                    $.messager.alert('系统提示', jsondata.err, 'error');
                }
            },
            error: function (data, status, e)//相当于java中catch语句块的用法
            {
                $.messager.alert('系统提示', '抱歉，上传图片出错，错误信息：' + e, 'error');
            }
        });
}

/**
 * 快递模板上传，可以带参数的上传
 * @param posttype 操作方法
 * @param tempname 模板名称
 * @param tempstatus 模板状态
 * @param tempurl 模板路径
 * @param tempid 模板ID
 * @param bid 大类ID
 * @param sid 小类ID
 * @param urlpath 提交的地址
 * @constructor
 */
function AjaxExpTempFileUpload(posttype,tempname,
                               tempstatus,tempurl,
                               tempid, bid, sid,
                               urlpath) {
    $.ajaxFileUpload(
        {
            url: urlpath,//需要链接到服务器地址
            secureuri: false,
            fileElementId: 'filename',//文件选择框的id属性
            dataType: 'text',
            data: {//加入的文本参数
                "expmethod":posttype,
                "expname":tempname,
                "enabled":tempstatus,
                "exppath":tempurl,
                "expid":tempid,
                "bid": bid,
                "sid": sid
            },  //服务器返回的格式，可以是json
            success: function (data, status)             //相当于java中try语句块的用法
            {
                var jsondata = eval('(' + data + ')');
                if (jsondata.err == "")//表示没有出错
                {
                    $(window.parent.$('#gd').window('close'));
                    $(window.parent.$.messager.alert('系统提示', jsondata.msg, 'info'));
                    $(window.parent.$("#ExpList").datagrid('load'));
                }
                else {
                    $.messager.alert('系统提示', jsondata.err, 'error');
                }
            },
            error: function (data, status, e)//相当于java中catch语句块的用法
            {
                $.messager.alert('系统提示', '抱歉，上传模板出错，错误信息：' + e, 'error');
            }
        });
}




/**
 * 删除上传图片
 * @param marktype　ID参数
 * @constructor
 */
function DelSupImg(marktype) {
    $("#" + marktype + "ImgTag").html("");
}


/**
 * 弹出窗口
 * @param title 窗口标题
 * @param url　窗体地址
 * @param width　宽度
 * @param height　高度
 * @constructor
 */
function OpenDetail(title, url, width, height) {
    var win = '<div id="gd" class="easyui-window" style="padding:2px;"></div>';
    $(document.body).append(win);
    $('#gd').window({
        title: title,
        width: width,
        height: height,
        shadow: true,
        modal: true,
        iconCls: 'icon-add',
        closed: true,
        minimizable: false,
        maximizable: false,
        collapsible: false
    });
    $('#gd').html("<iframe style='width: 100%;height: 100%;' marginwidth='0' marginheight='0' frameborder='no' scrolling='no' src='" + url + "'></iframe>");
    $('#gd').window('open');
}




/**
 * 格式化时间
 * 返回格式:2012/12/13
 * @returns {string}
 */
function getNowFormatDate() {
    var day = new Date();
    var Year = 0;
    var Month = 0;
    var Day = 0;
    var CurrentDate = "";
    Year = day.getFullYear();//ie火狐下都可以
    Month = day.getMonth() + 1;
    Day = day.getDate();
    CurrentDate += Year + "/";
    if (Month >= 10) {
        CurrentDate += Month + "/";
    }
    else {
        CurrentDate += "0" + Month + "/";
    }
    if (Day >= 10) {
        CurrentDate += Day;
    }
    else {
        CurrentDate += "0" + Day;
    }
    return CurrentDate;
}

