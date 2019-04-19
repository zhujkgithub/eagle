var Common = function () {

    var initTable = function (table, ajaxUrl, columns, formatShowingRowsValue, rowStyleFunction, pageSize) {
        table.bootstrapTable({
            method: 'get',
            url: ajaxUrl,
            cache: false,
            dataType: "json",
            striped: false,	 //使表格带有条纹
            sortable: true,
            pagination: true,	//在表格底部显示分页工具栏
            pageSize: pageSize,
            pageNumber: 1,
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: false,//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: false,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            paginationShowPageGo: false,
            formatRecordsPerPage: function (pageNumber) {
                return '';
            },
            minimumCountColumns: 2,    //最少允许的列数
            responseHandler: function (res) {
                console.log(res);
                return {
                    "rows": res.records,
                    "total": res.total
                };
            },
            silent: true,  //刷新事件必须设置
            formatNoMatches: function () {  //没有匹配的结果
                return '无符合条件的记录';
            },
            rowStyle: rowStyleFunction,
            columns: columns
        });
    };

    /**
     * 弹出层
     */
    var layerOpen = function (title, width, height, content, options) {
        var closeBtn = (options === undefined || options.closeBtn === undefined || options.closeBtn === '') ? '1' : options.closeBtn;
        var w = (width.indexOf("px") > 0) ? width.substring(0, width.length - 2) : width;
        var documentHeight = $(document).height() - 75;
        var heightNum = (height.indexOf("px") > 0) ? height.substring(0, height.length - 2) : height;
        var h = 0;
        if (heightNum >= documentHeight)
            h = documentHeight * 0.9;
        else
            h = heightNum;
        return layer.open({
            type: 2,
            skin: "xyjtech-custom-layer",
            title: title,
            area: [w + 'px', h + 'px'],
            fix: false, //不固定
            shadeClose: false,
            shade: 0.1,
            scrollbar: true,
            closeBtn: closeBtn,
            content: [content, 'yes']
        });
    };
    /**
     * 初始化基本表格 v
     * @param tableId
     * @param queryUrl
     * @param columns
     * @returns {jQuery}
     */
    var initBootStrapTable = function (table, ajaxUrl, columns, defaultParams) {
        table.bootstrapTable({
            method: 'get',
            url: ajaxUrl,
            cache: false,
            dataType: "json",
            striped: true,	 //使表格带有条纹
            sortable: true,
            pagination: true,	//在表格底部显示分页工具栏
            pageSize: 10,
            pageNumber: 1,
            contentType: "application/x-www-form-urlencoded",
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: ((defaultParams) && (defaultParams.isSingle == false) ? false : true),//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: true,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求
            queryParams: function (pageParams) {
                if (defaultParams === null || defaultParams === undefined) {
                    defaultParams = {};
                }
                //兼容初始化table时候携带参数问题，如默认显示状态正常的数据，可以选择全部、禁用等
                return $.extend({}, pageParams, {params: defaultParams})
            },
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            minimumCountColumns: 1,    //最少允许的列数
            responseHandler: function (res) {
                return {
                    "rows": res.records,
                    "total": res.total
                };
            },
            silent: true,  //刷新事件必须设置
            formatNoMatches: function () {  //没有匹配的结果
                return '无符合条件的记录';
            },
            columns: columns
        });
    };


    /**
     * 初始化自定义基本表格 v
     * @param tableId
     * @param queryUrl
     * @param columns
     * @returns {jQuery}
     */
    var initBootStrapTableNoPage = function (table, ajaxUrl, columns, page, pageSize) {
        table.bootstrapTable({
            method: 'get',
            url: ajaxUrl,
            cache: false,
            dataType: "json",
            striped: true,	 //使表格带有条纹
            sortable: true,
            pagination: page,	//在表格底部显示分页工具栏
            pageSize: pageSize,
            pageNumber: 1,
            contentType: "application/x-www-form-urlencoded",
            idField: "id",  //标识哪个字段为id主键
            showToggle: false,   //名片格式
            cardView: false,//设置为True时显示名片（card）布局
            singleSelect: true,//复选框只能选择一条记录
            search: false,//是否显示右上角的搜索框
            clickToSelect: true,//点击行即可选中单选/复选框
            sidePagination: "server",//表格分页的位置
            queryParamsType: "", //参数格式,发送标准的RESTFul类型的参数请求
            strictSearch: true,
            showColumns: false,     //是否显示所有的列
            showRefresh: false,     //是否显示刷新按钮
            minimumCountColumns: 1,    //最少允许的列数
            responseHandler: function (res) {
                return {
                    "rows": res.records,
                    "total": res.total
                };
            },
            silent: true,  //刷新事件必须设置
            formatNoMatches: function () {  //没有匹配的结果
                return '无符合条件的记录';
            },
            columns: columns
        });
    };

    /**
     * Ajax操作
     * @param ajaxUrl
     * @param data
     * @param pTableId
     */
    var ajaxFormSubmit = function (ajaxUrl, formId, pTableId, successFn) {
        $.ajax({
            type: "POST",
            cache: false,
            url: ajaxUrl,
            data: $("#" + formId).serialize(),
            success: successFn ? successFn : function (responseJson) {
                var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
                parent.layer.close(index);
                var pTable = parent.$("#" + pTableId);
                Common.refreshTable(pTable);//刷新表格
            },
            beforeSend: function (XMLHttpRequest) {

            },
            complete: function (XMLHttpRequest, textStatus) {

            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {

            }
        });
    };

    /**
     * 刷新表格方法
     * @param tableId
     */
    var refreshTable = function (table, params) {
        var options = table.bootstrapTable('getOptions');
        if (params) {
            options.queryParams = function (options) {
                options['params'] = params;
                return options;
            }
        }
        table.bootstrapTable('refresh', options);
    };

    var refreshTableWithoutPage = function (table, params) {
        var options = table.bootstrapTable('getOptions');
        if (params) {
            options.queryParams = function (options) {
                $.each(params, function (name, value) {
                    options[name] = value
                });
                return options;
            }
        }
        table.bootstrapTable('refresh', options);
    };

    var getFormJson = function (formId) {
        var o = {};
        var a = $("#" + formId).serializeArray();
        $.each(a, function () {
            if (o[this.name] !== undefined) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
    /**
     * 获取表格选中行
     * @param table
     */
    var getTableSelections = function (table) {
        return table.bootstrapTable('getSelections');
    };

    /**
     * 封装Ajax调用
     */
    var ajaxSend = function (url, data, async, type, dataType, successfn, errorfn) {
        async = (async == null || async == "" || typeof (async) == "undefined") ? "true" : async;
        type = (type == null || type == "" || typeof (type) == "undefined") ? "post" : type;
        $.ajax({
            type: type,
            async: async,
            data: data,
            url: url,
            dataType: dataType,
            timeout: 0,
            success: function (d) {
                successfn(d);
            },
            error: function (e) {
                if (errorfn) {
                    errorfn(e);
                }
            }
        });
    };

    var serachTable = function (tableId, searchFormId) {
        var params = $('#' + tableId).bootstrapTable('getOptions');
        params.queryParams = function (params) {
            //定义参数
            var search = {};
            //遍历form 组装json
            var _params = {};
            $.each($('#' + searchFormId).serializeArray(), function (i, field) {
                if (null != field.value && "" != field.value) {
                    if (field.value !== '999999') {
                        _params[field.name] = field.value;
                    }
                }
            });
            params['params'] = _params;
            return params;
        };
        $('#' + tableId).bootstrapTable('refresh', params);
    };
    /**
     * 根据 fromId 查询 from 表单中参数, 并封装成json
     * @param searchFormId
     * @returns {{}}
     */
    var getFormParams = function (searchFormId) {
        var params = {};
        //遍历form 组装json
        $.each($('#' + searchFormId).serializeArray(), function (i, field) {
            if (null != field.value && "" != field.value) {
                params[field.name] = field.value;
            }
        });
        return params;
    };

    var urlParams = function (searchFormId) {
        var p = '';
        $.each($('#' + searchFormId).serializeArray(), function (i, field) {
            if (null != field.value && "" != field.value) {
                p += field.name;
                p += "=";
                p += field.value;
                p += "&";
            }
        });
        return p;
    };

    var bindSelect = function (ctrlId, url, value, options) {
        var control = $('#' + ctrlId);
        //设置Select2的处理
        control.select2({
            allowClear: false,
            placeholder: '请选择',
            multiple: (options === undefined || options.multiple === undefined || options.multiple === '') ? false : options.multiple,
            escapeMarkup: function (m) {
                return m;
            }
        });
        var defSelected = (options === undefined || options.defSelected === undefined || options.defSelected === '') ? true : options.defSelected;
        var defPlaceholder = (options === undefined || options.defPlaceholder === undefined || options.defPlaceholder === '') ? false : options.defPlaceholder;
        //绑定Ajax的内容
        $.getJSON(url, function (data) {
            if (defSelected) {
                control.empty();//清空下拉框
            }
            if (defPlaceholder) {
                //XXX 需要用到的地方进行值为999999的判断
                control.append("<option selected value='999999'>--请选择--</option>");
            }
            $.each(data, function (i, item) {
                if (value) {
                    if (value == item.value) {
                        control.append("<option selected value='" + item.value + "'>&nbsp;" + item.text + "</option>");
                    } else {
                        control.append("<option value='" + item.value + "'>&nbsp;" + item.text + "</option>");
                    }
                } else {
                    if (i === 0 && defSelected) {
                        control.append("<option selected value='" + item.value + "'>&nbsp;" + item.text + "</option>");
                    } else {
                        control.append("<option value='" + item.value + "'>&nbsp;" + item.text + "</option>");
                    }
                }
            });
            //设置选中
            // if(value){
            //     control.val(value).trigger("change");
            // }
            $('#' + ctrlId).change();
        });
    };
    var bindThreeSelect = function (cityName, countyName, url) {
        var cityControl = $('#' + cityName);
        //设置Select2的处理
        cityControl.select2({
            allowClear: false,
            placeholder: '请选择',
            escapeMarkup: function (m) {
                return m;
            }
        });

        var countyControl = $('#' + countyName);
        //设置Select2的处理
        countyControl.select2({
            allowClear: false,
            placeholder: '请选择',
            escapeMarkup: function (m) {
                return m;
            }
        });

        //绑定Ajax的内容
        $.getJSON(url, function (data) {
            cityControl.empty();//清空下拉框
            countyControl.empty();//清空下拉框
            $.each(data.cityList, function (i, item) {
                cityControl.append("<option value='" + item.value + "'>&nbsp;" + item.text + "</option>");
            });
            $.each(data.countyList, function (i, item) {
                countyControl.append("<option value='" + item.value + "'>&nbsp;" + item.text + "</option>");
            });
        });

    };
    var loading = function (msg) {
        var layerIndex = layer.load((msg === undefined || msg === '' || msg === null) ? '加载中' : msg, {
            icon: 16
            , shade: 0.1
        });
        return layerIndex;
    };

    var closeLoading = function (index) {
        layer.close(index);
    };

    var now = function () {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var hour = date.getHours();
        if (hour >= 1 && hour <= 9) {
            hour = "0" + hour;
        }
        var min = date.getMinutes();
        if (min >= 0 && min <= 9) {
            min = "0" + min;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + hour + seperator2 + min
            + seperator2 + date.getSeconds();
        return currentdate;
    };

    var now2min = function () {
        var date = new Date();
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        var hour = date.getHours();
        if (hour >= 1 && hour <= 9) {
            hour = "0" + hour;
        }
        var min = date.getMinutes();
        if (min >= 0 && min <= 9) {
            min = "0" + min;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
            + " " + hour + seperator2 + min;
        return currentdate;
    };

    /**
     * 弹出的iframe关闭自身
     */
    var closeFrame = function () {
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
        parent.layer.close(index); //再执行关闭
    };
    var initDatePicker = function (selector, options) {
        var formatter = "yyyy-mm-dd";
        if (options && options.formatter) {
            formatter = options.formatter;
        }
        $(selector).datetimepicker({
            language: 'zh-CN',
            weekStart: 1,
            todayBtn: 1,
            autoclose: true,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            format: formatter,
            showMeridian: true,
            minuteStep: 1
        });
    };

    return {
        ajaxSend: function (url, data, async, type, dataType, successfn, errorfn) {
            ajaxSend(url, data, async, type, dataType, successfn, errorfn)
        },
        initBootStrapTable: function (table, ajaxUrl, columns, params) {
            initBootStrapTable(table, ajaxUrl, columns, params);
        },
        initBootStrapTableNoPage: function (table, ajaxUrl, columns, page, pageSize) {
            initBootStrapTableNoPage(table, ajaxUrl, columns, page, pageSize);
        },
        layerOpen: function (title, width, height, content, options) {
            layerOpen(title, width, height, content, options);
        },
        refreshTable: function (table, params) {
            refreshTable(table, params);
        },
        refreshTableWithoutPage: function (table, params) {
            refreshTableWithoutPage(table, params);
        },
        getTableSelections: function (table) {
            return getTableSelections(table);
        },
        getFormJson: function (formId) {
            return getFormJson(formId);
        },
        searchTable: function (tableId, searchFormId) {
            return serachTable(tableId, searchFormId)
        },
        getFormParams: function (searchFormId) {
            /**
             * 根据 fromId 查询 from 表单中参数, 并封装成json
             */
            return getFormParams(searchFormId)
        },
        bindSelect: function (ctrlName, url, value, options) {
            return bindSelect(ctrlName, url, value, options);
        },
        bindThreeSelect: function (cityName, countyName, url) {
            return bindThreeSelect(cityName, countyName, url);
        },
        closeFrame: function () {
            closeFrame();
        },
        now: function () {
            return now();
        },
        ajaxFormSubmit: function (ajaxUrl, formId, pTableId, successFn) {

            return ajaxFormSubmit(ajaxUrl, formId, pTableId, successFn);
        },
        urlParams: function (formId) {
            return urlParams(formId);
        },
        loading: function (msg) {
            return loading(msg);
        },
        closeLoading: function (index) {
            closeLoading(index);
        },
        initDatePicker: function (selector, options) {
            initDatePicker(selector, options);
        },
        now2min: function () {
            return now2min;
        }
    }

}();