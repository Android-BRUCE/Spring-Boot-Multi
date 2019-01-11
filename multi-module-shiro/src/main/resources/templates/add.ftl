<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${basePath}/static/js/jquery-1.11.3.js"></script>
<title>Insert title here</title>
</head>
<body>
具有添加权限   

<input type="button" id="updatePermission" value="更新链接权限" />
</body>
<script type="text/javascript">
$("#updatePermission").click(function(){
	$.post("${basePath}/updatePermission", {}, function(result) {
		if (result == "true") {
			alert("权限更新成功！！");
		}
	});
});
</script>
</html>