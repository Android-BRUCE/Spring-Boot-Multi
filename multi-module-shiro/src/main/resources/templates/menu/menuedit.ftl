<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>編輯</title>
    <link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/icon.css"/>

    <link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/icon.css"/>

    <link href="${basePath}/static/kingedit/js/kindeditor-4.1.10/themes/default/default.css" type="text/css"
          rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/material/easyui.css">



    <link href="${basePath}/static/css/content-base.css" rel="stylesheet"/>
    <link href="${basePath}/static/lib/bootstrap-Switch/bootstrapSwitch.css" rel="stylesheet"/>
</head>
<body class="gray-bg">
<div class="wrapper wrapper-content">
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5>菜单管理</h5>
            <div class="ibox-tools">
                <a class="collapse-link"> <i class="fa fa-chevron-up"></i>
                </a>
            </div>
        </div>
        <div class="ibox-content">
            <form action="${basePath}/sysMenu/edit" class="form-horizontal" method="post" id="indexaction">
                <input hidden="true" id="indexActionDetailId" name="indexActionDetailId" type="text"
                       value="<#if indexActionDetail??>${(indexActionDetail.indexActionDetailId)?c}</#if>"/>


                <div class="form-group">
                    <label class="col-sm-3 control-label"><label
                            for="LoginName">菜单名</label>：</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="sysMenuName" name="sysMenuName"
                               placeholder="菜单名" type="text"
                               value="<#if sysMenu??>${(sysMenu.sysMenuName)!""}</#if>"
                               data-val="true" data-val-maxlength="菜单名长度不能超过50个字符"
                               data-val-maxlength-max="50" data-val-required="封面标题不能为空"/> <span
                            data-valmsg-for="sysMenuName" data-valmsg-replace="true"
                            class="field-validation-valid"></span>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-3 control-label"><label
                            for="LoginName">菜单链接</label>：</label>
                    <div class="col-sm-8">
                        <input class="form-control" id="sysMenuUrl" name="sysMenuUrl"
                               placeholder="菜单链接" type="text"
                               value="<#if sysMenu??>${(sysMenu.sysMenuUrl)!""}</#if>"
                               data-val="true" data-val-maxlength="菜单链接度不能超过50个字符"
                               data-val-maxlength-max="50" data-val-required="封面标题不能为空"/> <span
                            data-valmsg-for="sysMenuUrl" data-valmsg-replace="true"
                            class="field-validation-valid"></span>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-3 control-label"><label
                            for="RealName">选择一级菜单</label>：</label>
                    <div class="col-sm-8">
                        <select class="form-control" name="sys_parent" id="sys_parent">
                            <option>请选择一级菜单</option>
                                <#if sysMenus??>
                                    <#list sysMenus>
                                        <#items as item>
                                            <#if sys_parent == (item.sysMenuId) >
                                                <option  selected='selected'
                                                         value=${item.sysMenuId}>${item.sysMenuName}</option>
                                            <#else>
                                                <option value=${item.sysMenuId}>${item.sysMenuName}</option>
                                            </#if>
                                        </#items>
                                    </#list>
                                </#if>
                        </select>
                    </div>
                </div>


                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-8">
                        <button class="btn btn-info" type="submit" id="btnSave">保存</button>
                        <button class="btn btn-white" type="button" id="back"
                                data-type="url">返回
                        </button>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>

<script type="text/javascript" src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.min.js"></script>


<script type="text/javascript"
        src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${basePath}/static/kingedit/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${basePath}/static/kingedit/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript"
        src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common.js"></script>
<script type="text/javascript" charset="utf-8" src="${basePath}/static/js/dateConvent.js"></script>


<script src="${basePath}/static/js/content/base.js"></script>
<script src="${basePath}/static/js/content/action.js"></script>
<script src="${basePath}/static/js/content/jqueryValidator.js"></script>
<script src="${basePath}/static/lib/bootstrap-Switch/bootstrapSwitch.js"></script>

<script type="text/javascript">
  //  var itemAddEditor;
    $(function () {
        TT.kingEditorParams.uploadJson = "${basePath}/pic/upload";
        TT.initOnePicUpload();
        //itemAddEditor = KindEditor.create("#indexaction [name=indexActionDetailContent]", TT.kingEditorParams);
        TAOTAO.init({
            fun: function (node) {
                //  TAOTAO.changeItemParam(node, "goodsform");
            }
        });
    });

    $("#back").bind("click", function () {
      //  itemAddEditor.sync();
        window.location.href = "/sysMenu/listPage";
    });
</script>
</body>
</html>

