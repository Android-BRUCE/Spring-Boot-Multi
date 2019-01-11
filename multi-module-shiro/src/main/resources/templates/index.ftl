<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>后台管理|首页</title>
   <link href="${basePath}/static/assets/css/dpl-min.css" rel="stylesheet" type="text/css" />
   <link href="${basePath}/static/assets/css/bui-min.css" rel="stylesheet" type="text/css" />
   <link href="${basePath}/static/assets/css/main.css" rel="stylesheet" type="text/css" />
 </head>
 <body>
	<div class="header">
		<div class="dl-title">
			<a href="####" title="个人博客地址" target="_blank">
				<span class="lp-title-port">z77z</span><span class="dl-title-text">后台管理系统</span>
			</a>
		</div>
		<div class="dl-log">
			欢迎您，<span class="dl-log-user">${uname}</span> <span class="admin">（${rname}）</span>
			<a href="logout" title="退出系统" class="dl-log-quit">[退出]</a>
		</div>
	</div>
	<div class="content">
		<div class="dl-main-nav">
			<ul id="J_Nav" class="nav-list ks-clear">
				<li class="nav-item dl-selected"><div
						class="nav-item-inner nav-storage">首页</div></li>
			</ul>
		</div>
		<ul id="J_NavContent" class="dl-tab-conten">
		</ul>
	</div>
	
  <script type="text/javascript" src="${basePath}/static/assets/js/jquery-1.8.1.min.js"></script>
  <script type="text/javascript" src="${basePath}/static/lib/layer/1.9.3/layer.js"></script>
  <script type="text/javascript" src="${basePath}/static/assets/js/bui-min.js"></script>
  <script type="text/javascript" src="${basePath}/static/assets/js/config-min.js"></script>
  <script type="text/javascript" src="${basePath}/static/js/barrage.js"></script>
  <script>
      var configJson = [${menuInt}];
      var configJson2 = [{
          id:'system',
          menu:[{
              text:'系统管理',
              items:[
                  {id:'yhgl',text:'用户管理',href:'${basePath}/user/userPage'},
                  {id:'qxgl',text:'权限管理',href:'${basePath}/permission/permissionPage' },
                  {id:'jsgl',text:'角色管理',href:'${basePath}/role/rolePage' },
                  {id:'csqxgl',text:'初始权限管理',href:'${basePath}/permissionInit/permissionInitPage'},
                  {id:'zxyhgl',text:'在线用户管理',href:'${basePath}/user/onlineUserPage'},
                  {id:'qxcsym',text:'权限测试页面',href:'${basePath}/add.ftl'},
                  {id:'rjk',text:'Redis监控',href:'${basePath}/redis/redisMonitor'},
                  {id:'djk',text:'Druid监控',href:'${basePath}/druid'}
              ]
          },{
              text:'系统管理',
              items:[
                  {id:'yhgl',text:'用户管理',href:'/user/userPage'},
                  {id:'qxgl',text:'权限管理',href:'/permission/permissionPage' },
                  {id:'jsgl',text:'角色管理',href:'/role/rolePage' },
                  {id:'csqxgl',text:'初始权限管理',href:'/permissionInit/permissionInitPage'},
                  {id:'zxyhgl',text:'在线用户管理',href:'/user/onlineUserPage'},
                  {id:'qxcsym',text:'权限测试页面',href:'/add.ftl'},
                  {id:'rjk',text:'Redis监控',href:'/redis/redisMonitor'},
                  {id:'djk',text:'Druid监控',href:'/druid'}
              ]
          }

          ]
      }];
	BUI.use('common/main',function(){
      var config = configJson;
      new PageUtil.MainPage({
        modulesConfig : config
      });
    });
  </script>
 </body>
</html>