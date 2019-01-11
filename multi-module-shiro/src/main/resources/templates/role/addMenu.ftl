<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>編輯</title>
<link href="${basePath}/static/css/content-base.css"
	rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.net/Public/js/easyui/themes/default/easyui.css">
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.net/Public/js/easyui/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="http://www.jeasyui.net/Public/js/easyui/demo/demo.css">

</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content">
		<div class="ibox float-e-margins">
			<div class="ibox-title">
				<h5>编辑角色菜单</h5>
				<div class="ibox-tools">
					<a class="collapse-link"> <i class="fa fa-chevron-up"></i>
					</a>
				</div>
			</div>
			<div class="ibox-content">
				<form action="${basePath}/role/edit" class="form-horizontal" method="post">
					<input hidden="true" id="id" name="id" type="text"
						value="<#if roleId??>${roleId!""}</#if>" />
					<div class="form-group">
                        <label class="col-sm-3 control-label"><label
                                for="RealName">勾选菜单</label>：</label>
						<div class="col-sm-8">
                            <ul id="tree02" class="easyui-tree" animate="true">
                            </ul>
						</div>
					</div>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-8">
							<#--<button class="btn btn-info" type="submit" id="btnSave">保存</button>-->
							<button class="btn btn-white" type="button" id="back"
								data-type="url">返回</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
	<script src="${basePath}/static/js/content/base.js"></script>
	<script src="${basePath}/static/js/content/action.js"></script>
	<script src="${basePath}/static/js/content/jqueryValidator.js"></script>
    <script type="text/javascript" src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.min.js"></script>
    <script type="text/javascript"
            src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/JSMAP.js"></script>
	<script type="text/javascript">
	$("#back").bind("click", function() {
		window.location.href="/role/rolePage";
	});
	</script>
<script>
    var myMap = new Map();
    $('#tree02').tree({
        //开启动画效果
        animate: true,
        //每个节点都要显示复选框
        checkbox: true,
        //连线
        lines: true,
        url: '${basePath}/role/MenuTree.do?roleId='+$("#id").val(),
        onBeforeLoad: function (node, param) {
            var rooNode = $("#tree02").tree('getRoot');
            //调用expand方法
            $("#tree02").tree('expand', rooNode);
        },
        onCheck: function (node, checked) {
            var nodeid = node.id;
            if (checked) {
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/role/roleMenu/add.do?menuId="+node.id+"&status=1&roleId="+$("#id").val(),
                        cache: false,
                        dataType: "json",
                        success: function (results) {
                        }
                    });
            }
            if(!checked) {
                    $.ajax({
                        type: "POST",
                        url: "${basePath}/role/roleMenu/delete.do?menuId="+node.id+"&status=0&roleId="+$("#id").val(),
                        cache: false,
                        dataType: "json",
                        success: function (results) {

                        }
                    });
            }
        }
    });
</script>
</body>
</html>

