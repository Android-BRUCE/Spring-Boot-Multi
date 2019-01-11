<link rel="stylesheet" type="text/css"
      href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/metro-blue/easyui.css">
<link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/icon.css"/>
<script type="text/javascript" src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.min.js"></script>
<script type="text/javascript" src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/jquery.easyui.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${basePath}/static/kingedit/js/kindeditor-4.1.10/kindeditor-all-min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${basePath}/static/kingedit/js/kindeditor-4.1.10/lang/zh_CN.js"></script>
<script type="text/javascript"
        src="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/locale/easyui-lang-zh_CN.js"></script>
<link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/jquery-easyui-1.4.1/themes/icon.css"/>
<link rel="stylesheet" type="text/css" href="${basePath}/static/kingedit/js/kindeditor-4.1.10/css/taotao.css"/>
<link href="${basePath}/static/kingedit/js/kindeditor-4.1.10/themes/default/default.css" type="text/css"
      rel="stylesheet">

<style>
    * {
        margin: 0;
        padding: 0;
    }

    ::-webkit-scrollbar-track {
        background-color: transparent;
    }

    ::-webkit-scrollbar {
        width: 12px;
        height: 12px;
    }

    ::-webkit-scrollbar-thumb {
        background-color: #c0c0c0;
    }

    ::-webkit-scrollbar-thumb:hover {
        background-color: #848484;
    }

    ::-webkit-scrollbar-corner {
        background-color: transparent;
    }

    ::-webkit-scrollbar-button {
        background-color: #848484;
    }

    ::-webkit-scrollbar-button:horizontal:single-button:start {
        border-radius: 12px 0 0 12px;
        cursor: pointer;
        width: 10px;
        height: 15px;
    }

    ::-webkit-scrollbar-button:horizontal:single-button:end {
        border-radius: 0 12px 12px 0;
        cursor: pointer;
        width: 10px;
        height: 15px;
    }

    ::-webkit-scrollbar-button:vertical:single-button:start {
        border-radius: 12px 12px 0 0;
        cursor: pointer;
        width: 15px;
        height: 10px;
    }

    ::-webkit-scrollbar-button:vertical:single-button:end {
        border-radius: 0 0 12px 12px;
        cursor: pointer;
        width: 15px;
        height: 10px;
    }

    a {
        color: #339;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }

    /* button
    ---------------------------------------------- */
    .button {
        display: inline-block;
        zoom: 1; /* zoom and *display = ie7 hack for display:inline-block */
        *display: inline;
        vertical-align: baseline;
        margin: 0 2px;
        outline: none;
        cursor: pointer;
        text-align: center;
        text-decoration: none;
        font: 14px/100% Arial, Helvetica, sans-serif;
        padding: .5em 2em .55em;
        text-shadow: 0 1px 1px rgba(0, 0, 0, .3);
        -webkit-border-radius: .5em;
        -moz-border-radius: .5em;
        border-radius: .5em;
        -webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
        -moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
        box-shadow: 0 1px 2px rgba(0, 0, 0, .2);
    }

    .button:hover {
        text-decoration: none;
    }

    .button:active {
        position: relative;
        top: 1px;
    }

    .bigrounded {
        -webkit-border-radius: 2em;
        -moz-border-radius: 2em;
        border-radius: 2em;
    }

    .medium {
        font-size: 12px;
        padding: .4em 1.5em .42em;
    }

    .small {
        font-size: 11px;
        padding: .2em 1em .275em;
    }

    /* color styles
    ---------------------------------------------- */
    /* blue */
    .blue {
        color: #d9eef7;
        border: solid 1px #0076a3;
        background: #0095cd;
        background: -webkit-gradient(linear, left top, left bottom, from(#00adee), to(#0078a5));
        background: -moz-linear-gradient(top, #00adee, #0078a5);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#00adee', endColorstr='#0078a5');
    }

    .blue:hover {
        background: #007ead;
        background: -webkit-gradient(linear, left top, left bottom, from(#0095cc), to(#00678e));
        background: -moz-linear-gradient(top, #0095cc, #00678e);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0095cc', endColorstr='#00678e');
    }

    .blue:active {
        color: #80bed6;
        background: -webkit-gradient(linear, left top, left bottom, from(#0078a5), to(#00adee));
        background: -moz-linear-gradient(top, #0078a5, #00adee);
        filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#0078a5', endColorstr='#00adee');
    }
</style>

<div style="width: fixWidth();height: fixHeight()">
    <p style="color: red">PDF转换！</p>
    <form id="LuckDrawModel_excel" class="itemForm" method="post" enctype=”multipart/form-data”>
        <table cellpadding="5">
            <tr>
                <td>选择PDF文件:</td>
                <td><input class="easyui-filebox" type="text" name="uploadFile"
                           data-options="required:true,validType:'length[0,20]'"
                           style="width: 280px;"/></td>
            </tr>
            <tr></tr>
        </table>
    <#--        <input type="hidden" name="id" value="1"/>-->
    </form>
    <div style="padding:5px;text-align: center;margin-bottom: 10px">
        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">提交</a>
    </div>
</div>

<div id="show" class="easyui-window" closed="true"></div>
<script type="text/javascript">
    var word;
    function submitForm() {
        var formData = new FormData($("#LuckDrawModel_excel")[0]);
        $.ajax({
            type: "POST",
            url: "${basePath}/test/pdf/convent.do",
            data: formData,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
               // alert(data.msg);
                word = data.msg;
                $('#show').window({
                    width: 800,
                    height: 500,
                    title: "转化详情",
                    modal: true
                });
                $('#show').window('refresh', '${basePath}/show_details.ftl');//刷新内容
                $('#show').window('center');//居中 people_modify.ftl
                $('#show').window('open'); // 打开窗口
            }
        });
    }
</script>
