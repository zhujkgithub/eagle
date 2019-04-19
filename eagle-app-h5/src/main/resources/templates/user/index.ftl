<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>MUI 分页demo</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="${rc.contextPath}/plugins/mui/css/mui.min.css">
    <link rel="stylesheet" href="${rc.contextPath}/plugins/mui/css/mui.picker.min.css">
    <link rel="stylesheet" href="${rc.contextPath}/css/reset.css">
    <link rel="stylesheet" href="${rc.contextPath}/css/common.css">
    <link rel="stylesheet" href="${rc.contextPath}/css/user/index.css">
</head>
<body>
<div class="wrap">
    <div class="mui-scroll-wrapper">
        <div class="mui-scroll">
            <div class="contentBox">
                <!-- 服务律师 -->
                <div class="lawyerBox">
                    <div class="lawyerList">
                        <div id="content"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${rc.contextPath}/js/jquery-1.11.3.min.js"></script>
<script src="${rc.contextPath}/plugins/mui/js/mui.min.js"></script>
<script src="${rc.contextPath}/plugins/mui/js/mui.picker.min.js"></script>
<script src="${rc.contextPath}/js/common.js"></script>
<script>
    $(function(){
        setRem(750,100);
        var data = {pageNumber: 1, pageSize: 10};
        initPullRefreshJson('.mui-scroll-wrapper', '${rc.contextPath!}/demo/queryPage', data, '#content', null);
    });
</script>
</body>
</html>