function setRem(pwidth, prem, cb) {
    var html = document.getElementsByTagName("html")[0];
    var oWidth = document.body.clientWidth || document.documentElement.clientWidth;
    html.style.fontSize = oWidth / pwidth * prem + "px";
    typeof cb == 'function' && cb();
}

function commonAlert(msg, callback) {
    mui.alert(msg, "提示", "确认", callback, 'div')
}

//判断是否为空
function isNullOrisEmpty(str) {
    if (str == null || str == '' || str == ' ' || str == undefined || str == 'undefined' || str == 'null' || str == NaN) {
        return true;
    } else {
        return false;
    }
}

//验证字符串长度
function checkLengthminmax(str, min, max) {
    if (!isNullOrisEmpty(str) && str.length >= min && str.length <= max) {
        return true;
    } else {
        return false;
    }
}

// 由于mui里面的a标签无法直接跳转 处理方法如下
mui('body').on('tap','a',function(){
　	window.top.location.href=this.href;
});

function initPullRefresh(container, url, param, contentId, callback) {
    mui.init({
        pullRefresh: {
            container: container,//待刷新区域标识
            up: {
                height: 50,//可选.默认50.触发上拉加载拖动距离
                auto: true,//可选,默认false.自动上拉加载一次
                contentrefresh: "正在加载...",//可选，正在加载状态时
                contentnomore: '没有更多数据了',//可选，请求完毕若没有更多数据时
                callback: function () {
                    $.ajax({
                        url: url,
                        data: param,
                        type: 'POST',
                        success: function (data) {
                            //判断是否有返回值 当没有返回值的时候就为空，则代表没有更多数据了
                            var flag = data == null || data == '';
                            if (flag == false) {
                                param.pageNumber++;
                            }
                            $(data).insertBefore(contentId);
                            mui(container).pullRefresh().endPullupToRefresh(flag);
                            if (callback) {
                                callback();
                            }
                        }
                    });
                }
            }
        }
    });
}

function initPullRefreshJson(container, url, param, contentId, callback) {
    mui.init({
        pullRefresh: {
            container: container,//待刷新区域标识
            up: {
                height: 50,//可选.默认50.触发上拉加载拖动距离
                auto: true,//可选,默认false.自动上拉加载一次
                contentrefresh: "正在加载...",//可选，正在加载状态时
                contentnomore: '没有更多数据了',//可选，请求完毕若没有更多数据时
                callback: function () {
                    $.ajax({
                        url: url,
                        data: JSON.stringify(param),
                        contentType: "application/json; charset=utf-8",
                        type: 'POST',
                        success: function (data) {
                            //判断是否有返回值 当没有返回值的时候就为空，则代表没有更多数据了
                            var flag = data == null || data == '';
                            if (flag == false) {
                                param.pageNumber++;
                            }
                            $(data).insertBefore(contentId);
                            mui(container).pullRefresh().endPullupToRefresh(flag);
                            if (callback) {
                                callback();
                            }
                        }
                    });
                }
            }
        }
    });
}
