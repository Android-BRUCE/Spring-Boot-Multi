<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Redis监控</title>
    <link href="${basePath}/static/css/content-base.css"
          rel="stylesheet"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="row">
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis 内存实时占用情况：</h5>
                </div>
                <div class="ibox-content">
                    <div id="container"></div>
                </div>
            </div>
        </div>
        <div class="col-sm-6">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis key的实时数量：</h5>
                </div>
                <div class="ibox-content">
                    <div id="keysChart"></div>
                </div>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-5">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis INFO：</h5>
                </div>
                <div class="ibox-content">
                    <table class="table table-condensed table-hover"
                           style="word-break: break-all; word-wrap: break-all;">
                        <tbody>


	<#list infoList as info>
    <tr>
        <td title="${info.key !0}:${info.desctiption!0}">${info.key!0}</td>
        <td>${info.desctiption!0}</td>
        <td>${info.value!0}</td>
    </tr>
    </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="col-sm-7">
            <div class="ibox float-e-margins">
                <div class="ibox-title">
                    <h5>Redis实时日志(共${logLen!0}条)：</h5>
                    <div class="ibox-tools">
                        <button type="button" onclick="empty();"
                                class="btn btn-warning btn-xs">清空日志
                        </button>
                    </div>
                </div>
                <div class="ibox-content">
                    <table class="table table-condensed table-hover">
                        <tbody>
<#if logList??><#list logList as log>
                                <tr>
                                    <td>${log.id!0}</td>
                                    <td>${log.executeTime!0}</td>
                                    <td>${log.usedTime!0}</td>
                                    <td>
                                        <p style="width: 600px; word-wrap: break-word; word-break: normal;">${log.args!0}</p>
                                    </td>
                                </tr>
</#list><#else></#if>


                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script src="${basePath}/static/js/jquery.js"></script>
<script src="${basePath}/static/js/redischarts/highcharts.js"></script>
<script src="${basePath}/static/js/redischarts/index.js"></script>
</html>

