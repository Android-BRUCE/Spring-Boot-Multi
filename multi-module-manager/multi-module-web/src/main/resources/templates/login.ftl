<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <meta http-equiv="Expires" content="0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>后台管理</title>
    <link rel="icon" sizes="124x124" href="${basePath}/static/login/logo.png">
    <link rel="shortcut icon" href="${basePath}/static/login/logo.png">
    <link href="${basePath}/static/login/login.css" rel="stylesheet" type="text/css" />
    
    <link rel="stylesheet" href="${basePath}/static/login/css/animate.min.css" >
    <link rel="stylesheet" href="${basePath}/static/login/css/bootstrap.min.css">
    <link rel="stylesheet" href="${basePath}/static/login/css/font-awesome.min.css">
    <link rel="stylesheet" href="${basePath}/static/login/css/style.min.css">
    <link rel="stylesheet" href="${basePath}/static/login/css/iconfont.css">
    <link rel="stylesheet" href="${basePath}/static/login/js/validator-0.7.3/jquery.validator.css">
    <link rel="stylesheet" href="${basePath}/static/login/js/jquery-1.8.3.js">
    <link rel="stylesheet" href="${basePath}/static/login/css/sweetalert/sweetalert.css">
    <script src="${basePath}/static/login/js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="${basePath}/static/login/js/validator-0.7.3/jquery.validator.js"></script>
    <script type="text/javascript" src="${basePath}/static/login/js/validator-0.7.3/local/zh_CN.js"></script>
    <script type="text/javascript" src="${basePath}/static/login/js/host.js"></script>
    <script type="text/javascript" src="${basePath}/static/login/js/sweetalert/sweetalert.min.js"></script>


    <script type="text/javascript" src="${basePath}/static/login/js/md5-min.js"></script>
    <script type="text/javascript" src="${basePath}/static/login/js/lingx.js"></script>
</head>

<body>
<div class="login_box">
    <div class="login_l_img"><img src="${basePath}/static/login/login-img.png"/></div>
    <div class="login">
        <div class="login_logo"><a href="#"><img src="${basePath}/static/login/login_logo.png"/></a></div>
        <div class="login_name">
            <p>后台管理系统</p>
        </div>
        <form id="userForm" class=" animated rollIn"
              data-validator-option="{theme:'yellow_right_effect',stopOnError:true}">
            <div class="form-group">
                <input type="text" class="form-control"  placeholder="用户名" data-rule="用户名:required" id = "username">
            </div>
            <div class="form-group">
                <input type="password" class="form-control" placeholder="密码" data-rule="密码:required;" id = "password">
            </div>
            <div class="form-group col-xs-6" style="padding-left: 0px;">
                <img src="${basePath}/login/getGifCode.do">
            </div>
            <div class="form-group col-xs-6">
                <span><input type="text" class="form-control" placeholder="验证码" data-rule="验证码:required"
                             id="vcode"></span>
            </div>
            <button type="submit" style="width:100%;" class="btn btn-primary block full-width " onclick="login();">登 录
            </button>
        </form>
    </div>
    <div class="copyright">Copyright © 2015 - 2017 wanjiakeji.net All Rights Reserved 易万家版权所有 ｜ 仿冒必究浙ICP备14036718号-6
    </div>
</div>
</body>
<script type="text/javascript">
    var password3;
    function login() {
        var username = $("#username").val();
        var password = $("#password").val();
        var vcode = $("#vcode").val();
        //var rememberMe =$('#rememberMe').is(':checked');
        var re = encodePassword();
        if (re) {
            $.ajax({
                type: "POST",
                data: {
                    "username": username,
                    "password": password3,
                    "vcode": vcode
                    //	"rememberMe" : rememberMe
                },
                dataType: "json",
                url: "${basePath}/login/ajaxLogin.do",
                success: function (result) {

                    if (result.status != 200) {
                        swal("对不起哦", result.message, "error");
                    } else {
                        swal({title: "成功", text: "登录成功，进入系统！", type: "success"},
                                function () {
                                    location.href = "${basePath}/index.html";
                                });
                    }
                }
            });
        }
    }
    function encodePassword() {
        var password = "" + $("#password").val();
        if (password && password.length != 32) {
            var pwd = Lingx.javascriptPasswordEncode($("#password").val(), $("#username").val());
            $("#password").val(pwd);
            password3 = $("#password").val();
        }
        return true;
    }
</script>
</html>
