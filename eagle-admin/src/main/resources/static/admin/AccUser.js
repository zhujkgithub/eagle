var AccUser = function () {

    var $accUserTable = $("#accUserTable");
    var $detail = $("#detail");
    var $queryBtn = $("#queryBtn");

    var tableColum = [
        {checkbox: true}
        , {title: '用户名', field: 'username', align: 'center', valign: 'middle'}
        , {
            title: '性别', field: 'sex', align: 'center', valign: 'middle', formatter: function (value) {
                if (value === '1') {
                    return "男";
                } else {
                    return "女";
                }

            }
        }
    ];

    var initTable = function () {
        Common.initBootStrapTable($accUserTable, basePath + "/user/list", tableColum, null);
    };

    var buttonHander = function () {
        $detail.on("click", function () {
            var rows = Common.getTableSelections($accUserTable);
            if (rows.length === 0 || rows.length > 1) {
                layer.msg("请选择一条信息进行操作");
                return;
            }
            //点击了添加按钮 弹出添加的layer frame
            Common.layerOpen("查看会员详情", '800px', '800px', basePath + "/acc/user/info/" + rows[0].id);
        });
        $queryBtn.on("click", function () {
            Common.searchTable("accUserTable", "queryForm");
        });
    };
    return {
        initTable: function () {
            initTable();
            buttonHander();
        }
    }
}();