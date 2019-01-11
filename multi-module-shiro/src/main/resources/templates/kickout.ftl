<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${basePath}/static/js/jquery-1.11.3.js"></script>
<title>被踢出</title>
</head>
<body>
被踢出 或则在另一地方登录，或已经达到此账号登录上限被挤掉。
<input type="button" id="login" value="重新登录" />
</body>
<script type="text/javascript">
$("#login").click(function(){
	window.open("${basePath}/login");
});
</script>
</html>